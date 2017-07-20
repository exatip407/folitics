package com.ohmuk.folitics.service.newsfeed;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedData;
import com.ohmuk.folitics.util.DateUtils;

@Service
@Transactional
public class FeedDataService implements IFeedDataService {

	private static Logger logger = LoggerFactory.getLogger(FeedDataService.class);

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public FeedData create(FeedData feedData) throws MessageException {
		if (feedData.getTimestamp() == null) {
			feedData.setTimestamp(DateUtils.getSqlTimeStamp());
		}

		logger.info("Inside FeedData save method");

		Long id = (Long) getSession().save(feedData);

		logger.info("Existing from FeedData save method");

		return read(id);
	}

	@Override
	public FeedData read(Long id) throws MessageException {

		logger.info("Inside FeedData read method");

		FeedData feedData = (FeedData) getSession().get(FeedData.class, id);

		if (feedData == null) {
			logger.error("No records found in database");
			throw (new MessageException("No records found in database"));
		}
		
		logger.info("Existing from FeedData read method");

		return feedData;

	}

	@Override
	public List<FeedData> readAll() {

		logger.info("Inside FeedData save method");

		List<FeedData> feedData = getSession().createCriteria(FeedData.class).list();

		logger.info("Existing from FeedData save method");

		return feedData;

	}

	@Override
	public FeedData update(FeedData feedData) throws MessageException {

		logger.info("Inside FeedData save method");

		getSession().merge(feedData);

		FeedData feedDataOriginal = read(feedData.getId());

		if (feedDataOriginal == null) {
			logger.error("No records found in database for the corresponding obect having id: " + feedData.getId());
			throw (new MessageException(
					"No records found in database for the corresponding obect having id: " + feedData.getId()));
		}

		getSession().update(feedData);
		feedData = (FeedData) getSession().get(FeedData.class, feedData.getId());

		logger.info("Existing from FeedData save method");

		return feedData;
	}

	@Override
	public FeedData delete(Long id) throws MessageException {

		logger.info("Inside FeedData delete method");

		FeedData feedData = (FeedData) getSession().get(FeedData.class, id);
		getSession().delete(feedData);
		feedData = (FeedData) getSession().get(FeedData.class, id);

		logger.info("Existing from FeedData delete method");

		return feedData;
	}

	@Override
	public FeedData findByLink(String link) {

		logger.info("Inside FeedData findByLink method");

		Criteria criteria = getSession().createCriteria(FeedData.class);
		criteria.add(Restrictions.eqOrIsNull("link", link));
		FeedData feedData = (FeedData) criteria.uniqueResult();

		logger.info("Existing from FeedData findByLink method");

		return feedData;
	}
}
