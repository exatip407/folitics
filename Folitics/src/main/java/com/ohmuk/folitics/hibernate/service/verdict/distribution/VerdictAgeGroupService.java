package com.ohmuk.folitics.hibernate.service.verdict.distribution;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.charting.beans.ChartResponse;
import com.ohmuk.folitics.charting.beans.VerdictChartBean;
import com.ohmuk.folitics.enums.Color;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictAgeGroup;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictAgeGroupId;
import com.ohmuk.folitics.hibernate.repository.verdict.distribution.IVerdictDistributionRepository;
import com.ohmuk.folitics.hibernate.service.verdict.IVerdictService;

/**
 * Service implementation for {@link VerdictAgeGroup}
 * 
 * @author Abhishek
 *
 */
@Service
@Transactional
public class VerdictAgeGroupService implements
		IVerdictDistributionService<VerdictAgeGroup, VerdictAgeGroupId> {

	private static Logger logger = LoggerFactory
			.getLogger(VerdictAgeGroupService.class);

	@Autowired
	private IVerdictDistributionRepository<VerdictAgeGroup, VerdictAgeGroupId> repository;

	@Autowired
	private IVerdictService VerdictService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.hibernate.service.verdict.IVerdictDistributionService
	 * #addDistribution(java.lang.Object)
	 */
	@Override
	public VerdictAgeGroup addDistribution(VerdictAgeGroup verdictAgeGroup)
			throws MessageException {

		logger.debug("Entered VerdictAgeGroupService addDistribution method");

		if (verdictAgeGroup == null) {

			logger.error("VerdictAgeGroup object found null in VerdictAgeGroupService.addDistribution method");
			throw (new MessageException("VerdictAgeGroup object can't be null"));
		}

		if (verdictAgeGroup.getId() == null) {

			logger.error("Id in VerdictAgeGroup object found null in VerdictAgeGroupService.addDistribution method");
			throw (new MessageException(
					"Id in VerdictAgeGroup object can't be null"));
		}

		logger.debug("Trying to save the VerdictAgeGroup object for verdict id = "
				+ verdictAgeGroup.getId().getVerdict().getId()
				+ " and age group id = "
				+ verdictAgeGroup.getId().getAgeGroup().getId());

		verdictAgeGroup = repository.save(verdictAgeGroup);

		logger.debug("VerdictAgeGroup saved successfully. Exiting VerdictAgeGroupService addDistribution method");

		return verdictAgeGroup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.hibernate.service.verdict.IVerdictDistributionService
	 * #getDistribution(java.lang.Object)
	 */
	@Override
	public VerdictAgeGroup getDistribution(VerdictAgeGroupId verdictAgeGroupId)
			throws MessageException {

		logger.debug("Entered VerdictAgeGroupService getDistribution method");

		if (verdictAgeGroupId == null) {

			logger.error("VerdictAgeGroupId object found null in VerdictAgeGroupService.getDistribution method");
			throw (new MessageException(
					"VerdictAgeGroupId object can't be null"));
		}

		if (verdictAgeGroupId.getVerdict() == null) {

			logger.error("Verdict in VerdictAgeGroupId object found null in VerdictAgeGroupService.getDistribution method");
			throw (new MessageException(
					"Verdict in VerdictAgeGroupId object can't be null"));
		}

		if (verdictAgeGroupId.getAgeGroup() == null) {

			logger.error("AgeGroup in VerdictAgeGroupId object found null in VerdictAgeGroupService.getDistribution method");
			throw (new MessageException(
					"AgeGroup in VerdictAgeGroupId object can't be null"));
		}

		logger.debug("Trying to get the VerdictAgeGroup object for verdict id = "
				+ verdictAgeGroupId.getVerdict().getId()
				+ " and age group id = "
				+ verdictAgeGroupId.getAgeGroup().getId());

		VerdictAgeGroup verdictAgeGroup = repository.find(verdictAgeGroupId);

		logger.debug("Got VerdictAgeGroup from database. Exiting VerdictAgeGroupService getDistribution method");

		return verdictAgeGroup;
	}

	@Override
	public List<VerdictAgeGroup> getDistributionsForVerdict(Long verdictId) {

		logger.debug("Entered VerdictAgeGroupService getDistributionsForVerdict method");

		List<VerdictAgeGroup> verdictAgeGroups = repository
				.findByVerdictId(verdictId);

		logger.debug("Got VerdictAgeGroup from database. Exiting VerdictAgeGroupService getDistributionsForVerdict method");

		return verdictAgeGroups;
	}

	@Override
	public VerdictAgeGroup updateDistribution(VerdictAgeGroup verdictAgeGroup)
			throws MessageException {

		logger.debug("Entered VerdictAgeGroupService updateDistribution method");

		if (verdictAgeGroup == null) {

			logger.error("VerdictAgeGroup object found null in VerdictAgeGroupService.updateDistribution method");
			throw (new MessageException("VerdictAgeGroup object can't be null"));
		}

		if (verdictAgeGroup.getId() == null) {

			logger.error("Id in VerdictAgeGroup object found null in VerdictAgeGroupService.updateDistribution method");
			throw (new MessageException(
					"Id in VerdictAgeGroup object can't be null"));
		}

		logger.debug("Trying to get the VerdictAgeGroup object for verdict id = "
				+ verdictAgeGroup.getId().getVerdict().getId()
				+ " and age group id = "
				+ verdictAgeGroup.getId().getAgeGroup().getId());

		verdictAgeGroup = repository.update(verdictAgeGroup);

		logger.debug("Updated VerdictAgeGroup in database. Exiting VerdictAgeGroupService updateDistribution method");

		return verdictAgeGroup;
	}

	@Override
	public ChartResponse getChartData(Long sentimentId) throws MessageException {
		ChartResponse chartResponse = new ChartResponse();
		Verdict verdict = VerdictService.getVerdictForSentiment(sentimentId);
		List<VerdictChartBean> verdictChartDatas = new ArrayList<VerdictChartBean>();
		Hibernate.initialize(verdict.getVerdictAgeGroups());
		for (VerdictAgeGroup verdictAgeGroup : verdict.getVerdictAgeGroups()) {
			VerdictChartBean verdictChartAnti = new VerdictChartBean();
			VerdictChartBean verdictChartPro = new VerdictChartBean();
			Long antiCount = verdictAgeGroup.getAntiCount();
			Long proCount = verdictAgeGroup.getProCount();
			Long sum = antiCount + proCount;
			antiCount = (antiCount * 100) / sum;
			proCount = (proCount * 100) / sum;
			verdictChartAnti.setxAxis("Anti "+verdictAgeGroup.getId().getAgeGroup()
					.getStartAge()
					+ "-"
					+ verdictAgeGroup.getId().getAgeGroup().getEndAge()
							.toString());
			verdictChartPro.setxAxis("Pro "+verdictAgeGroup.getId().getAgeGroup()
					.getStartAge()
					+ "-"
					+ verdictAgeGroup.getId().getAgeGroup().getEndAge()
							.toString());
			verdictChartAnti.setyAxisValue(antiCount);
			verdictChartPro.setyAxisValue(proCount);
			verdictChartAnti.setFlag("Anti");
			verdictChartPro.setFlag("Pro");
			verdictChartAnti.setColor(Color.ANTI.getValue());
			verdictChartPro.setColor(Color.PRO.getValue());
			verdictChartDatas.add(verdictChartAnti);
			verdictChartDatas.add(verdictChartPro);
		}
		chartResponse.setVerdictData(verdictChartDatas);
		return chartResponse;
	}

}
