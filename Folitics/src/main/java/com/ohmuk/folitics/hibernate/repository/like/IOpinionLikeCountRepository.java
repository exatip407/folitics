package com.ohmuk.folitics.hibernate.repository.like;

import com.ohmuk.folitics.hibernate.entity.like.OpinionLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.OpinionLikeCountId;
import com.ohmuk.folitics.hibernate.entity.like.SentimentLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.SentimentLikeCountId;

public interface IOpinionLikeCountRepository extends
		ILikeCountHibernateRepository<OpinionLikeCount> {

	/**
	 * This method is for finding the record for entity:
	 * {@link OpinionLikeCount} by id
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.like.OpinionLikeCountId
	 * @return com.ohmuk.folitics.hibernate.entity.like.OpinionLikeCount
	 */
	public OpinionLikeCount find(OpinionLikeCountId id);

	/**
	 * This method is for deleting the record from database for entity:
	 * {@link OpinionLikeCount} by id
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.like.OpinionLikeCountId
	 */
	public void delete(OpinionLikeCountId id);

}
