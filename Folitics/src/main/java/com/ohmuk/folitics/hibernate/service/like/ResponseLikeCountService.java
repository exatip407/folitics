package com.ohmuk.folitics.hibernate.service.like;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.like.ResponseLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.ResponseLikeCountId;
import com.ohmuk.folitics.hibernate.repository.like.IResponseLikeCountRepository;

@Service
@Transactional
public class ResponseLikeCountService implements
		ILikeCountService<ResponseLikeCount> {

	private static Logger logger = LoggerFactory
			.getLogger(ResponseLikeCountService.class);

	@Autowired
	private IResponseLikeCountRepository repository;

	@Override
	public ResponseLikeCount create(ResponseLikeCount responseLikeCount)
			throws MessageException {
		logger.info("Entered ResponseLikeCount service create method");

		if (responseLikeCount.getId().getResponse() == null) {
			logger.error("Response in ResponseLikeCount found to be null");
			throw (new MessageException(
					"Response in ResponseLikeCount found to be null"));
		}

		if (responseLikeCount.getId().getResponse().getId() == null) {
			logger.error("response id is null");
			throw (new MessageException("response id can't be null"));
		}

		logger.debug("Saving ResponseLikeCount with component id = "
				+ responseLikeCount.getId().getResponse().getId());
		return repository.save(responseLikeCount);
	}

	public ResponseLikeCount read(ResponseLikeCountId id)
			throws MessageException {

		logger.info("Entered ResponseLikeCount service read method");

		if (id.getResponse() == null) {
			logger.error("Response in ResponseLikeCount is null");
			throw (new MessageException(
					"Response in ResponseLikeCount can't be null"));
		}

		if (id.getResponse().getId() == null) {
			logger.error("Response id is null");
			throw (new MessageException("Response id can't be null"));
		}

		logger.debug("Getting ResponseLikeCount with component id = "
				+ id.getResponse().getId());
		return repository.find(id);
	}

	@Override
	public List<ResponseLikeCount> readAll() {
		logger.info("Entered ResponseLikeCount service readAll method");
		logger.debug("Getting all ResponseLikeCount");
		return repository.findAll();
	}

	@Override
	public ResponseLikeCount update(ResponseLikeCount responseLikeCount)
			throws MessageException {
		logger.info("Entered ResponseLikeCount service update method");

		if (responseLikeCount.getId().getResponse() == null) {
			logger.error("Response in ResponseLikeCount is null");
			throw (new MessageException(
					"Response in ResponseLikeCount found to be null"));
		}

		if (responseLikeCount.getId().getResponse().getId() == null) {
			logger.error("Response id is null");
			throw (new MessageException("Response id found to be null"));
		}

		logger.debug("Updating ResponseLikeCount with component id = "
				+ responseLikeCount.getId().getResponse().getId());
		return repository.update(responseLikeCount);
	}

	@Override
	public ResponseLikeCount delete(ResponseLikeCount responseLikeCount)
			throws MessageException {
		logger.info("Entered ResponseLikeCount service delete method");

		if (responseLikeCount.getId().getResponse() == null) {
			logger.error("Response in ResponseLikeCount is null");
			throw (new MessageException(
					"Response in ResponseLikeCount found to be null"));
		}

		if (responseLikeCount.getId().getResponse().getId() == null) {
			logger.error("Response id is null");
			throw (new MessageException("Response id found to be null"));
		}

		logger.debug("Deleting Response with component id = "
				+ responseLikeCount.getId().getResponse().getId());
		repository.delete(responseLikeCount.getId());
		return repository.find(responseLikeCount.getId());
	}

	public ResponseLikeCount delete(ResponseLikeCountId id)
			throws MessageException {

		logger.info("Entered ResponseLikeCount service delete method");

		if (id.getResponse() == null) {
			logger.error("Response in ResponseLikeCount is null");
			throw (new MessageException(
					"Response in ResponseLikeCount can't be null"));
		}

		if (id.getResponse().getId() == null) {
			logger.error("Response id is null");
			throw (new MessageException("Response id can't be null"));
		}

		logger.debug("Deleting ResponseLikeCount with component id = "
				+ id.getResponse().getId());
		repository.delete(id);
		return repository.find(id);
	}

	@Override
	public ResponseLikeCount getByComponentId(Long componentId)
			throws MessageException {

		logger.info("Entered ResponseLikeCount service getByComponentId method");

		if (componentId == null) {
			logger.error("Response id is null");
			throw (new MessageException("Response id found be null"));
		}

		logger.debug("Getting ResponseLikeCount with component id = "
				+ componentId);
		return repository.findByComponentId(componentId);
	}

}
