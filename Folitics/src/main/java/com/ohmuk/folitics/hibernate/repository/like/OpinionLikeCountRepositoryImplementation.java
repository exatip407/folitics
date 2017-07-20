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

import com.ohmuk.folitics.hibernate.entity.like.OpinionLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.OpinionLikeCountId;

/**
 * Repository implementation for entity: {@link OpinionLikeCount}
 * 
 * @author Hsrish
 *
 */

@Component
@Repository
public class OpinionLikeCountRepositoryImplementation implements
		IOpinionLikeCountRepository {

	private static Logger logger = LoggerFactory
			.getLogger(OpinionLikeCountRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public OpinionLikeCount save(OpinionLikeCount opinionLikeCount) {
		logger.info("Entered OpinionLikeCount save method");
		logger.debug("Saving like count for sentiment id = "
				+ opinionLikeCount.getId().getOpinion().getId());

		OpinionLikeCountId id = (OpinionLikeCountId) getSession().save(
				opinionLikeCount);
		opinionLikeCount = (OpinionLikeCount) getSession().get(
				OpinionLikeCount.class, id);

		logger.info("OpinionLikeCount saved. Exiting save method");
		return opinionLikeCount;
	}

	@Override
	public OpinionLikeCount update(OpinionLikeCount opinionLikeCount) {
		logger.info("Entered OpinionLikeCount update method");
		logger.debug("Updating like count for sentiment id = "
				+ opinionLikeCount.getId().getOpinion().getId());

		opinionLikeCount = (OpinionLikeCount) getSession().merge(
				opinionLikeCount);
		getSession().update(opinionLikeCount);

		opinionLikeCount = (OpinionLikeCount) getSession().get(
				OpinionLikeCount.class, opinionLikeCount.getId());

		logger.info("OpinionLikeCount updated. Exiting update method");
		return opinionLikeCount;
	}

	@Override
	public List<OpinionLikeCount> findAll() {

		logger.info("Entered OpinionLikeCount findAll method");

		Criteria selectAllCriteria = getSession().createCriteria(
				OpinionLikeCount.class);
		@SuppressWarnings("unchecked")
		List<OpinionLikeCount> opinionLikeCounts = selectAllCriteria.list();

		logger.info("Returning all results from database. Exiting findAll method");
		return opinionLikeCounts;
	}

	@Override
	public OpinionLikeCount findByComponentId(Long opinionId) {

		logger.info("Entered OpinionLikeCount findByComponentId method");
		logger.debug("Finding like count for sentiment id = " + opinionId);

		Criteria selectCriteria = getSession().createCriteria(
				OpinionLikeCount.class);
		selectCriteria.add(Restrictions.eq("id.opinion.id", opinionId));
		OpinionLikeCount opinionLikeCount = (OpinionLikeCount) selectCriteria
				.uniqueResult();

		logger.info("Returning OpinionLikeCount. Exiting findByComponentId method");
		return opinionLikeCount;
	}

	@Override
	public OpinionLikeCount find(OpinionLikeCountId id) {
		logger.info("Entered OpinionLikeCount find method");
		logger.debug("Getting like count for sentiment id = "
				+ id.getOpinion().getId());

		OpinionLikeCount opinionLikeCount = (OpinionLikeCount) getSession()
				.get(OpinionLikeCount.class, id);

		logger.info("Returning result from database. Exiting update method");
		return opinionLikeCount;
	}

	@Override
	public void delete(OpinionLikeCountId id) {
		logger.info("Entered OpinionLikeCount delete method");
		logger.debug("Deleting like count for sentiment id = "
				+ id.getOpinion().getId());

		OpinionLikeCount opinionLikeCount = (OpinionLikeCount) getSession()
				.get(OpinionLikeCount.class, id);

		logger.info("Deleted OpinionLikeCount. Exiting delete method");
		getSession().delete(opinionLikeCount);

	}

}
