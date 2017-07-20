package com.ohmuk.folitics.hibernate.service.share;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.share.TaskShareCount;
import com.ohmuk.folitics.hibernate.repository.share.ITaskShareCountRepository;

/**
 * Service implementation for entity: {@link TaskShareCount}
 * 
 * @author Harish
 *
 */
@Service
@Transactional
public class TaskShareCountService implements
		IShareCountService<TaskShareCount> {

	private static Logger logger = LoggerFactory
			.getLogger(TaskShareCountService.class);

	@Autowired
	private ITaskShareCountRepository repository;

	@Override
	public TaskShareCount create(TaskShareCount taskShareCount)
			throws MessageException {

		logger.info("Entered TaskShareCount service create method");

		if (taskShareCount.getId().getTask() == null) {
			logger.error("task in TaskShareCount is null");
			throw (new MessageException("task in TaskShareCount can't be null"));
		}

		if (taskShareCount.getId().getTask().getId() == null) {
			logger.error("task id is null");
			throw (new MessageException("task id can't be null"));
		}

		logger.debug("Saving TaskShareCount with component id = "
				+ taskShareCount.getId().getTask().getId());

		return repository.save(taskShareCount);
	}

	@Override
	public List<TaskShareCount> readAll() {

		logger.info("Entered TaskShareCount service readAll method");
		logger.debug("Getting all TaskShareCount");
		return repository.findAll();
	}

	@Override
	public TaskShareCount update(TaskShareCount taskShareCount)
			throws MessageException {

		logger.info("Entered TaskShareCount service update method");

		if (taskShareCount.getId().getTask() == null) {
			logger.error("task in TaskShareCount is null");
			throw (new MessageException("task in TaskShareCount can't be null"));
		}

		if (taskShareCount.getId().getTask().getId() == null) {
			logger.error("task id is null");
			throw (new MessageException("task id can't be null"));
		}

		logger.debug("Updating TaskShareCount with component id = "
				+ taskShareCount.getId().getTask().getId());
		return repository.update(taskShareCount);
	}

	@Override
	public TaskShareCount delete(TaskShareCount taskShareCount)
			throws MessageException {
		logger.info("Entered TaskShareCount service delete method");

		if (taskShareCount.getId().getTask() == null) {
			logger.error("Sentiment in SentimentLikeCount is null");
			throw (new MessageException(
					"Sentiment in SentimentLikeCount can't be null"));
		}

		if (taskShareCount.getId().getTask().getId() == null) {
			logger.error("Task id is null");
			throw (new MessageException("Task id can't be null"));
		}

		logger.debug("Deleting TaskShareCount with component id = "
				+ taskShareCount.getId().getTask().getId());
		return repository.update(taskShareCount);
	}

	@Override
	public TaskShareCount getByComponentId(Long componentId)
			throws MessageException {
		logger.info("Entered TaskShareCount service getByComponentId method");

		if (componentId == null) {
			logger.error("Task id is null");
			throw (new MessageException("Task id can't be null"));
		}

		logger.debug("Getting TaskShareCount with component id = "
				+ componentId);
		return repository.findByComponentId(componentId);
	}

}
