package com.ohmuk.folitics.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.constants.Constants;
import com.ohmuk.folitics.elasticsearch.service.IESService;
import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Achievement;
import com.ohmuk.folitics.hibernate.entity.BlockId;
import com.ohmuk.folitics.hibernate.entity.Leader;
import com.ohmuk.folitics.hibernate.entity.PoliticalView;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.UserBlockSettings;
import com.ohmuk.folitics.hibernate.entity.UserConnection;
import com.ohmuk.folitics.hibernate.entity.UserEmailNotificationSettings;
import com.ohmuk.folitics.hibernate.entity.UserPrivacyData;
import com.ohmuk.folitics.hibernate.entity.UserProfile;
import com.ohmuk.folitics.hibernate.entity.UserUINotification;
import com.ohmuk.folitics.hibernate.entity.task.Department;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.MaritalStatus;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Qualification;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.RegionState;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Religion;
import com.ohmuk.folitics.model.ImageModel;
import com.ohmuk.folitics.util.ElasticSearchUtils;
import com.ohmuk.folitics.util.ImageUtil;
import com.ohmuk.folitics.util.Serialize_JSON;

/**
 * @author Abhishek
 *
 */
@Service
@Transactional
public class UserService implements IUserService {

	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private SessionFactory _sessionFactory;

	@Autowired
	IESService esService;

	private Session getSession() {
		return _sessionFactory.getCurrentSession();
	}

	@Override
	public User create(User user) throws Exception {
		logger.info("Inside user service create method");

		Long userId = (Long) getSession().save(user);
		if (Constants.useElasticSearch)
			esService.save(ElasticSearchUtils.INDEX, ElasticSearchUtils.TYPE_USER, String.valueOf(userId),
					Serialize_JSON.getJSONString(user));

		logger.debug("User is added with username: " + user.getUsername());
		logger.info("Exiting from UserService create method");
		return findUserById(userId);
	}

