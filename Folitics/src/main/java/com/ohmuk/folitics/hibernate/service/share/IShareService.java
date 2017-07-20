package com.ohmuk.folitics.hibernate.service.share;

import java.util.List;

import com.ohmuk.folitics.beans.AirShareDataBean;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.User;

/**
 * Service interface for Share
 * 
 * @author Abhishek
 *
 * @param <T>
 */
public interface IShareService<T> {

	/**
	 * This method calls the repository save method to save the object
	 * AirShareDataBean in database
	 * 
	 * @author Harish
	 * @param AirShareDataBean
	 *            airShareDataBean
	 * @return T
	 * @throws MessageException
	 * @throws Exception
	 */
	public T create(AirShareDataBean airShareDataBean) throws MessageException,
			Exception;

	/**
	 * This method calls the repository find method to return object where id is
	 * equals to the given id
	 * 
	 * @author Abhishek
	 * @param java
	 *            .lang.Long id
	 * @return T
	 * @throws MessageException
	 */
	public T read(Long id) throws MessageException;

	/**
	 * This method calls the repository finaAll method to return all the objects
	 * of T present in database table
	 * 
	 * @author Abhishek
	 * @return java.util.List<T>
	 */
	public List<T> readAll();

	/**
	 * This method calls the repository update method to update the object
	 * AirShareDataBean in database and then return the updated object
	 * 
	 * @author Abhishek
	 * @param AirShareDataBean
	 *            airShareDataBean
	 * @return T
	 * @throws MessageException
	 * @throws Exception
	 */
	public T update(AirShareDataBean airShareDataBean) throws MessageException,
			Exception;

	/**
	 * This method returns list of all the users that share the component
	 * AirShareDataBean
	 * 
	 * @author Abhishek
	 * @param AirShareDataBean
	 *            airShareDataBean
	 * @return java.util.List<com.ohmuk.folitics.jpa.entity.User>
	 * @throws MessageException
	 * @throws Exception
	 */
	public List<User> findUserListForComponent(AirShareDataBean airShareDataBean)
			throws MessageException, Exception;

	/**
	 * This method checks whether user has shared the component airShareDataBean
	 * and returns true in case user shared the component otherwise false
	 * 
	 * @param AirShareDataBean
	 *            airShareDataBean
	 * @return boolean true if user shared the component t, else false
	 * @throws MessageException
	 * @throws Exception
	 */
	public boolean isComponentSharedByUser(AirShareDataBean airShareDataBean)
			throws MessageException, Exception;

	/**
	 * This method calls the repository delete method to delete the object t
	 * from database table
	 * 
	 * @author Abhishek
	 * @param java
	 *            .lang.Long id
	 * @return T
	 * @throws MessageException
	 * @throws Exception
	 */
	public T delete(Long id) throws MessageException, Exception;

}
