package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.beans.FollowDataBean;
import com.ohmuk.folitics.businessDelegate.interfaces.IFollowBusinessDelegate;
import com.ohmuk.folitics.constants.Constants;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.notification.InterfaceNotificationService;
import com.ohmuk.folitics.notification.NotificationMapping;
import com.ohmuk.folitics.service.follow.IFollowCountService;
import com.ohmuk.folitics.service.follow.IFollowService;

@Component
public class FollowBusinessDelegate implements IFollowBusinessDelegate {

	Object objectToReturn = new Object();

	private Logger logger = LoggerFactory
			.getLogger(FollowBusinessDelegate.class);

	@SuppressWarnings("rawtypes")
	@Autowired
	private volatile Map<String, IFollowService> followServiceMap;

	@SuppressWarnings("rawtypes")
	@Autowired
	private volatile Map<String, IFollowCountService> followCountServiceMap;

	@Autowired
	private InterfaceNotificationService notificationService;

	@Override
	public Object followComponent(FollowDataBean followDataBean)
			throws MessageException, Exception {

		logger.info("Inside Follow BusinessDelegate followComponent");

		if (followDataBean.getComponentId() == null) {

			logger.error("Component id is null");
			throw (new MessageException("Component id is null"));

		}

		if (followDataBean.getUserId() == null) {

			logger.error("User Id has null value");
			throw (new MessageException("User Id has null value"));

		}

		if (followDataBean.getComponentType() == null) {

			logger.error("Componenttype is unspecified");
			throw (new MessageException("Componenttype is unspecified"));

		}

		logger.info("exiting followComponent()");
		logger.debug("Passing FollowData bean reference " + followDataBean
				+ " to Service");

		@SuppressWarnings("rawtypes")
		IFollowService iFollowService = followServiceMap.get(followDataBean
				.getComponentType() + FOLLOW_SUFFIXs);

		objectToReturn = iFollowService.create(followDataBean);

		NotificationMapping notificationMapping = new NotificationMapping();
		notificationMapping.setComponentId(followDataBean.getComponentId());
		notificationMapping.setComponentType(followDataBean.getComponentType());
		notificationMapping.setUserId(followDataBean.getUserId());
		notificationMapping.setAction("Follow");

		notificationService.followNotification(notificationMapping);

		return objectToReturn;

	}

	@Override
	public Object unfollowComponent(FollowDataBean followDataBean)
			throws MessageException, Exception {

		logger.info("Inside BusinessDelegate unfollow Component ");
		logger.debug("Unfollow request for ComponentId "
				+ followDataBean.getComponentId());

		if (followDataBean.getComponentId() == null) {

			logger.error("Component id is null");
			throw (new MessageException("Component id is null"));

		}

		if (followDataBean.getUserId() == null) {

			logger.error("User Id has null value");
			throw (new MessageException("User Id has null value"));

		}

		if (followDataBean.getComponentType() == null) {

			logger.error("Componenttype is unspecified");
			throw (new MessageException("Componenttype is unspecified"));

		}

		else {

			logger.debug("Passing instance  of Bean to service "
					+ followDataBean);

			IFollowService iFollowService = followServiceMap.get(followDataBean
					.getComponentType() + FOLLOW_SUFFIXs);

			if (iFollowService == null) {

				logger.error("No Such implementation found: "
						+ followDataBean.getComponentType() + "Service");
				throw (new MessageException("No Such implementation found: "
						+ followDataBean.getComponentType() + "Service"));
			}

			else {

				logger.info("Exiting unfollow Component");

				return iFollowService.update(followDataBean);

			}
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getFollowersSet(FollowDataBean followDataBean)
			throws MessageException, Exception {

		if (followDataBean.getComponentId() == null) {

			logger.error("Component id is null");
			throw (new MessageException("Component id is null"));

		}

		// if (followDataBean.getUserId() == null) {
		//
		// logger.error("User Id has null value");
		// throw (new MessageException("User Id has null value"));
		//
		// }

		if (followDataBean.getComponentType() == null) {

			logger.error("Componenttype is unspecified");
			throw (new MessageException("Componenttype is unspecified"));

		}

		@SuppressWarnings("rawtypes")
		IFollowService iFollowService = followServiceMap.get(followDataBean
				.getComponentType() + FOLLOW_SUFFIXs);

		return iFollowService.getFollowersForComponent(followDataBean);
	}

	@Override
	public Long getFollowCount(FollowDataBean followDataBean)
			throws MessageException, Exception {

		logger.info("Inside getFollowCount method");

		if (followDataBean.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId found to be null"));
		}

		@SuppressWarnings("rawtypes")
		IFollowCountService followCountService = followCountServiceMap
				.get(followDataBean.getComponentType()
						+ Constants.FOLLOW_COUNT_SERVICE_SUFFIX);

		Long followCount = followCountService.getByComponentId(followDataBean
				.getComponentId());

		return followCount;

	}

	@Override
	public boolean isFollowing(FollowDataBean followDataBean)
			throws MessageException, Exception {

		if (followDataBean.getComponentId() == null) {

			logger.error("Component id is null");
			throw (new MessageException("Component id is null"));

		}

		if (followDataBean.getUserId() == null) {

			logger.error("User Id has null value");
			throw (new MessageException("User Id has null value"));

		}

		if (followDataBean.getComponentType() == null) {

			logger.error("Componenttype is unspecified");
			throw (new MessageException("Componenttype is unspecified"));

		}

		IFollowService iFollowService = followServiceMap.get(followDataBean
				.getComponentType() + FOLLOW_SUFFIXs);

		return iFollowService.getFollowing(followDataBean);
	}

}
