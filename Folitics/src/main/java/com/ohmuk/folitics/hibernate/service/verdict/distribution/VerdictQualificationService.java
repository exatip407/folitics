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
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictQualification;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictQualificationId;
import com.ohmuk.folitics.hibernate.repository.verdict.distribution.IVerdictDistributionRepository;
import com.ohmuk.folitics.hibernate.service.verdict.IVerdictService;

/**
 * Service implementation for {@link VerdictQualification}
 * 
 * @author Abhishek
 *
 */
@Service
@Transactional
public class VerdictQualificationService
		implements
		IVerdictDistributionService<VerdictQualification, VerdictQualificationId> {

	private static Logger logger = LoggerFactory
			.getLogger(VerdictQualificationService.class);

	@Autowired
	private IVerdictDistributionRepository<VerdictQualification, VerdictQualificationId> repository;

	@Autowired
	private IVerdictService VerdictService;

	@Override
	public VerdictQualification addDistribution(
			VerdictQualification verdictQualification) throws MessageException {

		logger.debug("Entered VerdictQualificationService addDistribution method");

		if (verdictQualification == null) {

			logger.error("VerdictQualification object found null in VerdictQualificationService.addDistribution method");
			throw (new MessageException(
					"VerdictQualification object can't be null"));
		}

		if (verdictQualification.getId() == null) {

			logger.error("Id in VerdictQualification object found null in VerdictQualificationService.addDistribution method");
			throw (new MessageException(
					"Id in VerdictQualification object can't be null"));
		}

		logger.debug("Trying to save the VerdictQualification object for verdict id = "
				+ verdictQualification.getId().getVerdict().getId()
				+ " and qualification id = "
				+ verdictQualification.getId().getQualification().getId());

		verdictQualification = repository.save(verdictQualification);

		logger.debug("VerdictQualification saved successfully. Exiting VerdictQualificationService addDistribution method");

		return verdictQualification;
	}

	@Override
	public VerdictQualification getDistribution(
			VerdictQualificationId verdictQualificationId)
			throws MessageException {

		logger.debug("Entered VerdictQualificationService getDistribution method");

		if (verdictQualificationId == null) {

			logger.error("VerdictQualificationId object found null in VerdictQualificationService.getDistribution method");
			throw (new MessageException(
					"VerdictQualificationId object can't be null"));
		}

		if (verdictQualificationId.getVerdict() == null) {

			logger.error("Verdict in VerdictQualificationId object found null in VerdictQualificationService.getDistribution method");
			throw (new MessageException(
					"Verdict in VerdictQualificationId object can't be null"));
		}

		if (verdictQualificationId.getQualification() == null) {

			logger.error("Qualification in VerdictQualificationId object found null in VerdictQualificationService.getDistribution method");
			throw (new MessageException(
					"Qualification in VerdictQualificationId object can't be null"));
		}

		logger.debug("Trying to get the VerdictQualification object for verdict id = "
				+ verdictQualificationId.getVerdict().getId()
				+ " and qualification id = "
				+ verdictQualificationId.getQualification().getId());

		VerdictQualification verdictQualification = repository
				.find(verdictQualificationId);

		logger.debug("Got VerdictQualification from database. Exiting VerdictQualificationService getDistribution method");

		return verdictQualification;
	}

	@Override
	public List<VerdictQualification> getDistributionsForVerdict(Long verdictId) {

		logger.debug("Entered VerdictQualificationService getDistributionsForVerdict method");

		List<VerdictQualification> verdictQualifications = repository
				.findByVerdictId(verdictId);

		logger.debug("Got VerdictQualification from database. Exiting VerdictQualificationService getDistributionsForVerdict method");

		return verdictQualifications;
	}

	@Override
	public VerdictQualification updateDistribution(
			VerdictQualification verdictQualification) throws MessageException {

		logger.debug("Entered VerdictQualificationService updateDistribution method");

		if (verdictQualification == null) {

			logger.error("VerdictQualification object found null in VerdictQualificationService.updateDistribution method");
			throw (new MessageException(
					"VerdictQualification object can't be null"));
		}

		if (verdictQualification.getId() == null) {

			logger.error("Id in VerdictQualification object found null in VerdictQualificationService.updateDistribution method");
			throw (new MessageException(
					"Id in VerdictQualification object can't be null"));
		}

		logger.debug("Trying to get the VerdictQualification object for verdict id = "
				+ verdictQualification.getId().getVerdict().getId()
				+ " and qualification id = "
				+ verdictQualification.getId().getQualification().getId());

		verdictQualification = repository.update(verdictQualification);

		logger.debug("Updated VerdictQualification in database. Exiting VerdictQualificationService updateDistribution method");

		return verdictQualification;
	}

	@Override
	public ChartResponse getChartData(Long sentimentId) throws MessageException {
		ChartResponse chartResponse = new ChartResponse();
		Verdict verdict = VerdictService.getVerdictForSentiment(sentimentId);
		List<VerdictChartBean> verdictChartDatas = new ArrayList<VerdictChartBean>();
		Hibernate.initialize(verdict.getVerdictQualifications());
		for (VerdictQualification verdictQualification : verdict
				.getVerdictQualifications()) {
			VerdictChartBean verdictChartAnti = new VerdictChartBean();
			VerdictChartBean verdictChartPro = new VerdictChartBean();
			Long antiCount = verdictQualification.getAntiCount();
			Long proCount = verdictQualification.getProCount();
			Long sum = antiCount + proCount;
			antiCount = (antiCount * 100) / sum;
			proCount = (proCount * 100) / sum;
			verdictChartAnti.setxAxis("Anti "+verdictQualification.getId()
					.getQualification().getQualification());
			verdictChartPro.setxAxis("Pro "+verdictQualification.getId()
					.getQualification().getQualification());
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
