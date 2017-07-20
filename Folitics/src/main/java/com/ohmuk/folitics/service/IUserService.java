package com.ohmuk.folitics.service;

import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Achievement;
import com.ohmuk.folitics.hibernate.entity.Leader;
import com.ohmuk.folitics.hibernate.entity.PoliticalView;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.UserBlockSettings;
import com.ohmuk.folitics.hibernate.entity.UserConnection;
import com.ohmuk.folitics.hibernate.entity.UserEmailNotificationSettings;
import com.ohmuk.folitics.hibernate.entity.UserPrivacyData;
import com.ohmuk.folitics.hibernate.entity.UserUINotification;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.MaritalStatus;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Qualification;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.RegionState;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Religion;

/**
 * 
 * @author Mayank Sharma
 *
 */
public interface IUserService extends IBaseService {
	/**
	 * Method is to add {@link User}
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User create(User user) throws Exception;

	/**
	 * Method is to add user connection
	 * 
	 * @return Long
	 * @throws Exception
	 */
	public Long addUserConnection(UserConnection userConnection)
			throws Exception;

	/**
	 * Method is to update {@link UserConnection}
	 * 
	 * @param userConnection
	 * @return boolean
	 * @throws Exception
	 */
	public boolean updateUserConnection(UserConnection userConnection)
			throws Exception;

	/**
	 * Delete {@link UserConnection}
	 * 
	 * @param userConnection
	 * @return boolean
	 * @throws Exception
	 */
	public boolean deleteUserConnection(UserConnection userConnection)
			throws Exception;

	/**
	 * Method is to find {@link UserConnection}
	 * 
	 * @return {@link UserConnection}
	 * @throws Exception
	 */
	public UserConnection findUserConnectionByUserAndConnectionId(Long userId,
			Long connectionId) throws Exception;

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
	 * @param user
	 * @return List<User>
	 * @throws Exception
	 */
	public List<User> getAllConnection(Long userId) throws Exception;
	
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
	 * Method is to get all requested user
	 * 
	 * @param connectionId
	 * @return List<User>
	 * @throws Exception
	 */
	public List<User> getRequestConnection(Long connectionId, String status)
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
	 * Method is to get all {@link User} where id in userIds
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<User> getAllUserWhereIdIn(List<Long> userIds) throws Exception;

	/**
	 * Method is to get all {@link User}
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<User> readAll() throws Exception;

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
	public User resetPassword(User user) throws Exception;

	/**
	 * Method is to upload {@link User} image
	 * 
	 * @param file
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public User uploadImage(User user) throws Exception;

	/**
	 * Method is to find {@link User} by username
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public User findByUsername(String username) throws Exception;

	/**
	 * Method is to save {@link UserUINotification}
	 * 
	 * @param userUINotification
	 * @return Long
	 * @throws Exception
	 */
	public Long saveUINotification(UserUINotification userUINotification)
			throws Exception;

	/**
	 * Method is to update {@link UserUINotification}
	 * 
	 * @param userUINotification
	 * @return boolean
	 * @throws Exception
	 */
	public boolean updateUINotification(UserUINotification userUINotification)
			throws Exception;

	/**
	 * Method is to save {@link UserEmailNotificationSettings}
	 * 
	 * @param userEmailNotificationSettings
	 * @return Long
	 * @throws Exception
	 */
	public Long saveUserEmailNotificationSettings(
			UserEmailNotificationSettings userEmailNotificationSettings)
			throws Exception;

	/**
	 * Method is to update {@link UserEmailNotificationSettings}
	 * 
	 * @param userEmailNotificationSettings
	 * @return boolean
	 * @throws Exception
	 */
	public boolean updateUserEmailNotificationSettings(
			UserEmailNotificationSettings userEmailNotificationSettings)
			throws Exception;

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
	 * Method is to find {@link UserBlockSettings} by
	 * {@link com.ohmuk.folitics.hibernate.entity.BlockId}
	 * 
	 * @return {@link UserBlockSettings}
	 * @throws Exception
	 */
	public UserBlockSettings findUserBlockSettingByblockId(Long userId,
			Long blockUserId) throws Exception;

	/**
	 * Web service is to get All {@link UserUINotification} by userId
	 * 
	 * @param userId
	 * @return List<UserUINotification>
	 * @throws Exception
	 */
	public List<UserUINotification> getAllUserUINotification(Long userId)
			throws Exception;

	/**
	 * Method is to save {@link UserPrivacySettings}
	 * 
	 * @param userPrivacySettings
	 * @return Long
	 * @throws Exception
	 */
	public Long saveUserPrivacySettings(UserPrivacyData userPrivacySettings)
			throws Exception;

	/**
	 * Method is to update {@link UserPrivacySettings}
	 * 
	 * @param userPrivacySettings
	 * @return
	 * @throws Exception
	 */
	public boolean updateUserPrivacySetting(UserPrivacyData userPrivacySettings)
			throws Exception;

	/**
	 * Web service is to get All {@link UserUINotification} by userId
	 * 
	 * @param userId
	 * @return List<UserUINotification>
	 * @throws Exception
	 */
	public List<UserEmailNotificationSettings> getAllUserEmailNotificationSettings(
			Long userId) throws Exception;

	/**
	 * Method is to get all {@link UserPrivacySettings} by user
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<UserPrivacyData> getAllUsePrivacySettings(Long userId)
			throws Exception;

	/**
	 * Method is to get user profile by viewerType
	 * 
	 * @param userId
	 * @param viewerType
	 * @return List<UserPrivacyData>
	 * @throws Exception
	 */
	public List<UserPrivacyData> getUserProfile(Long userId, String viewerType)
			throws Exception;

	java.util.Map<String, List<User>> getAntiProConnection(Long userId)
			throws Exception;

	List<User> getAllRemainingUsers(Long id) throws Exception;

	public User refreshEntity(User user);

	public List<Religion> getReligion();

	public List<RegionState> getState()throws Exception;

	public List<MaritalStatus> getMaritalStatus()throws Exception;

	public List<Qualification> getQualification()throws Exception;

	/**
	 * Method is to verify if username already exist
	 * @param userId
	 * @throws Exception
	 */
	public boolean verifyIfUsernameExist(String username)throws Exception;
	
	/**
	 * Method is to get mutual friends
	 * @param userId
	 * @param connectionId
	 * @return List<User>
	 * @throws Exception
	 * @throws MessageException
	 */
	public List<User> getMutualFriend(Long userId,Long connectionId)throws Exception,MessageException;
	
	public List<Long> getuserConnectionIds(Long userId)throws Exception,MessageException;
	
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
