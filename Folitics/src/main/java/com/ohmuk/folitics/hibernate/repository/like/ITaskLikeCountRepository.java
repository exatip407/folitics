package com.ohmuk.folitics.hibernate.repository.like;

import com.ohmuk.folitics.hibernate.entity.like.TaskLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.TaskLikeCountId;

/**
 * Interface for entity: {@link TaskLikeCount} repository
 * 
 * @author Harish
 *
 */
public interface ITaskLikeCountRepository extends
		ILikeCountHibernateRepository<TaskLikeCount> {

	/**
	 * This method is for finding the record for entity: {@link TaskLikeCount}
	 * by id
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.like.TaskLikeCountId id
	 * @return com.ohmuk.folitics.hibernate.entity.like.TaskLikeCount
	 */
	public TaskLikeCount find(TaskLikeCountId id);

	/**
	 * This method is for deleting the record from database for entity:
	 * {@link TaskLikeCount} by id
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.like.TaskLikeCountId id
	 */
	public void delete(TaskLikeCountId id);

}
