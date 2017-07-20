package com.ohmuk.folitics.hibernate.repository.share;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ohmuk.folitics.beans.AirShareDataBean;
import com.ohmuk.folitics.businessDelegate.interfaces.IUserMonetizationBusinessDeligate;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Module;
import com.ohmuk.folitics.hibernate.entity.UserMonetization;
import com.ohmuk.folitics.hibernate.entity.share.TaskShare;

@Component
@Repository
public class TaskShareRepositoryImplementation implements ITaskShareRepository {

	private static Logger logger = LoggerFactory
			.getLogger(TaskShareRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private volatile IUserMonetizationBusinessDeligate UserMonetizationBussinessDeligate;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public TaskShare save(TaskShare taskShare) {
		logger.info("Entered TaskShare save method");
		logger.debug("Saving share for sentiment id = "
				+ taskShare.getComponentId());

		Long id = (Long) getSession().save(taskShare);
		taskShare = (TaskShare) getSession().get(TaskShare.class, id);

		logger.info("TaskShare saved. Exiting save method");
		return taskShare;
	}

	@Override
	public TaskShare update(TaskShare taskShare) {

		logger.info("Entered TaskShare update method");
		logger.debug("Saving share for sentiment id = "
				+ taskShare.getComponentId());

		taskShare = (TaskShare) getSession().merge(taskShare);
		getSession().update(taskShare);

		taskShare = (TaskShare) getSession().get(TaskShare.class,
				taskShare.getId());

		logger.info("TaskShare updated. Exiting update method");
		return taskShare;
	}

	@Override
	public TaskShare find(Long id) {

		logger.info("Entered TaskShare find method");
		logger.debug("Getting share for task id = " + id);

		TaskShare taskShare = (TaskShare) getSession().get(TaskShare.class, id);

		logger.info("Returning TaskShare. Exiting find method");
		return taskShare;
	}

	@Override
	public List<TaskShare> findAll() {

		logger.info("Entered TaskShare findAll method");
		logger.debug("Getting all shares");

		@SuppressWarnings("unchecked")
		List<TaskShare> taskShares = getSession().createCriteria(
				TaskShare.class).list();

		logger.info("Returning all TaskShare. Exiting findAll method");
		return taskShares;
	}

	@Override
	public void delete(Long id) {

		logger.info("Entered TaskShare delete method");
		logger.debug("Deleting shares with id = " + id);

		TaskShare taskShare = (TaskShare) getSession().get(TaskShare.class, id);
		getSession().delete(taskShare);

		logger.info("Deleted TaskShare. Exiting delete method");
	}

	@Override
	public Set<TaskShare> getSharesByComponentId(Long componentId) {

		logger.info("Entered TaskShare getSharesByComponentId method");
		logger.debug("Getting shares with component id = " + componentId);

		Criteria selectCriteria = getSession().createCriteria(TaskShare.class);
		selectCriteria.add(Restrictions.eq("componentId", componentId));

		@SuppressWarnings("unchecked")
		Set<TaskShare> taskShares = (Set<TaskShare>) selectCriteria
				.uniqueResult();

		logger.info("Returing TaskShare. Exiting getSharesByComponentId method");
		return taskShares;
	}

	@Override
	public Set<TaskShare> getSharesByUserIdAndComponentId(Long userId,
			Long componentId) {

		logger.info("Entered TaskShare getSharesByUserIdAndComponentId method");
		logger.debug("Getting shares with component id = " + componentId
				+ " and user id = " + userId);

		Criteria selectCriteria = getSession().createCriteria(TaskShare.class);
		selectCriteria.add(Restrictions.eq("componentId", componentId)).add(
				Restrictions.eq("userId", userId));

		@SuppressWarnings("unchecked")
		Set<TaskShare> taskShares = (Set<TaskShare>) selectCriteria
				.uniqueResult();

		logger.info("Returing SentimentShares. Exiting getSharesByUserIdAndComponentId method");
		return taskShares;
	}

	@Override
	public void addMonetizationPoints(AirShareDataBean airShareDataBean,
			String action) throws Exception {

		Criteria criteria = getSession().createCriteria(Module.class);
		criteria.add(Restrictions.eq("componentType",
				airShareDataBean.getComponentType()));

		Module module = (Module) criteria.uniqueResult();
		if (module == null) {

			logger.error("No module found in module table for component: "
					+ airShareDataBean.getComponentType());
			throw new MessageException(
					"No module found in module table for component: "
							+ airShareDataBean.getComponentType());

		}
		UserMonetization userMonetization = new UserMonetization();

		userMonetization.setAction(action);
		userMonetization.setComponentType(airShareDataBean.getComponentType());

		userMonetization.setModule(module.getModule());
		userMonetization.setUserId(airShareDataBean.getUserId());
		userMonetization
				.setActionComponentId(airShareDataBean.getComponentId());

		UserMonetizationBussinessDeligate.addAction(userMonetization);

	}

}
