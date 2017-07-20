package com.ohmuk.folitics.hibernate.repository.share;

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

import com.ohmuk.folitics.hibernate.entity.share.TaskShareCount;
import com.ohmuk.folitics.hibernate.entity.share.TaskShareCountId;

/**
 * Repository implementation for entity: {@link TaskShareCount}
 * 
 * @author Harish
 *
 */
@Component
@Repository
public class TaskShareCountRepositoryImplementation implements
		ITaskShareCountRepository {

	private static Logger logger = LoggerFactory
			.getLogger(TaskShareCountRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public TaskShareCount save(TaskShareCount taskShareCount) {

		logger.debug("Entered TaskShareCount save method");
		logger.debug("Saving share count for Task id = "
				+ taskShareCount.getId().getTask().getId());

		TaskShareCountId id = (TaskShareCountId) getSession().save(
				taskShareCount);
		taskShareCount = (TaskShareCount) getSession().get(
				TaskShareCount.class, id);

		logger.debug("TaskShareCount saved. Exiting save method");
		return taskShareCount;
	}

	@Override
	public TaskShareCount update(TaskShareCount taskShareCount) {

		logger.debug("Entered TaskShareCount update method");
		logger.debug("Updating share count for task id = "
				+ taskShareCount.getId().getTask().getId());

		taskShareCount = (TaskShareCount) getSession().merge(taskShareCount);
		getSession().update(taskShareCount);

		taskShareCount = (TaskShareCount) getSession().get(
				TaskShareCount.class, taskShareCount.getId());

		logger.debug("Updated TaskShareCount. Exiting update method");
		return taskShareCount;
	}

	@Override
	public List<TaskShareCount> findAll() {
		logger.debug("Entered TaskShareCount findAll method");
		logger.debug("Getting all share count");

		@SuppressWarnings("unchecked")
		List<TaskShareCount> taskShareCounts = getSession().createCriteria(
				TaskShareCount.class).list();

		logger.debug("Returning all TaskShareCount. Exiting findAll method");
		return taskShareCounts;
	}

	@Override
	public TaskShareCount findByComponentId(Long taskId) {

		logger.debug("Entered TaskShareCount findByComponentId method");
		logger.debug("Getting share count for task id = " + taskId);

		Criteria selectCriteria = getSession().createCriteria(
				TaskShareCount.class);
		selectCriteria.add(Restrictions.eq("id.task.id", taskId));
		TaskShareCount tasksShareCount = (TaskShareCount) selectCriteria
				.uniqueResult();

		logger.debug("Returning TaskShareCount. Exiting findByComponentId method");
		return tasksShareCount;
	}

	@Override
	public TaskShareCount find(TaskShareCountId id) {

		logger.debug("Entered TaskShareCount find method");
		logger.debug("Getting share count for task id = "
				+ id.getTask().getId());

		TaskShareCount taskShareCount = (TaskShareCount) getSession().get(
				TaskShareCount.class, id);

		logger.debug("Returning TaskShareCount. Exiting find method");
		return taskShareCount;

	}

	@Override
	public void delete(TaskShareCountId id) {

		logger.debug("Entered TaskShareCount delete method");
		logger.debug("Deleting share count for task id = "
				+ id.getTask().getId());

		TaskShareCount taskShareCount = (TaskShareCount) getSession().get(
				TaskShareCount.class, id);

		logger.debug("Deleted TaskShareCount. Exiting delete method");
		getSession().delete(taskShareCount);

	}

}
