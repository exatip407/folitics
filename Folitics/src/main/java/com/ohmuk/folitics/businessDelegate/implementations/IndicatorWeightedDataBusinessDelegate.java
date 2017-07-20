package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.businessDelegate.interfaces.IIndicatorWeightedDataBusinessDelegate;
import com.ohmuk.folitics.hibernate.entity.Category;
import com.ohmuk.folitics.hibernate.entity.IndicatorWeightedData;
import com.ohmuk.folitics.service.IIndicatorWeightedDataService;


@Component
public class IndicatorWeightedDataBusinessDelegate implements IIndicatorWeightedDataBusinessDelegate {

    private static Logger logger = LoggerFactory.getLogger(IndicatorWeightedDataBusinessDelegate.class);

    @Autowired
    private volatile IIndicatorWeightedDataService indicatorWeightedDataService;

    @Override
    public IndicatorWeightedData create(IndicatorWeightedData indicatorweighteddata) throws Exception {
        logger.info("Inside create method in business delegate");
        IndicatorWeightedData indicatorWeighted = indicatorWeightedDataService.create(indicatorweighteddata);
        logger.info("Exiting create method in business delegate");
        return indicatorWeighted;
    }

    @Override
    public IndicatorWeightedData read(Long id) throws Exception {
        logger.info("Inside read method in business delegate");
        IndicatorWeightedData indicatorWeighted = indicatorWeightedDataService.read(id);
        logger.info("Exiting read method in business delegate");
        return indicatorWeighted;
    }

    @Override
    public List<IndicatorWeightedData> readAll() throws Exception {
        logger.info("Inside readAll method in business delegate");
        List<IndicatorWeightedData> indicatorWeighted = indicatorWeightedDataService.readAll();
        logger.info("Exiting readAll method in business delegate");
        return indicatorWeighted;
    }

    @Override
    public IndicatorWeightedData update(IndicatorWeightedData indicatorweighteddata) throws Exception {
        logger.info("Inside update method in business delegate");
        IndicatorWeightedData indicatorWeighted = indicatorWeightedDataService.update(indicatorweighteddata);
        logger.info("Exiting update method in business delegate");
        return indicatorWeighted;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        logger.info("Inside soft delete by id method in business delegate");
        boolean sucess = indicatorWeightedDataService.delete(id);
        logger.info("Exiting soft delete by id method in business delegate");
        return sucess;
    }

    @Override
    public boolean delete(IndicatorWeightedData indicatorweighteddata) throws Exception {
        logger.info("Inside soft delete by object method in business delegate");
        boolean sucess = indicatorWeightedDataService.delete(indicatorweighteddata);
        logger.info("Exiting soft delete by object method in business delegate");
        return sucess;
    }

    @Override
    public boolean deleteFromDB(Long id) throws Exception {
        logger.info("Inside deleteFromDB method by object in business delegate");
        boolean sucess = indicatorWeightedDataService.deleteFromDB(id);
        logger.info("Exiting deleteFromDB method by object in business delegate");
        return sucess;
    }

    @Override
    public IndicatorWeightedData findByCategoryLatest(Category category) throws Exception {
    
        logger.info("Inside findByCategoryLatest method by id in business delegate");
        IndicatorWeightedData indicatorWeighted = indicatorWeightedDataService.findByCategoryLatest(category);
        logger.info("Exiting findByCategoryLatest method by id  in business delegate");
        return indicatorWeighted;
    }

}
