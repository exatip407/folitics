package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.beans.AirShareDataBean;
import com.ohmuk.folitics.businessDelegate.interfaces.IShareBusinessDeligate;
import com.ohmuk.folitics.constants.Constants;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.share.SentimentShare;
import com.ohmuk.folitics.hibernate.service.share.IShareCountService;
import com.ohmuk.folitics.hibernate.service.share.IShareService;
import com.ohmuk.folitics.hibernate.service.share.TaskShareService;

@Component
public class ShareBusinessDeligate implements IShareBusinessDeligate {
	private static Logger logger = LoggerFactory
			.getLogger(ShareBusinessDeligate.class);

	@SuppressWarnings("rawtypes")
	@Autowired
	private volatile Map<String, IShareService> shareServiceMap;

	@SuppressWarnings("rawtypes")
	@Autowired
	private volatile Map<String, IShareCountService> shareCountServiceMap;

	@Override
	public Object create(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {

		Object object = new Object();

		// get the service based on the component type from the
		// shareServiceMap

		@SuppressWarnings("rawtypes")
		IShareService shareService = shareServiceMap.get(airShareDataBean
				.getComponentType() + Constants.SHARE_SERVICE_SUFFIX);

		// save the newly created object in database
		object = shareService.create(airShareDataBean);
		return object;

	}

	@Override
	public Object read(Long id, String componentType) throws MessageException {

		Object object = new Object();

		@SuppressWarnings("unchecked")
		IShareService<SentimentShare> shareService = shareServiceMap
				.get(componentType + Constants.SHARE_SERVICE_SUFFIX);

		object = shareService.read(id);

		return object;
	}

	@Override
	public List<Object> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object update(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {

		Object object = new Object();

		@SuppressWarnings("unchecked")
		IShareService<SentimentShare> shareService = shareServiceMap
				.get(airShareDataBean.getComponentType()
						+ Constants.SHARE_SERVICE_SUFFIX);

		object = shareService.update(airShareDataBean);

		return object;
	}

	@Override
	public Object delete(Long id, String componentType)
			throws MessageException, Exception {

		Object object = new Object(); 

		@SuppressWarnings("rawtypes")
		IShareService shareService = shareServiceMap.get(componentType
				+ Constants.SHARE_SERVICE_SUFFIX);

		object = shareService.delete(id);

		return object;
	}

	@Override
	public boolean isComponentSharedByUser(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {

		logger.info("Inside isShared");

		if (airShareDataBean.getUserId() == null) {
			logger.error("Share ID found to be null");
			throw (new MessageException("Share can't be null"));
		}

		if (airShareDataBean.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		boolean componentSharedByUser = false;

		@SuppressWarnings("rawtypes")
		IShareService shareService = shareServiceMap.get(airShareDataBean
				.getComponentType() + Constants.SHARE_SERVICE_SUFFIX);

		componentSharedByUser = shareService
				.isComponentSharedByUser(airShareDataBean);

		return componentSharedByUser;

	}

	/**
	 * This method is used to read the record by id.
	 * 
	 * @param id
	 * @return share : Read Share
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUserListForComponent(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {

		logger.info("Inside findUserListForComponent");

		if (airShareDataBean.getComponentType() == null) {
			logger.error("componentType found to be null");
			throw (new MessageException("componentType can't be null"));
		}
		if (airShareDataBean.getComponentId() == null) {
			logger.error("ComponentId found to be null");
			throw (new MessageException("ComponentId can't be null"));
		}

		List<User> users = new ArrayList<User>();

		@SuppressWarnings("rawtypes")
		IShareService shareService = shareServiceMap.get(airShareDataBean
				.getComponentType() + Constants.SHARE_SERVICE_SUFFIX);

		users = shareService.findUserListForComponent(airShareDataBean);

		logger.debug("Share fatched: " + users.size());

		return users;
	}

	@Override
	public Object getShareCount(String componentType, Long componentId)
			throws MessageException {

		logger.info("Inside getShareCount");

		if (componentType == null) {
			logger.error("componentType found to be null");
			throw (new MessageException("componentType can't be null"));
		}

		Object object = new Object();

		@SuppressWarnings("rawtypes")
		IShareCountService shareCountService = shareCountServiceMap
				.get(componentType + Constants.SHARE_COUNT_SERVICE_SUFFIX);

		object = shareCountService.getByComponentId(componentId);

		return object;
	}

}
