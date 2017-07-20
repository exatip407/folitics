package com.ohmuk.folitics.businessDelegate.implementations;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.beans.PollOptionAnswer;
import com.ohmuk.folitics.businessDelegate.interfaces.IPollOptionDistributionBusinessDelegate;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.poll.PollOption;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionAgeGroup;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionAgeGroupId;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionMaritalStatus;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionMaritalStatusId;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionQualification;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionQualificationId;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionRegion;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionRegionId;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionReligion;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionReligionId;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionSex;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionSexId;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.AgeGroup;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.MaritalStatus;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Qualification;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Region;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Religion;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Sex;
import com.ohmuk.folitics.hibernate.service.pollOption.distribution.IPollOptionDistributionService;
import com.ohmuk.folitics.hibernate.service.verdict.lookup.IRegionStateService;
import com.ohmuk.folitics.hibernate.service.verdict.lookup.IVerdictLookupService;
import com.ohmuk.folitics.service.IPollOptionService;
import com.ohmuk.folitics.service.IUserService;
import com.ohmuk.folitics.util.AggregationUtils;

/**
 * BusinessDelegate implementation for {@link PollOption} distributions
 * @author Abhishek
 *
 */

@Component
@Transactional
public class PollOptionDistributionBusinessDelegate implements IPollOptionDistributionBusinessDelegate {

    private static Logger logger = LoggerFactory.getLogger(PollOptionDistributionBusinessDelegate.class);

    @Autowired
    private volatile IPollOptionService pollOptionService;

    @Autowired
    private volatile IPollOptionDistributionService<PollOptionAgeGroup, PollOptionAgeGroupId> pollOptionAgeGroupService;

    @Autowired
    private volatile IVerdictLookupService<AgeGroup> verdictAgeGroupLookupService;

    @Autowired
    private volatile IPollOptionDistributionService<PollOptionSex, PollOptionSexId> pollOptionSexService;

    @Autowired
    private volatile IVerdictLookupService<Sex> verdictSexLookupService;

    @Autowired
    private volatile IPollOptionDistributionService<PollOptionMaritalStatus, PollOptionMaritalStatusId> pollOptionMaritalStatusService;

    @Autowired
    private volatile IVerdictLookupService<MaritalStatus> verdictMaritalStatusLookupService;

    @Autowired
    private volatile IPollOptionDistributionService<PollOptionRegion, PollOptionRegionId> pollOptionRegionService;

    @Autowired
    private volatile IVerdictLookupService<Region> verdictRegionLookupService;

    @Autowired
    private volatile IPollOptionDistributionService<PollOptionReligion, PollOptionReligionId> pollOptionReligionService;

    @Autowired
    private volatile IVerdictLookupService<Religion> verdictReligionLookupService;

    @Autowired
    private volatile IPollOptionDistributionService<PollOptionQualification, PollOptionQualificationId> pollOptionQualificationService;

    @Autowired
    private volatile IVerdictLookupService<Qualification> verdictQualificationLookupService;

    @Autowired
    private volatile IUserService userService;

    @Autowired
    private volatile IRegionStateService regionStateRepository;

