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
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictReligion;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictReligionId;
import com.ohmuk.folitics.hibernate.repository.verdict.distribution.IVerdictDistributionRepository;
import com.ohmuk.folitics.hibernate.service.verdict.IVerdictService;

/**
 * Service implementation for {@link VerdictReligion}
 * @author Abhishek
 *
 */
@Service
@Transactional
public class VerdictReligionService implements IVerdictDistributionService<VerdictReligion, VerdictReligionId> {

    private static Logger logger = LoggerFactory.getLogger(VerdictReligionService.class);

    @Autowired
    private IVerdictDistributionRepository<VerdictReligion, VerdictReligionId> repository;

    @Autowired
    private IVerdictService VerdictService;

    @Override
    public VerdictReligion addDistribution(VerdictReligion verdictReligion) throws MessageException {

        logger.debug("Entered VerdictReligionService addDistribution method");

        if (verdictReligion == null) {

            logger.error("VerdictReligion object found null in VerdictReligionService.addDistribution method");
            throw (new MessageException("VerdictReligion object can't be null"));
        }

        if (verdictReligion.getId() == null) {

            logger.error("Id in VerdictReligion object found null in VerdictReligionService.addDistribution method");
            throw (new MessageException("Id in VerdictReligion object can't be null"));
        }

        logger.debug("Trying to save the VerdictReligion object for verdict id = "
                + verdictReligion.getId().getVerdict().getId() + " and religion id = "
                + verdictReligion.getId().getReligion().getId());

        verdictReligion = repository.save(verdictReligion);

        logger.debug("VerdictReligion saved successfully. Exiting VerdictReligionService addDistribution method");

        return verdictReligion;
    }

    @Override
    public VerdictReligion getDistribution(VerdictReligionId verdictReligionId) throws MessageException {

        logger.debug("Entered VerdictReligionService getDistribution method");

        if (verdictReligionId == null) {

            logger.error("VerdictReligionId object found null in VerdictReligionService.getDistribution method");
            throw (new MessageException("VerdictReligionId object can't be null"));
        }

        if (verdictReligionId.getVerdict() == null) {

            logger.error("Verdict in VerdictReligionId object found null in VerdictReligionService.getDistribution method");
            throw (new MessageException("Verdict in VerdictReligionId object can't be null"));
        }

        if (verdictReligionId.getReligion() == null) {

            logger.error("Religion in VerdictReligionId object found null in VerdictReligionService.getDistribution method");
            throw (new MessageException("Religion in VerdictReligionId object can't be null"));
        }

        logger.debug("Trying to get the VerdictReligion object for verdict id = "
                + verdictReligionId.getVerdict().getId() + " and religion id = "
                + verdictReligionId.getReligion().getId());

        VerdictReligion verdictReligion = repository.find(verdictReligionId);

        logger.debug("Got VerdictReligion from database. Exiting VerdictReligionService getDistribution method");

        return verdictReligion;
    }

    @Override
	public List<VerdictReligion> getDistributionsForVerdict(Long verdictId) {

		logger.debug("Entered VerdictReligionService getDistributionsForVerdict method");

		List<VerdictReligion> verdictReligion = repository
				.findByVerdictId(verdictId);

		logger.debug("Got VerdictReligion from database. Exiting VerdictReligionService getDistributionsForVerdict method");

		return verdictReligion;
	}
    
    @Override
    public VerdictReligion updateDistribution(VerdictReligion verdictReligion) throws MessageException {

        logger.debug("Entered VerdictReligionService updateDistribution method");

        if (verdictReligion == null) {

            logger.error("VerdictReligion object found null in VerdictReligionService.updateDistribution method");
            throw (new MessageException("VerdictReligion object can't be null"));
        }

        if (verdictReligion.getId() == null) {

            logger.error("Id in VerdictReligion object found null in VerdictReligionService.updateDistribution method");
            throw (new MessageException("Id in VerdictReligion object can't be null"));
        }

        logger.debug("Trying to get the VerdictReligion object for verdict id = "
                + verdictReligion.getId().getVerdict().getId() + " and religion id = "
                + verdictReligion.getId().getReligion().getId());

        verdictReligion = repository.update(verdictReligion);

        logger.debug("Updated VerdictReligion in database. Exiting VerdictReligionService updateDistribution method");

        return verdictReligion;
    }

    @Override
    public ChartResponse getChartData(Long sentimentId) throws MessageException {
        ChartResponse chartResponse = new ChartResponse();
        Verdict verdict = VerdictService.getVerdictForSentiment(sentimentId);
        List<VerdictChartBean> verdictChartDatas = new ArrayList<VerdictChartBean>();
        Hibernate.initialize(verdict.getVerdictReligions());
        for (VerdictReligion verdictReligion : verdict.getVerdictReligions()) {
            VerdictChartBean verdictChartAnti = new VerdictChartBean();
            VerdictChartBean verdictChartPro = new VerdictChartBean();
            Long antiCount = verdictReligion.getAntiCount();
            Long proCount = verdictReligion.getProCount();
            Long sum = antiCount + proCount;
            antiCount = (antiCount * 100) / sum;
            proCount = (proCount * 100) / sum;
            verdictChartAnti.setxAxis("Anti "+verdictReligion.getId().getReligion().getReligion());
            verdictChartPro.setxAxis("Pro "+verdictReligion.getId().getReligion().getReligion());
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
