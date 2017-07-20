package com.ohmuk.folitics.hibernate.repository.follow;

import com.ohmuk.folitics.hibernate.entity.follow.OpinionFollowCount;
import com.ohmuk.folitics.hibernate.entity.follow.OpinionFollowCountId;

public interface IOpinionFollowCountRepository extends
		IFollowCountHibernateRepository<OpinionFollowCount> {
	/**
	 * @param id
	 * @return
	 */
	public OpinionFollowCount find(OpinionFollowCountId id);

	/**
	 * @param id
	 */
	public void delete(OpinionFollowCountId id);

}
