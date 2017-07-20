package com.ohmuk.folitics.hibernate.service.like;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.like.OpinionLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.OpinionLikeCountId;
import com.ohmuk.folitics.hibernate.repository.like.IOpinionLikeCountRepository;

/**
 * Service implementation for entity: {@link OpinionLikeCount}
 * 
 * @author Harish
 *
 */
@Service
@Transactional
public class OpinionLikeCountService implements
		ILikeCountService<OpinionLikeCount> {

	private static Logger logger = LoggerFactory
			.getLogger(OpinionLikeCountService.class);

	@Autowired
	private IOpinionLikeCountRepository repository;

	@Override
	public OpinionLikeCount create(OpinionLikeCount opinionLikeCount)
			throws MessageException {
		logger.info("Entered OpinionLikeCount service create method");

		if (opinionLikeCount.getId().getOpinion() == null) {
			logger.error("Opinion in OpinionLikeCount is null");
			throw (new MessageException(
					"Opinion in OpinionLikeCount can't be null"));
		}

		if (opinionLikeCount.getId().getOpinion().getId() == null) {
			logger.error("Opinion id is null");
			throw (new MessageException("Opinion id can't be null"));
		}

		logger.debug("Saving OpinionLikeCount with component id = "
				+ opinionLikeCount.getId().getOpinion().getId());
		return repository.save(opinionLikeCount);
	}

	public OpinionLikeCount read(OpinionLikeCountId id) throws MessageException {

		logger.info("Entered OpinionLikeCount service read method");

		if (id.getOpinion() == null) {
			logger.error("Opinion in OpinionLikeCount is null");
			throw (new MessageException(
					"Opinion in OpinionLikeCount can't be null"));
		}

		if (id.getOpinion().getId() == null) {
			logger.error("Opinion id is null");
			throw (new MessageException("Opinion id can't be null"));
		}

		logger.debug("Getting OpinionLikeCount with component id = "
				+ id.getOpinion().getId());
		return repository.find(id);
	}

	@Override
	public List<OpinionLikeCount> readAll() {
		logger.info("Entered OpinionLikeCount service readAll method");
		logger.debug("Getting all OpinionLikeCount");
		return repository.findAll();
	}

	@Override
	public OpinionLikeCount update(OpinionLikeCount opinionLikeCount)
			throws MessageException {
		logger.info("Entered OpinionLikeCount service update method");

		if (opinionLikeCount.getId().getOpinion() == null) {
			logger.error("Opinion in OpinionLikeCount is null");
			throw (new MessageException(
					"Opinion in OpinionLikeCount can't be null"));
		}

		if (opinionLikeCount.getId().getOpinion().getId() == null) {
			logger.error("Opinion id is null");
			throw (new MessageException("Opinion id can't be null"));
		}

		logger.debug("Updating OpinionLikeCount with component id = "
				+ opinionLikeCount.getId().getOpinion().getId());
		return repository.update(opinionLikeCount);
	}

	public OpinionLikeCount delete(OpinionLikeCountId id)
			throws MessageException {

		logger.info("Entered OpinionLikeCount service delete method");

		if (id.getOpinion() == null) {
			logger.error("Opinion in OpinionLikeCount is null");
			throw (new MessageException(
					"Opinion in OpinionLikeCount can't be null"));
		}

		if (id.getOpinion() == null) {
			logger.error("Opinion id is null");
			throw (new MessageException("Opinion id can't be null"));
		}

		logger.debug("Deleting OpinionLikeCount with component id = "
				+ id.getOpinion().getId());
		repository.delete(id);
		return repository.find(id);
	}

	@Override
	public OpinionLikeCount delete(OpinionLikeCount opinionLikeCount)
			throws MessageException {
		logger.info("Entered OpinionLikeCount service delete method");

		if (opinionLikeCount.getId().getOpinion() == null) {
			logger.error("Opinion in OpinionLikeCount is null");
			throw (new MessageException(
					"Opinion in OpinionLikeCount can't be null"));
		}

		if (opinionLikeCount.getId().getOpinion().getId() == null) {
			logger.error("Opinion id is null");
			throw (new MessageException("Opinion id can't be null"));
		}

		logger.debug("Deleting OpinionLikeCount with component id = "
				+ opinionLikeCount.getId().getOpinion().getId());
		repository.delete(opinionLikeCount.getId());
		return repository.find(opinionLikeCount.getId());
	}

	@Override
	public OpinionLikeCount getByComponentId(Long componentId)
			throws MessageException {
		logger.info("Entered OpinionLikeCount service getByComponentId method");

		if (componentId == null) {
			logger.error("Opinion id is null");
			throw (new MessageException("Opinion id can't be null"));
		}

		logger.debug("Getting OpinionLikeCount with component id = "
				+ componentId);
		return repository.findByComponentId(componentId);
	}

}
