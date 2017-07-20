package com.ohmuk.folitics.service.comment;

import java.util.List;

import com.ohmuk.folitics.beans.CommentBean;
import com.ohmuk.folitics.exception.MessageException;

public interface ICommentService<T> {

	/**
	 * This method calls the repository save method to save the object t in
	 * database
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.beans.CommentBean
	 * @return T
	 * @throws MessageException
	 */
	public T create(CommentBean commentBean) throws MessageException, Exception;

	/**
	 * This method calls the repository find method to return object where id is
	 * equals to the given id
	 * 
	 * @author Harish
	 * @param id
	 * @return T
	 * @throws MessageException
	 */
	public T read(Long id) throws MessageException;

	/**
	 * This method calls the repository finaAll method to return all the objects
	 * of T present in database table
	 * 
	 * @author Harish
	 * @return java.util.List<T>
	 */
	public List<T> readAll();

	/**
	 * This method calls the repository update method to update the object t in
	 * database and then return the updated object
	 * 
	 * @author Harish
	 * @param T
	 *            t
	 * @return T
	 * @throws MessageException
	 */
	public T update(CommentBean commentBean) throws MessageException;

	/**
	 * This method calls the repository delete method to delete the object t
	 * from database table
	 * 
	 * @author Harish
	 * @param java
	 *            .lang.Long id
	 * @return T
	 * @throws MessageException
	 * @throws Exception
	 */
	public T delete(Long id) throws MessageException, Exception;

	/**
	 * This method returns the object which have component id and user id equals
	 * to the given component id and user id
	 * 
	 * @author Harish
	 * @param java
	 *            .lang.Long componentId
	 * @param java
	 *            .lang.Long userId
	 * @return List<T>
	 * @throws MessageException
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
	 * @return T
	 */

	public List<T> getAllCommentsForComponent(Long componentId)
			throws MessageException;

	/**
	 * This method will find that list of record where user id is equal to the
	 * given user id.
	 * 
	 * @author Harish
	 * @param java
	 *            .lang.Long componentId
	 * @param java
	 *            .lang.Long userId
	 * @return T
	 */

	public List<T> getAllCommentsForUserId(Long userId) throws MessageException;

}
