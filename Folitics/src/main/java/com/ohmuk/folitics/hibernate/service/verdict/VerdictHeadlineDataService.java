package com.ohmuk.folitics.hibernate.service.verdict;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.hibernate.entity.verdict.VerdictHeadlineData;
import com.ohmuk.folitics.hibernate.repository.verdict.IVerdictHeadlineDataRepository;

/**
 * Service implementation for {@link VerdictHeadlineData}
 * 
 * @author Abhishek
 *
 */
@Service
@Transactional
public class VerdictHeadlineDataService implements IVerdictHeadlineDataService {

	private static Logger logger = LoggerFactory
			.getLogger(VerdictHeadlineDataService.class);

	@Autowired
	private IVerdictHeadlineDataRepository repository;

	@Override
	public VerdictHeadlineData create(VerdictHeadlineData verdictHeadlineData)
			throws MessageException {

		logger.debug("Entered VerdictHeadlineDataService create method");

		if (verdictHeadlineData == null) {
			logger.error("VerdictHeadlineData object found null in VerdictHeadlineDataService.create method");
			throw (new MessageException(
					"VerdictHeadlineData object can't be null"));
		}

		logger.debug("Trying to save the VerdictHeadlineData object");

		verdictHeadlineData = repository.save(verdictHeadlineData);

		logger.debug("Saved VerdictHeadlineData object in the database. Exiting VerdictHeadlineDataService.create method");

		return verdictHeadlineData;
	}

	@Override
	public VerdictHeadlineData read(Long id) throws MessageException {

		logger.debug("Entered VerdictHeadlineDataService read method");

		if (id == null) {
			logger.error("id found null in VerdictHeadlineDataService.read method");
			throw (new MessageException("id can't be null"));
		}

		logger.debug("Trying to get the VerdictHeadlineData object for id = "
				+ id);

		VerdictHeadlineData verdictHeadlineData = repository.find(id);

		logger.debug("Got VerdictHeadlineData object from the database. Exiting VerdictHeadlineDataService.read method");

		return verdictHeadlineData;
	}

	@Override
	public List<VerdictHeadlineData> readVerdictHeadlineDataForVerdict(
			Verdict verdict) {

		logger.debug("Entered VerdictHeadlineDataService readVerdictHeadlineDataForVerdict method");

		List<VerdictHeadlineData> verdictHeadlineDatas = repository
				.findByVerdict(verdict);

		logger.debug("Got VerdictHeadlineData objects from the database. Exiting VerdictHeadlineDataService.readVerdictHeadlineDataForVerdict method");

		return verdictHeadlineDatas;
	}

	@Override
	public List<VerdictHeadlineData> readAll() {

		logger.debug("Entered VerdictHeadlineDataService readAll method");
		logger.debug("Trying to get all the VerdictHeadlineData objects from databse");

		List<VerdictHeadlineData> verdictHeadlineDatas = repository.findAll();

		logger.debug("Got all the VerdictHeadlineData from database. Exiting VerdictHeadlineDataService.readAll method");

		return verdictHeadlineDatas;
	}

	@Override
	public VerdictHeadlineData update(VerdictHeadlineData verdictHeadlineData)
			throws MessageException {

		logger.debug("Entered VerdictHeadlineDataService update method");

		if (verdictHeadlineData == null) {
			logger.error("VerdictHeadlineData object found null in VerdictHeadlineDataService.update method");
			throw (new MessageException(
					"VerdictHeadlineData object can't be null"));
		}

		if (verdictHeadlineData.getId() == null) {
			logger.error("Id in VerdictHeadlineData object found null in VerdictHeadlineDataService.update method");
			throw (new MessageException(
					"Id in VerdictHeadlineData object can't be null"));
		}

		logger.debug("Trying to update VerdictHeadlineData object in databse for id = "
				+ verdictHeadlineData.getId());

		repository.update(verdictHeadlineData);

		logger.debug("Updated VerdictHeadlineData in database. Exiting VerdictHeadlineDataService.update method");

		return verdictHeadlineData;
	}

	@Override
	public boolean delete(VerdictHeadlineData verdictHeadlineData)
			throws MessageException {

		logger.debug("Entered VerdictHeadlineDataService delete method");

		if (verdictHeadlineData == null) {
			logger.error("VerdictHeadlineData object found null in VerdictHeadlineDataService.delete method");
			throw (new MessageException(
					"VerdictHeadlineData object can't be null"));
		}

		if (verdictHeadlineData.getId() == null) {
			logger.error("Id in VerdictHeadlineData object found null in VerdictHeadlineDataService.delete method");
			throw (new MessageException(
					"Id in VerdictHeadlineData object can't be null"));
		}

		logger.debug("Trying to get the object for VerdictHeadlineData with id = "
				+ verdictHeadlineData.getId());

		boolean status = repository.delete(verdictHeadlineData);

		logger.debug("Deleted VerdictHeadlineData object from database with id = "
				+ verdictHeadlineData.getId()
				+ ". Exiting VerdictHeadlineDataService.delete method");

		return status;
	}

	@Override
	public boolean delete(Long id) throws MessageException {

		logger.debug("Entered VerdictHeadlineDataService delete method");

		if (id == null) {
			logger.error("id found null in VerdictHeadlineDataService.delete method");
			throw (new MessageException("id can't be null"));
		}

		logger.debug("Tryng to get the object for VerdictHeadlineData with id = "
				+ id);

		boolean status = repository.delete(id);

		logger.debug("Deleted VerdictHeadlineData object from database with id = "
				+ id + ". Exiting VerdictHeadlineDataService.delete method");

		return status;
	}

	@Override
	public VerdictHeadlineData readVerdictHeadlineDataForParameters(
			VerdictHeadlineData verdictHeadlineData) throws MessageException {

		logger.debug("Entered VerdictHeadlineDataService readVerdictHeadlineDataForParameters method");

		if (verdictHeadlineData == null) {
			logger.error("VerdictHeadlineData found null in VerdictHeadlineDataService.readVerdictHeadlineDataForParameters method");
			throw (new MessageException("VerdictHeadlineData can't be null"));
		}

		logger.debug("Trying to get the VerdictHeadlineData object");

		verdictHeadlineData = repository
				.findByVerdictAndParameters(verdictHeadlineData);

		logger.debug("Got VerdictHeadlineData object from the database. Exiting VerdictHeadlineDataService.readVerdictHeadlineDataForParameters method");

		return verdictHeadlineData;
	}

}
