package com.ohmuk.folitics.service.newsfeed;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedChannel;
import com.ohmuk.folitics.util.DateUtils;

@Service
@Transactional
public class FeedChannelService implements IFeedChannelService {

	private static Logger logger = LoggerFactory.getLogger(FeedChannelService.class);

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public FeedChannel create(FeedChannel feedChannel) throws MessageException {
		if (feedChannel.getTimestamp() == null) {
			feedChannel.setTimestamp(DateUtils.getSqlTimeStamp());
		}

		logger.info("Inside FeedChannelService create method");

		Long id = (Long) getSession().save(feedChannel);

		logger.info("Existing from FeedChannelService create method");
		return read(id);
	}

	@Override
	public FeedChannel read(Long id) throws MessageException {

		logger.info("Inside FeedChannelService read method");

		FeedChannel feedChannel = (FeedChannel) getSession().get(FeedChannel.class, id);

		if (feedChannel == null) {
			logger.error("No records found in database for corresponding id: "+id);
			throw (new MessageException("No records found in database for corresponding id: "+id));
		}
		
		logger.info("Existing from FeedChannelService read method");
		return feedChannel;
	}

	@Override
	public List<FeedChannel> readAll() throws MessageException {

		logger.info("Inside FeedChannelService readAll method");

		List<FeedChannel> feedChannel = getSession().createCriteria(FeedChannel.class).list();

		if (feedChannel == null) {
			logger.error("No records found in database");
			throw (new MessageException("No records found in database"));
		}
		
		logger.info("Existing from FeedChannelService readAll method");

		return feedChannel;
	}

	@Override
	public FeedChannel update(FeedChannel feedChannel) throws MessageException, Exception {

		logger.info("Inside FeedChannelService save method");

		getSession().merge(feedChannel);

		FeedChannel feedChannelOriginal = read(feedChannel.getId());

		if (feedChannelOriginal == null) {
			logger.error("No records found in database for the corresponding obect having id: " + feedChannel.getId());
			throw (new MessageException(
					"No records found in database for the corresponding obect having id: " + feedChannel.getId()));
		}

		getSession().update(feedChannel);
		feedChannel = (FeedChannel) getSession().get(FeedChannel.class, feedChannel.getId());

		logger.info("Existing from FeedChannelService save method");

		return feedChannel;
	}

	@Override
	public FeedChannel delete(Long id) {

		logger.info("Inside FeedChannelService delete method");

		FeedChannel feedChannel = (FeedChannel) getSession().get(FeedChannel.class, id);
		getSession().delete(feedChannel);
		feedChannel = (FeedChannel) getSession().get(FeedChannel.class, id);

		logger.info("Existing from FeedChannelService delete method");

		return feedChannel;
	}
}
