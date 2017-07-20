package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.businessDelegate.interfaces.IIndicatorIdealValueDataBusinessDelegate;
import com.ohmuk.folitics.hibernate.entity.IndicatorIdealValueData;
import com.ohmuk.folitics.service.IIndicatorIdealValueDataService;

@Component
public class IndicatorIdealValueDataBusinessDelegate implements IIndicatorIdealValueDataBusinessDelegate {

    private static Logger logger = LoggerFactory.getLogger(IndicatorIdealValueDataBusinessDelegate.class);

    @Autowired
    private volatile IIndicatorIdealValueDataService indicatorIdealValueDataService;

    @Override
    public IndicatorIdealValueData create(IndicatorIdealValueData indicatoridealvaluedata) throws Exception {
        logger.info("Inside create method in business delegate");
        IndicatorIdealValueData indicatorIdealValue = indicatorIdealValueDataService.create(indicatoridealvaluedata);
        logger.info("Exiting create method in business delegate");
        return indicatorIdealValue;
    }

    @Override
    public IndicatorIdealValueData read(Long id) throws Exception {
        logger.info("Inside read method in business delegate");
        IndicatorIdealValueData indicatorIdealValue = indicatorIdealValueDataService.read(id);
        logger.info("Exiting read method in business delegate");
        return indicatorIdealValue;
    }

    @Override
    public List<IndicatorIdealValueData> readAll() throws Exception {
        logger.info("Inside readAll method in business delegate");
        List<IndicatorIdealValueData> indicatorIdealValue = indicatorIdealValueDataService.readAll();
        logger.info("Exiting readAll method in business delegate");
        return indicatorIdealValue;
    }

    @Override
    public IndicatorIdealValueData update(IndicatorIdealValueData indicatoridealvaluedata) throws Exception {
        logger.info("Inside update method in business delegate");
        IndicatorIdealValueData indicatorIdealValue = indicatorIdealValueDataService.update(indicatoridealvaluedata);
        logger.info("Exiting update method in business delegate");
        return indicatorIdealValue;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        logger.info("Inside delete method in business delegate");
        boolean sucess = indicatorIdealValueDataService.delete(id);
        logger.info("Exiting delete method in business delegate");
        return sucess;
    }

    @Override
    public boolean deleteFromDBById(Long id) throws Exception {
        logger.info("Inside deleteFromDB method in business delegate");
        boolean sucess = indicatorIdealValueDataService.deleteFromDBById(id);
        logger.info("Exiting deleteFromDB method in business delegate");
        return sucess;
    }

    @Override
    public boolean delete(IndicatorIdealValueData indicatoridealvaluedata) throws Exception {
        logger.info("Inside delete method in business delegate");
        boolean sucess = indicatorIdealValueDataService.delete(indicatoridealvaluedata);
        logger.info("Exiting delete method in business delegate");
        return sucess;
    }

    @Override
    public boolean deleteFromDB(IndicatorIdealValueData indicatoridealvaluedata) throws Exception {
        logger.info("Inside deleteFromDB method in business delegate");
        boolean sucess = indicatorIdealValueDataService.deleteFromDB(indicatoridealvaluedata);
        logger.info("Exiting deleteFromDB method in business delegate");
        return sucess;
    }

}
