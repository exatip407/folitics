package com.ohmuk.folitics.hibernate.repository.air;

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

import com.ohmuk.folitics.beans.AirShareDataBean;
import com.ohmuk.folitics.businessDelegate.interfaces.IUserMonetizationBusinessDeligate;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Module;
import com.ohmuk.folitics.hibernate.entity.UserMonetization;
import com.ohmuk.folitics.hibernate.entity.air.TaskAir;

/**
 * Repository implementation for entity: {@link TaskAir}
 * 
 * @author Harish
 *
 */

@Component
@Repository
public class TaskAirRepositoryImplementation implements ITaskAirRepository {
	
	private static Logger logger=LoggerFactory.getLogger(TaskAirRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private volatile IUserMonetizationBusinessDeligate userMonetizationBussinessDeligate;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public TaskAir save(TaskAir taskAir) {

		Long id = (Long) getSession().save(taskAir);
		taskAir = (TaskAir) getSession().get(TaskAir.class, id);
		return taskAir;
	}

	@Override
	public TaskAir update(TaskAir taskAir) {
		taskAir = (TaskAir) getSession().merge(taskAir);
		getSession().update(taskAir);

		taskAir = (TaskAir) getSession().get(TaskAir.class, taskAir.getId());

		return taskAir;
	}

	@Override
	public List<TaskAir> findAll() {
		@SuppressWarnings("unchecked")
		List<TaskAir> taskAirs = getSession().createQuery("FROM taskair")
				.list();
		return taskAirs;
	}

	@Override
	public void delete(Long likeId) {
		TaskAir taskAir = (TaskAir) getSession().get(TaskAir.class, likeId);
		getSession().delete(taskAir);

	}

	@Override
	public List<TaskAir> getByComponentIdAndUserId(Long componentId, Long userId) {
		Query query = getSession()
				.createSQLQuery(
						"SELECT * FROM taskair s WHERE s.componentId = :componentId AND s.userId = :userId")
				.addEntity(TaskAir.class)
				.setParameter("componentId", componentId)
				.setParameter("userId", userId);

		@SuppressWarnings("unchecked")
		List<TaskAir> tasksAirs = (List<TaskAir>) query.list();
		return tasksAirs;
	}

	@Override
	public TaskAir find(Long id) {

		TaskAir taskAir = (TaskAir) getSession().get(TaskAir.class, id);
		return taskAir;
	}

	@Override
	public List<TaskAir> findAirsByComponentId(Long id) {
		Query query = getSession()
				.createSQLQuery("SELECT * FROM taskair s WHERE s.id = :id")
				.addEntity(TaskAir.class).setParameter("id", id);

		@SuppressWarnings("unchecked")
		List<TaskAir> taskAirs = (List<TaskAir>) query.list();
		return taskAirs;
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

		userMonetizationBussinessDeligate.addAction(userMonetization);

	}

}
