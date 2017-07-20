package com.ohmuk.folitics.service.follow;

import java.util.List;

import com.ohmuk.folitics.exception.MessageException;

/**
 * @author Sarvesh
 *
 * @param <T>
 */
public interface IFollowCountService<T> {

	/**
	 * @param t
	 * @return
	 */
	public T create(T t) throws MessageException;

	/**
	 * @return
	 */
	public List<T> readAll();

	/**
	 * @param t
	 * @return T
	 */
	public T update(T t) throws MessageException;;

	/**
	 * @param t
	 */
	public void delete(T t) throws MessageException;;

	/**
	 * @param componentId
	 * @return T
	 */
	public Long getByComponentId(Long componentId) throws MessageException;;
}
