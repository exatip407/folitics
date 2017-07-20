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

import com.ohmuk.folitics.beans.LikeDataBean;
import com.ohmuk.folitics.businessDelegate.interfaces.IUserMonetizationBusinessDeligate;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Module;
import com.ohmuk.folitics.hibernate.entity.UserMonetization;
import com.ohmuk.folitics.hibernate.entity.like.LikeId;
import com.ohmuk.folitics.hibernate.entity.like.TaskCommentLike;

/**
 * Repository implementation for entity: {@link TaskCommentLike}
 * 
 * @author Harish
 *
 */
@Component
@Repository
public class TaskCommentLikeRepositoryImplementation implements
		ITaskCommentLikeRepository {

	private static Logger logger = LoggerFactory
			.getLogger(TaskCommentLikeRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private volatile IUserMonetizationBusinessDeligate userMonetizationBussinessDeligate;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public TaskCommentLike save(TaskCommentLike taskCommentLike) {
		logger.info("Entered TaskCommentLike save method");
		logger.debug("Saving like for TaskComment id = "
				+ taskCommentLike.getId().getComponentId() + " and user id = "
				+ taskCommentLike.getId().getUserId());

		LikeId id = (LikeId) getSession().save(taskCommentLike);
		taskCommentLike = (TaskCommentLike) getSession().get(
				TaskCommentLike.class, id);

		logger.info("TaskCommentLike saved. Exiting save method");
		return taskCommentLike;
	}

	@Override
	public TaskCommentLike update(TaskCommentLike taskCommentLike) {
		logger.info("Entered TaskCommentLike update method");
		logger.debug("Updating like for TaskComment id = "
				+ taskCommentLike.getId().getComponentId() + " and user id = "
				+ taskCommentLike.getId().getUserId());

		taskCommentLike = (TaskCommentLike) getSession().merge(taskCommentLike);
		getSession().update(taskCommentLike);

		taskCommentLike = (TaskCommentLike) getSession().get(
				TaskCommentLike.class, taskCommentLike.getId());

		logger.info("Updated TaskCommentLike. Exiting update method");
		return taskCommentLike;
	}

	@Override
	public TaskCommentLike find(LikeId likeId) {
		logger.info("Entered TaskCommentLike find method");
		logger.debug("Getting like for TaskComment id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());

		TaskCommentLike taskCommentLike = (TaskCommentLike) getSession().get(
				TaskCommentLike.class, likeId);

		logger.info("Returning TaskCommentLike. Exiting find method");
		return taskCommentLike;
	}

	@Override
	public List<TaskCommentLike> findAll() {
		logger.info("Entered TaskCommentLike findAll method");
		logger.debug("Getting all likes");

		Criteria selectAllCriteria = getSession().createCriteria(
				TaskCommentLike.class);

		@SuppressWarnings("unchecked")
		List<TaskCommentLike> taskCommentLikes = selectAllCriteria.list();

		logger.info("Returning all TaskCommentLike. Exiting findAll method");
		return taskCommentLikes;
	}

	@Override
	public void delete(LikeId likeId) {
		logger.info("Entered TaskCommentLike delete method");
		logger.debug("Deleting like for TaskComment id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());

		TaskCommentLike taskCommentLike = (TaskCommentLike) getSession().get(
				TaskCommentLike.class, likeId);

		logger.info("Deleted TaskCommentLike. Exiting delete method");
		getSession().delete(taskCommentLike);

	}

	@Override
	public TaskCommentLike getByComponentIdAndUserId(Long componentId,
			Long userId) {
		logger.info("Entered TaskCommentLike getByComponentIdAndUserId method");
		logger.debug("Getting like for TaskComment id = " + componentId
				+ " and user id = " + userId);

		Criteria selectCriteria = getSession().createCriteria(
				TaskCommentLike.class);
		selectCriteria.add(Restrictions.eq("id.componentId", componentId)).add(
				Restrictions.eq("id.userId", userId));
		TaskCommentLike taskCommentLike = (TaskCommentLike) selectCriteria
				.uniqueResult();

		logger.info("Returning TaskCommentLike. Exiting getByComponentIdAndUserId method");
		return taskCommentLike;
	}

	@Override
	public void addMonetizationPoints(LikeDataBean likeDataBean, String action)
			throws Exception {
		Criteria criteria = getSession().createCriteria(Module.class);
		criteria.add(Restrictions.eq("componentType",
				likeDataBean.getComponentType()));

		Module module = (Module) criteria.uniqueResult();
		if (module == null) {

			logger.error("No module found in module table for component: "
					+ likeDataBean.getComponentType());
			throw new MessageException(
					"No module found in module table for component: "
							+ likeDataBean.getComponentType());

		}
		UserMonetization userMonetization = new UserMonetization();

		userMonetization.setAction(action);
		userMonetization.setComponentType(likeDataBean.getComponentType());

		userMonetization.setModule(module.getModule());
		userMonetization.setUserId(likeDataBean.getUserId());
		userMonetization.setActionComponentId(likeDataBean.getComponentId());

		userMonetizationBussinessDeligate.addAction(userMonetization);

	}

}
