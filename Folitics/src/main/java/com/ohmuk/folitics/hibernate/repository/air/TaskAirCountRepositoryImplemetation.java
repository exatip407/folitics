package com.ohmuk.folitics.hibernate.repository.air;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ohmuk.folitics.hibernate.entity.air.TaskAirCount;
import com.ohmuk.folitics.hibernate.entity.air.TaskCountId;

@Component
@Repository
public class TaskAirCountRepositoryImplemetation implements
		ITaskAirCountRepository {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public TaskAirCount save(TaskAirCount taskAirCount) {
		TaskCountId id = (TaskCountId) getSession().save(taskAirCount);
		taskAirCount = (TaskAirCount) getSession().get(TaskAirCount.class, id);
		return taskAirCount;
	}

	@Override
	public TaskAirCount update(TaskAirCount taskAirCount) {
		taskAirCount = (TaskAirCount) getSession().merge(taskAirCount);
		getSession().update(taskAirCount);

		taskAirCount = (TaskAirCount) getSession().get(TaskAirCount.class,
				taskAirCount.getId());
		return taskAirCount;
	}

	@Override
	public List<TaskAirCount> findAll() {
		@SuppressWarnings("unchecked")
		List<TaskAirCount> taskAirCounts = getSession().createQuery(
				"FROM taskaircount").list();
		return taskAirCounts;
	}

	@Override
	public TaskAirCount findByComponentId(Long taskId) {
		Query query = getSession()
				.createSQLQuery(
						"SELECT * FROM taskaircount s WHERE s.taskId = :taskId")
				.addEntity(TaskAirCount.class).setParameter("taskId", taskId);

		TaskAirCount taskAirCount = (TaskAirCount) query.uniqueResult();
		return taskAirCount;
	}

	@Override
	public TaskAirCount find(TaskCountId id) {
		TaskAirCount taskAirCount = (TaskAirCount) getSession().get(
				TaskAirCount.class, id);
		return taskAirCount;
	}

	@Override
	public void delete(TaskCountId id) {
		TaskAirCount taskAirCount = (TaskAirCount) getSession().get(
				TaskAirCount.class, id);
		getSession().delete(taskAirCount);

	}

}
