package com.ohmuk.folitics.hibernate.service.verdict.lookup;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.MaritalStatus;
import com.ohmuk.folitics.hibernate.repository.verdict.lookup.IVerdictLookupRepository;

/**
 * Service implementation for {@link Verdict} {@link MaritalStatus} lookup entity
 * @author Abhishek
 *
 */
@Service
@Transactional
public class VerdictMaritalStatusLookupService implements IVerdictLookupService<MaritalStatus> {

    private static Logger logger = LoggerFactory.getLogger(VerdictMaritalStatusLookupService.class);

    @Autowired
    private IVerdictLookupRepository<MaritalStatus> repository;

    @Override
    public MaritalStatus create(MaritalStatus maritalStatus) throws MessageException {

        logger.debug("Entered VerdictMaritalStatusLookupService create method");

        if (maritalStatus == null) {
            logger.error("MaritalStatus object found null in VerdictMaritalStatusLookupService.create method");
            throw (new MessageException("MaritalStatus object can't be null"));
        }

        if (maritalStatus.getMaritalStatus() == null) {
            logger.error("MaritalStatus value in MaritalStatus object found null in VerdictMaritalStatusLookupService.create method");
            throw (new MessageException("MaritalStatus value in MaritalStatus object can't be null"));
        }

        logger.debug("Trying to save the MaritalStatus object with MaritalStatus value = "
                + maritalStatus.getMaritalStatus());

        maritalStatus = repository.save(maritalStatus);

        logger.debug("Saved MaritalStatus object in the database. Exiting VerdictMaritalStatusLookupService.create method");

        return maritalStatus;
    }

    @Override
    public MaritalStatus read(Long id) throws MessageException {

        logger.debug("Entered VerdictMaritalStatusLookupService read method");

        if (id == null) {
            logger.error("id found null in VerdictMaritalStatusLookupService.read method");
            throw (new MessageException("id can't be null"));
        }

        logger.debug("Trying to get the MaritalStatus object for id = " + id);

        MaritalStatus maritalStatus = repository.find(id);

        logger.debug("Got MaritalStatus object from the database. Exiting VerdictMaritalStatusLookupService.read method");

        return maritalStatus;
    }

    @Override
    public List<MaritalStatus> readAll() {

        logger.debug("Entered VerdictMaritalStatusLookupService readAll method");
        logger.debug("Trying to get all the MaritalStatus objects from database");

        List<MaritalStatus> maritalStatuss = repository.findAll();

        logger.debug("Got all the MaritalStatus objects from database. Exiting VerdictMaritalStatusLookupService.readAll method");

        return maritalStatuss;
    }

    @Override
    public MaritalStatus readByValue(Object value) throws MessageException {

        logger.debug("Entered VerdictMaritalStatusLookupService readByValue method");

        if (value == null) {
            logger.error("Value found null in VerdictMaritalStatusLookupService.readByValue method");
            throw (new MessageException("value can't be null"));
        }

        String maritalStatusValue = (String) value;

        logger.debug("Trying to get the MaritalStatus object for maritalStatus value = " + maritalStatusValue);

        MaritalStatus maritalStatus = repository.findByValue(maritalStatusValue);

        logger.debug("Got MaritalStatus object from the database. Exiting VerdictMaritalStatusLookupService.readByValue method");

        return maritalStatus;
    }

    @Override
    public MaritalStatus update(MaritalStatus maritalStatus) throws MessageException {

        logger.debug("Entered VerdictMaritalStatusLookupService update method");

        if (maritalStatus == null) {
            logger.error("MaritalStatus object found null in VerdictMaritalStatusLookupService.update method");
            throw (new MessageException("MaritalStatus object can't be null"));
        }

        if (maritalStatus.getMaritalStatus() == null) {
            logger.error("MaritalStatus value in MaritalStatus object found null in VerdictMaritalStatusLookupService.create method");
            throw (new MessageException("MaritalStatus value in MaritalStatus object can't be null"));
        }

        logger.debug("Trying to update MaritalStatus object in databse for id = " + maritalStatus.getId());

        repository.update(maritalStatus);

        logger.debug("Updated MaritalStatus in database. Exiting VerdictMaritalStatusLookupService.update method");

        return maritalStatus;
    }

    @Override
    public MaritalStatus delete(Long id) throws MessageException {

        logger.debug("Entered VerdictMaritalStatusLookupService delete method");

        if (id == null) {
            logger.error("Id found null in VerdictMaritalStatusLookupService.delete method");
            throw (new MessageException("Id can't be null"));
        }

        logger.debug("Trying to get the object for MaritalStatus with id = " + id);

        repository.delete(id);

        logger.debug("Trying to get the object for MaritalStatus with id = " + id);

        MaritalStatus maritalStatus = repository.find(id);

        logger.debug("Deleted MaritalStatus object from database with id = " + maritalStatus.getId()
                + ". Exiting VerdictMaritalStatusLookupService.delete method");

        return maritalStatus;
    }

    @Override
    public MaritalStatus delete(MaritalStatus maritalStatus) throws MessageException {

        logger.debug("Entered VerdictMaritalStatusLookupService delete method");

        if (maritalStatus == null) {
            logger.error("MaritalStatus object found null in VerdictMaritalStatusLookupService.delete method");
            throw (new MessageException("MaritalStatus object can't be null"));
        }

        if (maritalStatus.getId() == null) {
            logger.error("Id in MaritalStatus object found null in VerdictMaritalStatusLookupService.delete method");
            throw (new MessageException("Id in MaritalStatus object can't be null"));
        }

        logger.debug("Trying to get the object for MaritalStatus with id = " + maritalStatus.getId());

        repository.delete(maritalStatus.getId());

        logger.debug("Trying to get the object for MaritalStatus with id = " + maritalStatus.getId());

        maritalStatus = repository.find(maritalStatus.getId());

        logger.debug("Deleted MaritalStatus object from database with id = " + maritalStatus.getId()
                + ". Exiting VerdictMaritalStatusLookupService.delete method");

        return maritalStatus;
    }

}
