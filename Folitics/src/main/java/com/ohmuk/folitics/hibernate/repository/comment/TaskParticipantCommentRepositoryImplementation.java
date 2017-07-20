package com.ohmuk.folitics.hibernate.repository.comment;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ohmuk.folitics.beans.CommentBean;
import com.ohmuk.folitics.businessDelegate.interfaces.IUserMonetizationBusinessDeligate;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Module;
import com.ohmuk.folitics.hibernate.entity.UserMonetization;
import com.ohmuk.folitics.hibernate.entity.comment.TaskComment;
import com.ohmuk.folitics.hibernate.entity.comment.TaskParticipantsComment;

@Component
@Repository
public class TaskParticipantCommentRepositoryImplementation implements
		ITaskParticipantsCommentRepository {

	private static Logger logger = LoggerFactory
			.getLogger(TaskCommentRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private volatile IUserMonetizationBusinessDeligate userMonetizationBussinessDeligate;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public TaskParticipantsComment save(
			TaskParticipantsComment taskParticipantsComment) {
		logger.info("Entered TaskParticipantsComment save method");
		logger.debug("Saving Comment for task id = "
				+ taskParticipantsComment.getComponentId() + " and user id = "
				+ taskParticipantsComment.getUserId());

		Long id = (Long) getSession().save(taskParticipantsComment);
		taskParticipantsComment = (TaskParticipantsComment) getSession().get(
				TaskParticipantsComment.class, id);

		logger.info("TaskParticipantsComment saved. Exiting save method");
		return taskParticipantsComment;
	}

	@Override
	public TaskParticipantsComment update(
			TaskParticipantsComment taskParticipantsComment) {
		logger.info("Entered TaskParticipantsComment update method");
		logger.debug("Updating like for sentiment id = "
				+ taskParticipantsComment.getComponentId() + " and user id = "
				+ taskParticipantsComment.getUserId());

		taskParticipantsComment = (TaskParticipantsComment) getSession().merge(
				taskParticipantsComment);
		getSession().update(taskParticipantsComment);

		taskParticipantsComment = (TaskParticipantsComment) getSession().get(
				TaskParticipantsComment.class, taskParticipantsComment.getId());

		logger.info("Updated TaskParticipantsComment. Exiting update method");
		return taskParticipantsComment;
	}

	@Override
	public List<TaskParticipantsComment> findAll() {
		logger.info("Entered TaskParticipantsComment findAll method");
		logger.debug("Getting all likes");

		Criteria selectAllCriteria = getSession().createCriteria(
				TaskParticipantsComment.class);
		@SuppressWarnings("unchecked")
		List<TaskParticipantsComment> taskComments = selectAllCriteria.list();

		logger.info("Returning all TaskParticipantsComment. Exiting findAll method");
		return taskComments;
	}

	@Override
	public void delete(Long id) {
		logger.info("Entered TaskParticipantsComment delete method");

		TaskParticipantsComment taskLike = (TaskParticipantsComment) getSession()
				.get(TaskParticipantsComment.class, id);

		logger.info("Deleted TaskParticipantsComment. Exiting delete method");
		getSession().delete(taskLike);

	}

	@Override
	public List<TaskParticipantsComment> getByComponentIdAndUserId(
			Long componentId, Long userId) throws MessageException {
		logger.info("Entered TaskParticipantsComment getByComponentIdAndUserId method");
		Query query = getSession()
				.createSQLQuery(
						"SELECT * FROM taskparticipantscomment t WHERE t.componentId = :componentId AND t.userId = :userId")
				.addEntity(TaskComment.class)
				.setParameter("componentId", componentId)
				.setParameter("userId", userId);

		@SuppressWarnings("unchecked")
		List<TaskParticipantsComment> taskComments = (List<TaskParticipantsComment>) query
				.list();

		logger.info("Exiting TaskParticipantsComment getByComponentIdAndUserId method");

		return taskComments;
	}

	@Override
	public List<TaskParticipantsComment> getAllCommentsForComponent(
			Long componentId) throws MessageException {
		logger.info("Entered TaskParticipantsComment getAllCommentsForComponent method");

		Criteria criteria = getSession().createCriteria(
				TaskParticipantsComment.class);
		criteria.add(Restrictions.eq("componentId", componentId));

		@SuppressWarnings("unchecked")
		List<TaskParticipantsComment> taskComments = criteria.list();

		if (taskComments.size() == 0) {
			logger.error("comments does not exist for these componentId :"
					+ componentId);
			throw new MessageException(
					"comments does not exist for these componentId :"
							+ componentId);
		}
		logger.info("Exiting TaskParticipantsComment getAllCommentsForComponent method");
		return taskComments;

	}

	@Override
	public List<TaskParticipantsComment> getAllCommentsForUserId(Long userId)
			throws MessageException {
		logger.info("Entered TaskParticipantsComment getAllCommentsForUserId method");

		Criteria criteria = getSession().createCriteria(
				TaskParticipantsComment.class);
		criteria.add(Restrictions.eq("userId", userId));

		@SuppressWarnings("unchecked")
		List<TaskParticipantsComment> taskComments = criteria.list();

		if (taskComments.size() == 0) {
			logger.error("comments does not exist for these userId :" + userId);
			throw new MessageException(
					"comments does not exist for these UserId :" + userId);
		}
		logger.info("Exiting TaskParticipantsComment getAllCommentsForUserId method");
		return taskComments;
	}

	@Override
	public TaskParticipantsComment find(Long id) {
		logger.info("Entered TaskParticipantsComment find method");

		TaskParticipantsComment taskParticipantsComment = (TaskParticipantsComment) getSession()
				.get(TaskParticipantsComment.class, id);

		logger.info("Returning TaskParticipantsComment. Exiting find method");
		return taskParticipantsComment;
	}

	@Override
	public void addMonetizationPoints(CommentBean commentBean, String action)
			throws Exception {

		Criteria criteria = getSession().createCriteria(Module.class);
		criteria.add(Restrictions.eq("componentType",
				commentBean.getComponentType()));

		Module module = (Module) criteria.uniqueResult();
		if (module == null) {

			logger.error("No module found in module table for component: "
					+ commentBean.getComponentType());
			throw new MessageException(
					"No module found in module table for component: "
							+ commentBean.getComponentType());

		}
		UserMonetization userMonetization = new UserMonetization();

		userMonetization.setAction(action);
		userMonetization.setComponentType(commentBean.getComponentType());

		userMonetization.setModule(module.getModule());
		userMonetization.setUserId(commentBean.getUserId());
		userMonetization.setActionComponentId(commentBean.getComponentId());

		userMonetizationBussinessDeligate.addAction(userMonetization);
	}

}
