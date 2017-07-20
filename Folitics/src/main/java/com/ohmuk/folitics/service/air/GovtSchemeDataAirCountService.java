package com.ohmuk.folitics.service.air;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.air.GovtSchemeDataAirCount;
import com.ohmuk.folitics.hibernate.repository.air.IGovtSchemeDataAirCountRepository;
import com.ohmuk.folitics.hibernate.service.share.IShareCountService;

public class GovtSchemeDataAirCountService implements IShareCountService<GovtSchemeDataAirCount>{

	private static Logger logger = LoggerFactory.getLogger(GovtSchemeDataAirCountService.class);

    @Autowired
    private IGovtSchemeDataAirCountRepository repository;
	
	@Override
	public GovtSchemeDataAirCount create(GovtSchemeDataAirCount govtSchemeDataAirCount) throws MessageException {
		logger.info("Entered GovtSchemeDataAirCount service create method");

        if (govtSchemeDataAirCount.getId().getGovtSchemeData() == null) {
            logger.error("govtSchemeData in GovtSchemeDataAirCount is null");
            throw (new MessageException("govtSchemeData in GovtSchemeDataAirCount can't be null"));
        }

        if (govtSchemeDataAirCount.getId().getGovtSchemeData().getId() == null) {
            logger.error("govtSchemeData id is null");
            throw (new MessageException("govtSchemeData id can't be null"));
        }

        logger.debug("Saving GovtSchemeDataAirCount with component id = "
                + govtSchemeDataAirCount.getId().getGovtSchemeData().getId());

        return repository.save(govtSchemeDataAirCount);
	}

	@Override
	public List<GovtSchemeDataAirCount> readAll() {
		logger.info("Entered GovtSchemeDataAirCount service readAll method");
        logger.debug("Getting all GovtSchemeDataAirCount");
        return repository.findAll();
	}

	@Override
	public GovtSchemeDataAirCount update(GovtSchemeDataAirCount govtSchemeDataAirCount) throws MessageException {
		logger.info("Entered GovtSchemeDataAirCount service update method");

        if (govtSchemeDataAirCount.getId().getGovtSchemeData() == null) {
            logger.error("govtSchemeData in GovtSchemeDataAirCount is null");
            throw (new MessageException("govtSchemeData in GovtSchemeDataAirCount can't be null"));
        }

        if (govtSchemeDataAirCount.getId().getGovtSchemeData().getId() == null) {
            logger.error("govtSchemeData id is null");
            throw (new MessageException("govtSchemeData id can't be null"));
        }

        logger.debug("Updating GovtSchemeDataAirCount with component id = "
                + govtSchemeDataAirCount.getId().getGovtSchemeData().getId());
        return repository.update(govtSchemeDataAirCount);
	}

	@Override
	public GovtSchemeDataAirCount delete(GovtSchemeDataAirCount govtSchemeDataAirCount) throws MessageException {
		logger.info("Entered GovtSchemeDataAirCount service delete method");

        if (govtSchemeDataAirCount.getId().getGovtSchemeData() == null) {
            logger.error("govtSchemeData in GovtSchemeDataAirCount is null");
            throw (new MessageException("govtSchemeData in GovtSchemeDataAirCount can't be null"));
        }

        if (govtSchemeDataAirCount.getId().getGovtSchemeData().getId() == null) {
            logger.error("govtSchemeData id is null");
            throw (new MessageException("govtSchemeData id can't be null"));
        }

        logger.debug("Deleting GovtSchemeDataAirCount with component id = "
                + govtSchemeDataAirCount.getId().getGovtSchemeData().getId());
        return repository.update(govtSchemeDataAirCount);
	}

	@Override
	public GovtSchemeDataAirCount getByComponentId(Long componentId) throws MessageException {
		logger.info("Entered GovtSchemeDataAirCount service getByComponentId method");

        if (componentId == null) {
            logger.error("govtSchemeData id is null");
            throw (new MessageException("govtSchemeData id can't be null"));
        }

        logger.debug("Getting GovtSchemeDataAirCount with component id = " + componentId);
        return repository.findByComponentId(componentId);
	}

}
