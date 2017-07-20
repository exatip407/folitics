package com.ohmuk.folitics.notification;

import java.util.List;

/**
 * @author Harish Bagora
 */
public interface INotificationRepository {

	/**
	 * @return count of all messages
	 */
	public int count();

	/**
	 * Delete all messages
	 * 
	 * @return count of deleted messages
	 */
	public int deleteAll();

	/**
	 * Create a new message. The id and timeStamp will be set by the backend
	 * 
	 * @param msg
	 * @return the newly created {@link Notification AppMsg} with all attributes
	 *         set.
	 */
	public void createNotification(NotificationMapping notificationMapping);

	/**
	 * @return a sorted list of all @{link AppMsg AppMsgs}
	 */
	public List<Notification> readAll();

	/**
	 *
	 * @param startId
	 *            the start index of the messages to read
	 * @return subset of all messages, beginning with {@code startId} param
	 *         inclusive
	 */
	public List<Notification> readSubset(Integer startId);

	public void createNotificationWithType(Notification notification)throws Exception;
}
