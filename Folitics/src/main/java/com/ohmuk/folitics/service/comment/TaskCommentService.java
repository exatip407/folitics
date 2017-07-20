package com.ohmuk.folitics.service.comment;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.beans.CommentBean;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.comment.TaskComment;
import com.ohmuk.folitics.hibernate.entity.comment.TaskCommentCount;
import com.ohmuk.folitics.hibernate.entity.comment.TaskCommentCountId;
import com.ohmuk.folitics.hibernate.entity.task.Task;
import com.ohmuk.folitics.hibernate.repository.comment.ITaskCommentRepository;
import com.ohmuk.folitics.util.DateUtils;

@Service
@Transactional
public class TaskCommentService implements ICommentService<TaskComment> {

	private static Logger logger = LoggerFactory
			.getLogger(TaskCommentService.class);

	@Autowired
	private ICommentCountService<TaskCommentCount> taskCommentCountService;

	@Autowired
	private ITaskCommentRepository taskCommentRepository;

	@Override
	public TaskComment create(CommentBean commentBean) throws MessageException,
			Exception {

		logger.debug("Inside create  TaskComment Service");

		if (commentBean.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId found to be null"));
		}

		if (commentBean.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId found to be null"));
		}

		// create TaskComment object from comment bean
		TaskComment taskComment = new TaskComment();
		taskComment.setUserId(commentBean.getUserId());
		taskComment.setComponentId(commentBean.getComponentId());
		taskComment.setComponentType(commentBean.getComponentType());
		taskComment.setComment(commentBean.getComment());

		taskCommentRepository.save(taskComment);

		// add monetization points to user for Comment on any component
	//	taskCommentRepository.addMonetizationPoints(commentBean, "Comment");

		// Adding counter for the Comment
		TaskCommentCount taskCommentCount = new TaskCommentCount();
		Task task = new Task();
		task.setId(commentBean.getComponentId());

		TaskCommentCountId taskCountId = new TaskCommentCountId();
		taskCountId.setTask(task);

		taskCommentCount.setId(taskCountId);

		TaskCommentCount taskCommentCount2 = taskCommentCountService
				.getByComponentId(commentBean.getComponentId());

		// if count avaialble for component adding counter else entering counter
		// for the first time for user and component

		if (taskCommentCount2 != null) {
			taskCommentCount2.setCommentCount(taskCommentCount2
					.getCommentCount() + 1);
			taskCommentCountService.create(taskCommentCount2);
		} else {
			taskCommentCount.setCommentCount(1l);
			taskCommentCountService.create(taskCommentCount);
		}

		logger.debug("Exiting create Comment");
		return taskComment;

	}

	@Override
	public TaskComment read(Long id) throws MessageException {

		logger.info("Entered TaskComment service read method");
		if (id == null) {
			logger.error("Id found to be null");
			throw new MessageException("Id found to be null");
		}

		TaskComment taskComment = taskCommentRepository.find(id);

		logger.info("Exiting TaskComment service read method");
		return taskComment;
	}

	@Override
	public List<TaskComment> readAll() {

		logger.info("Entered TaskComment service readAll method");
		logger.debug("Getting all TaskComment");
		return taskCommentRepository.findAll();
	}

	@Override
	public TaskComment update(CommentBean commentBean) throws MessageException {

		logger.debug("Inside Update Comment method");

		TaskComment originalData = taskCommentRepository.find(commentBean
				.getId());
		if (originalData == null) {
			logger.error("No Record found in database for update");
			throw (new MessageException(
					"No Record found in database for update"));
		}
		originalData.setEditedTime(DateUtils.getSqlTimeStamp());

		TaskComment taskComment = taskCommentRepository.save(originalData);

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
	public TaskComment delete(Long id) throws MessageException {

		logger.info("Inside hard delete comment by ID");

		if (id == null) {
			logger.error("Comment ID found to be null");
			throw (new MessageException("Comment ID can't be null"));
		}

		TaskComment taskComment = taskCommentRepository.find(id);

		if (taskComment == null) {
			logger.error("Something went wrong, record not found for given id: "
					+ id + ", Can't delete record.");
			throw (new MessageException(
					"Something went wrong, record not found for given id: "
							+ id + ", Can't delete record."));
		}

		TaskCommentCount taskCommentCount = taskCommentCountService
				.getByComponentId(id);

		if (taskCommentCount != null) {

			taskCommentCountService.delete(taskCommentCount);

		}

		taskCommentRepository.delete(id);

		TaskComment tComment = taskCommentRepository.find(id);

		logger.debug("Deleted Comment :" + taskComment);
		logger.debug("Exiting  delete comment by ID");

		return tComment;
	}

	@Override
	public List<TaskComment> getByComponentIdAndUserId(Long componentId,
			Long userId) throws MessageException {

		logger.debug("Entered getByComponentIdAndUserId Comment");

		List<TaskComment> taskComments = null;
		taskComments = taskCommentRepository.getByComponentIdAndUserId(
				componentId, userId);

		logger.debug("Comment fetched: " + taskComments.size());
		logger.debug("Exiting getByComponentIdAndUserId comment");

		return taskComments;
	}

	@Override
	public List<TaskComment> getAllCommentsForComponent(Long componentId)
			throws MessageException {

		logger.debug("Entered getAllCommentsForComponent method");

		List<TaskComment> taskComments = null;
		taskComments = taskCommentRepository
				.getAllCommentsForComponent(componentId);

		logger.debug("Comment fetched: " + taskComments.size());
		logger.debug("Exiting getAllCommentsForComponent method");

		return taskComments;
	}

	@Override
	public List<TaskComment> getAllCommentsForUserId(Long userId)
			throws MessageException {
		logger.debug("Entered getAllCommentsForUserId method");

		List<TaskComment> taskComments = null;
		taskComments = taskCommentRepository.getAllCommentsForUserId(userId);

		logger.debug("Comment fetched: " + taskComments.size());
		logger.debug("Exiting getAllCommentsForUserId method");

		return taskComments;
	}

}
