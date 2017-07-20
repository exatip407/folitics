package com.ohmuk.folitics.service.comment;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.comment.TaskParticipantsComment;
import com.ohmuk.folitics.hibernate.entity.comment.TaskParticipantsCommentCount;
import com.ohmuk.folitics.hibernate.repository.comment.ITaskParticipantsCommentCountRepository;

/**
 * Service implementation for entity: {@link TaskParticipantsComment}
 * 
 * @author Harish
 *
 */
@Service
@Transactional
public class TaskParticipantsCommentCountService implements
		ICommentCountService<TaskParticipantsCommentCount> {

	@Autowired
	private ITaskParticipantsCommentCountRepository repository;

	private static Logger logger = LoggerFactory
			.getLogger(TaskParticipantsCommentCountService.class);

	@Override
	public TaskParticipantsCommentCount create(TaskParticipantsCommentCount taskParticipantsCommentCount)
			throws MessageException {
		logger.info("Entered TaskParticipantsCommentCount service create method");

		if (taskParticipantsCommentCount.getId().getTask() == null) {
			logger.error("task in TaskParticipantsCommentCount is null");
			throw (new MessageException(
					"task in TaskParticipantsCommentCount can't be null"));
		}

		if (taskParticipantsCommentCount.getId().getTask().getId() == null) {
			logger.error("task id is null");
			throw (new MessageException("task id can't be null"));
		}

		logger.debug("Saving TaskParticipantsCommentCount with component id = "
				+ taskParticipantsCommentCount.getId().getTask().getId());

		return repository.save(taskParticipantsCommentCount);
	}

	@Override
	public List<TaskParticipantsCommentCount> readAll() {
		logger.info("Entered TaskParticipantsCommentCount service readAll method");
		logger.debug("Getting all TaskParticipantsCommentCount");
		return repository.findAll();
	}

	@Override
	public TaskParticipantsCommentCount update(TaskParticipantsCommentCount taskParticipantsCommentCount)
			throws MessageException {

		logger.info("Entered TaskParticipantsCommentCount service update method");

		if (taskParticipantsCommentCount.getId().getTask() == null) {
			logger.error("task in TaskParticipantsCommentCount is null");
			throw (new MessageException(
					"task in TaskParticipantsCommentCount can't be null"));
		}

		if (taskParticipantsCommentCount.getId().getTask().getId() == null) {
			logger.error("task id is null");
			throw (new MessageException("task id can't be null"));
		}

		logger.debug("Updating TaskParticipantsCommentCount with component id = "
				+ taskParticipantsCommentCount.getId().getTask().getId());
		return repository.update(taskParticipantsCommentCount);
	}

	@Override
	public void delete(TaskParticipantsCommentCount taskParticipantsCommentCount) throws MessageException {
		logger.info("Entered TaskParticipantsCommentCount service delete method");

		if (taskParticipantsCommentCount.getId().getTask() == null) {
			logger.error("Task in TaskParticipantsCommentCount is null");
			throw (new MessageException(
					"Task in TaskParticipantsCommentCount can't be null"));
		}

		if (taskParticipantsCommentCount.getId().getTask().getId() == null) {
			logger.error("Task id is null");
			throw (new MessageException("Task id can't be null"));
		}

		logger.debug("Deleting TaskParticipantsCommentCount with component id = "
				+ taskParticipantsCommentCount.getId().getTask().getId());
		repository.delete(taskParticipantsCommentCount.getId());

	}

	@Override
	public TaskParticipantsCommentCount getByComponentId(Long componentId)
			throws MessageException {
		logger.info("Entered TaskParticipantsCommentCount service getByComponentId method");

		if (componentId == null) {
			logger.error("Task id is null");
			throw (new MessageException("Task id can't be null"));
		}

		logger.debug("Getting TaskParticipantsCommentCount with component id = "
				+ componentId);
		return repository.findByComponentId(componentId);
	}

}
