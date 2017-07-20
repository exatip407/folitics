package com.ohmuk.folitics.hibernate.repository.follow;

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

import com.ohmuk.folitics.hibernate.entity.follow.SentimentFollowCountId;
import com.ohmuk.folitics.hibernate.entity.follow.TaskFollowCount;
import com.ohmuk.folitics.hibernate.entity.follow.TaskFollowCountId;

/**
 * Repository implementation for entity: {@link TaskFollowCount}
 * 
 * @author Harish
 *
 */
@Component
@Repository
public class TaskFollowCountRepositoryImplementation implements
		ITaskFollowCountRepository {

	private Logger logger = LoggerFactory
			.getLogger(TaskFollowCountRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public TaskFollowCount save(TaskFollowCount taskFollowCount) {

		logger.info("Entered TaskFollowCount save method");
		logger.debug("Saving follow count for task id = "
				+ taskFollowCount.getId().getTask().getId());

		SentimentFollowCountId id = (SentimentFollowCountId) getSession().save(
				taskFollowCount);

		taskFollowCount = (TaskFollowCount) getSession().get(
				TaskFollowCount.class, id);

		logger.info("TaskFollowCount saved. Exiting save method");

		return taskFollowCount;
	}

	@Override
	public TaskFollowCount update(TaskFollowCount taskFollowCount) {

		logger.info("Entered TaskFollowCount update method");
		logger.debug("Updating follow count for task id = "
				+ taskFollowCount.getId().getTask().getId());

		taskFollowCount = (TaskFollowCount) getSession().merge(taskFollowCount);
		getSession().update(taskFollowCount);

		taskFollowCount = (TaskFollowCount) getSession().get(
				TaskFollowCount.class, taskFollowCount.getId());

		logger.info("TaskFollowCount updated. Exiting update method");
		return taskFollowCount;
	}

	@Override
	public List<TaskFollowCount> findAll() {

		logger.info("Entered TaskFollowCount findAll method");

		Criteria selectAllCriteria = getSession().createCriteria(
				TaskFollowCount.class);
		@SuppressWarnings("unchecked")
		List<TaskFollowCount> taskFollowCounts = selectAllCriteria.list();

		logger.info("Returning all results from database. Exiting findAll method");
		return taskFollowCounts;
	}

	@Override
	public TaskFollowCount findByComponentId(Long id) {
		logger.info("Entered TaskFollowCount findByComponentId method");
		logger.debug("Finding follow count for task id = " + id);

		Criteria selectCriteria = getSession().createCriteria(
				TaskFollowCount.class);
		selectCriteria.add(Restrictions.eq("id.task.id", id));
		TaskFollowCount taskFollowCount = (TaskFollowCount) selectCriteria
				.uniqueResult();

		logger.info("Returning TaskFollowCount. Exiting findByComponentId method");
		return taskFollowCount;
	}

	@Override
	public TaskFollowCount find(TaskFollowCountId id) {

		logger.info("Entered TaskFollowCount find method");
		logger.debug("Getting follow count for task id = "
				+ id.getTask().getId());

		TaskFollowCount taskFollowCount = (TaskFollowCount) getSession().get(
				TaskFollowCount.class, id);

		logger.info("Returning result from database. Exiting update method");
		return taskFollowCount;
	}

	@Override
	public void delete(TaskFollowCountId id) {
		logger.info("Entered TaskFollowCount delete method");
		logger.debug("Deleting follow count for task id = "
				+ id.getTask().getId());

		TaskFollowCount taskFollowCount = (TaskFollowCount) getSession().get(
				TaskFollowCount.class, id);

		logger.info("Deleted TaskFollowCount. Exiting delete method");
		getSession().delete(taskFollowCount);

	}

}
