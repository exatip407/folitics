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

import com.ohmuk.folitics.hibernate.entity.like.FactLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.FactLikeCountId;

/**
 * Repository implementation for entity: {@link FactLikeCount}
 * 
 * @author Harish
 *
 */

@Component
@Repository
public class FactLikeCountRepositoryImplementation implements
		IFactLikeCountRepository {

	private static Logger logger = LoggerFactory
			.getLogger(FactLikeCountRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public FactLikeCount save(FactLikeCount factLikeCount) {
		logger.info("Entered FactLikeCount save method");
		logger.debug("Saving like count for fact id = "
				+ factLikeCount.getId().getFact().getId());

		FactLikeCountId id = (FactLikeCountId) getSession().save(factLikeCount);
		factLikeCount = (FactLikeCount) getSession().get(FactLikeCount.class,
				id);

		logger.info("FactLikeCount saved. Exiting save method");
		return factLikeCount;
	}

	@Override
	public FactLikeCount update(FactLikeCount factLikeCount) {
		logger.info("Entered FactLikeCount update method");
		logger.debug("Updating like count for fact id = "
				+ factLikeCount.getId().getFact().getId());

		factLikeCount = (FactLikeCount) getSession().merge(factLikeCount);
		getSession().update(factLikeCount);

		factLikeCount = (FactLikeCount) getSession().get(FactLikeCount.class,
				factLikeCount.getId());

		logger.info("FactLikeCount updated. Exiting update method");
		return factLikeCount;
	}

	@Override
	public List<FactLikeCount> findAll() {
		logger.info("Entered FactLikeCount findAll method");

		Criteria selectAllCriteria = getSession().createCriteria(
				FactLikeCount.class);
		@SuppressWarnings("unchecked")
		List<FactLikeCount> factLikeCounts = selectAllCriteria.list();

		logger.info("Returning all results from database. Exiting findAll method");
		return factLikeCounts;
	}

	@Override
	public FactLikeCount findByComponentId(Long factId) {
		logger.info("Entered FactLikeCount findByComponentId method");
		logger.debug("Finding like count for Chart id = " + factId);

		Criteria selectCriteria = getSession().createCriteria(
				FactLikeCount.class);
		selectCriteria.add(Restrictions.eq("id.chart.id", factId));
		FactLikeCount factLikeCount = (FactLikeCount) selectCriteria
				.uniqueResult();

		logger.info("Returning FactLikeCount. Exiting findByComponentId method");
		return factLikeCount;
	}

	@Override
	public FactLikeCount find(FactLikeCountId id) {
		logger.info("Entered FactLikeCount find method");
		logger.debug("Getting like count for fact id = " + id.getFact().getId());

		FactLikeCount factLikeCount = (FactLikeCount) getSession().get(
				FactLikeCount.class, id);

		logger.info("Returning result from database. Exiting update method");
		return factLikeCount;
	}

	@Override
	public void delete(FactLikeCountId id) {
		logger.info("Entered FactLikeCount delete method");
		logger.debug("Deleting like count for fact id = "
				+ id.getFact().getId());

		FactLikeCount factLikeCount = (FactLikeCount) getSession().get(
				FactLikeCount.class, id);

		logger.info("Deleted FactLikeCount. Exiting delete method");
		getSession().delete(factLikeCount);

	}

}
