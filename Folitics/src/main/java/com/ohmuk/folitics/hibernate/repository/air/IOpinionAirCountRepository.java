package com.ohmuk.folitics.hibernate.repository.air;

import com.ohmuk.folitics.hibernate.entity.air.OpinionAirCount;
import com.ohmuk.folitics.hibernate.entity.air.OpinionCountId;
import com.ohmuk.folitics.hibernate.entity.like.SentimentLikeCount;

/**
 * Interface for entity: {@link SentimentLikeCount} repository
 * 
 * @author Harish
 *
 */
public interface IOpinionAirCountRepository extends IAirCountHibernateRepository<OpinionAirCount>{
	
	public OpinionAirCount find(OpinionCountId id);

	public void delete(OpinionCountId id);

}
