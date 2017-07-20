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

import com.ohmuk.folitics.hibernate.entity.share.GovtSchemeDataShareCount;
import com.ohmuk.folitics.hibernate.entity.share.GovtSchemeDataShareCountId;

/**
 * Repository implementation for entity: {@link GovtSchemeDataShareCount}
 * 
 * @aouthor Harish
 *
 */

@Component
@Repository
public class GovtSchemeDataShareCountRepositoryImplementation implements IGovtSchemeDataShareCountRepository {

	private static Logger logger = LoggerFactory.getLogger(GovtSchemeDataShareCountRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public GovtSchemeDataShareCount save(GovtSchemeDataShareCount govtSchemeDataShareCount) {
		logger.debug("Entered GovtSchemeDataShareCount save method");
		logger.debug("Saving share count for govtSchemeData id = "
				+ govtSchemeDataShareCount.getId().getGovtSchemeData().getId());

		GovtSchemeDataShareCountId id = (GovtSchemeDataShareCountId) getSession().save(govtSchemeDataShareCount);
		govtSchemeDataShareCount = (GovtSchemeDataShareCount) getSession().get(GovtSchemeDataShareCount.class, id);

		logger.debug("GovtSchemeDataShareCount saved. Exiting save method");
		return govtSchemeDataShareCount;
	}

	@Override
	public GovtSchemeDataShareCount update(GovtSchemeDataShareCount govtSchemeDataShareCount) {
		logger.debug("Entered GovtSchemeDataShareCount update method");
		logger.debug("Updating share count for govtSchemeData id = "
				+ govtSchemeDataShareCount.getId().getGovtSchemeData().getId());

		govtSchemeDataShareCount = (GovtSchemeDataShareCount) getSession().merge(govtSchemeDataShareCount);
		getSession().update(govtSchemeDataShareCount);

		govtSchemeDataShareCount = (GovtSchemeDataShareCount) getSession().get(GovtSchemeDataShareCount.class,
				govtSchemeDataShareCount.getId());

		logger.debug("Updated GovtSchemeDataShareCount. Exiting update method");
		return govtSchemeDataShareCount;
	}

	@Override
	public List<GovtSchemeDataShareCount> findAll() {
		logger.debug("Entered GovtSchemeDataShareCount findAll method");
		logger.debug("Getting all share count");

		@SuppressWarnings("unchecked")
		List<GovtSchemeDataShareCount> govtSchemeDataShareCounts = getSession()
				.createCriteria(GovtSchemeDataShareCount.class).list();

		logger.debug("Returning all GovtSchemeDataShareCount. Exiting findAll method");
		return govtSchemeDataShareCounts;
	}

	@Override
	public GovtSchemeDataShareCount findByComponentId(Long govtSchemeDataShareCountId) {
		logger.debug("Entered GovtSchemeDataShareCount findByComponentId method");
		logger.debug("Getting share count for govtSchemeData id = " + govtSchemeDataShareCountId);

		Criteria selectCriteria = getSession().createCriteria(GovtSchemeDataShareCount.class);
		selectCriteria.add(Restrictions.eq("id.govtSchemeData.id", govtSchemeDataShareCountId));
		GovtSchemeDataShareCount govtSchemeDataShareCount = (GovtSchemeDataShareCount) selectCriteria.uniqueResult();

		logger.debug("Returning GovtSchemeDataShareCount. Exiting find method");
		return govtSchemeDataShareCount;
	}

	@Override
	public GovtSchemeDataShareCount find(GovtSchemeDataShareCountId govtSchemeDataShareCountId) {
		logger.debug("Entered GovtSchemeDataShareCount find method");
		logger.debug("Getting share count for govtSchemeData id = "
				+ govtSchemeDataShareCountId.getGovtSchemeData().getId());

		GovtSchemeDataShareCount govtSchemeDataShareCount = (GovtSchemeDataShareCount) getSession()
				.get(GovtSchemeDataShareCount.class, govtSchemeDataShareCountId);

		logger.debug("Returning GovtSchemeDataShareCount. Exiting find method");
		return govtSchemeDataShareCount;
	}

	@Override
	public void delete(GovtSchemeDataShareCountId govtSchemeDataShareCountId) {
		logger.debug("Entered GovtSchemeDataShareCount delete method");
		logger.debug("Deleting share count for govtSchemeData id = "
				+ govtSchemeDataShareCountId.getGovtSchemeData().getId());

		GovtSchemeDataShareCount govtSchemeDataShareCount = (GovtSchemeDataShareCount) getSession()
				.get(GovtSchemeDataShareCount.class, govtSchemeDataShareCountId);

		logger.debug("Deleted GovtSchemeDataShareCount. Exiting delete method");
		getSession().delete(govtSchemeDataShareCount);

	}

}
