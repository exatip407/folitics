package com.ohmuk.folitics.notification;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

/**
 * Implementation of {@link INotificationRepository} using in-memory Concurrent
 * HashMap
 * 
 * @author Harish Bagora
 */
@Component
public class MemoryNotificationRepository implements INotificationRepository {
	private static final Logger LOG = LoggerFactory
			.getLogger(INotificationRepository.class);
	private AtomicInteger ID_GENERATOR = new AtomicInteger(0);
	protected List<Notification> appMsgs = new CopyOnWriteArrayList<>();

	@Autowired
	protected StringRedisTemplate redisTemplate;

	protected String topicName = "appMsg";

	@Override
	public int count() {
		return this.appMsgs.size();
	}

	@Override
	public int deleteAll() {
		int count = appMsgs.size();
		LOG.info("Deleting all: {} messages", count);
		this.appMsgs.clear();
		LOG.info("Publishing change to Redis: {}", -1);

		// this.redisTemplate.convertAndSend(topicName, "-1");
		return count;
	}

	@Override
	public void createNotification(NotificationMapping notificationMapping) {
		LOG.info("Added new message: {}", notificationMapping.getMessage());

		// connection established
		Jedis jedis = new Jedis("localhost", 6379);

		Set<Long> userSet = notificationMapping.getSendingUsers();

		String channel = String.valueOf(userSet);

		// publish message
		jedis.publish(channel, notificationMapping.getMessage());
	}

	@Override
	public List<Notification> readAll() {
		LOG.debug("Reading all messages...");
		List<Notification> temp = Lists.newArrayList(this.appMsgs);
		Collections.sort(temp);
		return temp;
	}

	@Override
	public List<Notification> readSubset(Integer startId) {

		int size = this.appMsgs.size();
		LOG.info("Reading messages from {} - {}", startId, size);
		List<Notification> temp = Lists.newArrayList(this.appMsgs.subList(
				startId, size));
		Collections.sort(temp);
		return temp;
	}

	@Override
	public void createNotificationWithType(Notification notification)
			throws JsonProcessingException {
		LOG.info("Added new message: {}", notification.getMessage());

		// connection established
		Jedis jedis = new Jedis("localhost", 6379);

		Set<Long> userSet = notification.getSendingUsers();

		String channel = String.valueOf(userSet);
		ObjectMapper objectMapper = new ObjectMapper();
		String notificationJson = objectMapper.writeValueAsString(notification);

		// publish message
		jedis.publish(channel, notificationJson);

	}
}
