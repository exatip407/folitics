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

import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdictAgeDistribution;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.AgeGroup;

/**
 * Repository implementation for {@link GlobalVerdictAgeDistribution}
 * 
 * @author Abhishek
 *
 */
@Component
@Repository
public class GlobalVerdictAgeDistributionRepository
		implements
		IGlobalVerdictDistributionRepository<GlobalVerdictAgeDistribution, AgeGroup> {

	private static Logger logger = LoggerFactory
			.getLogger(GlobalVerdictAgeDistributionRepository.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public GlobalVerdictAgeDistribution save(
			GlobalVerdictAgeDistribution globalVerdictAgeDistribution) {

		logger.debug("Entered GlobalVerdictAgeDistributionRepository save method");
		logger.debug("Trying to save GlobalVerdictAgeDistribution for AgeGroup id = "
				+ globalVerdictAgeDistribution.getAgeGroup().getId());

		globalVerdictAgeDistribution = (GlobalVerdictAgeDistribution) getSession()
				.save(globalVerdictAgeDistribution);

		logger.debug("Saved GlobalVerdictAgeDistribution object and got id = "
				+ globalVerdictAgeDistribution.getAgeGroup().getId()
				+ " and now getting GlobalVerdictAgeDistribution object from database");
		globalVerdictAgeDistribution = (GlobalVerdictAgeDistribution) getSession()
				.get(GlobalVerdictAgeDistribution.class,
						globalVerdictAgeDistribution.getAgeGroup().getId());

		logger.debug("Got GlobalVerdictAgeDistribution object from database. Exiting GlobalVerdictAgeDistributionRepository save method");
		return globalVerdictAgeDistribution;
	}

	@Override
	public GlobalVerdictAgeDistribution find(AgeGroup ageGroup) {

		logger.debug("Entered GlobalVerdictAgeDistributionRepository find method");
		logger.debug("Trying to get GlobalVerdictAgeDistribution with id = "
				+ ageGroup.getId());

		GlobalVerdictAgeDistribution globalVerdictAgeDistribution = (GlobalVerdictAgeDistribution) getSession()
				.get(GlobalVerdictAgeDistribution.class, ageGroup.getId());

		logger.debug("Got GlobalVerdictAgeDistribution object from database. Exiting GlobalVerdictAgeDistributionRepository find method");

		return globalVerdictAgeDistribution;
	}

	@Override
	public List<GlobalVerdictAgeDistribution> findAll() {

		logger.debug("Entered GlobalVerdictAgeDistributionRepository findAll method");
		logger.debug("Trying to get all GlobalVerdictAgeDistribution");

		Criteria selectAllCriteria = getSession().createCriteria(
				GlobalVerdictAgeDistribution.class);
		@SuppressWarnings("unchecked")
		List<GlobalVerdictAgeDistribution> globalVerdictAgeDistributions = selectAllCriteria
				.list();

		logger.debug("Got all GlobalVerdictAgeDistribution objects from database. Exiting GlobalVerdictAgeDistributionRepository findAll method");

		return globalVerdictAgeDistributions;
	}

	@Override
	public GlobalVerdictAgeDistribution update(
			GlobalVerdictAgeDistribution globalVerdictAgeDistribution) {

		logger.debug("Entered GlobalVerdictAgeDistributionRepository update method");
		logger.debug("Merging the object first with id = "
				+ globalVerdictAgeDistribution.getAgeGroup().getId());

		globalVerdictAgeDistribution = (GlobalVerdictAgeDistribution) getSession()
				.merge(globalVerdictAgeDistribution);

		logger.debug("Now updating the GlobalVerdictAgeDistribution object in database with id = "
				+ globalVerdictAgeDistribution.getAgeGroup().getId());
		getSession().update(globalVerdictAgeDistribution);

		logger.debug("Getting the GlobalVerdictAgeDistribution object from database");

		globalVerdictAgeDistribution = (GlobalVerdictAgeDistribution) getSession()
				.get(GlobalVerdictAgeDistribution.class,
						globalVerdictAgeDistribution.getAgeGroup().getId());

		logger.debug("Got GlobalVerdictAgeDistribution object from database. Exiting GlobalVerdictAgeDistributionRepository update method");

		return globalVerdictAgeDistribution;
	}

	@Override
	public boolean deleteById(AgeGroup ageGroup) {

		logger.debug("Entered GlobalVerdictAgeDistributionRepository deleteById method");
		logger.debug("Trying to get GlobalVerdictAgeDistribution with id = "
				+ ageGroup.getId());

		GlobalVerdictAgeDistribution globalVerdictAgeDistribution = (GlobalVerdictAgeDistribution) getSession()
				.get(GlobalVerdictAgeDistribution.class, ageGroup.getId());

		logger.debug("Now trying to delete the GlobalVerdictAgeDistribution object with id = "
				+ globalVerdictAgeDistribution.getAgeGroup().getId());

		getSession().delete(globalVerdictAgeDistribution);

		globalVerdictAgeDistribution = (GlobalVerdictAgeDistribution) getSession()
				.get(GlobalVerdictAgeDistribution.class, ageGroup.getId());

		if (globalVerdictAgeDistribution == null) {

			logger.debug("Deleted GlobalVerdictAgeDistribution object from database. Exiting GlobalVerdictAgeDistributionRepository deleteById method");
			return true;
		} else {

			return false;
		}
	}

	@Override
	public boolean delete(
			GlobalVerdictAgeDistribution globalVerdictAgeDistribution) {

		logger.debug("Entered GlobalVerdictAgeDistributionRepository delete method");
		logger.debug("Trying to get GlobalVerdictAgeDistribution with id = "
				+ globalVerdictAgeDistribution.getAgeGroup().getId());

		globalVerdictAgeDistribution = (GlobalVerdictAgeDistribution) getSession()
				.get(GlobalVerdictAgeDistribution.class,
						globalVerdictAgeDistribution.getAgeGroup().getId());

		logger.debug("Now trying to delete the GlobalVerdictAgeDistribution object with id = "
				+ globalVerdictAgeDistribution.getAgeGroup().getId());

		getSession().delete(globalVerdictAgeDistribution);

		globalVerdictAgeDistribution = (GlobalVerdictAgeDistribution) getSession()
				.get(GlobalVerdictAgeDistribution.class,
						globalVerdictAgeDistribution.getAgeGroup().getId());

		if (globalVerdictAgeDistribution == null) {

			logger.debug("Deleted GlobalVerdictAgeDistribution object from database. Exiting GlobalVerdictAgeDistributionRepository deleteById method");
			return true;
		} else {

			return false;
		}
	}

}
