package com.ohmuk.folitics.service.air;

import java.util.List;

import com.ohmuk.folitics.beans.AirShareDataBean;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.User;

public interface IAirService {

	/**
	 * This method is used to create the air.
	 * 
	 * @param AirShareDataBean
	 * @return the entity which is created
	 * @throws MessageException
	 *             ,Exception
	 */
	public Object create(AirShareDataBean airShareDataBean)
			throws MessageException, Exception;

	/**
	 * This method is used to update the air. Only description will be updated.
	 * 
	 * @param AirShareDataBean
	 * @return the entity which is updated
	 * @throws MessageException
	 *             ,Exception
	 */

	public Object update(AirShareDataBean airShareDataBean)
			throws MessageException, Exception;

	/**
	 * This method is used to find user list who aired the component.
	 * 
	 * @param AirShareDataBean
	 * @return List of user sharing the component
	 * @throws MessageException
	 *             ,Exception
	 */

	public List<User> findUserListForComponent(AirShareDataBean airShareDataBean)
			throws MessageException, Exception;

	/**
	 * This method is used to find if the given user has aired the component.
	 * 
	 * @param AirShareDataBean
	 * @return true if aired by user false if not
	 * @throws MessageException
	 *             ,Exception
	 */
	boolean isComponentAiredByUser(AirShareDataBean airShareDataBean)
			throws MessageException, Exception;

	/**
	 * This method is used to delete the air from DB.
	 * 
	 * @param AirShareDataBean
	 * @return Null object of deleted successfully
	 * @throws MessageException
	 *             ,Exception
	 */
	public Object deleteFromDB(AirShareDataBean airShareDataBean)
			throws MessageException, Exception;

	/**
	 * This method is used to soft delete the air from DB.
	 * 
	 * @param AirShareDataBean
	 * @return the entity which is softly deleted
	 * @throws MessageException
	 *             ,Exception
	 */
	public Object delete(AirShareDataBean airShareDataBean)
			throws MessageException, Exception;
}
