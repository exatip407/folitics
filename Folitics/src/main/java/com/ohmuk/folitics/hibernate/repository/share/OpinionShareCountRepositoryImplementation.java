package com.ohmuk.folitics.hibernate.repository.share;

import java.io.Serializable;
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

import com.ohmuk.folitics.hibernate.entity.share.OpinionShareCount;
import com.ohmuk.folitics.hibernate.entity.share.OpinionShareCount;
import com.ohmuk.folitics.hibernate.entity.share.OpinionShareCountId;
import com.ohmuk.folitics.hibernate.entity.share.SentimentShareCount;
import com.ohmuk.folitics.hibernate.entity.share.SentimentShareCountId;

/**
 * Repository implementation for entity: {@link OpinionShareCount}
 * 
 * @author Harish
 *
 */

@Component
@Repository
public class OpinionShareCountRepositoryImplementation implements IOpinionShareCountRepository{

	private static Logger logger = LoggerFactory
			.getLogger(OpinionShareCountRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public OpinionShareCount save(OpinionShareCount opinionShareCount) {
		
		logger.debug("Entered OpinionShareCount save method");
		logger.debug("Saving share count for opinion id = "
				+ opinionShareCount.getId().getOpinion().getId());

		OpinionShareCountId id = (OpinionShareCountId) getSession().save(
				opinionShareCount);
		opinionShareCount = (OpinionShareCount) getSession().get(
				OpinionShareCount.class, id);

		logger.debug("OpinionShareCount saved. Exiting save method");
		return opinionShareCount;
	}

	@Override
	public OpinionShareCount update(OpinionShareCount opinionShareCount) {
		
		logger.debug("Entered OpinionShareCount update method");
		logger.debug("Updating share count for opinion id = "
				+ opinionShareCount.getId().getOpinion().getId());

		opinionShareCount = (OpinionShareCount) getSession().merge(
				opinionShareCount);
		getSession().update(opinionShareCount);

		opinionShareCount = (OpinionShareCount) getSession().get(
				OpinionShareCount.class, opinionShareCount.getId());

		logger.debug("Updated OpinionShareCount. Exiting update method");
		return opinionShareCount;
	}

	@Override
	public List<OpinionShareCount> findAll() {
		
		logger.debug("Entered OpinionShareCount findAll method");
		logger.debug("Getting all share count");

		@SuppressWarnings("unchecked")
		List<OpinionShareCount> opinionShareCounts = getSession()
				.createCriteria(OpinionShareCount.class).list();

		logger.debug("Returning all OpinionShareCount. Exiting findAll method");
		return opinionShareCounts;
	}

	@Override
	public OpinionShareCount findByComponentId(Long opinionId) {
		
		logger.debug("Entered OpinionShareCount findByComponentId method");
		logger.debug("Getting share count for opinion id = " + opinionId);

		Criteria selectCriteria = getSession().createCriteria(
				OpinionShareCount.class);
		selectCriteria.add(Restrictions.eq("id.opinion.id", opinionId));
		OpinionShareCount opinionShareCount = (OpinionShareCount) selectCriteria
				.uniqueResult();
		
		logger.debug("Returning OpinionShareCount. Exiting find method");
		return opinionShareCount;
	}

	@Override
	public OpinionShareCount find(OpinionShareCountId id) {
		
		logger.debug("Entered OpinionShareCount find method");
		logger.debug("Getting share count for opinion id = "
				+ id.getOpinion().getId());

		OpinionShareCount opinionShareCount = (OpinionShareCount) getSession()
				.get(OpinionShareCount.class, id);

		logger.debug("Returning OpinionShareCount. Exiting find method");
		return opinionShareCount;
	}

	@Override
	public void delete(OpinionShareCountId id) {
		
		logger.debug("Entered OpinionShareCount delete method");
		logger.debug("Deleting share count for opinion id = "
				+ id.getOpinion().getId());

		OpinionShareCount opinionShareCount = (OpinionShareCount) getSession()
				.get(OpinionShareCount.class, id);

		logger.debug("Deleted OpinionShareCount. Exiting delete method");
		getSession().delete(opinionShareCount);
		
	}	
}
