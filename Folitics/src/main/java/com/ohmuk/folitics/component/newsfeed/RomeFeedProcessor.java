package com.ohmuk.folitics.component.newsfeed;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedChannel;
import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedChannelImage;
import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedData;
import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedSource;
import com.rometools.rome.feed.synd.SyndCategory;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndImage;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

public class RomeFeedProcessor implements IRSSFeedProcessor {
    
    protected static final Logger LOGGER = LoggerFactory.getLogger(RomeFeedProcessor.class);
   
    private SyndFeed feed;
    private FeedSource feedSource;

    RomeFeedProcessor(FeedSource feedSource) {
        this.feedSource = feedSource;

        URL feedSourceUrl;
        try {
            feedSourceUrl = new URL(this.feedSource.getFeedURL());

            SyndFeedInput input = new SyndFeedInput();
            this.feed = input.build(new XmlReader(feedSourceUrl));

            RSSFeedUtil.printRSSFeedHeader(feed);
            RSSFeedUtil.printRSSFeedItems(feed);
        } catch (MalformedURLException e) {
            LOGGER.error("Error while preparing ROME Feed Processor : MalformedURLException ",e);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error while preparing ROME Feed Processor : IllegalArgumentException ",e);
        } catch (FeedException e) {
            LOGGER.error("Error while preparing ROME Feed Processor : FeedException ",e);
        } catch (IOException e) {
            LOGGER.error("Error while preparing ROME Feed Processor : IOException ",e);
        }
        if (this.feed == null) {
            throw new RuntimeException("ROME Feed Processor: Feed could not be parsed!!");
        }
    }

    private SyndImage getSyndImage(SyndFeed feed) {
        return feed.getImage();
    }

    public SyndContent getDescriptionEx(SyndFeed feed) {
        return feed.getDescriptionEx();
    }

    public static final String getEntryDescription(SyndEntry entry) {
        if (entry != null && entry.getDescription() != null) {
            return entry.getDescription().getValue();
        }
        return null;
    }

    public List<SyndCategory> getCategories(SyndFeed feed) {
        List<SyndCategory> cats = feed.getCategories();
        RSSFeedUtil.printCategories(cats);
        return feed.getCategories();
    }

    private String getCategoriesString(List<SyndCategory> cats) {
        RSSFeedUtil.printCategories(cats);
        return RSSFeedUtil.getCategories(cats);
    }

    public List<SyndEntry> printEntries(SyndFeed feed) {
        List<SyndEntry> entries = feed.getEntries();
        RSSFeedUtil.printEntries(entries);
        return feed.getEntries();
    }

    private List<SyndEntry> getEntries(SyndFeed feed) {
        return feed.getEntries();
    }

    @Override
    public FeedChannelImage getImage() {
        SyndImage syndImage = getSyndImage(this.feed);
        if (syndImage != null) {
            FeedChannelImage image = new FeedChannelImage();
            image.setLink(syndImage.getLink());
            image.setDescription(syndImage.getDescription());
            image.setTitle(syndImage.getTitle());
            image.setUrl(syndImage.getUrl());

            return image;
        } else
            return null;
    }

    @Override
    public FeedChannel getChannel(FeedSource fd) {
        FeedChannel feedChannel = new FeedChannel();
        feedChannel.setCopyright(this.feed.getCopyright());
        feedChannel.setDescription(this.feed.getDescription());
        feedChannel.setLanguage(this.feed.getLanguage());
        feedChannel.setLink(this.feed.getLink());
        feedChannel.setTitle(this.feed.getTitle());
        feedChannel.setFeedSource(fd);
        return feedChannel;
    }

    @Override
    public Set<FeedData> getData() {
        Set<FeedData> feedDataLinks = new LinkedHashSet<FeedData>();
        List<SyndEntry> entries = getEntries(this.feed);
        if (entries != null) {
            for (SyndEntry entry : entries) {
                FeedData feedData = new FeedData();
                feedData.setAuthor(entry.getAuthor());
                feedData.setCategory(getCategoriesString(entry.getCategories()));
                feedData.setDescription(RSSFeedUtil.getContent(entry.getDescription()));
                feedData.setFeedItemXML(this.feed.toString());
                feedData.setLink(entry.getLink());
                feedData.setPublishedDate(entry.getPublishedDate());
                feedData.setTitle(entry.getTitle());
                feedData.setUpdatedDate(entry.getUpdatedDate());
                feedData.setUri(entry.getUri());
                feedData.setScrapped(Boolean.FALSE);
                feedData.setScrapError(Boolean.FALSE);
                feedData.setAttemps(1);
                
                feedDataLinks.add(feedData);
                
            }
        }
        return feedDataLinks;
    }
}
