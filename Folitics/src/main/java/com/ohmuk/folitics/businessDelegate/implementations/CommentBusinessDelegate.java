package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.beans.CommentBean;
import com.ohmuk.folitics.businessDelegate.interfaces.ICommentBusinessDelegate;
import com.ohmuk.folitics.constants.Constants;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.service.comment.ICommentService;

@Component
public class CommentBusinessDelegate implements ICommentBusinessDelegate {

	private static Logger logger = LoggerFactory
			.getLogger(CommentBusinessDelegate.class);

	@SuppressWarnings("rawtypes")
	@Autowired
	private volatile Map<String, ICommentService> commentServiceMap;

	@Override
	public Object create(CommentBean commentBean) throws MessageException,
			Exception {

		logger.info("Inside Comment BusinessDelegate create method");

		if (commentBean.getComponentId() == null) {

			logger.error("Component id is null");
			throw (new MessageException("Component id is null"));

		}

		if (commentBean.getUserId() == null) {

			logger.error("User Id has null value");
			throw (new MessageException("User Id has null value"));

		}

		if (commentBean.getComponentType() == null) {

			logger.error("Componenttype is unspecified");
			throw (new MessageException("Componenttype is unspecified"));

		}

		logger.debug("Passing CommentData bean reference " + commentBean
				+ " to Service");

		@SuppressWarnings("rawtypes")
		ICommentService commentService = commentServiceMap.get(commentBean
				.getComponentType() + Constants.COMMENT_SERVICE_SUFFIX);

		logger.info("Exiting Comment BusinessDelegate create method");
		return commentService.create(commentBean);
	}

	@Override
	public Object read(Long id, String componentType) throws MessageException {

		logger.info("Inside CommentBusinessDelegate read method");

		Object objectToReturn = new Object();

		@SuppressWarnings("rawtypes")
		ICommentService commentService = commentServiceMap.get(componentType
				+ Constants.COMMENT_SERVICE_SUFFIX);

		objectToReturn = commentService.read(id);

		logger.info("Exiting Comment BusinessDelegate read method");
		return objectToReturn;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> readAll(String componentType) throws MessageException {

		logger.info("Inside CommentBusinessDelegate readAll method");

		if (componentType == null) {

			logger.error("User Id has null value");
			throw (new MessageException("User Id has null value"));

		}

		List<Object> objectToReturn;

		@SuppressWarnings("rawtypes")
		ICommentService commentService = commentServiceMap.get(componentType
				+ Constants.COMMENT_SERVICE_SUFFIX);

		objectToReturn = commentService.readAll();

		logger.info("Exiting Comment BusinessDelegate read method");
		return objectToReturn;
	}

	@Override
	public Object update(CommentBean commentBean) throws MessageException {

		logger.info("Inside Comment BusinessDelegate create method");

		Object objectToReturn = new Object();

		if (commentBean.getComponentId() == null) {

			logger.error("Component id is null");
			throw (new MessageException("Component id is null"));

		}

		if (commentBean.getUserId() == null) {

			logger.error("User Id has null value");
			throw (new MessageException("User Id has null value"));

		}

		if (commentBean.getComponentType() == null) {

			logger.error("Componenttype is unspecified");
			throw (new MessageException("Componenttype is unspecified"));

		}

		logger.debug("Passing CommentData bean reference " + commentBean
				+ " to Service");

		@SuppressWarnings("rawtypes")
		ICommentService commentService = commentServiceMap.get(commentBean
				.getComponentType() + Constants.COMMENT_SERVICE_SUFFIX);

		objectToReturn = commentService.update(commentBean);
		return objectToReturn;

	}

	@Override
	public Object delete(Long id, String componentType) throws Exception,
			MessageException {

		Object objectToReturn = new Object();

		// get the service based on the component type from the
		// likeServiceMap

		@SuppressWarnings("rawtypes")
		ICommentService commentService = commentServiceMap.get(componentType
				+ Constants.COMMENT_SERVICE_SUFFIX);

		objectToReturn = commentService.delete(id);

		return objectToReturn;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getByComponentIdAndUserId(Long componentId,
			Long userId, String componentType) throws MessageException {

		logger.info("Inside CommentBusinessDelegate getByComponentIdAndUserId method");

		List<Object> objectToReturn;

		@SuppressWarnings("rawtypes")
		ICommentService commentService = commentServiceMap.get(componentType
				+ Constants.COMMENT_SERVICE_SUFFIX);

		objectToReturn = commentService.getByComponentIdAndUserId(componentId,
				userId);

		logger.info("Exiting Comment BusinessDelegate getByComponentIdAndUserId method");
		return objectToReturn;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getAllCommentsForComponent(Long componentId,
			String componentType) throws MessageException {

		logger.info("Inside CommentBusinessDelegate getAllCommentsForComponent method");

		List<Object> objectToReturn;

		@SuppressWarnings("rawtypes")
		ICommentService commentService = commentServiceMap.get(componentType
				+ Constants.COMMENT_SERVICE_SUFFIX);

		objectToReturn = commentService.getAllCommentsForComponent(componentId);

		logger.info("Exiting Comment BusinessDelegate getAllCommentsForComponent method");
		return objectToReturn;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getAllCommentsForUserId(Long userId,
			String componentType) throws MessageException {
		logger.info("Inside CommentBusinessDelegate getAllCommentsForUserId method");

		List<Object> objectToReturn;

		@SuppressWarnings("rawtypes")
		ICommentService commentService = commentServiceMap.get(componentType
				+ Constants.COMMENT_SERVICE_SUFFIX);

		objectToReturn = commentService.getAllCommentsForUserId(userId);

		logger.info("Exiting Comment BusinessDelegate getAllCommentsForUserId method");
		return objectToReturn;
	}

}
