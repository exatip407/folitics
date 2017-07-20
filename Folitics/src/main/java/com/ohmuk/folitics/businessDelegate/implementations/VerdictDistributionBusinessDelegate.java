package com.ohmuk.folitics.businessDelegate.implementations;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.businessDelegate.interfaces.IVerdictDistributionBusinessDelegate;
import com.ohmuk.folitics.businessDelegate.interfaces.IVerdictHeadlineDataBusinessDelegate;
import com.ohmuk.folitics.enums.OpinionType;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Sentiment;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.hibernate.entity.verdict.VerdictHeadlineData;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictAgeGroup;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictAgeGroupId;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictMaritalStatus;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictMaritalStatusId;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictQualification;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictQualificationId;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictRegion;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictRegionId;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictReligion;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictReligionId;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictSex;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictSexId;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.AgeGroup;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.MaritalStatus;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Qualification;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Region;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Religion;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Sex;
import com.ohmuk.folitics.hibernate.service.verdict.IVerdictService;
import com.ohmuk.folitics.hibernate.service.verdict.distribution.IVerdictDistributionService;
import com.ohmuk.folitics.hibernate.service.verdict.lookup.IRegionStateService;
import com.ohmuk.folitics.hibernate.service.verdict.lookup.IVerdictLookupService;
import com.ohmuk.folitics.service.IUserService;
import com.ohmuk.folitics.util.AggregationUtils;

/**
 * BusinessDelegate implementation for {@link Verdict} distributions
 * 
 * @author Abhishek
 *
 */
