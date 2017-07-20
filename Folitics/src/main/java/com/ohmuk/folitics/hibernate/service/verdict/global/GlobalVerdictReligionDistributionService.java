package com.ohmuk.folitics.hibernate.service.verdict.global;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdictReligionDistribution;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Religion;
import com.ohmuk.folitics.hibernate.repository.verdict.global.IGlobalVerdictDistributionRepository;

/**
 * Service implementation for {@link GlobalVerdictReligionDistribution}
 * 
 * @author Abhishek
 *
 */
@Service
@Transactional
public class GlobalVerdictReligionDistributionService
		implements
		IGlobalVerdictDistributionService<GlobalVerdictReligionDistribution, Religion> {

	private static Logger logger = LoggerFactory
			.getLogger(GlobalVerdictReligionDistributionService.class);

	@Autowired
	private IGlobalVerdictDistributionRepository<GlobalVerdictReligionDistribution, Religion> globalVerdictReligionDistributionRepository;

	@Override
	public GlobalVerdictReligionDistribution create(
			GlobalVerdictReligionDistribution globalVerdictReligionDistribution)
			throws MessageException {

		logger.debug("Entered GlobalVerdictReligionDistributionService create method");

		if (globalVerdictReligionDistribution == null) {
			logger.error("GlobalVerdictReligionDistribution object found null in GlobalVerdictReligionDistributionService.create method");
			throw (new MessageException(
					"GlobalVerdictReligionDistribution object can't be null"));
		}

		if (globalVerdictReligionDistribution.getReligion() == null) {
			logger.error("Religion in GlobalVerdictReligionDistribution object found null in GlobalVerdictReligionDistributionService.create method");
			throw (new MessageException(
					"Religion in GlobalVerdictReligionDistribution object can't be null"));
		}

		logger.debug("Trying to save the GlobalVerdictReligionDistribution object for Religion = "
				+ globalVerdictReligionDistribution.getReligion().getId());

		globalVerdictReligionDistribution = globalVerdictReligionDistributionRepository
				.save(globalVerdictReligionDistribution);

		logger.debug("Saved GlobalVerdictReligionDistribution object in the database. Exiting GlobalVerdictReligionDistributionService.create method");

		return globalVerdictReligionDistribution;
	}

	@Override
	public GlobalVerdictReligionDistribution read(Religion religion)
			throws MessageException {

		logger.debug("Entered GlobalVerdictReligionDistributionService read method");

		if (religion == null) {
			logger.error("Religion found null in GlobalVerdictReligionDistributionService.read method");
			throw (new MessageException("Religion can't be null"));
		}

		logger.debug("Trying to get the GlobalVerdictReligionDistribution object for id = "
				+ religion.getId());

		GlobalVerdictReligionDistribution globalVerdictReligionDistribution = globalVerdictReligionDistributionRepository
				.find(religion);

		logger.debug("Got GlobalVerdictReligionDistribution object from the database. Exiting GlobalVerdictReligionDistributionService.read method");

		return globalVerdictReligionDistribution;
	}

	@Override
	public List<GlobalVerdictReligionDistribution> readAll() {

		logger.debug("Entered GlobalVerdictReligionDistributionService readAll method");
		logger.debug("Trying to get all the GlobalVerdictReligionDistribution objects from databse");

		List<GlobalVerdictReligionDistribution> globalVerdictReligionDistributions = globalVerdictReligionDistributionRepository
				.findAll();

		logger.debug("Got all the GlobalVerdictReligionDistribution from database. Exiting GlobalVerdictReligionDistributionService.readAll method");

		return globalVerdictReligionDistributions;
	}

	@Override
	public GlobalVerdictReligionDistribution update(
			GlobalVerdictReligionDistribution globalVerdictReligionDistribution)
			throws MessageException {

		logger.debug("Entered GlobalVerdictReligionDistributionService update method");

		if (globalVerdictReligionDistribution == null) {
			logger.error("GlobalVerdictReligionDistribution object found null in GlobalVerdictReligionDistributionService.update method");
			throw (new MessageException(
					"GlobalVerdictReligionDistribution object can't be null"));
		}

		if (globalVerdictReligionDistribution.getReligion() == null) {
			logger.error("Religion in GlobalVerdictReligionDistribution object found null in GlobalVerdictReligionDistributionService.update method");
			throw (new MessageException(
					"Religion in GlobalVerdictReligionDistribution object can't be null"));
		}

		logger.debug("Trying to update GlobalVerdictReligionDistribution object in databse for Religion id = "
				+ globalVerdictReligionDistribution.getReligion().getId());

		globalVerdictReligionDistribution = globalVerdictReligionDistributionRepository
				.update(globalVerdictReligionDistribution);

		logger.debug("Updated GlobalVerdictReligionDistribution in database. Exiting GlobalVerdictReligionDistributionService.update method");

		return globalVerdictReligionDistribution;
	}

	@Override
	public boolean deleteById(Religion religion) throws MessageException {

		logger.debug("Entered GlobalVerdictReligionDistributionService deleteById method");

		if (religion == null) {
			logger.error("Religion found null in GlobalVerdictReligionDistributionService.delete method");
			throw (new MessageException("Religion can't be null"));
		}

		logger.debug("Trying to get the object for GlobalVerdictReligionDistribution with Religion id = "
				+ religion.getId());

		boolean status = globalVerdictReligionDistributionRepository
				.deleteById(religion);

		logger.debug("Deleted GlobalVerdictReligionDistribution object from database. Exiting GlobalVerdictReligionDistributionService.deleteById method");

		return status;
	}

	@Override
	public boolean delete(
			GlobalVerdictReligionDistribution globalVerdictReligionDistribution)
			throws MessageException {

		logger.debug("Entered GlobalVerdictReligionDistributionService delete method");

		if (globalVerdictReligionDistribution == null) {
			logger.error("GlobalVerdictReligionDistribution object found null in GlobalVerdictReligionDistributionService.delete method");
			throw (new MessageException(
					"GlobalVerdictReligionDistribution object can't be null"));
		}

		if (globalVerdictReligionDistribution.getReligion() == null) {
			logger.error("Religion in GlobalVerdictReligionDistribution object found null in GlobalVerdictReligionDistributionService.delete method");
			throw (new MessageException(
					"Religion in GlobalVerdictReligionDistribution object can't be null"));
		}

		logger.debug("Trying to get the object for GlobalVerdictReligionDistribution with Religion id = "
				+ globalVerdictReligionDistribution.getReligion().getId());

		boolean status = globalVerdictReligionDistributionRepository
				.delete(globalVerdictReligionDistribution);

		logger.debug("Deleted GlobalVerdictReligionDistribution object from database. Exiting GlobalVerdictReligionDistributionService.delete method");

		return status;
	}

}
