package com.ohmuk.folitics.hibernate.repository.comment;

import com.ohmuk.folitics.hibernate.entity.comment.SentimentCommentCount;
import com.ohmuk.folitics.hibernate.entity.comment.SentimentCommentCountId;

/**
 * Interface for entity: {@link SentimentCommentCount} repository
 * 
 * @author Harish
 *
 */
public interface ISentimentCommentCountRepository extends
		ICommentCountHibernateRepository<SentimentCommentCount> {

	public SentimentCommentCount find(SentimentCommentCountId id);

	public void delete(SentimentCommentCountId id);

}
