package com.ohmuk.folitics.hibernate.service.pollOption.distribution;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionSex;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionSexId;
import com.ohmuk.folitics.hibernate.repository.pollOption.distribution.IPollOptionDistributionRepository;

/**
 * Service implementation for {@link PollOptionSex}
 * @author Abhishek
 *
 */
@Service
@Transactional
public class PollOptionSexService implements IPollOptionDistributionService<PollOptionSex, PollOptionSexId> {

    private static Logger logger = LoggerFactory.getLogger(PollOptionSexService.class);

    @Autowired
    private IPollOptionDistributionRepository<PollOptionSex, PollOptionSexId> repository;

    /*
     * (non-Javadoc)
     * @see
     * com.ohmuk.folitics.hibernate.service.verdict.distribution.IVerdictDistributionService#addDistribution(java.lang
     * .Object)
     */
    @Override
    public PollOptionSex addDistribution(PollOptionSex pollOptionSex) throws MessageException {

        logger.debug("Entered PollOptionSexService create method");

        if (pollOptionSex == null) {

            logger.error("PollOptionSex object found null in PollOptionSexService.create method");
            throw (new MessageException("PollOptionSex object can't be null"));
        }

        if (pollOptionSex.getId() == null) {

            logger.error("Id in PollOptionSex object found null in PollOptionSexService.create method");
            throw (new MessageException("Id in PollOptionSex object can't be null"));
        }

        logger.debug("Trying to save the PollOptionSex object for poll option id = "
                + pollOptionSex.getId().getPollOption().getId() + " and sex id = "
                + pollOptionSex.getId().getSex().getId());

        pollOptionSex = repository.save(pollOptionSex);

        logger.debug("PollOptionSex saved successfully. Exiting PollOptionSexService addDistribution method");

        return pollOptionSex;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ohmuk.folitics.hibernate.service.verdict.distribution.IVerdictDistributionService#getDistribution(java.lang
     * .Object)
     */
    @Override
    public PollOptionSex getDistribution(PollOptionSexId pollOptionSexId) throws MessageException {

        logger.debug("Entered PollOptionSexService getDistribution method");

        if (pollOptionSexId == null) {

            logger.error("PollOptionSexId object found null in PollOptionSexService.getDistribution method");
            throw (new MessageException("PollOptionSexId object can't be null"));
        }

        if (pollOptionSexId.getPollOption() == null) {

            logger.error("PollOption in PollOptionSexId object found null in PollOptionSexService.getDistribution method");
            throw (new MessageException("PollOption in PollOptionSexId object can't be null"));
        }

        if (pollOptionSexId.getSex() == null) {

            logger.error("Sex in PollOptionSexId object found null in PollOptionSexService.getDistribution method");
            throw (new MessageException("Sex in PollOptionSexId object can't be null"));
        }

        logger.debug("Trying to get the PollOptionSex object for poll option id = "
                + pollOptionSexId.getPollOption().getId() + " and sex id = " + pollOptionSexId.getSex().getId());

        PollOptionSex pollOptionSex = repository.find(pollOptionSexId);

        logger.debug("Got PollOptionSex from database. Exiting PollOptionSexService getDistribution method");

        return pollOptionSex;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ohmuk.folitics.hibernate.service.verdict.distribution.IVerdictDistributionService#updateDistribution(java
     * .lang.Object)
     */
    @Override
    public PollOptionSex updateDistribution(PollOptionSex pollOptionSex) throws MessageException {

        logger.debug("Entered PollOptionSexService updateDistribution method");

        if (pollOptionSex == null) {

            logger.error("PollOptionSex object found null in PollOptionSexService.updateDistribution method");
            throw (new MessageException("PollOptionSex object can't be null"));
        }

        if (pollOptionSex.getId() == null) {

            logger.error("Id in PollOptionSex object found null in PollOptionSexService.updateDistribution method");
            throw (new MessageException("Id in PollOptionSex object can't be null"));
        }

        logger.debug("Trying to get the PollOptionSex object for poll option id = "
                + pollOptionSex.getId().getPollOption().getId() + " and sex id = "
                + pollOptionSex.getId().getSex().getId());

        pollOptionSex = repository.update(pollOptionSex);

        logger.debug("Updated PollOptionSex in database. Exiting PollOptionSexService updateDistribution method");

        return pollOptionSex;
    }

}
