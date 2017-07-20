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

import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdictReligionDistribution;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Religion;

/**
 * Repository implementation for {@link GlobalVerdictReligionDistribution}
 * 
 * @author Abhishek
 *
 */
@Component
@Repository
public class GlobalVerdictReligionDistributionRepository
		implements
		IGlobalVerdictDistributionRepository<GlobalVerdictReligionDistribution, Religion> {

	private static Logger logger = LoggerFactory
			.getLogger(GlobalVerdictReligionDistributionRepository.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public GlobalVerdictReligionDistribution save(
			GlobalVerdictReligionDistribution globalVerdictReligionDistribution) {

		logger.debug("Entered GlobalVerdictReligionDistributionRepository save method");
		logger.debug("Trying to save GlobalVerdictReligionDistribution for Religion id = "
				+ globalVerdictReligionDistribution.getReligion().getId());

		globalVerdictReligionDistribution = (GlobalVerdictReligionDistribution) getSession()
				.save(globalVerdictReligionDistribution);

		logger.debug("Saved GlobalVerdictReligionDistribution object and got id = "
				+ globalVerdictReligionDistribution.getReligion().getId()
				+ " and now getting GlobalVerdictReligionDistribution object from database");
		globalVerdictReligionDistribution = (GlobalVerdictReligionDistribution) getSession()
				.get(GlobalVerdictReligionDistribution.class,
						globalVerdictReligionDistribution.getReligion().getId());

		logger.debug("Got GlobalVerdictReligionDistribution object from database. Exiting GlobalVerdictReligionDistributionRepository save method");
		return globalVerdictReligionDistribution;
	}

	@Override
	public GlobalVerdictReligionDistribution find(Religion religion) {

		logger.debug("Entered GlobalVerdictReligionDistributionRepository find method");
		logger.debug("Trying to get GlobalVerdictReligionDistribution with id = "
				+ religion.getId());

		GlobalVerdictReligionDistribution globalVerdictReligionDistribution = (GlobalVerdictReligionDistribution) getSession()
				.get(GlobalVerdictReligionDistribution.class, religion.getId());

		logger.debug("Got GlobalVerdictReligionDistribution object from database. Exiting GlobalVerdictReligionDistributionRepository find method");

		return globalVerdictReligionDistribution;
	}

	@Override
	public List<GlobalVerdictReligionDistribution> findAll() {

		logger.debug("Entered GlobalVerdictReligionDistributionRepository findAll method");
		logger.debug("Trying to get all GlobalVerdictReligionDistribution");

		Criteria selectAllCriteria = getSession().createCriteria(
				GlobalVerdictReligionDistribution.class);
		@SuppressWarnings("unchecked")
		List<GlobalVerdictReligionDistribution> globalVerdictReligionDistributions = selectAllCriteria
				.list();

		logger.debug("Got all GlobalVerdictReligionDistribution objects from database. Exiting GlobalVerdictReligionDistributionRepository findAll method");

		return globalVerdictReligionDistributions;
	}

	@Override
	public GlobalVerdictReligionDistribution update(
			GlobalVerdictReligionDistribution globalVerdictReligionDistribution) {

		logger.debug("Entered GlobalVerdictReligionDistributionRepository update method");
		logger.debug("Merging the object first with id = "
				+ globalVerdictReligionDistribution.getReligion().getId());

		globalVerdictReligionDistribution = (GlobalVerdictReligionDistribution) getSession()
				.merge(globalVerdictReligionDistribution);

		logger.debug("Now updating the GlobalVerdictReligionDistribution object in database with id = "
				+ globalVerdictReligionDistribution.getReligion().getId());
		getSession().update(globalVerdictReligionDistribution);

		logger.debug("Getting the GlobalVerdictReligionDistribution object from database");

		globalVerdictReligionDistribution = (GlobalVerdictReligionDistribution) getSession()
				.get(GlobalVerdictReligionDistribution.class,
						globalVerdictReligionDistribution.getReligion().getId());

		logger.debug("Got GlobalVerdictReligionDistribution object from database. Exiting GlobalVerdictReligionDistributionRepository update method");

		return globalVerdictReligionDistribution;
	}

	@Override
	public boolean deleteById(Religion religion) {

		logger.debug("Entered GlobalVerdictReligionDistributionRepository deleteById method");
		logger.debug("Trying to get GlobalVerdictReligionDistribution with id = "
				+ religion.getId());

		GlobalVerdictReligionDistribution globalVerdictReligionDistribution = (GlobalVerdictReligionDistribution) getSession()
				.get(GlobalVerdictReligionDistribution.class, religion);

		logger.debug("Now trying to delete the GlobalVerdictReligionDistribution object with id = "
				+ globalVerdictReligionDistribution.getReligion().getId());

		getSession().delete(globalVerdictReligionDistribution);

		globalVerdictReligionDistribution = (GlobalVerdictReligionDistribution) getSession()
				.get(GlobalVerdictReligionDistribution.class, religion.getId());

		if (globalVerdictReligionDistribution == null) {

			logger.debug("Deleted GlobalVerdictReligionDistribution object from database. Exiting GlobalVerdictReligionDistributionRepository deleteById method");
			return true;
		} else {

			return false;
		}
	}

	@Override
	public boolean delete(
			GlobalVerdictReligionDistribution globalVerdictReligionDistribution) {

		logger.debug("Entered GlobalVerdictReligionDistributionRepository delete method");
		logger.debug("Trying to get GlobalVerdictReligionDistribution with id = "
				+ globalVerdictReligionDistribution.getReligion().getId());

		globalVerdictReligionDistribution = (GlobalVerdictReligionDistribution) getSession()
				.get(GlobalVerdictReligionDistribution.class,
						globalVerdictReligionDistribution.getReligion().getId());

		logger.debug("Now trying to delete the GlobalVerdictReligionDistribution object with id = "
				+ globalVerdictReligionDistribution.getReligion().getId());

		getSession().delete(globalVerdictReligionDistribution);

		globalVerdictReligionDistribution = (GlobalVerdictReligionDistribution) getSession()
				.get(GlobalVerdictReligionDistribution.class,
						globalVerdictReligionDistribution.getReligion().getId());

		if (globalVerdictReligionDistribution == null) {

			logger.debug("Deleted GlobalVerdictReligionDistribution object from database. Exiting GlobalVerdictReligionDistributionRepository deleteById method");
			return true;
		} else {

			return false;
		}
	}

}
