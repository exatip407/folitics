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

import com.ohmuk.folitics.hibernate.entity.poll.PollOptionRegion;
import com.ohmuk.folitics.hibernate.entity.poll.PollOptionRegionId;

/**
 * Repository implementation for {@link PollOptionRegion}
 * @author Abhishek
 *
 */
@Component
@Repository
public class PollOptionRegionRepository implements
        IPollOptionDistributionRepository<PollOptionRegion, PollOptionRegionId> {

    private static Logger logger = LoggerFactory.getLogger(PollOptionRegionRepository.class);

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
    public PollOptionRegion save(PollOptionRegion pollOptionRegion) {

        logger.debug("Entered PollOptionRegionRepository save method");
        logger.debug("Trying to save PollOptionRegion for poll option id = "
                + pollOptionRegion.getId().getPollOption().getId() + " and region id = "
                + pollOptionRegion.getId().getRegion().getId());

        PollOptionRegionId pollOptionRegionId = (PollOptionRegionId) getSession().save(pollOptionRegion);

        logger.debug("Saved PollOptionRegion object and now getting PollOptionRegion object from database");

        pollOptionRegion = (PollOptionRegion) getSession().get(PollOptionRegion.class, pollOptionRegionId);

        logger.debug("Got PollOptionRegion object from database. Exiting PollOptionRegionRepository save method");

        return pollOptionRegion;
    }

    /*
     * (non-Javadoc)
     * @see com.ohmuk.folitics.hibernate.repository.verdict.IVerdictDistributionRepository#find(java.lang.Object)
     */
    @Override
    public PollOptionRegion find(PollOptionRegionId pollOptionRegionId) {

        logger.debug("Entered PollOptionRegionRepository find method");
        logger.debug("Trying to get PollOptionRegion for poll option id = "
                + pollOptionRegionId.getPollOption().getId() + " and region id = "
                + pollOptionRegionId.getRegion().getId());

        PollOptionRegion pollOptionRegion = (PollOptionRegion) getSession().get(PollOptionRegion.class,
                pollOptionRegionId);

        logger.debug("Got PollOptionRegion object from database. Exiting PollOptionRegionRepository find method");

        return pollOptionRegion;
    }

    /*
     * (non-Javadoc)
     * @see com.ohmuk.folitics.hibernate.repository.verdict.IVerdictDistributionRepository#findAll()
     */
    @Override
    public List<PollOptionRegion> findAll() {

        logger.debug("Entered PollOptionRegionRepository findAll method");
        logger.debug("Trying to get all PollOptionRegion");

        Criteria selectAllCriteria = getSession().createCriteria(PollOptionRegion.class);
        @SuppressWarnings("unchecked")
        List<PollOptionRegion> pollOptionReligions = selectAllCriteria.list();

        logger.debug("Got all PollOptionRegion objects from database. Exiting PollOptionRegionRepository findAll method");

        return pollOptionReligions;
    }

    /*
     * (non-Javadoc)
     * @see com.ohmuk.folitics.hibernate.repository.verdict.IVerdictDistributionRepository#update(java.lang.Object)
     */
    @Override
    public PollOptionRegion update(PollOptionRegion pollOptionRegion) {

        logger.debug("Entered PollOptionRegionRepository update method");
        logger.debug("Merging the object first with poll option id = "
                + pollOptionRegion.getId().getPollOption().getId() + " and region id = "
                + pollOptionRegion.getId().getRegion().getId());

        pollOptionRegion = (PollOptionRegion) getSession().merge(pollOptionRegion);

        logger.debug("Now updating the PollOptionRegion object in database with poll option id = "
                + pollOptionRegion.getId().getPollOption().getId() + " and region id = "
                + pollOptionRegion.getId().getRegion().getId());

        getSession().update(pollOptionRegion);

        logger.debug("Getting the PollOptionRegion object from database");

        pollOptionRegion = (PollOptionRegion) getSession().get(PollOptionRegion.class, pollOptionRegion.getId());

        logger.debug("Got PollOptionRegion object from database. Exiting PollOptionRegionRepository update method");

        return pollOptionRegion;
    }

    /*
     * (non-Javadoc)
     * @see com.ohmuk.folitics.hibernate.repository.verdict.IVerdictDistributionRepository#delete(java.lang.Object)
     */
    @Override
    public void delete(PollOptionRegionId pollOptionRegionId) {

        logger.debug("Entered PollOptionRegionRepository delete method");
        logger.debug("Trying to get PollOptionRegion with poll option id = "
                + pollOptionRegionId.getPollOption().getId() + " and region id = "
                + pollOptionRegionId.getRegion().getId());

        PollOptionRegion pollOptionRegion = (PollOptionRegion) getSession().get(PollOptionRegion.class,
                pollOptionRegionId);

        logger.debug("Now trying to delete the PollOptionRegion object with poll option id = "
                + pollOptionRegionId.getPollOption().getId() + " and region id = "
                + pollOptionRegionId.getRegion().getId());

        getSession().delete(pollOptionRegion);

        logger.debug("Deleted PollOptionRegion object from database. Exiting PollOptionRegionRepository delete method");
    }

}
