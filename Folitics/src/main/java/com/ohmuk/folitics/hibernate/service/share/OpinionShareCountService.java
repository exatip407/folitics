package com.ohmuk.folitics.hibernate.service.share;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.share.OpinionShareCount;
import com.ohmuk.folitics.hibernate.repository.share.IOpinionShareCountRepository;

/**
 * Service implementation for entity: {@link OpinionShareCount}
 * @author Abhishek
 *
 */

@Service
@Transactional
public class OpinionShareCountService implements IShareCountService<OpinionShareCount>{

	private static Logger logger = LoggerFactory.getLogger(OpinionShareCountService.class);

    @Autowired
    private IOpinionShareCountRepository repository;

	@Override
	public OpinionShareCount create(OpinionShareCount opinionShareCount) throws MessageException {
		logger.info("Entered OpinionShareCount service create method");

        if (opinionShareCount.getId().getOpinion() == null) {
            logger.error("opinion in opinionShareCount is null");
            throw (new MessageException("opinion in opinionShareCount can't be null"));
        }

        if (opinionShareCount.getId().getOpinion().getId() == null) {
            logger.error("opinion id is null");
            throw (new MessageException("opinion id can't be null"));
        }

        logger.debug("Saving OpinionShareCount with component id = "
                + opinionShareCount.getId().getOpinion().getId());

        return repository.save(opinionShareCount);
	}

	@Override
	public List<OpinionShareCount> readAll() {
		logger.info("Entered OpinionShareCount service readAll method");
        logger.debug("Getting all OpinionShareCount");
        return repository.findAll();
	}

	@Override
	public OpinionShareCount update(OpinionShareCount opinionShareCount) throws MessageException {
		logger.info("Entered OpinionShareCount service update method");

        if (opinionShareCount.getId().getOpinion() == null) {
            logger.error("opinion in opinionShareCount is null");
            throw (new MessageException("opinion in opinionShareCount can't be null"));
        }

        if (opinionShareCount.getId().getOpinion().getId() == null) {
            logger.error("opinion id is null");
            throw (new MessageException("opinion id can't be null"));
        }

        logger.debug("Updating OpinionShareCount with component id = "
                + opinionShareCount.getId().getOpinion().getId());
        return repository.update(opinionShareCount);
	}

	@Override
	public OpinionShareCount delete(OpinionShareCount opinionShareCount) throws MessageException {
		logger.info("Entered OpinionShareCount service delete method");

        if (opinionShareCount.getId().getOpinion() == null) {
            logger.error("opinion in opinionShareCount is null");
            throw (new MessageException("opinion in opinionShareCount can't be null"));
        }

        if (opinionShareCount.getId().getOpinion().getId() == null) {
            logger.error("opinion id is null");
            throw (new MessageException("opinion id can't be null"));
        }

        logger.debug("Deleting OpinionShareCount with component id = "
                + opinionShareCount.getId().getOpinion().getId());
        return repository.update(opinionShareCount);
	}

	@Override
	public OpinionShareCount getByComponentId(Long componentId) throws MessageException {
		logger.info("Entered OpinionShareCount service getByComponentId method");

        if (componentId == null) {
            logger.error("opinion id is null");
            throw (new MessageException("opinion id can't be null"));
        }

        logger.debug("Getting OpinionShareCount with component id = " + componentId);
        return repository.findByComponentId(componentId);
	}
}
