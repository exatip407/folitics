package com.ohmuk.folitics.hibernate.repository.air;

import com.ohmuk.folitics.hibernate.entity.air.GovtSchemeDataAirCount;
import com.ohmuk.folitics.hibernate.entity.air.GovtSchemeDataCountId;

/**
 * Interface for entity: {@link GovtSchemeDataAirCount} repository
 * 
 * @author Harish
 *
 */
public interface IGovtSchemeDataAirCountRepository extends IAirCountHibernateRepository<GovtSchemeDataAirCount>{
	
	public GovtSchemeDataAirCount find(GovtSchemeDataCountId id);
	
	public void delete(GovtSchemeDataCountId id);

}
