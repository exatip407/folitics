package com.ohmuk.folitics.service.comment;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.beans.CommentBean;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Sentiment;
import com.ohmuk.folitics.hibernate.entity.comment.SentimentComment;
import com.ohmuk.folitics.hibernate.entity.comment.SentimentCommentCount;
import com.ohmuk.folitics.hibernate.entity.comment.SentimentCommentCountId;
import com.ohmuk.folitics.hibernate.repository.comment.ISentimentCommentRepository;
import com.ohmuk.folitics.util.DateUtils;

@Service
@Transactional
public class SentimentCommentService implements
		ICommentService<SentimentComment> {

	private static Logger logger = LoggerFactory
			.getLogger(SentimentCommentService.class);

	@Autowired
	private ICommentCountService<SentimentCommentCount> sentimentCommentCountService;

	@Autowired
	private ISentimentCommentRepository sentimentCommentRepository;

	@Override
	public SentimentComment create(CommentBean commentBean)
			throws MessageException, Exception {
		logger.debug("Inside create  SentimentComment Service");

		if (commentBean.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId found to be null"));
		}

		if (commentBean.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId found to be null"));
		}

		// create SentimentComment object from comment bean
		SentimentComment sentimentComment = new SentimentComment();
		sentimentComment.setUserId(commentBean.getUserId());
		sentimentComment.setComponentId(commentBean.getComponentId());
		sentimentComment.setComponentType(commentBean.getComponentType());
		sentimentComment.setComment(commentBean.getComment());

		sentimentCommentRepository.save(sentimentComment);

		// add monetization points to user for Comment on any component
		sentimentCommentRepository
				.addMonetizationPoints(commentBean, "Comment");

		// Adding counter for the Comment
		SentimentCommentCount sentimentCommentCount = new SentimentCommentCount();
		Sentiment sentiment = new Sentiment();
		sentiment.setId(commentBean.getComponentId());

		SentimentCommentCountId sentimentCommentCountId = new SentimentCommentCountId();
		sentimentCommentCountId.setSentiment(sentiment);

		sentimentCommentCount.setId(sentimentCommentCountId);

		SentimentCommentCount sentimentCommentCount2 = sentimentCommentCountService
				.getByComponentId(commentBean.getComponentId());

		
		// if count avaialble for component adding counter else entering counter
		// for the first time for user and component

		if (sentimentCommentCount2 != null) {
			sentimentCommentCount2.setCommentCount(sentimentCommentCount2
					.getCommentCount() + 1);
			sentimentCommentCountService.create(sentimentCommentCount2);
		} else {
			sentimentCommentCount.setCommentCount(1l);
			sentimentCommentCountService.create(sentimentCommentCount);
		}

		logger.debug("Exiting create Comment");
		return sentimentComment;

	}

	@Override
	public SentimentComment read(Long id) throws MessageException {
		logger.info("Entered SentimentComment service read method");
		if (id == null) {
			logger.error("Id found to be null");
			throw new MessageException("Id found to be null");
		}

		SentimentComment taskComment = sentimentCommentRepository.find(id);

		logger.info("Exiting SentimentComment service read method");
		return taskComment;
	}

	@Override
	public List<SentimentComment> readAll() {

		logger.info("Entered SentimentComment service readAll method");
		logger.debug("Getting all SentimentComment");
		return sentimentCommentRepository.findAll();
	}

	@Override
	public SentimentComment update(CommentBean commentBean)
			throws MessageException {

		logger.debug("Inside Update Comment method");

		SentimentComment originalData = sentimentCommentRepository
				.find(commentBean.getId());
		if (originalData == null) {
			logger.error("No Record found in database for update");
			throw (new MessageException(
					"No Record found in database for update"));
		}
		originalData.setEditedTime(DateUtils.getSqlTimeStamp());

		SentimentComment sentimentComment = sentimentCommentRepository
				.save(originalData);

		if (sentimentComment == null) {
			logger.error("Something went wrong, record not updated");
			throw (new MessageException(
					"Something went wrong, record not updated"));
		}

		logger.debug("Updated comment  : " + sentimentComment);
		logger.debug("Exiting Update");

		return sentimentComment;
	}

	@Override
	public SentimentComment delete(Long id) throws MessageException, Exception {
		logger.info("Inside hard delete comment by ID");

		if (id == null) {
			logger.error("Comment ID found to be null");
			throw (new MessageException("Comment ID can't be null"));
		}

		SentimentComment sentimentComment = sentimentCommentRepository.find(id);

		if (sentimentComment == null) {
			logger.error("Something went wrong, record not found for given id: "
					+ id + ", Can't delete record.");
			throw (new MessageException(
					"Something went wrong, record not found for given id: "
							+ id + ", Can't delete record."));
		}

		SentimentCommentCount sentimentCommentCount = sentimentCommentCountService
				.getByComponentId(id);

		if (sentimentCommentCount != null) {

			sentimentCommentCountService.delete(sentimentCommentCount);

		}

		sentimentCommentRepository.delete(id);

		SentimentComment sComment = sentimentCommentRepository.find(id);

		logger.debug("Deleted Comment :" + sentimentComment);
		logger.debug("Exiting  delete comment by ID");

		return sComment;
	}

	@Override
	public List<SentimentComment> getByComponentIdAndUserId(Long componentId,
			Long userId) throws MessageException {
		logger.debug("Entered getByComponentIdAndUserId Comment");

		List<SentimentComment> sentimentComments = null;
		sentimentComments = sentimentCommentRepository.getByComponentIdAndUserId(
				componentId, userId);

		logger.debug("Comment fetched: " + sentimentComments.size());
		logger.debug("Exiting getByComponentIdAndUserId comment");

		return sentimentComments;
	}

	@Override
	public List<SentimentComment> getAllCommentsForComponent(Long componentId)
			throws MessageException {
		logger.debug("Entered getAllCommentsForComponent method");

		List<SentimentComment> sentimentComments = null;
		sentimentComments = sentimentCommentRepository
				.getAllCommentsForComponent(componentId);

		logger.debug("Comment fetched: " + sentimentComments.size());
		logger.debug("Exiting getAllCommentsForComponent method");

		return sentimentComments;
	}

	@Override
	public List<SentimentComment> getAllCommentsForUserId(Long userId)
			throws MessageException {
		logger.debug("Entered getAllCommentsForUserId method");

		List<SentimentComment> sentimentComments = null;
		sentimentComments = sentimentCommentRepository
				.getAllCommentsForUserId(userId);

		logger.debug("Comment fetched: " + sentimentComments.size());
		logger.debug("Exiting getAllCommentsForUserId method");

		return sentimentComments;
	}

}
