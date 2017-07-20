
package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.businessDelegate.interfaces.IEventReportScoreBusinessDelegate;
import com.ohmuk.folitics.hibernate.entity.EventReportScore;
import com.ohmuk.folitics.service.IEventReportScoreService;

@Component
public class EventReportScoreBusinessDelegate implements IEventReportScoreBusinessDelegate {

    private static Logger logger = LoggerFactory.getLogger(EventReportScoreBusinessDelegate.class);

    @Autowired
    private volatile IEventReportScoreService eventReportService;

    @Override
    public EventReportScore create(EventReportScore event) throws Exception {
        logger.info("Inside create method in business delegate");
        EventReportScore eventReportScore = eventReportService.create(event);
        logger.info("Exiting create method in business delegate");
        return eventReportScore;
    }

    @Override
    public EventReportScore read(Long id) throws Exception {
        logger.info("Inside read method in business delegate");
        EventReportScore eventReportScore = eventReportService.read(id);
        logger.info("Exiting read method in business delegate");
        return eventReportScore;
    }

    @Override
    public List<EventReportScore> readAll() throws Exception {
        logger.info("Inside readAll method in business delegate");
        List<EventReportScore> eventReportScore = eventReportService.readAll();
        logger.info("Exiting readAll method in business delegate");
        return eventReportScore;
    }

    @Override
    public EventReportScore update(EventReportScore event) throws Exception {
        logger.info("Inside update method in business delegate");
        EventReportScore sucess = eventReportService.update(event);
        logger.info("Exiting update method in business delegate");
        return sucess;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        logger.info("Inside update method in business delegate");
        boolean sucess = eventReportService.delete(id);
        logger.info("Exiting update method in business delegate");
        return sucess;
    }

    @Override
    public boolean delete(EventReportScore event) throws Exception {
        logger.info("Inside soft delete by object method in business delegate");
        boolean sucess = eventReportService.delete(event);
        logger.info("Exiting soft delete by object method in business delegate");
        return sucess;
    }

    @Override
    public boolean deleteFromDB(Long id) throws Exception {
        logger.info("Inside deleteFromDB method by id in business delegate");
        boolean sucess = eventReportService.deleteFromDB(id);
        logger.info("Exiting deleteFromDB method by id  in business delegate");
        return sucess;
    }

}
