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

import com.ohmuk.folitics.hibernate.entity.poll.PollOptionSex;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionSexId;

/**
 * Repository implementation for {@link PollOptionSex}
 * @author Abhishek
 *
 */
@Component
@Repository
public class PollOptionSexRepository implements IPollOptionDistributionRepository<PollOptionSex, PollOptionSexId> {

    private static Logger logger = LoggerFactory.getLogger(PollOptionSexRepository.class);

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
    public PollOptionSex save(PollOptionSex pollOptionSex) {

        logger.debug("Entered PollOptionSexRepository save method");
        logger.debug("Trying to save PollOptionSex for poll option id = "
                + pollOptionSex.getId().getPollOption().getId() + " and sex id = "
                + pollOptionSex.getId().getSex().getId());

        PollOptionSexId pollOptionSexId = (PollOptionSexId) getSession().save(pollOptionSex);

        logger.debug("Saved PollOptionSex object and now getting PollOptionSex object from database");

        pollOptionSex = (PollOptionSex) getSession().get(PollOptionSex.class, pollOptionSexId);

        logger.debug("Got PollOptionSex object from database. Exiting PollOptionSexRepository save method");

        return pollOptionSex;
    }

    /*
     * (non-Javadoc)
     * @see com.ohmuk.folitics.hibernate.repository.verdict.IVerdictDistributionRepository#find(java.lang.Object)
     */
    @Override
    public PollOptionSex find(PollOptionSexId pollOptionSexId) {

        logger.debug("Entered PollOptionSexRepository find method");
        logger.debug("Trying to get PollOptionSex for poll option id = " + pollOptionSexId.getPollOption().getId()
                + " and sex id = " + pollOptionSexId.getSex().getId());

        PollOptionSex pollOptionSex = (PollOptionSex) getSession().get(PollOptionSex.class, pollOptionSexId);

        logger.debug("Got PollOptionSex object from database. Exiting PollOptionSexRepository find method");

        return pollOptionSex;
    }

    /*
     * (non-Javadoc)
     * @see com.ohmuk.folitics.hibernate.repository.verdict.IVerdictDistributionRepository#findAll()
     */
    @Override
    public List<PollOptionSex> findAll() {

        logger.debug("Entered PollOptionSexRepository findAll method");
        logger.debug("Trying to get all PollOptionSex");

        Criteria selectAllCriteria = getSession().createCriteria(PollOptionSex.class);
        @SuppressWarnings("unchecked")
        List<PollOptionSex> pollOptionSexes = selectAllCriteria.list();

        logger.debug("Got all PollOptionSex objects from database. Exiting PollOptionSexRepository findAll method");

        return pollOptionSexes;
    }

    /*
     * (non-Javadoc)
     * @see com.ohmuk.folitics.hibernate.repository.verdict.IVerdictDistributionRepository#update(java.lang.Object)
     */
    @Override
    public PollOptionSex update(PollOptionSex pollOptionSex) {

        logger.debug("Entered PollOptionSexRepository update method");
        logger.debug("Merging the object first with poll option id = " + pollOptionSex.getId().getPollOption().getId()
                + " and sex id = " + pollOptionSex.getId().getSex().getId());

        pollOptionSex = (PollOptionSex) getSession().merge(pollOptionSex);

        logger.debug("Now updating the PollOptionSex object in database with poll option id = "
                + pollOptionSex.getId().getPollOption().getId() + " and sex id = "
                + pollOptionSex.getId().getSex().getId());

        getSession().update(pollOptionSex);

        logger.debug("Getting the PollOptionSex object from database");

        pollOptionSex = (PollOptionSex) getSession().get(PollOptionSex.class, pollOptionSex.getId());

        logger.debug("Got PollOptionSex object from database. Exiting PollOptionSexRepository update method");

        return pollOptionSex;
    }

    /*
     * (non-Javadoc)
     * @see com.ohmuk.folitics.hibernate.repository.verdict.IVerdictDistributionRepository#delete(java.lang.Object)
     */
    @Override
    public void delete(PollOptionSexId pollOptionSexId) {

        logger.debug("Entered PollOptionSexRepository delete method");
        logger.debug("Trying to get PollOptionSex with poll option id = " + pollOptionSexId.getPollOption().getId()
                + " and sex id = " + pollOptionSexId.getSex().getId());

        PollOptionSex pollOptionSex = (PollOptionSex) getSession().get(PollOptionSex.class, pollOptionSexId);

        logger.debug("Now trying to delete the VerdictSex object with poll option id = "
                + pollOptionSexId.getPollOption().getId() + " and sex id = " + pollOptionSexId.getSex().getId());

        getSession().delete(pollOptionSex);

        logger.debug("Deleted PollOptionSex object from database. Exiting PollOptionSexRepository delete method");
    }

}
