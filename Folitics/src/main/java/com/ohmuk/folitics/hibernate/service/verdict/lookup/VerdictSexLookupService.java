package com.ohmuk.folitics.hibernate.service.verdict.lookup;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Sex;
import com.ohmuk.folitics.hibernate.repository.verdict.lookup.IVerdictLookupRepository;

/**
 * Service implementation for {@link Verdict} {@link Sex} lookup entity
 * @author Abhishek
 *
 */
@Service
@Transactional
public class VerdictSexLookupService implements IVerdictLookupService<Sex> {

    private static Logger logger = LoggerFactory.getLogger(VerdictSexLookupService.class);

    @Autowired
    private IVerdictLookupRepository<Sex> repository;

    @Override
    public Sex create(Sex sex) throws MessageException {

        logger.debug("Entered VerdictSexLookupService create method");

        if (sex == null) {
            logger.error("Sex object found null in VerdictSexLookupService.create method");
            throw (new MessageException("Sex object can't be null"));
        }

        if (sex.getSex() == null) {
            logger.error("Sex value in Sex object found null in VerdictSexLookupService.create method");
            throw (new MessageException("Sex value in Sex object can't be null"));
        }

        logger.debug("Trying to save the Sex object with Sex value = " + sex.getSex());

        sex = repository.save(sex);

        logger.debug("Saved Sex object in the database. Exiting VerdictSexLookupService.create method");

        return sex;
    }

    @Override
    public Sex read(Long id) throws MessageException {

        logger.debug("Entered VerdictSexLookupService read method");

        if (id == null) {
            logger.error("id found null in VerdictSexLookupService.read method");
            throw (new MessageException("id can't be null"));
        }

        logger.debug("Trying to get the Sex object for id = " + id);

        Sex sex = repository.find(id);

        logger.debug("Got Sex object from the database. Exiting VerdictSexLookupService.read method");

        return sex;
    }

    @Override
    public List<Sex> readAll() {

        logger.debug("Entered VerdictSexLookupService readAll method");
        logger.debug("Trying to get all the Sex objects from database");

        List<Sex> sexs = repository.findAll();

        logger.debug("Got all the Sex objects from database. Exiting VerdictSexLookupService.readAll method");

        return sexs;
    }

    @Override
    public Sex readByValue(Object value) throws MessageException {

        logger.debug("Entered VerdictSexLookupService readByValue method");

        if (value == null) {
            logger.error("Value found null in VerdictSexLookupService.readByValue method");
            throw (new MessageException("value can't be null"));
        }

        String sexValue = (String) value;

        logger.debug("Trying to get the Sex object for sex value = " + sexValue);

        Sex sex = repository.findByValue(sexValue);

        logger.debug("Got Sex object from the database. Exiting VerdictSexLookupService.readByValue method");

        return sex;
    }

    @Override
    public Sex update(Sex sex) throws MessageException {

        logger.debug("Entered VerdictSexLookupService update method");

        if (sex == null) {
            logger.error("Sex object found null in VerdictSexLookupService.update method");
            throw (new MessageException("Sex object can't be null"));
        }

        if (sex.getSex() == null) {
            logger.error("Sex value in Sex object found null in VerdictSexLookupService.update method");
            throw (new MessageException("Sex value in Sex object can't be null"));
        }

        logger.debug("Trying to update Sex object in databse for id = " + sex.getId());

        repository.update(sex);

        logger.debug("Updated Sex in database. Exiting VerdictSexLookupService.update method");

        return sex;
    }

    @Override
    public Sex delete(Long id) throws MessageException {

        logger.debug("Entered VerdictSexLookupService delete method");

        if (id == null) {
            logger.error("Id found null in VerdictSexLookupService.delete method");
            throw (new MessageException("Id can't be null"));
        }

        logger.debug("Trying to get the object for Sex with id = " + id);

        repository.delete(id);

        logger.debug("Trying to get the object for Sex with id = " + id);

        Sex sex = repository.find(id);

        logger.debug("Deleted Sex object from database with id = " + sex.getId()
                + ". Exiting VerdictSexLookupService.delete method");

        return sex;
    }

    @Override
    public Sex delete(Sex sex) throws MessageException {

        logger.debug("Entered VerdictSexLookupService delete method");

        if (sex == null) {
            logger.error("Sex object found null in VerdictSexLookupService.delete method");
            throw (new MessageException("Sex object can't be null"));
        }

        if (sex.getId() == null) {
            logger.error("Id in Sex object found null in VerdictSexLookupService.delete method");
            throw (new MessageException("Id in Sex object can't be null"));
        }

        logger.debug("Trying to get the object for Sex with id = " + sex.getId());

        repository.delete(sex.getId());

        logger.debug("Trying to get the object for Sex with id = " + sex.getId());

        sex = repository.find(sex.getId());

        logger.debug("Deleted Sex object from database with id = " + sex.getId()
                + ". Exiting VerdictSexLookupService.delete method");

        return sex;
    }

}
