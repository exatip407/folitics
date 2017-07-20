package com.ohmuk.folitics.hibernate.repository.like;

import com.ohmuk.folitics.hibernate.entity.like.SentimentLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.SentimentLikeCountId;

public interface ISentimentLikeCountRepository extends
		ILikeCountHibernateRepository<SentimentLikeCount> {

	/**
	 * This method is for finding the record for entity:
	 * {@link SentimentLikeCount} by id
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.jpa.entity.like.SentimentLikeCountId id
	 * @return com.ohmuk.folitics.jpa.entity.like.SentimentLikeCount
	 */
	public SentimentLikeCount find(SentimentLikeCountId id);

	/**
	 * This method is for deleting the record from database for entity:
	 * {@link SentimentLikeCount} by id
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.jpa.entity.like.SentimentLikeCountId id
	 */
	public void delete(SentimentLikeCountId id);

}
