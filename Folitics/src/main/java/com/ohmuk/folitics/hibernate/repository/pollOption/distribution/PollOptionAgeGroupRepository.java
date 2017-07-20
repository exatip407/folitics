package com.ohmuk.folitics.hibernate.repository.pollOption.distribution;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ohmuk.folitics.hibernate.entity.poll.PollOptionAgeGroup;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionAgeGroupId;

/**
 * Repository implementation for {@link PollOptionAgeGroup}
 * @author Abhishek
 *
 */
@Component
@Repository
public class PollOptionAgeGroupRepository implements
        IPollOptionDistributionRepository<PollOptionAgeGroup, PollOptionAgeGroupId> {

    private static Logger logger = LoggerFactory.getLogger(PollOptionAgeGroupRepository.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /*
     * (non-Javadoc)
     * @see com.ohmuk.folitics.hibernate.repository.verdict.IVerdictDistributionRepository#save(java.lang.Object)
     */
    @Override
    public PollOptionAgeGroup save(PollOptionAgeGroup pollOptionAgeGroup) {

        logger.debug("Entered PollOptionAgeGroupRepository save method");
        logger.debug("Trying to save PollOptionAgeGroup for poll option id = "
                + pollOptionAgeGroup.getId().getPollOption().getId() + " and age group id = "
                + pollOptionAgeGroup.getId().getAgeGroup().getId());

        PollOptionAgeGroupId pollOptionAgeGroupId = (PollOptionAgeGroupId) getSession().save(pollOptionAgeGroup);

        logger.debug("Saved PollOptionAgeGroup object and now getting PollOptionAgeGroup object from database");

        pollOptionAgeGroup = (PollOptionAgeGroup) getSession().get(PollOptionAgeGroup.class, pollOptionAgeGroupId);

        logger.debug("Got PollOptionAgeGroup object from database. Exiting PollOptionAgeGroupRepository save method");

        return pollOptionAgeGroup;
    }

    /*
     * (non-Javadoc)
     * @see com.ohmuk.folitics.hibernate.repository.verdict.IVerdictDistributionRepository#find(java.lang.Object)
     */
    @Override
    public PollOptionAgeGroup find(PollOptionAgeGroupId pollOptionAgeGroupId) {

        logger.debug("Entered PollOptionAgeGroupRepository find method");
        logger.debug("Trying to get PollOptionAgeGroup for poll option id = "
                + pollOptionAgeGroupId.getPollOption().getId() + " and age group id = "
                + pollOptionAgeGroupId.getAgeGroup().getId());

        PollOptionAgeGroup pollOptionAgeGroup = (PollOptionAgeGroup) getSession().get(PollOptionAgeGroup.class,
                pollOptionAgeGroupId);

        logger.debug("Got PollOptionAgeGroup object from database. Exiting PollOptionAgeGroupRepository find method");

        return pollOptionAgeGroup;
    }

    /*
     * (non-Javadoc)
     * @see com.ohmuk.folitics.hibernate.repository.verdict.IVerdictDistributionRepository#findAll()
     */
    @Override
    public List<PollOptionAgeGroup> findAll() {

        logger.debug("Entered PollOptionAgeGroupRepository findAll method");
        logger.debug("Trying to get all PollOptionAgeGroup");

        Criteria selectAllCriteria = getSession().createCriteria(PollOptionAgeGroup.class);
        @SuppressWarnings("unchecked")
        List<PollOptionAgeGroup> pollOptionAgeGroups = selectAllCriteria.list();

        logger.debug("Got all PollOptionAgeGroup objects from database. Exiting PollOptionAgeGroupRepository findAll method");

        return pollOptionAgeGroups;
    }

    /*
     * (non-Javadoc)
     * @see com.ohmuk.folitics.hibernate.repository.verdict.IVerdictDistributionRepository#update(java.lang.Object)
     */
    @Override
    public PollOptionAgeGroup update(PollOptionAgeGroup pollOptionAgeGroup) {

        logger.debug("Entered PollOptionAgeGroupRepository update method");
        logger.debug("Merging the object first with poll option id = "
                + pollOptionAgeGroup.getId().getPollOption().getId() + " and age group id = "
                + pollOptionAgeGroup.getId().getAgeGroup().getId());

        pollOptionAgeGroup = (PollOptionAgeGroup) getSession().merge(pollOptionAgeGroup);

        logger.debug("Now updating the PollOptionAgeGroup object in database with poll option id = "
                + pollOptionAgeGroup.getId().getPollOption().getId() + " and age group id = "
                + pollOptionAgeGroup.getId().getAgeGroup().getId());

        getSession().update(pollOptionAgeGroup);

        logger.debug("Getting the PollOptionAgeGroup object from database");

        pollOptionAgeGroup = (PollOptionAgeGroup) getSession()
                .get(PollOptionAgeGroup.class, pollOptionAgeGroup.getId());

        logger.debug("Got PollOptionAgeGroup object from database. Exiting PollOptionAgeGroupRepository update method");

        return pollOptionAgeGroup;
    }

    /*
     * (non-Javadoc)
     * @see com.ohmuk.folitics.hibernate.repository.verdict.IVerdictDistributionRepository#delete(java.lang.Object)
     */
    @Override
    public void delete(PollOptionAgeGroupId pollOptionAgeGroupId) {

        logger.debug("Entered PollOptionAgeGroupRepository delete method");
        logger.debug("Trying to get PollOptionAgeGroup with poll option id = "
                + pollOptionAgeGroupId.getPollOption().getId() + " and age group id = "
                + pollOptionAgeGroupId.getAgeGroup().getId());

        PollOptionAgeGroup pollOptionAgeGroup = (PollOptionAgeGroup) getSession().get(PollOptionAgeGroup.class,
                pollOptionAgeGroupId);

        logger.debug("Now trying to delete the PollOptionAgeGroup object with poll option id = "
                + pollOptionAgeGroupId.getPollOption().getId() + " and age group id = "
                + pollOptionAgeGroupId.getAgeGroup().getId());

        getSession().delete(pollOptionAgeGroup);

        logger.debug("Deleted PollOptionAgeGroup object from database. Exiting PollOptionAgeGroupRepository delete method");
    }

}
