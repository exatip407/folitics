package com.ohmuk.folitics.mongodb.service;

import java.math.BigInteger;
import java.util.List;

import com.ohmuk.folitics.mongodb.entity.NotificationMongo;

public interface INotificationMongoService {

	/**
	 * This method is for saving the object of {@link NotificationMongo} in
	 * mongodb
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.mongodb.entity.Notification the object of
	 *            {@link NotificationMongo} that is to be saved in the mongodb
	 * @return com.ohmuk.folitics.mongodb.entity.Notification the saved object
	 *         of {@link NotificationMongo} from mongodb
	 */
	public NotificationMongo save(NotificationMongo notification);

	/**
	 * This method is for getting object of {@link NotificationMongo} from
	 * mongodb with given id
	 * 
	 * @author Abhishek
	 * @param java
	 *            .math.BigInteger id of the object of {@link NotificationMongo}
	 *            that is to be searched in mongodb
	 * @return com.ohmuk.folitics.mongodb.entity.Notification the object of
	 *         {@link NotificationMongo} found in mongodb
	 */
	public NotificationMongo read(String id);

	/**
	 * This method is for getting all unread notifications for User from mongodb
	 * 
	 * @author Abhishek
	 * @param java
	 *            .lang.Long user id for which unread notifications are required
	 * @return java.util.List<com.ohmuk.folitics.mongodb.entity.Notification>
	 *         the list of unread notifications for given user
	 * @throws Exception
	 */
	public List<NotificationMongo> getUnreadNotificationForUser(Long userId,String notificationType)
			throws Exception;

	/**
	 * This method is for getting all the notifications from mongodb
	 * 
	 * @author Abhishek
	 * @return java.util.List<com.ohmuk.folitics.mongodb.entity.Notification>
	 *         the list of all the notifications from mongodb
	 */
	public List<NotificationMongo> getAll();

	/**
	 * This method is for updating the object of {@link NotificationMongo} in
	 * mongodb
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.mongodb.entity.Notification the object of
	 *            {@link NotificationMongo} that is to be updated in mongodb
	 * @return com.ohmuk.folitics.mongodb.entity.Notification the updated object
	 *         of {@link NotificationMongo} from mongodb
	 */
	public Boolean update(String notificationId);

	/**
	 * This method is for deleting the object of {@link NotificationMongo} from
	 * mongodb
	 * 
	 * @author Abhishek
	 * @param com
	 *            .ohmuk.folitics.mongodb.entity.Notification the object of
	 *            {@link NotificationMongo} that is to be deleted from mongodb
	 * @return boolean true if object is successfully deleted from database else
	 *         false
	 */
	public boolean delete(NotificationMongo notification);

	/**
	 * This method is for deleting the object of {@link NotificationMongo} from
	 * mongodb
	 * 
	 * @author Abhishek
	 * @param java
	 *            .math.BigInteger id of the object that is to be deleted from
	 *            database
	 * @return boolean true if object is successfully deleted from database else
	 *         false
	 */
	public boolean delete(BigInteger id);
}
