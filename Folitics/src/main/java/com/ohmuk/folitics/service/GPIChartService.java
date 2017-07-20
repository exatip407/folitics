package com.ohmuk.folitics.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.annotation.Aspect;
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

import com.ohmuk.folitics.charting.Exception.ProcessingException;
import com.ohmuk.folitics.charting.Exception.ValidationException;
import com.ohmuk.folitics.charting.beans.ChartRequest;
import com.ohmuk.folitics.charting.beans.ChartResponse;
import com.ohmuk.folitics.charting.beans.LineChartData;
import com.ohmuk.folitics.hibernate.entity.Chart;
import com.ohmuk.folitics.hibernate.entity.ChartMetaData;
import com.ohmuk.folitics.hibernate.entity.GPIPoint;
import com.ohmuk.folitics.util.Serialize_JSON;

@Aspect
@Service
@Transactional
public class GPIChartService implements IChartService {

    private static Logger logger = LoggerFactory.getLogger(GPIChartService.class);

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    /**
     * This method genrate gpi chart by ChartRequest and indicator id
     * @author Mayank Sharma
     * @return ChartResponse
     * @throws ProcessingException
     */
    @SuppressWarnings("unchecked")
    public ChartResponse getChartData(ChartRequest chartRequest, Long id) throws ProcessingException {
        logger.info("Inside GPIChartService getChartData method");

        ChartResponse chartResponseData = new ChartResponse();
        List<LineChartData> chartData = null;
        List<String> chartMeta = new ArrayList<String>();
        List<LineChartData> lineChartDatas = new ArrayList<LineChartData>();
        // String chartJson = null; //

        Criteria criteria = getSession().createCriteria(Chart.class);
        criteria.add(Restrictions.eq("chartID", chartRequest.getChartID()));
        criteria.add(Restrictions.eq("chartSecondaryID", chartRequest.getChartSubID()));
        Chart chart = (Chart) criteria.uniqueResult();

        for (ChartMetaData chartMetaData : chart.getChartMetaData()) {
            chartMeta.add(chartMetaData.getPropertyValue());
        }
        Criteria gpiCriteria = getSession().createCriteria(GPIPoint.class);
        gpiCriteria.addOrder(Order.asc("startTime"));
        List<GPIPoint> gpiPoints = gpiCriteria.list();

        for (GPIPoint gpi : gpiPoints) {
            LineChartData lineChartData = new LineChartData();
            lineChartData.setTimestamp(gpi.getStartTime());
            lineChartData.setScore(gpi.getTotalPoints());
            if (null != gpi.getMilestone()) {
                lineChartData.setMilestone(gpi.getMilestone().getMilestone());
                lineChartData.setUrl(gpi.getMilestone().getUrl());
                lineChartData.setDescription(gpi.getMilestone().getDescription());
                lineChartData.setMilestoneType(gpi.getMilestone().getMilestone_type());
                if (null != gpi.getMilestone().getImage()) {
                    lineChartData.setImage(gpi.getMilestone().getImage());
                }
            }
            lineChartDatas.add(lineChartData); //
            lineChartData = null;
        }
        if (lineChartDatas.size() != 0) {
            lineChartDatas.get(lineChartDatas.size() - 1).setMilestoneType("Verdict");
            lineChartDatas.get(lineChartDatas.size() - 1).setMilestone(40);
            lineChartDatas.get(lineChartDatas.size() - 1).setUrl("#/globalVerdict");
            ;
        }

        /*
         * try { chartJson = Serialize_JSON.getJSONString(chartMeta); } catch (Exception exception) {
         * logger.error("Exception: " + exception); throw new
         * ProcessingException("We are not able to process the request at this time"); }
         */
        chartResponseData.setChartMeta(chartMeta);
        chartResponseData.setData(lineChartDatas);
        logger.info("Exiting from GPIChartService getChartData method");
        return chartResponseData;

    }

    @Override
    public List<Chart> readAll() {
        return null;
    }

    @Override
    public boolean validate(ChartRequest chartRequest) throws ValidationException { // TODO Auto-generatedmethod stub
        return true;
    }
}
