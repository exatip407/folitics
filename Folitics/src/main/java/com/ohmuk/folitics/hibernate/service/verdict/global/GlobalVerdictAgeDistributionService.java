package com.ohmuk.folitics.hibernate.service.verdict.global;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdictAgeDistribution;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.AgeGroup;
import com.ohmuk.folitics.hibernate.repository.verdict.global.IGlobalVerdictDistributionRepository;

/**
 * Service implementation for {@link GlobalVerdictAgeDistribution}
 * 
 * @author Abhishek
 *
 */
@Service
@Transactional
public class GlobalVerdictAgeDistributionService
		implements
		IGlobalVerdictDistributionService<GlobalVerdictAgeDistribution, AgeGroup> {

	private static Logger logger = LoggerFactory
			.getLogger(GlobalVerdictAgeDistributionService.class);

	@Autowired
	private IGlobalVerdictDistributionRepository<GlobalVerdictAgeDistribution, AgeGroup> globalVerdictAgeDistributionRepository;

	@Override
	public GlobalVerdictAgeDistribution create(
			GlobalVerdictAgeDistribution globalVerdictAgeDistribution)
			throws MessageException {

		logger.debug("Entered GlobalVerdictAgeDistributionService create method");

		if (globalVerdictAgeDistribution == null) {
			logger.error("GlobalVerdictAgeDistribution object found null in GlobalVerdictAgeDistributionService.create method");
			throw (new MessageException(
					"GlobalVerdictAgeDistribution object can't be null"));
		}

		if (globalVerdictAgeDistribution.getAgeGroup() == null) {
			logger.error("AgeGroup in GlobalVerdictAgeDistribution object found null in GlobalVerdictAgeDistributionService.create method");
			throw (new MessageException(
					"AgeGroup in GlobalVerdictAgeDistribution object can't be null"));
		}

		logger.debug("Trying to save the GlobalVerdictAgeDistribution object for AgeGroup = "
				+ globalVerdictAgeDistribution.getAgeGroup().getId());

		globalVerdictAgeDistribution = globalVerdictAgeDistributionRepository
				.save(globalVerdictAgeDistribution);

		logger.debug("Saved GlobalVerdictAgeDistribution object in the database. Exiting GlobalVerdictAgeDistributionService.create method");

		return globalVerdictAgeDistribution;
	}

	@Override
	public GlobalVerdictAgeDistribution read(AgeGroup ageGroup)
			throws MessageException {

		logger.debug("Entered GlobalVerdictAgeDistributionService read method");

		if (ageGroup == null) {
			logger.error("AgeGroup found null in GlobalVerdictAgeDistributionService.read method");
			throw (new MessageException("AgeGroup can't be null"));
		}

		logger.debug("Trying to get the GlobalVerdictAgeDistribution object for id = "
				+ ageGroup.getId());

		GlobalVerdictAgeDistribution globalVerdictAgeDistribution = globalVerdictAgeDistributionRepository
				.find(ageGroup);

		logger.debug("Got GlobalVerdictAgeDistribution object from the database. Exiting GlobalVerdictAgeDistributionService.read method");

		return globalVerdictAgeDistribution;
	}

	@Override
	public List<GlobalVerdictAgeDistribution> readAll() {

		logger.debug("Entered GlobalVerdictAgeDistributionService readAll method");
		logger.debug("Trying to get all the GlobalVerdictAgeDistribution objects from databse");

		List<GlobalVerdictAgeDistribution> globalVerdictAgeDistributions = globalVerdictAgeDistributionRepository
				.findAll();

		logger.debug("Got all the GlobalVerdictAgeDistribution from database. Exiting GlobalVerdictAgeDistributionService.readAll method");

		return globalVerdictAgeDistributions;
	}

	@Override
	public GlobalVerdictAgeDistribution update(
			GlobalVerdictAgeDistribution globalVerdictAgeDistribution)
			throws MessageException {

		logger.debug("Entered GlobalVerdictAgeDistributionService update method");

		if (globalVerdictAgeDistribution == null) {
			logger.error("GlobalVerdictAgeDistribution object found null in GlobalVerdictAgeDistributionService.update method");
			throw (new MessageException(
					"GlobalVerdictAgeDistribution object can't be null"));
		}

		if (globalVerdictAgeDistribution.getAgeGroup() == null) {
			logger.error("AgeGroup in GlobalVerdictAgeDistribution object found null in GlobalVerdictAgeDistributionService.update method");
			throw (new MessageException(
					"AgeGroup in GlobalVerdictAgeDistribution object can't be null"));
		}

		logger.debug("Trying to update GlobalVerdictAgeDistribution object in databse for AgeGroup id = "
				+ globalVerdictAgeDistribution.getAgeGroup().getId());

		globalVerdictAgeDistribution = globalVerdictAgeDistributionRepository
				.update(globalVerdictAgeDistribution);

		logger.debug("Updated GlobalVerdictAgeDistribution in database. Exiting GlobalVerdictAgeDistributionService.update method");

		return globalVerdictAgeDistribution;
	}

	@Override
	public boolean deleteById(AgeGroup ageGroup) throws MessageException {

		logger.debug("Entered GlobalVerdictAgeDistributionService deleteById method");

		if (ageGroup == null) {
			logger.error("AgeGroup found null in GlobalVerdictAgeDistributionService.delete method");
			throw (new MessageException("AgeGroup can't be null"));
		}

		logger.debug("Trying to get the object for GlobalVerdictAgeDistribution with id = "
				+ ageGroup);

		boolean status = globalVerdictAgeDistributionRepository
				.deleteById(ageGroup);

		logger.debug("Deleted GlobalVerdictAgeDistribution object from database. Exiting GlobalVerdictAgeDistributionService.deleteById method");

		return status;
	}

	@Override
	public boolean delete(
			GlobalVerdictAgeDistribution globalVerdictAgeDistribution)
			throws MessageException {

		logger.debug("Entered GlobalVerdictAgeDistributionService delete method");

		if (globalVerdictAgeDistribution == null) {
			logger.error("GlobalVerdictAgeDistribution object found null in GlobalVerdictAgeDistributionService.delete method");
			throw (new MessageException(
					"GlobalVerdictAgeDistribution object can't be null"));
		}

		if (globalVerdictAgeDistribution.getAgeGroup() == null) {
			logger.error("AgeGroup in GlobalVerdictAgeDistribution object found null in GlobalVerdictAgeDistributionService.delete method");
			throw (new MessageException(
					"AgeGroup in GlobalVerdictAgeDistribution object can't be null"));
		}

		logger.debug("Trying to get the object for GlobalVerdictAgeDistribution with AgeGroup id = "
				+ globalVerdictAgeDistribution.getAgeGroup().getId());

		boolean status = globalVerdictAgeDistributionRepository
				.delete(globalVerdictAgeDistribution);

		logger.debug("Deleted GlobalVerdictAgeDistribution object from database. Exiting GlobalVerdictAgeDistributionService.delete method");

		return status;
	}

}
