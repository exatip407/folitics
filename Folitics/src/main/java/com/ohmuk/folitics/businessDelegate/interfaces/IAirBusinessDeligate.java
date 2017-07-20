package com.ohmuk.folitics.businessDelegate.interfaces;

import java.util.List;

import com.ohmuk.folitics.beans.AirShareDataBean;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.User;

public interface IAirBusinessDeligate {
	public static final String SUFFIX = "AirService";
	public static final String COUNT_SERVICE_SUFFIX = "AirCountService";

	public Object create(AirShareDataBean airShareBean) throws MessageException, Exception;

	public Object update(AirShareDataBean airShareBean) throws MessageException, Exception;

	public Object delete(AirShareDataBean airShareBean) throws MessageException, Exception;

	public Object deleteFromDB(AirShareDataBean airShareBean) throws MessageException, Exception;

	boolean isComponentAiredByUser(AirShareDataBean airShareDataBean) throws MessageException, Exception;

	List<User> findUserListForComponent(AirShareDataBean airShareDataBean) throws MessageException, Exception;

	Long getAirCount(AirShareDataBean airShareDataBean) throws MessageException, Exception;

}
