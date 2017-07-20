package com.ohmuk.folitics.hibernate.repository.follow;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ohmuk.folitics.beans.FollowDataBean;
import com.ohmuk.folitics.businessDelegate.interfaces.IUserMonetizationBusinessDeligate;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Module;
import com.ohmuk.folitics.hibernate.entity.UserMonetization;
import com.ohmuk.folitics.hibernate.entity.follow.TaskFollow;
import com.ohmuk.folitics.hibernate.entity.like.LikeId;

/**
 * Repository implementation for entity: {@link TaskFollow}
 * 
 * @author Harish
 *
 */
@Component
@Repository
public class TaskFollowRepositoryImplementation implements
		ITaskFollowRepository {

	private Logger logger = LoggerFactory
			.getLogger(TaskFollowRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private IUserMonetizationBusinessDeligate userMonetizationBussinessDeligate;

	private Session getSession() {

		return sessionFactory.getCurrentSession();

	}

	@Override
	public TaskFollow save(TaskFollow taskFollow) throws MessageException,
			Exception {

		logger.info("Entered TaskFollow save method");
		logger.debug("Saving follow for task id = "
				+ taskFollow.getFollowId().getComponentId() + " and user id = "
				+ taskFollow.getFollowId().getUserId());

		LikeId id = (LikeId) getSession().save(taskFollow);
		taskFollow = (TaskFollow) getSession().get(TaskFollow.class, id);

		logger.info("TaskFollow saved. Exiting save method");
		return taskFollow;
	}

	@Override
	public void delete(TaskFollow taskFollow) throws MessageException,
			Exception {
		logger.info("Entered TaskFollow delete method");

		getSession().delete(taskFollow);
		logger.info("Deleted TaskFollow. Exiting delete method");

	}

	@Override
	public TaskFollow findByComponentIdAndUserId(Long componentId, Long userId)
			throws MessageException, Exception {

		logger.info("Entered TaskFollow getByComponentIdAndUserId method");
		logger.debug("Getting follow for task id = " + componentId
				+ " and user id = " + userId);

		Criteria selectCriteria = getSession().createCriteria(TaskFollow.class);
		selectCriteria.add(Restrictions.eq("id.componentId", componentId)).add(
				Restrictions.eq("id.userId", userId));
		TaskFollow taskFollow = (TaskFollow) selectCriteria.uniqueResult();

		logger.info("Returning TaskFollow. Exiting getByComponentIdAndUserId method");
		return taskFollow;
	}

	@Override
	public TaskFollow update(TaskFollow taskFollow) throws MessageException,
			Exception {

		logger.info("Entered TaskFollow update method");
		logger.debug("Updating follow for task id = "
				+ taskFollow.getFollowId().getComponentId() + " and user id = "
				+ taskFollow.getFollowId().getUserId());

		taskFollow = (TaskFollow) getSession().merge(taskFollow);
		getSession().update(taskFollow);

		taskFollow = (TaskFollow) getSession().get(TaskFollow.class,
				taskFollow.getFollowId());

		logger.info("Updated TaskFollow. Exiting update method");
		return taskFollow;
	}

	@Override
	public List<TaskFollow> findByComponentIdAndIsFollowing(Long componentId,
			boolean isFollowing) {
		logger.info("Entered TaskFollow getByComponentIdAndUserId method");
		logger.debug("Getting follow for task id = " + componentId);

		Criteria criteria = getSession().createCriteria(TaskFollow.class);
		criteria.add(Restrictions.eqOrIsNull("followId.componentId",
				componentId));
		criteria.add(Restrictions.eqOrIsNull("isFollowing", isFollowing));

		@SuppressWarnings("unchecked")
		List<TaskFollow> followList = criteria.list();
		logger.info("Returning TaskFollow. Exiting getByComponentIdAndUserId method");
		return followList;
	}

	@Override
	public boolean findByComponentIdUserIdAndIsFollowing(
			FollowDataBean followDataBean) {
		logger.info("Entered TaskFollow findByComponentIdUserIdAndIsFollowing method");
		logger.info("Searching User...");

		Criteria criteria = getSession().createCriteria(TaskFollow.class);

		SimpleExpression expression1 = Restrictions.eq("followId.componentId",
				followDataBean.getComponentId());

		SimpleExpression expression2 = Restrictions.eq("followId.userId",
				followDataBean.getUserId());

		TaskFollow taskFollow = (TaskFollow) criteria.add(
				Restrictions.and(expression1, expression2)).uniqueResult();

		if (taskFollow == null) {

			logger.info("No user found");

		} else if (taskFollow.isFollowing()) {
			return true;
		}
		return false;
	}

	@Override
	public void addMonetizationPoints(FollowDataBean followDataBean,
			String action) throws Exception {

		Criteria criteria = getSession().createCriteria(Module.class);
		criteria.add(Restrictions.eq("componentType",
				followDataBean.getComponentType()));

		Module module = (Module) criteria.uniqueResult();
		if (module == null) {

			logger.error("No module found in module table for component: "
					+ followDataBean.getComponentType());
			throw new MessageException(
					"No module found in module table for component: "
							+ followDataBean.getComponentType());

		}
		UserMonetization userMonetization = new UserMonetization();

		userMonetization.setAction(action);
		userMonetization.setComponentType(followDataBean.getComponentType());

		userMonetization.setModule(module.getModule());
		userMonetization.setUserId(followDataBean.getUserId());
		userMonetization.setActionComponentId(followDataBean.getComponentId());

		userMonetizationBussinessDeligate.addAction(userMonetization);

	}

}
