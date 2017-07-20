package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohmuk.folitics.businessDelegate.interfaces.IUserBusinessDelegate;
import com.ohmuk.folitics.enums.ConnectionStatusType;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Achievement;
import com.ohmuk.folitics.hibernate.entity.Leader;
import com.ohmuk.folitics.hibernate.entity.PoliticalView;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.UserAssociation;
import com.ohmuk.folitics.hibernate.entity.UserConnection;
import com.ohmuk.folitics.hibernate.entity.UserEducation;
import com.ohmuk.folitics.hibernate.entity.UserEmailNotificationSettings;
import com.ohmuk.folitics.hibernate.entity.UserImage;
import com.ohmuk.folitics.hibernate.entity.UserPrivacyData;
import com.ohmuk.folitics.hibernate.entity.UserUINotification;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.MaritalStatus;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Qualification;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.RegionState;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Religion;
import com.ohmuk.folitics.model.ImageModel;
import com.ohmuk.folitics.notification.InterfaceNotificationService;
import com.ohmuk.folitics.notification.NotificationMapping;
import com.ohmuk.folitics.service.IUserService;

@Component
@Transactional
public class UserBusinessDelegate implements IUserBusinessDelegate {

	private static Logger logger = LoggerFactory
			.getLogger(UserBusinessDelegate.class);

	@Autowired
	private volatile IUserService userService;

	@Autowired
	private volatile InterfaceNotificationService notificationService;
	private AtomicInteger ID_GENERATOR = new AtomicInteger(0);
	// protected List<Notification> appMsgs = new CopyOnWriteArrayList<>();

	private Map<Long, List<String>> userSubscribeMessageMap = new HashMap();
	public Map<Long, JedisPubSub> userUnsubscribeMap = new HashMap();

	@Override
	public User create(User user) throws Exception {
		logger.info("Inside  create method in business delegate");

		if (null != user.getUserAssociation()) {
			for (UserAssociation userAssociation : user.getUserAssociation()) {
				userAssociation.setUser(user);
			}
		}

		if (null != user.getUserEducation()) {
			for (UserEducation userEducation : user.getUserEducation()) {
				userEducation.setUser(user);
			}
		}

		if (null != user.getUserEmailNotificationSettings()) {
			for (UserEmailNotificationSettings emailNotificationSetting : user
					.getUserEmailNotificationSettings()) {
				emailNotificationSetting.setUser(user);
			}
		}

		if (null != user.getUserUINotification()) {
			for (UserUINotification userUINotification : user
					.getUserUINotification()) {
				userUINotification.setUser(user);
			}
		}
		User userData = userService.create(user);
		logger.info("Exiting  create method in business delegate");
		return userData;

	}

	@Override
	public User findUserById(Long id) throws Exception {
		logger.info("Inside findUserById method in business delegate");
		User userData = userService.findUserById(id);
		logger.info("Exiting findUserById method in business delegate");
		return userData;
	}

	@Override
	public List<User> readAll() throws Exception {
		logger.info("Inside readAll method in business delegate");
		List<User> usersList = userService.readAll();
		for (User user : usersList) {
			Hibernate.initialize(user.getUserEducation());
			Hibernate.initialize(user.getUserImage());
			Hibernate.initialize(user.getReligion());
		}

		logger.info("Exiting readAll method in business delegate");
		return usersList;
	}

	@Override
	public List<User> getAllRemainingUsers(Long id) throws Exception {
		logger.info("Inside getAllRemainingUsers method in business delegate");
		List<User> usersList = userService.getAllRemainingUsers(id);
		;
		for (User user : usersList) {
			Hibernate.initialize(user.getUserEducation());
			Hibernate.initialize(user.getUserImage());
		}

		logger.info("Exiting getAllRemainingUsers method in business delegate");
		return usersList;
	}

	@Override
	public User update(User user) throws Exception {
		logger.info("Inside update method in business delegate");
		User userData = userService.update(user);
		logger.info("Exiting update method in business delegate");
		return userData;
	}

