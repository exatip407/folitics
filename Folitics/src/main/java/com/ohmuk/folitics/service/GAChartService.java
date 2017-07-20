package com.ohmuk.folitics.service;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.ohmuk.folitics.charting.beans.LineChartData;
import com.ohmuk.folitics.hibernate.entity.Category;
import com.ohmuk.folitics.hibernate.entity.Chart;
import com.ohmuk.folitics.hibernate.entity.ChartMetaData;
import com.ohmuk.folitics.hibernate.entity.IndicatorData;
import com.ohmuk.folitics.util.Serialize_JSON;

/**
 * 
 * @author Mayank Sharma
 *
 */
@Aspect
@Service
@Transactional
public class GAChartService implements IChartService {

    private static Logger logger = LoggerFactory.getLogger(GAChartService.class);

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    /**
     * This method is to genrate chart data by ChartRequest entity and chategory id
     * 
     * @return ChartResponse
     * @throws Exception
     */
    @Override
    @SuppressWarnings("unchecked")
    public ChartResponse getChartData(ChartRequest chartRequest, Long id) throws Exception {
        logger.info("Inside GAChartService getChartData method");

        ChartResponse chartResponseData = new ChartResponse();

        List<String> chartMeta = new ArrayList<String>();
        List<LineChartData> lineChartDatas = new ArrayList<LineChartData>();
        String chartJson = null;

        Criteria criteria = getSession().createCriteria(Chart.class);
        criteria.add(Restrictions.eq("chartID", chartRequest.getChartID()));
        criteria.add(Restrictions.eq("chartSecondaryID", chartRequest.getChartSubID()));
        Chart chart = (Chart) criteria.uniqueResult();
        int i = 0;
        for (ChartMetaData chartMetaData : chart.getChartMetaData()) {

            logger.debug("Chart property name: " + chartMetaData.getPropertyName() + " value: "
                    + chartMetaData.getPropertyValue());
            chartMeta.add(i, chartMetaData.getPropertyValue());
            i++;
        }
        Category category = new Category();
        category.setId(id);
        double score = 0.0;
        String idealValue = "";
        Criteria indicatorDataCriteria = getSession().createCriteria(IndicatorData.class);
        indicatorDataCriteria.add(Restrictions.eq("category", category));
        List<IndicatorData> indicatorsData = indicatorDataCriteria.list();

        for (IndicatorData indicatorData : indicatorsData) {
            LineChartData lineChartData = new LineChartData();
            lineChartData.setTimestamp(indicatorData.getEffectfromdate());
            lineChartData.setScore(indicatorData.getScore());
            lineChartDatas.add(lineChartData);
            score = indicatorData.getScore();
            idealValue = indicatorData.getIdealValueRange();
        }
        if (!idealValue.equals("")) {
            String idealValueStart = idealValue.substring(0, idealValue.indexOf("-"));
            double idealValueLong = Double.valueOf(idealValueStart);
            if (score > idealValueLong) {
                logger.debug("Chart direction UP");
                chartResponseData.getChartMeta().add("ChartTrend,UP");
            } else {
                logger.debug("Chart direction DOWN");
                chartResponseData.getChartMeta().add("ChartTrend,Down");
            }
        }

        try {
            chartJson = Serialize_JSON.getJSONString(chartMeta);
        } catch (Exception exception) {
            logger.error("Exception in serializing chart data");
            logger.error("Exception: " + exception);
            throw new ProcessingException("We are not able to process the request at this time");
        }
        chartResponseData.setChartMeta(chartMeta);
        chartResponseData.setData(lineChartDatas);
        logger.info("Exciting from GAChartService getChartData method");
        return chartResponseData;

    }

    @Override
    public List<Chart> readAll() {
        return null;
    }

    @Override
    public boolean validate(ChartRequest chartRequest) throws ValidationException {
        // TODO Auto-generated method stub
        return true;
    }
}
