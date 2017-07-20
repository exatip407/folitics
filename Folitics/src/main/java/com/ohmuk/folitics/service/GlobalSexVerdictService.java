package com.ohmuk.folitics.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.charting.beans.ChartResponse;
import com.ohmuk.folitics.charting.beans.VerdictChartBean;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdictSexDistribution;

@Service
@Transactional
public class GlobalSexVerdictService implements IGlobalDistributionService {
    
    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }


    @Override
    public ChartResponse getChartData(Long categoryId) throws MessageException {
        ChartResponse chartResponse = new ChartResponse();
        List<GlobalVerdictSexDistribution> globalVerdictSexDistributions = getSession().createCriteria(GlobalVerdictSexDistribution.class).list();
        List<VerdictChartBean> verdictChartDatas = new ArrayList<VerdictChartBean>();
        for (GlobalVerdictSexDistribution verdictSex : globalVerdictSexDistributions) {
            VerdictChartBean verdictChartAnti = new VerdictChartBean();
            VerdictChartBean verdictChartPro = new VerdictChartBean();
            Long antiCount = verdictSex.getAntiCount();
            Long proCount = verdictSex.getProCount();
            Long sum = antiCount + proCount;
            antiCount = (antiCount * 100) / sum;
            proCount = (proCount * 100) / sum;
            verdictChartAnti.setxAxis(verdictSex.getSex().getSex());
            verdictChartPro.setxAxis(verdictSex.getSex().getSex());
            verdictChartAnti.setyAxisValue(antiCount);
            verdictChartPro.setyAxisValue(proCount);
            verdictChartAnti.setFlag("Anti");
            verdictChartPro.setFlag("Pro");
            verdictChartAnti.setColor("#FF9933");
            verdictChartPro.setColor("#BEF781");
            verdictChartDatas.add(verdictChartAnti);
            verdictChartDatas.add(verdictChartPro);
        }
        chartResponse.setVerdictData(verdictChartDatas);
        return chartResponse;
    }

}
