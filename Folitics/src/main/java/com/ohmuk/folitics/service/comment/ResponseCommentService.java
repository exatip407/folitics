package com.ohmuk.folitics.service.comment;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.beans.CommentBean;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Response;
import com.ohmuk.folitics.hibernate.entity.comment.ResponseComment;
import com.ohmuk.folitics.hibernate.entity.comment.ResponseCommentCount;
import com.ohmuk.folitics.hibernate.entity.comment.ResponseCommentCountId;
import com.ohmuk.folitics.hibernate.repository.comment.IResponseCommentRepository;
import com.ohmuk.folitics.util.DateUtils;

@Service
@Transactional
public class ResponseCommentService implements ICommentService<ResponseComment> {

	private static Logger logger = LoggerFactory
			.getLogger(ResponseCommentService.class);

	@Autowired
	private ICommentCountService<ResponseCommentCount> responseCommmentCountService;

	@Autowired
	private IResponseCommentRepository responseCommentRepository;

	@Override
	public ResponseComment create(CommentBean commentBean)
			throws MessageException, Exception {

		logger.debug("Inside create  ResponseComment Service");

		if (commentBean.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId found to be null"));
		}

		if (commentBean.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId found to be null"));
		}

		// create ResponseComment object from comment bean
		ResponseComment reponseComment = new ResponseComment();
		reponseComment.setUserId(commentBean.getUserId());
		reponseComment.setComponentId(commentBean.getComponentId());
		reponseComment.setComponentType(commentBean.getComponentType());
		reponseComment.setComment(commentBean.getComment());

		responseCommentRepository.save(reponseComment);

		// add monetization points to user for Comment on any component
		responseCommentRepository.addMonetizationPoints(commentBean, "Comment");

		// Adding counter for the Comment
		ResponseCommentCount responseCommentCount = new ResponseCommentCount();
		Response response = new Response();
		response.setId(commentBean.getComponentId());

		ResponseCommentCountId responseCommentCountId = new ResponseCommentCountId();
		responseCommentCountId.setResponse(response);

		responseCommentCount.setId(responseCommentCountId);

		ResponseCommentCount responseCommentCount2 = responseCommmentCountService
				.getByComponentId(commentBean.getComponentId());

		// if count avaialble for component adding counter else entering counter
		// for the first time for user and component

		if (responseCommentCount2 != null) {
			responseCommentCount2.setCommentCount(responseCommentCount2
					.getCommentCount() + 1);
			responseCommmentCountService.create(responseCommentCount2);
		} else {
			responseCommentCount.setCommentCount(1l);
			responseCommmentCountService.create(responseCommentCount);
		}

		logger.debug("Exiting create Comment");
		return reponseComment;

	}

	@Override
	public ResponseComment read(Long id) throws MessageException {
		logger.info("Entered ResponseComment service read method");
		if (id == null) {
			logger.error("Id found to be null");
			throw new MessageException("Id found to be null");
		}

		ResponseComment responseComment = responseCommentRepository.find(id);

		logger.info("Exiting ResponseComment service read method");
		return responseComment;
	}

	@Override
	public List<ResponseComment> readAll() {
		logger.info("Entered ResponseComment service readAll method");
		logger.debug("Getting all ResponseComment");
		return responseCommentRepository.findAll();
	}

	@Override
	public ResponseComment update(CommentBean commentBean)
			throws MessageException {
		logger.debug("Inside Update Comment method");

		ResponseComment originalData = responseCommentRepository
				.find(commentBean.getId());
		if (originalData == null) {
			logger.error("No Record found in database for update");
			throw (new MessageException(
					"No Record found in database for update"));
		}
		originalData.setEditedTime(DateUtils.getSqlTimeStamp());

		ResponseComment responseComment = responseCommentRepository
				.save(originalData);

		if (responseComment == null) {
			logger.error("Something went wrong, record not updated");
			throw (new MessageException(
					"Something went wrong, record not updated"));
		}

		logger.debug("Updated comment  : " + responseComment);
		logger.debug("Exiting Update");

		return responseComment;
	}

	@Override
	public ResponseComment delete(Long id) throws MessageException, Exception {
		logger.info("Inside hard delete comment by ID");

		if (id == null) {
			logger.error("Comment ID found to be null");
			throw (new MessageException("Comment ID can't be null"));
		}

		ResponseComment resComment = responseCommentRepository.find(id);

		if (resComment == null) {
			logger.error("Something went wrong, record not found for given id: "
					+ id + ", Can't delete record.");
			throw (new MessageException(
					"Something went wrong, record not found for given id: "
							+ id + ", Can't delete record."));
		}

		ResponseCommentCount responseCommentCount = responseCommmentCountService
				.getByComponentId(id);

		if (responseCommentCount != null) {

			responseCommmentCountService.delete(responseCommentCount);

		}

		responseCommentRepository.delete(id);

		ResponseComment responseComment = responseCommentRepository.find(id);

		logger.debug("Deleted Comment :" + resComment);
		logger.debug("Exiting  delete comment by ID");

		return responseComment;
	}

	@Override
	public List<ResponseComment> getByComponentIdAndUserId(Long componentId,
			Long userId) throws MessageException {
		logger.info("Entered getByComponentIdAndUserId Comment");

		List<ResponseComment> responseComments = null;
		responseComments = responseCommentRepository.getByComponentIdAndUserId(
				componentId, userId);

		logger.debug("Comment fetched: " + responseComments.size());
		logger.info("Exiting getByComponentIdAndUserId comment");

		return responseComments;
	}

	@Override
	public List<ResponseComment> getAllCommentsForComponent(Long componentId)
			throws MessageException {
		logger.info("Entered getAllCommentsForComponent method");

		List<ResponseComment> taskComments = null;
		taskComments = responseCommentRepository
				.getAllCommentsForComponent(componentId);

		logger.debug("Comment fetched: " + taskComments.size());
		logger.info("Exiting getAllCommentsForComponent method");

		return taskComments;
	}

	@Override
	public List<ResponseComment> getAllCommentsForUserId(Long userId)
			throws MessageException {
		logger.debug("Entered getAllCommentsForUserId method");

		List<ResponseComment> responseComments = null;
		responseComments = responseCommentRepository
				.getAllCommentsForUserId(userId);

		logger.debug("Comment fetched: " + responseComments.size());
		logger.debug("Exiting getAllCommentsForUserId method");

		return responseComments;
	}

}
