package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ohmuk.folitics.businessDelegate.interfaces.IFeedSourceBusinessDeligate;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedSource;
import com.ohmuk.folitics.service.newsfeed.IFeedSourceService;

public class FeedSourceBusinessDeligate implements IFeedSourceBusinessDeligate {

	final static Logger logger = LoggerFactory.getLogger(FeedSourceBusinessDeligate.class);

	@Autowired
	private IFeedSourceService feedSourceService;

	@Override
	public FeedSource create(FeedSource feedSource) throws MessageException {

		logger.info("Inside create FeedSourceBusinessDeligate");

		feedSource = feedSourceService.create(feedSource);

		logger.info("Exiting create FeedSourceBusinessDeligate");

		return feedSource;
	}

	@Override
	public FeedSource read(Long id) throws MessageException {

		logger.info("Inside read FeedSourceBusinessDeligate");

		FeedSource feedSource = feedSourceService.read(id);

		logger.info("Exiting read FeedSourceBusinessDeligate");

		return feedSource;
	}

	@Override
	public List<FeedSource> readByName(String name) throws MessageException {

		logger.info("Inside readByName FeedSourceBusinessDeligate");

		List<FeedSource> feedSource = feedSourceService.readByName(name);

		logger.info("Exiting readByName FeedSourceBusinessDeligate");

		return feedSource;
	}

	@Override
	public List<FeedSource> readAll() throws MessageException {

		logger.info("Inside readAll FeedSourceBusinessDeligate");

		List<FeedSource> feedSource = feedSourceService.readAll();

		logger.info("Exiting readAll FeedSourceBusinessDeligate");

		return feedSource;
	}

	@Override
	public FeedSource update(FeedSource feedSource) throws MessageException {

		logger.info("Inside update FeedSourceBusinessDeligate");

		feedSource = feedSourceService.update(feedSource);

		logger.info("Exiting update FeedSourceBusinessDeligate");

		return feedSource;
	}

	@Override
	public FeedSource delete(Long id) throws MessageException {

		logger.info("Inside delete FeedSourceBusinessDeligate");

		FeedSource feedSource = feedSourceService.delete(id);

		logger.info("Exiting delete FeedSourceBusinessDeligate");

		return feedSource;
	}

	@Override
	public FeedSource createDefault(String name, String url) throws MessageException {

		logger.info("Inside createDefault FeedSourceBusinessDeligate");

		FeedSource feedSource = feedSourceService.createDefault(name, url);

		logger.info("Exiting createDefault FeedSourceBusinessDeligate");

		return feedSource;
	}

	@Override
	public Boolean disableFeedSource(Long id) throws MessageException {

		logger.info("Inside disableFeedSource FeedSourceBusinessDeligate");

		Boolean feedSource = feedSourceService.disableFeedSource(id);

		logger.info("Exiting disableFeedSource FeedSourceBusinessDeligate");

		return feedSource;
	}

}
