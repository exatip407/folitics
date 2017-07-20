package com.ohmuk.folitics.hibernate.service.verdict.global;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdictRegionDistribution;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Region;
import com.ohmuk.folitics.hibernate.repository.verdict.global.IGlobalVerdictDistributionRepository;

/**
 * Service implementation for {@link GlobalVerdictRegionDistribution}
 * 
 * @author Abhishek
 *
 */
@Service
@Transactional
public class GlobalVerdictRegionDistributionService
		implements
		IGlobalVerdictDistributionService<GlobalVerdictRegionDistribution, Region> {

	private static Logger logger = LoggerFactory
			.getLogger(GlobalVerdictRegionDistributionService.class);

	@Autowired
	private IGlobalVerdictDistributionRepository<GlobalVerdictRegionDistribution, Region> globalVerdictRegionDistributionRepository;

	@Override
	public GlobalVerdictRegionDistribution create(
			GlobalVerdictRegionDistribution globalVerdictRegionDistribution)
			throws MessageException {

		logger.debug("Entered GlobalVerdictRegionDistributionService create method");

		if (globalVerdictRegionDistribution == null) {
			logger.error("globalVerdictRegionDistribution object found null in GlobalVerdictRegionDistributionService.create method");
			throw (new MessageException(
					"globalVerdictRegionDistribution object can't be null"));
		}

		if (globalVerdictRegionDistribution.getRegion() == null) {
			logger.error("Region in globalVerdictRegionDistribution object found null in GlobalVerdictRegionDistributionService.create method");
			throw (new MessageException(
					"Region in globalVerdictRegionDistribution object can't be null"));
		}

		logger.debug("Trying to save the globalVerdictRegionDistribution object for Region = "
				+ globalVerdictRegionDistribution.getRegion().getId());

		globalVerdictRegionDistribution = globalVerdictRegionDistributionRepository
				.save(globalVerdictRegionDistribution);

		logger.debug("Saved globalVerdictRegionDistribution object in the database. Exiting GlobalVerdictRegionDistributionService.create method");

		return globalVerdictRegionDistribution;
	}

	@Override
	public GlobalVerdictRegionDistribution read(Region region)
			throws MessageException {

		logger.debug("Entered GlobalVerdictRegionDistributionService read method");

		if (region == null) {
			logger.error("Region found null in GlobalVerdictRegionDistributionService.read method");
			throw (new MessageException("Region can't be null"));
		}

		logger.debug("Trying to get the GlobalVerdictRegionDistribution object for id = "
				+ region.getId());

		GlobalVerdictRegionDistribution globalVerdictRegionDistribution = globalVerdictRegionDistributionRepository
				.find(region);

		logger.debug("Got GlobalVerdictRegionDistribution object from the database. Exiting GlobalVerdictRegionDistributionService.read method");

		return globalVerdictRegionDistribution;
	}

	@Override
	public List<GlobalVerdictRegionDistribution> readAll() {

		logger.debug("Entered GlobalVerdictRegionDistributionService readAll method");
		logger.debug("Trying to get all the GlobalVerdictRegionDistribution objects from databse");

		List<GlobalVerdictRegionDistribution> globalVerdictRegionDistributions = globalVerdictRegionDistributionRepository
				.findAll();

		logger.debug("Got all the GlobalVerdictRegionDistribution from database. Exiting GlobalVerdictRegionDistributionService.readAll method");

		return globalVerdictRegionDistributions;
	}

	@Override
	public GlobalVerdictRegionDistribution update(
			GlobalVerdictRegionDistribution globalVerdictRegionDistribution)
			throws MessageException {

		logger.debug("Entered GlobalVerdictRegionDistributionService update method");

		if (globalVerdictRegionDistribution == null) {
			logger.error("GlobalVerdictRegionDistribution object found null in GlobalVerdictRegionDistributionService.update method");
			throw (new MessageException(
					"GlobalVerdictRegionDistribution object can't be null"));
		}

		if (globalVerdictRegionDistribution.getRegion() == null) {
			logger.error("Region in GlobalVerdictRegionDistribution object found null in GlobalVerdictRegionDistributionService.update method");
			throw (new MessageException(
					"Region in GlobalVerdictRegionDistribution object can't be null"));
		}

		logger.debug("Trying to update GlobalVerdictRegionDistribution object in databse for Region id = "
				+ globalVerdictRegionDistribution.getRegion().getId());

		globalVerdictRegionDistribution = globalVerdictRegionDistributionRepository
				.update(globalVerdictRegionDistribution);

		logger.debug("Updated GlobalVerdictRegionDistribution in database. Exiting GlobalVerdictRegionDistributionService.update method");

		return globalVerdictRegionDistribution;
	}

	@Override
	public boolean deleteById(Region region) throws MessageException {

		logger.debug("Entered GlobalVerdictRegionDistributionService deleteById method");

		if (region == null) {
			logger.error("Region found null in GlobalVerdictRegionDistributionService.delete method");
			throw (new MessageException("Region can't be null"));
		}

		logger.debug("Trying to get the object for GlobalVerdictRegionDistribution with Region id = "
				+ region.getId());

		boolean status = globalVerdictRegionDistributionRepository
				.deleteById(region);

		logger.debug("Deleted GlobalVerdictRegionDistribution object from database. Exiting GlobalVerdictRegionDistributionService.deleteById method");

		return status;
	}

	@Override
	public boolean delete(
			GlobalVerdictRegionDistribution globalVerdictRegionDistribution)
			throws MessageException {

		logger.debug("Entered GlobalVerdictRegionDistributionService delete method");

		if (globalVerdictRegionDistribution == null) {
			logger.error("GlobalVerdictRegionDistribution object found null in GlobalVerdictRegionDistributionService.delete method");
			throw (new MessageException(
					"GlobalVerdictRegionDistribution object can't be null"));
		}

		if (globalVerdictRegionDistribution.getRegion() == null) {
			logger.error("Region in GlobalVerdictRegionDistribution object found null in GlobalVerdictRegionDistributionService.delete method");
			throw (new MessageException(
					"Region in GlobalVerdictRegionDistribution object can't be null"));
		}

		logger.debug("Trying to get the object for GlobalVerdictRegionDistribution with Region id = "
				+ globalVerdictRegionDistribution.getRegion().getId());

		boolean status = globalVerdictRegionDistributionRepository
				.delete(globalVerdictRegionDistribution);

		logger.debug("Deleted GlobalVerdictRegionDistribution object from database. Exiting GlobalVerdictRegionDistributionService.delete method");

		return status;
	}

}
