package com.ohmuk.folitics.hibernate.service.verdict;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.hibernate.repository.verdict.IVerdictRepository;

/**
 * Service implementation for {@link Verdict}
 * 
 * @author Abhishek
 *
 */
@Service
@Transactional
public class VerdictService implements IVerdictService {

	private static Logger logger = LoggerFactory
			.getLogger(VerdictService.class);

	@Autowired
	private IVerdictRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.hibernate.service.verdict.IVerdictService#create(com
	 * .ohmuk.folitics.jpa.entity.verdict.Verdict )
	 */
	@Override
	public Verdict create(Verdict verdict) throws MessageException {

		logger.debug("Entered VerdictService create method");

		if (verdict == null) {
			logger.error("Verdict object found null in VerdictService.create method");
			throw (new MessageException("Verdict object can't be null"));
		}

		if (verdict.getSentiment() == null) {
			logger.error("Sentiment in Verdict object found null in VerdictService.create method");
			throw (new MessageException(
					"Sentiment in Verdict object can't be null"));
		}

		logger.debug("Trying to save the Verdict object for sentimentId = "
				+ verdict.getSentiment().getId());

		verdict = repository.save(verdict);

		logger.debug("Saved Verdict object in the database. Exiting VerdictService.create method");

		return verdict;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.hibernate.service.verdict.IVerdictService#read(java
	 * .lang.Long)
	 */
	@Override
	public Verdict read(Long id) throws MessageException {

		logger.debug("Entered VerdictService read method");

		if (id == null) {
			logger.error("id found null in VerdictService.read method");
			throw (new MessageException("id can't be null"));
		}

		logger.debug("Trying to get the Verdict object for id = " + id);

		Verdict verdict = repository.find(id);

		logger.debug("Got Verdict object from the database. Exiting VerdictService.read method");

		return verdict;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.hibernate.service.verdict.IVerdictService#readAll()
	 */
	@Override
	public List<Verdict> readAll() {

		logger.debug("Entered VerdictService readAll method");
		logger.debug("Trying to get all the Verdict objects from databse");

		List<Verdict> verdicts = repository.findAll();

		logger.debug("Got all the Verdicts from database. Exiting VerdictService.readAll method");

		return verdicts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.service.verdict.IVerdictService#
	 * getVerdictForSentiment(java.lang.Long)
	 */
	@Override
	public Verdict getVerdictForSentiment(Long sentimentId)
			throws MessageException {

		logger.debug("Entered VerdictService getVerdictForSentiment method");

		if (sentimentId == null) {
			logger.error("sentimentId found null in VerdictService.getVerdictForSentiment method");
			throw (new MessageException("sentimentId can't be null"));
		}

		logger.debug("Trying to get Verdict object from database for sentimentId = "
				+ sentimentId);

		Verdict verdict = repository.getVerdictForSentiment(sentimentId);

		logger.debug("Got Verdict object for sentimentId = " + sentimentId
				+ ". Exiting VerdictService.");

		return verdict;
	}

	@Override
	public List<Verdict> getLatestNVerdicts(Integer noOfSentiments) {

		logger.info("Inside VerdictService getLatestNVerdicts method");

		List<Verdict> list = repository.readLatestNVerdicts(noOfSentiments);

		logger.info("Existing from VerdictService getLatestNVerdicts method");

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.hibernate.service.verdict.IVerdictService#update(com
	 * .ohmuk.folitics.jpa.entity.verdict.Verdict )
	 */
	@Override
	public Verdict update(Verdict verdict) throws MessageException {

		logger.debug("Entered VerdictService update method");

		if (verdict == null) {
			logger.error("Verdict object found null in VerdictService.update method");
			throw (new MessageException("Verdict object can't be null"));
		}

		if (verdict.getId() == null) {
			logger.error("Id in Verdict object found null in VerdictService.update method");
			throw (new MessageException("Id in Verdict object can't be null"));
		}

		if (verdict.getSentiment() == null) {
			logger.error("Sentiment in Verdict object found null in VerdictService.update method");
			throw (new MessageException(
					"Sentiment in Verdict object can't be null"));
		}

		logger.debug("Trying to update Verdict object in databse for id = "
				+ verdict.getId() + " and sentimentId = "
				+ verdict.getSentiment().getId());

		verdict = repository.update(verdict);

		logger.debug("Updated Verdict in database. Exiting VerdictService.update method");

		return verdict;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.hibernate.service.verdict.IVerdictService#delete(java
	 * .lang.Long)
	 */
	@Override
	public Verdict delete(Long id) throws MessageException {

		logger.debug("Entered VerdictService delete method");

		if (id == null) {
			logger.error("id found null in VerdictService.delete method");
			throw (new MessageException("id can't be null"));
		}

		logger.debug("Tryng to get the object for Verdict with id = " + id);

		repository.delete(id);

		logger.debug("Tryng to get the object for Verdict with id = " + id);

		Verdict verdict = repository.find(id);

		logger.debug("Deleted Verdict object from database with id = " + id
				+ ". Exiting VerdictService.delete method");

		return verdict;
	}

	@Override
	public Verdict delete(Verdict verdict) throws MessageException {

		logger.debug("Entered VerdictService delete method");

		if (verdict == null) {
			logger.error("Verdict object found null in VerdictService.delete method");
			throw (new MessageException("Verdict object can't be null"));
		}

		if (verdict.getId() == null) {
			logger.error("Id in Verdict object found null in VerdictService.delete method");
			throw (new MessageException("Id in Verdict object can't be null"));
		}

		logger.debug("Tryng to get the object for Verdict with id = "
				+ verdict.getId());

		repository.delete(verdict);

		logger.debug("Tryng to get the object for Verdict with id = "
				+ verdict.getId());

		verdict = repository.find(verdict.getId());

		logger.debug("Deleted Verdict object from database with id = "
				+ verdict.getId() + ". Exiting VerdictService.delete method");

		return verdict;
	}

}
