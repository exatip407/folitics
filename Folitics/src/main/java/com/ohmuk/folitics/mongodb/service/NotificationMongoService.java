package com.ohmuk.folitics.mongodb.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.enums.NotificationReadStatus;
import com.ohmuk.folitics.mongodb.dao.NotificationMongodbDAO;
import com.ohmuk.folitics.mongodb.entity.NotificationMongo;
import com.ohmuk.folitics.mongodb.repository.INotificationMongoRepository;

@Service
@Transactional
public class NotificationMongoService implements INotificationMongoService {

	@Autowired
	INotificationMongoRepository notificationMongoRepository;

	@Autowired
	NotificationMongodbDAO notificationMongodbDAO;

	@Override
	public NotificationMongo save(NotificationMongo notification) {

		notification = notificationMongoRepository.save(notification);
		return notification;
	}

	@Override
	public NotificationMongo read(String id) {

		NotificationMongo notification = notificationMongoRepository
				.findOne(id);
		return notification;
	}

	@Override
	public List<NotificationMongo> getUnreadNotificationForUser(Long userId,
			String notificationType) throws Exception {

		List<NotificationMongo> unreadNotifications = notificationMongodbDAO
				.findUnreadNotificationsForUser(userId, notificationType);

		return unreadNotifications;
	}

	@Override
	public List<NotificationMongo> getAll() {

		List<NotificationMongo> notifications = notificationMongoRepository
				.findAll();
		return notifications;
	}

	@Override
	public Boolean update(String notificationId) {

		NotificationMongo originalNotification = notificationMongoRepository
				.findOne(notificationId);

		if (originalNotification == null) {
			return null;
		}
		originalNotification.setReadStatus(NotificationReadStatus.READ
				.getValue());

		if (notificationMongoRepository.save(originalNotification) != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(NotificationMongo notification) {

		/*//BigInteger id = notification.getId();
		notificationMongoRepository.delete(notification);

		if (notificationMongoRepository.findOne(id) == null) {
			return true;
		} else {
			return false;
		}*/
		return false;
	}

	@Override
	public boolean delete(BigInteger id) {

		/*notificationMongoRepository.delete(id);

		if (notificationMongoRepository.findOne(id) == null) {
			return true;
		} else {
			return false;
		}*/
		return false;
	}

}
