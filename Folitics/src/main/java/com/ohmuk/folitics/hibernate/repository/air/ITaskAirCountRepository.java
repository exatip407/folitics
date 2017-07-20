package com.ohmuk.folitics.hibernate.repository.air;

import com.ohmuk.folitics.hibernate.entity.air.TaskAirCount;
import com.ohmuk.folitics.hibernate.entity.air.TaskCountId;

/**
 * Interface for entity: {@link TaskAirCount} repository
 * 
 * @author Harish
 *
 */
public interface ITaskAirCountRepository extends
		IAirCountHibernateRepository<TaskAirCount> {

	public TaskAirCount find(TaskCountId id);

	public void delete(TaskCountId id);

}
