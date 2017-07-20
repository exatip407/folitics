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

import com.ohmuk.folitics.hibernate.entity.poll.PollOptionQualification;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionQualificationId;

/**
 * Repository implementation for {@link PollOptionQualification}
 * @author Abhishek
 *
 */
@Component
@Repository
public class PollOptionQualificationRepository implements
        IPollOptionDistributionRepository<PollOptionQualification, PollOptionQualificationId> {

    private static Logger logger = LoggerFactory.getLogger(PollOptionQualificationRepository.class);

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
    public PollOptionQualification save(PollOptionQualification pollOptionQualification) {

        logger.debug("Entered PollOptionQualificationRepository save method");
        logger.debug("Trying to save PollOptionQualification for poll option id = "
                + pollOptionQualification.getId().getPollOption().getId() + " and qualification id = "
                + pollOptionQualification.getId().getQualification().getId());

        PollOptionQualificationId pollOptionQualificationId = (PollOptionQualificationId) getSession().save(
                pollOptionQualification);

        logger.debug("Saved PollOptionQualification object and now getting PollOptionQualification object from database");

        pollOptionQualification = (PollOptionQualification) getSession().get(PollOptionQualification.class,
                pollOptionQualificationId);

        logger.debug("Got PollOptionQualification object from database. Exiting PollOptionQualificationRepository save method");

        return pollOptionQualification;
    }

    /*
     * (non-Javadoc)
     * @see com.ohmuk.folitics.hibernate.repository.verdict.IVerdictDistributionRepository#find(java.lang.Object)
     */
    @Override
    public PollOptionQualification find(PollOptionQualificationId pollOptionQualificationId) {

        logger.debug("Entered PollOptionQualificationRepository find method");
        logger.debug("Trying to get PollOptionQualification for poll option id = "
                + pollOptionQualificationId.getPollOption().getId() + " and qualification id = "
                + pollOptionQualificationId.getQualification().getId());

        PollOptionQualification pollOptionQualification = (PollOptionQualification) getSession().get(
                PollOptionQualification.class, pollOptionQualificationId);

        logger.debug("Got PollOptionQualification object from database. Exiting PollOptionQualificationRepository find method");

        return pollOptionQualification;
    }

    /*
     * (non-Javadoc)
     * @see com.ohmuk.folitics.hibernate.repository.verdict.IVerdictDistributionRepository#findAll()
     */
    @Override
    public List<PollOptionQualification> findAll() {

        logger.debug("Entered PollOptionQualificationRepository findAll method");
        logger.debug("Trying to get all PollOptionQualification");

        Criteria selectAllCriteria = getSession().createCriteria(PollOptionQualification.class);
        @SuppressWarnings("unchecked")
        List<PollOptionQualification> pollOptionQualifications = selectAllCriteria.list();

        logger.debug("Got all PollOptionQualification objects from database. Exiting PollOptionQualificationRepository findAll method");

        return pollOptionQualifications;
    }

    /*
     * (non-Javadoc)
     * @see com.ohmuk.folitics.hibernate.repository.verdict.IVerdictDistributionRepository#update(java.lang.Object)
     */
    @Override
    public PollOptionQualification update(PollOptionQualification pollOptionQualification) {

        logger.debug("Entered PollOptionQualificationRepository update method");
        logger.debug("Merging the object first with poll option id = "
                + pollOptionQualification.getId().getPollOption().getId() + " and qualification id = "
                + pollOptionQualification.getId().getQualification().getId());

        pollOptionQualification = (PollOptionQualification) getSession().merge(pollOptionQualification);

        logger.debug("Now updating the PollOptionQualification object in database with poll option id = "
                + pollOptionQualification.getId().getPollOption().getId() + " and qualification id = "
                + pollOptionQualification.getId().getQualification().getId());

        getSession().update(pollOptionQualification);

        logger.debug("Getting the PollOptionQualification object from database");

        pollOptionQualification = (PollOptionQualification) getSession().get(PollOptionQualification.class,
                pollOptionQualification.getId());

        logger.debug("Got PollOptionQualification object from database. Exiting PollOptionQualificationRepository update method");

        return pollOptionQualification;
    }

    /*
     * (non-Javadoc)
     * @see com.ohmuk.folitics.hibernate.repository.verdict.IVerdictDistributionRepository#delete(java.lang.Object)
     */
    @Override
    public void delete(PollOptionQualificationId pollOptionQualificationId) {

        logger.debug("Entered PollOptionQualificationRepository delete method");
        logger.debug("Trying to get PollOptionQualification with poll option id = "
                + pollOptionQualificationId.getPollOption().getId() + " and qualification id = "
                + pollOptionQualificationId.getQualification().getId());

        PollOptionQualification pollOptionQualification = (PollOptionQualification) getSession().get(
                PollOptionQualification.class, pollOptionQualificationId);

        logger.debug("Now trying to delete the PollOptionQualification object with poll option id = "
                + pollOptionQualificationId.getPollOption().getId() + " and qualification id = "
                + pollOptionQualificationId.getQualification().getId());

        getSession().delete(pollOptionQualification);

        logger.debug("Deleted PollOptionQualification object from database. Exiting PollOptionQualificationRepository delete method");
    }

}
