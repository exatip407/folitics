package com.ohmuk.folitics.hibernate.repository.air;

import java.util.List;

import com.ohmuk.folitics.beans.AirShareDataBean;

/**
 * Hibernate repository interface for like
 * 
 * @author Abhishek
 *
 * @param <T>
 */
public interface IAirHibernateRepository<T> {

	public T save(T t);

	public T update(T t);

	public List<T> findAll();

	public void delete(Long id);

	public List<T> getByComponentIdAndUserId(Long componentId, Long userId);

	T find(Long id);

	List<T> findAirsByComponentId(Long id);

	/**
	 * This method will added user points, after Air on any component
	 * 
	 * @author Harish
	 * @param javacom
	 *            .ohmuk.folitics.beans.AirShareDataBean
	 * 
	 * @return void
	 */

	public void addMonetizationPoints(AirShareDataBean airShareDataBean,
			String action) throws Exception;

}
