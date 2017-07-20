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

import com.ohmuk.folitics.hibernate.entity.poll.PollOptionReligion;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionReligionId;

/**
 * Repository implementation for {@link PollOptionReligion}
 * @author Abhishek
 *
 */
@Component
@Repository
public class PollOptionReligionRepository implements
        IPollOptionDistributionRepository<PollOptionReligion, PollOptionReligionId> {

    private static Logger logger = LoggerFactory.getLogger(PollOptionReligionRepository.class);

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
    public PollOptionReligion save(PollOptionReligion pollOptionReligion) {

        logger.debug("Entered PollOptionReligionRepository save method");
        logger.debug("Trying to save PollOptionReligion for poll option id = "
                + pollOptionReligion.getId().getPollOption().getId() + " and religion id = "
                + pollOptionReligion.getId().getReligion().getId());

        PollOptionReligionId pollOptionReligionId = (PollOptionReligionId) getSession().save(pollOptionReligion);

        logger.debug("Saved PollOptionReligion object and now getting PollOptionReligion object from database");

        pollOptionReligion = (PollOptionReligion) getSession().get(PollOptionReligion.class, pollOptionReligionId);

        logger.debug("Got PollOptionReligion object from database. Exiting PollOptionReligionRepository save method");

        return pollOptionReligion;
    }

    /*
     * (non-Javadoc)
     * @see com.ohmuk.folitics.hibernate.repository.verdict.IVerdictDistributionRepository#find(java.lang.Object)
     */
    @Override
    public PollOptionReligion find(PollOptionReligionId pollOptionReligionId) {

        logger.debug("Entered PollOptionReligionRepository find method");
        logger.debug("Trying to get PollOptionReligion for poll option id = "
                + pollOptionReligionId.getPollOption().getId() + " and religion id = "
                + pollOptionReligionId.getReligion().getId());

        PollOptionReligion pollOptionReligion = (PollOptionReligion) getSession().get(PollOptionReligion.class,
                pollOptionReligionId);

        logger.debug("Got PollOptionReligion object from database. Exiting PollOptionReligionRepository find method");

        return pollOptionReligion;
    }

    /*
     * (non-Javadoc)
     * @see com.ohmuk.folitics.hibernate.repository.verdict.IVerdictDistributionRepository#findAll()
     */
    @Override
    public List<PollOptionReligion> findAll() {

        logger.debug("Entered PollOptionReligionRepository findAll method");
        logger.debug("Trying to get all PollOptionReligion");

        Criteria selectAllCriteria = getSession().createCriteria(PollOptionReligion.class);
        @SuppressWarnings("unchecked")
        List<PollOptionReligion> pollOptionReligions = selectAllCriteria.list();

        logger.debug("Got all PollOptionReligion objects from database. Exiting PollOptionReligionRepository findAll method");

        return pollOptionReligions;
    }

    /*
     * (non-Javadoc)
     * @see com.ohmuk.folitics.hibernate.repository.verdict.IVerdictDistributionRepository#update(java.lang.Object)
     */
    @Override
    public PollOptionReligion update(PollOptionReligion pollOptionReligion) {

        logger.debug("Entered PollOptionReligionRepository update method");
        logger.debug("Merging the object first with poll option id = "
                + pollOptionReligion.getId().getPollOption().getId() + " and religion id = "
                + pollOptionReligion.getId().getReligion().getId());

        pollOptionReligion = (PollOptionReligion) getSession().merge(pollOptionReligion);

        logger.debug("Now updating the PollOptionReligion object in database with poll option id = "
                + pollOptionReligion.getId().getPollOption().getId() + " and religion id = "
                + pollOptionReligion.getId().getReligion().getId());

        getSession().update(pollOptionReligion);

        logger.debug("Getting the PollOptionReligion object from database");

        pollOptionReligion = (PollOptionReligion) getSession()
                .get(PollOptionReligion.class, pollOptionReligion.getId());

        logger.debug("Got PollOptionReligion object from database. Exiting PollOptionReligionRepository update method");

        return pollOptionReligion;
    }

    /*
     * (non-Javadoc)
     * @see com.ohmuk.folitics.hibernate.repository.verdict.IVerdictDistributionRepository#delete(java.lang.Object)
     */
    @Override
    public void delete(PollOptionReligionId pollOptionReligionId) {

        logger.debug("Entered PollOptionReligionRepository delete method");
        logger.debug("Trying to get PollOptionReligion with poll option id = "
                + pollOptionReligionId.getPollOption().getId() + " and religion id = "
                + pollOptionReligionId.getReligion().getId());

        PollOptionReligion pollOptionReligion = (PollOptionReligion) getSession().get(PollOptionReligion.class,
                pollOptionReligionId);

        logger.debug("Now trying to delete the PollOptionReligion object with poll option id = "
                + pollOptionReligionId.getPollOption().getId() + " and religion id = "
                + pollOptionReligionId.getReligion().getId());

        getSession().delete(pollOptionReligion);

        logger.debug("Deleted PollOptionReligion object from database. Exiting PollOptionReligionRepository delete method");
    }

}
