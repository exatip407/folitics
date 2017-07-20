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

import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdictSexDistribution;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Sex;

/**
 * Repository implementation for {@link GlobalVerdictSexDistribution}
 * 
 * @author Abhishek
 *
 */
@Component
@Repository
public class GlobalVerdictSexDistributionRepository implements
		IGlobalVerdictDistributionRepository<GlobalVerdictSexDistribution, Sex> {

	private static Logger logger = LoggerFactory
			.getLogger(GlobalVerdictSexDistributionRepository.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public GlobalVerdictSexDistribution save(
			GlobalVerdictSexDistribution globalVerdictSexDistribution) {

		logger.debug("Entered GlobalVerdictSexDistributionRepository save method");
		logger.debug("Trying to save GlobalVerdictSexDistribution for Sex id = "
				+ globalVerdictSexDistribution.getSex().getId());

		globalVerdictSexDistribution = (GlobalVerdictSexDistribution) getSession()
				.save(globalVerdictSexDistribution);

		logger.debug("Saved GlobalVerdictSexDistribution object and got id = "
				+ globalVerdictSexDistribution.getSex().getId()
				+ " and now getting GlobalVerdictSexDistribution object from database");
		globalVerdictSexDistribution = (GlobalVerdictSexDistribution) getSession()
				.get(GlobalVerdictSexDistribution.class,
						globalVerdictSexDistribution.getSex().getId());

		logger.debug("Got GlobalVerdictSexDistribution object from database. Exiting GlobalVerdictSexDistributionRepository save method");
		return globalVerdictSexDistribution;
	}

	@Override
	public GlobalVerdictSexDistribution find(Sex sex) {

		logger.debug("Entered GlobalVerdictSexDistributionRepository find method");
		logger.debug("Trying to get GlobalVerdictSexDistribution with id = "
				+ sex.getId());

		GlobalVerdictSexDistribution globalVerdictSexDistribution = (GlobalVerdictSexDistribution) getSession()
				.get(GlobalVerdictSexDistribution.class, sex.getId());

		logger.debug("Got GlobalVerdictSexDistribution object from database. Exiting GlobalVerdictSexDistributionRepository find method");

		return globalVerdictSexDistribution;
	}

	@Override
	public List<GlobalVerdictSexDistribution> findAll() {

		logger.debug("Entered GlobalVerdictSexDistributionRepository findAll method");
		logger.debug("Trying to get all GlobalVerdictSexDistribution");

		Criteria selectAllCriteria = getSession().createCriteria(
				GlobalVerdictSexDistribution.class);
		@SuppressWarnings("unchecked")
		List<GlobalVerdictSexDistribution> globalVerdictSexDistributions = selectAllCriteria
				.list();

		logger.debug("Got all GlobalVerdictSexDistribution objects from database. Exiting GlobalVerdictSexDistributionRepository findAll method");

		return globalVerdictSexDistributions;
	}

	@Override
	public GlobalVerdictSexDistribution update(
			GlobalVerdictSexDistribution globalVerdictSexDistribution) {

		logger.debug("Entered GlobalVerdictSexDistributionRepository update method");
		logger.debug("Merging the object first with id = "
				+ globalVerdictSexDistribution.getSex().getId());

		globalVerdictSexDistribution = (GlobalVerdictSexDistribution) getSession()
				.merge(globalVerdictSexDistribution);

		logger.debug("Now updating the GlobalVerdictSexDistribution object in database with id = "
				+ globalVerdictSexDistribution.getSex().getId());
		getSession().update(globalVerdictSexDistribution);

		logger.debug("Getting the GlobalVerdictSexDistribution object from database");

		globalVerdictSexDistribution = (GlobalVerdictSexDistribution) getSession()
				.get(GlobalVerdictSexDistribution.class,
						globalVerdictSexDistribution.getSex().getId());

		logger.debug("Got GlobalVerdictSexDistribution object from database. Exiting GlobalVerdictSexDistributionRepository update method");

		return globalVerdictSexDistribution;
	}

	@Override
	public boolean deleteById(Sex sex) {

		logger.debug("Entered GlobalVerdictSexDistributionRepository deleteById method");
		logger.debug("Trying to get GlobalVerdictAgeDistribution with id = "
				+ sex);

		GlobalVerdictSexDistribution globalVerdictSexDistribution = (GlobalVerdictSexDistribution) getSession()
				.get(GlobalVerdictSexDistribution.class, sex);

		logger.debug("Now trying to delete the GlobalVerdictSexDistribution object with id = "
				+ globalVerdictSexDistribution.getSex());

		getSession().delete(globalVerdictSexDistribution);

		globalVerdictSexDistribution = (GlobalVerdictSexDistribution) getSession()
				.get(GlobalVerdictSexDistribution.class, sex.getId());

		if (globalVerdictSexDistribution == null) {

			logger.debug("Deleted GlobalVerdictSexDistribution object from database. Exiting GlobalVerdictSexDistributionRepository deleteById method");
			return true;
		} else {

			return false;
		}
	}

	@Override
	public boolean delete(
			GlobalVerdictSexDistribution globalVerdictSexDistribution) {

		logger.debug("Entered GlobalVerdictSexDistributionRepository delete method");
		logger.debug("Trying to get GlobalVerdictSexDistribution with id = "
				+ globalVerdictSexDistribution.getSex().getId());

		globalVerdictSexDistribution = (GlobalVerdictSexDistribution) getSession()
				.get(GlobalVerdictSexDistribution.class,
						globalVerdictSexDistribution.getSex().getId());

		logger.debug("Now trying to delete the GlobalVerdictSexDistribution object with id = "
				+ globalVerdictSexDistribution.getSex().getId());

		getSession().delete(globalVerdictSexDistribution);

		globalVerdictSexDistribution = (GlobalVerdictSexDistribution) getSession()
				.get(GlobalVerdictSexDistribution.class,
						globalVerdictSexDistribution.getSex().getId());

		if (globalVerdictSexDistribution == null) {

			logger.debug("Deleted GlobalVerdictSexDistribution object from database. Exiting GlobalVerdictSexDistributionRepository deleteById method");
			return true;
		} else {

			return false;
		}
	}

}
