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

import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Qualification;

/**
 * Hibernate repository implementation for {@link Qualification} lookup
 * @author Abhishek
 *
 */
@Component
@Repository
public class VerdictQualificationLookupRepository implements IVerdictLookupRepository<Qualification> {

    private static Logger logger = LoggerFactory.getLogger(VerdictQualificationLookupRepository.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Qualification save(Qualification qualification) {

        logger.debug("Entered VerdictQualificationLookupRepository save method");
        logger.debug("Trying to save Qualification with value = " + qualification.getQualification());

        Long id = (Long) getSession().save(qualification);

        logger.debug("Saved Qualification object and now getting Qualification object from database");

        qualification = (Qualification) getSession().get(Qualification.class, id);

        logger.debug("Got Qualification object from database. Exiting VerdictQualificationLookupRepository save method");

        return qualification;
    }

    @Override
    public Qualification find(Long id) {

        logger.debug("Entered VerdictQualificationLookupRepository find method");
        logger.debug("Trying to get Qualification for id = " + id);

        Qualification qualification = (Qualification) getSession().get(Qualification.class, id);

        logger.debug("Got Qualification object from database. Exiting VerdictQualificationLookupRepository find method");

        return qualification;
    }

    @Override
    public List<Qualification> findAll() {

        logger.debug("Entered VerdictQualificationLookupRepository findAll method");
        logger.debug("Trying to get all Qualification objects");

        Criteria selectAllCriteria = getSession().createCriteria(Qualification.class);
        @SuppressWarnings("unchecked")
        List<Qualification> qualificationes = selectAllCriteria.list();

        logger.debug("Got all Qualifications objects from database. Exiting VerdictQualificationLookupRepository findAll method");

        return qualificationes;
    }

    @Override
    public Qualification findByValue(Object value) {

        logger.debug("Entered VerdictQualificationLookupRepository findByValue method");

        String qualificationValue = (String) value;

        logger.debug("Trying to get Qualification for qualification value = " + qualificationValue);

        Criteria selectAllCriteria = getSession().createCriteria(Qualification.class);
        selectAllCriteria.add(Restrictions.eq("qualification", qualificationValue));
        Qualification qualification = (Qualification) selectAllCriteria.uniqueResult();

        logger.debug("Got Qualification object from database. Exiting VerdictQualificationLookupRepository findByValue method");

        return qualification;
    }

    @Override
    public Qualification update(Qualification qualification) {

        logger.debug("Entered VerdictQualificationLookupRepository update method");
        logger.debug("Merging the object first with id = " + qualification.getId());

        qualification = (Qualification) getSession().merge(qualification);

        logger.debug("Now updating the Qualification object in database with id = " + qualification.getId());

        getSession().update(qualification);

        logger.debug("Getting the Qualification object from database");

        qualification = (Qualification) getSession().get(Qualification.class, qualification.getId());

        logger.debug("Got Qualification object from database. Exiting VerdictQualificationLookupRepository update method");

        return qualification;
    }

    @Override
    public void delete(Long id) {

        logger.debug("Entered VerdictQualificationLookupRepository delete method");
        logger.debug("Trying to get Qualification with id = " + id);

        Qualification qualification = (Qualification) getSession().get(Qualification.class, id);

        logger.debug("Now trying to delete the Qualification object with id = " + id);

        getSession().delete(qualification);

        logger.debug("Deleted Qualification object from database. Exiting VerdictQualificationLookupRepository delete method");
    }

}
