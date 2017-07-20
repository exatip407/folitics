package com.ohmuk.folitics.hibernate.repository.comment;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ohmuk.folitics.hibernate.entity.comment.TaskCommentCount;
import com.ohmuk.folitics.hibernate.entity.comment.TaskCommentCountId;

@Component
@Repository
public class TaskCommentCountRepositoryImplementation implements
		ITaskCommentCountRepository {

	private static Logger logger = LoggerFactory
			.getLogger(TaskCommentCountRepositoryImplementation.class);
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public TaskCommentCount save(TaskCommentCount taskCommentCount) {

		logger.info("Entered TaskCommentCount save method");
		logger.debug("Saving comment count for task id = "
				+ taskCommentCount.getId().getTask().getId());

		TaskCommentCountId id = (TaskCommentCountId) getSession().save(
				taskCommentCount);
		taskCommentCount = (TaskCommentCount) getSession().get(
				TaskCommentCount.class, id);

		logger.info("TaskCommentCount saved. Exiting save method");
		return taskCommentCount;
	}

	@Override
	public TaskCommentCount update(TaskCommentCount taskCommentCount) {

		logger.info("Entered TaskCommentCount update method");
		logger.debug("Updating comment count for task id = "
				+ taskCommentCount.getId().getTask().getId());

		taskCommentCount = (TaskCommentCount) getSession().merge(
				taskCommentCount);
		getSession().update(taskCommentCount);

		taskCommentCount = (TaskCommentCount) getSession().get(
				TaskCommentCount.class, taskCommentCount.getId());

		logger.info("TaskCommentCount updated. Exiting update method");
		return taskCommentCount;
	}

	@Override
	public List<TaskCommentCount> findAll() {
		logger.info("Entered TaskCommentCount findAll method");

		Criteria selectAllCriteria = getSession().createCriteria(
				TaskCommentCount.class);
		@SuppressWarnings("unchecked")
		List<TaskCommentCount> taskCommentCounts = selectAllCriteria.list();

		logger.info("Returning all results from database. Exiting findAll method");
		return taskCommentCounts;
	}

	@Override
	public TaskCommentCount findByComponentId(Long taskId) {

		logger.info("Entered TaskCommentCount findByComponentId method");
		logger.debug("Finding like count for task id = " + taskId);

		Criteria selectCriteria = getSession().createCriteria(
				TaskCommentCount.class);
		selectCriteria.add(Restrictions.eq("id.task.id", taskId));
		TaskCommentCount taskCommentCount = (TaskCommentCount) selectCriteria
				.uniqueResult();

		logger.info("Returning TaskCommentCount. Exiting findByComponentId method");
		return taskCommentCount;
	}

	@Override
	public TaskCommentCount find(TaskCommentCountId id) {
		logger.info("Entered TaskCommentCount find method");
		logger.debug("Getting like count for task id = " + id.getTask().getId());

		TaskCommentCount taskCommentCount = (TaskCommentCount) getSession()
				.get(TaskCommentCount.class, id);

		logger.info("Returning result from database. Exiting update method");
		return taskCommentCount;
	}

	@Override
	public void delete(TaskCommentCountId id) {
		logger.info("Entered TaskCommentCount delete method");
		logger.debug("Deleting like count for task id = "
				+ id.getTask().getId());

		TaskCommentCount taskCommentCount = (TaskCommentCount) getSession()
				.get(TaskCommentCount.class, id);

		logger.info("Deleted TaskCommentCount. Exiting delete method");
		getSession().delete(taskCommentCount);

	}

}
