package com.ohmuk.folitics.hibernate.service.verdict.global;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdict;
import com.ohmuk.folitics.hibernate.repository.verdict.global.IGlobalVerdictRepository;

/**
 * Service implementation for {@link GlobalVerdict}
 * 
 * @author Abhishek
 *
 */
@Service
@Transactional
public class GlobalVerdictService implements IGlobalVerdictService {

	private static Logger logger = LoggerFactory
			.getLogger(GlobalVerdictService.class);

	@Autowired
	private IGlobalVerdictRepository repository;

	@Override
	public GlobalVerdict create(GlobalVerdict globalVerdict)
			throws MessageException {

		logger.debug("Entered GlobalVerdictService create method");

		if (globalVerdict == null) {
			logger.error("GlobalVerdict object found null in GlobalVerdictService.create method");
			throw (new MessageException("GlobalVerdict object can't be null"));
		}

		logger.debug("Trying to save the GlobalVerdict object");

		globalVerdict = repository.save(globalVerdict);

		logger.debug("Saved GlobalVerdict object in the database. Exiting GlobalVerdictService.create method");

		return globalVerdict;
	}

	@Override
	public GlobalVerdict read() {

		logger.debug("Entered GlobalVerdictService read method");
		logger.debug("Trying to get the GlobalVerdict objects from databse");

		GlobalVerdict globalVerdict = repository.find();

		logger.debug("Got the GlobalVerdict from database. Exiting GlobalVerdictService.read method");

		return globalVerdict;
	}

	@Override
	public GlobalVerdict update(GlobalVerdict globalVerdict)
			throws MessageException {

		logger.debug("Entered GlobalVerdictService update method");

		if (globalVerdict == null) {
			logger.error("GlobalVerdict object found null in GlobalVerdictService.update method");
			throw (new MessageException("GlobalVerdict object can't be null"));
		}

		if (globalVerdict.getId() == null) {
			logger.error("Id in GlobalVerdict object found null in GlobalVerdictService.update method");
			throw (new MessageException(
					"Id in GlobalVerdict object can't be null"));
		}

		logger.debug("Trying to update GlobalVerdict object in databse for id = "
				+ globalVerdict.getId());

		globalVerdict = repository.update(globalVerdict);

		logger.debug("Updated GlobalVerdict in database. Exiting GlobalVerdictService.update method");

		return globalVerdict;
	}

	@Override
	public boolean delete(GlobalVerdict globalVerdict) throws MessageException {

		logger.debug("Entered GlobalVerdictService delete method");

		if (globalVerdict == null) {
			logger.error("GlobalVerdict object found null in GlobalVerdictService.delete method");
			throw (new MessageException("GlobalVerdict object can't be null"));
		}

		if (globalVerdict.getId() == null) {
			logger.error("Id in GlobalVerdict object found null in GlobalVerdictService.delete method");
			throw (new MessageException(
					"Id in GlobalVerdict object can't be null"));
		}

		logger.debug("Tryng to get the object for GlobalVerdict with id = "
				+ globalVerdict.getId());

		boolean status = repository.delete(globalVerdict);

		logger.debug("Deleted GlobalVerdict object from database with id = "
				+ globalVerdict.getId()
				+ ". Exiting GlobalVerdictService.delete method");

		return status;
	}

	@Override
	public boolean delete(Long id) throws MessageException {

		logger.debug("Entered GlobalVerdictService delete method");

		if (id == null) {
			logger.error("id found null in GlobalVerdictService.delete method");
			throw (new MessageException("id can't be null"));
		}

		logger.debug("Tryng to get the object for GlobalVerdict with id = "
				+ id);

		boolean status = repository.delete(id);

		logger.debug("Deleted GlobalVerdict object from database with id = "
				+ id + ". Exiting GlobalVerdictService.delete method");

		return status;
	}

}
