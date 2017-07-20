package com.ohmuk.folitics.hibernate.repository.comment;

import com.ohmuk.folitics.hibernate.entity.comment.ResponseCommentCount;
import com.ohmuk.folitics.hibernate.entity.comment.ResponseCommentCountId;

public interface IResponseCommentCountRepository extends
		ICommentCountHibernateRepository<ResponseCommentCount> {

	public ResponseCommentCount find(ResponseCommentCountId id);

	public void delete(ResponseCommentCountId id);

}
