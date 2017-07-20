package com.ohmuk.folitics.hibernate.repository.comment;

import java.util.List;

import com.ohmuk.folitics.beans.CommentBean;
import com.ohmuk.folitics.exception.MessageException;

/**
 * Hibernate repository interface for comment
 * 
 * @author Harish
 *
 * @param <T>
 */
public interface ICommentHibernateRepository<T> {

	/**
	 * This method will save the object t in the database and then will return
	 * the object
	 * 
	 * @author Harish
	 * @param T
	 *            t
	 * @return T
	 */

	public T save(T t);

	/**
	 * This method will update the given object in the database and then will
	 * return the updated object
	 * 
	 * @author Harish
	 * @param T
	 *            t
	 * @return T
	 */
	public T update(T t);

	/**
	 * This method will return all the object present in table and will return
	 * them in list
	 * 
	 * @author Harish
	 * @return java.util.List<T>
	 */
	public List<T> findAll();

	/**
	 * This method will delete the record from database where id is equals to
	 * the given id
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.jpa.entity.like.LikeId id
	 */

	public void delete(Long id);

	/**
	 * This method will find that list of record where component id and user id
	 * is equal to the given component id and user id respectively
	 * 
	 * @author Harish
	 * @param java
	 *            .lang.Long componentId
	 * @param java
	 *            .lang.Long userId
	 * @return List<T>
	 */

	public List<T> getByComponentIdAndUserId(Long componentId, Long userId)throws MessageException;

	/**
	 * This method will find that list of record where component id is equal to
	 * the given component id.
	 * 
	 * @author Harish
	 * @param java
	 *            .lang.Long componentId
	 * @param java
	 *            .lang.Long userId
	 * @return List<T>
	 */

	public List<T> getAllCommentsForComponent(Long componentId)throws MessageException;

	/**
	 * This method will find that list of record where user id is equal to the
	 * given user id.
	 * 
	 * @author Harish
	 * @param java
	 *            .lang.Long componentId
	 * @param java
	 *            .lang.Long userId
	 * @return  List<T>
	 */

	public List<T> getAllCommentsForUserId(Long userId)throws MessageException;

	/**
	 * This method will find the record in database by the given id
	 * 
	 * @param com
	 *            .ohmuk.folitics.jpa.entity.like.LikeId id
	 * @return T
	 */
	public T find(Long id);

	/**
	 * This method will added user points, after comment on any component
	 * 
	 * @author Harish
	 * @param javacom
	 *            .ohmuk.folitics.beans.CommentBean
	 * 
	 * @return void
	 */

	public void addMonetizationPoints(CommentBean commentBean, String action)
			throws Exception;

}
