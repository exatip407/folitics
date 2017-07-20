package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.businessDelegate.interfaces.IMilestoneBusinessDelegate;
import com.ohmuk.folitics.hibernate.entity.Milestone;
import com.ohmuk.folitics.service.IMilestoneService;

@Component
public class MilestoneBusinessDelegate implements IMilestoneBusinessDelegate {

    private static Logger logger = LoggerFactory.getLogger(MilestoneBusinessDelegate.class);

    @Autowired
    private volatile IMilestoneService milestoneService;

    @Override
    public Milestone add(Long id, Milestone milestone) throws Exception {

        logger.info("Inside add method in business delegate");
        Milestone milestoneData = milestoneService.add(id, milestone);
        logger.info("Exiting add method in business delegate");
        return milestoneData;
    }

    @Override
    public boolean delete(Long gpiId) throws Exception {
        logger.info("Inside delete method in business delegate");
        boolean sucess = milestoneService.delete(gpiId);
        logger.info("Exiting delete method in business delegate");
        return sucess;
    }

    @Override
    public Milestone update(Milestone milestone) throws Exception {
        logger.info("Inside update method in business delegate");
        Milestone milestoneData = milestoneService.update(milestone);
        logger.info("Exiting update method in business delegate");
        return milestoneData;
    }

    @Override
    public List<Milestone> findAll() throws Exception {
        logger.info("Inside findAll method in business delegate");
        List<Milestone> milestoneData = milestoneService.findAll();
        logger.info("Exiting findAll method in business delegate");
        return milestoneData;
    }

    @Override
    public Milestone findByGpi(Long gpiId) throws Exception {
        logger.info("Inside findByGpi method in business delegate");
        Milestone milestoneData = milestoneService.findByGpi(gpiId);
        logger.info("Exiting findByGpi method in business delegate");
        return milestoneData;
    }

}
