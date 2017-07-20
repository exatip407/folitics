package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.businessDelegate.interfaces.IGlobalVerdictDistributionBusinessDelegate;
import com.ohmuk.folitics.constants.Constants;
import com.ohmuk.folitics.enums.OpinionType;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdictAgeDistribution;
import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdictMaritalStatusDistribution;
import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdictQualificationDistribution;
import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdictRegionDistribution;
import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdictReligionDistribution;
import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdictSexDistribution;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.AgeGroup;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.MaritalStatus;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Qualification;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Region;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Religion;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Sex;
import com.ohmuk.folitics.hibernate.service.verdict.global.IGlobalVerdictDistributionService;
import com.ohmuk.folitics.hibernate.service.verdict.lookup.IRegionStateService;
import com.ohmuk.folitics.hibernate.service.verdict.lookup.IVerdictLookupService;
import com.ohmuk.folitics.service.IUserService;
import com.ohmuk.folitics.util.AggregationUtils;

/**
 * BusinessDelegate implementation for Global Verdict Distributions
 * 
 * @author Abhishek
 *
 */
@Component
@Transactional
public class GlobalVerdictDistributionBusinessDelegate implements
		IGlobalVerdictDistributionBusinessDelegate {

	private static Logger logger = LoggerFactory
			.getLogger(GlobalVerdictDistributionBusinessDelegate.class);

	@SuppressWarnings("rawtypes")
	@Autowired
	Map<String, IGlobalVerdictDistributionService> globalVerdictDistributionServiceMap;

	@Autowired
	private volatile IUserService userService;

	@Autowired
	private volatile IVerdictLookupService<AgeGroup> verdictAgeGroupLookupService;

	@Autowired
	private volatile IVerdictLookupService<Sex> verdictSexLookupService;

	@Autowired
	private volatile IVerdictLookupService<MaritalStatus> verdictMaritalStatusLookupService;

	@Autowired
	private volatile IVerdictLookupService<Region> verdictRegionLookupService;

	@Autowired
	private volatile IRegionStateService regionStateRepository;

	@Autowired
	private volatile IVerdictLookupService<Religion> verdictReligionLookupService;

	@Autowired
	private volatile IVerdictLookupService<Qualification> verdictQualificationLookupService;

	@Override
	public void aggregateGlobalVerdictDistribution(String opinionType, User user)
			throws Exception {

		logger.debug("Entered GlobalVerdictDistributionBusinessDelegate aggregateGlobalVerdictDistribution method");

		logger.debug("Input values are : flag = " + opinionType
				+ ", user id = " + user.getId());

		user = userService.findUserById(user.getId());

		GlobalVerdictAgeDistribution globalVerdictAgeDistribution = aggregatGlobalVerdictAgeGroup(
				opinionType, user);

		GlobalVerdictSexDistribution globalVerdictSexDistribution = aggregatGlobalVerdictSex(
				opinionType, user);

		GlobalVerdictMaritalStatusDistribution globalVerdictMaritalStatusDistribution = aggregatGlobalVerdictMaritalStatus(
				opinionType, user);

		GlobalVerdictRegionDistribution globalVerdictRegionDistribution = aggregatGlobalVerdictRegion(
				opinionType, user);

		GlobalVerdictReligionDistribution globalVerdictReligionDistribution = aggregatGlobalVerdictReligion(
				opinionType, user);

		GlobalVerdictQualificationDistribution globalVerdictQualificationDistribution = aggregatGlobalVerdictQualification(
				opinionType, user);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GlobalVerdictAgeDistribution aggregatGlobalVerdictAgeGroup(
			String opinionType, User user) throws MessageException {

		logger.debug("Entered GlobalVerdictDistributionBusinessDelegate aggregatGlobalVerdictAgeGroup method");

		logger.debug("Input values are : flag = " + opinionType
				+ ", user id = " + user.getId());

		logger.debug("Getting age for user based on user's DOB = "
				+ user.getDob());

		int age = AggregationUtils.getAgeForDOB(user.getDob());

		logger.debug("Getting age group for age = " + age);

		AgeGroup ageGroup = verdictAgeGroupLookupService.readByValue(age);

		logger.debug("Got age group object for age = " + age + " having id = "
				+ ageGroup.getId());

		IGlobalVerdictDistributionService globalVerdictDistributionService = globalVerdictDistributionServiceMap
				.get("globalVerdictAgeDistributionService");

		GlobalVerdictAgeDistribution globalVerdictAgeDistribution = (GlobalVerdictAgeDistribution) globalVerdictDistributionService
				.read(ageGroup);

		if (globalVerdictAgeDistribution != null) {

			if (opinionType.equals(OpinionType.PROGOVT.getValue())) {

				globalVerdictAgeDistribution
						.setProCount(globalVerdictAgeDistribution.getProCount()
								+ Constants.OPINION_WEIGHT);

			} else if (opinionType.equals(OpinionType.ANTIGOVT.getValue())) {

				globalVerdictAgeDistribution
						.setAntiCount(globalVerdictAgeDistribution
								.getAntiCount() + Constants.OPINION_WEIGHT);
			}

			globalVerdictAgeDistribution = (GlobalVerdictAgeDistribution) globalVerdictDistributionService
					.update(globalVerdictAgeDistribution);
		} else {

			globalVerdictAgeDistribution = new GlobalVerdictAgeDistribution();
			globalVerdictAgeDistribution.setAgeGroup(ageGroup);

			if (opinionType.equals(OpinionType.PROGOVT.getValue())) {

				globalVerdictAgeDistribution
						.setProCount(Constants.OPINION_WEIGHT);
				globalVerdictAgeDistribution.setAntiCount(0l);

			} else if (opinionType.equals(OpinionType.ANTIGOVT.getValue())) {

				globalVerdictAgeDistribution
						.setAntiCount(Constants.OPINION_WEIGHT);
				globalVerdictAgeDistribution.setProCount(0l);
			}

			globalVerdictAgeDistribution = (GlobalVerdictAgeDistribution) globalVerdictDistributionService
					.create(globalVerdictAgeDistribution);
		}

		logger.debug("Returning GlobalVerdictAgeDistribution object. Exiting from GlobalVerdictDistributionBusinessDelegate aggregatGlobalVerdictAgeGroup method");

		return globalVerdictAgeDistribution;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GlobalVerdictSexDistribution aggregatGlobalVerdictSex(
			String opinionType, User user) throws MessageException {

		logger.debug("Entered GlobalVerdictDistributionBusinessDelegate aggregatGlobalVerdictSex method");

		logger.debug("Input values are : flag = " + opinionType
				+ ", user id = " + user.getId());

		logger.debug("Getting sex object for sex = " + user.getGender());

		Sex sex = verdictSexLookupService.readByValue(user.getGender());

		logger.debug("Got sex object for sex = " + user.getGender()
				+ " having id = " + sex.getId());

		IGlobalVerdictDistributionService globalVerdictDistributionService = globalVerdictDistributionServiceMap
				.get("globalVerdictSexDistributionService");

		GlobalVerdictSexDistribution globalVerdictSexDistribution = (GlobalVerdictSexDistribution) globalVerdictDistributionService
				.read(sex);

		if (globalVerdictSexDistribution != null) {

			if (opinionType.equals(OpinionType.PROGOVT.getValue())) {

				globalVerdictSexDistribution
						.setProCount(globalVerdictSexDistribution.getProCount()
								+ Constants.OPINION_WEIGHT);

			} else if (opinionType.equals(OpinionType.ANTIGOVT.getValue())) {

				globalVerdictSexDistribution
						.setAntiCount(globalVerdictSexDistribution
								.getAntiCount() + Constants.OPINION_WEIGHT);
			}

			globalVerdictSexDistribution = (GlobalVerdictSexDistribution) globalVerdictDistributionService
					.update(globalVerdictSexDistribution);
		} else {

			globalVerdictSexDistribution = new GlobalVerdictSexDistribution();
			globalVerdictSexDistribution.setSex(sex);

			if (opinionType.equals(OpinionType.PROGOVT.getValue())) {

				globalVerdictSexDistribution
						.setProCount(Constants.OPINION_WEIGHT);
				globalVerdictSexDistribution.setAntiCount(0l);

			} else if (opinionType.equals(OpinionType.ANTIGOVT.getValue())) {

				globalVerdictSexDistribution
						.setAntiCount(Constants.OPINION_WEIGHT);
				globalVerdictSexDistribution.setProCount(0l);
			}

			globalVerdictSexDistribution = (GlobalVerdictSexDistribution) globalVerdictDistributionService
					.create(globalVerdictSexDistribution);
		}

		logger.debug("Returning GlobalVerdictSexDistribution object. Exiting from GlobalVerdictDistributionBusinessDelegate aggregatGlobalVerdictSex method");

		return globalVerdictSexDistribution;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GlobalVerdictMaritalStatusDistribution aggregatGlobalVerdictMaritalStatus(
			String opinionType, User user) throws MessageException {

		logger.debug("Entered GlobalVerdictDistributionBusinessDelegate aggregatGlobalVerdictMaritalStatus method");

		logger.debug("Input values are : flag = " + opinionType
				+ ", user id = " + user.getId());

		logger.debug("Getting marital status object for marital status = "
				+ user.getMaritalStatus());

		MaritalStatus maritalStatus = verdictMaritalStatusLookupService
				.readByValue(user.getMaritalStatus());

		logger.debug("Got marital status object for marital status = "
				+ user.getMaritalStatus() + " having id = "
				+ maritalStatus.getId());

		IGlobalVerdictDistributionService globalVerdictDistributionService = globalVerdictDistributionServiceMap
				.get("globalVerdictMaritalStatusDistributionService");

		GlobalVerdictMaritalStatusDistribution globalVerdictMaritalStatusDistribution = (GlobalVerdictMaritalStatusDistribution) globalVerdictDistributionService
				.read(maritalStatus);

		if (globalVerdictMaritalStatusDistribution != null) {

			if (opinionType.equals(OpinionType.PROGOVT.getValue())) {

				globalVerdictMaritalStatusDistribution
						.setProCount(globalVerdictMaritalStatusDistribution
								.getProCount() + Constants.OPINION_WEIGHT);

			} else if (opinionType.equals(OpinionType.ANTIGOVT.getValue())) {

				globalVerdictMaritalStatusDistribution
						.setAntiCount(globalVerdictMaritalStatusDistribution
								.getAntiCount() + Constants.OPINION_WEIGHT);
			}

			globalVerdictMaritalStatusDistribution = (GlobalVerdictMaritalStatusDistribution) globalVerdictDistributionService
					.update(globalVerdictMaritalStatusDistribution);
		} else {

			globalVerdictMaritalStatusDistribution = new GlobalVerdictMaritalStatusDistribution();
			globalVerdictMaritalStatusDistribution
					.setMaritalStatus(maritalStatus);

			if (opinionType.equals(OpinionType.PROGOVT.getValue())) {

				globalVerdictMaritalStatusDistribution
						.setProCount(Constants.OPINION_WEIGHT);
				globalVerdictMaritalStatusDistribution.setAntiCount(0l);

			} else if (opinionType.equals(OpinionType.ANTIGOVT.getValue())) {

				globalVerdictMaritalStatusDistribution
						.setAntiCount(Constants.OPINION_WEIGHT);
				globalVerdictMaritalStatusDistribution.setProCount(0l);
			}

			globalVerdictMaritalStatusDistribution = (GlobalVerdictMaritalStatusDistribution) globalVerdictDistributionService
					.create(globalVerdictMaritalStatusDistribution);
		}

		logger.debug("Returning GlobalVerdictMaritalStatusDistribution object. Exiting from GlobalVerdictDistributionBusinessDelegate aggregatGlobalVerdictMaritalStatus method");

		return globalVerdictMaritalStatusDistribution;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GlobalVerdictRegionDistribution aggregatGlobalVerdictRegion(
			String opinionType, User user) throws MessageException {

		logger.debug("Entered GlobalVerdictDistributionBusinessDelegate aggregatGlobalVerdictRegion method");

		logger.debug("Input values are : flag = " + opinionType
				+ ", user id = " + user.getId());

		logger.debug("Getting region object for state = " + user.getState());

		Region region = regionStateRepository
				.getRegionForState(user.getState());

		logger.debug("Got region object for state = " + user.getState()
				+ " having id = " + region.getId());

		IGlobalVerdictDistributionService globalVerdictDistributionService = globalVerdictDistributionServiceMap
				.get("globalVerdictRegionDistributionService");

		GlobalVerdictRegionDistribution globalVerdictRegionDistribution = (GlobalVerdictRegionDistribution) globalVerdictDistributionService
				.read(region);

		if (globalVerdictRegionDistribution != null) {

			if (opinionType.equals(OpinionType.PROGOVT.getValue())) {

				globalVerdictRegionDistribution
						.setProCount(globalVerdictRegionDistribution
								.getProCount() + Constants.OPINION_WEIGHT);

			} else if (opinionType.equals(OpinionType.ANTIGOVT.getValue())) {

				globalVerdictRegionDistribution
						.setAntiCount(globalVerdictRegionDistribution
								.getAntiCount() + Constants.OPINION_WEIGHT);
			}

			globalVerdictRegionDistribution = (GlobalVerdictRegionDistribution) globalVerdictDistributionService
					.update(globalVerdictRegionDistribution);
		} else {

			globalVerdictRegionDistribution = new GlobalVerdictRegionDistribution();
			globalVerdictRegionDistribution.setRegion(region);

			if (opinionType.equals(OpinionType.PROGOVT.getValue())) {

				globalVerdictRegionDistribution
						.setProCount(Constants.OPINION_WEIGHT);
				globalVerdictRegionDistribution.setAntiCount(0l);

			} else if (opinionType.equals(OpinionType.ANTIGOVT.getValue())) {

				globalVerdictRegionDistribution
						.setAntiCount(Constants.OPINION_WEIGHT);
				globalVerdictRegionDistribution.setProCount(0l);
			}

			globalVerdictRegionDistribution = (GlobalVerdictRegionDistribution) globalVerdictDistributionService
					.create(globalVerdictRegionDistribution);
		}

		logger.debug("Returning GlobalVerdictRegionDistribution object. Exiting from GlobalVerdictDistributionBusinessDelegate aggregatGlobalVerdictRegion method");

		return globalVerdictRegionDistribution;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GlobalVerdictReligionDistribution aggregatGlobalVerdictReligion(
			String opinionType, User user) throws MessageException {

		logger.debug("Entered GlobalVerdictDistributionBusinessDelegate aggregatGlobalVerdictReligion method");

		logger.debug("Input values are : flag = " + opinionType
				+ ", user id = " + user.getId());

		logger.debug("Getting religion object for religion = "
				+ user.getReligion());

		Religion religion = verdictReligionLookupService.readByValue(user
				.getReligion());

		logger.debug("Got religion object for religion = " + user.getReligion()
				+ " having id = " + religion.getId());

		IGlobalVerdictDistributionService globalVerdictDistributionService = globalVerdictDistributionServiceMap
				.get("globalVerdictReligionDistributionService");

		GlobalVerdictReligionDistribution globalVerdictReligionDistribution = (GlobalVerdictReligionDistribution) globalVerdictDistributionService
				.read(religion);

		if (globalVerdictReligionDistribution != null) {

			if (opinionType.equals(OpinionType.PROGOVT.getValue())) {

				globalVerdictReligionDistribution
						.setProCount(globalVerdictReligionDistribution
								.getProCount() + Constants.OPINION_WEIGHT);

			} else if (opinionType.equals(OpinionType.ANTIGOVT.getValue())) {

				globalVerdictReligionDistribution
						.setAntiCount(globalVerdictReligionDistribution
								.getAntiCount() + Constants.OPINION_WEIGHT);
			}

			globalVerdictReligionDistribution = (GlobalVerdictReligionDistribution) globalVerdictDistributionService
					.update(globalVerdictReligionDistribution);
		} else {

			globalVerdictReligionDistribution = new GlobalVerdictReligionDistribution();
			globalVerdictReligionDistribution.setReligion(religion);

			if (opinionType.equals(OpinionType.PROGOVT.getValue())) {

				globalVerdictReligionDistribution
						.setProCount(Constants.OPINION_WEIGHT);
				globalVerdictReligionDistribution.setAntiCount(0l);

			} else if (opinionType.equals(OpinionType.ANTIGOVT.getValue())) {

				globalVerdictReligionDistribution
						.setAntiCount(Constants.OPINION_WEIGHT);
				globalVerdictReligionDistribution.setProCount(0l);
			}

			globalVerdictReligionDistribution = (GlobalVerdictReligionDistribution) globalVerdictDistributionService
					.create(globalVerdictReligionDistribution);
		}

		logger.debug("Returning GlobalVerdictReligionDistribution object. Exiting from GlobalVerdictDistributionBusinessDelegate aggregatGlobalVerdictReligion method");

		return globalVerdictReligionDistribution;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GlobalVerdictQualificationDistribution aggregatGlobalVerdictQualification(
			String opinionType, User user) throws Exception {

		logger.debug("Entered GlobalVerdictDistributionBusinessDelegate aggregatGlobalVerdictQualification method");

		logger.debug("Input values are : flag = " + opinionType
				+ ", user id = " + user.getId());

		user = userService.findUserById(user.getId());

		logger.debug("Getting highest education for user");
		Hibernate.initialize(user.getUserEducation());
		String highestEducation = AggregationUtils.getHighestEducation(user
				.getUserEducation());

		logger.debug("Getting qualification object for qualification = "
				+ highestEducation);

		Qualification qualification = verdictQualificationLookupService
				.readByValue(highestEducation);

		logger.debug("Got qualification object for qualification = "
				+ highestEducation + " having id = " + qualification.getId());

		IGlobalVerdictDistributionService globalVerdictDistributionService = globalVerdictDistributionServiceMap
				.get("globalVerdictQualificationDistributionService");

		GlobalVerdictQualificationDistribution globalVerdictQualificationDistribution = (GlobalVerdictQualificationDistribution) globalVerdictDistributionService
				.read(qualification);

		if (globalVerdictQualificationDistribution != null) {

			if (opinionType.equals(OpinionType.PROGOVT.getValue())) {

				globalVerdictQualificationDistribution
						.setProCount(globalVerdictQualificationDistribution
								.getProCount() + Constants.OPINION_WEIGHT);

			} else if (opinionType.equals(OpinionType.ANTIGOVT.getValue())) {

				globalVerdictQualificationDistribution
						.setAntiCount(globalVerdictQualificationDistribution
								.getAntiCount() + Constants.OPINION_WEIGHT);
			}

			globalVerdictQualificationDistribution = (GlobalVerdictQualificationDistribution) globalVerdictDistributionService
					.update(globalVerdictQualificationDistribution);
		} else {

			globalVerdictQualificationDistribution = new GlobalVerdictQualificationDistribution();
			globalVerdictQualificationDistribution
					.setQualification(qualification);

			if (opinionType.equals(OpinionType.PROGOVT.getValue())) {

				globalVerdictQualificationDistribution
						.setProCount(Constants.OPINION_WEIGHT);
				globalVerdictQualificationDistribution.setAntiCount(0l);

			} else if (opinionType.equals(OpinionType.ANTIGOVT.getValue())) {

				globalVerdictQualificationDistribution
						.setAntiCount(Constants.OPINION_WEIGHT);
				globalVerdictQualificationDistribution.setProCount(0l);
			}

			globalVerdictQualificationDistribution = (GlobalVerdictQualificationDistribution) globalVerdictDistributionService
					.create(globalVerdictQualificationDistribution);
		}

		logger.debug("Returning GlobalVerdictQualificationDistribution object. Exiting from GlobalVerdictDistributionBusinessDelegate aggregatGlobalVerdictQualification method");

		return globalVerdictQualificationDistribution;
	}
}
