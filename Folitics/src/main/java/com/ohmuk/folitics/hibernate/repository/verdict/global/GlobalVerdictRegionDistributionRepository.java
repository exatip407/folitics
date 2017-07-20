package com.ohmuk.folitics.hibernate.repository.verdict.global;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdictRegionDistribution;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Region;

/**
 * Repository implementation for {@link GlobalVerdictRegionDistribution}
 * 
 * @author Abhishek
 *
 */
@Component
@Repository
public class GlobalVerdictRegionDistributionRepository
		implements
		IGlobalVerdictDistributionRepository<GlobalVerdictRegionDistribution, Region> {

	private static Logger logger = LoggerFactory
			.getLogger(GlobalVerdictRegionDistributionRepository.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public GlobalVerdictRegionDistribution save(
			GlobalVerdictRegionDistribution globalVerdictRegionDistribution) {

		logger.debug("Entered GlobalVerdictRegionDistributionRepository save method");
		logger.debug("Trying to save GlobalVerdictRegionDistribution for Region id = "
				+ globalVerdictRegionDistribution.getRegion().getId());

		globalVerdictRegionDistribution = (GlobalVerdictRegionDistribution) getSession()
				.save(globalVerdictRegionDistribution);

		logger.debug("Saved GlobalVerdictRegionDistribution object and got id = "
				+ globalVerdictRegionDistribution.getRegion().getId()
				+ " and now getting GlobalVerdictRegionDistribution object from database");
		globalVerdictRegionDistribution = (GlobalVerdictRegionDistribution) getSession()
				.get(GlobalVerdictRegionDistribution.class,
						globalVerdictRegionDistribution.getRegion().getId());

		logger.debug("Got GlobalVerdictRegionDistribution object from database. Exiting GlobalVerdictRegionDistributionRepository save method");
		return globalVerdictRegionDistribution;
	}

	@Override
	public GlobalVerdictRegionDistribution find(Region region) {

		logger.debug("Entered GlobalVerdictRegionDistributionRepository find method");
		logger.debug("Trying to get GlobalVerdictRegionDistribution with id = "
				+ region.getId());

		GlobalVerdictRegionDistribution globalVerdictRegionDistribution = (GlobalVerdictRegionDistribution) getSession()
				.get(GlobalVerdictRegionDistribution.class, region.getId());

		logger.debug("Got GlobalVerdictRegionDistribution object from database. Exiting GlobalVerdictRegionDistributionRepository find method");

		return globalVerdictRegionDistribution;
	}

	@Override
	public List<GlobalVerdictRegionDistribution> findAll() {

		logger.debug("Entered GlobalVerdictRegionDistributionRepository findAll method");
		logger.debug("Trying to get all GlobalVerdictRegionDistribution");

		Criteria selectAllCriteria = getSession().createCriteria(
				GlobalVerdictRegionDistribution.class);
		@SuppressWarnings("unchecked")
		List<GlobalVerdictRegionDistribution> globalVerdictRegionDistributions = selectAllCriteria
				.list();

		logger.debug("Got all GlobalVerdictRegionDistribution objects from database. Exiting GlobalVerdictRegionDistributionRepository findAll method");

		return globalVerdictRegionDistributions;
	}

	@Override
	public GlobalVerdictRegionDistribution update(
			GlobalVerdictRegionDistribution globalVerdictRegionDistribution) {

		logger.debug("Entered GlobalVerdictRegionDistributionRepository update method");
		logger.debug("Merging the object first with id = "
				+ globalVerdictRegionDistribution.getRegion().getId());

		globalVerdictRegionDistribution = (GlobalVerdictRegionDistribution) getSession()
				.merge(globalVerdictRegionDistribution);

		logger.debug("Now updating the GlobalVerdictRegionDistribution object in database with id = "
				+ globalVerdictRegionDistribution.getRegion().getId());
		getSession().update(globalVerdictRegionDistribution);

		logger.debug("Getting the GlobalVerdictRegionDistribution object from database");

		globalVerdictRegionDistribution = (GlobalVerdictRegionDistribution) getSession()
				.get(GlobalVerdictRegionDistribution.class,
						globalVerdictRegionDistribution.getRegion().getId());

		logger.debug("Got GlobalVerdictRegionDistribution object from database. Exiting GlobalVerdictRegionDistributionRepository update method");

		return globalVerdictRegionDistribution;
	}

	@Override
	public boolean deleteById(Region region) {

		logger.debug("Entered GlobalVerdictRegionDistributionRepository deleteById method");
		logger.debug("Trying to get GlobalVerdictRegionDistribution with id = "
				+ region.getId());

		GlobalVerdictRegionDistribution globalVerdictRegionDistribution = (GlobalVerdictRegionDistribution) getSession()
				.get(GlobalVerdictRegionDistribution.class, region.getId());

		logger.debug("Now trying to delete the GlobalVerdictRegionDistribution object with id = "
				+ globalVerdictRegionDistribution.getRegion().getId());

		getSession().delete(globalVerdictRegionDistribution);

		globalVerdictRegionDistribution = (GlobalVerdictRegionDistribution) getSession()
				.get(GlobalVerdictRegionDistribution.class, region.getId());

		if (globalVerdictRegionDistribution == null) {

			logger.debug("Deleted GlobalVerdictRegionDistribution object from database. Exiting GlobalVerdictRegionDistributionRepository deleteById method");
			return true;
		} else {

			return false;
		}
	}

	@Override
	public boolean delete(
			GlobalVerdictRegionDistribution globalVerdictRegionDistribution) {

		logger.debug("Entered GlobalVerdictRegionDistributionRepository delete method");
		logger.debug("Trying to get GlobalVerdictRegionDistribution with id = "
				+ globalVerdictRegionDistribution.getRegion().getId());

		globalVerdictRegionDistribution = (GlobalVerdictRegionDistribution) getSession()
				.get(GlobalVerdictRegionDistribution.class,
						globalVerdictRegionDistribution.getRegion().getId());

		logger.debug("Now trying to delete the GlobalVerdictRegionDistribution object with id = "
				+ globalVerdictRegionDistribution.getRegion().getId());

		getSession().delete(globalVerdictRegionDistribution);

		globalVerdictRegionDistribution = (GlobalVerdictRegionDistribution) getSession()
				.get(GlobalVerdictRegionDistribution.class,
						globalVerdictRegionDistribution.getRegion().getId());

		if (globalVerdictRegionDistribution == null) {

			logger.debug("Deleted GlobalVerdictRegionDistribution object from database. Exiting GlobalVerdictRegionDistributionRepository deleteById method");
			return true;
		} else {

			return false;
		}
	}

}
