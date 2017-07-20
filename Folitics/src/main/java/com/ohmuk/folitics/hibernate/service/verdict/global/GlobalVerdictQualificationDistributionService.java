package com.ohmuk.folitics.hibernate.service.verdict.global;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdictQualificationDistribution;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Qualification;
import com.ohmuk.folitics.hibernate.repository.verdict.global.IGlobalVerdictDistributionRepository;

/**
 * Service implementation for {@link GlobalVerdictQualificationDistribution}
 * 
 * @author Abhishek
 *
 */
@Service
@Transactional
public class GlobalVerdictQualificationDistributionService
		implements
		IGlobalVerdictDistributionService<GlobalVerdictQualificationDistribution, Qualification> {

	private static Logger logger = LoggerFactory
			.getLogger(GlobalVerdictQualificationDistributionService.class);

	@Autowired
	private IGlobalVerdictDistributionRepository<GlobalVerdictQualificationDistribution, Qualification> globalVerdictQualificationDistributionRepository;

	@Override
	public GlobalVerdictQualificationDistribution create(
			GlobalVerdictQualificationDistribution globalVerdictQualificationDistribution)
			throws MessageException {

		logger.debug("Entered GlobalVerdictQualificationDistributionService create method");

		if (globalVerdictQualificationDistribution == null) {
			logger.error("GlobalVerdictQualificationDistribution object found null in GlobalVerdictQualificationDistributionService.create method");
			throw (new MessageException(
					"GlobalVerdictQualificationDistribution object can't be null"));
		}

		if (globalVerdictQualificationDistribution.getQualification() == null) {
			logger.error("Qualification in GlobalVerdictQualificationDistribution object found null in GlobalVerdictQualificationDistributionService.create method");
			throw (new MessageException(
					"Qualification in GlobalVerdictQualificationDistribution object can't be null"));
		}

		logger.debug("Trying to save the GlobalVerdictQualificationDistribution object for Qualification = "
				+ globalVerdictQualificationDistribution.getQualification()
						.getId());

		globalVerdictQualificationDistribution = globalVerdictQualificationDistributionRepository
				.save(globalVerdictQualificationDistribution);

		logger.debug("Saved GlobalVerdictQualificationDistribution object in the database. Exiting GlobalVerdictQualificationDistributionService.create method");

		return globalVerdictQualificationDistribution;
	}

	@Override
	public GlobalVerdictQualificationDistribution read(
			Qualification qualification) throws MessageException {

		logger.debug("Entered GlobalVerdictQualificationDistributionService read method");

		if (qualification == null) {
			logger.error("Qualification found null in GlobalVerdictQualificationDistributionService.read method");
			throw (new MessageException("Qualification can't be null"));
		}

		logger.debug("Trying to get the GlobalVerdictQualificationDistribution object for id = "
				+ qualification.getId());

		GlobalVerdictQualificationDistribution globalVerdictQualificationDistribution = globalVerdictQualificationDistributionRepository
				.find(qualification);

		logger.debug("Got GlobalVerdictQualificationDistribution object from the database. Exiting GlobalVerdictQualificationDistributionService.read method");

		return globalVerdictQualificationDistribution;
	}

	@Override
	public List<GlobalVerdictQualificationDistribution> readAll() {

		logger.debug("Entered GlobalVerdictQualificationDistributionService readAll method");
		logger.debug("Trying to get all the GlobalVerdictQualificationDistribution objects from databse");

		List<GlobalVerdictQualificationDistribution> globalVerdictQualificationDistributions = globalVerdictQualificationDistributionRepository
				.findAll();

		logger.debug("Got all the GlobalVerdictQualificationDistribution from database. Exiting GlobalVerdictQualificationDistributionService.readAll method");

		return globalVerdictQualificationDistributions;
	}

	@Override
	public GlobalVerdictQualificationDistribution update(
			GlobalVerdictQualificationDistribution globalVerdictQualificationDistribution)
			throws MessageException {

		logger.debug("Entered GlobalVerdictQualificationDistributionService update method");

		if (globalVerdictQualificationDistribution == null) {
			logger.error("GlobalVerdictQualificationDistribution object found null in GlobalVerdictQualificationDistributionService.update method");
			throw (new MessageException(
					"GlobalVerdictQualificationDistribution object can't be null"));
		}

		if (globalVerdictQualificationDistribution.getQualification() == null) {
			logger.error("Qualification in GlobalVerdictQualificationDistribution object found null in GlobalVerdictQualificationDistributionService.update method");
			throw (new MessageException(
					"Qualification in GlobalVerdictQualificationDistribution object can't be null"));
		}

		logger.debug("Trying to update GlobalVerdictQualificationDistribution object in databse for Qualification id = "
				+ globalVerdictQualificationDistribution.getQualification()
						.getId());

		globalVerdictQualificationDistribution = globalVerdictQualificationDistributionRepository
				.update(globalVerdictQualificationDistribution);

		logger.debug("Updated GlobalVerdictQualificationDistribution in database. Exiting GlobalVerdictQualificationDistributionService.update method");

		return globalVerdictQualificationDistribution;
	}

	@Override
	public boolean deleteById(Qualification qualification)
			throws MessageException {

		logger.debug("Entered GlobalVerdictQualificationDistributionService deleteById method");

		if (qualification == null) {
			logger.error("Qualification found null in GlobalVerdictQualificationDistributionService.delete method");
			throw (new MessageException("Qualification can't be null"));
		}

		logger.debug("Trying to get the object for GlobalVerdictQualificationDistribution with Qualification id = "
				+ qualification.getId());

		boolean status = globalVerdictQualificationDistributionRepository
				.deleteById(qualification);

		logger.debug("Deleted GlobalVerdictQualificationDistribution object from database. Exiting GlobalVerdictQualificationDistributionService.deleteById method");

		return status;
	}

	@Override
	public boolean delete(
			GlobalVerdictQualificationDistribution globalVerdictQualificationDistribution)
			throws MessageException {

		logger.debug("Entered GlobalVerdictQualificationDistributionService delete method");

		if (globalVerdictQualificationDistribution == null) {
			logger.error("GlobalVerdictQualificationDistribution object found null in GlobalVerdictQualificationDistributionService.delete method");
			throw (new MessageException(
					"GlobalVerdictQualificationDistribution object can't be null"));
		}

		if (globalVerdictQualificationDistribution.getQualification() == null) {
			logger.error("Qualification in GlobalVerdictQualificationDistribution object found null in GlobalVerdictQualificationDistributionService.delete method");
			throw (new MessageException(
					"Qualification in GlobalVerdictQualificationDistribution object can't be null"));
		}

		logger.debug("Trying to get the object for GlobalVerdictQualificationDistribution with Qualification id = "
				+ globalVerdictQualificationDistribution.getQualification()
						.getId());

		boolean status = globalVerdictQualificationDistributionRepository
				.delete(globalVerdictQualificationDistribution);

		logger.debug("Deleted GlobalVerdictQualificationDistribution object from database. Exiting GlobalVerdictQualificationDistributionService.delete method");

		return status;
	}

}
