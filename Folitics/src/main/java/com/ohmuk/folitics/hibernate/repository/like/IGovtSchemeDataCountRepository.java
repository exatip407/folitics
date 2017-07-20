package com.ohmuk.folitics.hibernate.repository.like;

import com.ohmuk.folitics.hibernate.entity.air.GovtSchemeDataCountId;
import com.ohmuk.folitics.hibernate.entity.like.GovtSchemeDataLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.ResponseLikeCount;

public interface IGovtSchemeDataCountRepository extends
		ILikeCountHibernateRepository<GovtSchemeDataLikeCount> {

	/**
	 * This method is for finding the record for entity:
	 * {@link ResponseLikeCount} by id
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.air.GovtSchemeDataCountId
	 * @return com.ohmuk.folitics.hibernate.entity.like.GovtSchemeDataLikeCount
	 */
	public GovtSchemeDataLikeCount find(GovtSchemeDataCountId id);

	/**
	 * This method is for deleting the record from database for entity:
	 * {@link ResponseLikeCount} by id
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.air.GovtSchemeDataCountId
	 */
	public void delete(GovtSchemeDataCountId id);

}
