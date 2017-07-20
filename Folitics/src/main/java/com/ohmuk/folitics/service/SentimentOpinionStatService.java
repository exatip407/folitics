package com.ohmuk.folitics.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.hibernate.entity.SentimentOpinionStat;

/**
 * @author Abhishek
 *
 */
@Service
@Transactional
public class SentimentOpinionStatService implements ISentimentOpinionStatService {

    private static Logger logger = LoggerFactory.getLogger(SentimentOpinionStatService.class);

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    @Override
    public SentimentOpinionStat create(SentimentOpinionStat sentimentOpinionStat) throws Exception {
        logger.info("Inside SentimentOpinionStatService create method");
        Long id = (Long) getSession().save(sentimentOpinionStat);
        logger.info("Exiting from SentimentOpinionStatService create method");
        return read(id);
    }

    @Override
    public SentimentOpinionStat read(Long id) throws Exception {
        logger.info("Inside SentimentOpinionStatService read method");
        SentimentOpinionStat sentimentOpinionStat = (SentimentOpinionStat) getSession().get(SentimentOpinionStat.class,
                id);
        logger.info("Exiting from SentimentOpinionStatService read method");
        return sentimentOpinionStat;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SentimentOpinionStat> readAll() throws Exception {
        logger.info("Inside SentimentOpinionStatService readAll method");
        List<SentimentOpinionStat> sentimentOpinionStats = getSession().createCriteria(SentimentOpinionStat.class)
                .list();
        logger.info("Exiting from SentimentOpinionStatService readAll method");
        return sentimentOpinionStats;
    }

    @Override
    public SentimentOpinionStat update(SentimentOpinionStat sentimentOpinionStat) throws Exception {
        logger.info("Inside SentimentOpinionStatService update method");
        getSession().update(sentimentOpinionStat);
        logger.info("Exiting from SentimentOpinionStatService update method");
        return sentimentOpinionStat;
    }

    @Override
    public boolean deleteFromDB(Long id) throws Exception {
        logger.info("Inside SentimentOpinionStatService deleteFromDB method");
        SentimentOpinionStat sentimentOpinionStat = read(id);
        getSession().delete(sentimentOpinionStat);
        logger.info("Exiting from SentimentOpinionStatService deleteFromDB method");
        return true;
    }

    @Override
    public boolean deleteFromDB(SentimentOpinionStat sentimentOpinionStat) throws Exception {
        logger.info("Inside SentimentOpinionStatService deleteFromDB method");
        boolean deleteStatus = deleteFromDB(sentimentOpinionStat.getId());
        logger.info("Exiting from SentimentOpinionStatService deleteFromDB method");
        return deleteStatus;
    }

}
