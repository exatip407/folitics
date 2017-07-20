package com.ohmuk.folitics.hibernate.repository.verdict.lookup;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ohmuk.folitics.hibernate.entity.verdict.lookup.RegionState;

/**
 * Hibernate repository implementation for {@link RegionState}
 * @author Abhishek
 *
 */
@Component
@Repository
public class RegionStateRepository implements IRegionStateRepository {

    private static Logger logger = LoggerFactory.getLogger(RegionStateRepository.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public RegionState getRegionForState(String state) {

        logger.debug("Entered RegionStateRepository getRegionForState method");

        logger.debug("Trying to get Region for state = " + state);

        Criteria selectRegionForStateCriteria = getSession().createCriteria(RegionState.class);
        selectRegionForStateCriteria.add(Restrictions.eq("id.state", state));
        RegionState regionState = (RegionState) selectRegionForStateCriteria.uniqueResult();

        logger.debug("Got RegionState object from database. Exiting RegionStateRepository getRegionForState method");

        return regionState;
    }

}
