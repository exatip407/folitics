package com.ohmuk.folitics.hibernate.repository.like;

import com.ohmuk.folitics.hibernate.entity.like.FactLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.FactLikeCountId;
import com.ohmuk.folitics.hibernate.entity.like.TaskLikeCount;

/**
 * Interface for entity: {@link factLikeCountF} repository
 * 
 * @author Harish
 *
 */
public interface IFactLikeCountRepository extends
		ILikeCountHibernateRepository<FactLikeCount> {

	/**
	 * This method is for finding the record for entity: {@link FactLikeCount}
	 * by id
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.like.FactLikeCountId
	 * @return com.ohmuk.folitics.hibernate.entity.like.FactLikeCount
	 */
	public FactLikeCount find(FactLikeCountId id);

	/**
	 * This method is for deleting the record from database for entity:
	 * {@link TaskLikeCount} by id
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.like.FactLikeCountId
	 */
	public void delete(FactLikeCountId id);

}
