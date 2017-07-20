package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.businessDelegate.interfaces.ISentimentBusinessDelegate;
import com.ohmuk.folitics.hibernate.entity.Category;
import com.ohmuk.folitics.hibernate.entity.Link;
import com.ohmuk.folitics.hibernate.entity.SentimentOpinionStat;
import com.ohmuk.folitics.hibernate.entity.Sentiment;
import com.ohmuk.folitics.hibernate.entity.poll.Poll;
import com.ohmuk.folitics.mongodb.entity.NewsFeed;
import com.ohmuk.folitics.mongodb.service.INewsFeedMongodbService;
import com.ohmuk.folitics.service.IPollService;
import com.ohmuk.folitics.service.ISentimentService;

@Component
@Transactional
public class SentimentBusinessDelegate implements ISentimentBusinessDelegate {

	private static Logger logger = LoggerFactory
			.getLogger(SentimentBusinessDelegate.class);

	@Autowired
	private volatile ISentimentService sentimentService;

	@Autowired
	IPollService pollService;

	@Autowired
	private INewsFeedMongodbService newsFeedService;

	@Override
	public Sentiment save(Sentiment sentiment) throws Exception {
		logger.info("Inside save method in business delegate");
		Sentiment sentimentData = sentimentService.save(sentiment);
		logger.info("Exiting save method in business delegate");
		return sentimentData;
	}

	@Override
	public Sentiment read(Long id) throws Exception {
		logger.info("Inside read method in business delegate");
		Sentiment sentimentData = sentimentService.read(id);
		logger.info("Exiting read method in business delegate");
		return sentimentData;
	}

	@Override
	public List<Sentiment> readAll() throws Exception {
		logger.info("Inside readAll method in business delegate");
		List<Sentiment> sentimentData = sentimentService.readAll();
		logger.info("Exiting readAll method in business delegate");
		return sentimentData;
	}

	@Override
	public Sentiment update(Sentiment sentiment) throws Exception {
		logger.info("Inside update method in business delegate");
		Sentiment sentimentData = sentimentService.update(sentiment);
		logger.info("Exiting update method in business delegate");
		return sentimentData;
	}

	@Override
	public boolean delete(Long id) throws Exception {
		logger.info("Inside delete method in business delegate");
		boolean sucess = sentimentService.delete(id);
		logger.info("Exiting delete method in business delegate");
		return sucess;
	}

	@Override
	public boolean delete(Sentiment sentiment) throws Exception {
		logger.info("Inside delete method in business delegate");
		boolean sucess = sentimentService.delete(sentiment);
		logger.info("Exiting delete method in business delegate");
		return sucess;
	}

	@Override
	public boolean deleteFromDB(Long id) throws Exception {
		logger.info("Inside deleteFromDBById method in business delegate");
		Sentiment sentiment = read(id);
		for (Poll poll : sentiment.getPolls()) {
			poll.setSentiment(null);
			pollService.update(poll);
		}

		boolean sucess = sentimentService.deleteFromDB(sentiment);
		logger.info("Exiting deleteFromDBById method in business delegate");
		return sucess;
	}

	@Override
	public boolean deleteFromDB(Sentiment sentiment) throws Exception {
		logger.info("Inside deleteFromDB method in business delegate");
		boolean sucess = sentimentService.deleteFromDB(sentiment);
		logger.info("Exiting deleteFromDB method in business delegate");
		return sucess;
	}

	@Override
	public boolean updateSentimentStatus(Long id, String status)
			throws Exception {
		logger.info("Inside updateSentimentStatus method in business delegate");
		boolean sucess = sentimentService.updateSentimentStatus(id, status);
		logger.info("Exiting updateSentimentStatus method in business delegate");
		return sucess;
	}

	@Override
	public Sentiment clone(Sentiment sentiment) throws Exception {
		logger.info("Inside clone method in business delegate");
		sentiment.setId(null);
		sentiment.setState("New");
		Sentiment sentimentData = sentimentService.clone(sentiment);
		logger.info("Exiting clone method in business delegate");
		return sentimentData;
	}

	@Override
	public List<Sentiment> getAllSentimentNotIn(Set<Long> sentimentIds)
			throws Exception {
		logger.info("Inside getAllSentimentNotIn method in business delegate");
		List<Sentiment> sentimentData = sentimentService
				.getAllSentimentNotIn(sentimentIds);
		logger.info("Exiting getAllSentimentNotIn method in business delegate");
		return sentimentData;
	}

	@Override
	public List<Link> getAllSourcesForSentiment(Sentiment sentiment)
			throws Exception {
		logger.info("Inside getAllSourcesForSentiment method in business delegate");
		List<Link> links = sentimentService
				.getAllSourcesForSentiment(sentiment);
		logger.info("Exiting getAllSourcesForSentiment method in business delegate");
		return links;
	}

	@Override
	public Set<Category> getAllIndicator(Long sentimentId) throws Exception {
		logger.info("Inside SentimentBusinessDelegate getAllIndicator method");
		Sentiment sentiment = sentimentService.read(sentimentId);
		if (null != sentiment) {
			Set<Category> indicators = new HashSet<Category>();

			for (Category subCategory : sentiment.getCategories()) {
				for (Category childCategory : subCategory.getChilds()) {
					Hibernate.initialize(subCategory.getChilds());
					indicators.add(childCategory);
				}
			}
			logger.info("Existing from SentimentBusinessDelegate getAllIndicator method");
			return indicators;
		} else {
			logger.info("Existing from SentimentBusinessDelegate getAllIndicator method");
			return null;
		}
	}

	@Override
	public List<Sentiment> findByType(String type) throws Exception {
		logger.info("Inside find by type method in business delegate");
		List<Sentiment> sentimentData = sentimentService.findByType(type);
		logger.info("Exiting findbytype method in business delegate");
		return sentimentData;
	}

	@Override
	public Set<Sentiment> getRelatedSentiment(Long sentimentId)
			throws Exception {
		// TODO Auto-generated method stub
		return sentimentService.getRelatedSentiment(sentimentId);
	}

	@Override
	public String getSentimentSupport(Long sentimentId) throws Exception {
		SentimentOpinionStat opinionStat = sentimentService
				.getSentimentOpinionStat(sentimentId);
		if (null != opinionStat) {
			if (opinionStat.getAgainstPoints() > opinionStat.getFavorPoints()) {
				return "anti";
			}
			if (opinionStat.getFavorPoints() > opinionStat.getAgainstPoints()) {
				return "pro";
			}
		}
		return "nutral";
	}

	@Override
	public void saveNewsFeed() {

		newsFeedService.saveNewsFeed();

	}

	@Override
	public NewsFeed updateNewsFeedWithSentimentId(String newsFeedId,
			Long sentimentId) {

		NewsFeed newsFeed = newsFeedService.updateWithSentiment(newsFeedId,
				sentimentId);
		return newsFeed;

	}

	@Override
	public List<NewsFeed> getNewsFeedBySentimentId(Long sentimentId) {

		List<NewsFeed> newsFeeds = newsFeedService
				.findBySentimentId(sentimentId);
		return newsFeeds;

		
	}

}
