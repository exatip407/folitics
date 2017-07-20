package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.businessDelegate.interfaces.IGlobalVerdictBusinessDelegate;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdict;
import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdictAgeDistribution;
import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdictMaritalStatusDistribution;
import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdictQualificationDistribution;
import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdictRegionDistribution;
import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdictReligionDistribution;
import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdictSexDistribution;
import com.ohmuk.folitics.hibernate.service.verdict.global.IGlobalVerdictDistributionService;
import com.ohmuk.folitics.hibernate.service.verdict.global.IGlobalVerdictService;

/**
 * BusinessDelegate implementation for {@link GlobalVerdict}
 * 
 * @author Abhishek
 *
 */
@Component
public class GlobalVerdictBusinessDelegate implements
		IGlobalVerdictBusinessDelegate {

	private static Logger logger = LoggerFactory
			.getLogger(GlobalVerdictBusinessDelegate.class);

	@Autowired
	private volatile IGlobalVerdictService service;

	@SuppressWarnings("rawtypes")
	@Autowired
	Map<String, IGlobalVerdictDistributionService> globalVerdictDistributionServiceMap;

	@Override
	public GlobalVerdict create(GlobalVerdict globalVerdict)
			throws MessageException {

		logger.debug("Entered GlobalVerdictBusinessDelegate create method");
		logger.debug("Trying to save the GlobalVerdict object");

		globalVerdict = service.create(globalVerdict);

		logger.debug("Saved GlobalVerdict object in database. Returning object and exiting GlobalVerdictBusinessDelegate create method");

		return globalVerdict;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GlobalVerdict read() {

		logger.debug("Entered GlobalVerdictBusinessDelegate readAll method");
		logger.debug("Trying to get the GlobalVerdict object");

		GlobalVerdict globalVerdict = service.read();

		@SuppressWarnings("rawtypes")
		IGlobalVerdictDistributionService globalVerdictDistributionService;

		globalVerdictDistributionService = globalVerdictDistributionServiceMap
				.get("globalVerdictAgeDistributionService");

		List<GlobalVerdictAgeDistribution> globalVerdictAgeDistributions = globalVerdictDistributionService
				.readAll();

		globalVerdictDistributionService = globalVerdictDistributionServiceMap
				.get("globalVerdictSexDistributionService");

		List<GlobalVerdictSexDistribution> globalVerdictSexDistributions = globalVerdictDistributionService
				.readAll();

		globalVerdictDistributionService = globalVerdictDistributionServiceMap
				.get("globalVerdictMaritalStatusDistributionService");

		List<GlobalVerdictMaritalStatusDistribution> globalVerdictMaritalStatusDistributions = globalVerdictDistributionService
				.readAll();

		globalVerdictDistributionService = globalVerdictDistributionServiceMap
				.get("globalVerdictRegionDistributionService");

		List<GlobalVerdictRegionDistribution> globalVerdictRegionDistributions = globalVerdictDistributionService
				.readAll();

		globalVerdictDistributionService = globalVerdictDistributionServiceMap
				.get("globalVerdictReligionDistributionService");

		List<GlobalVerdictReligionDistribution> globalVerdictReligionDistributions = globalVerdictDistributionService
				.readAll();

		globalVerdictDistributionService = globalVerdictDistributionServiceMap
				.get("globalVerdictQualificationDistributionService");

		List<GlobalVerdictQualificationDistribution> globalVerdictQualificationDistributions = globalVerdictDistributionService
				.readAll();

		globalVerdict
				.setGlobalVerdictAgeDistributions(new HashSet<GlobalVerdictAgeDistribution>(
						globalVerdictAgeDistributions));

		globalVerdict
				.setGlobalVerdictSexDistributions(new HashSet<GlobalVerdictSexDistribution>(
						globalVerdictSexDistributions));

		globalVerdict
				.setGlobalVerdictMaritalStatusDistributions(new HashSet<GlobalVerdictMaritalStatusDistribution>(
						globalVerdictMaritalStatusDistributions));

		globalVerdict
				.setGlobalVerdictRegionDistributions(new HashSet<GlobalVerdictRegionDistribution>(
						globalVerdictRegionDistributions));

		globalVerdict
				.setGlobalVerdictReligionDistributions(new HashSet<GlobalVerdictReligionDistribution>(
						globalVerdictReligionDistributions));

		globalVerdict
				.setGlobalVerdictQualificationDistributions(new HashSet<GlobalVerdictQualificationDistribution>(
						globalVerdictQualificationDistributions));

		logger.debug("Got GlobalVerdict object from database. Returning object and exiting GlobalVerdictBusinessDelegate read method");

		return globalVerdict;
	}

	@Override
	public GlobalVerdict update(GlobalVerdict globalVerdict)
			throws MessageException {

		logger.debug("Entered GlobalVerdictBusinessDelegate readAll method");
		logger.debug("Trying to update GlobalVerdict object with id = "
				+ globalVerdict.getId());

		globalVerdict = service.update(globalVerdict);

		logger.debug("Updated GlobalVerdict object in database. Exiting GlobalVerdictBusinessDelegate update method.");

		return globalVerdict;
	}

	@Override
	public boolean delete(GlobalVerdict globalVerdict) throws MessageException {

		logger.debug("Entered GlobalVerdictBusinessDelegate delete method");
		logger.debug("Trying to delete GlobalVerdict object with id = "
				+ globalVerdict.getId());

		boolean status = service.delete(globalVerdict);

		logger.debug("Deleted GlobalVerdict object in database. Exiting GlobalVerdictBusinessDelegate delete method.");

		return status;
	}

	@Override
	public boolean delete(Long id) throws MessageException {

		logger.debug("Entered GlobalVerdictBusinessDelegate delete method");
		logger.debug("Trying to delete GlobalVerdict object with id = " + id);
		boolean status = service.delete(id);

		logger.debug("Deleted GlobalVerdict object in database. Exiting GlobalVerdictBusinessDelegate delete method.");

		return status;
	}

}
