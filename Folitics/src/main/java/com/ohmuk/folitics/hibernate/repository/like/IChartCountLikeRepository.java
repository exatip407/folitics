package com.ohmuk.folitics.hibernate.repository.like;

import com.ohmuk.folitics.hibernate.entity.like.ChartLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.ChartLikeCountId;
import com.ohmuk.folitics.hibernate.entity.like.SentimentLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.SentimentLikeCountId;

public interface IChartCountLikeRepository extends
		ILikeCountHibernateRepository<ChartLikeCount> {

	/**
	 * This method is for finding the record for entity:
	 * {@link ChartLikeCount} by id
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.like.ChartLikeCountId
	 * @return com.ohmuk.folitics.hibernate.entity.like.ChartLikeCount
	 */
	public ChartLikeCount find(ChartLikeCountId id);

	/**
	 * This method is for deleting the record from database for entity:
	 * {@link ChartLikeCount} by id
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.like.ChartLikeCountId
	 */
	public void delete(ChartLikeCountId id);

}
