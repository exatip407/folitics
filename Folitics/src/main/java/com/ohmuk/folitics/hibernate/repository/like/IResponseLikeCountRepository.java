package com.ohmuk.folitics.hibernate.repository.like;

import com.ohmuk.folitics.hibernate.entity.like.ResponseLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.ResponseLikeCountId;

public interface IResponseLikeCountRepository extends
		ILikeCountHibernateRepository<ResponseLikeCount> {

	/**
	 * This method is for finding the record for entity:
	 * {@link ResponseLikeCount} by id
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.like.ResponseLikeCountId
	 * @return com.ohmuk.folitics.hibernate.entity.like.ResponseLikeCount
	 */
	public ResponseLikeCount find(ResponseLikeCountId id);

	/**
	 * This method is for deleting the record from database for entity:
	 * {@link ResponseLikeCount} by id
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.like.ResponseLikeCountId
	 */
	public void delete(ResponseLikeCountId id);

}