	@Override
	public User findUserById(Long id) throws Exception {
		logger.info("Inside UserService findUserById method");
		User user = (User) getSession().get(User.class, id);
		Hibernate.initialize(user.getUserImage());
		if (null != user) {
			logger.debug("User found with id: " + id);
			logger.info("Exiting from UserService findUserById method");
			return user;
		}
		logger.debug("No user found with id: " + id);
		logger.info("Exiting from UserService findUserById method");
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> readAll() throws Exception {
		logger.info("Inside UserService readAll method");
		List<User> users = getSession().createCriteria(User.class).list();
		logger.info("Exiting from UserService readAll method");
		return users;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> getAllRemainingUsers(Long id) throws Exception {
		logger.info("Inside UserService getAllRemainingUsers method");

		List<User> users = getSession().createCriteria(User.class).list();
		List<User> remainingUsers = new ArrayList();
		for (User user : users) {
			if (user.getId() != id) {
				remainingUsers.add(user);
			}

		}
		logger.info("Exiting from UserService getAllRemainingUsers method");
		return remainingUsers;
	}

	@Override
	public User update(User user) throws Exception {
		logger.info("Inside UserService update method");
		getSession().update(user);
		logger.info("Exiting from UserService update method");	
		return user;
	}

	@Override
	public boolean delete(Long id) throws Exception {
		logger.info("Inside UserService delete method");

		Query query = getSession().createQuery("update User set status=:s where id=:i");
		query.setString("s", ComponentState.DELETED.getValue());
		query.setLong("i", id);

		int result = query.executeUpdate();
		if (result > 0) {
			logger.info("Exiting from UserService delete method");
			return true;
		}

		logger.info("Exiting from UserService delete method");
		return false;
	}

	@Override
	public boolean delete(User user) throws Exception {
		logger.info("Inside UserService delete method");
		Query query = getSession().createQuery("update User set status=:s where id=:i");
		query.setString("s", ComponentState.DELETED.getValue());
		query.setLong("i", user.getId());

		int result = query.executeUpdate();
		if (result > 0) {
			logger.info("Exiting from UserService delete method");
			return true;
		}
		logger.info("Exiting from UserService delete method");
		return false;

	}

	@Override
	public boolean deleteFromDB(Long id) throws Exception {
		logger.info("Inside UserService readAll method");
		User user = findUserById(id);
		if (null != user) {
			getSession().delete(user);
			logger.info("Exiting from UserService deleteFromDB method");
			return true;
		}
		logger.info("Exiting from UserService deleteFromDB method");
		return false;
	}

	@Override
	public boolean deleteFromDB(User user) throws Exception {
		logger.info("Inside UserService deleteFromDB method");
		boolean status = deleteFromDB(user.getId());
		logger.info("Exiting from UserService deleteFromDB method");
		return status;
	}

	@Override
	public User resetPassword(User user) throws Exception {
		logger.info("Inside UserService resetPassword method");

		// User userWithNewPassword = (User) getSession().save(user);
		Long userId = (Long) getSession().save(user);
		User userWithNewPassword = (User) getSession().get(User.class, userId);

		logger.info("Exiting from UserService resetPassword method");
		return userWithNewPassword;

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

		javax.mail.Session session = javax.mail.Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("testfoliticsuser@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userWithNewPassword.getEmailId()));
			message.setSubject("Folitics : Reset Password");
			message.setText("Dear user," + "\n\n Your new password is " + userWithNewPassword.getPassword());

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ImageModel getImageModel(Long id, boolean isThumbnail) throws Exception {
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

	@Override
	public List<ImageModel> getImageModels(String entityIds, boolean isThumbnail) {
		List<ImageModel> imageModels = new ArrayList<ImageModel>();
		Query query = getSession().createQuery("from User WHERE id IN: ids");
		query.setParameter("ids", ImageUtil.getListOfIds(entityIds));
		List<User> users = query.list();
		for (User user : users) {
			imageModels.add(getImageModel(user, isThumbnail));
		}
		return imageModels;

	}

	@Override
	public User uploadImage(User user) throws Exception {
		logger.info("Inside UserService readAll method");

		getSession().saveOrUpdate(user);
		logger.info("Exiting UserService readAll method");
		return findUserById(user.getId());

	}

	@Override
	public User findByUsername(String username) throws Exception {
		logger.info("Inside UserService readAll method");
		User user = null;
		try {
			Criteria criteria = getSession().createCriteria(User.class);

			criteria.add(Restrictions.eq("username", username));
			user = (User) criteria.uniqueResult();
		} catch (Exception exception) {
			logger.error("Exception in find user with username: " + username);
		}

		if (null != user) {
			logger.debug("User found with username: " + username);
			return user;
		} else {
			logger.debug("No user found with username: " + username);
			return null;
		}
	}

	/**
	 * This method will return user object with only essential information and
	 * other field rename null
	 * 
	 * @author Mayank Sharma
	 * @param id
	 * @return User
	 */
	private User findUserEssentialInformationById(Long id) {
		logger.info("Inside UserService readAll method");
		Criteria cr = getSession().createCriteria(User.class)
				.setProjection(Projections.projectionList().add(Projections.property("id"), "id")
						.add(Projections.property("mobileNo"), "mobileNo")
						.add(Projections.property("emailId"), "emailId").add(Projections.property("name"), "Name"))
				.add(Restrictions.eq("id", id)).setResultTransformer(Transformers.aliasToBean(User.class));

		User user = (User) cr.uniqueResult();
		if (null != user) {
			logger.debug("User found with id: " + id);
			logger.info("Exiting from UserService readAll method");
			return user;
		}
		logger.debug("No user found with id: " + id);
		logger.info("Exiting from UserService readAll method");
		return null;
	}

	@Override
	public Long addUserConnection(UserConnection userConnection) throws Exception {
		logger.info("Inside UserService addUserConnection method");
		Long connectionId = (Long) getSession().save(userConnection);
		logger.info("Exiting from UserService addUserConnection method");
		return connectionId;
	}

	@Override
	public boolean deleteUserConnection(UserConnection userConnection) throws Exception {
		logger.info("Inside UserService deleteUserConnection method");
		getSession().delete(userConnection);
		logger.info("Exiting from UserService deleteUserConnection method");
		return true;
	}

	@Override
	public UserConnection findUserConnectionByUserAndConnectionId(Long userId, Long connectionId) throws Exception {
		logger.info("Inside UserService findUserConnectionByUserAndConnectionId method");
		Criteria criteria = getSession().createCriteria(UserConnection.class).add(Restrictions.eq("userId", userId))
				.add(Restrictions.eq("connectionId", connectionId));
		logger.info("Exiting from UserService findUserConnectionByUserAndConnectionId method");
		return (UserConnection) criteria.uniqueResult();
	}

	@Override
	public boolean updateConnectionStatus(Long userId, Long connectionId, String status) throws Exception {
		logger.info("Inside UserService updateConnectionStatus method");
		UserConnection userConnection = findUserConnectionByUserAndConnectionId(userId, connectionId);
		userConnection.setConnectionStatus(status);
		boolean updateStatus = updateUserConnection(userConnection);
		logger.info("Exiting from UserService updateConnectionStatus method");
		return updateStatus;
	}

	@Override
	public boolean updateUserConnection(UserConnection userConnection) throws Exception {
		logger.info("Inside UserService updateUserConnection method");
		getSession().update(userConnection);
		logger.info("Exiting from UserService updateUserConnection method");
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> getRequestConnection(Long connectionId, String status) throws Exception {
		logger.info("Inside UserService getRequestConnection method");
		List<User> userConnection = new ArrayList<User>();
		Criteria criteriaUserConnection = getSession().createCriteria(UserConnection.class)
				.add(Restrictions.eq("connectionId", connectionId)).add(Restrictions.eq("connectionStatus", status))
				.setProjection(Projections.property("userId"));
		/*
		 * Criteria criteriaConnection =
		 * getSession().createCriteria(UserConnection.class)
		 * .add(Restrictions.eq("connectionId",
		 * userId)).add(Restrictions.eq("connectionStatus", status))
		 * .setProjection(Projections.property("userId"));
		 */
		List<Long> userConnnectionIds = criteriaUserConnection.list();
		for (Long userId : userConnnectionIds) {
			User user = findUserById(userId);
			userConnection.add(user);
		}
		/*
		 * if (null != userConnnectionIds && userConnnectionIds.size() != 0) {
		 * logger.debug("User with id: " + userId +
		 * " connection founfind with ids: " + userConnnectionIds.toString());
		 * userConnection = getAllUserWhereIdIn(userConnnectionIds); }
		 */

		logger.info("Exiting from UserService getRequestConnection method");
		return userConnection;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, List<User>> getAntiProConnection(Long userId) throws Exception {
		logger.info("Inside UserService getAntiProConnection method");
		List<User> userConnection = null;
		List<User> antiConnection = new ArrayList<User>();
		List<User> proConnection = new ArrayList<User>();
		Map<String, List<User>> map = new HashMap();
		Criteria criteriaUserConnection = getSession().createCriteria(UserConnection.class)
				.add(Restrictions.eq("userId", userId)).add(Restrictions.eq("connectionStatus", "Accepted"))
				.setProjection(Projections.property("connectionId"));
		Criteria criteriaConnection = getSession().createCriteria(UserConnection.class)
				.add(Restrictions.eq("connectionId", userId)).add(Restrictions.eq("connectionStatus", "Accepted"))
				.setProjection(Projections.property("userId"));
		List<Long> userConnnectionIds = criteriaUserConnection.list();
		userConnnectionIds.addAll(criteriaConnection.list());
		if (null != userConnnectionIds && userConnnectionIds.size() != 0) {
			logger.debug("User with id: " + userId + " connection found with ids: " + userConnnectionIds.toString());
			userConnection = getAllUserWhereIdIn(userConnnectionIds);
			for (User user : userConnection) {

				if (user.getPoints() < 0) {

					antiConnection.add(user);
					logger.info("ANTICONNECTION====" + antiConnection);

				} else {

					proConnection.add(user);
					logger.info("PROCONNECTION====" + proConnection);
				}

			}
			map.put("anti", antiConnection);
			map.put("pro", proConnection);

		}

		logger.info("Exiting from UserService getAntiProConnection method");
		return map;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> getAllConnection(Long userId) throws Exception {
		logger.info("Inside UserService getAllConnection method");
		List<User> userConnection = null;
		List<Long> userConnnectionIds = getuserConnectionIds(userId);
		if (null != userConnnectionIds && userConnnectionIds.size() != 0) {
			logger.debug("User with id: " + userId + " connection found with ids: " + userConnnectionIds.toString());
			userConnection = getAllUserWhereIdIn(userConnnectionIds);
		}

		logger.info("Exiting from UserService getAllConnection method");
		return userConnection;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> getAllUserWhereIdIn(List<Long> userIds) throws Exception {
		logger.info("Inside UserService getAllUserWhereIdIn method");
		Criteria criteria = getSession().createCriteria(User.class).add(Restrictions.in("id", userIds));
		logger.info("Exiting from UserService getAllUserWhereIdIn method");
		return criteria.list();
	}

	@Override
	public Long saveUINotification(UserUINotification userUINotification) throws Exception {
		logger.info("Inside UserService save method");
		Long uINotificationId = (Long) getSession().save(userUINotification);
		logger.info("Exiting from UserService save method");
		return uINotificationId;
	}

	@Override
	public boolean updateUINotification(UserUINotification userUINotification) throws Exception {
		logger.info("Inside UserService update method");
		getSession().update(userUINotification);
		logger.info("Exiting from UserService update method");
		return true;
	}

	@Override
	public boolean blockUser(Long userId, Long blockUserId) throws Exception {
		logger.info("Inside UserService blockUser method");
		BlockId blockId = new BlockId();
		blockId.setBlockUserId(blockUserId);
		blockId.setUserId(userId);
		UserBlockSettings userBlockSettings = new UserBlockSettings();
		userBlockSettings.setId(blockId);
		getSession().save(userBlockSettings);
		logger.info("Exiting from UserService blockUser method");
		return true;
	}

	@Override
	public boolean unBlockUser(Long userId, Long blockUserId) throws Exception {
		logger.info("Inside UserService blockUser method");
		BlockId blockId = new BlockId();
		blockId.setBlockUserId(blockUserId);
		blockId.setUserId(userId);
		UserBlockSettings userBlockSettings = new UserBlockSettings();
		userBlockSettings.setId(blockId);
		getSession().delete(userBlockSettings);
		logger.info("Exiting from UserService blockUser method");
		return true;
	}

	@Override
	public UserBlockSettings findUserBlockSettingByblockId(Long userId, Long blockUserId) throws Exception {
		logger.info("Inside UserService blockUser method");
		BlockId blockId = new BlockId();
		blockId.setBlockUserId(blockUserId);
		blockId.setUserId(userId);
		UserBlockSettings userBlockSettings = (UserBlockSettings) getSession().get(UserBlockSettings.class, blockId);
		logger.info("Exiting from UserService blockUser method");
		return userBlockSettings;
	}

	@Override
	public List<UserUINotification> getAllUserUINotification(Long userId) throws Exception {
		logger.info("Inside UserService getAllUserUINotification method");
		List<UserUINotification> userUINotifications = new ArrayList<UserUINotification>();
		User user = findUserById(userId);
		Hibernate.initialize(user.getUserUINotification());
		userUINotifications.addAll(user.getUserUINotification());
		logger.info("Exiting from UserService getAllUserUINotification method");
		return userUINotifications;
	}

	@Override
	public Long saveUserPrivacySettings(UserPrivacyData userPrivacySettings) throws Exception {
		logger.info("Inside UserService saveUserPrivacySetting method");
		Long userPSId = (Long) getSession().save(userPrivacySettings);
		logger.info("Exiting from UserService saveUserPrivacySetting method");
		return userPSId;
	}

	@Override
	public boolean updateUserPrivacySetting(UserPrivacyData userPrivacySettings) throws Exception {
		logger.info("Inside UserService updateUserPrivacySetting method");
		getSession().update(userPrivacySettings);
		logger.info("Exiting from UserService updateUserPrivacySetting method");
		return true;
	}

	@Override
	public Long saveUserEmailNotificationSettings(UserEmailNotificationSettings userEmailNotificationSettings)
			throws Exception {

		logger.info("Inside UserService saveEmailNotification  method");
		Long userENId = (Long) getSession().save(userEmailNotificationSettings);
		logger.info("Exiting from UserService save method");
		return userENId;
	}

	@Override
	public boolean updateUserEmailNotificationSettings(UserEmailNotificationSettings userEmailNotificationSettings)
			throws Exception {
		logger.info("Inside UserService UpdateUserEmailNotificationSettings method");
		getSession().update(userEmailNotificationSettings);
		logger.info("Exiting from UserService UpdateUserEmailNotificationSettings method");
		return true;
	}

	@Override
	public List<UserEmailNotificationSettings> getAllUserEmailNotificationSettings(Long userId) throws Exception {
		logger.info("Inside UserService getAllUserEmailNotificationSettings method");
		List<UserEmailNotificationSettings> userEmailNotificationSettings = new ArrayList<UserEmailNotificationSettings>();
		User user = findUserById(userId);
		Hibernate.initialize(user.getUserUINotification());
		userEmailNotificationSettings.addAll(user.getUserEmailNotificationSettings());
		logger.info("Exiting from UserService getAllUserEmailNotificationSettings method");
		return userEmailNotificationSettings;
	}

	@Override
	public List<UserPrivacyData> getAllUsePrivacySettings(Long userId) throws Exception {
		logger.info("Inside UserService getAllUserEmailNotificationSettings method");
		List<UserPrivacyData> userPrivacySettings;
		Criteria criteria = getSession().createCriteria(UserPrivacyData.class).add(Restrictions.eq("userId", userId));
		userPrivacySettings = criteria.list();
		logger.debug(userPrivacySettings.size() + " UserPrivacySetting found for userId: " + userId);
		logger.info("Exiting from UserService getAllUserEmailNotificationSettings method");
		return userPrivacySettings;
	}

	@Override
	public List<UserPrivacyData> getUserProfile(Long userId, String viewerType) throws Exception {
		Criteria criteria = getSession().createCriteria(UserProfile.class)
				.add(Restrictions.in("notificationType", new String[] { "All", viewerType }))
				.add(Restrictions.eq("id", userId));
		return criteria.list();
	}

	@Override
	public User refreshEntity(User user) {

		getSession().refresh(user);
		return user;
	}

	@Override
	public List<Religion> getReligion() {
		return getSession().createCriteria(Religion.class).list();
	}

	@Override
	public List<RegionState> getState() throws Exception {
		// TODO Auto-generated method stub
		return getSession().createCriteria(RegionState.class).list();
	}

	@Override
	public List<MaritalStatus> getMaritalStatus() throws Exception {
		return getSession().createCriteria(MaritalStatus.class).list();
	}

	@Override
	public List<Qualification> getQualification() throws Exception {
		return getSession().createCriteria(Qualification.class).list();
	}

	@Override
	public UserConnection connections(Long userId, Long connecionId) throws Exception {
		Criteria criteria = getSession().createCriteria(UserConnection.class);
		criteria.add(Restrictions.eq("connectionId", connecionId));
		criteria.add(Restrictions.eq("userId", userId));
		UserConnection userConnection = (UserConnection) criteria.uniqueResult();

		if (userConnection == null) {
			Criteria criteriaNew = getSession().createCriteria(UserConnection.class);
			criteriaNew.add(Restrictions.eq("connectionId", userId));
			criteriaNew.add(Restrictions.eq("userId", connecionId));
			userConnection = (UserConnection) criteriaNew.uniqueResult();
		}

		return userConnection;
	}

	@Override
	public boolean verifyIfUsernameExist(String username) throws Exception {
		logger.info("Inside UserService verifyIfUsernameExist method");
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("username", username));
		if (criteria.list().size() == 0) {
			logger.info("Exiting from UserService verifyIfUsernameExist method");
			return false;
		} else {
			logger.info("Exiting from UserService verifyIfUsernameExist method");
			return true;
		}

	}

	@Override
	public List<User> getMutualFriend(Long userId, Long connectionId) throws Exception, MessageException {
		List<Long> userConnnectionIds = getuserConnectionIds(userId);
		Criteria criteriaUserConnection = getSession().createCriteria(UserConnection.class)
				.add(Restrictions.eq("userId", userId)).add(Restrictions.eq("connectionStatus", "Accepted"))
				.setProjection(Projections.property("connectionId"));
		return null;
	}

	@Override
	public List<Long> getuserConnectionIds(Long userId) throws Exception, MessageException {
		Criteria criteriaUserConnection = getSession().createCriteria(UserConnection.class)
				.add(Restrictions.eq("userId", userId)).add(Restrictions.eq("connectionStatus", "Accepted"))
				.setProjection(Projections.property("connectionId"));
		Criteria criteriaConnection = getSession().createCriteria(UserConnection.class)
				.add(Restrictions.eq("connectionId", userId)).add(Restrictions.eq("connectionStatus", "Accepted"))
				.setProjection(Projections.property("userId"));
		List<Long> userConnnectionIds = criteriaUserConnection.list();
		userConnnectionIds.addAll(criteriaConnection.list());
		return userConnnectionIds;
	}

	@Override
	public Long add(Achievement achievement) throws Exception {

		logger.info("Inside add achievement method service");

		Long id = (Long) getSession().save(achievement);

		logger.info("Exiting add achievement method service");

		return id;
	}

	@Override
	public Long add(Leader leader) throws Exception {
		logger.info("Inside add leader method service");

		Long id = (Long) getSession().save(leader);

		logger.info("Exiting add leader method service");

		return id;
	}

	@Override
	public Long add(PoliticalView politicalView) throws Exception {
		logger.info("Inside add politicalView method service");

		Long id = (Long) getSession().save(politicalView);

		logger.info("Exiting add politicalView method service");

		return id;
	}

	@Override
	public List<Achievement> getAchievement(Long userId) throws Exception {
		logger.info("Inside UserService getAchievement method");
		User user = new User();
		user.setId(userId);
		
		Criteria criteria = getSession().createCriteria(Achievement.class)
				.add(Restrictions.eqOrIsNull("user", user));
		
		List<Achievement> achievement = criteria.list();
		
		if (criteria.list().size() == 0) {
			logger.info("Exiting from UserService getAchievement method");
			return null;
		} else {
			logger.info("Exiting from UserService getAchievement method");
			return achievement;
		}
		
		
	}

	@Override
	public List<Leader> getLeader(Long userId) throws Exception {
		logger.info("Inside UserService getLeader method");
		User user = new User();
		user.setId(userId);
		
		Criteria criteria = getSession().createCriteria(Leader.class)
				.add(Restrictions.eqOrIsNull("user", user));
		
		List<Leader> leader = criteria.list();
		
		if (criteria.list().size() == 0) {
			logger.info("Exiting from UserService getLeader method");
			return null;
		} else {
			logger.info("Exiting from UserService getLeader method");
			return leader;
		}
	}

	@Override
	public List<PoliticalView> getPoliticalView(Long userId) throws Exception {
		logger.info("Inside UserService getPoliticalView method");
		User user = new User();
		user.setId(userId);
		
		Criteria criteria = getSession().createCriteria(PoliticalView.class)
				.add(Restrictions.eqOrIsNull("user", user));
		
		List<PoliticalView> politicalView = criteria.list();
		
		if (criteria.list().size() == 0) {
			logger.info("Exiting from UserService getPoliticalView method");
			return null;
		} else {
			logger.info("Exiting from UserService getPoliticalView method");
			return politicalView;
		}
	}

}
