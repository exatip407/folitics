package com.ohmuk.folitics.hibernate.service.like;

import java.util.List;

import com.ohmuk.folitics.beans.LikeDataBean;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.like.LikeId;

/**
 * Service interface for Like
 * 
 * @author Abhishek
 *
 * @param <T>
 */
public interface ILikeService<T> {

	/**
	 * This method calls the repository save method to save the object t in
	 * database
	 * 
	 * @author Abhishek
	 * @param T
	 *            t
	 * @return T
	 * @throws MessageException
	 */
	public T create(LikeDataBean likeDataBean) throws MessageException,Exception;

	/**
	 * This method calls the repository find method to return object where id is
	 * equals to the given id
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.jpa.entity.like.LikeId likeId
	 * @return T
	 * @throws MessageException
	 */
	public T read(LikeId likeId) throws MessageException;

	/**
	 * This method calls the repository finaAll method to return all the objects
	 * of T present in database table
	 * 
	 * @author Abhishek
	 * @return java.util.List<T>
	 */
	public List<T> readAll();

	/**
	 * This method calls the repository update method to update the object t in
	 * database and then return the updated object
	 * 
	 * @author Abhishek
	 * @param T
	 *            t
	 * @return T
	 * @throws MessageException
	 */
	public T update(T t) throws MessageException;

	/**
	 * This method calls the repository delete method to delete the object from
	 * database table where id is equals to the given id
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.jpa.entity.like.LikeId likeId
	 * @return T
	 * @throws MessageException
	 */
	public T delete(LikeId likeId) throws MessageException;

	/**
	 * This method calls the repository delete method to delete the object t
	 * from database table
	 * 
	 * @author Abhishek
	 * @param T
	 *            t
	 * @return T
	 * @throws MessageException
	 */
	public T delete(T t) throws MessageException;

	/**
	 * This method returns the object which have component id and user id equals
	 * to the given component id and user id
	 * 
	 * @author Abhishek
	 * @param java
	 *            .lang.Long componentId
	 * @param java
	 *            .lang.Long userId
	 * @return T
	 * @throws MessageException
	 */
	public T getByComponentIdAndUserId(Long componentId, Long userId)
			throws MessageException;

	/**
	 * This method to update the entry of like corresponding LikeDataBean to the
	 * database for unlike event
	 * 
	 * @author Harish
	 * @param likeDataBean
	 * @return java.lang.Object
	 * @throws MessageException
	 */
	public T unlike(LikeDataBean likeDataBean) throws MessageException,Exception;

	/**
	 * This method to create an entry for dislike event in the database
	 * 
	 * @author Harish
	 * @Param likeDataBean
	 * @return java.lang.Object
	 * @throws MessageException
	 */
	public T dislike(LikeDataBean likeDataBean) throws MessageException,Exception;

	/**
	 * This method to update the entry of dislike corresponding to given
	 * LikeDataBean in the database for undislike event
	 * 
	 * @author Harish
	 * @param likeDataBean
	 * @return java.lang.Object
	 * @throws MessageException
	 */
	public T undislike(LikeDataBean likeDataBean) throws MessageException,Exception;

}
