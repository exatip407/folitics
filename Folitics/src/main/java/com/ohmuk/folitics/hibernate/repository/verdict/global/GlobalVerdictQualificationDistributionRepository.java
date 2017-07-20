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

import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdictQualificationDistribution;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Qualification;

/**
 * Repository implementation for {@link GlobalVerdictQualificationDistribution}
 * 
 * @author Abhishek
 *
 */
@Component
@Repository
public class GlobalVerdictQualificationDistributionRepository
		implements
		IGlobalVerdictDistributionRepository<GlobalVerdictQualificationDistribution, Qualification> {

	private static Logger logger = LoggerFactory
			.getLogger(GlobalVerdictQualificationDistributionRepository.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public GlobalVerdictQualificationDistribution save(
			GlobalVerdictQualificationDistribution globalVerdictQualificationDistribution) {

		logger.debug("Entered GlobalVerdictQualificationDistributionRepository save method");
		logger.debug("Trying to save GlobalVerdictQualificationDistribution for Qualification id = "
				+ globalVerdictQualificationDistribution.getQualification()
						.getId());

		globalVerdictQualificationDistribution = (GlobalVerdictQualificationDistribution) getSession()
				.save(globalVerdictQualificationDistribution);

		logger.debug("Saved GlobalVerdictQualificationDistribution object and got id = "
				+ globalVerdictQualificationDistribution.getQualification()
						.getId()
				+ " and now getting GlobalVerdictQualificationDistribution object from database");
		globalVerdictQualificationDistribution = (GlobalVerdictQualificationDistribution) getSession()
				.get(GlobalVerdictQualificationDistribution.class,
						globalVerdictQualificationDistribution
								.getQualification().getId());

		logger.debug("Got GlobalVerdictQualificationDistribution object from database. Exiting GlobalVerdictQualificationDistributionRepository save method");
		return globalVerdictQualificationDistribution;
	}

	@Override
	public GlobalVerdictQualificationDistribution find(
			Qualification qualification) {

		logger.debug("Entered GlobalVerdictQualificationDistributionRepository find method");
		logger.debug("Trying to get GlobalVerdictQualificationDistribution with id = "
				+ qualification.getId());

		GlobalVerdictQualificationDistribution globalVerdictQualificationDistribution = (GlobalVerdictQualificationDistribution) getSession()
				.get(GlobalVerdictQualificationDistribution.class,
						qualification.getId());

		logger.debug("Got GlobalVerdictQualificationDistribution object from database. Exiting GlobalVerdictQualificationDistributionRepository find method");

		return globalVerdictQualificationDistribution;
	}

	@Override
	public List<GlobalVerdictQualificationDistribution> findAll() {

		logger.debug("Entered GlobalVerdictQualificationDistributionRepository findAll method");
		logger.debug("Trying to get all GlobalVerdictQualificationDistribution");

		Criteria selectAllCriteria = getSession().createCriteria(
				GlobalVerdictQualificationDistribution.class);
		@SuppressWarnings("unchecked")
		List<GlobalVerdictQualificationDistribution> globalVerdictQualificationDistributions = selectAllCriteria
				.list();

		logger.debug("Got all GlobalVerdictQualificationDistribution objects from database. Exiting GlobalVerdictQualificationDistributionRepository findAll method");

		return globalVerdictQualificationDistributions;
	}

	@Override
	public GlobalVerdictQualificationDistribution update(
			GlobalVerdictQualificationDistribution globalVerdictQualificationDistribution) {

		logger.debug("Entered GlobalVerdictQualificationDistributionRepository update method");
		logger.debug("Merging the object first with id = "
				+ globalVerdictQualificationDistribution.getQualification()
						.getId());

		globalVerdictQualificationDistribution = (GlobalVerdictQualificationDistribution) getSession()
				.merge(globalVerdictQualificationDistribution);

		logger.debug("Now updating the GlobalVerdictQualificationDistribution object in database with id = "
				+ globalVerdictQualificationDistribution.getQualification()
						.getId());
		getSession().update(globalVerdictQualificationDistribution);

		logger.debug("Getting the GlobalVerdictQualificationDistribution object from database");

		globalVerdictQualificationDistribution = (GlobalVerdictQualificationDistribution) getSession()
				.get(GlobalVerdictQualificationDistribution.class,
						globalVerdictQualificationDistribution
								.getQualification().getId());

		logger.debug("Got GlobalVerdictQualificationDistribution object from database. Exiting GlobalVerdictQualificationDistributionRepository update method");

		return globalVerdictQualificationDistribution;
	}

	@Override
	public boolean deleteById(Qualification qualification) {

		logger.debug("Entered GlobalVerdictQualificationDistributionRepository deleteById method");
		logger.debug("Trying to get GlobalVerdictQualificationDistribution with id = "
				+ qualification.getId());

		GlobalVerdictQualificationDistribution globalVerdictQualificationDistribution = (GlobalVerdictQualificationDistribution) getSession()
				.get(GlobalVerdictQualificationDistribution.class,
						qualification.getId());

		logger.debug("Now trying to delete the GlobalVerdictQualificationDistribution object with id = "
				+ globalVerdictQualificationDistribution.getQualification()
						.getId());

		getSession().delete(globalVerdictQualificationDistribution);

		globalVerdictQualificationDistribution = (GlobalVerdictQualificationDistribution) getSession()
				.get(GlobalVerdictQualificationDistribution.class,
						qualification);

		if (globalVerdictQualificationDistribution == null) {

			logger.debug("Deleted GlobalVerdictQualificationDistribution object from database. Exiting GlobalVerdictQualificationDistributionRepository deleteById method");
			return true;
		} else {

			return false;
		}
	}

	@Override
	public boolean delete(
			GlobalVerdictQualificationDistribution globalVerdictQualificationDistribution) {

		logger.debug("Entered GlobalVerdictQualificationDistributionRepository delete method");
		logger.debug("Trying to get GlobalVerdictQualificationDistribution with id = "
				+ globalVerdictQualificationDistribution.getQualification()
						.getId());

		globalVerdictQualificationDistribution = (GlobalVerdictQualificationDistribution) getSession()
				.get(GlobalVerdictQualificationDistribution.class,
						globalVerdictQualificationDistribution
								.getQualification().getId());

		logger.debug("Now trying to delete the GlobalVerdictQualificationDistribution object with id = "
				+ globalVerdictQualificationDistribution.getQualification()
						.getId());

		getSession().delete(globalVerdictQualificationDistribution);

		globalVerdictQualificationDistribution = (GlobalVerdictQualificationDistribution) getSession()
				.get(GlobalVerdictQualificationDistribution.class,
						globalVerdictQualificationDistribution
								.getQualification().getId());

		if (globalVerdictQualificationDistribution == null) {

			logger.debug("Deleted GlobalVerdictQualificationDistribution object from database. Exiting GlobalVerdictQualificationDistributionRepository deleteById method");
			return true;
		} else {

			return false;
		}
	}

}
