package com.ohmuk.folitics.hibernate.service.verdict.lookup;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Qualification;
import com.ohmuk.folitics.hibernate.repository.verdict.lookup.IVerdictLookupRepository;

/**
 * Service implementation for {@link Verdict} {@link Qualification} lookup entity
 * @author Abhishek
 *
 */
@Service
@Transactional
public class VerdictQualificationLookupService implements IVerdictLookupService<Qualification> {

    private static Logger logger = LoggerFactory.getLogger(VerdictQualificationLookupService.class);

    @Autowired
    private IVerdictLookupRepository<Qualification> repository;

    @Override
    public Qualification create(Qualification qualification) throws MessageException {

        logger.debug("Entered VerdictQualificationLookupService create method");

        if (qualification == null) {
            logger.error("Qualification object found null in VerdictQualificationLookupService.create method");
            throw (new MessageException("Qualification object can't be null"));
        }

        if (qualification.getQualification() == null) {
            logger.error("Qualification value in Qualification object found null in VerdictQualificationLookupService.create method");
            throw (new MessageException("StartAge in Qualification object can't be null"));
        }

        logger.debug("Trying to save the Qualification object with Qualification value = "
                + qualification.getQualification());

        qualification = repository.save(qualification);

        logger.debug("Saved Qualification object in the database. Exiting VerdictQualificationLookupService.create method");

        return qualification;
    }

    @Override
    public Qualification read(Long id) throws MessageException {

        logger.debug("Entered VerdictQualificationLookupService read method");

        if (id == null) {
            logger.error("id found null in VerdictQualificationLookupService.read method");
            throw (new MessageException("id can't be null"));
        }

        logger.debug("Trying to get the Qualification object for id = " + id);

        Qualification qualification = repository.find(id);

        logger.debug("Got Qualification object from the database. Exiting VerdictQualificationLookupService.read method");

        return qualification;
    }

    @Override
    public List<Qualification> readAll() {

        logger.debug("Entered VerdictQualificationLookupService readAll method");
        logger.debug("Trying to get all the Qualification objects from database");

        List<Qualification> qualifications = repository.findAll();

        logger.debug("Got all the Qualification objects from database. Exiting VerdictQualificationLookupService.readAll method");

        return qualifications;
    }

    @Override
    public Qualification readByValue(Object value) throws MessageException {

        logger.debug("Entered VerdictQualificationLookupService readByValue method");

        if (value == null) {
            logger.error("Value found null in VerdictQualificationLookupService.readByValue method");
            throw (new MessageException("value can't be null"));
        }

        String qualificationValue = (String) value;

        logger.debug("Trying to get the Qualification object for qualification value = " + qualificationValue);

        Qualification qualification = repository.findByValue(qualificationValue);

        logger.debug("Got Qualification object from the database. Exiting VerdictQualificationLookupService.readByValue method");

        return qualification;
    }

    @Override
    public Qualification update(Qualification qualification) throws MessageException {

        logger.debug("Entered VerdictQualificationLookupService update method");

        if (qualification == null) {
            logger.error("Qualification object found null in VerdictQualificationLookupService.update method");
            throw (new MessageException("Qualification object can't be null"));
        }

        if (qualification.getQualification() == null) {
            logger.error("Qualification value in Qualification object found null in VerdictQualificationLookupService.update method");
            throw (new MessageException("StartAge in Qualification object can't be null"));
        }

        logger.debug("Trying to update Qualification object in databse for id = " + qualification.getId());

        repository.update(qualification);

        logger.debug("Updated Qualification in database. Exiting VerdictQualificationLookupService.update method");

        return qualification;
    }

    @Override
    public Qualification delete(Long id) throws MessageException {

        logger.debug("Entered VerdictQualificationLookupService delete method");

        if (id == null) {
            logger.error("Id found null in VerdictQualificationLookupService.delete method");
            throw (new MessageException("Id can't be null"));
        }

        logger.debug("Trying to get the object for Qualification with id = " + id);

        repository.delete(id);

        logger.debug("Trying to get the object for Qualification with id = " + id);

        Qualification qualification = repository.find(id);

        logger.debug("Deleted Qualification object from database with id = " + qualification.getId()
                + ". Exiting VerdictQualificationLookupService.delete method");

        return qualification;
    }

    @Override
    public Qualification delete(Qualification qualification) throws MessageException {

        logger.debug("Entered VerdictQualificationLookupService delete method");

        if (qualification == null) {
            logger.error("Qualification object found null in VerdictQualificationLookupService.delete method");
            throw (new MessageException("Qualification object can't be null"));
        }

        if (qualification.getId() == null) {
            logger.error("Id in Qualification object found null in VerdictQualificationLookupService.delete method");
            throw (new MessageException("Id in Qualification object can't be null"));
        }

        logger.debug("Trying to get the object for Qualification with id = " + qualification.getId());

        repository.delete(qualification.getId());

        logger.debug("Trying to get the object for Qualification with id = " + qualification.getId());

        qualification = repository.find(qualification.getId());

        logger.debug("Deleted Qualification object from database with id = " + qualification.getId()
                + ". Exiting VerdictQualificationLookupService.delete method");

        return qualification;
    }

}