@Component
@Transactional
public class VerdictDistributionBusinessDelegate implements
		IVerdictDistributionBusinessDelegate {

	private static Logger logger = LoggerFactory
			.getLogger(VerdictDistributionBusinessDelegate.class);

	@Autowired
	private volatile IVerdictService verdictService;

	@Autowired
	private volatile IVerdictDistributionService<VerdictAgeGroup, VerdictAgeGroupId> verdictAgeGroupService;

	@Autowired
	private volatile IVerdictLookupService<AgeGroup> verdictAgeGroupLookupService;

	@Autowired
	private volatile IVerdictDistributionService<VerdictSex, VerdictSexId> verdictSexService;

	@Autowired
	private volatile IVerdictLookupService<Sex> verdictSexLookupService;

	@Autowired
	private volatile IVerdictDistributionService<VerdictMaritalStatus, VerdictMaritalStatusId> verdictMaritalStatusService;

	@Autowired
	private volatile IVerdictLookupService<MaritalStatus> verdictMaritalStatusLookupService;

	@Autowired
	private volatile IVerdictDistributionService<VerdictRegion, VerdictRegionId> verdictRegionService;

	@Autowired
	private volatile IVerdictLookupService<Region> verdictRegionLookupService;

	@Autowired
	private volatile IVerdictDistributionService<VerdictReligion, VerdictReligionId> verdictReligionService;

	@Autowired
	private volatile IVerdictLookupService<Religion> verdictReligionLookupService;

	@Autowired
	private volatile IVerdictDistributionService<VerdictQualification, VerdictQualificationId> verdictQualificationService;

	@Autowired
	private volatile IVerdictLookupService<Qualification> verdictQualificationLookupService;

	@Autowired
	private volatile IUserService userService;

	@Autowired
	private volatile IRegionStateService regionStateRepository;

	@Autowired
	private volatile IVerdictHeadlineDataBusinessDelegate verdictHeadlineDataBusinessDelegate;

	@Override
	public Verdict create(Long sentimentId, String flag, User user)
			throws Exception {

		logger.debug("Entered VerdictDistributionBusinessDelegate create method");

		logger.debug("Input values are : sentiment id = " + sentimentId
				+ ", flag = " + flag + ", user id = " + user.getId());

		logger.debug("Getting user object for user id = " + user.getId());

		user = userService.findUserById(user.getId());

		logger.debug("Checking whether verdict object exists for sentiment id = "
				+ sentimentId);

		Verdict verdict = verdictService.getVerdictForSentiment(sentimentId);

		if (verdict == null) {

			logger.debug("Verdict object found null that means verdict does not exist yet in the database");

			Sentiment sentiment = new Sentiment();
			sentiment.setId(sentimentId);

			logger.debug("Creating new entry for verdict for sentiment id = "
					+ sentimentId);
			verdict = new Verdict();
			verdict.setSentiment(sentiment);

			logger.debug("Saving the object for verdict in database for sentiment id = "
					+ sentimentId);

			verdict = verdictService.create(verdict);

		} else {

			logger.debug("Verdict already exists in the database for sentiment id = "
					+ sentimentId);
		}

		logger.debug("Aggregating the verdict for age group");

		VerdictAgeGroup verdictAgeGroup = aggregatVerdictAgeGroup(verdict,
				flag, user);

		logger.debug("Aggregated verdict for age group");
		logger.debug("Aggregating the verdict for sex");

		VerdictSex verdictSex = aggregatVerdictSex(verdict, flag, user);

		logger.debug("Aggregated verdict for sex");
		logger.debug("Aggregating the verdict for marital status");

		VerdictMaritalStatus verdictMaritalStatus = aggregatVerdictMaritalStatus(
				verdict, flag, user);

		logger.debug("Aggregated verdict for marital status");
		logger.debug("Aggregating the verdict for region");

		VerdictRegion verdictRegion = aggregatVerdictRegion(verdict, flag, user);

		logger.debug("Aggregated verdict for region");
		logger.debug("Aggregating the verdict for religion");

		VerdictReligion verdictReligion = aggregatVerdictReligion(verdict,
				flag, user);

		logger.debug("Aggregated verdict for religion");
		logger.debug("Aggregating the verdict for qualification");
		VerdictQualification verdictQualification = aggregatVerdictQualification(
				verdict, flag, user);

		logger.debug("Aggregated verdict for qualification");

		logger.debug("Getting the verdict object with verdict id = "
				+ verdict.getId());

		verdict = verdictService.read(verdict.getId());

		VerdictHeadlineData verdictHeadlineData = new VerdictHeadlineData();

		verdictHeadlineData.setVerdictId(verdict);
		verdictHeadlineData.setAgegroup(verdictAgeGroup.getId().getAgeGroup());
		verdictHeadlineData.setSex(verdictSex.getId().getSex());
		verdictHeadlineData.setMaritalstatus(verdictMaritalStatus.getId()
				.getMaritalStatus());
		verdictHeadlineData.setRegion(verdictRegion.getId().getRegion());
		verdictHeadlineData.setReligion(verdictReligion.getId().getReligion());
		verdictHeadlineData.setQualification(verdictQualification.getId()
				.getQualification());

		VerdictHeadlineData verdictHeadlineDataFromDB = verdictHeadlineDataBusinessDelegate
				.readVerdictHeadlineDataForParameters(verdictHeadlineData);

		if (verdictHeadlineDataFromDB != null) {

			verdictHeadlineData.setId(verdictHeadlineDataFromDB.getId());

			if (flag.equals(OpinionType.PROGOVT.getValue())) {

				verdictHeadlineData.setProCount(verdictHeadlineDataFromDB
						.getProCount() + 1l);
				verdictHeadlineData.setAntiCount(verdictHeadlineDataFromDB
						.getAntiCount());

			} else if (flag.equals(OpinionType.ANTIGOVT.getValue())) {

				verdictHeadlineData.setAntiCount(verdictHeadlineDataFromDB
						.getAntiCount() + 1l);
				verdictHeadlineData.setProCount(verdictHeadlineDataFromDB
						.getProCount());
			}

			verdictHeadlineDataBusinessDelegate.update(verdictHeadlineData);

		} else {

			if (flag.equals(OpinionType.PROGOVT.getValue())) {

				verdictHeadlineData.setProCount(1l);
				verdictHeadlineData.setAntiCount(0l);

			} else if (flag.equals(OpinionType.ANTIGOVT.getValue())) {

				verdictHeadlineData.setAntiCount(1l);
				verdictHeadlineData.setProCount(0l);
			}

			verdictHeadlineDataBusinessDelegate.create(verdictHeadlineData);
		}

		logger.debug("Got verdict object with verdict id = " + verdict.getId()
				+ ". Exiting VerdictDistributionBusinessDelegate create method");

		return verdict;
	}

	@Override
	public Verdict read(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VerdictAgeGroup aggregatVerdictAgeGroup(Verdict verdict,
			String flag, User user) throws MessageException {

		logger.debug("Entered VerdictDistributionBusinessDelegate aggregatVerdictAgeGroup method");

		logger.debug("Input values are : verdict id = " + verdict.getId()
				+ ", flag = " + flag + ", user id = " + user.getId());

		logger.debug("Getting age for user based on user's DOB = "
				+ user.getDob());

		int age = AggregationUtils.getAgeForDOB(user.getDob());

		logger.debug("Getting age group for age = " + age);

		AgeGroup ageGroup = verdictAgeGroupLookupService.readByValue(age);

		logger.debug("Got age group object for age = " + age + " having id = "
				+ ageGroup.getId());

		VerdictAgeGroupId verdictAgeGroupId = new VerdictAgeGroupId();
		verdictAgeGroupId.setVerdict(verdict);
		verdictAgeGroupId.setAgeGroup(ageGroup);

		logger.debug("Getting VerdictAgeGroup object for verdict id = "
				+ verdict.getId() + " and age group = " + ageGroup.getId());

		VerdictAgeGroup verdictAgeGroup = verdictAgeGroupService
				.getDistribution(verdictAgeGroupId);

		if (verdictAgeGroup == null) {

			logger.debug("VerdictAgeGroup object found null. Initialising new VerdictAgeGroup object");

			verdictAgeGroup = new VerdictAgeGroup();
			verdictAgeGroup.setId(verdictAgeGroupId);

			logger.debug("Flag = " + flag + ". So setting " + flag
					+ " count = 1");

			if (flag.equals(OpinionType.PROGOVT.getValue())) {

				verdictAgeGroup.setProCount(1l);
				verdictAgeGroup.setAntiCount(0l);

			} else if (flag.equals(OpinionType.ANTIGOVT.getValue())) {

				verdictAgeGroup.setProCount(0l);
				verdictAgeGroup.setAntiCount(1l);
			}

			logger.debug("Trying to save VerdictAgeGroup object into database.");

			verdictAgeGroup = verdictAgeGroupService
					.addDistribution(verdictAgeGroup);

			logger.debug("Saved VerdictAgeGroup object in database");

		} else {

			logger.debug("VerdictAgeGroup object is not null.");

			logger.debug("Flag = " + flag + ". So incrementing " + flag
					+ " count by 1");

			if (flag.equals(OpinionType.PROGOVT.getValue())) {

				verdictAgeGroup.setProCount(verdictAgeGroup.getProCount() + 1l);

			} else if (flag.equals(OpinionType.ANTIGOVT.getValue())) {

				verdictAgeGroup
						.setAntiCount(verdictAgeGroup.getAntiCount() + 1l);
			}

			logger.debug("Trying to update VerdictAgeGroup object in database.");

			verdictAgeGroup = verdictAgeGroupService
					.updateDistribution(verdictAgeGroup);

			logger.debug("Updated VerdictAgeGroup object in database");
		}

		logger.debug("Returning VerdictAgeGroup object. Exiting from VerdictDistributionBusinessDelegate aggregatVerdictAgeGroup method");

		return verdictAgeGroup;
	}

	@Override
	public VerdictSex aggregatVerdictSex(Verdict verdict, String flag, User user)
			throws MessageException {

		logger.debug("Entered VerdictDistributionBusinessDelegate aggregatVerdictSex method");

		logger.debug("Input values are : verdict id = " + verdict.getId()
				+ ", flag = " + flag + ", user id = " + user.getId());

		logger.debug("Getting sex object for sex = " + user.getGender());

		Sex sex = verdictSexLookupService.readByValue(user.getGender());

		logger.debug("Got sex object for sex = " + user.getGender()
				+ " having id = " + sex.getId());

		VerdictSexId verdictSexId = new VerdictSexId();
		verdictSexId.setVerdict(verdict);
		verdictSexId.setSex(sex);

		logger.debug("Getting VerdictSex object for verdict id = "
				+ verdict.getId() + " and sex = " + sex.getId());

		VerdictSex verdictSex = verdictSexService.getDistribution(verdictSexId);

		if (verdictSex == null) {

			logger.debug("VerdictSex object found null. Initialising new VerdictSex object");

			verdictSex = new VerdictSex();
			verdictSex.setId(verdictSexId);

			logger.debug("Flag = " + flag + ". So setting " + flag
					+ " count = 1");

			if (flag.equals(OpinionType.PROGOVT.getValue())) {

				verdictSex.setProCount(1l);
				verdictSex.setAntiCount(0l);

			} else if (flag.equals(OpinionType.ANTIGOVT.getValue())) {

				verdictSex.setProCount(0l);
				verdictSex.setAntiCount(1l);
			}

			logger.debug("Trying to save VerdictSex object into database.");

			verdictSex = verdictSexService.addDistribution(verdictSex);

			logger.debug("Saved VerdictSex object in database");

		} else {

			logger.debug("VerdictSex object is not null.");

			logger.debug("Flag = " + flag + ". So incrementing " + flag
					+ " count by 1");

			if (flag.equals(OpinionType.PROGOVT.getValue())) {

				verdictSex.setProCount(verdictSex.getProCount() + 1l);

			} else if (flag.equals(OpinionType.ANTIGOVT.getValue())) {

				verdictSex.setAntiCount(verdictSex.getAntiCount() + 1l);
			}

			logger.debug("Trying to update VerdictSex object in database.");

			verdictSex = verdictSexService.updateDistribution(verdictSex);

			logger.debug("Updated VerdictSex object in database");
		}

		logger.debug("Returning VerdictSex object. Exiting from VerdictDistributionBusinessDelegate aggregatVerdictSex method");

		return verdictSex;
	}

	@Override
	public VerdictMaritalStatus aggregatVerdictMaritalStatus(Verdict verdict,
			String flag, User user) throws MessageException {

		logger.debug("Entered VerdictDistributionBusinessDelegate aggregatVerdictMaritalStatus method");

		logger.debug("Input values are : verdict id = " + verdict.getId()
				+ ", flag = " + flag + ", user id = " + user.getId());

		logger.debug("Getting marital status object for marital status = "
				+ user.getMaritalStatus());

		MaritalStatus maritalStatus = verdictMaritalStatusLookupService
				.readByValue(user.getMaritalStatus());

		logger.debug("Got marital status object for marital status = "
				+ user.getMaritalStatus() + " having id = "
				+ maritalStatus.getId());

		VerdictMaritalStatusId verdictMaritalStatusId = new VerdictMaritalStatusId();
		verdictMaritalStatusId.setVerdict(verdict);
		verdictMaritalStatusId.setMaritalStatus(maritalStatus);

		logger.debug("Getting VerdictMaritalStatus object for verdict id = "
				+ verdict.getId() + " and marital status = "
				+ maritalStatus.getId());

		VerdictMaritalStatus verdictMaritalStatus = verdictMaritalStatusService
				.getDistribution(verdictMaritalStatusId);

		if (verdictMaritalStatus == null) {

			logger.debug("VerdictMaritalStatus object found null. Initialising new VerdictMaritalStatus object");

			verdictMaritalStatus = new VerdictMaritalStatus();
			verdictMaritalStatus.setId(verdictMaritalStatusId);

			logger.debug("Flag = " + flag + ". So setting " + flag
					+ " count = 1");

			if (flag.equals(OpinionType.PROGOVT.getValue())) {

				verdictMaritalStatus.setProCount(1l);
				verdictMaritalStatus.setAntiCount(0l);

			} else if (flag.equals(OpinionType.ANTIGOVT.getValue())) {

				verdictMaritalStatus.setProCount(0l);
				verdictMaritalStatus.setAntiCount(1l);
			}

			logger.debug("Trying to save VerdictMaritalStatus object into database.");

			verdictMaritalStatus = verdictMaritalStatusService
					.addDistribution(verdictMaritalStatus);

			logger.debug("Saved VerdictMaritalStatus object in database");

		} else {

			logger.debug("VerdictMaritalStatus object is not null.");

			logger.debug("Flag = " + flag + ". So incrementing " + flag
					+ " count by 1");

			if (flag.equals(OpinionType.PROGOVT.getValue())) {

				verdictMaritalStatus.setProCount(verdictMaritalStatus
						.getProCount() + 1l);

			} else if (flag.equals(OpinionType.ANTIGOVT.getValue())) {

				verdictMaritalStatus.setAntiCount(verdictMaritalStatus
						.getAntiCount() + 1l);
			}

			logger.debug("Trying to update VerdictMaritalStatus object in database.");

			verdictMaritalStatus = verdictMaritalStatusService
					.updateDistribution(verdictMaritalStatus);

			logger.debug("Updated VerdictMaritalStatus object in database");
		}

		logger.debug("Returning VerdictMaritalStatus object. Exiting from VerdictDistributionBusinessDelegate aggregatVerdictMaritalStatus method");

		return verdictMaritalStatus;
	}

	@Override
	public VerdictRegion aggregatVerdictRegion(Verdict verdict, String flag,
			User user) throws MessageException {

		logger.debug("Entered VerdictDistributionBusinessDelegate aggregatVerdictRegion method");

		logger.debug("Input values are : verdict id = " + verdict.getId()
				+ ", flag = " + flag + ", user id = " + user.getId());

		logger.debug("Getting region object for state = " + user.getState());

		Region region = regionStateRepository
				.getRegionForState(user.getState());

		logger.debug("Got region object for state = " + user.getState()
				+ " having id = " + region.getId());

		VerdictRegionId verdictRegionId = new VerdictRegionId();
		verdictRegionId.setVerdict(verdict);
		verdictRegionId.setRegion(region);

		logger.debug("Getting VerdictRegion object for verdict id = "
				+ verdict.getId() + " and region = " + region.getId());

		VerdictRegion verdictRegion = verdictRegionService
				.getDistribution(verdictRegionId);

		if (verdictRegion == null) {

			logger.debug("VerdictRegion object found null. Initialising new VerdictRegion object");

			verdictRegion = new VerdictRegion();
			verdictRegion.setId(verdictRegionId);

			logger.debug("Flag = " + flag + ". So setting " + flag
					+ " count = 1");

			if (flag.equals(OpinionType.PROGOVT.getValue())) {

				verdictRegion.setProCount(1l);
				verdictRegion.setAntiCount(0l);

			} else if (flag.equals(OpinionType.ANTIGOVT.getValue())) {

				verdictRegion.setProCount(0l);
				verdictRegion.setAntiCount(1l);
			}

			logger.debug("Trying to save VerdictRegion object into database.");

			verdictRegion = verdictRegionService.addDistribution(verdictRegion);

			logger.debug("Saved VerdictRegion object in database");

		} else {

			logger.debug("VerdictRegion object is not null.");

			logger.debug("Flag = " + flag + ". So incrementing " + flag
					+ " count by 1");

			if (flag.equals(OpinionType.PROGOVT.getValue())) {

				verdictRegion.setProCount(verdictRegion.getProCount() + 1l);

			} else if (flag.equals(OpinionType.ANTIGOVT.getValue())) {

				verdictRegion.setAntiCount(verdictRegion.getAntiCount() + 1l);
			}

			logger.debug("Trying to update VerdictRegion object in database.");

			verdictRegion = verdictRegionService
					.updateDistribution(verdictRegion);

			logger.debug("Updated VerdictRegion object in database");
		}

		logger.debug("Returning VerdictRegion object. Exiting from VerdictDistributionBusinessDelegate aggregatVerdictRegion method");

		return verdictRegion;
	}

	@Override
	public VerdictReligion aggregatVerdictReligion(Verdict verdict,
			String flag, User user) throws MessageException {

		logger.debug("Entered VerdictDistributionBusinessDelegate aggregatVerdictReligion method");

		logger.debug("Input values are : verdict id = " + verdict.getId()
				+ ", flag = " + flag + ", user id = " + user.getId());

		logger.debug("Getting religion object for religion = "
				+ user.getReligion());

		Religion religion = verdictReligionLookupService.readByValue(user
				.getReligion());

		logger.debug("Got religion object for religion = " + user.getReligion()
				+ " having id = " + religion.getId());

		VerdictReligionId verdictReligionId = new VerdictReligionId();
		verdictReligionId.setVerdict(verdict);
		verdictReligionId.setReligion(religion);

		logger.debug("Getting VerdictReligion object for verdict id = "
				+ verdict.getId() + " and religion = " + religion.getId());

		VerdictReligion verdictReligion = verdictReligionService
				.getDistribution(verdictReligionId);

		if (verdictReligion == null) {

			logger.debug("VerdictReligion object found null. Initialising new VerdictReligion object");

			verdictReligion = new VerdictReligion();
			verdictReligion.setId(verdictReligionId);

			logger.debug("Flag = " + flag + ". So setting " + flag
					+ " count = 1");

			if (flag.equals(OpinionType.PROGOVT.getValue())) {

				verdictReligion.setProCount(1l);
				verdictReligion.setAntiCount(0l);

			} else if (flag.equals(OpinionType.ANTIGOVT.getValue())) {

				verdictReligion.setProCount(0l);
				verdictReligion.setAntiCount(1l);
			}

			logger.debug("Trying to save VerdictReligion object into database.");

			verdictReligion = verdictReligionService
					.addDistribution(verdictReligion);

			logger.debug("Saved VerdictReligion object in database");

		} else {

			logger.debug("VerdictReligion object is not null.");

			logger.debug("Flag = " + flag + ". So incrementing " + flag
					+ " count by 1");

			if (flag.equals(OpinionType.PROGOVT.getValue())) {

				verdictReligion.setProCount(verdictReligion.getProCount() + 1l);

			} else if (flag.equals(OpinionType.ANTIGOVT.getValue())) {

				verdictReligion
						.setAntiCount(verdictReligion.getAntiCount() + 1l);
			}

			logger.debug("Trying to update VerdictReligion object in database.");

			verdictReligion = verdictReligionService
					.updateDistribution(verdictReligion);

			logger.debug("Updated VerdictReligion object in database");
		}

		logger.debug("Returning VerdictReligion object. Exiting from VerdictDistributionBusinessDelegate aggregatVerdictReligion method");

		return verdictReligion;
	}

	@Override
	public VerdictQualification aggregatVerdictQualification(Verdict verdict,
			String flag, User user) throws MessageException {

		logger.debug("Entered VerdictDistributionBusinessDelegate aggregatVerdictQualification method");

		logger.debug("Input values are : verdict id = " + verdict.getId()
				+ ", flag = " + flag + ", user id = " + user.getId());

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

		VerdictQualificationId verdictQualificationId = new VerdictQualificationId();
		verdictQualificationId.setVerdict(verdict);
		verdictQualificationId.setQualification(qualification);

		logger.debug("Getting VerdictQualification object for verdict id = "
				+ verdict.getId() + " and qualification = "
				+ qualification.getId());

		VerdictQualification verdictQualification = verdictQualificationService
				.getDistribution(verdictQualificationId);

		if (verdictQualification == null) {

			logger.debug("VerdictQualification object found null. Initialising new VerdictQualification object");

			verdictQualification = new VerdictQualification();
			verdictQualification.setId(verdictQualificationId);

			logger.debug("Flag = " + flag + ". So setting " + flag
					+ " count = 1");

			if (flag.equals(OpinionType.PROGOVT.getValue())) {

				verdictQualification.setProCount(1l);
				verdictQualification.setAntiCount(0l);

			} else if (flag.equals(OpinionType.ANTIGOVT.getValue())) {

				verdictQualification.setProCount(0l);
				verdictQualification.setAntiCount(1l);
			}

			logger.debug("Trying to save VerdictQualification object into database.");

			verdictQualification = verdictQualificationService
					.addDistribution(verdictQualification);

			logger.debug("Saved VerdictQualification object in database");

		} else {

			logger.debug("VerdictQualification object is not null.");

			logger.debug("Flag = " + flag + ". So incrementing " + flag
					+ " count by 1");

			if (flag.equals(OpinionType.PROGOVT.getValue())) {

				verdictQualification.setProCount(verdictQualification
						.getProCount() + 1l);

			} else if (flag.equals(OpinionType.ANTIGOVT.getValue())) {

				verdictQualification.setAntiCount(verdictQualification
						.getAntiCount() + 1l);
			}

			logger.debug("Trying to update VerdictQualification object in database.");

			verdictQualification = verdictQualificationService
					.updateDistribution(verdictQualification);

			logger.debug("Updated VerdictQualification object in database");
		}

		logger.debug("Returning VerdictQualification object. Exiting from VerdictDistributionBusinessDelegate aggregatVerdictQualification method");

		return verdictQualification;
	}
}
