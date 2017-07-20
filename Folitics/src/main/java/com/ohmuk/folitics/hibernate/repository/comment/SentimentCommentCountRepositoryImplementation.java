package com.ohmuk.folitics.hibernate.repository.comment;

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

import com.ohmuk.folitics.hibernate.entity.comment.SentimentCommentCount;
import com.ohmuk.folitics.hibernate.entity.comment.SentimentCommentCountId;

@Component
@Repository
public class SentimentCommentCountRepositoryImplementation implements
		ISentimentCommentCountRepository {

	private static Logger logger = LoggerFactory
			.getLogger(SentimentCommentCountRepositoryImplementation.class);
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public SentimentCommentCount save(
			SentimentCommentCount sentimentCommentCount) {
		logger.info("Entered SentimentCommentCount save method");
		logger.debug("Saving comment count for senitment id = "
				+ sentimentCommentCount.getId().getSentiment().getId());

		SentimentCommentCountId id = (SentimentCommentCountId) getSession()
				.save(sentimentCommentCount);
		sentimentCommentCount = (SentimentCommentCount) getSession().get(
				SentimentCommentCount.class, id);

		logger.info("SentimentCommentCount saved. Exiting save method");
		return sentimentCommentCount;
	}

	@Override
	public SentimentCommentCount update(
			SentimentCommentCount sentimentCommentCount) {
		logger.info("Entered SentimentCommentCount update method");
		logger.debug("Updating comment count for senitment id = "
				+ sentimentCommentCount.getId().getSentiment().getId());

		sentimentCommentCount = (SentimentCommentCount) getSession().merge(
				sentimentCommentCount);
		getSession().update(sentimentCommentCount);

		sentimentCommentCount = (SentimentCommentCount) getSession().get(
				SentimentCommentCount.class, sentimentCommentCount.getId());

		logger.info("SentimentCommentCount updated. Exiting update method");
		return sentimentCommentCount;
	}

	@Override
	public List<SentimentCommentCount> findAll() {
		logger.info("Entered SentimentCommentCount findAll method");

		Criteria selectAllCriteria = getSession().createCriteria(
				SentimentCommentCount.class);
		@SuppressWarnings("unchecked")
		List<SentimentCommentCount> sentimentCommentCounts = selectAllCriteria
				.list();

		logger.info("Returning all results from database. Exiting findAll method");
		return sentimentCommentCounts;

	}

	@Override
	public SentimentCommentCount findByComponentId(Long sentimentId) {
		logger.info("Entered SentimentCommentCount findByComponentId method");
		logger.debug("Finding like count for senitment id = " + sentimentId);

		Criteria selectCriteria = getSession().createCriteria(
				SentimentCommentCount.class);
		selectCriteria.add(Restrictions.eq("id.senitment.id", sentimentId));
		SentimentCommentCount sentimentCommentCount = (SentimentCommentCount) selectCriteria
				.uniqueResult();

		logger.info("Returning SentimentCommentCount. Exiting findByComponentId method");
		return sentimentCommentCount;
	}

	@Override
	public SentimentCommentCount find(SentimentCommentCountId id) {
		logger.info("Entered SentimentCommentCount find method");
		logger.debug("Getting like count for senitment id = "
				+ id.getSentiment().getId());

		SentimentCommentCount sentimentCommentCount = (SentimentCommentCount) getSession()
				.get(SentimentCommentCount.class, id);

		logger.info("Returning result from database. Exiting update method");
		return sentimentCommentCount;
	}

	@Override
	public void delete(SentimentCommentCountId id) {
		logger.info("Entered SentimentCommentCount delete method");
		logger.debug("Deleting Comment count for senitment id = "
				+ id.getSentiment().getId());

		SentimentCommentCount sentimentCommentCount = (SentimentCommentCount) getSession()
				.get(SentimentCommentCount.class, id);

		logger.info("Deleted SentimentCommentCount. Exiting delete method");
		getSession().delete(sentimentCommentCount);

	}

}
