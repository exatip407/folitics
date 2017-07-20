package com.ohmuk.folitics.hibernate.service.like;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.like.TaskLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.TaskLikeCountId;
import com.ohmuk.folitics.hibernate.repository.like.ITaskLikeCountRepository;
@Service
@Transactional
public class TaskLikeCountService implements ILikeCountService<TaskLikeCount> {

	private static Logger logger = LoggerFactory
			.getLogger(TaskLikeCountService.class);

	@Autowired
	private ITaskLikeCountRepository repository;

	@Override
	public TaskLikeCount create(TaskLikeCount taskLikeCount)
			throws MessageException {

		logger.info("Entered TaskLikeCount service create method");

		if (taskLikeCount.getId().getTask() == null) {
			logger.error("Task in TaskLikeCount found to be null");
			throw (new MessageException(
					"Task in TaskLikeCount found to be null"));
		}

		if (taskLikeCount.getId().getTask().getId() == null) {
			logger.error("task id is null");
			throw (new MessageException("task id can't be null"));
		}

		logger.debug("Saving TaskLikeCount with component id = "
				+ taskLikeCount.getId().getTask().getId());
		return repository.save(taskLikeCount);
	}

	public TaskLikeCount read(TaskLikeCountId id) throws MessageException {

		logger.info("Entered TaskLikeCount service read method");

		if (id.getTask() == null) {
			logger.error("Sentiment in TaskLikeCount is null");
			throw (new MessageException(
					"Sentiment in TaskLikeCount can't be null"));
		}

		if (id.getTask().getId() == null) {
			logger.error("Sentiment id is null");
			throw (new MessageException("Sentiment id can't be null"));
		}

		logger.debug("Getting TaskLikeCount with component id = "
				+ id.getTask().getId());
		return repository.find(id);
	}

	@Override
	public List<TaskLikeCount> readAll() {

		logger.info("Entered TaskLikeCount service readAll method");
		logger.debug("Getting all TaskLikeCount");
		return repository.findAll();
	}

	@Override
	public TaskLikeCount update(TaskLikeCount taskLikeCount)
			throws MessageException {

		logger.info("Entered TaskLikeCount service update method");

		if (taskLikeCount.getId().getTask() == null) {
			logger.error("Task in TaskLikeCount is null");
			throw (new MessageException(
					"Task in TaskLikeCount found to be null"));
		}

		if (taskLikeCount.getId().getTask().getId() == null) {
			logger.error("Task id is null");
			throw (new MessageException("Task id found to be null"));
		}

		logger.debug("Updating TaskLikeCount with component id = "
				+ taskLikeCount.getId().getTask().getId());
		return repository.update(taskLikeCount);
	}

	@Override
	public TaskLikeCount delete(TaskLikeCount taskLikeCount)
			throws MessageException {

		logger.info("Entered TaskLikeCount service delete method");

		if (taskLikeCount.getId().getTask() == null) {
			logger.error("Task in TaskLikeCount is null");
			throw (new MessageException(
					"Task in TaskLikeCount found to be null"));
		}

		if (taskLikeCount.getId().getTask().getId() == null) {
			logger.error("Task id is null");
			throw (new MessageException("Task id found to be null"));
		}

		logger.debug("Deleting Task with component id = "
				+ taskLikeCount.getId().getTask().getId());
		repository.delete(taskLikeCount.getId());
		return repository.find(taskLikeCount.getId());
	}

	public TaskLikeCount delete(TaskLikeCountId id) throws MessageException {

		logger.info("Entered TaskLikeCount service delete method");

		if (id.getTask() == null) {
			logger.error("Task in TaskLikeCount is null");
			throw (new MessageException("Task in TaskLikeCount can't be null"));
		}

		if (id.getTask().getId() == null) {
			logger.error("Task id is null");
			throw (new MessageException("Task id can't be null"));
		}

		logger.debug("Deleting TaskLikeCount with component id = "
				+ id.getTask().getId());
		repository.delete(id);
		return repository.find(id);
	}

	@Override
	public TaskLikeCount getByComponentId(Long componentId)
			throws MessageException {

		logger.info("Entered TaskLikeCount service getByComponentId method");

		if (componentId == null) {
			logger.error("Task id is null");
			throw (new MessageException("Task id found be null"));
		}

		logger.debug("Getting TaskLikeCount with component id = " + componentId);
		return repository.findByComponentId(componentId);
	}
}