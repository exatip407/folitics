package com.ohmuk.folitics.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.hibernate.entity.EventReportScore;
import com.ohmuk.folitics.util.DateUtils;

/**
 * 
 * @author Mayank Sharma
 *
 */
@Service
@Transactional
public class EventReportScoreService implements IEventReportScoreService

{
    private static Logger logger = LoggerFactory.getLogger(EventReportScoreService.class);

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    @Override
    public EventReportScore create(EventReportScore event) throws Exception {
        logger.info("Inside EventReportService controller");
        Long id = (Long) getSession().save(event);
        logger.info("Exciting from EventReportService controller");
        return read(id);
    }

    @Override
    public EventReportScore read(Long id) throws Exception {
        return (EventReportScore) getSession().get(EventReportScore.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<EventReportScore> readAll() throws Exception {
        return getSession().createCriteria(EventReportScore.class).list();
    }

    @Override
    public boolean deleteFromDB(Long id) throws Exception {
        logger.info("Inside EventReportScore service deleteFromDB method");
        EventReportScore eventReportScore = read(id);
        getSession().delete(eventReportScore);
        logger.info("Exiting from EventReportScore");
        return true;
    }

    @Override
    public EventReportScore update(EventReportScore event) throws Exception {
        logger.info("Inside EventReportScore service update method");
        event.setEditTime(DateUtils.getSqlTimeStamp());
        getSession().update(event);
        EventReportScore eventReportScore = read(event.getId());
        logger.info("Exiting from EventReportScore service update method");
        return eventReportScore;

    }

    @Override
    public boolean delete(Long id) throws Exception {
        logger.info("Inside EventReportScore service");
        EventReportScore eventReportScore = (EventReportScore) getSession().get(EventReportScore.class, id);
        eventReportScore.setState(ComponentState.DELETED.getValue());
        getSession().update(eventReportScore);
        logger.info("Exiting from EventReportScore service");
        return true;
    }

    @Override
    public boolean delete(EventReportScore event) {
        logger.info("Inside EventReportScore service");
        event.setState(ComponentState.DELETED.getValue());
        getSession().update(event);
        logger.info("Exiting from EventReportScore service");
        return true;
    }

}
