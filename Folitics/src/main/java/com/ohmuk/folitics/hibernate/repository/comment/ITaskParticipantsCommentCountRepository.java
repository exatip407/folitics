package com.ohmuk.folitics.hibernate.repository.comment;

import com.ohmuk.folitics.hibernate.entity.comment.TaskParticipantsCommentCount;
import com.ohmuk.folitics.hibernate.entity.comment.TaskParticipantsCommentCountId;

public interface ITaskParticipantsCommentCountRepository extends
		ICommentCountHibernateRepository<TaskParticipantsCommentCount> {

	public TaskParticipantsCommentCount find(TaskParticipantsCommentCountId id);

	public void delete(TaskParticipantsCommentCountId id);

}
