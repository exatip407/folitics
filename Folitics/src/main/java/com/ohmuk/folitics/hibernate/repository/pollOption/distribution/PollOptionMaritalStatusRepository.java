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

import com.ohmuk.folitics.hibernate.entity.poll.PollOptionMaritalStatus;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionMaritalStatusId;

/**
 * Repository implementation for {@link PollOptionMaritalStatus}
 * @author Abhishek
 *
 */
@Component
@Repository
public class PollOptionMaritalStatusRepository implements
        IPollOptionDistributionRepository<PollOptionMaritalStatus, PollOptionMaritalStatusId> {

    private static Logger logger = LoggerFactory.getLogger(PollOptionMaritalStatusRepository.class);

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
    public PollOptionMaritalStatus save(PollOptionMaritalStatus pollOptionMaritalStatus) {

        logger.debug("Entered PollOptionMaritalStatusRepository save method");
        logger.debug("Trying to save PollOptionMaritalStatus for poll option id = "
                + pollOptionMaritalStatus.getId().getPollOption().getId() + " and maritalstatus id = "
                + pollOptionMaritalStatus.getId().getMaritalStatus().getId());

        PollOptionMaritalStatusId pollOptionMaritalStatusId = (PollOptionMaritalStatusId) getSession().save(
                pollOptionMaritalStatus);

        logger.debug("Saved PollOptionMaritalStatus object and now getting PollOptionMaritalStatus object from database");

        pollOptionMaritalStatus = (PollOptionMaritalStatus) getSession().get(PollOptionMaritalStatus.class,
                pollOptionMaritalStatusId);

        logger.debug("Got PollOptionMaritalStatus object from database. Exiting PollOptionMaritalStatusRepository save method");

        return pollOptionMaritalStatus;
    }

    /*
     * (non-Javadoc)
     * @see com.ohmuk.folitics.hibernate.repository.verdict.IVerdictDistributionRepository#find(java.lang.Object)
     */
    @Override
    public PollOptionMaritalStatus find(PollOptionMaritalStatusId pollOptionMaritalStatusId) {

        logger.debug("Entered PollOptionMaritalStatusRepository find method");
        logger.debug("Trying to get PollOptionMaritalStatus for poll option id = "
                + pollOptionMaritalStatusId.getPollOption().getId() + " and marital status id = "
                + pollOptionMaritalStatusId.getMaritalStatus().getId());

        PollOptionMaritalStatus pollOptionMaritalStatus = (PollOptionMaritalStatus) getSession().get(
                PollOptionMaritalStatus.class, pollOptionMaritalStatusId);

        logger.debug("Got PollOptionMaritalStatus object from database. Exiting PollOptionMaritalStatusRepository find method");

        return pollOptionMaritalStatus;
    }

    /*
     * (non-Javadoc)
     * @see com.ohmuk.folitics.hibernate.repository.verdict.IVerdictDistributionRepository#findAll()
     */
    @Override
    public List<PollOptionMaritalStatus> findAll() {

        logger.debug("Entered PollOptionMaritalStatusRepository findAll method");
        logger.debug("Trying to get all PollOptionMaritalStatus");

        Criteria selectAllCriteria = getSession().createCriteria(PollOptionMaritalStatus.class);
        @SuppressWarnings("unchecked")
        List<PollOptionMaritalStatus> pollOptionMaritalStatuses = selectAllCriteria.list();

        logger.debug("Got all PollOptionMaritalStatus objects from database. Exiting PollOptionMaritalStatusRepository findAll method");

        return pollOptionMaritalStatuses;
    }

    /*
     * (non-Javadoc)
     * @see com.ohmuk.folitics.hibernate.repository.verdict.IVerdictDistributionRepository#update(java.lang.Object)
     */
    @Override
    public PollOptionMaritalStatus update(PollOptionMaritalStatus pollOptionMaritalStatus) {

        logger.debug("Entered PollOptionMaritalStatusRepository update method");
        logger.debug("Merging the object first with poll option id = "
                + pollOptionMaritalStatus.getId().getPollOption().getId() + " and marital status id = "
                + pollOptionMaritalStatus.getId().getMaritalStatus().getId());

        pollOptionMaritalStatus = (PollOptionMaritalStatus) getSession().merge(pollOptionMaritalStatus);

        logger.debug("Now updating the PollOptionMaritalStatus object in database with poll option id = "
                + pollOptionMaritalStatus.getId().getPollOption().getId() + " and marital status id = "
                + pollOptionMaritalStatus.getId().getMaritalStatus().getId());

        getSession().update(pollOptionMaritalStatus);

        logger.debug("Getting the PollOptionMaritalStatus object from database");

        pollOptionMaritalStatus = (PollOptionMaritalStatus) getSession().get(PollOptionMaritalStatus.class,
                pollOptionMaritalStatus.getId());

        logger.debug("Got PollOptionMaritalStatus object from database. Exiting PollOptionMaritalStatusRepository update method");

        return pollOptionMaritalStatus;
    }

    /*
     * (non-Javadoc)
     * @see com.ohmuk.folitics.hibernate.repository.verdict.IVerdictDistributionRepository#delete(java.lang.Object)
     */
    @Override
    public void delete(PollOptionMaritalStatusId pollOptionMaritalStatusId) {

        logger.debug("Entered PollOptionMaritalStatusRepository delete method");
        logger.debug("Trying to get PollOptionMaritalStatus with poll option id = "
                + pollOptionMaritalStatusId.getPollOption().getId() + " and marital status id = "
                + pollOptionMaritalStatusId.getMaritalStatus().getId());

        PollOptionMaritalStatus pollOptionMaritalStatus = (PollOptionMaritalStatus) getSession().get(
                PollOptionMaritalStatus.class, pollOptionMaritalStatusId);

        logger.debug("Now trying to delete the PollOptionMaritalStatus object with poll option id = "
                + pollOptionMaritalStatusId.getPollOption().getId() + " and marital status id = "
                + pollOptionMaritalStatusId.getMaritalStatus().getId());

        getSession().delete(pollOptionMaritalStatus);

        logger.debug("Deleted PollOptionMaritalStatus object from database. Exiting PollOptionMaritalStatusRepository delete method");
    }

}
