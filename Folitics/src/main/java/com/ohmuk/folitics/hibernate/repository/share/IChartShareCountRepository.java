package com.ohmuk.folitics.hibernate.repository.share;

import com.ohmuk.folitics.hibernate.entity.share.ChartShareCount;
import com.ohmuk.folitics.hibernate.entity.share.ChartShareCountId;

/**
 * Interface for entity: {@link ChartShareCount} repository
 * 
 * @author Harish
 *
 */
public interface IChartShareCountRepository extends
		IShareCountHibernateRepository<ChartShareCount> {

	/**
	 * This method is for finding the record for entity: {@link ChartShareCount}
	 * by id
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.share.ChartShareCountId
	 * @return com.ohmuk.folitics.hibernate.entity.share.ChartShareCount
	 */
	public ChartShareCount find(ChartShareCountId id);

	/**
	 * This method is for deleting the record from database for entity:
	 * {@link ChartShareCount} by id
	 * 
	 * @author Harish
	 * @return com.ohmuk.folitics.hibernate.entity.share.ChartShareCount
	 */
	public void delete(ChartShareCountId id);

}
