package com.ohmuk.folitics.hibernate.repository.like;

import com.ohmuk.folitics.hibernate.entity.like.TaskCommentLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.TaskCommentLikeCountId;
import com.ohmuk.folitics.hibernate.entity.like.TaskLikeCount;

/**
 * Interface for entity: {@link TaskLikeCount} repository
 * 
 * @author Harish
 *
 */
public interface ITaskCommentLikeCountRepository extends
		ILikeCountHibernateRepository<TaskCommentLikeCount> {

	/**
	 * This method is for finding the record for entity:
	 * {@link TaskCommentLikeCount} by id
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.like.TaskCommentLikeCountId
	 * @return com.ohmuk.folitics.hibernate.entity.like.TaskCommentLikeCount
	 */
	public TaskCommentLikeCount find(TaskCommentLikeCountId id);

	/**
	 * This method is for deleting the record from database for entity:
	 * {@link TaskCommentLikeCount} by id
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.like.TaskCommentLikeCountId
	 */
	public void delete(TaskCommentLikeCountId id);

}
