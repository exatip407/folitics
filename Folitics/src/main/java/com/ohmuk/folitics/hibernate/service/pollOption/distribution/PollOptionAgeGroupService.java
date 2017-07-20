package com.ohmuk.folitics.hibernate.service.pollOption.distribution;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionAgeGroup;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionAgeGroupId;
import com.ohmuk.folitics.hibernate.repository.pollOption.distribution.IPollOptionDistributionRepository;

/**
 * Service implementation for {@link PollOptionAgeGroup}
 * @author Abhishek
 *
 */
@Service
@Transactional
public class PollOptionAgeGroupService implements
        IPollOptionDistributionService<PollOptionAgeGroup, PollOptionAgeGroupId> {

    private static Logger logger = LoggerFactory.getLogger(PollOptionAgeGroupService.class);

    @Autowired
    private IPollOptionDistributionRepository<PollOptionAgeGroup, PollOptionAgeGroupId> repository;

    /*
     * (non-Javadoc)
     * @see com.ohmuk.folitics.hibernate.service.verdict.IVerdictDistributionService#addDistribution(java.lang.Object)
     */
    @Override
    public PollOptionAgeGroup addDistribution(PollOptionAgeGroup pollOptionAgeGroup) throws MessageException {

        logger.debug("Entered PollOptionAgeGroupService addDistribution method");

        if (pollOptionAgeGroup == null) {

            logger.error("PollOptionAgeGroup object found null in PollOptionAgeGroupService.addDistribution method");
            throw (new MessageException("PollOptionAgeGroup object can't be null"));
        }

        if (pollOptionAgeGroup.getId() == null) {

            logger.error("Id in PollOptionAgeGroup object found null in PollOptionAgeGroupService.addDistribution method");
            throw (new MessageException("Id in PollOptionAgeGroup object can't be null"));
        }

        logger.debug("Trying to save the PollOptionAgeGroup object for poll option id = "
                + pollOptionAgeGroup.getId().getPollOption().getId() + " and age group id = "
                + pollOptionAgeGroup.getId().getAgeGroup().getId());

        pollOptionAgeGroup = repository.save(pollOptionAgeGroup);

        logger.debug("PollOptionAgeGroup saved successfully. Exiting PollOptionAgeGroupService addDistribution method");

        return pollOptionAgeGroup;
    }

    /*
     * (non-Javadoc)
     * @see com.ohmuk.folitics.hibernate.service.verdict.IVerdictDistributionService#getDistribution(java.lang.Object)
     */
    @Override
    public PollOptionAgeGroup getDistribution(PollOptionAgeGroupId pollOptionAgeGroupId) throws MessageException {

        logger.debug("Entered PollOptionAgeGroupService getDistribution method");

        if (pollOptionAgeGroupId == null) {

            logger.error("PollOptionAgeGroupId object found null in PollOptionAgeGroupService.getDistribution method");
            throw (new MessageException("PollOptionAgeGroupId object can't be null"));
        }

        if (pollOptionAgeGroupId.getPollOption() == null) {

            logger.error("PollOption in PollOptionAgeGroupId object found null in PollOptionAgeGroupService.getDistribution method");
            throw (new MessageException("PollOption in PollOptionAgeGroupId object can't be null"));
        }

        if (pollOptionAgeGroupId.getAgeGroup() == null) {

            logger.error("AgeGroup in PollOptionAgeGroupId object found null in PollOptionAgeGroupService.getDistribution method");
            throw (new MessageException("AgeGroup in PollOptionAgeGroupId object can't be null"));
        }

        logger.debug("Trying to get the PollOptionAgeGroup object for poll option id = "
                + pollOptionAgeGroupId.getPollOption().getId() + " and age group id = "
                + pollOptionAgeGroupId.getAgeGroup().getId());

        PollOptionAgeGroup pollOptionAgeGroup = repository.find(pollOptionAgeGroupId);

        logger.debug("Got PollOptionAgeGroup from database. Exiting PollOptionAgeGroupService getDistribution method");

        return pollOptionAgeGroup;
    }

    @Override
    public PollOptionAgeGroup updateDistribution(PollOptionAgeGroup pollOptionAgeGroup) throws MessageException {

        logger.debug("Entered PollOptionAgeGroupService updateDistribution method");

        if (pollOptionAgeGroup == null) {

            logger.error("PollOptionAgeGroup object found null in PollOptionAgeGroupService.updateDistribution method");
            throw (new MessageException("PollOptionAgeGroup object can't be null"));
        }

        if (pollOptionAgeGroup.getId() == null) {

            logger.error("Id in PollOptionAgeGroup object found null in PollOptionAgeGroupService.updateDistribution method");
            throw (new MessageException("Id in PollOptionAgeGroup object can't be null"));
        }

        logger.debug("Trying to get the PollOptionAgeGroup object for poll option id = "
                + pollOptionAgeGroup.getId().getPollOption().getId() + " and age group id = "
                + pollOptionAgeGroup.getId().getAgeGroup().getId());

        pollOptionAgeGroup = repository.update(pollOptionAgeGroup);

        logger.debug("Updated PollOptionAgeGroup in database. Exiting PollOptionAgeGroupService updateDistribution method");

        return pollOptionAgeGroup;
    }

}
