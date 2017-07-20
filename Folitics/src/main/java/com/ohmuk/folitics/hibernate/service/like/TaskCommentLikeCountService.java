package com.ohmuk.folitics.hibernate.service.like;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.like.TaskCommentLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.TaskCommentLikeCountId;
import com.ohmuk.folitics.hibernate.repository.like.ITaskCommentLikeCountRepository;

@Service
@Transactional
public class TaskCommentLikeCountService implements
		ILikeCountService<TaskCommentLikeCount> {

	private static Logger logger = LoggerFactory
			.getLogger(TaskCommentLikeCountService.class);

	@Autowired
	private ITaskCommentLikeCountRepository repository;

	@Override
	public TaskCommentLikeCount create(TaskCommentLikeCount taskCommentLikeCount)
			throws MessageException {
		logger.info("Entered TaskCommentLikeCount service create method");

		if (taskCommentLikeCount.getId().getTaskComment() == null) {
			logger.error("taskComment in TaskCommentLikeCount found to be null");
			throw (new MessageException(
					"taskComment in TaskCommentLikeCount found to be null"));
		}

		if (taskCommentLikeCount.getId().getTaskComment().getId() == null) {
			logger.error("taskComment id is null");
			throw (new MessageException("taskComment id can't be null"));
		}

		logger.debug("Saving TaskCommentLikeCount with component id = "
				+ taskCommentLikeCount.getId().getTaskComment().getId());
		return repository.save(taskCommentLikeCount);
	}

	public TaskCommentLikeCount read(TaskCommentLikeCountId id)
			throws MessageException {

		logger.info("Entered TaskCommentLikeCount service read method");

		if (id.getTaskComment() == null) {
			logger.error("TaskComment in TaskCommentLikeCount is null");
			throw (new MessageException(
					"TaskComment in TaskCommentLikeCount can't be null"));
		}

		if (id.getTaskComment().getId() == null) {
			logger.error("TaskComment id is null");
			throw (new MessageException("TaskComment id can't be null"));
		}

		logger.debug("Getting TaskCommentLikeCount with component id = "
				+ id.getTaskComment().getId());
		return repository.find(id);
	}

	@Override
	public List<TaskCommentLikeCount> readAll() {
		logger.info("Entered TaskCommentLikeCount service readAll method");
		logger.debug("Getting all TaskCommentLikeCount");
		return repository.findAll();
	}

	@Override
	public TaskCommentLikeCount update(TaskCommentLikeCount taskCommentLikeCount)
			throws MessageException {
		logger.info("Entered TaskCommentLikeCount service update method");

		if (taskCommentLikeCount.getId().getTaskComment() == null) {
			logger.error("taskComment in TaskCommentLikeCount is null");
			throw (new MessageException(
					"taskComment in TaskCommentLikeCount found to be null"));
		}

		if (taskCommentLikeCount.getId().getTaskComment().getId() == null) {
			logger.error("taskComment id is null");
			throw (new MessageException("taskComment id found to be null"));
		}

		logger.debug("Updating TaskCommentLikeCount with component id = "
				+ taskCommentLikeCount.getId().getTaskComment().getId());
		return repository.update(taskCommentLikeCount);
	}

	public TaskCommentLikeCount delete(TaskCommentLikeCountId id)
			throws MessageException {

		logger.info("Entered TaskCommentLikeCount service delete method");

		if (id.getTaskComment() == null) {
			logger.error("TaskComment in TaskCommentLikeCount is null");
			throw (new MessageException("TaskComment in TaskCommentLikeCount can't be null"));
		}

		if (id.getTaskComment().getId() == null) {
			logger.error("TaskComment id is null");
			throw (new MessageException("Task id can't be null"));
		}

		logger.debug("Deleting TaskCommentLikeCount with component id = "
				+ id.getTaskComment().getId());
		repository.delete(id);
		return repository.find(id);
	}

	@Override
	public TaskCommentLikeCount delete(TaskCommentLikeCount taskCommentLikeCount)
			throws MessageException {
		logger.info("Entered TaskCommentLikeCount service delete method");

		if (taskCommentLikeCount.getId().getTaskComment() == null) {
			logger.error("taskComment in TaskCommentLikeCount is null");
			throw (new MessageException(
					"taskComment in TaskCommentLikeCount found to be null"));
		}

		if (taskCommentLikeCount.getId().getTaskComment().getId() == null) {
			logger.error("taskComment id is null");
			throw (new MessageException("taskComment id found to be null"));
		}

		logger.debug("Deleting taskComment with component id = "
				+ taskCommentLikeCount.getId().getTaskComment().getId());
		repository.delete(taskCommentLikeCount.getId());
		return repository.find(taskCommentLikeCount.getId());
	}

	@Override
	public TaskCommentLikeCount getByComponentId(Long componentId)
			throws MessageException {
		logger.info("Entered TaskCommentLikeCount service getByComponentId method");

		if (componentId == null) {
			logger.error("taskComment id is null");
			throw (new MessageException("taskComment id found be null"));
		}

		logger.debug("Getting TaskCommentLikeCount with component id = "
				+ componentId);
		return repository.findByComponentId(componentId);
	}

}
