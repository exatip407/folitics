package com.ohmuk.folitics.mongodb.service;

import java.net.UnknownHostException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.MongoAutoConfiguration;
import com.ohmuk.folitics.component.newsfeed.NewsFeedMongodbDAO;
import com.ohmuk.folitics.mongodb.entity.NewsFeed;
import com.ohmuk.folitics.mongodb.repository.INewsFeedMongoRepository;

@Service
@Transactional
public class NewsFeedMongodbService implements INewsFeedMongodbService {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(NewsFeedMongodbService.class);

	@Autowired
	private MongoAutoConfiguration mongoAutoConfiguration;

	@Autowired
	INewsFeedMongoRepository newsFeedRepository;

	@Autowired
	NewsFeedMongodbDAO newsFeedMongodbDAO;

	@Override
	public NewsFeed create(NewsFeed newsFeed) {
		return newsFeedRepository.save(newsFeed);
	}

	@Override
	public NewsFeed read(String id) {
		return newsFeedRepository.findById(id);
	}

	@Override
	public List<NewsFeed> readAll() {
		return newsFeedRepository.findAll();
	}

	@Override
	public NewsFeed update(NewsFeed newsFeed) {
		NewsFeed originalNewsFeed = newsFeedRepository.findById(newsFeed
				.getId());
		if (originalNewsFeed == null)
			return null;
		return newsFeedRepository.save(newsFeed);
	}

	@Override
	public NewsFeed updateWithSentiment(String newsFeedId, Long sentimentId) {

		NewsFeed originalNewsFeed = newsFeedRepository.findById(newsFeedId);
		if (originalNewsFeed == null)
			return null;
		originalNewsFeed.setSentimentId(sentimentId);

		return newsFeedRepository.save(originalNewsFeed);
	}

	@Override
	public NewsFeed delete(String id) {
		newsFeedRepository.delete(id);
		return newsFeedRepository.findOne(id);
	}

	@Override
	public List<NewsFeed> searchText(String keyWord) {
		try {
			System.out.println("mongoAutoConfiguration : "
					+ mongoAutoConfiguration.getProperties().getDatabase());
			TextCriteria criteria = TextCriteria.forDefaultLanguage().matching(
					keyWord);
			Query query = TextQuery.queryText(criteria).sortByScore()
					.with(new PageRequest(0, 5));
			MongoTemplate template = mongoAutoConfiguration.getMongoTemplate();
			List<NewsFeed> newsFeeds = template.find(query, NewsFeed.class);
			return newsFeeds;
		} catch (UnknownHostException e) {
			LOGGER.error("Failed in mongodb text search: Unknown Host ", e);
		} catch (Exception e) {
			LOGGER.error("Failed in mongodb text search", e);
		}
		return null;
	}

	@Override
	public NewsFeed findById(String id) {
		return newsFeedRepository.findById(id);
	}

	@Override
	public List<NewsFeed> findByFeedSourceId(Long feedSourceId) {
		return newsFeedRepository.findByFeedSourceId(feedSourceId);
	}

	@Override
	public List<NewsFeed> findByFeedChannelId(Long feedSourceId,
			Long feedChannelId) {
		try {
			return newsFeedMongodbDAO.findByFeedChannelId(feedSourceId,
					feedChannelId);
		} catch (Exception e) {
			LOGGER.error("Error file searching news feed", e);
		}
		return null;
	}

	@Override
	public List<NewsFeed> findByFeedDataId(Long feedSourceId,
			Long feedChannelId, Long feedDataId) {
		try {
			return newsFeedMongodbDAO.findByFeedDataId(feedSourceId,
					feedChannelId, feedDataId);
		} catch (Exception e) {
			LOGGER.error("Error file searching news feed", e);
		}
		return null;
	}

	@Override
	public int deleteByFeedSourceId(Long feedSourceId) {
		return newsFeedMongodbDAO.deleteByFeedSourceId(feedSourceId);
	}

	@Override
	public int deleteByFeedChannelId(Long feedSourceId, Long feedChannelId) {
		return newsFeedMongodbDAO.deleteByFeedChannelId(feedSourceId,
				feedChannelId);
	}

	@Override
	public int deleteByFeedDataId(Long feedSourceId, Long feedChannelId,
			Long feedDataId) {
		return newsFeedMongodbDAO.deleteByFeedDataId(feedSourceId,
				feedChannelId, feedDataId);
	}

	@Override
	public List<NewsFeed> findBySentimentId(Long sentimentId) {
		return newsFeedRepository.findBySentimentId(sentimentId);
	}

	public void saveNewsFeed() {

		NewsFeed newsFeed = new NewsFeed();
		newsFeed.setCategory("test newsFeed");
		newsFeed.setFeedChannelId(2l);
		newsFeed.setFeedDataId(34l);
		newsFeed.setFeedSourceId(2l);
		newsFeed.setSentimentId(1l);
		newsFeed.setHtmlText("test html text");
		newsFeed.setPlainText("dfbfgb");
		newsFeed.setLink("http://localhost:8080");
		create(newsFeed);

	}

}
