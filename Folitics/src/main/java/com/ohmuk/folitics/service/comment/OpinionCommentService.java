package com.ohmuk.folitics.service.comment;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.beans.CommentBean;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Opinion;
import com.ohmuk.folitics.hibernate.entity.comment.OpinionComment;
import com.ohmuk.folitics.hibernate.entity.comment.OpinionCommentCount;
import com.ohmuk.folitics.hibernate.entity.comment.OpinionCommentCountId;
import com.ohmuk.folitics.hibernate.repository.comment.IOpinionCommentRepository;
import com.ohmuk.folitics.util.DateUtils;

@Service
@Transactional
public class OpinionCommentService implements ICommentService<OpinionComment> {

	private static Logger logger = LoggerFactory
			.getLogger(OpinionCommentService.class);

	@Autowired
	private ICommentCountService<OpinionCommentCount> opinionCommentCountService;

	@Autowired
	private IOpinionCommentRepository opinionCommentRepository;

	@Override
	public OpinionComment create(CommentBean commentBean)
			throws MessageException, Exception {
		logger.debug("Inside create  OpinionComment Service");

		if (commentBean.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId found to be null"));
		}

		if (commentBean.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId found to be null"));
		}

		// create OpinionComment object from comment bean
		OpinionComment opinionComment = new OpinionComment();
		opinionComment.setUserId(commentBean.getUserId());
		opinionComment.setComponentId(commentBean.getComponentId());
		opinionComment.setComponentType(commentBean.getComponentType());
		opinionComment.setComment(commentBean.getComment());

		opinionCommentRepository.save(opinionComment);

		// add monetization points to user for Comment on any component
		opinionCommentRepository
				.addMonetizationPoints(commentBean, "Comment");

		// Adding counter for the Comment
		OpinionCommentCount opinionCommentCount = new OpinionCommentCount();
		Opinion opinion = new Opinion();
		opinion.setId(commentBean.getComponentId());

		OpinionCommentCountId opinionCommentCountId = new OpinionCommentCountId();
		opinionCommentCountId.setOpinion(opinion);

		opinionCommentCount.setId(opinionCommentCountId);

		OpinionCommentCount opinionCommentCount2 = opinionCommentCountService
				.getByComponentId(commentBean.getComponentId());

		
		// if count available for component adding counter else entering counter
		// for the first time for user and component

		if (opinionCommentCount2 != null) {
			opinionCommentCount2.setCommentCount(opinionCommentCount2
					.getCommentCount() + 1);
			opinionCommentCountService.create(opinionCommentCount2);
		} else {
			opinionCommentCount.setCommentCount(1l);
			opinionCommentCountService.create(opinionCommentCount);
		}

		logger.debug("Exiting create Comment");
		return opinionComment;

	}

	@Override
	public OpinionComment read(Long id) throws MessageException {
		logger.info("Entered OpinionComment service read method");
		if (id == null) {
			logger.error("Id found to be null");
			throw new MessageException("Id found to be null");
		}

		OpinionComment opinionComment = opinionCommentRepository.find(id);

		logger.info("Exiting OpinionComment service read method");
		return opinionComment;
	}

	@Override
	public List<OpinionComment> readAll() {

		logger.info("Entered OpinionComment service readAll method");
		logger.debug("Getting all OpinionComment");
		return opinionCommentRepository.findAll();
	}

	@Override
	public OpinionComment update(CommentBean commentBean)
			throws MessageException {
		
		logger.debug("Inside Update Comment method");

		OpinionComment originalData = opinionCommentRepository
				.find(commentBean.getId());
		if (originalData == null) {
			logger.error("No Record found in database for update");
			throw (new MessageException(
					"No Record found in database for update"));
		}
		originalData.setEditedTime(DateUtils.getSqlTimeStamp());

		OpinionComment opinionComment = opinionCommentRepository
				.save(originalData);

		if (opinionComment == null) {
			logger.error("Something went wrong, record not updated");
			throw (new MessageException(
					"Something went wrong, record not updated"));
		}

		logger.debug("Updated comment  : " + opinionComment);
		logger.debug("Exiting Update");

		return opinionComment;
	}

	@Override
	public OpinionComment delete(Long id) throws MessageException, Exception {
		logger.info("Inside hard delete comment by ID");

		if (id == null) {
			logger.error("Comment ID found to be null");
			throw (new MessageException("Comment ID can't be null"));
		}

		OpinionComment opinionComment = opinionCommentRepository.find(id);

		if (opinionComment == null) {
			logger.error("Something went wrong, record not found for given id: "
					+ id + ", Can't delete record.");
			throw (new MessageException(
					"Something went wrong, record not found for given id: "
							+ id + ", Can't delete record."));
		}

		OpinionCommentCount opinionCommentCount = opinionCommentCountService
				.getByComponentId(id);

		if (opinionCommentCount != null) {

			opinionCommentCountService.delete(opinionCommentCount);

		}

		opinionCommentRepository.delete(id);

		OpinionComment oComment = opinionCommentRepository.find(id);

		logger.debug("Deleted Comment :" + opinionComment);
		logger.debug("Exiting  delete comment by ID");

		return oComment;
	}

	@Override
	public List<OpinionComment> getByComponentIdAndUserId(Long componentId,
			Long userId) throws MessageException {
		logger.debug("Entered getByComponentIdAndUserId Comment");

		List<OpinionComment> opinionComments = null;
		opinionComments = opinionCommentRepository.getByComponentIdAndUserId(
				componentId, userId);

		logger.debug("Comment fetched: " + opinionComments.size());
		logger.debug("Exiting getByComponentIdAndUserId comment");

		return opinionComments;
	}

	@Override
	public List<OpinionComment> getAllCommentsForComponent(Long componentId)
			throws MessageException {
		logger.debug("Entered getAllCommentsForComponent method");

		List<OpinionComment> opinionComments = null;
		opinionComments = opinionCommentRepository
				.getAllCommentsForComponent(componentId);

		logger.debug("Comment fetched: " + opinionComments.size());
		logger.debug("Exiting getAllCommentsForComponent method");

		return opinionComments;
	}

	@Override
	public List<OpinionComment> getAllCommentsForUserId(Long userId)
			throws MessageException {
		logger.debug("Entered getAllCommentsForUserId method");

		List<OpinionComment> opinionComments = null;
		opinionComments = opinionCommentRepository
				.getAllCommentsForUserId(userId);

		logger.debug("Comment fetched: " + opinionComments.size());
		logger.debug("Exiting getAllCommentsForUserId method");

		return opinionComments;
	}

}
