package com.ohmuk.folitics.service.comment;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.comment.ResponseCommentCount;
import com.ohmuk.folitics.hibernate.repository.comment.IResponseCommentCountRepository;

/**
 * Service implementation for entity: {@link ResponseCommentCount}
 * 
 * @author Harish
 *
 */
@Service
@Transactional
public class ResponseCommentCountService implements
ICommentCountService<ResponseCommentCount>  {
	
	@Autowired
	private IResponseCommentCountRepository repository;

	private static Logger logger = LoggerFactory
			.getLogger(ResponseCommentCountService.class);


	@Override
	public ResponseCommentCount create(ResponseCommentCount responseCommentCount)
			throws MessageException {
		logger.info("Entered ResponseCommentCount service create method");

		if (responseCommentCount.getId().getResponse() == null) {
			logger.error("response in ResponseCommentCount is null");
			throw (new MessageException(
					"response in ResponseCommentCount found to be  be null"));
		}

		if (responseCommentCount.getId().getResponse().getId() == null) {
			logger.error("response id is null");
			throw (new MessageException("response id can'responseCommentCount be null"));
		}

		logger.debug("Saving ResponseCommentCount with component id = "
				+ responseCommentCount.getId().getResponse().getId());

		return repository.save(responseCommentCount);
	}

	@Override
	public List<ResponseCommentCount> readAll() {
		logger.info("Entered ResponseCommentCount service readAll method");
		logger.debug("Getting all ResponseCommentCount");
		return repository.findAll();
	}

	@Override
	public ResponseCommentCount update(ResponseCommentCount responseCommentCount)
			throws MessageException {
		logger.info("Entered ResponseCommentCount service update method");

		if (responseCommentCount.getId().getResponse() == null) {
			logger.error("response in ResponseCommentCount is null");
			throw (new MessageException(
					"response in ResponseCommentCount can'responseCommentCount be null"));
		}

		if (responseCommentCount.getId().getResponse().getId() == null) {
			logger.error("response id is null");
			throw (new MessageException("response id can'responseCommentCount be null"));
		}

		logger.debug("Updating ResponseCommentCount with component id = "
				+ responseCommentCount.getId().getResponse().getId());
		return repository.update(responseCommentCount);
	}

	@Override
	public void delete(ResponseCommentCount responseCommentCount) throws MessageException {
		logger.info("Entered ResponseCommentCount service delete method");

		if (responseCommentCount.getId().getResponse() == null) {
			logger.error("Response in ResponseCommentCount is null");
			throw (new MessageException(
					"Response in ResponseCommentCount can'responseCommentCount be null"));
		}

		if (responseCommentCount.getId().getResponse().getId() == null) {
			logger.error("Response id is null");
			throw (new MessageException("Response id can'responseCommentCount be null"));
		}

		logger.debug("Deleting ResponseCommentCount with component id = "
				+ responseCommentCount.getId().getResponse().getId());
		repository.delete(responseCommentCount.getId());
		
	}

	@Override
	public ResponseCommentCount getByComponentId(Long componentId)
			throws MessageException {
		logger.info("Entered ResponseCommentCount service getByComponentId method");

		if (componentId == null) {
			logger.error("Response id is null");
			throw (new MessageException("Response id can'responseCommentCount be null"));
		}

		logger.debug("Getting ResponseCommentCount with component id = "
				+ componentId);
		return repository.findByComponentId(componentId);
	}

}
