package com.ohmuk.folitics.hibernate.service.verdict.global;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdictMaritalStatusDistribution;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.MaritalStatus;
import com.ohmuk.folitics.hibernate.repository.verdict.global.IGlobalVerdictDistributionRepository;

/**
 * Service implementation for {@link GlobalVerdictMaritalStatusDistribution}
 * 
 * @author Abhishek
 *
 */
@Service
@Transactional
public class GlobalVerdictMaritalStatusDistributionService
		implements
		IGlobalVerdictDistributionService<GlobalVerdictMaritalStatusDistribution, MaritalStatus> {

	private static Logger logger = LoggerFactory
			.getLogger(GlobalVerdictMaritalStatusDistributionService.class);

	@Autowired
	private IGlobalVerdictDistributionRepository<GlobalVerdictMaritalStatusDistribution, MaritalStatus> globalVerdictMaritalStatusDistributionRepository;

	@Override
	public GlobalVerdictMaritalStatusDistribution create(
			GlobalVerdictMaritalStatusDistribution globalVerdictMaritalStatusDistribution)
			throws MessageException {

		logger.debug("Entered GlobalVerdictMaritalStatusDistributionService create method");

		if (globalVerdictMaritalStatusDistribution == null) {
			logger.error("GlobalVerdictMaritalStatusDistribution object found null in GlobalVerdictMaritalStatusDistributionService.create method");
			throw (new MessageException(
					"GlobalVerdictMaritalStatusDistribution object can't be null"));
		}

		if (globalVerdictMaritalStatusDistribution.getMaritalStatus() == null) {
			logger.error("MaritalStatus in GlobalVerdictMaritalStatusDistribution object found null in GlobalVerdictMaritalStatusDistributionService.create method");
			throw (new MessageException(
					"MaritalStatus in GlobalVerdictMaritalStatusDistribution object can't be null"));
		}

		logger.debug("Trying to save the GlobalVerdictMaritalStatusDistribution object for MaritalStatus = "
				+ globalVerdictMaritalStatusDistribution.getMaritalStatus()
						.getId());

		globalVerdictMaritalStatusDistribution = globalVerdictMaritalStatusDistributionRepository
				.save(globalVerdictMaritalStatusDistribution);

		logger.debug("Saved GlobalVerdictMaritalStatusDistribution object in the database. Exiting GlobalVerdictMaritalStatusDistributionService.create method");

		return globalVerdictMaritalStatusDistribution;
	}

	@Override
	public GlobalVerdictMaritalStatusDistribution read(
			MaritalStatus maritalStatus) throws MessageException {

		logger.debug("Entered GlobalVerdictMaritalStatusDistributionService read method");

		if (maritalStatus == null) {
			logger.error("MaritalStatus found null in GlobalVerdictMaritalStatusDistributionService.read method");
			throw (new MessageException("MaritalStatus can't be null"));
		}

		logger.debug("Trying to get the GlobalVerdictMaritalStatusDistribution object for id = "
				+ maritalStatus.getId());

		GlobalVerdictMaritalStatusDistribution globalVerdictMaritalStatusDistribution = globalVerdictMaritalStatusDistributionRepository
				.find(maritalStatus);

		logger.debug("Got GlobalVerdictMaritalStatusDistribution object from the database. Exiting GlobalVerdictMaritalStatusDistributionService.read method");

		return globalVerdictMaritalStatusDistribution;
	}

	@Override
	public List<GlobalVerdictMaritalStatusDistribution> readAll() {

		logger.debug("Entered GlobalVerdictMaritalStatusDistributionService readAll method");
		logger.debug("Trying to get all the GlobalVerdictMaritalStatusDistribution objects from databse");

		List<GlobalVerdictMaritalStatusDistribution> globalVerdictMaritalStatusDistributions = globalVerdictMaritalStatusDistributionRepository
				.findAll();

		logger.debug("Got all the GlobalVerdictMaritalStatusDistribution from database. Exiting GlobalVerdictMaritalStatusDistributionService.readAll method");

		return globalVerdictMaritalStatusDistributions;
	}

	@Override
	public GlobalVerdictMaritalStatusDistribution update(
			GlobalVerdictMaritalStatusDistribution globalVerdictMaritalStatusDistribution)
			throws MessageException {

		logger.debug("Entered GlobalVerdictMaritalStatusDistributionService update method");

		if (globalVerdictMaritalStatusDistribution == null) {
			logger.error("GlobalVerdictMaritalStatusDistribution object found null in GlobalVerdictMaritalStatusDistributionService.update method");
			throw (new MessageException(
					"GlobalVerdictMaritalStatusDistribution object can't be null"));
		}

		if (globalVerdictMaritalStatusDistribution.getMaritalStatus() == null) {
			logger.error("MaritalStatus in GlobalVerdictMaritalStatusDistribution object found null in GlobalVerdictMaritalStatusDistributionService.update method");
			throw (new MessageException(
					"MaritalStatus in GlobalVerdictMaritalStatusDistribution object can't be null"));
		}

		logger.debug("Trying to update GlobalVerdictMaritalStatusDistribution object in databse for MaritalStatus id = "
				+ globalVerdictMaritalStatusDistribution.getMaritalStatus()
						.getId());

		globalVerdictMaritalStatusDistribution = globalVerdictMaritalStatusDistributionRepository
				.update(globalVerdictMaritalStatusDistribution);

		logger.debug("Updated GlobalVerdictMaritalStatusDistribution in database. Exiting GlobalVerdictMaritalStatusDistributionService.update method");

		return globalVerdictMaritalStatusDistribution;
	}

	@Override
	public boolean deleteById(MaritalStatus maritalStatus)
			throws MessageException {

		logger.debug("Entered GlobalVerdictMaritalStatusDistributionService deleteById method");

		if (maritalStatus == null) {
			logger.error("MaritalStatus found null in GlobalVerdictMaritalStatusDistributionService.delete method");
			throw (new MessageException("MaritalStatus can't be null"));
		}

		logger.debug("Trying to get the object for GlobalVerdictMaritalStatusDistribution with MaritalStatus id = "
				+ maritalStatus.getId());

		boolean status = globalVerdictMaritalStatusDistributionRepository
				.deleteById(maritalStatus);

		logger.debug("Deleted GlobalVerdictMaritalStatusDistribution object from database. Exiting GlobalVerdictMaritalStatusDistributionService.deleteById method");

		return status;
	}

	@Override
	public boolean delete(
			GlobalVerdictMaritalStatusDistribution globalVerdictMaritalStatusDistribution)
			throws MessageException {

		logger.debug("Entered GlobalVerdictMaritalStatusDistributionService delete method");

		if (globalVerdictMaritalStatusDistribution == null) {
			logger.error("GlobalVerdictMaritalStatusDistribution object found null in GlobalVerdictMaritalStatusDistributionService.delete method");
			throw (new MessageException(
					"GlobalVerdictMaritalStatusDistribution object can't be null"));
		}

		if (globalVerdictMaritalStatusDistribution.getMaritalStatus() == null) {
			logger.error("MaritalStatus in GlobalVerdictMaritalStatusDistribution object found null in GlobalVerdictMaritalStatusDistributionService.delete method");
			throw (new MessageException(
					"MaritalStatus in GlobalVerdictMaritalStatusDistribution object can't be null"));
		}

		logger.debug("Trying to get the object for GlobalVerdictMaritalStatusDistribution with MaritalStatus id = "
				+ globalVerdictMaritalStatusDistribution.getMaritalStatus()
						.getId());

		boolean status = globalVerdictMaritalStatusDistributionRepository
				.delete(globalVerdictMaritalStatusDistribution);

		logger.debug("Deleted GlobalVerdictMaritalStatusDistribution object from database. Exiting GlobalVerdictMaritalStatusDistributionService.delete method");

		return status;
	}

}
