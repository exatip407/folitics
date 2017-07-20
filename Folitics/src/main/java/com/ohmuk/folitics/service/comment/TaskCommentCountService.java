package com.ohmuk.folitics.service.comment;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.comment.TaskCommentCount;
import com.ohmuk.folitics.hibernate.repository.comment.ITaskCommentCountRepository;

/**
 * Service implementation for entity: {@link TaskCommentCount}
 * 
 * @author Harish
 *
 */
@Service
@Transactional
public class TaskCommentCountService implements
		ICommentCountService<TaskCommentCount> {

	@Autowired
	private ITaskCommentCountRepository repository;

	private static Logger logger = LoggerFactory
			.getLogger(TaskCommentCountService.class);

	@Override
	public TaskCommentCount create(TaskCommentCount taskCommentCount)
			throws MessageException {

		logger.info("Entered TaskCommentCount service create method");

		if (taskCommentCount.getId().getTask() == null) {
			logger.error("task in TaskCommentCount is null");
			throw (new MessageException(
					"task in TaskCommentCount can'taskCommentCount be null"));
		}

		if (taskCommentCount.getId().getTask().getId() == null) {
			logger.error("task id is null");
			throw (new MessageException("task id can'taskCommentCount be null"));
		}

		logger.debug("Saving TaskCommentCount with component id = "
				+ taskCommentCount.getId().getTask().getId());

		return repository.save(taskCommentCount);
	}

	@Override
	public List<TaskCommentCount> readAll() {

		logger.info("Entered TaskCommentCount service readAll method");
		logger.debug("Getting all TaskCommentCount");
		return repository.findAll();
	}

	@Override
	public TaskCommentCount update(TaskCommentCount taskCommentCount)
			throws MessageException {

		logger.info("Entered TaskCommentCount service update method");

		if (taskCommentCount.getId().getTask() == null) {
			logger.error("task in TaskCommentCount is null");
			throw (new MessageException(
					"task in TaskCommentCount can't be null"));
		}

		if (taskCommentCount.getId().getTask().getId() == null) {
			logger.error("task id is null");
			throw (new MessageException("task id can't be null"));
		}

		logger.debug("Updating TaskCommentCount with component id = "
				+ taskCommentCount.getId().getTask().getId());
		return repository.update(taskCommentCount);
	}

	@Override
	public void delete(TaskCommentCount taskCommentCount)
			throws MessageException {

		logger.info("Entered TaskCommentCount service delete method");

		if (taskCommentCount.getId().getTask() == null) {
			logger.error("Task in TaskCommentCount is null");
			throw (new MessageException(
					"Task in TaskCommentCount can't be null"));
		}

		if (taskCommentCount.getId().getTask().getId() == null) {
			logger.error("Task id is null");
			throw (new MessageException("Task id can't be null"));
		}

		logger.debug("Deleting TaskCommentCount with component id = "
				+ taskCommentCount.getId().getTask().getId());
		repository.delete(taskCommentCount.getId());
	}

	@Override
	public TaskCommentCount getByComponentId(Long componentId)
			throws MessageException {

		logger.info("Entered TaskCommentCount service getByComponentId method");

		if (componentId == null) {
			logger.error("Task id is null");
			throw (new MessageException("Task id can't be null"));
		}

		logger.debug("Getting TaskCommentCount with component id = "
				+ componentId);
		return repository.findByComponentId(componentId);
	}

}
