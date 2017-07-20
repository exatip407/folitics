package com.ohmuk.folitics.hibernate.repository.verdict.lookup;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Region;

/**
 * Hibernate repository implementation for {@link Region} lookup
 * @author Abhishek
 *
 */
@Component
@Repository
public class VerdictRegionLookupRepository implements IVerdictLookupRepository<Region> {

    private static Logger logger = LoggerFactory.getLogger(VerdictRegionLookupRepository.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Region save(Region region) {

        logger.debug("Entered VerdictRegionLookupRepository save method");
        logger.debug("Trying to save Region with value = " + region.getRegion());

        Long id = (Long) getSession().save(region);

        logger.debug("Saved Region object and now getting Region object from database");

        region = (Region) getSession().get(Region.class, id);

        logger.debug("Got Region object from database. Exiting VerdictRegionLookupRepository save method");

        return region;
    }

    @Override
    public Region find(Long id) {

        logger.debug("Entered VerdictRegionLookupRepository find method");
        logger.debug("Trying to get Region for id = " + id);

        Region region = (Region) getSession().get(Region.class, id);

        logger.debug("Got Region object from database. Exiting VerdictRegionLookupRepository find method");

        return region;
    }

    @Override
    public List<Region> findAll() {

        logger.debug("Entered VerdictRegionLookupRepository findAll method");
        logger.debug("Trying to get all Region objects");

        Criteria selectAllCriteria = getSession().createCriteria(Region.class);
        @SuppressWarnings("unchecked")
        List<Region> regiones = selectAllCriteria.list();

        logger.debug("Got all Regions objects from database. Exiting VerdictRegionLookupRepository findAll method");

        return regiones;
    }

    @Override
    public Region findByValue(Object value) {

        logger.debug("Entered VerdictRegionLookupRepository findByValue method");

        String regionValue = (String) value;

        logger.debug("Trying to get Region for Region value = " + regionValue);

        Criteria selectAllCriteria = getSession().createCriteria(Region.class);
        selectAllCriteria.add(Restrictions.eq("region", regionValue));
        Region region = (Region) selectAllCriteria.uniqueResult();

        logger.debug("Got Region object from database. Exiting VerdictRegionLookupRepository findByValue method");

        return region;
    }

    @Override
    public Region update(Region region) {

        logger.debug("Entered VerdictRegionLookupRepository update method");
        logger.debug("Merging the object first with id = " + region.getId());

        region = (Region) getSession().merge(region);

        logger.debug("Now updating the Region object in database with id = " + region.getId());

        getSession().update(region);

        logger.debug("Getting the Region object from database");

        region = (Region) getSession().get(Region.class, region.getId());

        logger.debug("Got Region object from database. Exiting VerdictRegionLookupRepository update method");

        return region;
    }

    @Override
    public void delete(Long id) {

        logger.debug("Entered VerdictRegionLookupRepository delete method");
        logger.debug("Trying to get Region with id = " + id);

        Region region = (Region) getSession().get(Region.class, id);

        logger.debug("Now trying to delete the Region object with id = " + id);

        getSession().delete(region);

        logger.debug("Deleted Region object from database. Exiting VerdictRegionLookupRepository delete method");
    }

}
