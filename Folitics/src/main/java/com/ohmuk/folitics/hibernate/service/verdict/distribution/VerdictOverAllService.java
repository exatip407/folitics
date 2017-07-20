package com.ohmuk.folitics.hibernate.service.verdict.distribution;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.charting.beans.ChartResponse;
import com.ohmuk.folitics.charting.beans.VerdictChartBean;
import com.ohmuk.folitics.enums.Color;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictAgeGroup;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictMaritalStatus;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictQualification;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictRegion;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictReligion;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictSex;
import com.ohmuk.folitics.hibernate.service.verdict.IVerdictService;
import com.ohmuk.folitics.util.FoliticsUtils;

@Service
@Transactional
public class VerdictOverAllService implements
		IVerdictDistributionService<Object, Object> {

	@Autowired
	private volatile IVerdictService verdictService;

	@Override
	public ChartResponse getChartData(Long sentimentId) throws MessageException {
		Long antiCount = 0l;
		Long proCount = 0l;
		ChartResponse chartResponse = new ChartResponse();
		Verdict verdict = verdictService.getVerdictForSentiment(sentimentId);
		List<VerdictChartBean> verdictChartDatas = new ArrayList<VerdictChartBean>();
		Hibernate.initialize(verdict);
		for (VerdictAgeGroup ageGroup : verdict.getVerdictAgeGroups()) {
			antiCount += ageGroup.getAntiCount();
			proCount += ageGroup.getProCount();
		}
		for (VerdictMaritalStatus maritalStatus : verdict
				.getVerdictMaritalStatuses()) {
			antiCount += maritalStatus.getAntiCount();
			proCount += maritalStatus.getProCount();
		}
		for (VerdictQualification qualification : verdict
				.getVerdictQualifications()) {
			antiCount += qualification.getAntiCount();
			proCount += qualification.getProCount();
		}
		for (VerdictRegion region : verdict.getVerdictRegions()) {
			antiCount += region.getAntiCount();
			proCount += region.getProCount();
		}
		for (VerdictReligion religion : verdict.getVerdictReligions()) {
			antiCount += religion.getAntiCount();
			proCount += religion.getProCount();
		}// 5,17
		for (VerdictSex sex : verdict.getVerdictSexes()) {
			antiCount += sex.getAntiCount();
			proCount += sex.getProCount();
		} // 6 , 22
		VerdictChartBean verdictChartAnti = new VerdictChartBean();
		VerdictChartBean verdictChartPro = new VerdictChartBean();
		Long sum = antiCount + proCount;
		double antiCountPer = (double) (antiCount * 100) / sum;
		double proCountPer = (double) (proCount * 100) / sum;
		verdictChartAnti.setxAxis("OverAll Anti");
		verdictChartPro.setxAxis("OverAll pro");
		verdictChartAnti.setyAxisValue(FoliticsUtils
				.getRoundedDouble(antiCountPer));
		verdictChartPro.setyAxisValue(FoliticsUtils
				.getRoundedDouble(proCountPer));
		verdictChartAnti.setFlag("Anti");
		verdictChartPro.setFlag("Pro");
		verdictChartAnti.setColor(Color.ANTI.getValue());
		verdictChartPro.setColor(Color.PRO.getValue());
		verdictChartDatas.add(verdictChartAnti);
		verdictChartDatas.add(verdictChartPro);

		chartResponse.setVerdictData(verdictChartDatas);
		return chartResponse;
	}

	@Override
	public Object addDistribution(Object t) throws MessageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getDistribution(Object u) throws MessageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object updateDistribution(Object t) throws MessageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> getDistributionsForVerdict(Long verdictId) {
		// TODO Auto-generated method stub
		return null;
	}

}
