package com.ohmuk.folitics.businessDelegate.interfaces;

import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.like.OpinionLike;

/**
 * Business Deligate interface for {@link ComponentModuleStorage}
 * 
 * @author
 * 
 *
 */
public interface IOpinionLikeBusinessDeligate {

	/**
	 * This method is to add OpinionLike
	 * 
	 * @author
	 * @return OpinionLike
	 * @throws Exception
	 */
	OpinionLike create(OpinionLike entity) throws MessageException;

	/**
	 * This method is to read OpinionLike by passing id
	 * 
	 * @author
	 * @return OpinionLike
	 * @throws Exception
	 */
	OpinionLike read(Long id) throws MessageException;

	/**
	 * This method is to read OpinionLike by passing id and userId
	 * 
	 * @author
	 * @return OpinionLike
	 * @throws Exception
	 */
	OpinionLike read(Long id, Long userId) throws MessageException;

	/**
	 * This method is to readAllByUser OpinionLike
	 * 
	 * @author
	 * @return OpinionLike
	 * @throws Exception
	 */
	List<OpinionLike> readAllByUser(Long userId) throws MessageException;

	/**
	 * This method is to getOpinionLikes OpinionLike
	 * 
	 * @author
	 * @return OpinionLike
	 * @throws Exception
	 */
	Long getOpinionLikes(Long opinionId) throws MessageException;

	/**
	 * This method is to getUserOpinionLikes OpinionLike
	 * 
	 * @author
	 * @return List<OpinionLike>
	 * @throws Exception
	 */
	List<OpinionLike> getUserOpinionLikes(Long userId) throws MessageException;

	/**
	 * This method is to delete OpinionLike by passing opinionId and userId
	 * 
	 * @author
	 * @throws Exception
	 */
	void delete(Long opinionId, Long userId) throws MessageException;

	/**
	 * This method is to deleteLike OpinionLike
	 * 
	 * @author
	 * @throws Exception
	 */
	void deleteLike(Long opinionLikeId) throws MessageException;

	/**
	 * This method is to like OpinionLike
	 * 
	 * @author
	 * @return OpinionLike
	 * @throws Exception
	 */
	OpinionLike like(Long opinionId, Long userId) throws MessageException;

	/**
	 * This method is to dislike OpinionLike
	 * 
	 * @author
	 * @return OpinionLike
	 * @throws Exception
	 */
	OpinionLike dislike(Long opinionId, Long userId) throws MessageException;

	/**
	 * This method is to prepareOpinionLikeEntity OpinionLike
	 * 
	 * @author
	 * @return OpinionLike
	 * @throws Exception
	 */
	OpinionLike prepareOpinionLikeEntity(Long opinionId, Long userId) throws MessageException;
}
