package com.ohmuk.folitics.hibernate.service.pollOption.distribution;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionMaritalStatus;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionMaritalStatusId;
import com.ohmuk.folitics.hibernate.repository.pollOption.distribution.IPollOptionDistributionRepository;

/**
 * Service implementation for {@link PollOptionMaritalStatus}
 * @author Abhishek
 *
 */
@Service
@Transactional
public class PollOptionMaritalStatusService implements
        IPollOptionDistributionService<PollOptionMaritalStatus, PollOptionMaritalStatusId> {

    private static Logger logger = LoggerFactory.getLogger(PollOptionMaritalStatusService.class);

    @Autowired
    private IPollOptionDistributionRepository<PollOptionMaritalStatus, PollOptionMaritalStatusId> repository;

    /*
     * (non-Javadoc)
     * @see
     * com.ohmuk.folitics.hibernate.service.verdict.distribution.IVerdictDistributionService#addDistribution(java.lang
     * .Object)
     */
    @Override
    public PollOptionMaritalStatus addDistribution(PollOptionMaritalStatus pollOptionMaritalStatus)
            throws MessageException {

        logger.debug("Entered PollOptionMaritalStatusService addDistribution method");

        if (pollOptionMaritalStatus == null) {

            logger.error("PollOptionMaritalStatus object found null in PollOptionMaritalStatusService.addDistribution method");
            throw (new MessageException("PollOptionMaritalStatus object can't be null"));
        }

        if (pollOptionMaritalStatus.getId() == null) {

            logger.error("Id in PollOptionMaritalStatus object found null in PollOptionMaritalStatusService.addDistribution method");
            throw (new MessageException("Id in PollOptionMaritalStatus object can't be null"));
        }

        logger.debug("Trying to save the PollOptionMaritalStatus object for poll option id = "
                + pollOptionMaritalStatus.getId().getPollOption().getId() + " and age group id = "
                + pollOptionMaritalStatus.getId().getMaritalStatus().getId());

        pollOptionMaritalStatus = repository.save(pollOptionMaritalStatus);

        logger.debug("PollOptionMaritalStatus saved successfully. Exiting PollOptionMaritalStatusService addDistribution method");

        return pollOptionMaritalStatus;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ohmuk.folitics.hibernate.service.verdict.distribution.IVerdictDistributionService#getDistribution(java.lang
     * .Object)
     */
    @Override
    public PollOptionMaritalStatus getDistribution(PollOptionMaritalStatusId pollOptionMaritalStatusId)
            throws MessageException {

        logger.debug("Entered PollOptionMaritalStatusService getDistribution method");

        if (pollOptionMaritalStatusId == null) {

            logger.error("PollOptionMaritalStatusId object found null in PollOptionMaritalStatusService.getDistribution method");
            throw (new MessageException("PollOptionMaritalStatusId object can't be null"));
        }

        if (pollOptionMaritalStatusId.getPollOption() == null) {

            logger.error("PollOption in PollOptionMaritalStatusId object found null in PollOptionMaritalStatusService.getDistribution method");
            throw (new MessageException("PollOption in PollOptionMaritalStatusId object can't be null"));
        }

        if (pollOptionMaritalStatusId.getMaritalStatus() == null) {

            logger.error("MaritalStatus in PollOptionMaritalStatusId object found null in PollOptionMaritalStatusService.getDistribution method");
            throw (new MessageException("MaritalStatus in PollOptionMaritalStatusId object can't be null"));
        }

        logger.debug("Trying to get the PollOptionMaritalStatus object for poll option id = "
                + pollOptionMaritalStatusId.getPollOption().getId() + " and marital status id = "
                + pollOptionMaritalStatusId.getMaritalStatus().getId());

        PollOptionMaritalStatus pollOptionMaritalStatus = repository.find(pollOptionMaritalStatusId);

        logger.debug("Got PollOptionMaritalStatus from database. Exiting PollOptionMaritalStatusService getDistribution method");

        return pollOptionMaritalStatus;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ohmuk.folitics.hibernate.service.verdict.distribution.IVerdictDistributionService#updateDistribution(java
     * .lang.Object)
     */
    @Override
    public PollOptionMaritalStatus updateDistribution(PollOptionMaritalStatus pollOptionMaritalStatus)
            throws MessageException {

        logger.debug("Entered PollOptionMaritalStatusService updateDistribution method");

        if (pollOptionMaritalStatus == null) {

            logger.error("PollOptionMaritalStatus object found null in PollOptionMaritalStatusService.updateDistribution method");
            throw (new MessageException("PollOptionMaritalStatus object can't be null"));
        }

        if (pollOptionMaritalStatus.getId() == null) {

            logger.error("Id in PollOptionMaritalStatus object found null in PollOptionMaritalStatusService.updateDistribution method");
            throw (new MessageException("Id in PollOptionMaritalStatus object can't be null"));
        }

        logger.debug("Trying to get the PollOptionMaritalStatus object for poll option id = "
                + pollOptionMaritalStatus.getId().getPollOption().getId() + " and marital status id = "
                + pollOptionMaritalStatus.getId().getMaritalStatus().getId());

        pollOptionMaritalStatus = repository.update(pollOptionMaritalStatus);

        logger.debug("Updated PollOptionMaritalStatus in database. Exiting PollOptionMaritalStatusService updateDistribution method");

        return pollOptionMaritalStatus;
    }

}
