package com.ohmuk.folitics.hibernate.service.verdict.lookup;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.AgeGroup;
import com.ohmuk.folitics.hibernate.repository.verdict.lookup.IVerdictLookupRepository;

/**
 * Service implementation for {@link Verdict} {@link AgeGroup} lookup entity
 * @author Abhishek
 *
 */
@Service
@Transactional
public class VerdictAgeGroupLookupService implements IVerdictLookupService<AgeGroup> {

    private static Logger logger = LoggerFactory.getLogger(VerdictAgeGroupLookupService.class);

    @Autowired
    private IVerdictLookupRepository<AgeGroup> repository;

    @Override
    public AgeGroup create(AgeGroup ageGroup) throws MessageException {

        logger.debug("Entered VerdictAgeGroupLookupService create method");

        if (ageGroup == null) {
            logger.error("AgeGroup object found null in VerdictAgeGroupLookupService.create method");
            throw (new MessageException("AgeGroup object can't be null"));
        }

        if (ageGroup.getStartAge() == null) {
            logger.error("StartAge in AgeGroup object found null in VerdictAgeGroupLookupService.create method");
            throw (new MessageException("StartAge in AgeGroup object can't be null"));
        }

        if (ageGroup.getEndAge() == null) {
            logger.error("EndAge in AgeGroup object found null in VerdictAgeGroupLookupService.create method");
            throw (new MessageException("StartAge in AgeGroup object can't be null"));
        }

        logger.debug("Trying to save the AgeGroup object with StartAge = " + ageGroup.getStartAge() + " and EndAge = "
                + ageGroup.getEndAge());

        ageGroup = repository.save(ageGroup);

        logger.debug("Saved AgeGroup object in the database. Exiting VerdictAgeGroupLookupService.create method");

        return ageGroup;
    }

    @Override
    public AgeGroup read(Long id) throws MessageException {

        logger.debug("Entered VerdictAgeGroupLookupService read method");

        if (id == null) {
            logger.error("id found null in VerdictAgeGroupLookupService.read method");
            throw (new MessageException("id can't be null"));
        }

        logger.debug("Trying to get the AgeGroup object for id = " + id);

        AgeGroup ageGroup = repository.find(id);

        logger.debug("Got AgeGroup object from the database. Exiting VerdictAgeGroupLookupService.read method");

        return ageGroup;
    }

    @Override
    public List<AgeGroup> readAll() {

        logger.debug("Entered VerdictAgeGroupLookupService readAll method");
        logger.debug("Trying to get all the AgeGroup objects from database");

        List<AgeGroup> ageGroups = repository.findAll();

        logger.debug("Got all the AgeGroup objects from database. Exiting VerdictAgeGroupLookupService.readAll method");

        return ageGroups;
    }

    @Override
    public AgeGroup readByValue(Object value) throws MessageException {

        logger.debug("Entered VerdictAgeGroupLookupService readByValue method");

        if (value == null) {
            logger.error("Value found null in VerdictAgeGroupLookupService.readByValue method");
            throw (new MessageException("value can't be null"));
        }

        int age = (int) value;

        logger.debug("Trying to get the AgeGroup object for age = " + age);

        AgeGroup ageGroup = repository.findByValue(age);

        logger.debug("Got AgeGroup object from the database. Exiting VerdictAgeGroupLookupService.readByValue method");

        return ageGroup;
    }

    @Override
    public AgeGroup update(AgeGroup ageGroup) throws MessageException {

        logger.debug("Entered VerdictAgeGroupLookupService update method");

        if (ageGroup == null) {
            logger.error("AgeGroup object found null in VerdictAgeGroupLookupService.update method");
            throw (new MessageException("AgeGroup object can't be null"));
        }

        if (ageGroup.getStartAge() == null) {
            logger.error("StartAge in AgeGroup object found null in VerdictAgeGroupLookupService.update method");
            throw (new MessageException("StartAge in AgeGroup object can't be null"));
        }

        if (ageGroup.getEndAge() == null) {
            logger.error("EndAge in AgeGroup object found null in VerdictAgeGroupLookupService.update method");
            throw (new MessageException("EndAge in AgeGroup object can't be null"));
        }

        logger.debug("Trying to update AgeGroup object in databse for id = " + ageGroup.getId());

        repository.update(ageGroup);

        logger.debug("Updated AgeGroup in database. Exiting VerdictAgeGroupLookupService.update method");

        return ageGroup;
    }

    @Override
    public AgeGroup delete(Long id) throws MessageException {

        logger.debug("Entered VerdictAgeGroupLookupService delete method");

        if (id == null) {
            logger.error("Id found null in VerdictAgeGroupLookupService.delete method");
            throw (new MessageException("Id can't be null"));
        }

        logger.debug("Trying to get the object for AgeGroup with id = " + id);

        repository.delete(id);

        logger.debug("Trying to get the object for AgeGroup with id = " + id);

        AgeGroup ageGroup = repository.find(id);

        logger.debug("Deleted AgeGroup object from database with id = " + ageGroup.getId()
                + ". Exiting VerdictAgeGroupLookupService.delete method");

        return ageGroup;
    }

    @Override
    public AgeGroup delete(AgeGroup ageGroup) throws MessageException {

        logger.debug("Entered VerdictAgeGroupLookupService delete method");

        if (ageGroup == null) {
            logger.error("AgeGroup object found null in VerdictAgeGroupLookupService.delete method");
            throw (new MessageException("AgeGroup object can't be null"));
        }

        if (ageGroup.getId() == null) {
            logger.error("Id in AgeGroup object found null in VerdictAgeGroupLookupService.delete method");
            throw (new MessageException("Id in AgeGroup object can't be null"));
        }

        logger.debug("Trying to get the object for AgeGroup with id = " + ageGroup.getId());

        repository.delete(ageGroup.getId());

        logger.debug("Trying to get the object for AgeGroup with id = " + ageGroup.getId());

        ageGroup = repository.find(ageGroup.getId());

        logger.debug("Deleted AgeGroup object from database with id = " + ageGroup.getId()
                + ". Exiting VerdictAgeGroupLookupService.delete method");

        return ageGroup;
    }

}
