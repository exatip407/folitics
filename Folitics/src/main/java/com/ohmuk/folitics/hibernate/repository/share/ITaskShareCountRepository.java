package com.ohmuk.folitics.hibernate.repository.share;

import com.ohmuk.folitics.hibernate.entity.share.SentimentShareCount;
import com.ohmuk.folitics.hibernate.entity.share.TaskShareCount;
import com.ohmuk.folitics.hibernate.entity.share.TaskShareCountId;

/**
 * Interface for entity: {@link TaskShareCount} repository
 * 
 * @author Harish
 *
 */
public interface ITaskShareCountRepository extends
		IShareCountHibernateRepository<TaskShareCount> {

	/**
	 * This method is for finding the record for entity: {@link TaskShareCount}
	 * by id
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.share.TaskShareCountId
	 * @return com.ohmuk.folitics.hibernate.entity.share.TaskShareCount
	 */
	public TaskShareCount find(TaskShareCountId id);

	/**
	 * This method is for deleting the record from database for entity:
	 * {@link SentimentShareCount} by id
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.jpa.entity.share.SentimentShareCountId id
	 */
	public void delete(TaskShareCountId id);

}
