package com.ohmuk.folitics.hibernate.repository.follow;

import com.ohmuk.folitics.hibernate.entity.follow.TaskFollowCount;
import com.ohmuk.folitics.hibernate.entity.follow.TaskFollowCountId;

public interface ITaskFollowCountRepository extends
		IFollowCountHibernateRepository<TaskFollowCount> {
	/**
	 * This method is for finding the record for entity: {@link TaskFollowCount}
	 * by id
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.follow.TaskFollowCountId id
	 * @return com.ohmuk.folitics.hibernate.entity.follow.TaskFollowCount
	 */
	public TaskFollowCount find(TaskFollowCountId id);

	/**
	 * This method is for deleting the record from database for entity:
	 * {@link TaskFollowCount} by id
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.follow.TaskFollowCountId id
	 */
	public void delete(TaskFollowCountId id);

}
