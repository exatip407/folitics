package com.ohmuk.folitics.hibernate.repository.share;

import com.ohmuk.folitics.hibernate.entity.share.GovtSchemeDataShareCount;
import com.ohmuk.folitics.hibernate.entity.share.GovtSchemeDataShareCountId;

/**
 * Hibernate repository interface for Share Count
 * @author Harish
 *
 */
public interface IGovtSchemeDataShareCountRepository extends IShareCountHibernateRepository<GovtSchemeDataShareCount>{

	/**
     * This method is for finding the record for entity: {@link GovtSchemeDataShareCount} by id
     * 
     * @author Harish
     * @param com.ohmuk.folitics.jpa.entity.share.GovtSchemeDataShareCountId id
     * @return com.ohmuk.folitics.jpa.entity.share.GovtSchemeDataShareCount
     */	
    public GovtSchemeDataShareCount find(GovtSchemeDataShareCountId id);
	
    /**
     * This method is for deleting the record from database for entity: {@link GovtSchemeDataShareCount} by id
     * 
     * @author Harish
     * @param com.ohmuk.folitics.jpa.entity.share.GovtSchemeDataShareCountId id
     */
    public void delete(GovtSchemeDataShareCountId id);
}
