package com.ohmuk.folitics.hibernate.service.share;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.share.GovtSchemeDataShareCount;
import com.ohmuk.folitics.hibernate.repository.share.IGovtSchemeDataShareCountRepository;
import com.ohmuk.folitics.hibernate.repository.share.ISentimentShareCountRepository;

/**
 * Service implementation for entity: {@link GovtSchemeDataShareCount}
 * @author Abhishek
 *
 */

@Service
@Transactional
public class GovtSchemeDataShareCountService implements IShareCountService<GovtSchemeDataShareCount>{
	
	private static Logger logger = LoggerFactory.getLogger(GovtSchemeDataShareCountService.class);

    @Autowired
    private IGovtSchemeDataShareCountRepository repository;

	@Override
	public GovtSchemeDataShareCount create(GovtSchemeDataShareCount govtSchemeDataShareCount) throws MessageException {
		logger.info("Entered GovtSchemeDataShareCount service create method");

        if (govtSchemeDataShareCount.getId().getGovtSchemeData() == null) {
            logger.error("govtSchemeData in GovtSchemeDataShareCount is null");
            throw (new MessageException("govtSchemeData in GovtSchemeDataShareCount can't be null"));
        }

        if (govtSchemeDataShareCount.getId().getGovtSchemeData().getId() == null) {
            logger.error("govtSchemeData id is null");
            throw (new MessageException("govtSchemeData id can't be null"));
        }

        logger.debug("Saving GovtSchemeDataShareCount with component id = "
                + govtSchemeDataShareCount.getId().getGovtSchemeData().getId());

        return repository.save(govtSchemeDataShareCount);
	}

	@Override
	public List<GovtSchemeDataShareCount> readAll() {
		logger.info("Entered GovtSchemeDataShareCount service readAll method");
        logger.debug("Getting all GovtSchemeDataShareCount");
        return repository.findAll();
	}

	@Override
	public GovtSchemeDataShareCount update(GovtSchemeDataShareCount govtSchemeDataShareCount) throws MessageException {
		logger.info("Entered GovtSchemeDataShareCount service update method");

        if (govtSchemeDataShareCount.getId().getGovtSchemeData() == null) {
            logger.error("govtSchemeData in GovtSchemeDataShareCount is null");
            throw (new MessageException("govtSchemeData in GovtSchemeDataShareCount can't be null"));
        }

        if (govtSchemeDataShareCount.getId().getGovtSchemeData().getId() == null) {
            logger.error("govtSchemeData id is null");
            throw (new MessageException("govtSchemeData id can't be null"));
        }

        logger.debug("Updating GovtSchemeDataShareCount with component id = "
                + govtSchemeDataShareCount.getId().getGovtSchemeData().getId());
        return repository.update(govtSchemeDataShareCount);
	}

	@Override
	public GovtSchemeDataShareCount delete(GovtSchemeDataShareCount govtSchemeDataShareCount) throws MessageException {
		logger.info("Entered GovtSchemeDataShareCount service delete method");

        if (govtSchemeDataShareCount.getId().getGovtSchemeData() == null) {
            logger.error("govtSchemeData in GovtSchemeDataShareCount is null");
            throw (new MessageException("govtSchemeData in GovtSchemeDataShareCount can't be null"));
        }

        if (govtSchemeDataShareCount.getId().getGovtSchemeData().getId() == null) {
            logger.error("govtSchemeData id is null");
            throw (new MessageException("govtSchemeData id can't be null"));
        }

        logger.debug("Deleting GovtSchemeDataShareCount with component id = "
                + govtSchemeDataShareCount.getId().getGovtSchemeData().getId());
        return repository.update(govtSchemeDataShareCount);
	}

	@Override
	public GovtSchemeDataShareCount getByComponentId(Long componentId) throws MessageException {
		logger.info("Entered GovtSchemeDataShareCount service getByComponentId method");

        if (componentId == null) {
            logger.error("govtSchemeData id is null");
            throw (new MessageException("govtSchemeData id can't be null"));
        }

        logger.debug("Getting GovtSchemeDataShareCount with component id = " + componentId);
        return repository.findByComponentId(componentId);
	}

}
