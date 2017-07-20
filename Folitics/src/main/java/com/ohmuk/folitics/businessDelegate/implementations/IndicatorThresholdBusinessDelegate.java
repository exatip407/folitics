package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.businessDelegate.interfaces.IIndicatorThresholdBusinessDelegate;
import com.ohmuk.folitics.hibernate.entity.Category;
import com.ohmuk.folitics.hibernate.entity.IndicatorThreshold;
import com.ohmuk.folitics.service.IIndicatorThresholdService;

@Component
public class IndicatorThresholdBusinessDelegate implements IIndicatorThresholdBusinessDelegate {

    private static Logger logger = LoggerFactory.getLogger(IndicatorThresholdBusinessDelegate.class);

    @Autowired
    private volatile IIndicatorThresholdService indicatorThresholdService;

    @Override
    public IndicatorThreshold create(IndicatorThreshold indicatorthreshold) throws Exception {

        logger.info("Inside create method in business delegate");
        IndicatorThreshold indicator = indicatorThresholdService.create(indicatorthreshold);
        logger.info("Exiting create method in business delegate");
        return indicator;
    }

    @Override
    public IndicatorThreshold read(Long id) throws Exception {
        logger.info("Inside read method in business delegate");
        IndicatorThreshold indicator = indicatorThresholdService.read(id);
        logger.info("Exiting read method in business delegate");
        return indicator;
    }

    @Override
    public List<IndicatorThreshold> readAll() throws Exception {
        logger.info("Inside readAll method in business delegate");
        List<IndicatorThreshold> indicator = indicatorThresholdService.readAll();
        logger.info("Exiting readAll method in business delegate");
        return indicator;
    }

    @Override
    public IndicatorThreshold update(IndicatorThreshold indicatorthreshold) throws Exception {
        logger.info("Inside update method in business delegate");
        IndicatorThreshold indicator = indicatorThresholdService.update(indicatorthreshold);
        logger.info("Exiting update method in business delegate");
        return indicator;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        logger.info("Inside update method in business delegate");
        boolean sucess = indicatorThresholdService.delete(id);
        logger.info("Exiting update method in business delegate");
        return sucess;
    }

    @Override
    public boolean delete(IndicatorThreshold indicatorthreshold) throws Exception {
        logger.info("Inside soft delete by object method in business delegate");
        boolean indicator = indicatorThresholdService.delete(indicatorthreshold);
        logger.info("Exiting soft delete by object method in business delegate");
        return indicator;
    }

    @Override
    public boolean deleteFromDB(Long id) throws Exception {
        logger.info("Inside deleteFromDB method by id in business delegate");
        boolean sucess = indicatorThresholdService.deleteFromDB(id);
        logger.info("Exiting deleteFromDB method by id  in business delegate");
        return sucess;
    }

    @Override
    public List<IndicatorThreshold> findByCategoryLatest(Category category) throws Exception {

        logger.info("Inside findByCategoryLatest method by id in business delegate");
        List<IndicatorThreshold> indicator = indicatorThresholdService.findByCategoryLatest(category);
        logger.info("Exiting findByCategoryLatest method by id  in business delegate");
        return indicator;
    }
    
    
    @Override
    public List<IndicatorThreshold> findByCategory(Category category) throws Exception {

        logger.info("Inside findByCategoryLatest method by id in business delegate");
        List<IndicatorThreshold> indicator = indicatorThresholdService.findByCategory(category.getId());
        logger.info("Exiting findByCategoryLatest method by id  in business delegate");
        return indicator;
    }

}
