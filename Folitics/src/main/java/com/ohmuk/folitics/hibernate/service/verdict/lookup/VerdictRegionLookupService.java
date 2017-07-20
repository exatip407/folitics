package com.ohmuk.folitics.hibernate.service.verdict.lookup;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Region;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.RegionState;
import com.ohmuk.folitics.hibernate.repository.verdict.lookup.IRegionStateRepository;
import com.ohmuk.folitics.hibernate.repository.verdict.lookup.IVerdictLookupRepository;

/**
 * Service implementation for {@link Verdict} {@link Region} lookup entity
 * @author Abhishek
 *
 */
@Service
@Transactional
public class VerdictRegionLookupService implements IVerdictLookupService<Region> {

    private static Logger logger = LoggerFactory.getLogger(VerdictRegionLookupService.class);

    @Autowired
    private IVerdictLookupRepository<Region> repository;

    @Autowired
    private IRegionStateRepository regionStateRepository;

    @Override
    public Region create(Region region) throws MessageException {

        logger.debug("Entered VerdictRegionLookupService create method");

        if (region == null) {
            logger.error("Region object found null in VerdictRegionLookupService.create method");
            throw (new MessageException("Region object can't be null"));
        }

        if (region.getRegion() == null) {
            logger.error("Region value in Region object found null in VerdictRegionLookupService.create method");
            throw (new MessageException("Region value in Region object can't be null"));
        }

        logger.debug("Trying to save the Region object with Region value = " + region.getRegion());

        region = repository.save(region);

        logger.debug("Saved Region object in the database. Exiting VerdictRegionLookupService.create method");

        return region;
    }

    @Override
    public Region read(Long id) throws MessageException {

        logger.debug("Entered VerdictRegionLookupService read method");

        if (id == null) {
            logger.error("id found null in VerdictRegionLookupService.read method");
            throw (new MessageException("id can't be null"));
        }

        logger.debug("Trying to get the Region object for id = " + id);

        Region region = repository.find(id);

        logger.debug("Got Region object from the database. Exiting VerdictRegionLookupService.read method");

        return region;
    }

    @Override
    public List<Region> readAll() {

        logger.debug("Entered VerdictRegionLookupService readAll method");
        logger.debug("Trying to get all the Region objects from database");

        List<Region> regions = repository.findAll();

        logger.debug("Got all the Region objects from database. Exiting VerdictRegionLookupService.readAll method");

        return regions;
    }

    @Override
    public Region readByValue(Object value) throws MessageException {

        logger.debug("Entered VerdictRegionLookupService readByValue method");

        if (value == null) {
            logger.error("Value found null in VerdictRegionLookupService.readByValue method");
            throw (new MessageException("value can't be null"));
        }

        String regionValue = (String) value;

        logger.debug("Trying to get the Region object for region value = " + regionValue);

        Region region = repository.findByValue(regionValue);

        logger.debug("Got Region object from the database. Exiting VerdictRegionLookupService.readByValue method");

        return region;
    }

    public Region getRegionForState(String state) throws MessageException {

        logger.debug("Entered VerdictRegionLookupService getRegionForState method");

        if (state == null) {
            logger.error("State found null in VerdictRegionLookupService.getRegionForState method");
            throw (new MessageException("State can't be null"));
        }

        logger.debug("Trying to get the RegionState object for state = " + state);

        RegionState regionState = regionStateRepository.getRegionForState(state);

        return regionState.getId().getRegion();

    }

    @Override
    public Region update(Region region) throws MessageException {

        logger.debug("Entered VerdictRegionLookupService update method");

        if (region == null) {
            logger.error("Region object found null in VerdictRegionLookupService.update method");
            throw (new MessageException("Region object can't be null"));
        }

        if (region.getRegion() == null) {
            logger.error("Region value in Region object found null in VerdictRegionLookupService.update method");
            throw (new MessageException("Region value in Region object can't be null"));
        }

        logger.debug("Trying to update Region object in databse for id = " + region.getId());

        repository.update(region);

        logger.debug("Updated Region in database. Exiting VerdictRegionLookupService.update method");

        return region;
    }

    @Override
    public Region delete(Long id) throws MessageException {

        logger.debug("Entered VerdictRegionLookupService delete method");

        if (id == null) {
            logger.error("Id found null in VerdictRegionLookupService.delete method");
            throw (new MessageException("Id can't be null"));
        }

        logger.debug("Trying to get the object for Region with id = " + id);

        repository.delete(id);

        logger.debug("Trying to get the object for Region with id = " + id);

        Region region = repository.find(id);

        logger.debug("Deleted Region object from database with id = " + region.getId()
                + ". Exiting VerdictRegionLookupService.delete method");

        return region;
    }

    @Override
    public Region delete(Region region) throws MessageException {

        logger.debug("Entered VerdictRegionLookupService delete method");

        if (region == null) {
            logger.error("Region object found null in VerdictRegionLookupService.delete method");
            throw (new MessageException("Region object can't be null"));
        }

        if (region.getId() == null) {
            logger.error("Id in Region object found null in VerdictRegionLookupService.delete method");
            throw (new MessageException("Id in Region object can't be null"));
        }

        logger.debug("Trying to get the object for Region with id = " + region.getId());

        repository.delete(region.getId());

        logger.debug("Trying to get the object for Region with id = " + region.getId());

        region = repository.find(region.getId());

        logger.debug("Deleted Region object from database with id = " + region.getId()
                + ". Exiting VerdictRegionLookupService.delete method");

        return region;
    }

}
