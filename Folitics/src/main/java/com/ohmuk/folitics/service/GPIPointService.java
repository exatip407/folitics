package com.ohmuk.folitics.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.enums.OpinionType;
import com.ohmuk.folitics.enums.ResponseType;
import com.ohmuk.folitics.hibernate.entity.EventReportScore;
import com.ohmuk.folitics.hibernate.entity.GPIPoint;
import com.ohmuk.folitics.hibernate.entity.IndicatorData;
import com.ohmuk.folitics.hibernate.entity.Opinion;
import com.ohmuk.folitics.hibernate.entity.Response;
import com.ohmuk.folitics.util.DateUtils;

@Service
@Transactional
public class GPIPointService implements IGPIPointService {

    private static Logger logger = LoggerFactory.getLogger(GPIChartService.class);

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public static int opinionWeightage = 5;
    public static int responseWeightage = 1;

    @Override
    public GPIPoint create(GPIPoint gpiPoint) throws Exception {
        logger.info("Inside GPIPointService create method");
        Long id = (Long) getSession().save(gpiPoint);
        logger.info("Exiting from GPIPointService create method");
        return read(id);
    }

    @Override
    public GPIPoint read(Long id) throws Exception {
        return (GPIPoint) getSession().get(GPIPoint.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GPIPoint> readAll() throws Exception {
        logger.info("Inside GPIPointService readAll method");
        Criteria criteria = getSession().createCriteria(GPIPoint.class);
        logger.info("Exiting from GPIPointService readAll method");
        return criteria.list();
    }
    @Override
    public GPIPoint update(GPIPoint gpiPoint) throws Exception {
        logger.info("Inside GPIPointService update method");
        gpiPoint.setEditTime(DateUtils.getSqlTimeStamp());
        gpiPoint.setState(ComponentState.ACTIVE.getValue());
        getSession().update(gpiPoint);
        logger.info("Exiting from GPIPointService update method");
        return gpiPoint;
    }
    @Override
    public boolean delete(Long id) throws Exception {
        logger.info("Inside GPIPointService delete method");
        GPIPoint gpiPoint = (GPIPoint) getSession().get(GPIPoint.class, id);
        gpiPoint.setState(ComponentState.DELETED.getValue());
        getSession().update(gpiPoint);
        logger.info("Exiting from GPIPointService delete method");
        return true;
    }

   
    @Override
    public boolean delete(GPIPoint gpiPoint) throws Exception {
        logger.info("Inside GPIPointService delete method");
        gpiPoint.setState(ComponentState.DELETED.getValue());
        getSession().update(gpiPoint);
        logger.info("Exiting from GPIPointService delete method");
        return true;
    }

   

    /**
     * This method is to return the EventReportScore between start time and end time
     * @param startTime
     * @param endTime
     * @return List of EventReportScore
     * @throws Exception
     */
    public List<EventReportScore> reportEventAggregation(Timestamp startTime, Timestamp endTime) throws Exception {
        logger.info("Inside GPIPointService reportEventAggregation method");       
        Criteria criteria = getSession().createCriteria(EventReportScore.class);
        criteria.add(Restrictions.between("date", startTime, endTime));
        List<EventReportScore> eventReportScoreList = criteria.list();
        logger.info("Exiting GPIPointService reportEventAggregation method");
        return eventReportScoreList;
        
    }
    

    /**
     * This method is return list of IndicatorData 
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public  List<IndicatorData> indicatorChangeValueAggregation(Timestamp startTime, Timestamp endTime) throws Exception {
        logger.info("Inside GPIPointService indicatorChangeValueAggregation method");
        List<IndicatorData> indicatorDataList = null;
        Criteria criteria = getSession().createCriteria(IndicatorData.class);
        criteria.add(Restrictions.between("effectfromdate", startTime, endTime));
        indicatorDataList = criteria.list();
        return indicatorDataList;
    }
   
    /**
     * This method is to get opinion by timestamp
     * @param startTime
     * @param endTime
     * @return List<Opinion>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<Opinion> getOpinions(Timestamp startTime, Timestamp endTime) throws Exception {
        logger.info("Inside GPIPointService getOpinions method");
       
        Criteria criteria = getSession().createCriteria(Opinion.class);
        criteria.add(Restrictions.between("createTime", startTime, endTime));    
        List<Opinion>listOfOpinions = criteria.list();
        
        logger.info("Exiting from GPIPointService getOpinions method");
        return listOfOpinions;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GPIPoint> getALLGPIpoints() throws Exception {
        logger.info("Inside GPIPointService getALLGPIpoints method");
        Criteria criteria = getSession().createCriteria(GPIPoint.class);
        criteria.addOrder(Order.asc("startTime"));
        logger.info("Exiting from GPIPointService getALLGPIpoints method");
        return criteria.list();
    }

    @Override
    public boolean hardDelete(Long id) throws Exception {
        logger.info("Inside GPIPointService hardDelete method");
        GPIPoint gpiPoint = (GPIPoint) getSession().get(GPIPoint.class, id);
        getSession().delete(gpiPoint);
        logger.info("Exiting from GPIPointService hardDelete method");
        return true;
    }

}
