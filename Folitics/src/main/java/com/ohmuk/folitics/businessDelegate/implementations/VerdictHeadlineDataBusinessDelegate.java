package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.businessDelegate.interfaces.IVerdictHeadlineDataBusinessDelegate;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.hibernate.entity.verdict.VerdictHeadlineData;
import com.ohmuk.folitics.hibernate.service.verdict.IVerdictHeadlineDataService;

/**
 * BusinessDelegate implementation for {@link VerdictHeadlineData}
 * 
 * @author Abhishek
 *
 */
@Component
@Transactional
public class VerdictHeadlineDataBusinessDelegate implements
		IVerdictHeadlineDataBusinessDelegate {

	private static Logger logger = LoggerFactory
			.getLogger(VerdictHeadlineDataBusinessDelegate.class);

	@Autowired
	private volatile IVerdictHeadlineDataService verdictHeadlineDataService;

	@Override
	public VerdictHeadlineData create(VerdictHeadlineData verdictHeadlineData)
			throws MessageException {

		logger.debug("Entered VerdictDistributionBusinessDelegate create method");

		verdictHeadlineData = verdictHeadlineDataService
				.create(verdictHeadlineData);

		logger.debug("Exiting VerdictDistributionBusinessDelegate create method");
		return verdictHeadlineData;
	}

	@Override
	public VerdictHeadlineData read(Long id) throws MessageException {

		logger.debug("Entered VerdictDistributionBusinessDelegate read method");

		VerdictHeadlineData verdictHeadlineData = verdictHeadlineDataService
				.read(id);

		logger.debug("Exiting VerdictDistributionBusinessDelegate read method");
		return verdictHeadlineData;
	}

	@Override
	public VerdictHeadlineData readVerdictHeadlineDataForParameters(
			VerdictHeadlineData verdictHeadlineData) throws MessageException {

		logger.debug("Entered VerdictDistributionBusinessDelegate readVerdictHeadlineDataForParameters method");

		verdictHeadlineData = verdictHeadlineDataService
				.readVerdictHeadlineDataForParameters(verdictHeadlineData);

		logger.debug("Exiting VerdictDistributionBusinessDelegate readVerdictHeadlineDataForParameters method");
		return verdictHeadlineData;
	}

	@Override
	public List<VerdictHeadlineData> readVerdictHeadlineDataForVerdict(
			Verdict verdict) {

		logger.debug("Entered VerdictDistributionBusinessDelegate readVerdictHeadlineDataForVerdict method");

		List<VerdictHeadlineData> verdictHeadlineData = verdictHeadlineDataService
				.readVerdictHeadlineDataForVerdict(verdict);

		logger.debug("Exiting VerdictDistributionBusinessDelegate readVerdictHeadlineDataForVerdict method");
		return verdictHeadlineData;
	}

	@Override
	public List<VerdictHeadlineData> readAll() {

		logger.debug("Entered VerdictDistributionBusinessDelegate readAll method");

		List<VerdictHeadlineData> verdictHeadlineData = verdictHeadlineDataService
				.readAll();

		logger.debug("Exiting VerdictDistributionBusinessDelegate readAll method");
		return verdictHeadlineData;
	}

	@Override
	public VerdictHeadlineData update(VerdictHeadlineData verdictHeadlineData)
			throws MessageException {

		logger.debug("Entered VerdictDistributionBusinessDelegate update method");

		verdictHeadlineData = verdictHeadlineDataService
				.update(verdictHeadlineData);

		logger.debug("Exiting VerdictDistributionBusinessDelegate update method");
		return verdictHeadlineData;
	}

	@Override
	public boolean delete(Long id) throws MessageException {

		logger.debug("Entered VerdictDistributionBusinessDelegate delete method");

		boolean status = verdictHeadlineDataService.delete(id);

		logger.debug("Exiting VerdictDistributionBusinessDelegate delete method");
		return status;
	}

	@Override
	public boolean delete(VerdictHeadlineData verdictHeadlineData)
			throws MessageException {

		logger.debug("Entered VerdictDistributionBusinessDelegate delete method");

		boolean status = verdictHeadlineDataService.delete(verdictHeadlineData);

		logger.debug("Exiting VerdictDistributionBusinessDelegate delete method");
		return status;
	}

}
