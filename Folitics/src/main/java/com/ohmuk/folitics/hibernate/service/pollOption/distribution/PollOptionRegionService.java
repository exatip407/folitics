package com.ohmuk.folitics.hibernate.service.pollOption.distribution;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionRegion;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionRegionId;
import com.ohmuk.folitics.hibernate.repository.pollOption.distribution.IPollOptionDistributionRepository;

/**
 * Service implementation for {@link PollOptionRegion}
 * @author Abhishek
 *
 */
@Service
@Transactional
public class PollOptionRegionService implements IPollOptionDistributionService<PollOptionRegion, PollOptionRegionId> {

    private static Logger logger = LoggerFactory.getLogger(PollOptionRegionService.class);

    @Autowired
    private IPollOptionDistributionRepository<PollOptionRegion, PollOptionRegionId> repository;

    @Override
    public PollOptionRegion addDistribution(PollOptionRegion pollOptionRegion) throws MessageException {

        logger.debug("Entered PollOptionRegionService addDistribution method");

        if (pollOptionRegion == null) {

            logger.error("PollOptionRegion object found null in PollOptionRegionService.addDistribution method");
            throw (new MessageException("PollOptionRegion object can't be null"));
        }

        if (pollOptionRegion.getId() == null) {

            logger.error("Id in PollOptionRegion object found null in PollOptionRegionService.addDistribution method");
            throw (new MessageException("Id in PollOptionRegion object can't be null"));
        }

        logger.debug("Trying to save the PollOptionRegion object for poll option id = "
                + pollOptionRegion.getId().getPollOption().getId() + " and region id = "
                + pollOptionRegion.getId().getRegion().getId());

        pollOptionRegion = repository.save(pollOptionRegion);

        logger.debug("PollOptionRegion saved successfully. Exiting PollOptionRegionService addDistribution method");

        return pollOptionRegion;
    }

    @Override
    public PollOptionRegion getDistribution(PollOptionRegionId pollOptionRegionId) throws MessageException {

        logger.debug("Entered PollOptionRegionService getDistribution method");

        if (pollOptionRegionId == null) {

            logger.error("PollOptionRegionId object found null in PollOptionRegionService.getDistribution method");
            throw (new MessageException("PollOptionRegionId object can't be null"));
        }

        if (pollOptionRegionId.getPollOption() == null) {

            logger.error("PollOption in PollOptionRegionId object found null in PollOptionRegionService.getDistribution method");
            throw (new MessageException("PollOption in PollOptionRegionId object can't be null"));
        }

        if (pollOptionRegionId.getRegion() == null) {

            logger.error("Region in PollOptionRegionId object found null in PollOptionRegionService.getDistribution method");
            throw (new MessageException("Region in PollOptionRegionId object can't be null"));
        }

        logger.debug("Trying to get the PollOptionRegion object for poll option id = "
                + pollOptionRegionId.getPollOption().getId() + " and region id = "
                + pollOptionRegionId.getRegion().getId());

        PollOptionRegion pollOptionRegion = repository.find(pollOptionRegionId);

        logger.debug("Got PollOptionRegion from database. Exiting PollOptionRegionService getDistribution method");

        return pollOptionRegion;
    }

    @Override
    public PollOptionRegion updateDistribution(PollOptionRegion pollOptionRegion) throws MessageException {

        logger.debug("Entered PollOptionRegionService updateDistribution method");

        if (pollOptionRegion == null) {

            logger.error("PollOptionRegion object found null in PollOptionRegionService.updateDistribution method");
            throw (new MessageException("PollOptionRegion object can't be null"));
        }

        if (pollOptionRegion.getId() == null) {

            logger.error("Id in PollOptionRegion object found null in PollOptionRegionService.updateDistribution method");
            throw (new MessageException("Id in PollOptionRegion object can't be null"));
        }

        logger.debug("Trying to get the PollOptionRegion object for poll option id = "
                + pollOptionRegion.getId().getPollOption().getId() + " and region id = "
                + pollOptionRegion.getId().getRegion().getId());

        pollOptionRegion = repository.update(pollOptionRegion);

        logger.debug("Updated PollOptionRegion in database. Exiting PollOptionRegionService updateDistribution method");

        return pollOptionRegion;
    }

}
