/*
 * 
 */
package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.beans.AirShareDataBean;
import com.ohmuk.folitics.businessDelegate.interfaces.IAirBusinessDeligate;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.service.air.IAirCountService;
import com.ohmuk.folitics.service.air.IAirService;

// TODO: Auto-generated Javadoc
/**
 * The Class AirBusinessDeligate.
 */
@Component
public class AirBusinessDeligate implements IAirBusinessDeligate {
	
	/**
	 * The logger.
	 */
	private static Logger logger = LoggerFactory.getLogger(AirBusinessDeligate.class);
	
	/**
	 * The air service map.
	 */
	@Autowired
	private volatile Map<String ,IAirService> airServiceMap;
	
	/**
	 * The air count service map.
	 */
	@Autowired
	private volatile Map<String ,IAirCountService> airCountServiceMap;

	/**
	 * This method is used to create the air.
	 * @param AirShareDataBean
	 * @return the entity which is created
	 * @throws MessageException ,Exception
	 */
	
	@Override
	public Object create(AirShareDataBean airShareDataBean) throws MessageException, Exception {
		
		if (airShareDataBean.getComponentType() == null) {
			logger.error("componentType found to be null");
			throw (new MessageException("componentType can't be null"));
		}

		if (airShareDataBean.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (airShareDataBean.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}
		
		IAirService airService = airServiceMap.get(airShareDataBean.getComponentType()+SUFFIX);
		
		return airService.create(airShareDataBean);
	}
	/**
	 * This method is used to update the air. Only discription will be updated.
	 * @param AirShareDataBean
	 * @return the entity which is updated
	 * @throws MessageException ,Exception
	 */
	@Override
	public Object update(AirShareDataBean airShareDataBean) throws MessageException, Exception {
		if (airShareDataBean.getComponentType() ==null) {
			logger.error("componentType found to be null");
			throw (new MessageException("componentType can't be null"));
		}
		
		if (airShareDataBean.getId() ==null) {
			logger.error("id found to be null");
			throw (new MessageException("componentid can't be null"));
		}
		
		IAirService airService = airServiceMap.get(airShareDataBean.getComponentType()+SUFFIX);

		return airService.update(airShareDataBean);
	}
	
	/**
	 * This method is used to soft delete the air from DB.
	 * @param AirShareDataBean
	 * @return the entity which is softly deleted
	 * @throws MessageException ,Exception
	 */
	
	@Override
	public Object delete(AirShareDataBean airShareDataBean) throws MessageException, Exception {
		
		if (airShareDataBean.getComponentType() ==null) {
			logger.error("componentType found to be null");
			throw (new MessageException("componentType can't be null"));
		}
		
		if (airShareDataBean.getId() ==null) {
			logger.error("id found to be null");
			throw (new MessageException("componentid can't be null"));
		}
		IAirService airService = airServiceMap.get(airShareDataBean.getComponentType()+COUNT_SERVICE_SUFFIX);

		return airService.delete(airShareDataBean);
	}

	/**
	 * This method is used to delete the air from DB.
	 * @param AirShareDataBean
	 * @return Null object of deleted successfully
	 * @throws MessageException ,Exception
	 */
	
	@Override
	public Object deleteFromDB(AirShareDataBean airShareDataBean) throws MessageException, Exception {
		if (airShareDataBean.getComponentType() ==null) {
			logger.error("componentType found to be null");
			throw (new MessageException("componentType can't be null"));
		}
		if (airShareDataBean.getId() ==null) {
			logger.error("id found to be null");
			throw (new MessageException("id can't be null"));
		}
		IAirService airService = airServiceMap.get(airShareDataBean.getComponentType()+SUFFIX);

		return airService.deleteFromDB(airShareDataBean);
	}
	/**
	 * This method is used to find if the given user has aired the component.
	 * @param AirShareDataBean
	 * @return true if aired by user false if not
	 * @throws MessageException ,Exception
	 */
	@Override
	public boolean isComponentAiredByUser(AirShareDataBean airShareDataBean) throws MessageException, Exception {
		
		logger.info("Inside isAired");
		
		if (airShareDataBean.getComponentType() ==null) {
			logger.error("componentType found to be null");
			throw (new MessageException("componentType can't be null"));
		}
		
		if (airShareDataBean.getComponentId() ==null) {
			logger.error("componentid found to be null");
			throw (new MessageException("componentid can't be null"));
		}
		
		if (airShareDataBean.getUserId() ==null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}
		IAirService airService = airServiceMap.get(airShareDataBean.getComponentType()+SUFFIX);
		return airService.isComponentAiredByUser(airShareDataBean);

	}
	
	
	/**
	 * This method is used to find user list who aired the component.
	 * @param AirShareDataBean
	 * @return List of user sharing the component
	 * @throws MessageException ,Exception
	 */
	@Override
	public List<User> findUserListForComponent(AirShareDataBean airShareDataBean) throws MessageException, Exception {
		
		logger.info("Inside findUserListForComponent");
		if (airShareDataBean.getComponentType() ==null) {
			logger.error("componentType found to be null");
			throw (new MessageException("componentType can't be null"));
		}
		
		if (airShareDataBean.getComponentId() ==null) {
			logger.error("componentid found to be null");
			throw (new MessageException("componentid can't be null"));
		}
		IAirService airService = airServiceMap.get(airShareDataBean.getComponentType()+SUFFIX);
	
		
		List<User> users=  airService.findUserListForComponent(airShareDataBean);			
		logger.debug("Users fatched: " + users.size());
		return users;
	}


	/**
	 * This method is used to get the count airs on component.
	 * @param AirShareDataBean
	 * @return Count of airs 
	 * @throws MessageException ,Exception
	 */
	
	@Override
	public Long getAirCount(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {
		logger.info("Inside getAirCount");

		if (airShareDataBean.getComponentType() ==null) {
			logger.error("componentType found to be null");
			throw (new MessageException("componentType can't be null"));
		}
		@SuppressWarnings("rawtypes")
		IAirCountService airCountService = airCountServiceMap.get(airShareDataBean.getComponentType()+COUNT_SERVICE_SUFFIX);
		
		Long count =  (Long)airCountService.getAirCountByComponentId(airShareDataBean.getComponentId());
		logger.debug("Count fetched: " + count);
		return count;
	}

	
	
}
