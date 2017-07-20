package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ohmuk.folitics.businessDelegate.interfaces.IFeedChannelBusinessDeligate;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedChannel;
import com.ohmuk.folitics.service.newsfeed.IFeedChannelService;

public class FeedChannelBusinessDeligate implements IFeedChannelBusinessDeligate {

	final static Logger logger = LoggerFactory.getLogger(FeedChannelBusinessDeligate.class);

	@Autowired
	private IFeedChannelService feedChannelService;

	@Override
	public FeedChannel create(FeedChannel feedChannel) throws MessageException, Exception {

		logger.info("Inside create FeedChannelBusinessDeligate");

		feedChannel = feedChannelService.create(feedChannel);

		logger.info("Exiting create FeedChannelBusinessDeligate");

		return feedChannel;
	}

	@Override
	public FeedChannel read(Long id) throws MessageException, Exception {

		logger.info("Inside read FeedChannelBusinessDeligate");

		FeedChannel feedChannel = feedChannelService.read(id);

		logger.info("Exiting read FeedChannelBusinessDeligate");

		return feedChannel;
	}

	@Override
	public List<FeedChannel> readAll() throws MessageException, Exception {

		logger.info("Inside readAll FeedChannelBusinessDeligate");

		List<FeedChannel> feedChannel = feedChannelService.readAll();

		logger.info("Exiting readAll FeedChannelBusinessDeligate");

		return feedChannel;
	}

	@Override
	public FeedChannel update(FeedChannel feedChannel) throws MessageException, Exception {

		logger.info("Inside update FeedChannelBusinessDeligate");

		feedChannel = feedChannelService.update(feedChannel);

		logger.info("Exiting update FeedChannelBusinessDeligate");

		return feedChannel;
	}

	@Override
	public FeedChannel delete(Long id) throws MessageException, Exception {

		logger.info("Inside delete FeedChannelBusinessDeligate");

		FeedChannel feedChannel = feedChannelService.delete(id);

		logger.info("Exiting delete FeedChannelBusinessDeligate");

		return feedChannel;
	}

}
