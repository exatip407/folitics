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

import com.ohmuk.folitics.hibernate.entity.comment.TaskParticipantsCommentCount;
import com.ohmuk.folitics.hibernate.entity.comment.TaskParticipantsCommentCountId;

@Component
@Repository
public class TaskParticipantsCommentCountRepositoryImplementation implements
		ITaskParticipantsCommentCountRepository {

	private static Logger logger = LoggerFactory
			.getLogger(TaskCommentCountRepositoryImplementation.class);
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public TaskParticipantsCommentCount save(
			TaskParticipantsCommentCount taskParticipantsCommentCount) {
		logger.info("Entered TaskParticipantsCommentCount save method");
		logger.debug("Saving comment count for task id = "
				+ taskParticipantsCommentCount.getId().getTask().getId());

		TaskParticipantsCommentCountId id = (TaskParticipantsCommentCountId) getSession()
				.save(taskParticipantsCommentCount);
		taskParticipantsCommentCount = (TaskParticipantsCommentCount) getSession()
				.get(TaskParticipantsCommentCount.class, id);

		logger.info("TaskParticipantsCommentCount saved. Exiting save method");
		return taskParticipantsCommentCount;
	}

	@Override
	public TaskParticipantsCommentCount update(
			TaskParticipantsCommentCount taskParticipantsCommentCount) {

		logger.info("Entered TaskParticipantsCommentCount update method");
		logger.debug("Updating comment count for task id = "
				+ taskParticipantsCommentCount.getId().getTask().getId());

		taskParticipantsCommentCount = (TaskParticipantsCommentCount) getSession()
				.merge(taskParticipantsCommentCount);
		getSession().update(taskParticipantsCommentCount);

		taskParticipantsCommentCount = (TaskParticipantsCommentCount) getSession()
				.get(TaskParticipantsCommentCount.class,
						taskParticipantsCommentCount.getId());

		logger.info("TaskParticipantsCommentCount updated. Exiting update method");
		return taskParticipantsCommentCount;
	}

	@Override
	public List<TaskParticipantsCommentCount> findAll() {
		logger.info("Entered TaskParticipantsCommentCount findAll method");

		Criteria selectAllCriteria = getSession().createCriteria(
				TaskParticipantsCommentCount.class);
		@SuppressWarnings("unchecked")
		List<TaskParticipantsCommentCount> taskCommentCounts = selectAllCriteria
				.list();

		logger.info("Returning all results from database. Exiting findAll method");
		return taskCommentCounts;
	}

	@Override
	public TaskParticipantsCommentCount findByComponentId(Long id) {
		logger.info("Entered TaskParticipantsCommentCount findByComponentId method");
		logger.debug("Finding comment count for task id = " + id);

		Criteria selectCriteria = getSession().createCriteria(
				TaskParticipantsCommentCount.class);
		selectCriteria.add(Restrictions.eq("id.task.id", id));
		TaskParticipantsCommentCount taskParticipantsCommentCount = (TaskParticipantsCommentCount) selectCriteria
				.uniqueResult();

		logger.info("Returning TaskParticipantsCommentCount. Exiting findByComponentId method");
		return taskParticipantsCommentCount;
	}

	@Override
	public TaskParticipantsCommentCount find(TaskParticipantsCommentCountId id) {
		logger.info("Entered TaskParticipantsCommentCount find method");
		logger.debug("Getting comment count for task id = "
				+ id.getTask().getId());

		TaskParticipantsCommentCount taskParticipantsCommentCount = (TaskParticipantsCommentCount) getSession()
				.get(TaskParticipantsCommentCount.class, id);

		logger.info("Returning result from database. Exiting update method");
		return taskParticipantsCommentCount;
	}

	@Override
	public void delete(TaskParticipantsCommentCountId id) {
		logger.info("Entered TaskParticipantsCommentCount delete method");
		logger.debug("Deleting comment count for task id = "
				+ id.getTask().getId());

		TaskParticipantsCommentCount taskParticipantsCommentCount = (TaskParticipantsCommentCount) getSession()
				.get(TaskParticipantsCommentCount.class, id);

		logger.info("Deleted TaskParticipantsCommentCount. Exiting delete method");
		getSession().delete(taskParticipantsCommentCount);

	}

}
