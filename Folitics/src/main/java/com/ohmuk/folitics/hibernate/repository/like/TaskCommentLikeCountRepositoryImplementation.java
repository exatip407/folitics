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

import com.ohmuk.folitics.hibernate.entity.like.TaskCommentLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.TaskCommentLikeCountId;

/**
 * Repository implementation for entity: {@link TaskCommentLikeCount}
 * 
 * @author Harish
 *
 */

@Component
@Repository
public class TaskCommentLikeCountRepositoryImplementation implements
		ITaskCommentLikeCountRepository {

	private static Logger logger = LoggerFactory
			.getLogger(TaskCommentLikeCountRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public TaskCommentLikeCount save(TaskCommentLikeCount taskCommentLikeCount) {
		logger.info("Entered TaskCommentLikeCount save method");
		logger.debug("Saving like count for taskComment id = "
				+ taskCommentLikeCount.getId().getTaskComment().getId());

		TaskCommentLikeCountId id = (TaskCommentLikeCountId) getSession().save(
				taskCommentLikeCount);
		taskCommentLikeCount = (TaskCommentLikeCount) getSession().get(
				TaskCommentLikeCount.class, id);

		logger.info("TaskCommentLikeCount saved. Exiting save method");
		return taskCommentLikeCount;
	}

	@Override
	public TaskCommentLikeCount update(TaskCommentLikeCount taskCommentLikeCount) {
		logger.info("Entered TaskCommentLikeCount update method");
		logger.debug("Updating like count for taskComment id = "
				+ taskCommentLikeCount.getId().getTaskComment().getId());

		taskCommentLikeCount = (TaskCommentLikeCount) getSession().merge(
				taskCommentLikeCount);
		getSession().update(taskCommentLikeCount);

		taskCommentLikeCount = (TaskCommentLikeCount) getSession().get(
				TaskCommentLikeCount.class, taskCommentLikeCount.getId());

		logger.info("TaskCommentLikeCount updated. Exiting update method");
		return taskCommentLikeCount;
	}

	@Override
	public List<TaskCommentLikeCount> findAll() {
		logger.info("Entered TaskCommentLikeCount findAll method");

		Criteria selectAllCriteria = getSession().createCriteria(
				TaskCommentLikeCount.class);
		@SuppressWarnings("unchecked")
		List<TaskCommentLikeCount> taskLikeCounts = selectAllCriteria.list();

		logger.info("Returning all results from database. Exiting findAll method");
		return taskLikeCounts;
	}

	@Override
	public TaskCommentLikeCount findByComponentId(Long taskCommentId) {
		logger.info("Entered TaskCommentLikeCount findByComponentId method");
		logger.debug("Finding like count for taskComment id = " + taskCommentId);

		Criteria selectCriteria = getSession().createCriteria(
				TaskCommentLikeCount.class);
		selectCriteria.add(Restrictions.eq("id.taskComment.id", taskCommentId));
		TaskCommentLikeCount taskCommentLikeCount = (TaskCommentLikeCount) selectCriteria
				.uniqueResult();

		logger.info("Returning TaskCommentLikeCount. Exiting findByComponentId method");
		return taskCommentLikeCount;
	}

	@Override
	public TaskCommentLikeCount find(TaskCommentLikeCountId id) {
		logger.info("Entered TaskCommentLikeCount find method");
		logger.debug("Getting like count for taskComment id = "
				+ id.getTaskComment().getId());

		TaskCommentLikeCount taskCommentLikeCount = (TaskCommentLikeCount) getSession()
				.get(TaskCommentLikeCount.class, id);

		logger.info("Returning result from database. Exiting update method");
		return taskCommentLikeCount;
	}

	@Override
	public void delete(TaskCommentLikeCountId id) {
		logger.info("Entered TaskCommentLikeCount delete method");
		logger.debug("Deleting like count for taskComment id = "
				+ id.getTaskComment().getId());

		TaskCommentLikeCount taskCommentLikeCount = (TaskCommentLikeCount) getSession()
				.get(TaskCommentLikeCount.class, id);

		logger.info("Deleted TaskCommentLikeCount. Exiting delete method");
		getSession().delete(taskCommentLikeCount);

	}

}
