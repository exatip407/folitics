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
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictRegion;
import com.ohmuk.folitics.hibernate.entity.verdict.distribution.VerdictRegionId;
import com.ohmuk.folitics.hibernate.repository.verdict.distribution.IVerdictDistributionRepository;
import com.ohmuk.folitics.hibernate.service.verdict.IVerdictService;

/**
 * Service implementation for {@link VerdictRegion}
 * @author Abhishek
 *
 */
@Service
@Transactional
public class VerdictRegionService implements IVerdictDistributionService<VerdictRegion, VerdictRegionId> {

    private static Logger logger = LoggerFactory.getLogger(VerdictRegionService.class);

    @Autowired
    private IVerdictDistributionRepository<VerdictRegion, VerdictRegionId> repository;
    
    @Autowired
    private IVerdictService VerdictService;

    @Override
    public VerdictRegion addDistribution(VerdictRegion verdictRegion) throws MessageException {

        logger.debug("Entered VerdictRegionService addDistribution method");

        if (verdictRegion == null) {

            logger.error("VerdictRegion object found null in VerdictRegionService.addDistribution method");
            throw (new MessageException("VerdictRegion object can't be null"));
        }

        if (verdictRegion.getId() == null) {

            logger.error("Id in VerdictRegion object found null in VerdictRegionService.addDistribution method");
            throw (new MessageException("Id in VerdictRegion object can't be null"));
        }

        logger.debug("Trying to save the VerdictRegion object for verdict id = "
                + verdictRegion.getId().getVerdict().getId() + " and region id = "
                + verdictRegion.getId().getRegion().getId());

        verdictRegion = repository.save(verdictRegion);

        logger.debug("VerdictRegion saved successfully. Exiting VerdictRegionService addDistribution method");

        return verdictRegion;
    }

    @Override
    public VerdictRegion getDistribution(VerdictRegionId verdictRegionId) throws MessageException {

        logger.debug("Entered VerdictRegionService getDistribution method");

        if (verdictRegionId == null) {

            logger.error("VerdictRegionId object found null in VerdictRegionService.getDistribution method");
            throw (new MessageException("VerdictRegionId object can't be null"));
        }

        if (verdictRegionId.getVerdict() == null) {

            logger.error("Verdict in VerdictRegionId object found null in VerdictRegionService.getDistribution method");
            throw (new MessageException("Verdict in VerdictRegionId object can't be null"));
        }

        if (verdictRegionId.getRegion() == null) {

            logger.error("Region in VerdictRegionId object found null in VerdictRegionService.getDistribution method");
            throw (new MessageException("Region in VerdictRegionId object can't be null"));
        }

        logger.debug("Trying to get the VerdictRegion object for verdict id = " + verdictRegionId.getVerdict().getId()
                + " and region id = " + verdictRegionId.getRegion().getId());

        VerdictRegion verdictRegion = repository.find(verdictRegionId);

        logger.debug("Got VerdictRegion from database. Exiting VerdictRegionService getDistribution method");

        return verdictRegion;
    }

    @Override
	public List<VerdictRegion> getDistributionsForVerdict(Long verdictId) {

		logger.debug("Entered VerdictRegionService getDistributionsForVerdict method");

		List<VerdictRegion> verdictRegions = repository
				.findByVerdictId(verdictId);

		logger.debug("Got VerdictRegion from database. Exiting VerdictRegionService getDistributionsForVerdict method");

		return verdictRegions;
	}
    
    @Override
    public VerdictRegion updateDistribution(VerdictRegion verdictRegion) throws MessageException {

        logger.debug("Entered VerdictRegionService updateDistribution method");

        if (verdictRegion == null) {

            logger.error("VerdictRegion object found null in VerdictRegionService.updateDistribution method");
            throw (new MessageException("VerdictRegion object can't be null"));
        }

        if (verdictRegion.getId() == null) {

            logger.error("Id in VerdictRegion object found null in VerdictRegionService.updateDistribution method");
            throw (new MessageException("Id in VerdictRegion object can't be null"));
        }

        logger.debug("Trying to get the VerdictRegion object for verdict id = "
                + verdictRegion.getId().getVerdict().getId() + " and region id = "
                + verdictRegion.getId().getRegion().getId());

        verdictRegion = repository.update(verdictRegion);

        logger.debug("Updated VerdictRegion in database. Exiting VerdictRegionService updateDistribution method");

        return verdictRegion;
    }

    @Override
    public ChartResponse getChartData(Long sentimentId) throws MessageException {
        ChartResponse chartResponse = new ChartResponse();
        Verdict verdict = VerdictService.getVerdictForSentiment(sentimentId);
        List<VerdictChartBean> verdictChartDatas = new ArrayList<VerdictChartBean>();
        Hibernate.initialize(verdict.getVerdictRegions());
        for (VerdictRegion verdictRegion : verdict.getVerdictRegions()) {
            VerdictChartBean verdictChartAnti = new VerdictChartBean();
            VerdictChartBean verdictChartPro = new VerdictChartBean();
            Long antiCount = verdictRegion.getAntiCount();
            Long proCount = verdictRegion.getProCount();
            Long sum = antiCount + proCount;
            antiCount = (antiCount * 100) / sum;
            proCount = (proCount * 100) / sum;
            verdictChartAnti.setxAxis("Anti "+verdictRegion.getId().getRegion().getRegion());
            verdictChartPro.setxAxis("Pro "+verdictRegion.getId().getRegion().getRegion());
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