	@Override
	public boolean delete(Long id) throws Exception {
		logger.info("Inside delete method in business delegate");
		boolean sucess = userService.delete(id);
		logger.info("Exiting delete method in business delegate");
		return sucess;
	}

	@Override
	public boolean delete(User user) throws Exception {
		logger.info("Inside delete method in business delegate");
		boolean sucess = userService.delete(user);
		logger.info("Exiting delete method in business delegate");
		return sucess;
	}

	@Override
	public boolean deleteFromDB(Long id) throws Exception {
		logger.info("Inside deleteFromDB method in business delegate");
		boolean sucess = userService.deleteFromDB(id);
		logger.info("Exiting deleteFromDB method in business delegate");
		return sucess;
	}

	@Override
	public boolean deleteFromDB(User user) throws Exception {
		logger.info("Inside deleteFromDB method in business delegate");
		boolean sucess = userService.deleteFromDB(user);
		logger.info("Exiting deleteFromDB method in business delegate");
		return sucess;
	}

	@Override
	public User resetPassword(Long id) throws Exception {
		logger.info("Inside  resetPassword method in business delegate");
		User user = findUserById(id);
		String newPassword = generateRandomPassword();
		user.setPassword(newPassword);
		User userWithNewPassword = userService.resetPassword(user);
		if (userWithNewPassword != null) {
			sendPasswordEmailToUser(userWithNewPassword);
			logger.info("Exiting  resetPassword method in business delegate");
			return userWithNewPassword;
		}
		logger.info("Exiting from UserService resetPassword method");
		return null;
	}

	public String generateRandomPassword() {
		final String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final Random rnd = new Random();
		StringBuilder sb = new StringBuilder(8);
		for (int i = 0; i < 8; i++)
			sb.append(characters.charAt(rnd.nextInt(characters.length())));
		return sb.toString();
	}

