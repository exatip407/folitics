package com.ohmuk.folitics.service.comment;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.beans.CommentBean;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.GovtSchemeData;
import com.ohmuk.folitics.hibernate.entity.comment.GovtSchemeDataComment;
import com.ohmuk.folitics.hibernate.entity.comment.GovtSchemeDataCommentCount;
import com.ohmuk.folitics.hibernate.entity.comment.GovtSchemeDataCommentCountId;
import com.ohmuk.folitics.hibernate.entity.share.SentimentShare;
import com.ohmuk.folitics.hibernate.entity.share.SentimentShareCount;
import com.ohmuk.folitics.hibernate.repository.comment.IGovtSchemeDataCommentRepository;
import com.ohmuk.folitics.util.DateUtils;

@Service
@Transactional
public class GovtSchemeDataCommentService implements
		ICommentService<GovtSchemeDataComment> {

	private static Logger logger = LoggerFactory
			.getLogger(GovtSchemeDataCommentService.class);

	@Autowired
	private ICommentCountService<GovtSchemeDataCommentCount> govtshemecommentcountService;

	@Autowired
	private IGovtSchemeDataCommentRepository govtSchemeDataCommentRepository;

	@Override
	public GovtSchemeDataComment create(CommentBean commentBean)
			throws MessageException, Exception {

		logger.debug("Inside create  GovtSchemeDataComment Service");

		if (commentBean.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId found to be null"));
		}

		if (commentBean.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId found to be null"));
		}

		// create GovtSchemeDataComment object from comment bean
		GovtSchemeDataComment govtSchemeDataComment = new GovtSchemeDataComment();
		govtSchemeDataComment.setUserId(commentBean.getUserId());
		govtSchemeDataComment.setComponentId(commentBean.getComponentId());
		govtSchemeDataComment.setComponentType(commentBean.getComponentType());
		govtSchemeDataComment.setComment(commentBean.getComment());

		govtSchemeDataCommentRepository.save(govtSchemeDataComment);

		// add monetization points to user for Comment on any component
		govtSchemeDataCommentRepository.addMonetizationPoints(commentBean,
				"Comment");

		// Adding counter for the Comment
		GovtSchemeDataCommentCount govtSchemeDataCommentCount = new GovtSchemeDataCommentCount();
		GovtSchemeData govtSchemeData = new GovtSchemeData();
		govtSchemeData.setId(commentBean.getComponentId());

		GovtSchemeDataCommentCountId govtSchemeDataCommentCountId = new GovtSchemeDataCommentCountId();
		govtSchemeDataCommentCountId.setGovtSchemeData(govtSchemeData);

		govtSchemeDataCommentCount.setId(govtSchemeDataCommentCountId);

		GovtSchemeDataCommentCount govtSchemeDataCommentCount2 = govtshemecommentcountService
				.getByComponentId(commentBean.getComponentId());

		// if count available for component adding counter else entering counter
		// for the first time for user and component

		if (govtSchemeDataCommentCount2 != null) {
			govtSchemeDataCommentCount2
					.setCommentCount(govtSchemeDataCommentCount2
							.getCommentCount() + 1);
			govtshemecommentcountService.create(govtSchemeDataCommentCount2);
		} else {
			govtSchemeDataCommentCount.setCommentCount(1l);
			govtshemecommentcountService.create(govtSchemeDataCommentCount);
		}

		logger.debug("Exiting create Comment");
		return govtSchemeDataComment;

	}

	@Override
	public GovtSchemeDataComment read(Long id) throws MessageException {
		if (id == null) {
			logger.error("Id found to be null");
			throw new MessageException("Id found to be null");
		}
		logger.info("Entered GovtSchemeDataComment service read method");
		GovtSchemeDataComment govtSchemeDataComment = govtSchemeDataCommentRepository
				.find(id);

		logger.info("Exiting GovtSchemeDataComment service read method");
		return govtSchemeDataComment;
	}

	@Override
	public List<GovtSchemeDataComment> readAll() {
		logger.info("Entered GovtSchemeDataComment service readAll method");
		logger.debug("Getting all GovtSchemeDataComment");
		return govtSchemeDataCommentRepository.findAll();
	}

	@Override
	public GovtSchemeDataComment update(CommentBean commentBean)
			throws MessageException {
		logger.debug("Inside Update Comment method");

		GovtSchemeDataComment originalData = govtSchemeDataCommentRepository
				.find(commentBean.getId());
		if (originalData == null) {
			logger.error("No Record found in database for update");
			throw (new MessageException(
					"No Record found in database for update"));
		}
		originalData.setEditedTime(DateUtils.getSqlTimeStamp());

		GovtSchemeDataComment govtSchemeDataComment = govtSchemeDataCommentRepository
				.save(originalData);

		if (govtSchemeDataComment == null) {
			logger.error("Something went wrong, record not updated");
			throw (new MessageException(
					"Something went wrong, record not updated"));
		}

		logger.debug("Updated comment  : " + govtSchemeDataComment);
		logger.debug("Exiting Update");

		return govtSchemeDataComment;
	}

	@Override
	public GovtSchemeDataComment delete(Long id) throws MessageException {
		
		logger.info("Inside hard delete comment by ID");

		if (id == null) {
			logger.error("Comment ID found to be null");
			throw (new MessageException("Comment ID can't be null"));
		}

		GovtSchemeDataComment govtSchemeDataComment = govtSchemeDataCommentRepository
				.find(id);

		if (govtSchemeDataComment == null) {
			logger.error("Something went wrong, record not found for given id: "
					+ id + ", Can't delete record.");
			throw (new MessageException(
					"Something went wrong, record not found for given id: "
							+ id + ", Can't delete record."));
		}
		
		GovtSchemeDataCommentCount govtSchemeDataCommentCount = govtshemecommentcountService
				.getByComponentId(id);

		if (govtSchemeDataCommentCount != null) {

			govtshemecommentcountService.delete(govtSchemeDataCommentCount);

		}

		govtSchemeDataCommentRepository.delete(id);
		
		GovtSchemeDataComment gComment = govtSchemeDataCommentRepository.find(id);

		logger.debug("Deleted Comment :" + govtSchemeDataComment);
		logger.debug("Exiting  delete comment by ID");

		return gComment;
	}

	@Override
	public List<GovtSchemeDataComment> getByComponentIdAndUserId(
			Long componentId, Long userId) throws MessageException {

		logger.debug("Entered getByComponentIdAndUserId Comment");

		List<GovtSchemeDataComment> govtSchemeDataComments = null;
		govtSchemeDataComments = govtSchemeDataCommentRepository
				.getByComponentIdAndUserId(componentId, userId);

		logger.debug("Comment fetched: " + govtSchemeDataComments.size());
		logger.debug("Exiting getByComponentIdAndUserId comment");

		return govtSchemeDataComments;
	}

	@Override
	public List<GovtSchemeDataComment> getAllCommentsForComponent(
			Long componentId) throws MessageException {

		logger.debug("Entered getAllCommentsForComponent Comment");

		List<GovtSchemeDataComment> govtSchemeDataComments = null;
		govtSchemeDataComments = govtSchemeDataCommentRepository
				.getAllCommentsForComponent(componentId);

		logger.debug("Comment fetched: " + govtSchemeDataComments.size());
		logger.debug("Exiting getAllCommentsForComponent comment");

		return govtSchemeDataComments;
	}

	@Override
	public List<GovtSchemeDataComment> getAllCommentsForUserId(Long userId)
			throws MessageException {
		logger.debug("Entered getAllCommentsForUserId Comment");

		List<GovtSchemeDataComment> govtSchemeDataComments = null;
		govtSchemeDataComments = govtSchemeDataCommentRepository
				.getAllCommentsForUserId(userId);

		logger.debug("Comment fetched: " + govtSchemeDataComments.size());
		logger.debug("Exiting getAllCommentsForUserId comment");

		return govtSchemeDataComments;
	}

}
