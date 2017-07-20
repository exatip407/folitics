package com.ohmuk.folitics.hibernate.service.verdict.lookup;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Region;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.RegionState;
import com.ohmuk.folitics.hibernate.repository.verdict.lookup.IRegionStateRepository;

/**
 * Service implementation for {@link RegionState}
 * @author Abhishek
 *
 */
@Service
@Transactional
public class RegionStateService implements IRegionStateService {

    private static Logger logger = LoggerFactory.getLogger(RegionStateService.class);

    @Autowired
    private IRegionStateRepository repository;

    @Override
    public Region getRegionForState(String state) throws MessageException {

        logger.debug("Entered RegionStateService getRegionForState method");

        if (state == null) {
            logger.error("State found null in RegionStateService.getRegionForState method");
            throw (new MessageException("State can't be null"));
        }

        logger.debug("Trying to get the RegionState object for state = " + state);

        RegionState regionState = repository.getRegionForState(state);

        logger.debug("Got RegionState object from the database. Exiting RegionStateService.getRegionForState method");

        return regionState.getId().getRegion();
    }

}
