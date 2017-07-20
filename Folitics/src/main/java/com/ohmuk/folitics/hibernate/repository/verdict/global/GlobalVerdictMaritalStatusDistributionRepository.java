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

import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdictMaritalStatusDistribution;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.MaritalStatus;

/**
 * Repository implementation for {@link GlobalVerdictMaritalStatusDistribution}
 * 
 * @author Abhishek
 *
 */
@Component
@Repository
public class GlobalVerdictMaritalStatusDistributionRepository
		implements
		IGlobalVerdictDistributionRepository<GlobalVerdictMaritalStatusDistribution, MaritalStatus> {

	private static Logger logger = LoggerFactory
			.getLogger(GlobalVerdictMaritalStatusDistributionRepository.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public GlobalVerdictMaritalStatusDistribution save(
			GlobalVerdictMaritalStatusDistribution globalVerdictMaritalStatusDistribution) {

		logger.debug("Entered GlobalVerdictMaritalStatusDistributionRepository save method");
		logger.debug("Trying to save GlobalVerdictMaritalStatusDistribution for MaritalStatus id = "
				+ globalVerdictMaritalStatusDistribution.getMaritalStatus()
						.getId());

		globalVerdictMaritalStatusDistribution = (GlobalVerdictMaritalStatusDistribution) getSession()
				.save(globalVerdictMaritalStatusDistribution);

		logger.debug("Saved GlobalVerdictMaritalStatusDistribution object and got id = "
				+ globalVerdictMaritalStatusDistribution.getMaritalStatus()
						.getId()
				+ " and now getting GlobalVerdictMaritalStatusDistribution object from database");
		globalVerdictMaritalStatusDistribution = (GlobalVerdictMaritalStatusDistribution) getSession()
				.get(GlobalVerdictMaritalStatusDistribution.class,
						globalVerdictMaritalStatusDistribution
								.getMaritalStatus().getId());

		logger.debug("Got GlobalVerdictMaritalStatusDistribution object from database. Exiting GlobalVerdictMaritalStatusDistributionRepository save method");
		return globalVerdictMaritalStatusDistribution;
	}

	@Override
	public GlobalVerdictMaritalStatusDistribution find(
			MaritalStatus maritalStatus) {

		logger.debug("Entered GlobalVerdictMaritalStatusDistributionRepository find method");
		logger.debug("Trying to get GlobalVerdictMaritalStatusDistribution with id = "
				+ maritalStatus.getId());

		GlobalVerdictMaritalStatusDistribution globalVerdictMaritalStatusDistribution = (GlobalVerdictMaritalStatusDistribution) getSession()
				.get(GlobalVerdictMaritalStatusDistribution.class,
						maritalStatus.getId());

		logger.debug("Got GlobalVerdictMaritalStatusDistribution object from database. Exiting GlobalVerdictMaritalStatusDistributionRepository find method");

		return globalVerdictMaritalStatusDistribution;
	}

	@Override
	public List<GlobalVerdictMaritalStatusDistribution> findAll() {

		logger.debug("Entered GlobalVerdictMaritalStatusDistributionRepository findAll method");
		logger.debug("Trying to get all GlobalVerdictMaritalStatusDistribution");

		Criteria selectAllCriteria = getSession().createCriteria(
				GlobalVerdictMaritalStatusDistribution.class);
		@SuppressWarnings("unchecked")
		List<GlobalVerdictMaritalStatusDistribution> globalVerdictMaritalStatusDistributions = selectAllCriteria
				.list();

		logger.debug("Got all GlobalVerdictMaritalStatusDistribution objects from database. Exiting GlobalVerdictMaritalStatusDistributionRepository findAll method");

		return globalVerdictMaritalStatusDistributions;
	}

	@Override
	public GlobalVerdictMaritalStatusDistribution update(
			GlobalVerdictMaritalStatusDistribution globalVerdictMaritalStatusDistribution) {

		logger.debug("Entered GlobalVerdictAgeDistributionRepository update method");
		logger.debug("Merging the object first with id = "
				+ globalVerdictMaritalStatusDistribution.getMaritalStatus()
						.getId());

		globalVerdictMaritalStatusDistribution = (GlobalVerdictMaritalStatusDistribution) getSession()
				.merge(globalVerdictMaritalStatusDistribution);

		logger.debug("Now updating the GlobalVerdictMaritalStatusDistribution object in database with id = "
				+ globalVerdictMaritalStatusDistribution.getMaritalStatus()
						.getId());
		getSession().update(globalVerdictMaritalStatusDistribution);

		logger.debug("Getting the GlobalVerdictMaritalStatusDistribution object from database");

		globalVerdictMaritalStatusDistribution = (GlobalVerdictMaritalStatusDistribution) getSession()
				.get(GlobalVerdictMaritalStatusDistribution.class,
						globalVerdictMaritalStatusDistribution
								.getMaritalStatus().getId());

		logger.debug("Got GlobalVerdictMaritalStatusDistribution object from database. Exiting GlobalVerdictMaritalStatusDistribution update method");

		return globalVerdictMaritalStatusDistribution;
	}

	@Override
	public boolean deleteById(MaritalStatus maritalStatus) {

		logger.debug("Entered GlobalVerdictMaritalStatusDistribution deleteById method");
		logger.debug("Trying to get GlobalVerdictMaritalStatusDistribution with id = "
				+ maritalStatus.getId());

		GlobalVerdictMaritalStatusDistribution globalVerdictMaritalStatusDistribution = (GlobalVerdictMaritalStatusDistribution) getSession()
				.get(GlobalVerdictMaritalStatusDistribution.class,
						maritalStatus.getId());

		logger.debug("Now trying to delete the GlobalVerdictMaritalStatusDistribution object with id = "
				+ globalVerdictMaritalStatusDistribution.getMaritalStatus()
						.getId());

		getSession().delete(globalVerdictMaritalStatusDistribution);

		globalVerdictMaritalStatusDistribution = (GlobalVerdictMaritalStatusDistribution) getSession()
				.get(GlobalVerdictMaritalStatusDistribution.class,
						maritalStatus.getId());

		if (globalVerdictMaritalStatusDistribution == null) {

			logger.debug("Deleted GlobalVerdictMaritalStatusDistribution object from database. Exiting GlobalVerdictMaritalStatusDistribution deleteById method");
			return true;
		} else {

			return false;
		}
	}

	@Override
	public boolean delete(
			GlobalVerdictMaritalStatusDistribution globalVerdictMaritalStatusDistribution) {

		logger.debug("Entered GlobalVerdictMaritalStatusDistribution delete method");
		logger.debug("Trying to get GlobalVerdictMaritalStatusDistribution with id = "
				+ globalVerdictMaritalStatusDistribution.getMaritalStatus()
						.getId());

		globalVerdictMaritalStatusDistribution = (GlobalVerdictMaritalStatusDistribution) getSession()
				.get(GlobalVerdictMaritalStatusDistribution.class,
						globalVerdictMaritalStatusDistribution
								.getMaritalStatus().getId());

		logger.debug("Now trying to delete the GlobalVerdictMaritalStatusDistribution object with id = "
				+ globalVerdictMaritalStatusDistribution.getMaritalStatus()
						.getId());

		getSession().delete(globalVerdictMaritalStatusDistribution);

		globalVerdictMaritalStatusDistribution = (GlobalVerdictMaritalStatusDistribution) getSession()
				.get(GlobalVerdictMaritalStatusDistribution.class,
						globalVerdictMaritalStatusDistribution
								.getMaritalStatus().getId());

		if (globalVerdictMaritalStatusDistribution == null) {

			logger.debug("Deleted GlobalVerdictMaritalStatusDistribution object from database. Exiting GlobalVerdictMaritalStatusDistribution deleteById method");
			return true;
		} else {

			return false;
		}
	}

}
