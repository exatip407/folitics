package com.ohmuk.folitics.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.hibernate.entity.GPIPoint;
import com.ohmuk.folitics.hibernate.entity.Milestone;

/**
 * 
 * @author Mayank Sharma
 *
 */
@Service
@Transactional
public class MilestoneService implements IMilestoneService {

    private static Logger logger = LoggerFactory.getLogger(MilestoneService.class);

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    @Autowired
    IGPIPointService gpiService;

    /**
     * Method is to add {@link Milestone} on {@link GPIPoint}
     * @param id
     * @param milestone
     * @return {@link Milestone}
     * @throws Exception
     */
    @Override
    public Milestone add(Long gpiId, Milestone milestone) throws Exception {
        logger.info("Inside MilestoneService add method");
        GPIPoint gpiPoint = gpiService.read(gpiId);
        if (null != gpiPoint) {
            milestone.setGpi(gpiPoint);
            getSession().saveOrUpdate(milestone);
            logger.info("Exiting from MilestoneService add method");
            return milestone;
        }
        logger.info("Exiting from MilestoneService add method");
        return null;
    }

    /**
     * Method is to update {@link Milestone}
     * @param milestone
     * @return {@link Milestone}
     * @throws Exception
     */
    @Override
    public Milestone update(Milestone milestone) {
        logger.info("Inside MilestoneService update method");
        getSession().update(milestone);
        logger.info("Exiting from MilestoneService update method");
        return milestone;
    }

    /**
     * Method is used to delete {@link Milestone} by gpiId
     * @param gpiId
     * @return boolean
     * @throws Exception
     */
    @Override
    public boolean delete(Long gpiId) throws Exception {
        logger.info("Inside MilestoneService delete method");
        Milestone milestone = findByGpi(gpiId);
        if (null != milestone) {
            getSession().delete(milestone);
            logger.info("Exiting from MilestoneService delete method");
            return true;
        }
        logger.info("Exiting from MilestoneService delete method");
        return false;
    }

    /**
     * Method is to get all {@link Milestone}
     * @return {@link List < Milestone >}
     * @throws Exception
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Milestone> findAll() {
        logger.info("Inside MilestoneService findAll method");
        logger.info("Exiting from MilestoneService findAll method");
        return getSession().createCriteria(Milestone.class).list();
    }

    /**
     * Method is to get {@link Milestone} on {@link GPIPoint}
     * @param gpiId
     * @return
     * @throws Exception
     */
    @Override
    public Milestone findByGpi(Long gpiId) throws Exception {
        logger.info("Inside MilestoneService findByGpi method");
        GPIPoint gpiPoint = (GPIPoint) getSession().get(GPIPoint.class, gpiId);
        if (null != gpiPoint) {
            Criteria criteria = getSession().createCriteria(Milestone.class).add(Restrictions.eq("gpi", gpiPoint));
            logger.info("Exiting from MilestoneService findByGpi method");
            return (Milestone) criteria.uniqueResult();
        }
        logger.info("Exiting from MilestoneService findByGpi method");
        return null;
    }
}
