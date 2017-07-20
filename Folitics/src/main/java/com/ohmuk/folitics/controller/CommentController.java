package com.ohmuk.folitics.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohmuk.folitics.beans.CommentBean;
import com.ohmuk.folitics.businessDelegate.interfaces.ICommentBusinessDelegate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.exception.MessageException;

/**
 * Controller for Comment
 * 
 * @author Harish
 *
 */

@Controller
@RequestMapping("/comment")
public class CommentController {

	private static Logger logger = Logger.getLogger(CommentController.class);

	@Autowired
	private ICommentBusinessDelegate businessDelegate;

	/**
	 * Spring web service(POST) for Comment on the component
	 * 
	 * @author Harish
	 * @param: com.ohmuk.folitics.beans.CommentBean
	 * 
	 * @return com.ohmuk.folitics.dto.ResponseDto<java.lang.Object>
	 */
	@RequestMapping(value = "/comment", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<Object> comment(
			@RequestBody CommentBean commentBean) {

		logger.info("Inside CommentController Comment method");
		logger.debug("Input componentType = " + commentBean.getComponentType()
				+ ", componentId = " + commentBean.getComponentId()
				+ " and userId = " + commentBean.getUserId());

		Object commentObject = new Object();
		try {

			logger.info("Trying to save comment. Entering commentBusinessDelegate create method");
			commentObject = businessDelegate.create(commentBean);

		} catch (MessageException exception) {

			logger.error("Error while updating Comment for componentType = "
					+ commentBean.getComponentType() + ", componentId = "
					+ commentBean.getComponentId() + " and userId = "
					+ commentBean.getUserId());
			logger.error("Error is ", exception);
			logger.info("Exiting edit method due to error");

			return new ResponseDto<Object>(false, null, exception.getMessage());

		} catch (Exception exception) {

			logger.error("Error while updating Comment for componentType = "
					+ commentBean.getComponentType() + ", componentId = "
					+ commentBean.getComponentId() + " and userId = "
					+ commentBean.getUserId());
			logger.error("Error is ", exception);
			logger.info("Exiting edit method due to error");

			return new ResponseDto<Object>(false, null, exception.getMessage());
		}

		if (commentObject != null) {

			logger.info("updated Comment. Exiting edit method");
			return new ResponseDto<Object>(true, commentObject);
		}

		logger.info("Comment object after updating is null. Exiting edit method");
		return new ResponseDto<Object>(false);
	}

	/**
	 * Spring web service(POST) for edit on the component
	 * 
	 * @author Harish
	 * @param: com.ohmuk.folitics.beans.CommentBean
	 * @return com.ohmuk.folitics.dto.ResponseDto<java.lang.Object>
	 */

	@RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<Object> edit(
			@RequestBody CommentBean commentBean) {

		logger.info("Inside CommentController Comment method");
		logger.debug("Input componentType = " + commentBean.getComponentType()
				+ ", componentId = " + commentBean.getComponentId()
				+ " and userId = " + commentBean.getUserId());

		Object commentObject = new Object();
		try {

			logger.info("Trying to update comment. Entering commentBusinessDelegate create method");
			commentObject = businessDelegate.update(commentBean);

		} catch (MessageException exception) {

			logger.error("Error while updating Comment for componentType = "
					+ commentBean.getComponentType() + ", componentId = "
					+ commentBean.getComponentId() + " and userId = "
					+ commentBean.getUserId());
			logger.error("Error is ", exception);
			logger.info("Exiting Comment method due to error");

			return new ResponseDto<Object>(false, null, exception.getMessage());

		} catch (Exception exception) {

			logger.error("Error while updating Comment for componentType = "
					+ commentBean.getComponentType() + ", componentId = "
					+ commentBean.getComponentId() + " and userId = "
					+ commentBean.getUserId());
			logger.error("Error is ", exception);
			logger.info("Exiting Comment method due to error");

			return new ResponseDto<Object>(false, null, exception.getMessage());
		}

		if (commentObject != null) {

			logger.info("Saved Comment. Exiting Comment method");
			return new ResponseDto<Object>(true, commentObject);
		}

		logger.info("Comment object after updating is null. Exiting Comment method");
		return new ResponseDto<Object>(false);
	}

	/**
	 * Spring web service(POST) for finding on the component with id and
	 * component type for the userid
	 * 
	 * @author Harish
	 * @param java
	 *            .lang.String componentType
	 * @param java
	 *            .lang.Long componentId
	 * @param java
	 *            .lang.Long userId
	 * @return 
	 *         com.ohmuk.folitics.dto.ResponseDto<java.util.List<java.lang.Object
	 *         >
	 */
	@RequestMapping(value = "/getByComponentIdAndUserId", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Object> getByComponentIdAndUserId(
			String componentType, Long componentId, Long userId) {

		logger.info("Inside CommentController find method");
		logger.debug("Input componentType = " + componentType
				+ ", componentId = " + componentId + " and userId = " + userId);

		List<Object> commentObject;
		try {

			logger.info("Trying to get comment object. Entering CommentBusinessDelegate getByComponentIdAndUserId method");
			commentObject = businessDelegate.getByComponentIdAndUserId(
					componentId, userId, componentType);

		} catch (MessageException e) {

			logger.error("Error while getting comment object for componentType = "
					+ componentType
					+ ", componentId = "
					+ componentId
					+ " and userId = " + userId);
			logger.error("Error is ", e);
			logger.info("Exiting getByComponentIdAndUserId method due to error");

			return new ResponseDto<Object>(false, null, e.getMessage());

		} catch (Exception e) {

			logger.error("Error while getting comment object for componentType = "
					+ componentType
					+ ", componentId = "
					+ componentId
					+ " and userId = " + userId);
			logger.error("Error is ", e);
			logger.info("Exiting getByComponentIdAndUserId method due to error");

			return new ResponseDto<Object>(false, null, e.getMessage());

		}

		if (commentObject != null) {

			logger.info("Got comment object. Exiting getByComponentIdAndUserId method");
			return new ResponseDto<Object>(true, commentObject);
		}

		logger.info("comment object found null. Exiting getByComponentIdAndUserIdO method");
		return new ResponseDto<Object>(false);
	}

	/**
	 * Spring web service(POST) for getting all comments component with id and
	 * component type for the userid
	 * 
	 * @author Harish
	 * @param java
	 *            .lang.String componentType
	 * @param java
	 *            .lang.Long componentId
	 * 
	 * @return 
	 *         com.ohmuk.folitics.dto.ResponseDto<java.util.List<java.lang.Object
	 *         >
	 */
	@RequestMapping(value = "/getAllCommentsForComponentId", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Object> getAllCommentsForComponentId(
			String componentType, Long componentId) {

		logger.info("Inside CommentController getAllCommentsForCommentId method");
		logger.debug("Input componentType = " + componentType
				+ ", componentId = " + componentId);

		List<Object> commentObject;

		try {

			logger.info("Trying to get comment list. Entering CommentBusinessDelegate getByComponentIdAndUserId method");
			commentObject = businessDelegate.getAllCommentsForComponent(
					componentId, componentType);

		} catch (MessageException e) {

			logger.error("Error while getting comment List for componentType = "
					+ componentType + ", componentId = " + componentId);
			logger.error("Error is ", e);
			logger.info("Exiting getAllCommentsForComponentId method due to error");

			return new ResponseDto<Object>(false, null, e.getMessage());

		} catch (Exception e) {

			logger.error("Error while getting comment List for componentType = "
					+ componentType + ", componentId = " + componentId);
			logger.error("Error is ", e);
			logger.info("Exiting getAllCommentsForComponentId method due to error");

			return new ResponseDto<Object>(false, null, e.getMessage());

		}

		if (commentObject != null) {

			logger.info("Got comment List. Exiting getAllCommentsForComponentId method");
			return new ResponseDto<Object>(true, commentObject);
		}

		logger.info("comment List found null. Exiting getAllCommentsForComponentId method");
		return new ResponseDto<Object>(false);
	}

	/**
	 * Spring web service(POST) for getting all comments for a particular userId
	 * 
	 * 
	 * @author Harish
	 * @param java
	 *            .lang.String componentType
	 * 
	 * @param java
	 *            .lang.Long userId
	 * @return 
	 *         com.ohmuk.folitics.dto.ResponseDto<java.util.List<java.lang.Object
	 *         >
	 */
	@RequestMapping(value = "/getAllCommentsForUserId", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Object> getAllCommentsForUserId(
			String componentType, Long userId) {

		logger.info("Inside CommentController getAllCommentsForUserId method");
		logger.debug("Input componentType = " + componentType);

		List<Object> commentObject;

		try {

			logger.info("Trying to get comment list. Entering CommentBusinessDelegate getAllCommentsForUserId method");
			commentObject = businessDelegate.getAllCommentsForUserId(userId,
					componentType);

		} catch (MessageException e) {

			logger.error("Error while getting comment list for componentType = "
					+ componentType + ", userId = " + userId);
			logger.error("Error is ", e);
			logger.info("Exiting getAllCommentsForComponentId method due to error");

			return new ResponseDto<Object>(false, null, e.getMessage());

		} catch (Exception e) {

			logger.error("Error while getting comment list for componentType = "
					+ componentType + ", userId = " + userId);
			logger.error("Error is ", e);
			logger.info("Exiting getAllCommentsForUserId method due to error");

			return new ResponseDto<Object>(false, null, e.getMessage());

		}

		if (commentObject != null) {

			logger.info("Got comment List. Exiting getAllCommentsForUserId method");
			return new ResponseDto<Object>(true, commentObject);
		}

		logger.info("comment List found null. Exiting getAllCommentsForUserId method");
		return new ResponseDto<Object>(false);
	}

	/**
	 * Spring web service(POST) for getting all comments from database.
	 * 
	 * 
	 * @author Harish
	 * @param java
	 *            .lang.String componentType
	 * 
	 * @return 
	 *         com.ohmuk.folitics.dto.ResponseDto<java.util.List<java.lang.Object
	 *         >
	 */
	@RequestMapping(value = "/getAllComments", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Object> getAllComments(String componentType) {

		logger.info("Inside CommentController getAllComments method");
		logger.debug("Input componentType = " + componentType);

		List<Object> commentObject;

		try {

			logger.info("Trying to get comment list. Entering CommentBusinessDelegate getAllComments method");
			commentObject = businessDelegate.readAll(componentType);

		} catch (MessageException e) {

			logger.error("Error while getting all comments  for componentType = "
					+ componentType);
			logger.error("Error is ", e);
			logger.info("Exiting getAllComments method due to error");

			return new ResponseDto<Object>(false, null, e.getMessage());

		} catch (Exception e) {

			logger.error("Error while getting comment list for componentType = "
					+ componentType);
			logger.error("Error is ", e);
			logger.info("Exiting getAllComments method due to error");

			return new ResponseDto<Object>(false, null, e.getMessage());

		}

		if (commentObject != null) {

			logger.info("Got comment List. Exiting getAllComments method");
			return new ResponseDto<Object>(true, commentObject);
		}

		logger.info("comment List found null. Exiting getAllComments method");
		return new ResponseDto<Object>(false);
	}

	/**
	 * Spring web service(Post) for deleting the component with id and component
	 * type
	 * 
	 * @author Harish
	 * @param java
	 *            .lang.String componentType
	 * @param java
	 *            .lang.Long id
	 * @return com.ohmuk.folitics.dto.ResponseDto<java.lang.Object>
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<Object> delete(String componentType,
			Long id) {
		logger.info("Inside commentController delete method");
		logger.debug("Input componentType = " + componentType
				+ "and componentId = " + id);

		Object commentObject = new Object();
		try {

			logger.info("Trying to delete Comment object. Entering CommentBusinessDelegate delete method");
			commentObject = businessDelegate.delete(id, componentType);

			if (commentObject == null) {

				logger.info("Deleted comment object. Exiting delete method");
				return new ResponseDto<Object>(true, commentObject);
			} else {

				logger.info("comment object not null. Exiting delete method");
				return new ResponseDto<Object>(false);
			}

		} catch (MessageException exception) {

			logger.error("Error while deleting comment object for componentType = "
					+ componentType + ", componentId = " + id);
			logger.error("Error is ", exception);
			logger.info("Exiting delete method due to error");

			return new ResponseDto<Object>(false, null, exception.getMessage());

		}

		catch (Exception exception) {
			logger.error("Error while deleting comment object for componentType = "
					+ componentType + ", componentId = " + id);

			logger.error("Error is ", exception);
			logger.info("Exiting delete method due to error");

			return new ResponseDto<Object>(false, null, exception.getMessage());

		}

	}

}
