package com.ohmuk.folitics.hibernate.repository.follow;

import com.ohmuk.folitics.hibernate.entity.air.GovtSchemeDataCountId;
import com.ohmuk.folitics.hibernate.entity.follow.GovtSchemeDataFollowCount;

public interface IGovtSchemeDataFollowCountRepository extends
		IFollowCountHibernateRepository<GovtSchemeDataFollowCount> {

	/**
	 * @param id
	 * @return
	 */
	public GovtSchemeDataFollowCount find(GovtSchemeDataCountId id);

	/**
	 * @param id
	 */
	public void delete(GovtSchemeDataCountId id);

}
