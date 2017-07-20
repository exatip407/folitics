package com.ohmuk.folitics.service.comment;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.comment.OpinionCommentCount;
import com.ohmuk.folitics.hibernate.repository.comment.IOpinionCommentCountRepository;

/**
 * Service implementation for entity: {@link OpinionCommentCount}
 * 
 * @author Harish
 *
 */
@Service
@Transactional
public class OpinionCommentCountService implements
		ICommentCountService<OpinionCommentCount> {

	@Autowired
	private IOpinionCommentCountRepository repository;

	private static Logger logger = LoggerFactory
			.getLogger(OpinionCommentCountService.class);

	@Override
	public OpinionCommentCount create(OpinionCommentCount opinionCommentCount)
			throws MessageException {

		logger.info("Entered OpinionCommentCount service create method");

		if (opinionCommentCount.getId().getOpinion() == null) {
			logger.error("opinion in OpinionCommentCount is null");
			throw (new MessageException(
					"opinion in OpinionCommentCount can't be null"));
		}

		if (opinionCommentCount.getId().getOpinion().getId() == null) {
			logger.error("opinion id is null");
			throw (new MessageException("opinion id can't be null"));
		}

		logger.debug("Saving OpinionCommentCount with component id = "
				+ opinionCommentCount.getId().getOpinion().getId());

		return repository.save(opinionCommentCount);
	}

	@Override
	public List<OpinionCommentCount> readAll() {
		logger.info("Entered OpinionCommentCount service readAll method");
		logger.debug("Getting all OpinionCommentCount");
		return repository.findAll();
	}

	@Override
	public OpinionCommentCount update(OpinionCommentCount opinionCommentCount)
			throws MessageException {

		logger.info("Entered OpinionCommentCount service update method");

		if (opinionCommentCount.getId().getOpinion() == null) {
			logger.error("opinion in OpinionCommentCount is null");
			throw (new MessageException(
					"opinion in OpinionCommentCount can't be null"));
		}

		if (opinionCommentCount.getId().getOpinion().getId() == null) {
			logger.error("opinion id is null");
			throw (new MessageException("opinion id can't be null"));
		}

		logger.debug("Updating OpinionCommentCount with component id = "
				+ opinionCommentCount.getId().getOpinion().getId());
		return repository.update(opinionCommentCount);
	}

	@Override
	public void delete(OpinionCommentCount opinionCommentCount)
			throws MessageException {
		logger.info("Entered OpinionCommentCount service delete method");

		if (opinionCommentCount.getId().getOpinion() == null) {
			logger.error("opinion in OpinionCommentCount is null");
			throw (new MessageException(
					"opinion in OpinionCommentCount can't be null"));
		}

		if (opinionCommentCount.getId().getOpinion().getId() == null) {
			logger.error("opinion id is null");
			throw (new MessageException("opinion id can't be null"));
		}

		logger.debug("Deleting OpinionCommentCount with component id = "
				+ opinionCommentCount.getId().getOpinion().getId());
		repository.delete(opinionCommentCount.getId());

	}

	@Override
	public OpinionCommentCount getByComponentId(Long componentId)
			throws MessageException {
		logger.info("Entered OpinionCommentCount service getByComponentId method");

		if (componentId == null) {
			logger.error("opinion id is null");
			throw (new MessageException("opinion id can't be null"));
		}

		logger.debug("Getting OpinionCommentCount with component id = "
				+ componentId);
		return repository.findByComponentId(componentId);
	}

}
