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
import com.ohmuk.folitics.hibernate.entity.like.TaskLike;

/**
 * Repository implementation for entity: {@link TaskLike}
 * 
 * @author Harish
 *
 */
@Component
@Repository
public class TaskLikeRepositoryImplementation implements ITaskLikeRepository {

	private static Logger logger = LoggerFactory
			.getLogger(TaskLikeRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private volatile IUserMonetizationBusinessDeligate userMonetizationBussinessDeligate;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public TaskLike save(TaskLike taskLike) {

		logger.info("Entered TaskLike save method");
		logger.debug("Saving like for task id = "
				+ taskLike.getId().getComponentId() + " and user id = "
				+ taskLike.getId().getUserId());

		LikeId id = (LikeId) getSession().save(taskLike);
		taskLike = (TaskLike) getSession().get(TaskLike.class, id);

		logger.info("TaskLike saved. Exiting save method");
		return taskLike;
	}

	@Override
	public TaskLike update(TaskLike taskLike) {

		logger.info("Entered TaskLike update method");
		logger.debug("Updating like for sentiment id = "
				+ taskLike.getId().getComponentId() + " and user id = "
				+ taskLike.getId().getUserId());

		taskLike = (TaskLike) getSession().merge(taskLike);
		getSession().update(taskLike);

		taskLike = (TaskLike) getSession()
				.get(TaskLike.class, taskLike.getId());

		logger.info("Updated TaskLike. Exiting update method");
		return taskLike;

	}

	@Override
	public TaskLike find(LikeId likeId) {

		logger.info("Entered TaskLike find method");
		logger.debug("Getting like for task id = " + likeId.getComponentId()
				+ " and user id = " + likeId.getUserId());

		TaskLike taskLike = (TaskLike) getSession().get(TaskLike.class, likeId);

		logger.info("Returning TaskLike. Exiting find method");
		return taskLike;
	}

	@Override
	public List<TaskLike> findAll() {

		logger.info("Entered TaskLike findAll method");
		logger.debug("Getting all likes");

		Criteria selectAllCriteria = getSession()
				.createCriteria(TaskLike.class);
		@SuppressWarnings("unchecked")
		List<TaskLike> taskLikes = selectAllCriteria.list();

		logger.info("Returning all TaskLike. Exiting findAll method");
		return taskLikes;
	}

	@Override
	public void delete(LikeId likeId) {

		logger.info("Entered TaskLike delete method");
		logger.debug("Deleting like for sentiment id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());

		TaskLike taskLike = (TaskLike) getSession().get(TaskLike.class, likeId);

		logger.info("Deleted TaskLike. Exiting delete method");
		getSession().delete(taskLike);

	}

	@Override
	public TaskLike getByComponentIdAndUserId(Long componentId, Long userId) {

		logger.info("Entered TaskLike getByComponentIdAndUserId method");
		logger.debug("Getting like for task id = " + componentId
				+ " and user id = " + userId);

		Criteria selectCriteria = getSession().createCriteria(TaskLike.class);
		selectCriteria.add(Restrictions.eq("id.componentId", componentId)).add(
				Restrictions.eq("id.userId", userId));
		TaskLike taskLike = (TaskLike) selectCriteria.uniqueResult();

		logger.info("Returning TaskLike. Exiting getByComponentIdAndUserId method");
		return taskLike;
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
