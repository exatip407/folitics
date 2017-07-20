package com.ohmuk.folitics.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ohmuk.folitics.businessDelegate.interfaces.IUserBusinessDelegate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Achievement;
import com.ohmuk.folitics.hibernate.entity.Leader;
import com.ohmuk.folitics.hibernate.entity.PoliticalView;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.UserConnection;
import com.ohmuk.folitics.hibernate.entity.UserEmailNotificationSettings;
import com.ohmuk.folitics.hibernate.entity.UserImage;
import com.ohmuk.folitics.hibernate.entity.UserPrivacyData;
import com.ohmuk.folitics.hibernate.entity.UserProfile;
import com.ohmuk.folitics.hibernate.entity.UserUINotification;
import com.ohmuk.folitics.hibernate.entity.task.Department;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.MaritalStatus;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Qualification;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.RegionState;
import com.ohmuk.folitics.hibernate.entity.verdict.lookup.Religion;
import com.ohmuk.folitics.util.FoliticsUtils;

/**
 * 
 * @author Mayank Sharma
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private volatile IUserBusinessDelegate businessDelegate;

	private static Logger logger = LoggerFactory
			.getLogger(UserController.class);

	@RequestMapping
	public String getUserPage() {
		return "user-page";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<User> getAdd() {
		List<User> users = new ArrayList<>();
		users.add(getTestUser());
		return new ResponseDto<User>(true, users);
	}

	/**
	 * Web service to add user
	 * 
	 * @param user
	 * @param userImageMultipart
	 * @return ResponseDto<User>
	 */
	@RequestMapping(value = "/addUserMultipart", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<User> add(
			@RequestPart(value = "user") User user,
			@RequestParam(value = "userImage", required = false) MultipartFile userImageMultipart) {
		logger.info("Inside UserController add method");

		User userFromDB = null;

		/**
		 * Check for user with that username
		 */
		try {
			userFromDB = businessDelegate.findByUsername(user.getUsername());
			logger.debug("User found with username: " + user.getUsername());
		} catch (Exception exception) {
			logger.error("Exception in finding user with username: "
					+ user.getUsername());
			logger.info("Exiting from UserController add method");
			return new ResponseDto<User>(false);
		}

		if (null != userFromDB) {
			logger.debug("Username already exist with username: "
					+ user.getUsername());
			logger.info("Exiting from UserController add method");
			return new ResponseDto<User>(false, new User(),
					"Username already existwith username: "
							+ user.getUsername());
		}

		user.setStatus(ComponentState.ACTIVE.getValue());
		UserImage userImage = new UserImage();
		try {
			userImage.setImage(userImageMultipart.getBytes());
			user.setUserImage(userImage);
			logger.debug("set image to user");
		} catch (IOException ioException) {
			logger.error("Exception in while add image in user");
			logger.error("Exception: " + ioException);
			logger.info("Exiting from UserController add method");
			return new ResponseDto<User>(false);
		}
		try {
			user = businessDelegate.save(user);
		} catch (Exception exception) {
			logger.error("Exception in save user with username: "
					+ user.getUsername());
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController add method");
			return new ResponseDto<User>(false);
		}
		if (null != user) {
			logger.info("Exiting from UserController add method");
			return new ResponseDto<User>(true, user);
		}
		logger.info("Exiting from UserController add method");
		return new ResponseDto<User>(false);
	}

	/**
	 * Web service is to add {@link User}
	 * 
	 * @param user
	 * @return ResponseDto<User>
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<User> addUser(@RequestBody User user) {
		logger.info("Inside UserController addUser method");
		try {
			businessDelegate.save(user);
			logger.debug("User added with username: " + user.getUsername());
		} catch (Exception exception) {
			logger.error("Exception in adding user with username: "
					+ user.getUsername());
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController addUser method");
			return new ResponseDto<User>(false);
		}
		logger.info("Exiting from UserController addUser method");
		return new ResponseDto<User>(true, user);
	}

	/**
	 * Web service to update user image
	 * 
	 * @param file
	 * @param userId
	 * @return ResponseDto<User>
	 */
	@RequestMapping(value = "/uploadimage", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<User> uploadImage(
			@RequestParam("userImage") MultipartFile file,
			@RequestParam("userId") Long userId) {
		logger.info("Inside UserController uploadImage method");
		User user = null;
		try {
			user = businessDelegate.uploadImage(file, userId);
		} catch (Exception exception) {
			logger.error("Exception in upload user image with user id: "
					+ userId);
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController uploadImage method");
			return new ResponseDto<User>(false);
		}
		if (user != null) {
			logger.debug("User image is uploaded with user id: " + userId);
			logger.info("Exiting from UserController uploadImage method");
			return new ResponseDto<User>(true, user);
		}
		logger.info("Exiting from UserController uploadImage method");
		return new ResponseDto<User>(false);
	}

	/**
	 * Web service to edit user
	 * 
	 * @author Mayank Sharma
	 * @param user
	 * @return new ResponseDto<User>
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<User> edit(@RequestBody User user) {
		logger.info("Inside UserController edit method");
		try {
			businessDelegate.update(user);
			logger.debug("User updated with username: " + user.getUsername());
		} catch (Exception exception) {
			logger.error("Exception in updating user with username: "
					+ user.getUsername());
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController edit method");
			return new ResponseDto<User>(false);
		}
		logger.info("Exiting from UserController edit method");
		return new ResponseDto<User>(true, user);
	}

	/**
	 * Web service to soft delete user by user id
	 * 
	 * @param id
	 * @return ResponseDto<User>
	 */
	@RequestMapping(value = "/deleteById", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<User> delete(Long id) {
		logger.info("Indide UserController delete method");
		try {
			if (businessDelegate.delete(id)) {
				logger.debug("User is soft deleted");
				return new ResponseDto<User>(true);
			}
		} catch (Exception exception) {
			logger.error("Exception in deleting user with username: " + id);
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController delete method");
			return new ResponseDto<User>(false);
		}

		logger.debug("User is not deleted");
		logger.info("Exiting from UserController delete method");
		return new ResponseDto<User>(false);
	}

	/**
	 * Web service to soft delete user
	 * 
	 * @param user
	 * @return ResponseDto<User>
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<User> delete(@RequestBody User user) {
		logger.info("Inside UserController delete method");
		try {
			if (businessDelegate.delete(user)) {
				logger.debug("User is soft deleted");
				logger.info("Exiting from UserController delete method");
				return new ResponseDto<User>(true);
			}
		} catch (Exception exception) {
			logger.error("Exception in deleting user with username: "
					+ user.getName());
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController delete method");
			return new ResponseDto<User>(false);
		}
		logger.debug("User is not deleted");
		logger.info("Exiting from UserController delete method");
		return new ResponseDto<User>(false);
	}

	/**
	 * Web service to hard delete user by user id
	 * 
	 * @param id
	 * @return ResponseDto<User>
	 */
	@RequestMapping(value = "/deleteFromDbById", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<User> deleteFromDB(Long id) {
		logger.info("Inside UserController deleteFromDbById method");
		try {
			if (businessDelegate.deleteFromDB(id)) {
				logger.debug("User is hard deleted");
				return new ResponseDto<User>(true);
			}
		} catch (Exception exception) {
			logger.error("Exception in deleting user with user id: " + id);
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController deleteFromDbById method");
			return new ResponseDto<User>(false);
		}

		logger.debug("User is not deleted");
		logger.info("Exiting from UserController deleteFromDbById method");
		return new ResponseDto<User>(false);
	}

	/**
	 * Web service to hard delete user
	 * 
	 * @param user
	 * @return ResponseDto<User>
	 */
	@RequestMapping(value = "/deleteFromDb", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<User> deleteFromDB(@RequestBody User user) {
		logger.info("Inside UserController deleteFromDB method");
		try {
			if (businessDelegate.deleteFromDB(user)) {
				logger.debug("User is hard deleted");
				logger.info("Exiting from UserController deleteFromDB method");
				return new ResponseDto<User>(true);
			}
		} catch (Exception exception) {
			logger.error("Exception in deleting user with username: "
					+ user.getName());
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController deleteFromDB method");
			return new ResponseDto<User>(false);
		}

		logger.debug("User is not deleted");
		logger.info("Exiting from UserController deleteFromDB method");
		return new ResponseDto<User>(false);
	}

	/**
	 * Web service to get all user
	 * 
	 * @return ResponseDto<User>
	 */
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<User> getAll() {
		logger.info("Inside UserController getAll method");
		List<User> users = null;
		try {
			users = businessDelegate.readAll();
		} catch (Exception exception) {
			logger.error("Exception in reading getAll user: ");
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController getAll method");
			return new ResponseDto<User>(false);
		}
		if (users != null) {
			return new ResponseDto<User>(true, users);
		}
		logger.debug("No user found");
		logger.info("Exiting from UserController add method");
		return new ResponseDto<User>(false);
	}

	@RequestMapping(value = "/getAllRemainingUsers", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<User> getAllRemainingUsers(Long id) {
		logger.info("Inside UserController getAllRemainingUsers method");
		List<User> users = null;
		try {
			users = businessDelegate.getAllRemainingUsers(id);
		} catch (Exception exception) {
			logger.error("Exception in reading getAllRemainingUsers user: ");
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController getAllRemainingUsers method");
			return new ResponseDto<User>(false);
		}
		if (users != null) {
			return new ResponseDto<User>(true, users);
		}
		logger.debug("No user found");
		logger.info("Exiting from UserController add method");
		return new ResponseDto<User>(false);
	}

	/**
	 * Web businessDelegate to find user by user id
	 * 
	 * @param id
	 * @return ResponseDto<User>
	 */
	@RequestMapping(value = "/find", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseDto<User> find(Long id) {
		logger.info("Inside UserController find method");
		User user = null;
		try {
			user = businessDelegate.findUserById(id);
		} catch (Exception exception) {
			logger.error("Exception in finding user with user id: " + id);
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController getAll method");
			return new ResponseDto<User>(false);
		}
		if (user != null) {
			logger.info("Exiting from UserController getAll method");
			ResponseDto<User> responseDto = new ResponseDto<User>(true, user);
			return responseDto;
		} else {
			logger.info("Exiting from UserController getAll method");
			return new ResponseDto<User>(false);
		}
	}

	/**
	 * Web businessDelegate to find user by user id
	 * 
	 * @param id
	 * @return ResponseDto<User>
	 */
	@RequestMapping(value = "/findByUsername", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<User> findByUsername(String username) {
		logger.info("Inside UserController findByUsername method");
		User user = null;
		try {
			user = businessDelegate.findByUsername(username);
		} catch (Exception exception) {
			logger.error("Exception in finding user with user name: "
					+ username);
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController findByUsername method");
			return new ResponseDto<User>(false);
		}
		if (user != null) {
			logger.info("Exiting from UserController findByUsername method");
			ResponseDto<User> responseDto = new ResponseDto<User>(true, user);
			return responseDto;
		} else {
			logger.info("Exiting from UserController findByUsername method");
			return new ResponseDto<User>(false);
		}
	}

	/**
	 * Web service is to reset {@link User} password
	 * 
	 * @param id
	 * @return ResponseDto<User>
	 */
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<User> resetPassword(Long id) {
		logger.info("Inside UserController resetPassword method");
		User user = null;
		try {
			user = businessDelegate.resetPassword(id);
		} catch (Exception exception) {
			logger.error("Exception in reseting user password with user id: "
					+ id);
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController resetPassword method");
			return new ResponseDto<User>(false);
		}
		if (user != null) {
			logger.debug("Password is reset");
			logger.info("Exiting from UserController resetPassword method");
			return new ResponseDto<User>(true, user);
		}
		logger.debug("Password is not reset");
		logger.info("Exiting from UserController resetPassword method");
		return new ResponseDto<User>(false);
	}

	/**
	 * Web service to find {@link User} from session
	 * 
	 * @param id
	 * @return ResponseDto<User>
	 */
	@RequestMapping(value = "/getUserFromSession", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<User> getUserFromSession(
			HttpServletRequest request) {
		logger.info("Inside UserController getUserFromSession method");
		User user = null;
		try {
			user = businessDelegate.getUserFromSession(request);
		} catch (Exception exception) {
			logger.error("Exception in finding user from session ");
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController getUserFromSession method");
			return new ResponseDto<User>(false);
		}
		if (user != null) {
			logger.info("Exiting from UserController getUserFromSession method");
			return new ResponseDto<User>(true, user);
		}
		logger.info("Exiting from UserController getUserFromSession method");
		return new ResponseDto<User>(false);
	}

	/**
	 * Web service is to get user privacy details by viewer
	 * 
	 * @param viewerId
	 * @param UserId
	 * @return ResponseDto<UserPrivacyData>
	 */
	@RequestMapping(value = "/viewUserProfile", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<UserPrivacyData> viewUserProfile(
			Long viewerId, Long userId) {
		logger.info("Inside UserController viewUserProfile method");
		try {
			List<UserPrivacyData> userPrivacyDatas = businessDelegate
					.viewUserPrivacyData(viewerId, userId);
			return new ResponseDto<UserPrivacyData>(true, userPrivacyDatas);
		} catch (Exception exception) {
			logger.error("Exception in getting user details");
			logger.error("Exception: " + exception);
			logger.info("Exiting from viewUserProfile method");
			return new ResponseDto<UserPrivacyData>(false);
		}
	}

	/**
	 * Web service is to add user connection
	 * 
	 * @param userId
	 * @param connectionId
	 * @return ResponseDto<User>
	 */
	@RequestMapping(value = "/addConnection", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<User> addConnection(Long userId,
			Long connectionId) {
		logger.info("Inside UserController addConnection method");
		try {
			Long userConnectionId = businessDelegate.addConnection(userId,
					connectionId);
			if (null != userConnectionId) {
				logger.debug("User connection is added");
				logger.info("Exiting from UserController addConnection method");
				return new ResponseDto<User>(true);
			} else {
				logger.info("Exiting from UserController addConnection method");
				return new ResponseDto<User>(false);
			}
		} catch (Exception exception) {
			logger.error("Exception in adding user connection with userId: "
					+ userId + "and connectionId:" + connectionId);
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController addConnection method");
			return new ResponseDto<User>(false);
		}

	}
	@RequestMapping(value = "/getAllReligion", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Object> getAllReligion() {

		List<Religion>religions;
		try {
			religions = businessDelegate.getAllReligion();
		} catch (Exception e) {
			logger.error("CustomException while fetching the religions " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		}
		return new ResponseDto<Object>(true, religions,
				"Sucessfully Fetched list of all religions");
	}
	
	@RequestMapping(value = "/getAllState", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Object> getAllState() {

		List<RegionState>states;
		try {
			states= businessDelegate.getAllState();
		} catch (Exception e) {
			logger.error("CustomException while fetching the states " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		}
		return new ResponseDto<Object>(true, states,
				"Sucessfully Fetched list of all states");
	}
	
	@RequestMapping(value = "/getAllMaritalStatus", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Object>getAllMaritalStatus() {

		List<MaritalStatus>maritalstatus;
		try {
			maritalstatus= businessDelegate.getAllMaritalStatus();
		} catch (Exception e) {
			logger.error("CustomException while fetching the maritalstatus " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		}
		return new ResponseDto<Object>(true, maritalstatus,
				"Sucessfully Fetched list of all maritalstatus");
	}
	
	@RequestMapping(value = "/getAllQualification", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Object> getAllQualification(){

		List<Qualification>qualification;
		try {
			qualification= businessDelegate.getAllQualification();
		} catch (Exception e) {
			logger.error("CustomException while fetching the qualification " + e);
			return new ResponseDto<Object>(false, null, e.getMessage());
		}
		return new ResponseDto<Object>(true, qualification,
				"Sucessfully Fetched list of all states");
	}

	/**
	 * Web service is to delete user connection
	 * 
	 * @param userId
	 * @param connectionId
	 * @return ResponseDto<User>
	 */
	@RequestMapping(value = "/deleteConnection", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<User> deleteConnection(Long userId,
			Long connectionId) {
		logger.info("Inside UserController deleteConnection method");
		try {
			if (businessDelegate.deleteConnection(userId, connectionId)) {
				logger.debug("User connection is added");
				logger.info("Exiting from UserController deleteConnection method");
				return new ResponseDto<User>(true);
			} else {
				logger.info("Exiting from UserController deleteConnection method");
				return new ResponseDto<User>(false);
			}
		} catch (Exception exception) {
			logger.error("Exception in updating user connection with userId: "
					+ userId + "and connectionId:" + connectionId);
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController deleteConnection method");
			return new ResponseDto<User>(false);
		}

	}

	/**
	 * Web service is to update
	 * {@link com.ohmuk.folitics.hibernate.entity.UserConnection} status
	 * 
	 * @param userId
	 * @param connectionId
	 * @param connectionStatus
	 * @return ResponseDto<User>
	 */
	@RequestMapping(value = "/updateConnectionStatus", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<User> updateConnectionStatus(Long userId,
			Long connectionId, String connectionStatus) {
		logger.info("Inside UserController updateConnectionStatus method");
		try {
			if (businessDelegate.updateConnectionStatus(userId, connectionId,
					connectionStatus)) {
				return new ResponseDto<User>(true);
			}
			return new ResponseDto<User>(false);
		} catch (Exception exception) {
			logger.error("Exception in updating user connection status with userId: "
					+ userId + "and connectionId:" + connectionId);
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController updateConnectionStatus method");
			return new ResponseDto<User>(false);
		}

	}

	/**
	 * Method is to get all {@link User} connection
	 * 
  @RequestMapping(value = "/getRequestConnection", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<User> getRequestConnection(Long connectionId,String status) {
        logger.info("Inside UserController getUserConnection method");
        try {
            List<User>connections = businessDelegate.getRequestConnection(connectionId,status);
            if (null != connections) {
                logger.info("Exiting from UserController getUserConnection method");
                return new ResponseDto<User>(true, connections);
            }
            logger.info("Exiting from UserController getUserConnection method");
            return new ResponseDto<User>(false);
          } catch (Exception exception) {
            logger.error("Exception in getting user connection with connectionId:" + connectionId);
            logger.error("Exception: " + exception);
            logger.info("Exiting from UserController getRequesteConnection method");
            return new ResponseDto<User>(false);
        }
    }
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/getUserConnection", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<User> getUserConnection(Long userId) {
		logger.info("Inside UserController getUserConnection method");
		try {
			List<User> connections = businessDelegate.getAllConnection(userId);
			if (null != connections) {
				logger.info("Exiting from UserController getUserConnection method");
				return new ResponseDto<User>(true, connections);
			}
			logger.info("Exiting from UserController getUserConnection method");
			return new ResponseDto<User>(false);
		} catch (Exception exception) {
			logger.error("Exception in updating user connection status with userId:"
					+ userId);
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController getUserConnection method");
			return new ResponseDto<User>(false);
		}
	}

	/**
	 * Method is to block {@link User}
	 * 
	 * @param userId
	 * @param blockUserId
	 * @return ResponseDto<User>
	 */
	@RequestMapping(value = "/blockUser", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<User> blockUser(Long userId,
			Long blockUserId) {
		logger.info("Inside UserController blockUser method");
		try {
			if (businessDelegate.blockUser(userId, blockUserId)) {
				logger.debug("User connection is added");
				logger.info("Exiting from UserController blockUser method");
				return new ResponseDto<User>(true);
			} else {
				logger.info("Exiting from UserController blockUser method");
				return new ResponseDto<User>(false);
			}
		} catch (Exception exception) {
			logger.error("Exception in blocking user with userId: "
					+ blockUserId + "and by user with id:" + userId);
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController blockUser method");
			return new ResponseDto<User>(false);
		}
	}

	/**
	 * Web service is to unblock {@link User}
	 *
	 * @param userId
	 * @param blockUserId
	 * @return ResponseDto<User>
	 */
	@RequestMapping(value = "/unBlockUser", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<User> unBlockUser(Long userId,
			Long blockUserId) {
		logger.info("Inside UserController unBlockUser method");
		try {
			if (businessDelegate.unBlockUser(userId, blockUserId)) {
				logger.debug("User connection is added");
				logger.info("Exiting from UserController unBlockUser method");
				return new ResponseDto<User>(true);
			} else {
				logger.info("Exiting from UserController unBlockUser method");
				return new ResponseDto<User>(false);
			}
		} catch (Exception exception) {
			logger.error("Exception in unblocking user with userId: "
					+ blockUserId + "and by user with id:" + userId);
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController unBlockUser method");
			return new ResponseDto<User>(false);
		}
	}

	/**
	 * Method is to save {@link UserUINotification} by userId
	 * 
	 * @param userUINotification
	 * @param userId
	 * @return ResponseDto<User>
	 */
	@RequestMapping(value = "/saveUserUINotification", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<Object> saveUserUINOtification(
			@RequestBody UserUINotification userUINotification,
			@RequestParam Long userId) {
		logger.info("Inside UserController saveUserUINOtification method");
		try {
			Long uINotificationId = businessDelegate.saveUserUINotification(
					userId, userUINotification);
			if (null != uINotificationId) {
				logger.info("Exiting from UserController saveUserUINOtification method");
				return new ResponseDto<Object>(true, uINotificationId);
			} else {
				return new ResponseDto<Object>(false);
			}
		} catch (Exception exception) {
			logger.error("Exception in saving UserUINotifiaction in userId: "
					+ userId);
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController saveUserUINOtification method");
			return new ResponseDto<Object>(false);
		}
	}

	/**
	 * Method is to save {@link UserEmailNotificationSettings} by userId
	 * 
	 * @param userEmailNotificationSettings
	 * @param userId
	 * @return ResponseDto<User>
	 */
	@RequestMapping(value = "/saveUserEmailNotificationSettings", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<Object> saveUserEmailNotificationSettings(
			@RequestBody UserEmailNotificationSettings userEmailNotificationSettings,
			@RequestParam Long userId) {
		logger.info("Inside UserController saveUserEmailNotificationSettings method");
		try {
			Long userENId = businessDelegate.saveUserEmailNotificationSettings(
					userId, userEmailNotificationSettings);
			if (null != userENId) {
				logger.info("Exiting from UserController UserEmailNotificationSettings method");
				return new ResponseDto<Object>(true, userENId);
			} else {
				return new ResponseDto<Object>(false);
			}
		} catch (Exception exception) {
			logger.error("Exception while saving UserEmailNotificationSettings in userId: "
					+ userId);
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController UserEmailNotificationSettings method");
			return new ResponseDto<Object>(false);
		}
	}

	/**
	 * Method is to update {@link UserUINotification}
	 * 
	 * @param userUINotification
	 * @return ResponseDto<UserUINotification>
	 */
	@RequestMapping(value = "/updateUserUINotification", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<UserUINotification> updateUserUINotification(
			@RequestBody UserUINotification userUINotification) {
		logger.info("Inside UserController updateUserUINotification method");
		try {
			if (businessDelegate.updateUserUINotification(userUINotification)) {
				logger.info("Exiting from UserController updateUserUINotification method");
				return new ResponseDto<UserUINotification>(true);
			} else {
				logger.info("Exiting from UserController updateUserUINotification method");
				return new ResponseDto<UserUINotification>(false);
			}
		} catch (Exception exception) {
			logger.error("Exception in reading all user UI notification user with userId:");
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController updateUserUINotification method");
			return new ResponseDto<UserUINotification>(false);
		}
	}

	/**
	 * Method is to update {@link UserEmailNotificationSettings}
	 * 
	 * @param userEmailNotificationSettings
	 * @return ResponseDto<UserEmailNotificationSettings>
	 */
	@RequestMapping(value = "/updateUserEmailNotificationSettings", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<UserEmailNotificationSettings> updateUserEmailNotificationSettings(
			@RequestBody UserEmailNotificationSettings userEmailNotificationSettings) {
		logger.info("Inside UserController updateUserEmailNotificationSettings method");
		try {
			if (businessDelegate
					.updateUserEmailNotificationSettings(userEmailNotificationSettings)) {
				logger.info("Exiting from UserController updateUserEmailNotificationSettings method");
				return new ResponseDto<UserEmailNotificationSettings>(true);
			} else {
				logger.info("Exiting from UserController updateUserEmailNotificationSettings method");
				return new ResponseDto<UserEmailNotificationSettings>(false);
			}
		} catch (Exception exception) {
			logger.error("Exception in reading all user UI notification user with userId:");
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController updateUserEmailNotificationSettings method");
			return new ResponseDto<UserEmailNotificationSettings>(false);
		}
	}

	/**
	 * Web service is to get all {@link UserUINotification} by userId
	 * 
	 * @param userId
	 * @return ResponseDto<UserUINotification>
	 */
	@RequestMapping(value = "/getAllUserUINotification", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<UserUINotification> getAllUserUINotification(
			Long userId) {
		logger.info("Inside UserController updateUserUINotification method");
		try {
			List<UserUINotification> userUINotifications = businessDelegate
					.getAllUserUINotification(userId);
			if (userUINotifications != null) {
				return new ResponseDto<UserUINotification>(true,
						userUINotifications);
			} else {
				return new ResponseDto<UserUINotification>(false);
			}
		} catch (Exception exception) {
			logger.error("Exception in reading all user UI notification user with userId:");
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController updateUserUINotification method");
			return new ResponseDto<UserUINotification>(false);
		}
	}

	/**
	 * Web service is to get all {@link UserEmailNotificationSettings} by userId
	 * 
	 * @param userId
	 * @return ResponseDto<UserEmailNotificationSettings>
	 */
	@RequestMapping(value = "/getAllUserEmailNotificationSettings", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<UserEmailNotificationSettings> getAllUserEmailNotificationSettings(
			Long userId) {
		logger.info("Inside UserController getAllUserEmailNotificationSettings method");
		try {
			List<UserEmailNotificationSettings> userEmailNotificationSettings = businessDelegate
					.getAllUserEmailNotificationSettings(userId);
			if (userEmailNotificationSettings != null) {
				logger.info("Exiting UserController getAllUserEmailNotificationSettings method");
				return new ResponseDto<UserEmailNotificationSettings>(true,
						userEmailNotificationSettings);
			} else {
				return new ResponseDto<UserEmailNotificationSettings>(false);
			}
		} catch (Exception exception) {
			logger.error("Exception in reading all getAllUserEmailNotificationSettings user with userId:");
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController getAllUserEmailNotificationSettings method");
			return new ResponseDto<UserEmailNotificationSettings>(false);
		}
	}

	/**
	 * Method is to save {@link UserPrivacySettings} by userId
	 * 
	 * @param userPrivacySettings
	 * @param userId
	 * @return ResponseDto<UserPrivacySettings>
	 */
	@RequestMapping(value = "/saveUserPrivacySettings", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<Object> saveUserPrivacySettings(
			@RequestBody UserPrivacyData userPrivacySettings,
			@RequestParam Long userId) {
		logger.info("Inside UserController saveUserPrivacySettings method");
		try {
			Long userPSId = businessDelegate.saveUserPrivacySettings(userId,
					userPrivacySettings);
			if (null != userPSId) {
				logger.info("Exiting from UserController saveUserPrivacySettings method");
				return new ResponseDto<Object>(true, userPSId);
			} else {
				logger.info("Exiting from UserController saveUserPrivacySettings method");
				return new ResponseDto<Object>(false);
			}
		} catch (Exception exception) {
			logger.error("Exception in saving UserPrivacySetting by userId: "
					+ userId);
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController saveUserPrivacySettings method");
			return new ResponseDto<Object>(false);
		}
	}

	/**
	 * Method is to update {@link UserPrivacySettings}
	 * 
	 * @param userPrivacySettings
	 * @return ResponseDto<UserPrivacySettings>
	 */
	@RequestMapping(value = "/updateUserPrivacySettings", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<UserPrivacyData> updateUserPrivacySettings(
			@RequestBody UserPrivacyData userPrivacySettings) {
		logger.info("Inside UserController updateUserPrivacySettings method");
		try {
			if (businessDelegate.updateUserPrivacySetting(userPrivacySettings)) {
				logger.info("Exiting from UserController updateUserPrivacySettings method");
				return new ResponseDto<UserPrivacyData>(true);
			} else {
				logger.info("Exiting from UserController updateUserPrivacySettings method");
				return new ResponseDto<UserPrivacyData>(false);
			}
		} catch (Exception exception) {
			logger.error("Exception in updating UserPrivacySetting");
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController updateUserPrivacySettings method");
			return new ResponseDto<UserPrivacyData>(false);
		}
	}
	
	/**
     * Method is to get all {@link User} connection
     * @param connectionId,status
     * @return
     */
    @RequestMapping(value = "/getRequestConnection", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<User> getRequestConnection(Long connectionId,String status) {
        logger.info("Inside UserController getUserConnection method");
        try {
            List<User>connections = businessDelegate.getRequestConnection(connectionId,status);
            if (null != connections) {
                logger.info("Exiting from UserController getUserConnection method");
                return new ResponseDto<User>(true, connections);
            }
            logger.info("Exiting from UserController getUserConnection method");
            return new ResponseDto<User>(false);
          } catch (Exception exception) {
            logger.error("Exception in getting user connection with connectionId:" + connectionId);
            logger.error("Exception: " + exception);
            logger.info("Exiting from UserController getRequesteConnection method");
            return new ResponseDto<User>(false);
        }
    }

	/**
	 * Web service is to get all User privacy data
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/getAllUserPrivacyData", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<UserPrivacyData> getAllUserPrivacyData(
			Long userId) {
		logger.info("Inside UserController getAllUserEmailNotificationSettings method");
		try {
			if (null != userId) {
				List<UserPrivacyData> userPrivacySettings = businessDelegate
						.getAllUserPrivacySettings(userId);
				return new ResponseDto<UserPrivacyData>(true,
						userPrivacySettings);
			} else {
				return new ResponseDto<UserPrivacyData>(true);
			}
		} catch (Exception exception) {
			logger.error("Exception in reading all getAllUserEmailNotificationSettings user with userId:");
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController getAllUserEmailNotificationSettings method");
			return new ResponseDto<UserPrivacyData>(false);
		}
	}

	@RequestMapping(value = "/getTestUser", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody User getTestUser() {
		return getDummyUser();
	}

	private User getDummyUser() {
		User user = new User();
		// user.setId((new Random()).nextLong());
		user.setUsername("username");
		user.setPassword("12345");
		user.setUserprofile(getUserProfile());
		return user;
	}

	private UserProfile getUserProfile() {
		UserProfile userProfile = new UserProfile();
		userProfile.setCity("Indore");
		userProfile.setCountry("India");
		userProfile.setCurrentLocation("Metro");
		userProfile.setHobbies("Chess");
		return userProfile;
	}

	@RequestMapping(value = "/getLoggedInUser", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getLoggedInUser() {
		return FoliticsUtils.getUserName();
	}

	/**
	 * Web service is to subscribe particular user from redis
	 * 
	 * @param userId
	 * @return true if successfully subscribed
	 */

	@RequestMapping(value = "/subscribe", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<Object> subscribe(Long userId)
			throws Exception {
		try {
			businessDelegate.subscribe(userId);
			return new ResponseDto<Object>(true);
		} catch (MessageException exception) {

			logger.error("CustomException while subscribing", exception);

			return new ResponseDto<>(exception.getMessage(), false);
		}

		catch (Exception exception) {

			logger.error("Exception while subscribing", exception);

			return new ResponseDto<>(exception.getMessage(), false);
		}

	}

	/**
	 * Web service is to unSubscribe particular user from redis
	 * 
	 * @param userId
	 * @return true if successfully unSubscribed
	 */

	@RequestMapping(value = "/unSubscribe", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<Object> unSubscribe(Long userId)
			throws Exception {
		try {
			businessDelegate.unSubscribe(userId);
			return new ResponseDto<Object>(true);
		} catch (MessageException exception) {

			logger.error("CustomException while unSubscribing", exception);

			return new ResponseDto<>(exception.getMessage(), false);
		}

		catch (Exception exception) {

			logger.error("Exception while unSubscribing", exception);

			return new ResponseDto<>(exception.getMessage(), false);
		}
	}
	
	/**
	 * Web service to verifyIfUsernameExist
	 * 
	 * @return ResponseDto<username>(success)
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/verifyIfUsernameExist", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<User> verifyIfUsernameExist(String username) {
		logger.info("Inside UserController getAllUsername method");		
		boolean result;
		try {
			result = businessDelegate.verifyIfUsernameExist(username);
			logger.info("Username Match:"+ result);
			if (result != false) {
				logger.info("user with this username:"+username+" found");
				logger.info("Exiting from UserController verifyIfUsernameExist method");
				return new ResponseDto<User>(true);
			}
			logger.info("No user with this username:"+username+" found");
			logger.info("Exiting from UserController verifyIfUsernameExist method");
			return new ResponseDto<User>(false);
			}
		 catch (Exception exception) {
			logger.error("Exception in reading verifyIfUsernameExist method in user: ");
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController verifyIfUsernameExist method");
			return new ResponseDto<User>(false);
		}
		
	}

	
	/**
	 * Web service is to add user connection
	 * 
	 * @param userId
	 * @param connectionId
	 * @return ResponseDto<User>
	 */
	@RequestMapping(value = "/checkconnection", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<UserConnection> connections(Long userId,
			Long connectionId) {
		logger.info("Inside UserController connections method");
		try {
			UserConnection userConnection = businessDelegate.connections(userId,
					connectionId);
			if (null != userConnection) {
				logger.debug("User connection is added");
				logger.info("Exiting from UserController connections method");
				return new ResponseDto<UserConnection>(true,userConnection);
			} else {
				logger.info("Exiting from UserController connections method");
				return new ResponseDto<UserConnection>(false);
			}
		} catch (Exception exception) {
			logger.error("Exception while fatching user connection with userId: "
					+ userId + "and connectionId:" + connectionId);
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController connections method");
			return new ResponseDto<UserConnection>(false);
		}

	}
	
	/**
	 * Web service is to add user achievement
	 * 
	 * @param achievement
	 * @return ResponseDto<Achievement>
	 */
	
	@RequestMapping(value = "/addAchievement", method = RequestMethod.POST)
	 public @ResponseBody ResponseDto<Object> addAchievement(@RequestBody Achievement achievement){
		
		logger.info("Inside add achievement controller");
		try{
			Long id = businessDelegate.add(achievement);			
			
		}catch (Exception exception) {
			logger.error("Exception while adding user achievement");
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController add achievement method");
			return new ResponseDto<Object>(false);
		}	 
		
		logger.info("Exiting add achievement controller");
		
		return new ResponseDto<Object>(true,"successfully added achievement");
		
	}
	
	/**
	 * Web service is to add user achievement
	 * 
	 * @param achievement
	 * @return ResponseDto<Achievement>
	 */
	
	@RequestMapping(value = "/addLeader", method = RequestMethod.POST)
	 public @ResponseBody ResponseDto<Object> addLeader(@RequestBody Leader leader){
		
		logger.info("Inside add leader controller");
		try{
			Long id = businessDelegate.add(leader);			
			
		}catch (Exception exception) {
			logger.error("Exception while adding user leader");
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController add leader method");
			return new ResponseDto<Object>(false);
		}	 
		
		logger.info("Exiting add leader controller");
		
		return new ResponseDto<Object>(true,"successfully added leader");
		
	}

	/**
	 * Web service is to add user politicalView
	 * 
	 * @param achievement
	 * @return ResponseDto<Achievement>
	 */
	
	@RequestMapping(value = "/addPoliticalView", method = RequestMethod.POST)
	 public @ResponseBody ResponseDto<Object> addPoliticalView(@RequestBody PoliticalView politicalView){
		
		logger.info("Inside add politicalView controller");
		try{
			Long id = businessDelegate.add(politicalView);			
			
		}catch (Exception exception) {
			logger.error("Exception while adding user politicalView");
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController add politicalView method");
			return new ResponseDto<Object>(false);
		}	 
		
		logger.info("Exiting add politicalView controller");
		
		return new ResponseDto<Object>(true,"successfully added politicalView");
		
	}

	/**
	 * Web service is to get user achievement by id
	 * 
	 * @param userId
	 * @return ResponseDto<Achievement>
	 */
	
	@RequestMapping(value = "/getAchievement", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Achievement> getAchievement(@RequestParam Long userId){
		
		logger.info("Inside get Achievement method of user controller");
		
		List<Achievement> achievement;
		
		try{
			if(userId==null){
				logger.error("userId can not be null");
			}
			
			achievement = businessDelegate.getAchievement(userId);
			
			if(achievement==null){
				logger.info("Exiting get achievement method of user controller");
				return  new ResponseDto<Achievement>("No data found in database for the corresponding userId",true);
			}
			
		}catch(Exception exception){
			logger.error("Exception while fatching user achievement");
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController get achievement method");
			return new ResponseDto<Achievement>(false);
		}
		
		logger.info("Exiting get Achievement method of user controller");
		return  new ResponseDto<Achievement>(true, achievement, "successfully fatched user achievement");
	}
	
	/**
	 * Web service is to get user Leader by id
	 * 
	 * @param userId
	 * @return ResponseDto<Leader>
	 */
	
	@RequestMapping(value = "/getLeader", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Leader> getLeader(@RequestParam Long userId){
		
		logger.info("Inside get leader method of user controller");
		
		List<Leader> leader;
		
		try{
			if(userId==null){
				logger.error("userId can not be null");
			}
			
			leader = businessDelegate.getLeader(userId);
			
			if(leader==null){
				logger.info("Exiting get leader method of user controller");
				return  new ResponseDto<Leader>("No data found in database for the corresponding userId",true);
			}
			
		}catch(Exception exception){
			logger.error("Exception while fatching user leader");
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController get leader method");
			return new ResponseDto<Leader>(false);
		}
		
		logger.info("Exiting get leader method of user controller");
		return  new ResponseDto<Leader>(true, leader, "successfully fatched leader");
	}
	
	/**
	 * Web service is to get user PoliticalView by id
	 * 
	 * @param userId
	 * @return ResponseDto<PoliticalView>
	 */
	
	@RequestMapping(value = "/getPoliticalView", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<PoliticalView> getPoliticalView(@RequestParam Long userId){
		
		logger.info("Inside get PoliticalView method of user controller");
		
		List<PoliticalView> politicalView;
		
		try{
			if(userId==null){
				logger.error("userId can not be null");
			}
			
			politicalView = businessDelegate.getPoliticalView(userId);
			
			if(politicalView==null){
				logger.info("Exiting get PoliticalView method of user controller");
				return  new ResponseDto<PoliticalView>("No data found in database for the corresponding userId",true);
			}
			
		}catch(Exception exception){
			logger.error("Exception while fatching user PoliticalView");
			logger.error("Exception: " + exception);
			logger.info("Exiting from UserController get PoliticalView method");
			return new ResponseDto<PoliticalView>(false);
		}
		
		logger.info("Exiting get PoliticalView method of user controller");
		return  new ResponseDto<PoliticalView>(true, politicalView, "successfully fatched user PoliticalView");
	}

}
