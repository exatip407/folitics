package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.businessDelegate.interfaces.IIndicatorMetaDataBusinessDelegate;
import com.ohmuk.folitics.hibernate.entity.IndicatorMetaData;
import com.ohmuk.folitics.service.IIndicatorMetaDataService;

@Component
public class IndicatorMetaDataBusinessDelegate implements IIndicatorMetaDataBusinessDelegate {

    private static Logger logger = LoggerFactory.getLogger(IndicatorMetaDataBusinessDelegate.class);

    @Autowired
    private volatile IIndicatorMetaDataService indicatorMetaDataService;

    @Override
    public IndicatorMetaData create(IndicatorMetaData indicatormetadata) throws Exception {

        logger.info("Inside create method in business delegate");
        IndicatorMetaData govtScheme = indicatorMetaDataService.create(indicatormetadata);
        logger.info("Exiting create method in business delegate");
        return govtScheme;
    }

    @Override
    public IndicatorMetaData read(Long id) throws Exception {
        logger.info("Inside read method in business delegate");
        IndicatorMetaData govtScheme = indicatorMetaDataService.read(id);
        logger.info("Exiting read method in business delegate");
        return govtScheme;
    }

    @Override
    public List<IndicatorMetaData> readAll() throws Exception {
        logger.info("Inside readAll method in business delegate");
        List<IndicatorMetaData> govtScheme = indicatorMetaDataService.readAll();
        logger.info("Exiting readAll method in business delegate");
        return govtScheme;
    }

    @Override
    public IndicatorMetaData update(IndicatorMetaData indicatormetadata) throws Exception {
        logger.info("Inside update method in business delegate");
        IndicatorMetaData govtScheme = indicatorMetaDataService.update(indicatormetadata);
        logger.info("Exiting update method in business delegate");
        return govtScheme;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        logger.info("Inside soft delete by id method in business delegate");
        boolean govtScheme = indicatorMetaDataService.delete(id);
        logger.info("Exiting soft delete by id method in business delegate");
        return govtScheme;
    }

    @Override
    public boolean delete(IndicatorMetaData indicatormetadata) throws Exception {
        logger.info("Inside soft delete by object method in business delegate");
        boolean sucess = indicatorMetaDataService.delete(indicatormetadata);
        logger.info("Exiting soft delete by object method in business delegate");
        return sucess;
    }

    @Override
    public boolean deleteFromDB(IndicatorMetaData indicatorMetaData) throws Exception {
        logger.info("Inside deleteFromDB method by object in business delegate");
        boolean sucess = indicatorMetaDataService.deleteFromDB(indicatorMetaData);
        logger.info("Exiting deleteFromDB method by object in business delegate");
        return sucess;
    }

    @Override
    public boolean deleteFromDBById(Long id) throws Exception {
        logger.info("Inside deleteFromDB method by id in business delegate");
        boolean sucess = indicatorMetaDataService.deleteFromDBById(id);
        logger.info("Exiting deleteFromDB method by id  in business delegate");
        return sucess;
    }

}
