package com.ohmuk.folitics.notification;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import com.ohmuk.folitics.businessDelegate.interfaces.IUserBusinessDelegate;

/**
 * @author Harish
 */
@Component("notificationHandler")
public class NotificationHandler implements MessageListener {

	// private CountDownLatch messageReceivedLatch = new CountDownLatch(1);

	protected List<Notification> appMsgs = new CopyOnWriteArrayList<>();

	// private AtomicInteger ID_GENERATOR = new AtomicInteger(0);
	@Autowired
	private INotificationRepository notificationRepository;

	private static final Logger logger = LoggerFactory
			.getLogger(NotificationHandler.class);
	private final static Charset UTF8 = StandardCharsets.UTF_8;

	private ConcurrentHashMap<DeferredResult<List<String>>, Long> waitingRequests = new ConcurrentHashMap<>();
	private IUserBusinessDelegate appMsgRepo;

	public void clear() {
		this.waitingRequests.clear();
	}

	/**
	 * @param startId
	 *            the start index for which messages should be returned
	 * @throws InterruptedException
	 */
	public void addAsyncRequest(
			final DeferredResult<List<String>> deferredResult, Long userId)
			throws InterruptedException {

		// add the deferred result to the map of waiting requests. The {@code
		// AppMsgHandler} will set the result when a message
		// ping is encountered from Redis.
		deferredResult.onTimeout(new Runnable() {
			@Override
			public void run() {
				logger.debug("Request timed out (returning empty list.");
				waitingRequests.remove(deferredResult);
				deferredResult.setResult(null);

			}
		});

		deferredResult.onCompletion(new Runnable() {
			@Override
			public void run() {
				logger.debug("Request completed");
				waitingRequests.remove(deferredResult);
				deferredResult.setResult(null);
			}
		});
		this.waitingRequests.put(deferredResult, userId);

		// JedisPubSub jedisPubSub = setupSubscriber(userId);

		// messageReceivedLatch.await();

		// jedisPubSub.unsubscribe();

	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		logger.info("RedisPub: {} on Channel: {}", new String(
				message.getBody(), UTF8),
				new String(message.getChannel(), UTF8));
		Iterator<Map.Entry<DeferredResult<List<String>>, Long>> it = waitingRequests
				.entrySet().iterator();
		try {
			while (it.hasNext()) {
				Map.Entry<DeferredResult<List<String>>, Long> entry = it.next();
				List<String> messages = appMsgRepo.readNotifications(entry
						.getValue());
				entry.getKey().setResult(messages);
				it.remove();
			}
		} catch (Exception e) {
			logger.error("Exception while set notification on deffered result object in notification handler class "
					+ e.getMessage());
			e.printStackTrace();
		}
	}

	public int count() {
		return this.waitingRequests.size();
	}

	

}
