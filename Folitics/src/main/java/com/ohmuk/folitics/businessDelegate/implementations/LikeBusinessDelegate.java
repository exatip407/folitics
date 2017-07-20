package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.beans.LikeDataBean;
import com.ohmuk.folitics.businessDelegate.interfaces.ILikeBusinessDelegate;
import com.ohmuk.folitics.constants.Constants;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.like.LikeId;
import com.ohmuk.folitics.hibernate.service.like.ILikeCountService;
import com.ohmuk.folitics.hibernate.service.like.ILikeService;
import com.ohmuk.folitics.notification.InterfaceNotificationService;
import com.ohmuk.folitics.notification.NotificationMapping;

/**
 * @author Abhishek
 *
 */

@Component
public class LikeBusinessDelegate implements ILikeBusinessDelegate {

	private static Logger logger = LoggerFactory
			.getLogger(LikeBusinessDelegate.class);

	@SuppressWarnings("rawtypes")
	@Autowired
	private volatile Map<String, ILikeService> likeServiceMap;

	@SuppressWarnings("rawtypes")
	@Autowired
	private volatile Map<String, ILikeCountService> likeCountServiceMap;

	@Autowired
	private InterfaceNotificationService notificationService;

	@Override
	public Object getLikes(String componentType, Long id)
			throws MessageException {

		logger.info("Entered LikeBusinessDelegate getLikes method");

		Object object = new Object();

		logger.debug("Component type = " + componentType);

		// get the service based on the component type from the
		// likeCountServiceMap

		@SuppressWarnings("rawtypes")
		ILikeCountService countService = likeCountServiceMap.get(componentType
				+ Constants.LIKE_COUNT_SERVICE_SUFFIX);

		// get the object of likecount from database based on id
		object = countService.getByComponentId(id);

		return object;
	}

	@Override
	public Object like(Long componentId, String componentType, Long userId)
			throws MessageException, Exception {

		Object objectToReturn = new Object();

		// get the service based on the component type from the
		// likeServiceMap

		@SuppressWarnings("rawtypes")
		ILikeService service = likeServiceMap.get(componentType
				+ Constants.LIKE_SERVICE_SUFFIX);

		LikeDataBean likeDataBean = new LikeDataBean();
		likeDataBean.setComponentId(componentId);
		likeDataBean.setComponentType(componentType);
		likeDataBean.setUserId(userId);

		objectToReturn = service.create(likeDataBean);

		NotificationMapping notificationMapping = new NotificationMapping();
		notificationMapping.setComponentId(likeDataBean.getComponentId());
		notificationMapping.setComponentType(likeDataBean.getComponentType());
		notificationMapping.setUserId(likeDataBean.getUserId());
		notificationMapping.setAction("Like");

		notificationService.likeNotification(notificationMapping);

		return objectToReturn;
	}

	@Override
	public Object unlike(Long componentId, String componentType, Long userId)
			throws MessageException, Exception {

		Object objectToReturn = new Object();

		@SuppressWarnings("rawtypes")
		ILikeService service = likeServiceMap.get(componentType
				+ Constants.LIKE_SERVICE_SUFFIX);

		LikeDataBean likeDataBean = new LikeDataBean();
		likeDataBean.setComponentId(componentId);
		likeDataBean.setComponentType(componentType);
		likeDataBean.setUserId(userId);

		objectToReturn = service.unlike(likeDataBean);

		return objectToReturn;
	}

	@Override
	public Object dislike(Long componentId, String componentType, Long userId)
			throws MessageException, Exception {

		Object objectToReturn = new Object();

		@SuppressWarnings("rawtypes")
		ILikeService service = likeServiceMap.get(componentType
				+ Constants.LIKE_SERVICE_SUFFIX);

		LikeDataBean likeDataBean = new LikeDataBean();
		likeDataBean.setComponentId(componentId);
		likeDataBean.setComponentType(componentType);
		likeDataBean.setUserId(userId);

		objectToReturn = service.dislike(likeDataBean);

		NotificationMapping notificationMapping = new NotificationMapping();
		notificationMapping.setComponentId(likeDataBean.getComponentId());
		notificationMapping.setComponentType(likeDataBean.getComponentType());
		notificationMapping.setUserId(likeDataBean.getUserId());
		notificationMapping.setAction("Dislike");

		notificationService.dislikeNotification(notificationMapping);

		return objectToReturn;

	}

	@Override
	public Object undislike(Long componentId, String componentType, Long userId)
			throws MessageException, Exception {

		Object objectToReturn = new Object();

		@SuppressWarnings("rawtypes")
		ILikeService service = likeServiceMap.get(componentType
				+ Constants.LIKE_SERVICE_SUFFIX);

		LikeDataBean likeDataBean = new LikeDataBean();
		likeDataBean.setComponentId(componentId);
		likeDataBean.setComponentType(componentType);
		likeDataBean.setUserId(userId);

		objectToReturn = service.undislike(likeDataBean);

		return objectToReturn;
	}

	@Override
	public Object read(Long componentId, Long userId, String componentType)
			throws MessageException {

		Object objectToReturn = new Object();

		// get the service based on the component type from the
		// likeServiceMap

		@SuppressWarnings("rawtypes")
		ILikeService service = likeServiceMap.get(componentType
				+ Constants.LIKE_SERVICE_SUFFIX);

		LikeId likeId = new LikeId();
		likeId.setComponentId(componentId);
		likeId.setUserId(userId);

		objectToReturn = service.read(likeId);

		return objectToReturn;
	}

	@Override
	public Object delete(Long componentId, Long userId, String componentType)
			throws MessageException {

		Object objectToReturn = new Object();

		// get the service based on the component type from the
		// likeServiceMap

		@SuppressWarnings("rawtypes")
		ILikeService service = likeServiceMap.get(componentType
				+ Constants.LIKE_SERVICE_SUFFIX);

		LikeId likeId = new LikeId();
		likeId.setComponentId(componentId);
		likeId.setUserId(userId);

		// delete the entry for like
		objectToReturn = service.delete(likeId);

		return objectToReturn;
	}

}
