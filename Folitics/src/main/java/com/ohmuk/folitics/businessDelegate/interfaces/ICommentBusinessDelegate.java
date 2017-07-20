package com.ohmuk.folitics.businessDelegate.interfaces;

import java.util.List;

import com.ohmuk.folitics.beans.CommentBean;
import com.ohmuk.folitics.exception.MessageException;

public interface ICommentBusinessDelegate {

	/**
	 * Business Delegate method to save the object CommentBean in database
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.beans.CommentBean
	 * @return Object
	 * @throws MessageException
	 */
	public Object create(CommentBean commentBean) throws MessageException,
			Exception;

	/**
	 * Business Delegate method to find the object where id is equals to the
	 * given id
	 * 
	 * @author Harish
	 * @param id
	 * @return Object
	 * @throws MessageException
	 */
	public Object read(Long id, String componentType) throws MessageException;

	/**
	 * Business Delegate method to return all the objects of Object present in
	 * database table
	 * 
	 * @author Harish
	 * @return java.util.List<Object>
	 */
	public List<Object> readAll(String componentType) throws MessageException;

	/**
	 * Business Delegate method to update the object CommentBean in database and
	 * then return the updated object
	 * 
	 * @author Harish
	 * @param Object
	 *            t
	 * @return Object
	 * @throws MessageException
	 */
	public Object update(CommentBean commentBean) throws MessageException;

	/**
	 * Business delegate method for deleting the Comment object from database
	 * whose id and component type is equal to the given respective values
	 * 
	 * @author Harish
	 * @param java
	 *            .lang.Long id
	 * @param java
	 *            .lang.String componentType
	 * @return java.lang.Object
	 * @throws com.ohmuk.folitics.exception.MessageException
	 * @throws java.lang.Exception
	 */
	public Object delete(Long id, String componentType)
			throws MessageException, Exception;

	/**
	 * Business Delegate method returns the list of object which have component
	 * id and user id equals to the given component id and user id
	 * 
	 * @author Harish
	 * @param java
	 *            .lang.Long componentId
	 * @param java
	 *            .lang.Long userId
	 * @return List<Object>
	 * @throws MessageException
	 */
	public List<Object> getByComponentIdAndUserId(Long componentId,
			Long userId, String componentType) throws MessageException;

	/**
	 * Business Delegate method will find that list of record where component id
	 * is equal to the given component id.
	 * 
	 * @author Harish
	 * @param java
	 *            .lang.Long componentId
	 * @param java
	 *            .lang.Long userId
	 * @return Object
	 */

	public List<Object> getAllCommentsForComponent(Long componentId,
			String componentType) throws MessageException;

	/**
	 * Business Delegate method will find that list of record where user id is
	 * equal to the given user id.
	 * 
	 * @author Harish
	 * @param java
	 *            .lang.Long userId
	 * @param java
	 *            .lang.Long userId
	 * @return Object
	 */

	public List<Object> getAllCommentsForUserId(Long userId,
			String componentType) throws MessageException;

}
