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

import com.ohmuk.folitics.hibernate.entity.air.GovtSchemeDataCountId;
import com.ohmuk.folitics.hibernate.entity.like.GovtSchemeDataLikeCount;

/**
 * Repository implementation for entity: {@link GovtSchemeDataLikeCount}
 * 
 * @author Harish
 *
 */

@Component
@Repository
public class GovtSchemeDataLikeCountRepositoryImplementation implements
		IGovtSchemeDataCountRepository {

	private static Logger logger = LoggerFactory
			.getLogger(GovtSchemeDataLikeCountRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public GovtSchemeDataLikeCount save(
			GovtSchemeDataLikeCount govtSchemeDataLikeCount) {
		logger.info("Entered GovtSchemeDataLikeCount save method");
		logger.debug("Saving like count for govtSchemeData id = "
				+ govtSchemeDataLikeCount.getId().getGovtSchemeData().getId());

		GovtSchemeDataCountId id = (GovtSchemeDataCountId) getSession().save(
				govtSchemeDataLikeCount);
		govtSchemeDataLikeCount = (GovtSchemeDataLikeCount) getSession().get(
				GovtSchemeDataLikeCount.class, id);

		logger.info("GovtSchemeDataLikeCount saved. Exiting save method");
		return govtSchemeDataLikeCount;
	}

	@Override
	public GovtSchemeDataLikeCount update(
			GovtSchemeDataLikeCount govtSchemeDataLikeCount) {

		logger.info("Entered GovtSchemeDataLikeCount update method");
		logger.debug("Updating like count for govtSchemeData id = "
				+ govtSchemeDataLikeCount.getId().getGovtSchemeData().getId());

		govtSchemeDataLikeCount = (GovtSchemeDataLikeCount) getSession().merge(
				govtSchemeDataLikeCount);
		getSession().update(govtSchemeDataLikeCount);

		govtSchemeDataLikeCount = (GovtSchemeDataLikeCount) getSession().get(
				GovtSchemeDataLikeCount.class, govtSchemeDataLikeCount.getId());

		logger.info("GovtSchemeDataLikeCount updated. Exiting update method");
		return govtSchemeDataLikeCount;
	}

	@Override
	public List<GovtSchemeDataLikeCount> findAll() {
		logger.info("Entered GovtSchemeDataLikeCount findAll method");

		Criteria selectAllCriteria = getSession().createCriteria(
				GovtSchemeDataLikeCount.class);
		@SuppressWarnings("unchecked")
		List<GovtSchemeDataLikeCount> govtSchemeDataLikeCounts = selectAllCriteria
				.list();

		logger.info("Returning all results from database. Exiting findAll method");
		return govtSchemeDataLikeCounts;
	}

	@Override
	public GovtSchemeDataLikeCount findByComponentId(Long schemeId) {
		logger.info("Entered GovtSchemeDataLikeCount findByComponentId method");
		logger.debug("Finding like count for govtSchemeData id = " + schemeId);

		Criteria selectCriteria = getSession().createCriteria(
				GovtSchemeDataLikeCount.class);
		selectCriteria.add(Restrictions.eq("id.govtSchemeData.id", schemeId));
		GovtSchemeDataLikeCount govtSchemeDataLikeCount = (GovtSchemeDataLikeCount) selectCriteria
				.uniqueResult();

		logger.info("Returning GovtSchemeDataLikeCount. Exiting findByComponentId method");
		return govtSchemeDataLikeCount;
	}

	@Override
	public GovtSchemeDataLikeCount find(GovtSchemeDataCountId id) {
		logger.info("Entered GovtSchemeDataLikeCount find method");
		logger.debug("Getting like count for govtSchemeData id = "
				+ id.getGovtSchemeData().getId());

		GovtSchemeDataLikeCount govtSchemeDataLikeCount = (GovtSchemeDataLikeCount) getSession()
				.get(GovtSchemeDataLikeCount.class, id);

		logger.info("Returning result from database. Exiting update method");
		return govtSchemeDataLikeCount;
	}

	@Override
	public void delete(GovtSchemeDataCountId id) {
		logger.info("Entered GovtSchemeDataLikeCount delete method");
		logger.debug("Deleting like count for govtSchemeData id = "
				+ id.getGovtSchemeData().getId());

		GovtSchemeDataLikeCount govtSchemeDataLikeCount = (GovtSchemeDataLikeCount) getSession()
				.get(GovtSchemeDataLikeCount.class, id);

		logger.info("Deleted GovtSchemeDataLikeCount. Exiting delete method");
		getSession().delete(govtSchemeDataLikeCount);

	}

}
