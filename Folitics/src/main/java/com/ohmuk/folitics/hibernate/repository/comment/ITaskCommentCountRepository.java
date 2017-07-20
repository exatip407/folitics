package com.ohmuk.folitics.hibernate.repository.comment;

import com.ohmuk.folitics.hibernate.entity.comment.TaskCommentCount;
import com.ohmuk.folitics.hibernate.entity.comment.TaskCommentCountId;

/**
 * Interface for entity: {@link TaskCommentCount} repository
 * 
 * @author Harish
 *
 */
public interface ITaskCommentCountRepository extends
		ICommentCountHibernateRepository<TaskCommentCount> {

	public TaskCommentCount find(TaskCommentCountId id);

	public void delete(TaskCommentCountId id);

}
