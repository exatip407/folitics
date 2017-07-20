package com.ohmuk.folitics.component.newsfeed;

import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.component.newsfeed.scrapper.NewsFeedScrapper;
import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedChannel;
import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedChannelImage;
import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedData;
import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedSource;
import com.ohmuk.folitics.mongodb.entity.NewsFeed;
import com.ohmuk.folitics.mongodb.service.INewsFeedMongodbService;
import com.ohmuk.folitics.service.newsfeed.IFeedChannelService;
import com.ohmuk.folitics.service.newsfeed.IFeedDataService;
import com.ohmuk.folitics.service.newsfeed.IFeedSourceService;

@Component("rssFeedAggegrator")
public class RSSFeedAggegrator {

    protected static final Logger LOGGER = LoggerFactory.getLogger(RSSFeedAggegrator.class);

    @Autowired
    private IFeedSourceService feedSourceService;

    @Autowired
    private IFeedChannelService feedChannelService;

    @Autowired
    private IFeedDataService feedDataService;

    @Autowired
    private INewsFeedMongodbService newsFeedMongodbService;

    public List<FeedSource> fidAllFeedSources() throws Exception {
        return feedSourceService.readAll();
    }

    public List<FeedSource> findFeedSoruce(String sourceName) throws Exception {
        return feedSourceService.readByName(sourceName);
    }

    /**
     * Clear the data in following order to do the cleanup 1. Clear news feed from mongodb 2. Clear feed data 3. Clear
     * feed channel 4. clear feed source
     * 
     * @param feedSourceId
     * @return
     */
    public boolean clearFeed(Long feedSourceId) throws Exception {
        FeedSource feedSource = feedSourceService.read(feedSourceId);
        if (feedSource != null) {
            newsFeedMongodbService.deleteByFeedSourceId(feedSourceId);
            feedSourceService.delete(feedSource.getId());
            return true;
        }
        return false;
    }

    private NewsFeed scrap(FeedData feedData, FeedChannel feedChannel, FeedSource feedSource) {
        Document document = NewsFeedScrapper.scrap(feedData.getLink());
        if (document != null) {
            String htmlText = NewsFeedScrapper.getHtmlText(document);
            String plainText = NewsFeedScrapper.getPlainText(document);
            NewsFeed newsFeed = prepareNewsFeed(feedData, feedChannel, feedSource, htmlText, plainText);
            newsFeed = newsFeedMongodbService.create(newsFeed);
            return newsFeed;
        } else {
            feedData.setScrapError(Boolean.TRUE);
            return null;
        }
    }

    public boolean disableFeedSource(Long id) throws Exception {
        return feedSourceService.disableFeedSource(id);
    }

    public FeedSource addFeedSource(String sourceName, String sourceURL) throws Exception {
        return feedSourceService.createDefault(sourceName, sourceURL);
    }

    // @Scheduled(fixedRate = 5000)
    public boolean aggregateFeed(String sourceName, List<String> messages) {

        try {
            // List<FeedSource> feedSources = feedSourceService.readAll();
            List<FeedSource> feedSources = feedSourceService.readByName(sourceName);
            if (feedSources == null || feedSources.isEmpty()) {
                messages.add("No active RSS Feed source found in database with name : " + sourceName
                        + "Please add or enable the feed source.");
                return false;
            }
            for (FeedSource feedSource : feedSources) {
                if (!feedSource.getDisabled()) {
                    Integer records = feedSource.getRecords();
                    IRSSFeedProcessor feedProcessor = RSSFeedProcessorFactory.getRSSFeedProcessor(
                            RSSFeedProcessorEnum.ROME.getValue(), feedSource);

                    FeedChannel feedChannel = feedProcessor.getChannel(feedSource);
                    FeedChannelImage feedChannelImage = feedProcessor.getImage();
                    feedChannel.setFeedChannelImage(feedChannelImage);
                    feedChannel = feedChannelService.create(feedChannel);

                    Set<FeedData> feeds = feedProcessor.getData();

                    int count = 0;
                    for (FeedData feedData : feeds) {
                        feedData.setLink(getCompleteLink(feedChannel, feedData));
                        FeedData existingFeed = feedDataService.findByLink(feedData.getLink());
                        if (existingFeed != null && existingFeed.getScrapped() && !existingFeed.getScrapError()) {
                            continue;
                        } else if (existingFeed != null
                                && !(existingFeed.getScrapped() && !existingFeed.getScrapError())) {
                            feedData = existingFeed;
                            if (feedData.getAttemps().intValue() < RSSFeedUtil.MAX_SCRAP_ATTEMPTS) {
                                NewsFeed newsFeed = scrap(feedData, feedChannel, feedSource);
                                if (newsFeed != null) {
                                    count++;
                                    feedData.setScrapError(Boolean.FALSE);
                                }
                                feedData.setScrapped(Boolean.TRUE);
                                feedData.setAttemps(feedData.getAttemps() + 1);
                                feedData = feedDataService.update(feedData);
                                if (newsFeed != null) {
                                    newsFeed.setFeedDataId(feedData.getId());
                                    newsFeedMongodbService.update(newsFeed);
                                }
                            }
                        } else {
                            feedData.setFeedChannel(feedChannel);
                            NewsFeed newsFeed = scrap(feedData, feedChannel, feedSource);
                            if (newsFeed != null) {
                                count++;
                                feedData.setScrapped(Boolean.TRUE);
                            }
                            try {
                                feedData = feedDataService.create(feedData);
                                if (newsFeed != null) {
                                    newsFeed.setFeedDataId(feedData.getId());
                                    newsFeedMongodbService.update(newsFeed);
                                }
                            } catch (Exception e) {
                                // Deleting the feed from mongo db if
                                // FeedData failed to save
                                if (newsFeed != null) {
                                    newsFeedMongodbService.delete(newsFeed.getId());
                                }
                                LOGGER.error("Failed to save feed data : ", e);
                            }

                        }
                        if (count >= records.intValue()) {
                            // break if the records limit reaches
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            messages.add("Failed to load feed from source : " + sourceName);
            LOGGER.error("Failed aggregateFeed : ", e);
            return false;
        }
        messages.add("Feed loaded successfully from source : " + sourceName);
        return true;
    }

    private NewsFeed prepareNewsFeed(FeedData feedData, FeedChannel feedChannel, FeedSource feedSource,
            String htmlPage, String plainText) {
        NewsFeed nf = new NewsFeed();
        nf.setLink(feedData.getLink());
        nf.setHtmlText(htmlPage);
        nf.setPlainText(plainText);
        nf.setFeedChannelId(feedChannel.getId());
        nf.setFeedDataId(feedData.getId());
        nf.setFeedSourceId(feedSource.getId());
        nf.setCategory(feedData.getCategory());
        return nf;
    }

    public List<NewsFeed> searchText(String keyword) {
        return newsFeedMongodbService.searchText(keyword);
    }

    private static final String getCompleteLink(FeedChannel feedChannel, FeedData feedData) {
        StringBuilder sBuilder = new StringBuilder();
        if (feedData.getLink().contains("http")) {
            return feedData.getLink();
        } else {
            sBuilder.append(feedChannel.getLink());
            sBuilder.append(feedData.getLink().replace("../", "/"));
        }
        return sBuilder.toString();
    }
}
