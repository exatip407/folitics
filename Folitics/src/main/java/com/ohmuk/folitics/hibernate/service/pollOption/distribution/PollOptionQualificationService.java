package com.ohmuk.folitics.hibernate.service.pollOption.distribution;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionQualification;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionQualificationId;
import com.ohmuk.folitics.hibernate.repository.pollOption.distribution.IPollOptionDistributionRepository;

/**
 * Service implementation for {@link PollOptionQualification}
 * @author Abhishek
 *
 */
@Service
@Transactional
public class PollOptionQualificationService implements
        IPollOptionDistributionService<PollOptionQualification, PollOptionQualificationId> {

    private static Logger logger = LoggerFactory.getLogger(PollOptionQualificationService.class);

    @Autowired
    private IPollOptionDistributionRepository<PollOptionQualification, PollOptionQualificationId> repository;

    @Override
    public PollOptionQualification addDistribution(PollOptionQualification pollOptionQualification)
            throws MessageException {

        logger.debug("Entered PollOptionQualificationService addDistribution method");

        if (pollOptionQualification == null) {

            logger.error("PollOptionQualification object found null in PollOptionQualificationService.addDistribution method");
            throw (new MessageException("PollOptionQualification object can't be null"));
        }

        if (pollOptionQualification.getId() == null) {

            logger.error("Id in PollOptionQualification object found null in PollOptionQualificationService.addDistribution method");
            throw (new MessageException("Id in PollOptionQualification object can't be null"));
        }

        logger.debug("Trying to save the PollOptionQualification object for poll option id = "
                + pollOptionQualification.getId().getPollOption().getId() + " and qualification id = "
                + pollOptionQualification.getId().getQualification().getId());

        pollOptionQualification = repository.save(pollOptionQualification);

        logger.debug("PollOptionQualification saved successfully. Exiting PollOptionQualificationService addDistribution method");

        return pollOptionQualification;
    }

    @Override
    public PollOptionQualification getDistribution(PollOptionQualificationId pollOptionQualificationId)
            throws MessageException {

        logger.debug("Entered PollOptionQualificationService getDistribution method");

        if (pollOptionQualificationId == null) {

            logger.error("PollOptionQualificationId object found null in PollOptionQualificationService.getDistribution method");
            throw (new MessageException("PollOptionQualificationId object can't be null"));
        }

        if (pollOptionQualificationId.getPollOption() == null) {

            logger.error("PollOption in PollOptionQualificationId object found null in PollOptionQualificationService.getDistribution method");
            throw (new MessageException("PollOption in PollOptionQualificationId object can't be null"));
        }

        if (pollOptionQualificationId.getQualification() == null) {

            logger.error("Qualification in PollOptionQualificationId object found null in PollOptionQualificationService.getDistribution method");
            throw (new MessageException("Qualification in PollOptionQualificationId object can't be null"));
        }

        logger.debug("Trying to get the PollOptionQualification object for poll option id = "
                + pollOptionQualificationId.getPollOption().getId() + " and qualification id = "
                + pollOptionQualificationId.getQualification().getId());

        PollOptionQualification pollOptionQualification = repository.find(pollOptionQualificationId);

        logger.debug("Got PollOptionQualification from database. Exiting PollOptionQualificationService getDistribution method");

        return pollOptionQualification;
    }

    @Override
    public PollOptionQualification updateDistribution(PollOptionQualification pollOptionQualification)
            throws MessageException {

        logger.debug("Entered PollOptionQualificationService updateDistribution method");

        if (pollOptionQualification == null) {

            logger.error("PollOptionQualification object found null in PollOptionQualificationService.updateDistribution method");
            throw (new MessageException("PollOptionQualification object can't be null"));
        }

        if (pollOptionQualification.getId() == null) {

            logger.error("Id in PollOptionQualification object found null in PollOptionQualificationService.updateDistribution method");
            throw (new MessageException("Id in PollOptionQualification object can't be null"));
        }

        logger.debug("Trying to get the PollOptionQualification object for poll option id = "
                + pollOptionQualification.getId().getPollOption().getId() + " and qualification id = "
                + pollOptionQualification.getId().getQualification().getId());

        pollOptionQualification = repository.update(pollOptionQualification);

        logger.debug("Updated PollOptionQualification in database. Exiting PollOptionQualificationService updateDistribution method");

        return pollOptionQualification;
    }

}
