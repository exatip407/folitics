package com.ohmuk.folitics.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.charting.beans.ChartResponse;
import com.ohmuk.folitics.charting.beans.LineChartData;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.IndicatorIdealVsActual;

@Service
@Transactional
public class GlobalVerdictPromiseIndicator implements IGlobalDistributionService {
    @Autowired
    private volatile IIndicatorThresholdService indicatorThresholdService;

    @Autowired
    IIndicatorDataService indicatorDataService;
    
    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    @Override
    public ChartResponse getChartData(Long categoryId) throws MessageException {
        ChartResponse chartResponse = new ChartResponse();
        List<LineChartData> indicatorlineChartDatas = new ArrayList<LineChartData>();
        List<IndicatorIdealVsActual> idealVsActuals =  getSession().createCriteria(IndicatorIdealVsActual.class).add(Restrictions.eq("indicatorid", categoryId)).list();
        
        for (IndicatorIdealVsActual indicatorIdealVsActual : idealVsActuals) {
            LineChartData lineChartData = new LineChartData();
            lineChartData.setTimestamp(indicatorIdealVsActual.getEditTime());
            lineChartData.setScore(indicatorIdealVsActual.getIndicatorIdealValue());
            lineChartData.setScoreCompare(indicatorIdealVsActual.getIndicatorActualValue());
            indicatorlineChartDatas.add(lineChartData);
        }
        
       /* List<IndicatorThreshold> indicatorThresholds = indicatorThresholdService.findByCategory(categoryId);
        List<IndicatorData> indicatorDatas = indicatorDataService.findByCategory(categoryId);
        List<LineChartData> indicatorlineChartDatas = new ArrayList<>();
        List<LineChartData> indicatorThresholdlineChartDatas = new ArrayList<>();
        for (IndicatorData indicatorData : indicatorDatas) {
            LineChartData lineChartData = new LineChartData();
            lineChartData.setTimestamp(indicatorData.getUpdateddate());
            lineChartData.setScore(indicatorData.getScore());
            indicatorlineChartDatas.add(lineChartData);
        }
        lineChartCompare.setChartDatas(indicatorlineChartDatas);
        for (IndicatorThreshold indicatorThreshold : indicatorThresholds) {
            LineChartData lineChartData = new LineChartData();
            lineChartData.setTimestamp(indicatorThreshold.getEditTime());
            lineChartData
                    .setScore((indicatorThreshold.getThreshold_start() + indicatorThreshold.getThreshold_start()) / 2);
            indicatorThresholdlineChartDatas.add(lineChartData);
        }
        lineChartCompare.setCompareChartDatas(indicatorThresholdlineChartDatas);*/
       /* chartResponse.setLineChartCompare(lineChartCompare);*/
        chartResponse.setData(indicatorlineChartDatas);
        
        return chartResponse;
    }

}
