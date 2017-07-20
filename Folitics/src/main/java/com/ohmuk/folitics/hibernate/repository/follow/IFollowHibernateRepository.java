package com.ohmuk.folitics.hibernate.repository.follow;

import java.util.List;

import com.ohmuk.folitics.beans.FollowDataBean;
import com.ohmuk.folitics.exception.MessageException;

/**
 * Hibernate repository for follow
 * 
 * @author Kshitij
 *
 * @param <T>
 */
public interface IFollowHibernateRepository<T> {

	/**
	 * @param t
	 * @return
	 */
	public T save(T t) throws MessageException, Exception;

	/**
	 * @param t
	 */
	public void delete(T t) throws MessageException, Exception;

	/**
	 * select * from table_name where componentId=? and userId=?
	 * 
	 * @param componentId
	 * @param userId
	 * @return
	 */
	public T findByComponentIdAndUserId(Long componentId, Long userId)
			throws MessageException, Exception;

	/**
	 * @param t
	 * @return
	 */
	public T update(T t) throws MessageException, Exception;

	/**
	 * select * from table_name where componentId=? and userId=?
	 * 
	 * @param componentId
	 * @param isFollowing
	 * @return
	 */
	public List<T> findByComponentIdAndIsFollowing(Long componentId,
			boolean isFollowing);

	/**
	 * @param followDataBean
	 * @return
	 */
	public boolean findByComponentIdUserIdAndIsFollowing(
			FollowDataBean followDataBean);

	/**
	 * This method will added user points, after follow on any component
	 * 
	 * @author Harish
	 * @param javacom
	 *            .ohmuk.folitics.beans.LikeDataBean
	 * 
	 * @return void
	 */

	public void addMonetizationPoints(FollowDataBean followDataBean,
			String action) throws Exception;

}
