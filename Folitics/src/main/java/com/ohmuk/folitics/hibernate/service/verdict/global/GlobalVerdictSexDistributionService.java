package com.ohmuk.folitics.hibernate.service.verdict.global;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdictSexDistribution;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Sex;
import com.ohmuk.folitics.hibernate.repository.verdict.global.IGlobalVerdictDistributionRepository;

/**
 * Service implementation for {@link GlobalVerdictSexDistribution}
 * 
 * @author Abhishek
 *
 */
@Service
@Transactional
public class GlobalVerdictSexDistributionService implements
		IGlobalVerdictDistributionService<GlobalVerdictSexDistribution, Sex> {

	private static Logger logger = LoggerFactory
			.getLogger(GlobalVerdictSexDistributionService.class);

	@Autowired
	private IGlobalVerdictDistributionRepository<GlobalVerdictSexDistribution, Sex> globalVerdictSexDistributionRepository;

	@Override
	public GlobalVerdictSexDistribution create(
			GlobalVerdictSexDistribution globalVerdictSexDistribution)
			throws MessageException {

		logger.debug("Entered GlobalVerdictSexDistributionService create method");

		if (globalVerdictSexDistribution == null) {
			logger.error("GlobalVerdictSexDistribution object found null in GlobalVerdictSexDistributionService.create method");
			throw (new MessageException(
					"GlobalVerdictSexDistribution object can't be null"));
		}

		if (globalVerdictSexDistribution.getSex() == null) {
			logger.error("Sex in GlobalVerdictSexDistribution object found null in GlobalVerdictSexDistributionService.create method");
			throw (new MessageException(
					"Sex in GlobalVerdictSexDistribution object can't be null"));
		}

		logger.debug("Trying to save the GlobalVerdictSexDistribution object for Sex = "
				+ globalVerdictSexDistribution.getSex().getId());

		globalVerdictSexDistribution = globalVerdictSexDistributionRepository
				.save(globalVerdictSexDistribution);

		logger.debug("Saved GlobalVerdictSexDistribution object in the database. Exiting GlobalVerdictSexDistributionService.create method");

		return globalVerdictSexDistribution;
	}

	@Override
	public GlobalVerdictSexDistribution read(Sex sex) throws MessageException {

		logger.debug("Entered GlobalVerdictSexDistributionService read method");

		if (sex == null) {
			logger.error("Sex found null in GlobalVerdictSexDistributionService.read method");
			throw (new MessageException("Sex can't be null"));
		}

		logger.debug("Trying to get the GlobalVerdictSexDistribution object for id = "
				+ sex.getId());

		GlobalVerdictSexDistribution globalVerdictSexDistribution = globalVerdictSexDistributionRepository
				.find(sex);

		logger.debug("Got GlobalVerdictSexDistribution object from the database. Exiting GlobalVerdictSexDistributionService.read method");

		return globalVerdictSexDistribution;
	}

	@Override
	public List<GlobalVerdictSexDistribution> readAll() {

		logger.debug("Entered GlobalVerdictSexDistributionService readAll method");
		logger.debug("Trying to get all the GlobalVerdictSexDistribution objects from databse");

		List<GlobalVerdictSexDistribution> globalVerdictSexDistributions = globalVerdictSexDistributionRepository
				.findAll();

		logger.debug("Got all the GlobalVerdictSexDistribution from database. Exiting GlobalVerdictSexDistributionService.readAll method");

		return globalVerdictSexDistributions;
	}

	@Override
	public GlobalVerdictSexDistribution update(
			GlobalVerdictSexDistribution globalVerdictSexDistribution)
			throws MessageException {

		logger.debug("Entered GlobalVerdictSexDistributionService update method");

		if (globalVerdictSexDistribution == null) {
			logger.error("GlobalVerdictSexDistribution object found null in GlobalVerdictSexDistributionService.update method");
			throw (new MessageException(
					"GlobalVerdictSexDistribution object can't be null"));
		}

		if (globalVerdictSexDistribution.getSex() == null) {
			logger.error("Sex in GlobalVerdictSexDistribution object found null in GlobalVerdictSexDistributionService.update method");
			throw (new MessageException(
					"Sex in GlobalVerdictSexDistribution object can't be null"));
		}

		logger.debug("Trying to update GlobalVerdictSexDistribution object in databse for Sex id = "
				+ globalVerdictSexDistribution.getSex().getId());

		globalVerdictSexDistribution = globalVerdictSexDistributionRepository
				.update(globalVerdictSexDistribution);

		logger.debug("Updated GlobalVerdictSexDistribution in database. Exiting GlobalVerdictSexDistributionService.update method");

		return globalVerdictSexDistribution;
	}

	@Override
	public boolean deleteById(Sex sex) throws MessageException {

		logger.debug("Entered GlobalVerdictSexDistributionService deleteById method");

		if (sex == null) {
			logger.error("Sex found null in GlobalVerdictSexDistributionService.delete method");
			throw (new MessageException("Sex can't be null"));
		}

		logger.debug("Trying to get the object for GlobalVerdictSexDistribution with Sex id = "
				+ sex.getId());

		boolean status = globalVerdictSexDistributionRepository.deleteById(sex);

		logger.debug("Deleted GlobalVerdictSexDistribution object from database. Exiting GlobalVerdictSexDistributionService.deleteById method");

		return status;
	}

	@Override
	public boolean delete(
			GlobalVerdictSexDistribution globalVerdictSexDistribution)
			throws MessageException {

		logger.debug("Entered GlobalVerdictSexDistributionService delete method");

		if (globalVerdictSexDistribution == null) {
			logger.error("GlobalVerdictSexDistribution object found null in GlobalVerdictSexDistributionService.delete method");
			throw (new MessageException(
					"GlobalVerdictSexDistribution object can't be null"));
		}

		if (globalVerdictSexDistribution.getSex() == null) {
			logger.error("Sex in GlobalVerdictSexDistribution object found null in GlobalVerdictSexDistributionService.delete method");
			throw (new MessageException(
					"Sex in GlobalVerdictSexDistribution object can't be null"));
		}

		logger.debug("Trying to get the object for GlobalVerdictSexDistribution with Sex id = "
				+ globalVerdictSexDistribution.getSex().getId());

		boolean status = globalVerdictSexDistributionRepository
				.delete(globalVerdictSexDistribution);

		logger.debug("Deleted GlobalVerdictSexDistribution object from database. Exiting GlobalVerdictSexDistributionService.delete method");

		return status;
	}

}
