package com.ohmuk.folitics.notification;

import com.ohmuk.folitics.hibernate.entity.Response;

/**
 * @author Harish Bagora
 *
 */
public interface InterfaceNotificationService {

	/**
	 * This method is used to generate notification while user follow on any
	 * component
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.notification.NotificationMapping
	 * 
	 */

	public void followNotification(NotificationMapping notificationMapping)
			throws Exception;

	/**
	 * This method is used to generate notification while user like on any
	 * component
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.notification.NotificationMapping
	 * 
	 */

	public void likeNotification(NotificationMapping notificationMapping)
			throws Exception;

	/**
	 * This method is used to generate notification while user dislike on any
	 * component
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.notification.NotificationMapping
	 * 
	 */
	public void dislikeNotification(NotificationMapping notificationMapping)
			throws Exception;

	/**
	 * This method is used to generate notification while user air on any
	 * component
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.notification.NotificationMapping
	 * 
	 */
	public void airNotification(NotificationMapping notificationMapping)
			throws Exception;

	/**
	 * This method is used to generate notification while user share on any
	 * component
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.notification.NotificationMapping
	 * 
	 */
	public void shareNotification(NotificationMapping notificationMapping)
			throws Exception;

	/**
	 * This method is used to generate notification while user give response on
	 * any opinion
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.notification.NotificationMapping
	 * 
	 */
	public void responseNotification(NotificationMapping notificationMapping,
			Response response) throws Exception;

	/**
	 * This method is used to generate notification when task creator added
	 * users in his task
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.notification.NotificationMapping
	 * 
	 */
	public void taskNotification(NotificationMapping notificationMapping)
			throws Exception;

	/**
	 * This method is used to generate notification while user participate in
	 * problem
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.notification.NotificationMapping
	 * 
	 */

	public void participateNotification(NotificationMapping notificationMapping)
			throws Exception;

	/**
	 * This method is used to generate notification while user perform any
	 * action in system
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.notification.NotificationMapping
	 * 
	 */
	public void monetizationNotification(NotificationMapping notificationMapping)
			throws Exception;

	/**
	 * This method is used to generate notification while user create opinion on
	 * any sentiment
	 * 
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.notification.NotificationMapping
	 * 
	 */
	public void opinionNotification(NotificationMapping notificationMapping,
			Long sentiemntId) throws Exception;

	/**
	 * This method is used to generate notification while user participate in
	 * problem
	 * 
	 * @author Harish
	 * @param com
	 *            .ohmuk.folitics.notification.NotificationMapping
	 * 
	 */
	public void connectionNotification(NotificationMapping notificationMapping)
			throws Exception;

}
