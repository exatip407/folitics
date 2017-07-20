package com.ohmuk.folitics.notification;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.ohmuk.folitics.businessDelegate.interfaces.IUserBusinessDelegate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.mongodb.entity.NotificationMongo;
import com.ohmuk.folitics.mongodb.service.INotificationMongoService;

@RestController
public class NotificationControl {
	private static final Logger logger = LoggerFactory
			.getLogger(NotificationControl.class);

	@Autowired
	NotificationHandler notificatioHandler;

	@Autowired
	IUserBusinessDelegate notificationRepository;

	@Autowired
	INotificationMongoService notificationMongoService;

	private Long asyncTimeout = 6000l;

	/**
	 * Async
	 *
	 * @param userId
	 * @return all or a subset of {@code AppMsgs} (depending on {@code userId}
	 *         parameter
	 * @throws Exception
	 */
	@RequestMapping(value = "/appMsgsAsync", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody DeferredResult<List<String>> appMsgsAsync(Long userId)
			throws Exception {
		logger.debug("/appMsgsAsync......(userId=[{}])", userId);
		userId = userId == null ? 0 : userId;

		// create the deferred result with an empty collection in case of error,
		// no timeout is set
		final DeferredResult<List<String>> deferredResult = new DeferredResult<List<String>>(
				asyncTimeout, Collections.EMPTY_LIST);

		// If there are messages that have yet to be processed, no need to deal
		// with the pubsub - just get them. The client will
		// next have to make a new request that should immediately block.
		List<String> messages = this.notificationRepository
				.readNotifications(userId);
		if (messages != null) {
			deferredResult.setResult(messages);
		}
		// No new messages - let's wait for pubsub alert
		else {
			notificatioHandler.addAsyncRequest(deferredResult, userId);
		}

		return deferredResult;
	}

	/**
	 *
	 * @return a count of waiting async requests
	 */
	@RequestMapping(value = "/asyncRequests", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> asyncCount() {
		Integer count = this.notificatioHandler.count();
		logger.debug("Count of waiting async requests: {}", count);
		return new ResponseEntity<Integer>(count, HttpStatus.OK);
	}

	/**
	 * Web service is to get all unread notifications
	 * 
	 * @author Abhishek
	 * @param userId
	 * @return com.ohmuk.folitics.dto.ResponseDto
	 */
	@RequestMapping(value = "/getUnreadNotifications", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<NotificationMongo> getUnreadNotifications(
			Long userId, String notificationType) {
		logger.info("Inside NotificationControl.getUnreadNotifications method");
		try {
			if (null != userId) {
				List<NotificationMongo> unreadNotifications = notificationMongoService
						.getUnreadNotificationForUser(userId, notificationType);
				return new ResponseDto<NotificationMongo>(true,
						unreadNotifications);
			} else {
				return new ResponseDto<NotificationMongo>(true);
			}
		} catch (Exception exception) {
			logger.error("Exception in reading all unread notifications in NotificationControl.getUnreadNotifications with userId:"
					+ userId);
			logger.error("Exception: " + exception);
			logger.info("Exiting from NotificationControl.getUnreadNotifications method");
			return new ResponseDto<NotificationMongo>(false);
		}
	}

	@RequestMapping(value = "/updateNotificationreadStatus", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<NotificationMongo> updateNotificationreadStatus(
			String notificationMongoId) {
		logger.info("Inside NotificationControl.updateNotificationreadStatus method");
	//	BigInteger bigInteger = new BigDecimal(notificationMongoId).toBigInteger();
		try {
			if (null != notificationMongoId) {
				return new ResponseDto<NotificationMongo>(
						notificationMongoService.update(notificationMongoId));
			} else {
				return new ResponseDto<NotificationMongo>(false);
			}
		} catch (Exception exception) {
			logger.error("Exception in updating notification mongo db object in NotificationControl.updateNotificationreadStatus with id:"
					+ notificationMongoId);
			logger.error("Exception: " + exception);
			logger.info("Exiting from NotificationControl.updateNotificationreadStatus method");
			return new ResponseDto<NotificationMongo>(false);
		}
	}
}