	private void sendPasswordEmailToUser(User userWithNewPassword) {
		final String username = "testfoliticsuser@gmail.com";
		final String password = "ohmukgroup2016";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		javax.mail.Session session = javax.mail.Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("testfoliticsuser@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(userWithNewPassword.getEmailId()));
			message.setSubject("Folitics : Reset Password");
			message.setText("Dear user," + "\n\n Your new password is "
					+ userWithNewPassword.getPassword());

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ImageModel getImageModel(Long id, boolean isThumbnail)
			throws Exception {
		User u = findUserById(id);
		ImageModel imageModel = getImageModel(u, isThumbnail);
		return imageModel;
	}

	private ImageModel getImageModel(User u, boolean isThumbnail) {
		ImageModel imageModel = new ImageModel();
		if (u != null) {
		}
		return imageModel;
	}

	public List<ImageModel> getImageModels(String entityIds, boolean isThumbnail)
			throws Exception {
		logger.info("Inside getImageModels method in business delegate");
		List<ImageModel> imageModel = userService.getImageModels(entityIds,
				isThumbnail);
		logger.info("Exiting getImageModels method in business delegate");
		return imageModel;
	}

	@Override
	public User uploadImage(MultipartFile file, Long userId) throws Exception {
		logger.info("Inside uploadImage  method in business delegate");
		User user = findUserById(userId);
		if (user != null) {
			String fileName = null;
			if (!file.isEmpty()) {
				try {
					UserImage userImage = new UserImage();
					userImage.setImage(file.getBytes());
					user.setUserImage(userImage);
					User userData = userService.update(user);
					logger.info("Exiting uploadImage  method in business delegate");
					return userData;

				} catch (Exception e) {
					logger.error(
							"You failed to upload " + fileName + ": "
									+ e.getMessage(), e);
				}
			} else {
				logger.debug("Unable to upload. File is empty.");
			}
		}
		return null;
	}

	@Override
	public User findByUsername(String username) throws Exception {
		logger.info("Inside findByUsername method in business delegate");
		User user = userService.findByUsername(username);
		if (user != null) {
			Hibernate.initialize(user.getUserImage());
		}
		logger.info("Exiting findByUsername method in business delegate");
		return user;
	}

	@Override
	public User getUserFromSession(HttpServletRequest request) throws Exception {
		logger.info("Inside userService getUserFromSession method");
		HttpSession httpSession = request.getSession();
		String userName = (String) httpSession.getAttribute("userName");
		System.out.println("username :" + httpSession.getAttribute("userName"));
		if (null != userName) {
			logger.debug("User is found with username: " + userName);
			User user = userService.findByUsername(userName);
			Hibernate.initialize(user.getUserImage());
			logger.info("Exiting from userService getUserFromSession method");
			return user;
		}
		logger.info("Exiting from userService getUserFromSession method");
		return null;
	}

	@Override
	public Long addConnection(Long userId, Long connecionId) throws Exception {
		logger.info("Inside UserBusinessDelegate addConnection method");
		User user = userService.findUserById(userId);
		User connection = userService.findUserById(connecionId);
		if (null != user && null != connection) {
			logger.debug("User and connection found with " + userId + " and"
					+ connecionId);
			UserConnection userConnection = new UserConnection();
			userConnection.setUserId(userId);
			userConnection.setConnectionStatus(ConnectionStatusType.PENDING
					.getValue());
			userConnection.setConnectionId(connecionId);

			Long connectionId = userService.addUserConnection(userConnection);
			if (connectionId != null) {
				NotificationMapping notificationMapping = new NotificationMapping();
				notificationMapping.setAction("connection");
				notificationMapping.setUserId(userId);
				notificationService.connectionNotification(notificationMapping);

			}
			logger.info("Exiting from UserBusinessDelegate addConnection method");
			return connectionId;
		}

		logger.debug("User and connection are not found");
		logger.info("Exiting from UserBusinessDelegate addConnection method");
		return null;
	}

	@Override
	public boolean deleteConnection(Long userId, Long connectionId)
			throws Exception {
		logger.info("Inside UserBusinessDelegate deleteConnection method");
		User user = userService.findUserById(userId);
		if (null != user) {
			logger.debug("User is found with id: " + userId);
			UserConnection userConnection = userService
					.findUserConnectionByUserAndConnectionId(userId,
							connectionId);
			userService.deleteUserConnection(userConnection);
			logger.info("Exiting from UserBusinessDelegate deleteConnection method");
			return true;
		}
		logger.info("Exiting from UserBusinessDelegate deleteConnection method");
		return false;
	}

	@Override
	public boolean updateConnectionStatus(Long userId, Long connectionId,
			String status) throws Exception {
		logger.info("Inside UserBusinessDelegate updateConnectionStatus method");
		if (null != userId & null != connectionId & null != status) {
			userService.updateConnectionStatus(userId, connectionId, status);
			logger.info("Exiting from UserBusinessDelegate updateConnectionStatus method");
			return true;
		}
		logger.info("Exiting from UserBusinessDelegate updateConnectionStatus method");
		return false;
	}

	@Override
	public List<User> getRequestConnection(Long connectionId, String status)
			throws Exception {
		logger.info("Inside UserBusinessDelegategetRequestConnection method");
		List<User> users = userService.getRequestConnection(connectionId,
				status);
		for (User user : users) {

			Hibernate.initialize(user.getUserImage());
		}

		logger.info("Exiting from UserBusinessDelegate getAllConnection method");
		return users;
	}

	@Override
	public List<User> getAllConnection(Long userId) throws Exception {
		logger.info("Inside UserBusinessDelegate getAllConnection method");
		List<User> users = userService.getAllConnection(userId);
		for (User user : users) {

			Hibernate.initialize(user.getUserImage());
		}
		logger.info("Exiting from UserBusinessDelegate getAllConnection method");
		return users;
	}

	@Override
	public Long saveUserUINotification(Long userId,
			UserUINotification userUINotification) throws Exception {
		logger.info("Inside UserBusinessDelegate saveUserUINotification method");
		User user = userService.findUserById(userId);
		userUINotification.setUser(user);
		Long uINotificationId = userService
				.saveUINotification(userUINotification);
		logger.info("Exiting from UserBusinessDelegate saveUserUINotification method");
		return uINotificationId;
	}

	@Override
	public boolean updateUserUINotification(
			UserUINotification userUINotification) throws Exception {
		logger.info("Inside UserBusinessDelegate updateUserUINotification method");
		boolean status = userService.updateUINotification(userUINotification);
		logger.info("Exiting from UserBusinessDelegate updateUserUINotification method");
		return status;
	}

	@Override
	public boolean blockUser(Long userId, Long blockUserId) throws Exception {
		logger.info("Inside UserBusinessDelegate blockUser method");
		boolean status = userService.blockUser(userId, blockUserId);
		logger.info("Exiting from UserBusinessDelegate blockUser method");
		return status;
	}

	@Override
	public boolean unBlockUser(Long userId, Long blockUserId) throws Exception {
		logger.info("Inside UserBusinessDelegate unBlockUser method");
		boolean status = userService.unBlockUser(userId, blockUserId);
		logger.info("Exiting from UserBusinessDelegate unBlockUser method");
		return status;
	}

	@Override
	public Long saveUserEmailNotificationSettings(Long userId,
			UserEmailNotificationSettings userEmailNotificationSettings)
			throws Exception {
		logger.info("Inside UserBusinessDelegate saveUserEmailNotificationSettings method");
		User user = userService.findUserById(userId);
		userEmailNotificationSettings.setUser(user);
		Long userENId = userService
				.saveUserEmailNotificationSettings(userEmailNotificationSettings);
		logger.info("Exiting from UserBusinessDelegate saveUserEmailNotificationSettings method");
		return userENId;
	}

	@Override
	public boolean updateUserEmailNotificationSettings(
			UserEmailNotificationSettings userEmailNotificationSettings)
			throws Exception {
		logger.info("Inside UserBusinessDelegate UpdateUserEmailNotificationSettings method");
		boolean status = userService
				.updateUserEmailNotificationSettings(userEmailNotificationSettings);
		logger.info("Exiting from UserBusinessDelegate UpdateUserEmailNotificationSettings method");
		return status;
	}

	@Override
	public List<UserEmailNotificationSettings> getAllUserEmailNotificationSettings(
			Long userId) throws Exception {
		logger.info("Inside UserBusinessDelegate getAllUserEmailNotificationSettings method");
		List<UserEmailNotificationSettings> users = userService
				.getAllUserEmailNotificationSettings(userId);
		logger.info("Exiting from UserBusinessDelegate getAllUserEmailNotificationSettings method");
		return users;
	}

	@Override
	public List<UserUINotification> getAllUserUINotification(Long userId)
			throws Exception {
		logger.info("Inside UserBusinessDelegate getAllUserUINotification method");
		List<UserUINotification> userUINotifications = userService
				.getAllUserUINotification(userId);
		logger.info("Exiting from UserBusinessDelegate getAllUserUINotification method");
		return userUINotifications;
	}

	@Override
	public Long saveUserPrivacySettings(Long userId,
			UserPrivacyData userPrivacySettings) throws Exception {
		logger.info("Inside UserBusinessDelegate saveUserPrivacySetting method");
		userPrivacySettings.setUserId(userId);
		Long userPSId = userService
				.saveUserPrivacySettings(userPrivacySettings);
		logger.info("Exiting from UserBusinessDelegate saveUserPrivacySetting method");
		return userPSId;
	}

	@Override
	public boolean updateUserPrivacySetting(UserPrivacyData userPrivacySettings)
			throws Exception {
		logger.info("Inside UserBusinessDelegate updateUserPrivacySetting method");
		boolean status = userService
				.updateUserPrivacySetting(userPrivacySettings);
		logger.info("Exiting from UserBusinessDelegate updateUserPrivacySetting method");
		return status;
	}

	@Override
	public List<UserPrivacyData> getAllUserPrivacySettings(Long userId)
			throws Exception {
		logger.info("Inside UserBusinessDelegate getAllUsePrivacySettings method");
		List<UserPrivacyData> userPrivacySettings;
		userPrivacySettings = userService.getAllUsePrivacySettings(userId);
		logger.info("Exiting from UserBusinessDelegate getAllUsePrivacySettings method");
		return userPrivacySettings;
	}

	@SuppressWarnings("unchecked")
	@Override
	public User save(User user) throws Exception {
		System.out.println("Inside deligate");
		user = create(user);
		Map<String, Object> map = new ObjectMapper().convertValue(
				user.getUserprofile(), Map.class);
		if (null != map) {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				UserPrivacyData userPrivacySettings = new UserPrivacyData();
				userPrivacySettings.setNotificationType("All");
				userPrivacySettings.setUserDataField(entry.getKey());
				if (null != entry.getValue()) {
					userPrivacySettings.setUserDataValue(entry.getValue()
							.toString());
				} else {
					userPrivacySettings.setUserDataValue("null");
				}
				userPrivacySettings.setUserId(user.getId());
				userService.saveUserPrivacySettings(userPrivacySettings);
			}
		}
		return user;
	}

	@Override
	public List<UserPrivacyData> viewUserPrivacyData(Long viewerId, Long userId)
			throws Exception {
		String viewerType = friendFoeAggrigation(viewerId, userId);
		List<UserPrivacyData> userPrivacyDatas = userService.getUserProfile(
				userId, viewerType);
		return userPrivacyDatas;
	}

	@Override
	public String friendFoeAggrigation(Long viewerId, Long userId)
			throws Exception {
		return "Friends";
	}

	@Override
	public Map<String, List<User>> getAntiProConnection(Long userId)
			throws Exception {
		logger.info("Inside UserBusinessDelegate getantiProConnection method");
		Map<String, List<User>> map = (Map<String, List<User>>) userService
				.getAntiProConnection(userId);

		logger.info("Exiting from UserBusinessDelegate getAntiProConnection method");
		return map;
	}

	@Override
	public JedisPubSub subscribe(Long userId) throws MessageException,
			Exception {

		logger.info("Inside subscribe method in userBusinessDelegate");
		if (userId == null) {
			logger.error("UserId can't be null");
			throw new MessageException("UserId can't be null");

		}
		logger.debug("Redis subscribe for userId:-" + userId);

		JedisPubSub jedisPubSub = setupSubscriber(userId);
		userUnsubscribeMap.put(userId, jedisPubSub);

		logger.info("Exiting subscribe method from userBusinessDelegate");
		return jedisPubSub;

	}

	private JedisPubSub setupSubscriber(Long userId) {

		final JedisPubSub jedisPubSub = new JedisPubSub() {
			@Override
			public void onUnsubscribe(String channel, int subscribedChannels) {

			}

			@Override
			public void onSubscribe(String channel, int subscribedChannels) {

			}

			@Override
			public void onPUnsubscribe(String pattern, int subscribedChannels) {

			}

			@Override
			public void onPSubscribe(String pattern, int subscribedChannels) {

			}

			@Override
			public void onPMessage(String pattern, String channel,
					String message) {
				// messageContainer.add(message);
				// Integer id = ID_GENERATOR.getAndIncrement();
				// Notification notification = new Notification(id, message);
				List<String> messages = new ArrayList<String>();
				messages.add(message);
				userSubscribeMessageMap.put(userId, messages);

				logger.info("Added new message: {}", message);

				// appMsgs.add(notification);

				log("Message received - " + message);
				// messageReceivedLatch.countDown();

			}

			@Override
			public void onMessage(String channel, String message) {

			}
		};
		new Thread(new Runnable() {
			public void run() {
				try {
					log("Connecting");
					Jedis jedis = new Jedis("localhost", 6379);
					log("subscribing");
					String pattern = "*" + userId + "*";
					jedis.psubscribe(jedisPubSub, pattern);
					log("subscribe returned, closing down");
					jedis.quit();
				} catch (Exception e) {
					log(">>> OH NOES Sub - " + e.getMessage());
					// e.printStackTrace();
				}
			}
		}, "subscriberThread").start();
		return jedisPubSub;
	}

	static final long startMillis = System.currentTimeMillis();

	private static void log(String string, Object... args) {
		long millisSinceStart = System.currentTimeMillis() - startMillis;
		System.out.printf("%20s %6d %s\n", Thread.currentThread().getName(),
				millisSinceStart, String.format(string, args));
	}

	public List<String> readNotifications(Long userId) throws Exception {

		logger.info("Inside readNotifications method in userBusinessDelegate");
		logger.debug("Notifications fetch for userId:-" + userId);

		List<String> messages = userSubscribeMessageMap.get(userId);
		userSubscribeMessageMap.remove(userId);

		logger.info("Exiting readNotifications method from userBusinessDelegate");
		return messages;
		// return userMap;

		/*
		 * int size = this.appMsgs.size(); logger.info(
		 * "Reading messages from {} - {}", startId, size); List<Notification>
		 * temp = Lists.newArrayList(this.appMsgs.subList( startId, size));
		 * Collections.sort(temp); return temp;
		 */
	}

	@Override
	public void unSubscribe(Long userId) throws MessageException, Exception {

		logger.info("Inside unSubscribe method in userBusinessDelegate");
		if (userId == null) {
			logger.error("UserId can't be null");
			throw new MessageException("UserId can't be null");

		}
		logger.debug("Redis unSubscribe for userId:-" + userId);
		JedisPubSub jedisPubSub = userUnsubscribeMap.get(userId);
		jedisPubSub.unsubscribe();
		logger.info("Exiting unSubscribe method in userBusinessDelegate");

	}

	@Override
	public List<Religion> getAllReligion() {
		// TODO Auto-generated method stub
		return userService.getReligion();
	}

	@Override
	public List<RegionState> getAllState() throws Exception {
		// TODO Auto-generated method stub
		return userService.getState();
	}

	@Override
	public List<MaritalStatus> getAllMaritalStatus() throws Exception {
		// TODO Auto-generated method stub
		return userService.getMaritalStatus();
	}

	@Override
	public List<Qualification> getAllQualification() throws Exception {
		// TODO Auto-generated method stub
		return userService.getQualification();
	}

	@Override
	public UserConnection connections(Long userId, Long connecionId)
			throws Exception {
		logger.info("Inside findUserById method in business delegate");
		UserConnection userConnection = userService.connections(userId,
				connecionId);
		logger.info("Exiting findUserById method in business delegate");
		return userConnection;
	}

	@Override
	public boolean verifyIfUsernameExist(String username) throws Exception {
		logger.info("Inside verifyIfUsernameExist method in business delegate");
		boolean result = userService.verifyIfUsernameExist(username);
		logger.info("Exiting verifyIfUsernameExist method in business delegate");
		return result;
	}

	@Override
	public Long add(Achievement achievement) throws Exception {
		logger.info("Inside add achievement method in business delegate");
		Long id = userService.add(achievement);
		logger.info("Exiting add achievement method in business delegate");
		return id;
	}

	@Override
	public Long add(Leader leader) throws Exception {
		logger.info("Inside add leader method in business delegate");
		Long id = userService.add(leader);
		logger.info("Exiting add leader method in business delegate");
		return id;
	}

	@Override
	public Long add(PoliticalView politicalView) throws Exception {
		logger.info("Inside add politicalView method in business delegate");
		Long id = userService.add(politicalView);
		logger.info("Exiting add politicalView method in business delegate");
		return id;
	}

	@Override
	public List<Achievement> getAchievement(Long userId) throws Exception {
		logger.info("Inside add achievement method in business delegate");
		List<Achievement> achievement = userService.getAchievement(userId);
		logger.info("Exiting add achievement method in business delegate");
		return achievement;
	}

	@Override
	public List<Leader> getLeader(Long userId) throws Exception {
		logger.info("Inside add leader method in business delegate");
		List<Leader> leader = userService.getLeader(userId);
		logger.info("Exiting add leader method in business delegate");
		return leader;
	}

	@Override
	public List<PoliticalView> getPoliticalView(Long userId) throws Exception {
		logger.info("Inside add politicalView method in business delegate");
		List<PoliticalView> politicalView = userService
				.getPoliticalView(userId);
		logger.info("Exiting add politicalView method in business delegate");
		return politicalView;
	}

}
