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
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictMaritalStatus;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictMaritalStatusId;
import com.ohmuk.folitics.hibernate.repository.verdict.distribution.IVerdictDistributionRepository;
import com.ohmuk.folitics.hibernate.service.verdict.IVerdictService;

/**
 * Service implementation for {@link VerdictMaritalStatus}
 * 
 * @author Abhishek
 *
 */
@Service
@Transactional
public class VerdictMaritalStatusService
		implements
		IVerdictDistributionService<VerdictMaritalStatus, VerdictMaritalStatusId> {

	private static Logger logger = LoggerFactory
			.getLogger(VerdictMaritalStatusService.class);

	@Autowired
	private IVerdictDistributionRepository<VerdictMaritalStatus, VerdictMaritalStatusId> repository;

	@Autowired
	private IVerdictService VerdictService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.service.verdict.distribution.
	 * IVerdictDistributionService#addDistribution(java.lang .Object)
	 */
	@Override
	public VerdictMaritalStatus addDistribution(
			VerdictMaritalStatus verdictMaritalStatus) throws MessageException {

		logger.debug("Entered VerdictMaritalStatusService addDistribution method");

		if (verdictMaritalStatus == null) {

			logger.error("VerdictMaritalStatus object found null in VerdictMaritalStatusService.addDistribution method");
			throw (new MessageException(
					"VerdictMaritalStatus object can't be null"));
		}

		if (verdictMaritalStatus.getId() == null) {

			logger.error("Id in VerdictMaritalStatus object found null in VerdictMaritalStatusService.addDistribution method");
			throw (new MessageException(
					"Id in VerdictMaritalStatus object can't be null"));
		}

		logger.debug("Trying to save the VerdictMaritalStatus object for verdict id = "
				+ verdictMaritalStatus.getId().getVerdict().getId()
				+ " and age group id = "
				+ verdictMaritalStatus.getId().getMaritalStatus().getId());

		verdictMaritalStatus = repository.save(verdictMaritalStatus);

		logger.debug("VerdictMaritalStatus saved successfully. Exiting VerdictMaritalStatusService addDistribution method");

		return verdictMaritalStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.service.verdict.distribution.
	 * IVerdictDistributionService#getDistribution(java.lang .Object)
	 */
	@Override
	public VerdictMaritalStatus getDistribution(
			VerdictMaritalStatusId verdictMaritalStatusId)
			throws MessageException {

		logger.debug("Entered VerdictMaritalStatusService getDistribution method");

		if (verdictMaritalStatusId == null) {

			logger.error("VerdictMaritalStatusId object found null in VerdictMaritalStatusService.getDistribution method");
			throw (new MessageException(
					"VerdictMaritalStatusId object can't be null"));
		}

		if (verdictMaritalStatusId.getVerdict() == null) {

			logger.error("Verdict in VerdictMaritalStatusId object found null in VerdictMaritalStatusService.getDistribution method");
			throw (new MessageException(
					"Verdict in VerdictMaritalStatusId object can't be null"));
		}

		if (verdictMaritalStatusId.getMaritalStatus() == null) {

			logger.error("MaritalStatus in VerdictMaritalStatusId object found null in VerdictMaritalStatusService.getDistribution method");
			throw (new MessageException(
					"MaritalStatus in VerdictMaritalStatusId object can't be null"));
		}

		logger.debug("Trying to get the VerdictAgeGroup object for verdict id = "
				+ verdictMaritalStatusId.getVerdict().getId()
				+ " and marital status id = "
				+ verdictMaritalStatusId.getMaritalStatus().getId());

		VerdictMaritalStatus verdictMaritalStatus = repository
				.find(verdictMaritalStatusId);

		logger.debug("Got VerdictMaritalStatus from database. Exiting VerdictMaritalStatusService getDistribution method");

		return verdictMaritalStatus;
	}

	@Override
	public List<VerdictMaritalStatus> getDistributionsForVerdict(Long verdictId) {

		logger.debug("Entered VerdictMaritalStatusService getDistributionsForVerdict method");

		List<VerdictMaritalStatus> verdictMaritalStatuses = repository
				.findByVerdictId(verdictId);

		logger.debug("Got VerdictMaritalStatus from database. Exiting VerdictMaritalStatusService getDistributionsForVerdict method");

		return verdictMaritalStatuses;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.service.verdict.distribution.
	 * IVerdictDistributionService#updateDistribution(java .lang.Object)
	 */
	@Override
	public VerdictMaritalStatus updateDistribution(
			VerdictMaritalStatus verdictMaritalStatus) throws MessageException {

		logger.debug("Entered VerdictMaritalStatusService updateDistribution method");

		if (verdictMaritalStatus == null) {

			logger.error("VerdictMaritalStatus object found null in VerdictMaritalStatusService.updateDistribution method");
			throw (new MessageException(
					"VerdictMaritalStatus object can't be null"));
		}

		if (verdictMaritalStatus.getId() == null) {

			logger.error("Id in VerdictMaritalStatus object found null in VerdictMaritalStatusService.updateDistribution method");
			throw (new MessageException(
					"Id in VerdictMaritalStatus object can't be null"));
		}

		logger.debug("Trying to get the VerdictMaritalStatus object for verdict id = "
				+ verdictMaritalStatus.getId().getVerdict().getId()
				+ " and marital status id = "
				+ verdictMaritalStatus.getId().getMaritalStatus().getId());

		verdictMaritalStatus = repository.update(verdictMaritalStatus);

		logger.debug("Updated VerdictMaritalStatus in database. Exiting VerdictMaritalStatusService updateDistribution method");

		return verdictMaritalStatus;
	}

	@Override
	public ChartResponse getChartData(Long sentimentId) throws MessageException {
		ChartResponse chartResponse = new ChartResponse();
		Verdict verdict = VerdictService.getVerdictForSentiment(sentimentId);
		List<VerdictChartBean> verdictChartDatas = new ArrayList<VerdictChartBean>();
		Hibernate.initialize(verdict.getVerdictMaritalStatuses());
		for (VerdictMaritalStatus verdictMaritalStatus : verdict
				.getVerdictMaritalStatuses()) {
			VerdictChartBean verdictChartAnti = new VerdictChartBean();
			VerdictChartBean verdictChartPro = new VerdictChartBean();
			Long antiCount = verdictMaritalStatus.getAntiCount();
			Long proCount = verdictMaritalStatus.getProCount();
			Long sum = antiCount + proCount;
			antiCount = (antiCount * 100) / sum;
			proCount = (proCount * 100) / sum;
			verdictChartAnti.setxAxis("Anti "+verdictMaritalStatus.getId()
					.getMaritalStatus().getMaritalStatus());
			verdictChartPro.setxAxis("Pro "+verdictMaritalStatus.getId()
					.getMaritalStatus().getMaritalStatus());
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
