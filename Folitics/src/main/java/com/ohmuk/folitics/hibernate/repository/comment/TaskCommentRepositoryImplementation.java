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

/**
 * Repository implementation for entity: {@link TaskComment}
 * 
 * @author Harish
 *
 */

@Component
@Repository
public class TaskCommentRepositoryImplementation implements
		ITaskCommentRepository {

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
	public TaskComment save(TaskComment taskComment) {

		logger.info("Entered TaskComment save method");
		logger.debug("Saving Comment for task id = "
				+ taskComment.getComponentId() + " and user id = "
				+ taskComment.getUserId());

		Long id = (Long) getSession().save(taskComment);
		taskComment = (TaskComment) getSession().get(TaskComment.class, id);

		logger.info("TaskComment saved. Exiting save method");
		return taskComment;
	}

	@Override
	public TaskComment update(TaskComment taskComment) {

		logger.info("Entered TaskComment update method");
		logger.debug("Updating like for sentiment id = "
				+ taskComment.getComponentId() + " and user id = "
				+ taskComment.getUserId());

		taskComment = (TaskComment) getSession().merge(taskComment);
		getSession().update(taskComment);

		taskComment = (TaskComment) getSession().get(TaskComment.class,
				taskComment.getId());

		logger.info("Updated TaskComment. Exiting update method");
		return taskComment;
	}

	@Override
	public List<TaskComment> findAll() {

		logger.info("Entered TaskComment findAll method");
		logger.debug("Getting all likes");

		Criteria selectAllCriteria = getSession().createCriteria(
				TaskComment.class);
		@SuppressWarnings("unchecked")
		List<TaskComment> taskComments = selectAllCriteria.list();

		logger.info("Returning all TaskComment. Exiting findAll method");
		return taskComments;
	}

	@Override
	public void delete(Long id) {

		logger.info("Entered TaskComment delete method");

		TaskComment taskLike = (TaskComment) getSession().get(
				TaskComment.class, id);

		logger.info("Deleted TaskComment. Exiting delete method");
		getSession().delete(taskLike);

	}

	@Override
	public List<TaskComment> getByComponentIdAndUserId(Long componentId,
			Long userId) throws MessageException {
		logger.info("Entered TaskComment getByComponentIdAndUserId method");
		Query query = getSession()
				.createSQLQuery(
						"SELECT * FROM taskcomment t WHERE t.componentId = :componentId AND t.userId = :userId")
				.addEntity(TaskComment.class)
				.setParameter("componentId", componentId)
				.setParameter("userId", userId);

		@SuppressWarnings("unchecked")
		List<TaskComment> taskComments = (List<TaskComment>) query.list();

		logger.info("Exiting TaskComment getByComponentIdAndUserId method");

		return taskComments;
	}

	@Override
	public List<TaskComment> getAllCommentsForComponent(Long componentId)
			throws MessageException {

		logger.info("Entered TaskComment getAllCommentsForComponent method");

		Criteria criteria = getSession().createCriteria(TaskComment.class);
		criteria.add(Restrictions.eq("componentId", componentId));

		@SuppressWarnings("unchecked")
		List<TaskComment> taskComments = criteria.list();

		if (taskComments.size() == 0) {
			logger.error("comments does not exist for these componentId :"
					+ componentId);
			throw new MessageException(
					"comments does not exist for these componentId :"
							+ componentId);
		}
		logger.info("Exiting TaskComment getAllCommentsForComponent method");
		return taskComments;

	}

	@Override
	public List<TaskComment> getAllCommentsForUserId(Long userId)
			throws MessageException {

		logger.info("Entered TaskComment getAllCommentsForUserId method");

		Criteria criteria = getSession().createCriteria(TaskComment.class);
		criteria.add(Restrictions.eq("userId", userId));

		@SuppressWarnings("unchecked")
		List<TaskComment> taskComments = criteria.list();

		if (taskComments.size() == 0) {
			logger.error("comments does not exist for these userId :" + userId);
			throw new MessageException(
					"comments does not exist for these UserId :" + userId);
		}
		logger.info("Exiting TaskComment getAllCommentsForUserId method");
		return taskComments;
	}

	@Override
	public TaskComment find(Long id) {
		logger.info("Entered TaskComment find method");

		TaskComment taskComment = (TaskComment) getSession().get(
				TaskComment.class, id);

		logger.info("Returning TaskComment. Exiting find method");
		return taskComment;
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
