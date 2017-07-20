package com.ohmuk.folitics.service.comment;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ohmuk.folitics.beans.CommentBean;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.comment.TaskParticipantsComment;
import com.ohmuk.folitics.hibernate.entity.comment.TaskParticipantsCommentCount;
import com.ohmuk.folitics.hibernate.entity.comment.TaskParticipantsCommentCountId;
import com.ohmuk.folitics.hibernate.entity.task.Task;
import com.ohmuk.folitics.hibernate.repository.comment.ITaskParticipantsCommentRepository;
import com.ohmuk.folitics.util.DateUtils;

public class TaskParticipantsCommentService implements
		ICommentService<TaskParticipantsComment> {

	private static Logger logger = LoggerFactory
			.getLogger(TaskParticipantsCommentService.class);

	@Autowired
	private ICommentCountService<TaskParticipantsCommentCount> taskParticipantsCommentCountService;

	@Autowired
	private ITaskParticipantsCommentRepository taskParticipantsCommentRepository;

	@Override
	public TaskParticipantsComment create(CommentBean commentBean)
			throws MessageException, Exception {
		logger.debug("Inside create  TaskParticipantsComment Service");

		if (commentBean.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId found to be null"));
		}

		if (commentBean.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId found to be null"));
		}

		// create TaskParticipantsComment object from comment bean
		TaskParticipantsComment taskParticipantsComment = new TaskParticipantsComment();
		taskParticipantsComment.setUserId(commentBean.getUserId());
		taskParticipantsComment.setComponentId(commentBean.getComponentId());
		taskParticipantsComment
				.setComponentType(commentBean.getComponentType());
		taskParticipantsComment.setComment(commentBean.getComment());

		taskParticipantsCommentRepository.save(taskParticipantsComment);

		// add monetization points to user for Comment on any component
		taskParticipantsCommentRepository.addMonetizationPoints(commentBean,
				"Comment");

		// Adding counter for the Comment
		TaskParticipantsCommentCount taskCommentCount = new TaskParticipantsCommentCount();
		Task task = new Task();
		task.setId(commentBean.getComponentId());

		TaskParticipantsCommentCountId taskCountId = new TaskParticipantsCommentCountId();
		taskCountId.setTask(task);

		taskCommentCount.setId(taskCountId);

		TaskParticipantsCommentCount taskCommentCount2 = taskParticipantsCommentCountService
				.getByComponentId(commentBean.getComponentId());

		// if count avaialble for component adding counter else entering counter
		// for the first time for user and component

		if (taskCommentCount2 != null) {
			taskCommentCount2.setCommentCount(taskCommentCount2
					.getCommentCount() + 1);
			taskParticipantsCommentCountService.create(taskCommentCount2);
		} else {
			taskCommentCount.setCommentCount(1l);
			taskParticipantsCommentCountService.create(taskCommentCount);
		}

		logger.debug("Exiting create Comment");
		return taskParticipantsComment;
	}

	@Override
	public TaskParticipantsComment read(Long id) throws MessageException {
		logger.info("Entered TaskParticipantsComment service read method");
		if (id == null) {
			logger.error("Id found to be null");
			throw new MessageException("Id found to be null");
		}

		TaskParticipantsComment taskComment = taskParticipantsCommentRepository
				.find(id);

		logger.info("Exiting TaskParticipantsComment service read method");
		return taskComment;
	}

	@Override
	public List<TaskParticipantsComment> readAll() {
		logger.info("Entered TaskParticipantsComment service readAll method");
		logger.debug("Getting all TaskParticipantsComment");
		return taskParticipantsCommentRepository.findAll();
	}

	@Override
	public TaskParticipantsComment update(CommentBean commentBean)
			throws MessageException {
		logger.debug("Inside Update Comment method");

		TaskParticipantsComment originalData = taskParticipantsCommentRepository
				.find(commentBean.getId());
		if (originalData == null) {
			logger.error("No Record found in database for update");
			throw (new MessageException(
					"No Record found in database for update"));
		}
		originalData.setEditedTime(DateUtils.getSqlTimeStamp());

		TaskParticipantsComment taskComment = taskParticipantsCommentRepository
				.save(originalData);

		if (taskComment == null) {
			logger.error("Something went wrong, record not updated");
			throw (new MessageException(
					"Something went wrong, record not updated"));
		}

		logger.debug("Updated comment  : " + taskComment);
		logger.debug("Exiting Update");

		return taskComment;
	}

	@Override
	public TaskParticipantsComment delete(Long id) throws MessageException,
			Exception {
		logger.info("Inside hard delete comment by ID");

		if (id == null) {
			logger.error("Comment ID found to be null");
			throw (new MessageException("Comment ID can't be null"));
		}

		TaskParticipantsComment taskComment = taskParticipantsCommentRepository
				.find(id);

		if (taskComment == null) {
			logger.error("Something went wrong, record not found for given id: "
					+ id + ", Can't delete record.");
			throw (new MessageException(
					"Something went wrong, record not found for given id: "
							+ id + ", Can't delete record."));
		}

		TaskParticipantsCommentCount taskCommentCount = taskParticipantsCommentCountService
				.getByComponentId(id);

		if (taskCommentCount != null) {

			taskParticipantsCommentCountService.delete(taskCommentCount);

		}

		taskParticipantsCommentRepository.delete(id);

		TaskParticipantsComment tComment = taskParticipantsCommentRepository
				.find(id);

		logger.debug("Deleted Comment :" + taskComment);
		logger.debug("Exiting  delete comment by ID");

		return tComment;
	}

	@Override
	public List<TaskParticipantsComment> getByComponentIdAndUserId(
			Long componentId, Long userId) throws MessageException {
		logger.debug("Entered getByComponentIdAndUserId Comment");

		List<TaskParticipantsComment> taskComments = null;
		taskComments = taskParticipantsCommentRepository
				.getByComponentIdAndUserId(componentId, userId);

		logger.debug("Comment fetched: " + taskComments.size());
		logger.debug("Exiting getByComponentIdAndUserId comment");

		return taskComments;
	}

	@Override
	public List<TaskParticipantsComment> getAllCommentsForComponent(
			Long componentId) throws MessageException {
		logger.debug("Entered getAllCommentsForComponent method");

		List<TaskParticipantsComment> taskComments = null;
		taskComments = taskParticipantsCommentRepository
				.getAllCommentsForComponent(componentId);

		logger.debug("Comment fetched: " + taskComments.size());
		logger.debug("Exiting getAllCommentsForComponent method");

		return taskComments;
	}

	@Override
	public List<TaskParticipantsComment> getAllCommentsForUserId(Long userId)
			throws MessageException {
		logger.debug("Entered getAllCommentsForUserId method");

		List<TaskParticipantsComment> taskComments = null;
		taskComments = taskParticipantsCommentRepository
				.getAllCommentsForUserId(userId);

		logger.debug("Comment fetched: " + taskComments.size());
		logger.debug("Exiting getAllCommentsForUserId method");

		return taskComments;
	}

}
