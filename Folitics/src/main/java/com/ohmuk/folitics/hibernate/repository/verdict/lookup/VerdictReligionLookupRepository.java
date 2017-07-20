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

import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Religion;

/**
 * Hibernate repository implementation for {@link Religion} lookup
 * @author Abhishek
 *
 */
@Component
@Repository
public class VerdictReligionLookupRepository implements IVerdictLookupRepository<Religion> {

    private static Logger logger = LoggerFactory.getLogger(VerdictReligionLookupRepository.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Religion save(Religion religion) {

        logger.debug("Entered VerdictReligionLookupRepository save method");
        logger.debug("Trying to save Religion with value = " + religion.getReligion());

        Long id = (Long) getSession().save(religion);

        logger.debug("Saved Religion object and now getting Religion object from database");

        religion = (Religion) getSession().get(Religion.class, id);

        logger.debug("Got Religion object from database. Exiting VerdictReligionLookupRepository save method");

        return religion;
    }

    @Override
    public Religion find(Long id) {

        logger.debug("Entered VerdictReligionLookupRepository find method");
        logger.debug("Trying to get Religion for id = " + id);

        Religion religion = (Religion) getSession().get(Religion.class, id);

        logger.debug("Got Religion object from database. Exiting VerdictReligionLookupRepository find method");

        return religion;
    }

    @Override
    public List<Religion> findAll() {

        logger.debug("Entered VerdictReligionLookupRepository findAll method");
        logger.debug("Trying to get all Religion objects");

        Criteria selectAllCriteria = getSession().createCriteria(Religion.class);
        @SuppressWarnings("unchecked")
        List<Religion> religiones = selectAllCriteria.list();

        logger.debug("Got all Religions objects from database. Exiting VerdictReligionLookupRepository findAll method");

        return religiones;
    }

    @Override
    public Religion findByValue(Object value) {

        logger.debug("Entered VerdictReligionLookupRepository findByValue method");

        String religionValue = (String) value;

        logger.debug("Trying to get Religion for religion value = " + religionValue);

        Criteria selectAllCriteria = getSession().createCriteria(Religion.class);
        selectAllCriteria.add(Restrictions.eq("religion", religionValue));
        Religion religion = (Religion) selectAllCriteria.uniqueResult();

        logger.debug("Got Religion object from database. Exiting VerdictReligionLookupRepository findByValue method");

        return religion;
    }

    @Override
    public Religion update(Religion religion) {

        logger.debug("Entered VerdictReligionLookupRepository update method");
        logger.debug("Merging the object first with id = " + religion.getId());

        religion = (Religion) getSession().merge(religion);

        logger.debug("Now updating the Religion object in database with id = " + religion.getId());

        getSession().update(religion);

        logger.debug("Getting the Religion object from database");

        religion = (Religion) getSession().get(Religion.class, religion.getId());

        logger.debug("Got Religion object from database. Exiting VerdictReligionLookupRepository update method");

        return religion;
    }

    @Override
    public void delete(Long id) {

        logger.debug("Entered VerdictReligionLookupRepository delete method");
        logger.debug("Trying to get Religion with id = " + id);

        Religion religion = (Religion) getSession().get(Religion.class, id);

        logger.debug("Now trying to delete the Religion object with id = " + id);

        getSession().delete(religion);

        logger.debug("Deleted Religion object from database. Exiting VerdictReligionLookupRepository delete method");
    }

}
