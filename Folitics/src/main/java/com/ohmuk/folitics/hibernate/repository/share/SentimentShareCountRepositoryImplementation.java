package com.ohmuk.folitics.hibernate.repository.share;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ohmuk.folitics.hibernate.entity.share.SentimentShareCount;
import com.ohmuk.folitics.hibernate.entity.share.SentimentShareCountId;

/**
 * Repository implementation for entity: {@link SentimentShareCount}
 * 
 * @author Abhishek
 *
 */

@Component
@Repository
public class SentimentShareCountRepositoryImplementation implements
		ISentimentShareCountRepository {

	private static Logger logger = LoggerFactory
			.getLogger(SentimentShareCountRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public SentimentShareCount save(SentimentShareCount sentimentShareCount) {

		logger.debug("Entered SentimentShareCount save method");
		logger.debug("Saving share count for sentiment id = "
				+ sentimentShareCount.getId().getSentiment().getId());

		SentimentShareCountId id = (SentimentShareCountId) getSession().save(
				sentimentShareCount);
		sentimentShareCount = (SentimentShareCount) getSession().get(
				SentimentShareCount.class, id);

		logger.debug("SentimentShareCount saved. Exiting save method");
		return sentimentShareCount;
	}

	@Override
	public SentimentShareCount update(SentimentShareCount sentimentShareCount) {

		logger.debug("Entered SentimentShareCount update method");
		logger.debug("Updating share count for sentiment id = "
				+ sentimentShareCount.getId().getSentiment().getId());

		sentimentShareCount = (SentimentShareCount) getSession().merge(
				sentimentShareCount);
		getSession().update(sentimentShareCount);

		sentimentShareCount = (SentimentShareCount) getSession().get(
				SentimentShareCount.class, sentimentShareCount.getId());

		logger.debug("Updated SentimentShareCount. Exiting update method");
		return sentimentShareCount;
	}

	@Override
	public List<SentimentShareCount> findAll() {

		logger.debug("Entered SentimentShareCount findAll method");
		logger.debug("Getting all share count");

		@SuppressWarnings("unchecked")
		List<SentimentShareCount> sentimentShareCounts = getSession()
				.createCriteria(SentimentShareCount.class).list();

		logger.debug("Returning all SentimentShareCount. Exiting findAll method");
		return sentimentShareCounts;
	}

	@Override
	public SentimentShareCount findByComponentId(Long sentimentId) {

		logger.debug("Entered SentimentShareCount findByComponentId method");
		logger.debug("Getting share count for sentiment id = " + sentimentId);

		Criteria selectCriteria = getSession().createCriteria(
				SentimentShareCount.class);
		selectCriteria.add(Restrictions.eq("id.sentiment.id", sentimentId));
		SentimentShareCount sentimentShareCount = (SentimentShareCount) selectCriteria
				.uniqueResult();

		/*
		 * Query query = getSession().createSQLQuery(
		 * "FROM SentimentShareCount S WHERE S.sentimentId = :sentimentId")
		 * .addEntity(SentimentShareCount.class).setParameter("sentimentId",
		 * sentimentId); SentimentShareCount sentimentShareCount =
		 * (SentimentShareCount) query.uniqueResult();
		 */

		logger.debug("Returning SentimentShareCount. Exiting findByComponentId method");
		return sentimentShareCount;
	}

	@Override
	public SentimentShareCount find(SentimentShareCountId id) {

		logger.debug("Entered SentimentShareCount find method");
		logger.debug("Getting share count for sentiment id = "
				+ id.getSentiment().getId());

		SentimentShareCount sentimentShareCount = (SentimentShareCount) getSession()
				.get(SentimentShareCount.class, id);

		logger.debug("Returning SentimentShareCount. Exiting find method");
		return sentimentShareCount;
	}

	@Override
	public void delete(SentimentShareCountId id) {

		logger.debug("Entered SentimentShareCount delete method");
		logger.debug("Deleting share count for sentiment id = "
				+ id.getSentiment().getId());

		SentimentShareCount sentimentShareCount = (SentimentShareCount) getSession()
				.get(SentimentShareCount.class, id);

		logger.debug("Deleted SentimentShareCount. Exiting delete method");
		getSession().delete(sentimentShareCount);
	}

}
