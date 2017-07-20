package com.ohmuk.folitics.hibernate.repository.comment;

import com.ohmuk.folitics.hibernate.entity.comment.OpinionCommentCount;
import com.ohmuk.folitics.hibernate.entity.comment.OpinionCommentCountId;

public interface IOpinionCommentCountRepository extends
		ICommentCountHibernateRepository<OpinionCommentCount> {

	public OpinionCommentCount find(OpinionCommentCountId id);

	public void delete(OpinionCommentCountId id);

}
