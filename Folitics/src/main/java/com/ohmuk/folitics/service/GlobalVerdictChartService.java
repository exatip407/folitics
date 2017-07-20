package com.ohmuk.folitics.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.charting.Exception.ProcessingException;
import com.ohmuk.folitics.charting.Exception.ValidationException;
import com.ohmuk.folitics.charting.beans.ChartRequest;
import com.ohmuk.folitics.charting.beans.ChartResponse;
import com.ohmuk.folitics.hibernate.entity.Chart;
import com.ohmuk.folitics.hibernate.entity.ChartMetaData;

@Aspect
@Service
@Transactional
public class GlobalVerdictChartService implements IChartService{
    private static Logger logger = LoggerFactory.getLogger(GlobalVerdictChartService.class);
    
    @Autowired
    Map<String, IGlobalDistributionService> globalDistributionService;
    
    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    
    @Override
    public boolean validate(ChartRequest chartRequest) throws ValidationException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ChartResponse getChartData(ChartRequest chartRequest, Long categoryId) throws ProcessingException, Exception {
        logger.info("Inside GPIChartService getChartData method");

        ChartResponse chartResponseData = new ChartResponse();
        List<String> chartMeta = new ArrayList<String>();
        Criteria criteria = getSession().createCriteria(Chart.class);
        criteria.add(Restrictions.eq("chartID", chartRequest.getChartID()));
        criteria.add(Restrictions.eq("chartSecondaryID", chartRequest.getChartSubID()));
        Chart chart = (Chart) criteria.uniqueResult();

        for (ChartMetaData chartMetaData : chart.getChartMetaData()) {
            chartMeta.add(chartMetaData.getPropertyValue());
        }
        
        IGlobalDistributionService distributionService = globalDistributionService.get(chartRequest.getChartSubID());
        chartResponseData = distributionService.getChartData(categoryId);
        chartResponseData.setChartMeta(chartMeta);
        return chartResponseData;
    }

    @Override
    public List<Chart> readAll() {
        // TODO Auto-generated method stub
        return null;
    }

}
