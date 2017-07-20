package com.ohmuk.folitics.hibernate.repository.air;

import com.ohmuk.folitics.hibernate.entity.air.SentimentAirCount;
import com.ohmuk.folitics.hibernate.entity.air.SentimentCountId;
import com.ohmuk.folitics.hibernate.entity.like.SentimentLikeCount;

/**
 * Interface for entity: {@link SentimentLikeCount} repository
 * 
 * @author Abhishek
 *
 */
public interface ISentimentAirCountRepository extends
		IAirCountHibernateRepository<SentimentAirCount> {

	public SentimentAirCount find(SentimentCountId id);

	public void delete(SentimentCountId id);

}
