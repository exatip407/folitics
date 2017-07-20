package com.ohmuk.folitics.hibernate.service.pollOption.distribution;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionReligion;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionReligionId;
import com.ohmuk.folitics.hibernate.repository.pollOption.distribution.IPollOptionDistributionRepository;

/**
 * Service implementation for {@link PollOptionReligion}
 * @author Abhishek
 *
 */
@Service
@Transactional
public class PollOptionReligionService implements
        IPollOptionDistributionService<PollOptionReligion, PollOptionReligionId> {

    private static Logger logger = LoggerFactory.getLogger(PollOptionReligionService.class);

    @Autowired
    private IPollOptionDistributionRepository<PollOptionReligion, PollOptionReligionId> repository;

    @Override
    public PollOptionReligion addDistribution(PollOptionReligion pollOptionReligion) throws MessageException {

        logger.debug("Entered PollOptionReligionService addDistribution method");

        if (pollOptionReligion == null) {

            logger.error("PollOptionReligion object found null in PollOptionReligionService.addDistribution method");
            throw (new MessageException("PollOptionReligion object can't be null"));
        }

        if (pollOptionReligion.getId() == null) {

            logger.error("Id in PollOptionReligion object found null in PollOptionReligionService.addDistribution method");
            throw (new MessageException("Id in PollOptionReligion object can't be null"));
        }

        logger.debug("Trying to save the PollOptionReligion object for poll option id = "
                + pollOptionReligion.getId().getPollOption().getId() + " and religion id = "
                + pollOptionReligion.getId().getReligion().getId());

        pollOptionReligion = repository.save(pollOptionReligion);

        logger.debug("PollOptionReligion saved successfully. Exiting PollOptionReligionService addDistribution method");

        return pollOptionReligion;
    }

    @Override
    public PollOptionReligion getDistribution(PollOptionReligionId pollOptionReligionId) throws MessageException {

        logger.debug("Entered PollOptionReligionService getDistribution method");

        if (pollOptionReligionId == null) {

            logger.error("PollOptionReligionId object found null in PollOptionReligionService.getDistribution method");
            throw (new MessageException("PollOptionReligionId object can't be null"));
        }

        if (pollOptionReligionId.getPollOption() == null) {

            logger.error("PollOption in PollOptionReligionId object found null in PollOptionReligionService.getDistribution method");
            throw (new MessageException("PollOption in PollOptionReligionId object can't be null"));
        }

        if (pollOptionReligionId.getReligion() == null) {

            logger.error("Religion in PollOptionReligionId object found null in PollOptionReligionService.getDistribution method");
            throw (new MessageException("Religion in PollOptionReligionId object can't be null"));
        }

        logger.debug("Trying to get the PollOptionReligion object for poll option id = "
                + pollOptionReligionId.getPollOption().getId() + " and religion id = "
                + pollOptionReligionId.getReligion().getId());

        PollOptionReligion pollOptionReligion = repository.find(pollOptionReligionId);

        logger.debug("Got PollOptionReligion from database. Exiting PollOptionReligionService getDistribution method");

        return pollOptionReligion;
    }

    @Override
    public PollOptionReligion updateDistribution(PollOptionReligion pollOptionReligion) throws MessageException {

        logger.debug("Entered PollOptionReligionService updateDistribution method");

        if (pollOptionReligion == null) {

            logger.error("PollOptionReligion object found null in PollOptionReligionService.updateDistribution method");
            throw (new MessageException("PollOptionReligion object can't be null"));
        }

        if (pollOptionReligion.getId() == null) {

            logger.error("Id in PollOptionReligion object found null in PollOptionReligionService.updateDistribution method");
            throw (new MessageException("Id in PollOptionReligion object can't be null"));
        }

        logger.debug("Trying to get the PollOptionReligion object for poll option id = "
                + pollOptionReligion.getId().getPollOption().getId() + " and religion id = "
                + pollOptionReligion.getId().getReligion().getId());

        pollOptionReligion = repository.update(pollOptionReligion);

        logger.debug("Updated PollOptionReligion in database. Exiting PollOptionReligionService updateDistribution method");

        return pollOptionReligion;
    }

}
