package com.ohmuk.folitics.hibernate.service.verdict.lookup;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Religion;
import com.ohmuk.folitics.hibernate.repository.verdict.lookup.IVerdictLookupRepository;

/**
 * Service implementation for {@link Verdict} {@link Religion} lookup entity
 * @author Abhishek
 *
 */
@Service
@Transactional
public class VerdictReligionLookupService implements IVerdictLookupService<Religion> {

    private static Logger logger = LoggerFactory.getLogger(VerdictReligionLookupService.class);

    @Autowired
    private IVerdictLookupRepository<Religion> repository;

    @Override
    public Religion create(Religion religion) throws MessageException {

        logger.debug("Entered VerdictReligionLookupService create method");

        if (religion == null) {
            logger.error("Religion object found null in VerdictReligionLookupService.create method");
            throw (new MessageException("Religion object can't be null"));
        }

        if (religion.getReligion() == null) {
            logger.error("Religion value in Religion object found null in VerdictReligionLookupService.create method");
            throw (new MessageException("Religion value in Religion object can't be null"));
        }

        logger.debug("Trying to save the Religion object with Religion value = " + religion.getReligion());

        religion = repository.save(religion);

        logger.debug("Saved Religion object in the database. Exiting VerdictReligionLookupService.create method");

        return religion;
    }

    @Override
    public Religion read(Long id) throws MessageException {

        logger.debug("Entered VerdictReligionLookupService read method");

        if (id == null) {
            logger.error("id found null in VerdictReligionLookupService.read method");
            throw (new MessageException("id can't be null"));
        }

        logger.debug("Trying to get the Religion object for id = " + id);

        Religion religion = repository.find(id);

        logger.debug("Got Religion object from the database. Exiting VerdictReligionLookupService.read method");

        return religion;
    }

    @Override
    public List<Religion> readAll() {

        logger.debug("Entered VerdictReligionLookupService readAll method");
        logger.debug("Trying to get all the Religion objects from database");

        List<Religion> religions = repository.findAll();

        logger.debug("Got all the Religion objects from database. Exiting VerdictReligionLookupService.readAll method");

        return religions;
    }

    @Override
    public Religion readByValue(Object value) throws MessageException {

        logger.debug("Entered VerdictReligionLookupService readByValue method");

        if (value == null) {
            logger.error("Value found null in VerdictReligionLookupService.readByValue method");
            throw (new MessageException("value can't be null"));
        }

        String religionValue = (String) value;

        logger.debug("Trying to get the Religion object for religion value = " + religionValue);

        Religion religion = repository.findByValue(religionValue);

        logger.debug("Got Religion object from the database. Exiting VerdictReligionLookupService.readByValue method");

        return religion;
    }

    @Override
    public Religion update(Religion religion) throws MessageException {

        logger.debug("Entered VerdictReligionLookupService update method");

        if (religion == null) {
            logger.error("Religion object found null in VerdictReligionLookupService.update method");
            throw (new MessageException("Religion object can't be null"));
        }

        if (religion.getReligion() == null) {
            logger.error("Religion value in Religion object found null in VerdictReligionLookupService.update method");
            throw (new MessageException("Religion value in Religion object can't be null"));
        }

        logger.debug("Trying to update Religion object in databse for id = " + religion.getId());

        repository.update(religion);

        logger.debug("Updated Religion in database. Exiting VerdictReligionLookupService.update method");

        return religion;
    }

    @Override
    public Religion delete(Long id) throws MessageException {

        logger.debug("Entered VerdictReligionLookupService delete method");

        if (id == null) {
            logger.error("Id found null in VerdictReligionLookupService.delete method");
            throw (new MessageException("Id can't be null"));
        }

        logger.debug("Trying to get the object for Religion with id = " + id);

        repository.delete(id);

        logger.debug("Trying to get the object for Religion with id = " + id);

        Religion religion = repository.find(id);

        logger.debug("Deleted Religion object from database with id = " + religion.getId()
                + ". Exiting VerdictReligionLookupService.delete method");

        return religion;
    }

    @Override
    public Religion delete(Religion religion) throws MessageException {

        logger.debug("Entered VerdictReligionLookupService delete method");

        if (religion == null) {
            logger.error("Religion object found null in VerdictReligionLookupService.delete method");
            throw (new MessageException("Religion object can't be null"));
        }

        if (religion.getId() == null) {
            logger.error("Id in Religion object found null in VerdictReligionLookupService.delete method");
            throw (new MessageException("Id in Religion object can't be null"));
        }

        logger.debug("Trying to get the object for Religion with id = " + religion.getId());

        repository.delete(religion.getId());

        logger.debug("Trying to get the object for Religion with id = " + religion.getId());

        religion = repository.find(religion.getId());

        logger.debug("Deleted Religion object from database with id = " + religion.getId()
                + ". Exiting VerdictReligionLookupService.delete method");

        return religion;
    }

}
