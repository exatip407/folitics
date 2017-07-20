package com.ohmuk.folitics.businessDelegate.interfaces;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import redis.clients.jedis.JedisPubSub;

import com.ohmuk.folitics.hibernate.entity.Achievement;
import com.ohmuk.folitics.hibernate.entity.Leader;
import com.ohmuk.folitics.hibernate.entity.PoliticalView;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.UserConnection;
import com.ohmuk.folitics.hibernate.entity.UserEmailNotificationSettings;
import com.ohmuk.folitics.hibernate.entity.UserPrivacyData;
import com.ohmuk.folitics.hibernate.entity.UserUINotification;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.MaritalStatus;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Qualification;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.RegionState;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Religion;
import com.ohmuk.folitics.model.ImageModel;

public interface IUserBusinessDelegate {
	/**
	 * Method is to add {@link User}
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User create(User user) throws Exception;

	/**
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User save(User user) throws Exception;

	/**
	 * Method is to get User details to viewer
	 * 
	 * @param viewerId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<UserPrivacyData> viewUserPrivacyData(Long viewerId, Long userId)
			throws Exception;

	/**
	 * Method is to find {@link User} by id
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public User findUserById(Long id) throws Exception;

	/**
	 * Method is to get all {@link User}
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<User> readAll() throws Exception;

	public List<User> getAllRemainingUsers(Long id) throws Exception;

	/**
	 * Method is to update {@link User}
	 * 
	 * @return boolean
	 * @param user
	 * @throws Exception
	 */
	public User update(User user) throws Exception;

	/**
	 * Method is to soft delete {@link User} by id
	 * 
	 * @param id
	 * @return boolean
	 * @throws Exception
	 */
	public boolean delete(Long id) throws Exception;

	/**
	 * Method is to soft delete {@link User}
	 * 
	 * @param user
	 * @return boolean
	 * @throws Exception
	 */
	public boolean delete(User user) throws Exception;

	/**
	 * Method is to hard delete {@link User} by id
	 * 
	 * @param id
	 * @return boolean
	 * @throws Exception
	 */
	public boolean deleteFromDB(Long id) throws Exception;

	/**
	 * Method is to hard delete {@link User}
	 * 
	 * @param user
	 * @return boolean
	 * @throws Exception
	 */
	public boolean deleteFromDB(User user) throws Exception;

	/**
	 * Method is to reset {@link User} password
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public User resetPassword(Long id) throws Exception;

	/**
	 * Method is to upload {@link User} image
	 * 
	 * @param file
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public User uploadImage(MultipartFile file, Long userId) throws Exception;

	/**
	 * Method is to find {@link User} by username
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public User findByUsername(String username) throws Exception;

	/**
	 * Find {@link User} from session
	 * 
	 * @return {@link User}
	 * @throws Exception
	 */
	public User getUserFromSession(HttpServletRequest request) throws Exception;

	/**
	 * Add {@link User} connection
	 * 
	 * @param userId
	 * @param connecionId
	 * @return Long
	 * @throws Exception
	 */
	public Long addConnection(Long userId, Long connecionId) throws Exception;
	
	/**
	 * Add {@link User} connection
	 * 
	 * @param userId
	 * @param connecionId
	 * @return Long
	 * @throws Exception
	 */
	public UserConnection connections(Long userId, Long connecionId) throws Exception;

	/**
	 * Method is to delete
	 * {@link com.ohmuk.folitics.hibernate.entity.UserConnection}
	 * 
	 * @param userId
	 * @param connectionId
	 * @return boolean
	 * @throws Exception
	 */
	public boolean deleteConnection(Long userId, Long connectionId)
			throws Exception;

	/**
	 * Method is to update {@link UserConnection} status
	 * 
	 * @param user
	 * @param connectionId
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public boolean updateConnectionStatus(Long userId, Long connectionId,
			String status) throws Exception;

	/**
	 * Method is to get all user connection
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<User> getAllConnection(Long userId) throws Exception;

	/**
	 * Method is to get all requested user connection
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<User> getRequestConnection(Long connectionId, String status)
			throws Exception;

	/**
	 * Method is to get all user Email notification settings
	 * 
	 * @return list of UserEmailNotificationSettings
	 * @throws Exception
	 */
	public List<UserEmailNotificationSettings> getAllUserEmailNotificationSettings(
			Long userId) throws Exception;

	/**
	 * Method is to update {@link userEmailNotificationSettings} by userId
	 * 
	 * @param userId
	 * @param userEmailNotificationSettings
	 * @return boolean
	 * @throws Exception
	 */
	public boolean updateUserEmailNotificationSettings(
			UserEmailNotificationSettings userEmailNotificationSettings)
			throws Exception;

