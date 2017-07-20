package com.ohmuk.folitics.hibernate.repository.share;

import com.ohmuk.folitics.hibernate.entity.share.OpinionShareCount;
import com.ohmuk.folitics.hibernate.entity.share.OpinionShareCountId;

/**
 * Hibernate repository interface for Share Count
 * @author Harish
 *
 */

public interface IOpinionShareCountRepository extends IShareCountHibernateRepository<OpinionShareCount> {

	/**
     * This method is for finding the record for entity: {@link OpinionShareCount} by id
     * 
     * @author Harish
     * @param com.ohmuk.folitics.jpa.entity.share.OpinionShareCountId id
     * @return com.ohmuk.folitics.jpa.entity.share.OpinionShareCount
     */	
    public OpinionShareCount find(OpinionShareCountId id);

    /**
     * This method is for deleting the record from database for entity: {@link OpinionShareCount} by id
     * 
     * @author Harish
     * @param com.ohmuk.folitics.jpa.entity.share.OpinionShareCountId id
     */
    public void delete(OpinionShareCountId id);
	
}
