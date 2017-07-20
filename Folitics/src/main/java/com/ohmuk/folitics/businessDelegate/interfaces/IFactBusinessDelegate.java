package com.ohmuk.folitics.businessDelegate.interfaces;

import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Fact;

public interface IFactBusinessDelegate {
	/**
	 * Method is to add {@link com.ohmuk.folitics.hibernate.entity.Fact}
	 * 
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.Fact
	 * @return {@link com.ohmuk.folitics.hibernate.entity.Fact} @ throws
	 *         MessageException, Exception
	 * 
	 */
	public Fact create(Fact fact) throws MessageException, Exception;

	/**
	 * Method is to get {@link com.ohmuk.folitics.hibernate.entity.Fact} by id
	 * 
	 * @param id
	 * @return {@link com.ohmuk.folitics.hibernate.entity.Fact} @ throws
	 *         MessageException, Exception
	 * 
	 */
	public Fact read(Long id) throws MessageException, Exception;

	/**
	 * Method is to get all {@link com.ohmuk.folitics.hibernate.entity.Fact}
	 * 
	 * @return List of :com.ohmuk.folitics.hibernate.entity.Fact @ throws
	 *         MessageException, Exception
	 * 
	 */
	public List<Fact> readAll() throws MessageException, Exception;

	/**
	 * Method is to update {@link com.ohmuk.folitics.hibernate.entity.Fact}
	 * 
	 * @param com
	 *            .ohmuk.folitics.hibernate.entity.Fact
	 * @return com.ohmuk.folitics.hibernate.entity.Fact @ throws
	 *         MessageException, Exception
	 * 
	 */
	public Fact update(Fact fact) throws MessageException, Exception;

	/**
	 * Method is to hard delete {@link Fact} by id
	 * 
	 * @param id
	 * @return boolean @ throws MessageException, Exception
	 * 
	 */
	public boolean deleteFromDB(Long id) throws MessageException, Exception;

	/**
	 * Method is to soft delete {@link Fact} by id
	 * 
	 * @param id
	 * @return boolean @ throws MessageException, Exception
	 * 
	 */
	public boolean delete(Long id) throws MessageException, Exception;

	/**
	 * Method is to get all facts corresponding to particular userId
	 * 
	 * @return List of :com.ohmuk.folitics.hibernate.entity.Fact @ throws
	 *         MessageException, Exception
	 * 
	 */
	public List<Fact> readAll(Long userId) throws MessageException, Exception;

	/**
	 * Method is to get all facts by status
	 * 
	 * @return List of :com.ohmuk.folitics.hibernate.entity.Fact @ throws
	 *         MessageException, Exception
	 * 
	 */
	public List<Fact> readAll(String status) throws MessageException, Exception;

	/**
	 * Method is to get all com.ohmuk.folitics.hibernate.entity.Fact
	 * corresponding to parentId and parentType
	 * 
	 * @return List of :com.ohmuk.folitics.hibernate.entity.Fact @ throws
	 *         MessageException, Exception
	 * 
	 */
	public List<Fact> readAll(Long parentId, String parentType)
			throws MessageException, Exception;

	/**
	 * Method is to approve com.ohmuk.folitics.hibernate.entity.Fact
	 * 
	 * @param Long
	 *            factId
	 * @return {@link com.ohmuk.folitics.hibernate.entity.Fact} @ throws
	 *         MessageException, Exception
	 * 
	 */
	public Fact approveFact(Long id) throws MessageException, Exception;

	/**
	 * Method is to reject com.ohmuk.folitics.hibernate.entity.Fact
	 * 
	 * @param Long
	 *            factId
	 * @return {@link com.ohmuk.folitics.hibernate.entity.Fact} 
	 * @ throws
	 *         MessageException, Exception
	 * 
	 */
	public Fact rejectFact(Long factId) throws MessageException, Exception;

	/**
	 * Method is to get List of ten {com.ohmuk.folitics.hibernate.entity.Fact}
	 * starting from startId
	 * 
	 * @return List of :com.ohmuk.folitics.hibernate.entity.Fact
	 * @param: startId
	 * 
	 *  @ throws
	 *         MessageException, Exception
	 * 
	 */

	public List<Fact> paginationApi(int start)
			throws MessageException, Exception;

}
