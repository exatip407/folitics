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
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictSex;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictSexId;
import com.ohmuk.folitics.hibernate.repository.verdict.distribution.IVerdictDistributionRepository;
import com.ohmuk.folitics.hibernate.service.verdict.IVerdictService;

/**
 * Service implementation for {@link VerdictSex}
 * 
 * @author Abhishek
 *
 */
@Service
@Transactional
public class VerdictSexService implements
		IVerdictDistributionService<VerdictSex, VerdictSexId> {

	private static Logger logger = LoggerFactory
			.getLogger(VerdictSexService.class);

	@Autowired
	private IVerdictDistributionRepository<VerdictSex, VerdictSexId> repository;

	@Autowired
	private IVerdictService VerdictService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.service.verdict.distribution.
	 * IVerdictDistributionService#addDistribution(java.lang .Object)
	 */
	@Override
	public VerdictSex addDistribution(VerdictSex verdictSex)
			throws MessageException {

		logger.debug("Entered VerdictSexService create method");

		if (verdictSex == null) {

			logger.error("VerdictSex object found null in VerdictSexService.create method");
			throw (new MessageException("VerdictSex object can't be null"));
		}

		if (verdictSex.getId() == null) {

			logger.error("Id in VerdictSex object found null in VerdictSexService.create method");
			throw (new MessageException("Id in VerdictSex object can't be null"));
		}

		logger.debug("Trying to save the VerdictSex object for verdict id = "
				+ verdictSex.getId().getVerdict().getId() + " and sex id = "
				+ verdictSex.getId().getSex().getId());

		verdictSex = repository.save(verdictSex);

		return verdictSex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.service.verdict.distribution.
	 * IVerdictDistributionService#getDistribution(java.lang .Object)
	 */
	@Override
	public VerdictSex getDistribution(VerdictSexId verdictSexId)
			throws MessageException {

		logger.debug("Entered VerdictSexService getDistribution method");

		if (verdictSexId == null) {

			logger.error("VerdictSexId object found null in VerdictSexService.getDistribution method");
			throw (new MessageException("VerdictSexId object can't be null"));
		}

		if (verdictSexId.getVerdict() == null) {

			logger.error("Verdict in VerdictSexId object found null in VerdictSexService.getDistribution method");
			throw (new MessageException(
					"Verdict in VerdictSexId object can't be null"));
		}

		if (verdictSexId.getSex() == null) {

			logger.error("Sex in VerdictSexId object found null in VerdictSexService.getDistribution method");
			throw (new MessageException(
					"Sex in VerdictSexId object can't be null"));
		}

		logger.debug("Trying to get the VerdictSex object for verdict id = "
				+ verdictSexId.getVerdict().getId() + " and sex id = "
				+ verdictSexId.getSex().getId());

		VerdictSex verdictSex = repository.find(verdictSexId);

		logger.debug("Got VerdictSex from database. Exiting VerdictSexService getDistribution method");

		return verdictSex;
	}

	@Override
	public List<VerdictSex> getDistributionsForVerdict(Long verdictId) {

		logger.debug("Entered VerdictSexService getDistributionsForVerdict method");

		List<VerdictSex> verdictSexes = repository
				.findByVerdictId(verdictId);

		logger.debug("Got VerdictSex from database. Exiting VerdictSexService getDistributionsForVerdict method");

		return verdictSexes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.service.verdict.distribution.
	 * IVerdictDistributionService#updateDistribution(java .lang.Object)
	 */
	@Override
	public VerdictSex updateDistribution(VerdictSex verdictSex)
			throws MessageException {

		logger.debug("Entered VerdictSexService updateDistribution method");

		if (verdictSex == null) {

			logger.error("VerdictSex object found null in VerdictSexService.updateDistribution method");
			throw (new MessageException("VerdictSex object can't be null"));
		}

		if (verdictSex.getId() == null) {

			logger.error("Id in VerdictSex object found null in VerdictSexService.updateDistribution method");
			throw (new MessageException("Id in VerdictSex object can't be null"));
		}

		logger.debug("Trying to get the VerdictSex object for verdict id = "
				+ verdictSex.getId().getVerdict().getId() + " and sex id = "
				+ verdictSex.getId().getSex().getId());

		verdictSex = repository.update(verdictSex);

		logger.debug("Updated VerdictAgeGroup in database. Exiting VerdictSexService updateDistribution method");

		return verdictSex;
	}

	@Override
	public ChartResponse getChartData(Long sentimentId) throws MessageException {
		ChartResponse chartResponse = new ChartResponse();
		Verdict verdict = VerdictService.getVerdictForSentiment(sentimentId);
		List<VerdictChartBean> verdictChartDatas = new ArrayList<VerdictChartBean>();
		Hibernate.initialize(verdict.getVerdictSexes());
		for (VerdictSex verdictSex : verdict.getVerdictSexes()) {
			VerdictChartBean verdictChartAnti = new VerdictChartBean();
			VerdictChartBean verdictChartPro = new VerdictChartBean();
			Long antiCount = verdictSex.getAntiCount();
			Long proCount = verdictSex.getProCount();
			Long sum = antiCount + proCount;
			antiCount = (antiCount * 100) / sum;
			proCount = (proCount * 100) / sum;
			verdictChartAnti.setxAxis("Anti "+verdictSex.getId().getSex().getSex());
			verdictChartPro.setxAxis("Pro "+verdictSex.getId().getSex().getSex());
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
