package com.ohmuk.folitics.hibernate.repository.like;

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

import com.ohmuk.folitics.hibernate.entity.like.SentimentCommentLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.SentimentCommentLikeCountId;

/**
 * Repository implementation for entity: {@link SentimentCommentLikeCount}
 * 
 * @author Harish
 *
 */

@Component
@Repository
public class SentimentCommentLikeCountRepositoryImplementation implements
		ISentimentCommentLikeCountRepository {

	private static Logger logger = LoggerFactory
			.getLogger(TaskCommentLikeCountRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public SentimentCommentLikeCount save(
			SentimentCommentLikeCount sentimentCommentLikeCount) {
		logger.info("Entered SentimentCommentLikeCount save method");
		logger.debug("Saving like count for sentimentComment id = "
				+ sentimentCommentLikeCount.getId().getSentimentComment()
						.getId());

		SentimentCommentLikeCountId id = (SentimentCommentLikeCountId) getSession()
				.save(sentimentCommentLikeCount);
		sentimentCommentLikeCount = (SentimentCommentLikeCount) getSession()
				.get(SentimentCommentLikeCount.class, id);

		logger.info("SentimentCommentLikeCount saved. Exiting save method");
		return sentimentCommentLikeCount;
	}

	@Override
	public SentimentCommentLikeCount update(
			SentimentCommentLikeCount sentimentCommentLikeCount) {
		logger.info("Entered SentimentCommentLikeCount update method");
		logger.debug("Updating like count for sentimentComment id = "
				+ sentimentCommentLikeCount.getId().getSentimentComment()
						.getId());

		sentimentCommentLikeCount = (SentimentCommentLikeCount) getSession()
				.merge(sentimentCommentLikeCount);
		getSession().update(sentimentCommentLikeCount);

		sentimentCommentLikeCount = (SentimentCommentLikeCount) getSession()
				.get(SentimentCommentLikeCount.class,
						sentimentCommentLikeCount.getId());

		logger.info("SentimentCommentLikeCount updated. Exiting update method");
		return sentimentCommentLikeCount;
	}

	@Override
	public List<SentimentCommentLikeCount> findAll() {
		logger.info("Entered SentimentCommentLikeCount findAll method");

		Criteria selectAllCriteria = getSession().createCriteria(
				SentimentCommentLikeCount.class);
		@SuppressWarnings("unchecked")
		List<SentimentCommentLikeCount> sentimentCommentLikeCounts = selectAllCriteria
				.list();

		logger.info("Returning all results from database. Exiting findAll method");
		return sentimentCommentLikeCounts;
	}

	@Override
	public SentimentCommentLikeCount findByComponentId(Long sentimentCommentId) {
		logger.info("Entered SentimentCommentLikeCount findByComponentId method");
		logger.debug("Finding like count for sentimentComment id = "
				+ sentimentCommentId);

		Criteria selectCriteria = getSession().createCriteria(
				SentimentCommentLikeCount.class);
		selectCriteria.add(Restrictions.eq("id.sentimentComment.id",
				sentimentCommentId));
		SentimentCommentLikeCount sentimentCommentLikeCount = (SentimentCommentLikeCount) selectCriteria
				.uniqueResult();

		logger.info("Returning SentimentCommentLikeCount. Exiting findByComponentId method");
		return sentimentCommentLikeCount;
	}

	@Override
	public SentimentCommentLikeCount find(SentimentCommentLikeCountId id) {
		logger.info("Entered SentimentCommentLikeCount find method");
		logger.debug("Getting like count for sentimentComment id = "
				+ id.getSentimentComment().getId());

		SentimentCommentLikeCount sentimentCommentLikeCount = (SentimentCommentLikeCount) getSession()
				.get(SentimentCommentLikeCount.class, id);

		logger.info("Returning result from database. Exiting update method");
		return sentimentCommentLikeCount;
	}

	@Override
	public void delete(SentimentCommentLikeCountId id) {
		logger.info("Entered SentimentCommentLikeCount delete method");
		logger.debug("Deleting like count for sentimentComment id = "
				+ id.getSentimentComment().getId());

		SentimentCommentLikeCount sentimentCommentLikeCount = (SentimentCommentLikeCount) getSession()
				.get(SentimentCommentLikeCount.class, id);

		logger.info("Deleted SentimentCommentLikeCount. Exiting delete method");
		getSession().delete(sentimentCommentLikeCount);

	}

}
