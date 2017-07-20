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

import com.ohmuk.folitics.hibernate.entity.comment.OpinionCommentCount;
import com.ohmuk.folitics.hibernate.entity.comment.OpinionCommentCountId;

@Component
@Repository
public class OpinionCommentCountRepositoryImplementation implements
		IOpinionCommentCountRepository {

	private static Logger logger = LoggerFactory
			.getLogger(OpinionCommentCountRepositoryImplementation.class);
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public OpinionCommentCount save(OpinionCommentCount opinionCommentCount) {
		logger.info("Entered OpinionCommentCount save method");
		logger.debug("Saving comment count for opinion id = "
				+ opinionCommentCount.getId().getOpinion().getId());

		OpinionCommentCountId id = (OpinionCommentCountId) getSession().save(
				opinionCommentCount);
		opinionCommentCount = (OpinionCommentCount) getSession().get(
				OpinionCommentCount.class, id);

		logger.info("OpinionCommentCount saved. Exiting save method");
		return opinionCommentCount;
	}

	@Override
	public OpinionCommentCount update(OpinionCommentCount opinionCommentCount) {
		logger.info("Entered OpinionCommentCount update method");
		logger.debug("Updating comment count for opinion id = "
				+ opinionCommentCount.getId().getOpinion().getId());

		opinionCommentCount = (OpinionCommentCount) getSession().merge(
				opinionCommentCount);
		getSession().update(opinionCommentCount);

		opinionCommentCount = (OpinionCommentCount) getSession().get(
				OpinionCommentCount.class, opinionCommentCount.getId());

		logger.info("OpinionCommentCount updated. Exiting update method");
		return opinionCommentCount;
	}

	@Override
	public List<OpinionCommentCount> findAll() {
		logger.info("Entered OpinionCommentCount findAll method");

		Criteria selectAllCriteria = getSession().createCriteria(
				OpinionCommentCount.class);
		@SuppressWarnings("unchecked")
		List<OpinionCommentCount> opinionCommentCounts = selectAllCriteria
				.list();

		logger.info("Returning all results from database. Exiting findAll method");
		return opinionCommentCounts;
	}

	@Override
	public OpinionCommentCount findByComponentId(Long opinionId) {
		logger.info("Entered OpinionCommentCount findByComponentId method");
		logger.debug("Finding comment count for opinion id = " + opinionId);

		Criteria selectCriteria = getSession().createCriteria(
				OpinionCommentCount.class);
		selectCriteria.add(Restrictions.eq("id.opinion.id", opinionId));
		OpinionCommentCount opinionCommentCount = (OpinionCommentCount) selectCriteria
				.uniqueResult();

		logger.info("Returning OpinionCommentCount. Exiting findByComponentId method");
		return opinionCommentCount;
	}

	@Override
	public OpinionCommentCount find(OpinionCommentCountId id) {
		logger.info("Entered OpinionCommentCount find method");
		logger.debug("Getting comment count for opinion id = "
				+ id.getOpinion().getId());

		OpinionCommentCount opinionCommentCount = (OpinionCommentCount) getSession()
				.get(OpinionCommentCount.class, id);

		logger.info("Returning result from database. Exiting update method");
		return opinionCommentCount;
	}

	@Override
	public void delete(OpinionCommentCountId id) {
		logger.info("Entered OpinionCommentCount delete method");
		logger.debug("Deleting comment count for opinion id = "
				+ id.getOpinion().getId());

		OpinionCommentCount opinionCommentCount = (OpinionCommentCount) getSession()
				.get(OpinionCommentCount.class, id);

		logger.info("Deleted OpinionCommentCount. Exiting delete method");
		getSession().delete(opinionCommentCount);

	}

}
