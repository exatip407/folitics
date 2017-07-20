package com.ohmuk.folitics.hibernate.repository.like;

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

import com.ohmuk.folitics.hibernate.entity.like.TaskLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.TaskLikeCountId;

/**
 * Repository implementation for entity: {@link TaskLikeCount}
 * 
 * @author Harish
 *
 */

@Component
@Repository
public class TaskLikeCountRepositoryImplementation implements
		ITaskLikeCountRepository {

	private static Logger logger = LoggerFactory
			.getLogger(TaskLikeCountRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public TaskLikeCount save(TaskLikeCount taskLikeCount) {

		logger.info("Entered TaskLikeCount save method");
		logger.debug("Saving like count for task id = "
				+ taskLikeCount.getId().getTask().getId());

		TaskLikeCountId id = (TaskLikeCountId) getSession().save(taskLikeCount);
		taskLikeCount = (TaskLikeCount) getSession().get(TaskLikeCount.class,
				id);

		logger.info("TaskLikeCount saved. Exiting save method");
		return taskLikeCount;
	}

	@Override
	public TaskLikeCount update(TaskLikeCount taskLikeCount) {

		logger.info("Entered TaskLikeCount update method");
		logger.debug("Updating like count for task id = "
				+ taskLikeCount.getId().getTask().getId());

		taskLikeCount = (TaskLikeCount) getSession().merge(taskLikeCount);
		getSession().update(taskLikeCount);

		taskLikeCount = (TaskLikeCount) getSession().get(TaskLikeCount.class,
				taskLikeCount.getId());

		logger.info("TaskLikeCount updated. Exiting update method");
		return taskLikeCount;
	}

	@Override
	public List<TaskLikeCount> findAll() {

		logger.info("Entered TaskLikeCount findAll method");

		Criteria selectAllCriteria = getSession().createCriteria(
				TaskLikeCount.class);
		@SuppressWarnings("unchecked")
		List<TaskLikeCount> taskLikeCounts = selectAllCriteria.list();

		logger.info("Returning all results from database. Exiting findAll method");
		return taskLikeCounts;
	}

	@Override
	public TaskLikeCount findByComponentId(Long taskId) {

		logger.info("Entered TaskLikeCount findByComponentId method");
		logger.debug("Finding like count for task id = " + taskId);

		Criteria selectCriteria = getSession().createCriteria(
				TaskLikeCount.class);
		selectCriteria.add(Restrictions.eq("id.task.id", taskId));
		TaskLikeCount taskLikeCount = (TaskLikeCount) selectCriteria
				.uniqueResult();

		logger.info("Returning TaskLikeCount. Exiting findByComponentId method");
		return taskLikeCount;
	}

	@Override
	public TaskLikeCount find(TaskLikeCountId id) {

		logger.info("Entered TaskLikeCount find method");
		logger.debug("Getting like count for task id = " + id.getTask().getId());

		TaskLikeCount taskLikeCount = (TaskLikeCount) getSession().get(
				TaskLikeCount.class, id);

		logger.info("Returning result from database. Exiting update method");
		return taskLikeCount;
	}

	@Override
	public void delete(TaskLikeCountId id) {

		logger.info("Entered TaskLikeCount delete method");
		logger.debug("Deleting like count for task id = "
				+ id.getTask().getId());

		TaskLikeCount taskLikeCount = (TaskLikeCount) getSession().get(
				TaskLikeCount.class, id);

		logger.info("Deleted TaskLikeCount. Exiting delete method");
		getSession().delete(taskLikeCount);

	}

}
