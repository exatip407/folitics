package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.businessDelegate.interfaces.ISentimentOpinionStatBusinessDelegate;
import com.ohmuk.folitics.hibernate.entity.SentimentOpinionStat;
import com.ohmuk.folitics.service.ISentimentOpinionStatService;

@Component
public class SentimentOpinionStatBusinessDelegate implements  ISentimentOpinionStatBusinessDelegate{

    private static Logger logger = LoggerFactory.getLogger(SentimentOpinionStatBusinessDelegate.class);

    @Autowired
    private volatile ISentimentOpinionStatService sentimentOpinionStatService;

    @Override
    public SentimentOpinionStat create(SentimentOpinionStat sentimentOpinionStat) throws Exception {
        logger.info("Inside create method in business delegate");
        SentimentOpinionStat sentimentOpinionStatData = sentimentOpinionStatService.create(sentimentOpinionStat);
        logger.info("Exiting create method in business delegate");
        return sentimentOpinionStatData;
    }

    @Override
    public SentimentOpinionStat read(Long id) throws Exception {
        logger.info("Inside read method in business delegate");
        SentimentOpinionStat sentimentOpinionStatData = sentimentOpinionStatService.read(id);
        logger.info("Exiting read method in business delegate");
        return sentimentOpinionStatData;
    }

    @Override
    public List<SentimentOpinionStat> readAll() throws Exception {
        logger.info("Inside readAll method in business delegate");
        List<SentimentOpinionStat> sentimentOpinionStatData = sentimentOpinionStatService.readAll();
        logger.info("Exiting readAll method in business delegate");
        return sentimentOpinionStatData;
    }

    @Override
    public SentimentOpinionStat update(SentimentOpinionStat sentimentOpinionStat) throws Exception {
        logger.info("Inside update method in business delegate");
        SentimentOpinionStat sentimentOpinionStatData = sentimentOpinionStatService.update(sentimentOpinionStat);
        logger.info("Exiting update method in business delegate");
        return sentimentOpinionStatData;
    }

    @Override
    public boolean deleteFromDB(Long id) throws Exception {
        logger.info("Inside deleteFromDBById method in business delegate");
        boolean sucess = sentimentOpinionStatService.deleteFromDB(id);
        logger.info("Exiting deleteFromDBById method in business delegate");
        return sucess;
    }

    @Override
    public boolean deleteFromDB(SentimentOpinionStat sentimentOpinionStat) throws Exception {
        logger.info("Inside deleteFromDB method in business delegate");
        boolean sucess = sentimentOpinionStatService.deleteFromDB(sentimentOpinionStat);
        logger.info("Exiting deleteFromDB method in business delegate");
        return sucess;
    }

}
