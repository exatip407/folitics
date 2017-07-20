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

import com.ohmuk.folitics.hibernate.entity.verdict.lookup.MaritalStatus;

/**
 * Hibernate repository implementation for {@link MaritalStatus} lookup
 * @author Abhishek
 *
 */
@Component
@Repository
public class VerdictMaritalStatusLookupRepository implements IVerdictLookupRepository<MaritalStatus> {

    private static Logger logger = LoggerFactory.getLogger(VerdictMaritalStatusLookupRepository.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public MaritalStatus save(MaritalStatus maritalStatus) {

        logger.debug("Entered VerdictMaritalStatusLookupRepository save method");
        logger.debug("Trying to save MaritalStatus with value = " + maritalStatus.getMaritalStatus());

        Long id = (Long) getSession().save(maritalStatus);

        logger.debug("Saved MaritalStatus object and now getting MaritalStatus object from database");

        maritalStatus = (MaritalStatus) getSession().get(MaritalStatus.class, id);

        logger.debug("Got MaritalStatus object from database. Exiting VerdictMaritalStatusLookupRepository save method");

        return maritalStatus;
    }

    @Override
    public MaritalStatus find(Long id) {

        logger.debug("Entered VerdictMaritalStatusLookupRepository find method");
        logger.debug("Trying to get MaritalStatus for id = " + id);

        MaritalStatus maritalStatus = (MaritalStatus) getSession().get(MaritalStatus.class, id);

        logger.debug("Got MaritalStatus object from database. Exiting VerdictMaritalStatusLookupRepository find method");

        return maritalStatus;
    }

    @Override
    public List<MaritalStatus> findAll() {

        logger.debug("Entered VerdictMaritalStatusLookupRepository findAll method");
        logger.debug("Trying to get all MaritalStatus objects");

        Criteria selectAllCriteria = getSession().createCriteria(MaritalStatus.class);
        @SuppressWarnings("unchecked")
        List<MaritalStatus> maritalStatuses = selectAllCriteria.list();

        logger.debug("Got all MaritalStatuses objects from database. Exiting VerdictMaritalStatusLookupRepository findAll method");

        return maritalStatuses;
    }

    @Override
    public MaritalStatus findByValue(Object value) {

        logger.debug("Entered VerdictMaritalStatusLookupRepository findByValue method");

        String maritalStatusValue = (String) value;

        logger.debug("Trying to get MaritalStatus for marital status = " + maritalStatusValue);

        Criteria selectAllCriteria = getSession().createCriteria(MaritalStatus.class);
        selectAllCriteria.add(Restrictions.eq("maritalStatus", maritalStatusValue));
        MaritalStatus maritalStatus = (MaritalStatus) selectAllCriteria.uniqueResult();

        logger.debug("Got MaritalStatus object from database. Exiting VerdictMaritalStatusLookupRepository findByValue method");

        return maritalStatus;
    }

    @Override
    public MaritalStatus update(MaritalStatus maritalStatus) {

        logger.debug("Entered VerdictMaritalStatusLookupRepository update method");
        logger.debug("Merging the object first with id = " + maritalStatus.getId());

        maritalStatus = (MaritalStatus) getSession().merge(maritalStatus);

        logger.debug("Now updating the MaritalStatus object in database with id = " + maritalStatus.getId());

        getSession().update(maritalStatus);

        logger.debug("Getting the MaritalStatus object from database");

        maritalStatus = (MaritalStatus) getSession().get(MaritalStatus.class, maritalStatus.getId());

        logger.debug("Got MaritalStatus object from database. Exiting VerdictMaritalStatusLookupRepository update method");

        return maritalStatus;
    }

    @Override
    public void delete(Long id) {

        logger.debug("Entered VerdictMaritalStatusLookupRepository delete method");
        logger.debug("Trying to get MaritalStatus with id = " + id);

        MaritalStatus maritalStatus = (MaritalStatus) getSession().get(MaritalStatus.class, id);

        logger.debug("Now trying to delete the MaritalStatus object with id = " + id);

        getSession().delete(maritalStatus);

        logger.debug("Deleted MaritalStatus object from database. Exiting VerdictMaritalStatusLookupRepository delete method");
    }

}