    @SuppressWarnings("unused")
    @Override
    public PollOption create(PollOptionAnswer pollOptionAnswer) throws Exception {

        PollOption pollOption = pollOptionAnswer.getPollOption();

        User user = pollOptionAnswer.getUser();

        logger.debug("Entered PollOptionDistributionBusinessDelegate create method");

        logger.debug("Input values are : poll option id = " + pollOption.getId() + ", user id = " + user.getId());

        logger.debug("Getting user object for user id = " + user.getId());

        user = userService.findUserById(user.getId());

        logger.debug("Aggregating the poll option for age group");

        PollOptionAgeGroup pollOptionAgeGroup = aggregatPollOptionAgeGroup(pollOption, user);

        logger.debug("Aggregated poll option for age group");
        logger.debug("Aggregating the poll option for sex");

        PollOptionSex pollOptionSex = aggregatPollOptionSex(pollOption, user);

        logger.debug("Aggregated poll option for sex");
        logger.debug("Aggregating the poll option for marital status");

        PollOptionMaritalStatus pollOptionMaritalStatus = aggregatPollOptionMaritalStatus(pollOption, user);

        logger.debug("Aggregated poll option for marital status");
        logger.debug("Aggregating the poll option for region");

        PollOptionRegion pollOptionRegion = aggregatPollOptionRegion(pollOption, user);

        logger.debug("Aggregated poll option for region");
        logger.debug("Aggregating the poll option for religion");

        PollOptionReligion pollOptionReligion = aggregatPollOptionReligion(pollOption, user);

        logger.debug("Aggregated poll option for religion");
        logger.debug("Aggregating the poll option for qualification");

        PollOptionQualification pollOptionQualification = aggregatPollOptionQualification(pollOption, user);

        logger.debug("Aggregated poll option for qualification");

        logger.debug("Getting the poll option object with poll option id = " + pollOption.getId());

        pollOption = pollOptionService.read(pollOption.getId());

        logger.debug("Got poll option object with poll option id = " + pollOption.getId()
                + ". Exiting PollOptionDistributionBusinessDelegate create method");

        return pollOption;
    }

