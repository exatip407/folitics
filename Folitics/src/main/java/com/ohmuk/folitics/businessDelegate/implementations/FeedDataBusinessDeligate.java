package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ohmuk.folitics.businessDelegate.interfaces.IFeedDataBusinessDeligate;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedData;
import com.ohmuk.folitics.service.newsfeed.IFeedDataService;

public class FeedDataBusinessDeligate implements IFeedDataBusinessDeligate{

	final static Logger logger = LoggerFactory.getLogger(FeedDataBusinessDeligate.class);

	@Autowired
	private IFeedDataService feedDataService;

	@Override
	public FeedData create(FeedData feedData) throws MessageException {
		
		logger.info("Inside create FeedDataBusinessDeligate");

		feedData = feedDataService.create(feedData);

		logger.info("Exiting create FeedDataBusinessDeligate");

		return feedData;
	}

	@Override
	public FeedData read(Long id) throws MessageException {
		
		logger.info("Inside read FeedDataBusinessDeligate");

		FeedData feedData = feedDataService.read(id);

		logger.info("Exiting read FeedDataBusinessDeligate");

		return feedData;
	}

	@Override
	public List<FeedData> readAll() throws MessageException {
		
		logger.info("Inside readAll FeedDataBusinessDeligate");

		List<FeedData> feedData = feedDataService.readAll();

		logger.info("Exiting readAll FeedDataBusinessDeligate");

		return feedData;
	}

	@Override
	public FeedData update(FeedData feedData) throws MessageException {
		
		logger.info("Inside update FeedDataBusinessDeligate");

		feedData = feedDataService.update(feedData);

		logger.info("Exiting update FeedDataBusinessDeligate");

		return feedData;
	}

	@Override
	public FeedData delete(Long id) throws MessageException {

		logger.info("Inside delete FeedDataBusinessDeligate");

		FeedData feedData = feedDataService.delete(id);

		logger.info("Exiting delete FeedDataBusinessDeligate");

		return feedData;
	}

	@Override
	public FeedData findByLink(String link) throws MessageException {

		logger.info("Inside delete FeedDataBusinessDeligate");

		FeedData feedData = feedDataService.findByLink(link);

		logger.info("Exiting delete FeedDataBusinessDeligate");

		return feedData;
	}

}
