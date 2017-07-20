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

import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Sex;

/**
 * Hibernate repository implementation for {@link Sex} lookup
 * @author Abhishek
 *
 */
@Component
@Repository
public class VerdictSexLookupRepository implements IVerdictLookupRepository<Sex> {

    private static Logger logger = LoggerFactory.getLogger(VerdictSexLookupRepository.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Sex save(Sex sex) {

        logger.debug("Entered VerdictSexLookupRepository save method");
        logger.debug("Trying to save Sex with value = " + sex.getSex());

        Long id = (Long) getSession().save(sex);

        logger.debug("Saved Sex object and now getting Sex object from database");

        sex = (Sex) getSession().get(Sex.class, id);

        logger.debug("Got Sex object from database. Exiting VerdictSexLookupRepository save method");

        return sex;
    }

    @Override
    public Sex find(Long id) {

        logger.debug("Entered VerdictSexLookupRepository find method");
        logger.debug("Trying to get Sex for id = " + id);

        Sex sex = (Sex) getSession().get(Sex.class, id);

        logger.debug("Got Sex object from database. Exiting VerdictSexLookupRepository find method");

        return sex;
    }

    @Override
    public List<Sex> findAll() {

        logger.debug("Entered VerdictSexLookupRepository findAll method");
        logger.debug("Trying to get all Sex objects");

        Criteria selectAllCriteria = getSession().createCriteria(Sex.class);
        @SuppressWarnings("unchecked")
        List<Sex> sexes = selectAllCriteria.list();

        logger.debug("Got all Sexes objects from database. Exiting VerdictSexLookupRepository findAll method");

        return sexes;
    }

    @Override
    public Sex findByValue(Object value) {

        logger.debug("Entered VerdictSexLookupRepository findByValue method");

        String sexValue = (String) value;

        logger.debug("Trying to get Sex for sex value = " + sexValue);

        Criteria selectAllCriteria = getSession().createCriteria(Sex.class);
        selectAllCriteria.add(Restrictions.eq("sex", sexValue));
        Sex sex = (Sex) selectAllCriteria.uniqueResult();

        logger.debug("Got Sex object from database. Exiting VerdictSexLookupRepository findByValue method");

        return sex;
    }

    @Override
    public Sex update(Sex sex) {

        logger.debug("Entered VerdictSexLookupRepository update method");
        logger.debug("Merging the object first with id = " + sex.getId());

        sex = (Sex) getSession().merge(sex);

        logger.debug("Now updating the Sex object in database with id = " + sex.getId());

        getSession().update(sex);

        logger.debug("Getting the Sex object from database");

        sex = (Sex) getSession().get(Sex.class, sex.getId());

        logger.debug("Got Sex object from database. Exiting VerdictSexLookupRepository update method");

        return sex;
    }

    @Override
    public void delete(Long id) {

        logger.debug("Entered VerdictSexLookupRepository delete method");
        logger.debug("Trying to get Sex with id = " + id);

        Sex sex = (Sex) getSession().get(Sex.class, id);

        logger.debug("Now trying to delete the Sex object with id = " + id);

        getSession().delete(sex);

        logger.debug("Deleted Sex object from database. Exiting VerdictSexLookupRepository delete method");
    }

}
