package com.ohmuk.folitics.hibernate.repository.like;

import com.ohmuk.folitics.hibernate.entity.like.SentimentCommentLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.SentimentCommentLikeCountId;
import com.ohmuk.folitics.hibernate.entity.like.TaskLikeCount;

/**
 * Interface for entity: {@link TaskLikeCount} repository
 * 
 * @author Harish
 *
 */
public interface ISentimentCommentLikeCountRepository extends
		ILikeCountHibernateRepository<SentimentCommentLikeCount> {

	/**
	 * This method is for finding the record for entity:
	 * {@link SentimentCommentLikeCount} by id
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.like.
	 *            SentimentCommentLikeCountId
	 * @return 
	 *         com.ohmuk.folitics.hibernate.entity.like.SentimentCommentLikeCount
	 */
	public SentimentCommentLikeCount find(SentimentCommentLikeCountId id);

	/**
	 * This method is for deleting the record from database for entity:
	 * {@link SentimentCommentLikeCount} by id
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.like.
	 *            SentimentCommentLikeCountId
	 */
	public void delete(SentimentCommentLikeCountId id);

}