    @Override
    public PollOption read(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PollOptionAgeGroup aggregatPollOptionAgeGroup(PollOption pollOption, User user) throws MessageException {

        logger.debug("Entered PollOptionDistributionBusinessDelegate aggregatPollOptionAgeGroup method");

        logger.debug("Input values are : poll option id = " + pollOption.getId() + ", user id = " + user.getId());

        logger.debug("Getting age for user based on user's DOB = " + user.getDob());

        int age = AggregationUtils.getAgeForDOB(user.getDob());

        logger.debug("Getting age group for age = " + age);

        AgeGroup ageGroup = verdictAgeGroupLookupService.readByValue(age);

        logger.debug("Got age group object for age = " + age + " having id = " + ageGroup.getId());

        PollOptionAgeGroupId pollOptionAgeGroupId = new PollOptionAgeGroupId();
        pollOptionAgeGroupId.setPollOption(pollOption);
        pollOptionAgeGroupId.setAgeGroup(ageGroup);

        logger.debug("Getting PollOptionAgeGroup object for poll option id = " + pollOption.getId()
                + " and age group = " + ageGroup.getId());

        PollOptionAgeGroup pollOptionAgeGroup = pollOptionAgeGroupService.getDistribution(pollOptionAgeGroupId);

        if (pollOptionAgeGroup == null) {

            logger.debug("PollOptionAgeGroup object found null. Initialising new PollOptionAgeGroup object");

            pollOptionAgeGroup = new PollOptionAgeGroup();
            pollOptionAgeGroup.setId(pollOptionAgeGroupId);

            logger.debug("Setting count to 1");

            pollOptionAgeGroup.setCount(1l);

            logger.debug("Trying to save PollOptionAgeGroup object into database.");

            pollOptionAgeGroup = pollOptionAgeGroupService.addDistribution(pollOptionAgeGroup);

            logger.debug("Saved PollOptionAgeGroup object in database");

        } else {

            logger.debug("PollOptionAgeGroup object is not null.");

            logger.debug("Incrementing count by 1");

            pollOptionAgeGroup.setCount(pollOptionAgeGroup.getCount() + 1l);

            logger.debug("Trying to update PollOptionAgeGroup object in database.");

            pollOptionAgeGroup = pollOptionAgeGroupService.updateDistribution(pollOptionAgeGroup);

            logger.debug("Updated PollOptionAgeGroup object in database");
        }

        logger.debug("Returning PollOptionAgeGroup object. Exiting from PollOptionDistributionBusinessDelegate aggregatPollOptionAgeGroup method");

        return pollOptionAgeGroup;
    }

    @Override
    public PollOptionSex aggregatPollOptionSex(PollOption pollOption, User user) throws MessageException {

        logger.debug("Entered PollOptionDistributionBusinessDelegate aggregatPollOptionSex method");

        logger.debug("Input values are : poll option id = " + pollOption.getId() + ", user id = " + user.getId());

        logger.debug("Getting sex object for sex = " + user.getGender());

        Sex sex = verdictSexLookupService.readByValue(user.getGender());

        logger.debug("Got sex object for sex = " + user.getGender() + " having id = " + sex.getId());

        PollOptionSexId pollOptionSexId = new PollOptionSexId();
        pollOptionSexId.setPollOption(pollOption);
        pollOptionSexId.setSex(sex);

        logger.debug("Getting PollOptionSex object for poll option id = " + pollOption.getId() + " and sex = "
                + sex.getId());

        PollOptionSex pollOptionSex = pollOptionSexService.getDistribution(pollOptionSexId);

        if (pollOptionSex == null) {

            logger.debug("PollOptionSex object found null. Initialising new PollOptionSex object");

            pollOptionSex = new PollOptionSex();
            pollOptionSex.setId(pollOptionSexId);

            logger.debug("Setting count as 1");

            pollOptionSex.setCount(1l);

            logger.debug("Trying to save PollOptionSex object into database.");

            pollOptionSex = pollOptionSexService.addDistribution(pollOptionSex);

            logger.debug("Saved PollOptionSex object in database");

        } else {

            logger.debug("PollOptionSex object is not null.");

            logger.debug("Incrementing count by 1");

            pollOptionSex.setCount(pollOptionSex.getCount() + 1l);

            logger.debug("Trying to update PollOptionSex object in database.");

            pollOptionSex = pollOptionSexService.updateDistribution(pollOptionSex);

            logger.debug("Updated PollOptionSex object in database");
        }

        logger.debug("Returning PollOptionSex object. Exiting from PollOptionDistributionBusinessDelegate aggregatPollOptionSex method");

        return pollOptionSex;
    }

    @Override
    public PollOptionMaritalStatus aggregatPollOptionMaritalStatus(PollOption pollOption, User user)
            throws MessageException {

        logger.debug("Entered PollOptionDistributionBusinessDelegate aggregatPollOptionMaritalStatus method");

        logger.debug("Input values are : poll optiion id = " + pollOption.getId() + ", user id = " + user.getId());

        logger.debug("Getting marital status object for marital status = " + user.getMaritalStatus());

        MaritalStatus maritalStatus = verdictMaritalStatusLookupService.readByValue(user.getMaritalStatus());

        logger.debug("Got marital status object for marital status = " + user.getMaritalStatus()
                + " having id = " + maritalStatus.getId());

        PollOptionMaritalStatusId pollOptionMaritalStatusId = new PollOptionMaritalStatusId();
        pollOptionMaritalStatusId.setPollOption(pollOption);
        pollOptionMaritalStatusId.setMaritalStatus(maritalStatus);

        logger.debug("Getting PollOptionMaritalStatus object for poll option id = " + pollOption.getId()
                + " and marital status = " + maritalStatus.getId());

        PollOptionMaritalStatus pollOptionMaritalStatus = pollOptionMaritalStatusService
                .getDistribution(pollOptionMaritalStatusId);

        if (pollOptionMaritalStatus == null) {

            logger.debug("PollOptionMaritalStatus object found null. Initialising new PollOptionMaritalStatus object");

            pollOptionMaritalStatus = new PollOptionMaritalStatus();
            pollOptionMaritalStatus.setId(pollOptionMaritalStatusId);

            logger.debug("Setting count as 1");

            pollOptionMaritalStatus.setCount(1l);

            logger.debug("Trying to save PollOptionMaritalStatus object into database.");

            pollOptionMaritalStatus = pollOptionMaritalStatusService.addDistribution(pollOptionMaritalStatus);

            logger.debug("Saved PollOptionMaritalStatus object in database");

        } else {

            logger.debug("PollOptionMaritalStatus object is not null.");

            logger.debug("Incrementing count by 1");

            pollOptionMaritalStatus.setCount(pollOptionMaritalStatus.getCount() + 1l);

            logger.debug("Trying to update PollOptionMaritalStatus object in database.");

            pollOptionMaritalStatus = pollOptionMaritalStatusService.updateDistribution(pollOptionMaritalStatus);

            logger.debug("Updated PollOptionMaritalStatus object in database");
        }

        logger.debug("Returning PollOptionMaritalStatus object. Exiting from PollOptionDistributionBusinessDelegate aggregatPollOptionMaritalStatus method");

        return pollOptionMaritalStatus;
    }

    @Override
    public PollOptionRegion aggregatPollOptionRegion(PollOption pollOption, User user) throws MessageException {

        logger.debug("Entered PollOptionDistributionBusinessDelegate aggregatPollOptionRegion method");

        logger.debug("Input values are : poll option id = " + pollOption.getId() + ", user id = " + user.getId());

        logger.debug("Getting region object for state = " + user.getState());

        Region region = regionStateRepository.getRegionForState(user.getState());

        logger.debug("Got region object for state = " + user.getState() + " having id = "
                + region.getId());

        PollOptionRegionId pollOptionRegionId = new PollOptionRegionId();
        pollOptionRegionId.setPollOption(pollOption);
        pollOptionRegionId.setRegion(region);

        logger.debug("Getting PollOptionRegion object for poll option id = " + pollOption.getId() + " and region = "
                + region.getId());

        PollOptionRegion pollOptionRegion = pollOptionRegionService.getDistribution(pollOptionRegionId);

        if (pollOptionRegion == null) {

            logger.debug("PollOptionRegion object found null. Initialising new PollOptionRegion object");

            pollOptionRegion = new PollOptionRegion();
            pollOptionRegion.setId(pollOptionRegionId);

            logger.debug("Setting count as 1");

            pollOptionRegion.setCount(1l);

            logger.debug("Trying to save PollOptionRegion object into database.");

            pollOptionRegion = pollOptionRegionService.addDistribution(pollOptionRegion);

            logger.debug("Saved PollOptionRegion object in database");

        } else {

            logger.debug("PollOptionRegion object is not null.");

            logger.debug("Incrementing count by 1");

            pollOptionRegion.setCount(pollOptionRegion.getCount() + 1l);

            logger.debug("Trying to update PollOptionRegion object in database.");

            pollOptionRegion = pollOptionRegionService.updateDistribution(pollOptionRegion);

            logger.debug("Updated PollOptionRegion object in database");
        }

        logger.debug("Returning PollOptionRegion object. Exiting from PollOptionDistributionBusinessDelegate aggregatPollOptionRegion method");

        return pollOptionRegion;
    }

    @Override
    public PollOptionReligion aggregatPollOptionReligion(PollOption pollOption, User user) throws MessageException {

        logger.debug("Entered PollOptionDistributionBusinessDelegate aggregatPollOptionReligion method");

        logger.debug("Input values are : poll option id = " + pollOption.getId() + ", user id = " + user.getId());

        logger.debug("Getting religion object for religion = " + user.getReligion());

        Religion religion = verdictReligionLookupService.readByValue(user.getReligion());

        PollOptionReligionId pollOptionReligionId = new PollOptionReligionId();
        pollOptionReligionId.setPollOption(pollOption);
        pollOptionReligionId.setReligion(religion);

        logger.debug("Getting PollOptionReligion object for poll option id = " + pollOption.getId()
                + " and religion = " + religion.getId());

        PollOptionReligion pollOptionReligion = pollOptionReligionService.getDistribution(pollOptionReligionId);

        if (pollOptionReligion == null) {

            logger.debug("PollOptionReligion object found null. Initialising new PollOptionReligion object");

            pollOptionReligion = new PollOptionReligion();
            pollOptionReligion.setId(pollOptionReligionId);

            logger.debug("Setting count as 1");

            pollOptionReligion.setCount(1l);

            logger.debug("Trying to save PollOptionReligion object into database.");

            pollOptionReligion = pollOptionReligionService.addDistribution(pollOptionReligion);

            logger.debug("Saved PollOptionReligion object in database");

        } else {

            logger.debug("PollOptionReligion object is not null.");

            logger.debug("Incrementing count by 1");

            pollOptionReligion.setCount(pollOptionReligion.getCount() + 1l);

            logger.debug("Trying to update PollOptionReligion object in database.");

            pollOptionReligion = pollOptionReligionService.updateDistribution(pollOptionReligion);

            logger.debug("Updated PollOptionReligion object in database");
        }

        logger.debug("Returning PollOptionReligion object. Exiting from PollOptionDistributionBusinessDelegate aggregatPollOptionReligion method");

        return pollOptionReligion;
    }

    @Override
    public PollOptionQualification aggregatPollOptionQualification(PollOption pollOption, User user)
            throws MessageException {

        logger.debug("Entered PollOptionDistributionBusinessDelegate aggregatPollOptionQualification method");

        logger.debug("Input values are : poll option id = " + pollOption.getId() + ", user id = " + user.getId());

        logger.debug("Getting highest education for user");
        Hibernate.initialize(user.getUserEducation());
        String highestEducation = AggregationUtils.getHighestEducation(user.getUserEducation());

        logger.debug("Getting qualification object for qualification = " + highestEducation);

        Qualification qualification = verdictQualificationLookupService.readByValue(highestEducation);

        logger.debug("Got qualification object for qualification = " + highestEducation + " having id = "
                + qualification.getId());

        PollOptionQualificationId pollOptionQualificationId = new PollOptionQualificationId();
        pollOptionQualificationId.setPollOption(pollOption);
        pollOptionQualificationId.setQualification(qualification);

        logger.debug("Getting PollOptionQualification object for poll option id = " + pollOption.getId()
                + " and qualification = " + qualification.getId());

        PollOptionQualification pollOptionQualification = pollOptionQualificationService
                .getDistribution(pollOptionQualificationId);

        if (pollOptionQualification == null) {

            logger.debug("PollOptionQualification object found null. Initialising new PollOptionQualification object");

            pollOptionQualification = new PollOptionQualification();
            pollOptionQualification.setId(pollOptionQualificationId);

            logger.debug("Setting count as 1");

            pollOptionQualification.setCount(1l);

            logger.debug("Trying to save PollOptionQualification object into database.");

            pollOptionQualification = pollOptionQualificationService.addDistribution(pollOptionQualification);

            logger.debug("Saved PollOptionQualification object in database");

        } else {

            logger.debug("PollOptionQualification object is not null.");

            logger.debug("Incrementing count by 1");

            pollOptionQualification.setCount(pollOptionQualification.getCount() + 1l);

            logger.debug("Trying to update PollOptionQualification object in database.");

            pollOptionQualification = pollOptionQualificationService.updateDistribution(pollOptionQualification);

            logger.debug("Updated PollOptionQualification object in database");
        }

        logger.debug("Returning PollOptionQualification object. Exiting from PollOptionDistributionBusinessDelegate aggregatPollOptionQualification method");

        return pollOptionQualification;
    }

    @Override
    public boolean answerPoll(Long pollOptionId, Long userId) throws Exception,MessageException{
        PollOption pollOption = pollOptionService.read(pollOptionId);
        User user = userService.findUserById(userId);
        if (null !=pollOption & null!=user) {
            aggregatPollOptionAgeGroup(pollOption, user);
            aggregatPollOptionMaritalStatus(pollOption, user);
            aggregatPollOptionQualification(pollOption, user);
            aggregatPollOptionRegion(pollOption, user);
            aggregatPollOptionReligion(pollOption, user);
            aggregatPollOptionSex(pollOption, user);
        }else {
            return false;
        }
        
        return true;
    }

}