	/**
	 * Method is to save {@link UserUINotification} by userId
	 * 
	 * @param userId
	 * @param userUINotification
	 * @return Long
	 * @throws Exception
	 */
	public Long saveUserUINotification(Long userId,
			UserUINotification userUINotification) throws Exception;

	/**
	 * Method is to update {@link UserUINotification} by userId
	 * 
	 * @param userId
	 * @param userUINotification
	 * @return boolean
	 * @throws Exception
	 */
	public boolean updateUserUINotification(
			UserUINotification userUINotification) throws Exception;

	/**
	 * Method is to block {@link User}
	 * 
	 * @param userId
	 * @param blockUserId
	 * @return boolean
	 * @throws Exception
	 */
	public boolean blockUser(Long userId, Long blockUserId) throws Exception;

	/**
	 * Method is to unblock {@link User}
	 * 
	 * @param userId
	 * @param blockUserId
	 * @return boolean
	 * @throws Exception
	 */
	public boolean unBlockUser(Long userId, Long blockUserId) throws Exception;

	/**
	 * Method is to save {@link UserPrivacySettings} by userId
	 * 
	 * @param userId
	 * @param userPrivacySettings
	 * @return Long
	 * @throws Exception
	 */
	public Long saveUserPrivacySettings(Long userId,
			UserPrivacyData userPrivacySettings) throws Exception;

	/**
	 * Method is update {@link UserPrivacySettings}
	 * 
	 * @param userPrivacySettings
	 * @return {@link UserPrivacySettings}
	 * @throws Exception
	 */
	public boolean updateUserPrivacySetting(UserPrivacyData userPrivacySettings)
			throws Exception;

	/**
	 * Method is to save {@link UserEmailNotificationSettings}
	 * 
	 * @param userId
	 * @param userEmailNotificationSettings
	 * @return Long
	 * @throws Exception
	 */
	public Long saveUserEmailNotificationSettings(Long userId,
			UserEmailNotificationSettings userEmailNotificationSettings)
			throws Exception;

	/**
	 * Method is to get all {@link UserUINotification}
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<UserUINotification> getAllUserUINotification(Long userId)
			throws Exception;

	/**
	 * Method is to get all {@link UserPrivacySettings} by user
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<UserPrivacyData> getAllUserPrivacySettings(Long userId)
			throws Exception;

	/**
	 * Method is to find weather two user are friends or foe
	 * 
	 * @param viewerId
	 * @param userId
	 * @return String
	 * @throws Exception
	 */
	public String friendFoeAggrigation(Long viewerId, Long userId)
			throws Exception;

	public ImageModel getImageModel(Long id, boolean isThumbnail)
			throws Exception;

	List<ImageModel> getImageModels(String entityIds, boolean isThumbnail)
			throws Exception;

	/**
	 * Method is to subscribe user from redis 
	 * @param userId
	 * @return redis.clients.jedis.JedisPubSub
	 * @throws Exception
	 */
	public JedisPubSub subscribe(Long userId) throws Exception;
	/**
	 * Method is to unSubscribe user from redis 
	 * @param userId
	 * @return null
	 * @throws Exception
	 */
	public void unSubscribe(Long userId)throws Exception;
	
	/**
	 * Method is to get notification for loggedIn user from redis
	 * @param userId
	 * @return List of String
	 * @throws Exception
	 */

	public List<String> readNotifications(Long userId)throws Exception;

	public Map<String, List<User>> getAntiProConnection(Long userId)
			throws Exception;

	public List<Religion> getAllReligion();

	public List<RegionState> getAllState()throws Exception;

	public List<MaritalStatus> getAllMaritalStatus()throws Exception;

	public List<Qualification> getAllQualification()throws Exception;
	/**
	 * Method is to verify if username already exist
	 * @param userId
	 * @throws Exception
	 */
	public boolean verifyIfUsernameExist(String username)throws Exception;
	
	
	/**
	 * Method is to add {@link Achievement}
	 * 
	 * @param achievement
	 * @return
	 * @throws Exception
	 */
	public Long add(Achievement achievement) throws Exception;
	
	/**
	 * Method is to add {@link Achievement}
	 * 
	 * @param leader
	 * @return
	 * @throws Exception
	 */
	public Long add(Leader leader) throws Exception;
	
	/**
	 * Method is to add {@link Leader}
	 * 
	 * @param achievement
	 * @return
	 * @throws Exception
	 */
	public Long add(PoliticalView politicalView) throws Exception;

	/**
	 * Method is to find {@link Achievement} by userId
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<Achievement> getAchievement(Long userId) throws Exception;
	
	/**
	 * Method is to find {@link Leader} by userId
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<Leader> getLeader(Long userId) throws Exception;
	
	/**
	 * Method is to find {@link PoliticalView} by userId
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<PoliticalView> getPoliticalView(Long userId) throws Exception;
}
