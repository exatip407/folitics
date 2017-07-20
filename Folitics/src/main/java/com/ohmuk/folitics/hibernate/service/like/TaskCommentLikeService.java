package com.ohmuk.folitics.hibernate.service.like;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.beans.LikeDataBean;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.comment.TaskComment;
import com.ohmuk.folitics.hibernate.entity.like.LikeId;
import com.ohmuk.folitics.hibernate.entity.like.TaskCommentLike;
import com.ohmuk.folitics.hibernate.entity.like.TaskCommentLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.TaskCommentLikeCountId;
import com.ohmuk.folitics.hibernate.repository.like.ITaskCommentLikeRepository;

@Service
@Transactional
public class TaskCommentLikeService implements ILikeService<TaskCommentLike> {

	private static Logger logger = LoggerFactory
			.getLogger(TaskCommentLikeService.class);

	@Autowired
	private ILikeCountService<TaskCommentLikeCount> countService;

	@Autowired
	private ITaskCommentLikeRepository repository;

	@Override
	public TaskCommentLike create(LikeDataBean likeDataBean)
			throws MessageException, Exception {
		logger.info("Entered TaskCommentLike service create method");

		if (likeDataBean.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (likeDataBean.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		// check whether the entry for same component id and user id already
		// exists in database
		TaskCommentLike taskCommentLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		TaskCommentLikeCount taskCommentLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// if no entry exist for like in database
		if (taskCommentLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			taskCommentLike = new TaskCommentLike();
			taskCommentLike.setId(likeId);

			// set dislike flag false and like flag true
			taskCommentLike.setDislikeFlag(false);
			taskCommentLike.setLikeFlag(true);

			// save the new entry for like event
			taskCommentLike = repository.save(taskCommentLike);

			// added monetization points to user
			repository.addMonetizationPoints(likeDataBean, "Like");

			// if count object is also null that means this is the first
			// like on the component
			if (taskCommentLikeCount == null) {

				TaskComment taskComment = new TaskComment();
				taskComment.setId(likeDataBean.getComponentId());

				TaskCommentLikeCountId taskCommentLikeCountId = new TaskCommentLikeCountId();
				taskCommentLikeCountId.setTaskComment(taskComment);

				taskCommentLikeCount = new TaskCommentLikeCount();
				taskCommentLikeCount.setId(taskCommentLikeCountId);

				// since this call is for like so set like count 1 and
				// dislike count 0
				taskCommentLikeCount.setLikeCount(1l);
				taskCommentLikeCount.setDislikeCount(0l);

				// save the new entry for count in database
				countService.create(taskCommentLikeCount);
			} else {
				// if this is not the first like on the component, then
				// increment the like count by 1 and update in
				// database
				taskCommentLikeCount.setLikeCount(taskCommentLikeCount.getLikeCount() + 1l);
				countService.update(taskCommentLikeCount);
			}

		} else {

			// if the like object already exists in database and the entry
			// was for dislike, decrement the dislike
			// count by 1
			if (taskCommentLike.isDislikeFlag()) {

				taskCommentLikeCount
						.setDislikeCount(taskCommentLikeCount.getDislikeCount() - 1l);
			}

			// increment like count by 1
			taskCommentLikeCount.setLikeCount(taskCommentLikeCount.getLikeCount() + 1l);
			// set dislike flag false
			taskCommentLike.setDislikeFlag(false);
			// set like flag true
			taskCommentLike.setLikeFlag(true);

			// update like entry in database
			taskCommentLike = update(taskCommentLike);
			// update count entry in database
			countService.update(taskCommentLikeCount);

			// added monetization points to user
			repository.addMonetizationPoints(likeDataBean, "like");

		}
		return taskCommentLike;

	}

	@Override
	public TaskCommentLike read(LikeId likeId) throws MessageException {
		logger.info("Entered TaskCommentLike service read method");

		if (likeId.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (likeId.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Getting TaskCommentLike with component id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());
		return repository.find(likeId);
	}

	@Override
	public List<TaskCommentLike> readAll() {
		logger.info("Entered TaskCommentLike service readAll method");
		logger.debug("Getting all TaskCommentLike");
		return repository.findAll();
	}

	@Override
	public TaskCommentLike update(TaskCommentLike taskCommentLike) throws MessageException {
		logger.info("Entered TaskCommentLike service update method");

		if (taskCommentLike.getId().getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (taskCommentLike.getId().getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Updating TaskCommentLike with taskComment id = "
				+ taskCommentLike.getId().getComponentId() + " and user id = "
				+ taskCommentLike.getId().getUserId());

		TaskCommentLike originalTaskLike = repository.find(taskCommentLike.getId());

		if (originalTaskLike == null) {
			return null;
		}

		return repository.update(taskCommentLike);
	}

	@Override
	public TaskCommentLike delete(LikeId likeId) throws MessageException {
		logger.info("Entered TaskCommentLike service delete method");

		if (likeId.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (likeId.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Deleting TaskCommentLike with taskComment id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());

		// get the entry for count from database
		TaskCommentLikeCount taskCommentLikeCount = (TaskCommentLikeCount) countService
				.getByComponentId(likeId.getComponentId());

		// delete the entry for count
		countService.delete(taskCommentLikeCount);

		repository.delete(likeId);
		return repository.find(likeId);
	}

	@Override
	public TaskCommentLike delete(TaskCommentLike taskCommentLike) throws MessageException {
		logger.info("Entered TaskCommentLike service delete method");

		if (taskCommentLike.getId().getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (taskCommentLike.getId().getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Deleting TaskCommentLike with sentiment id = "
				+ taskCommentLike.getId().getComponentId() + " and user id = "
				+ taskCommentLike.getId().getUserId());

		return delete(taskCommentLike.getId());
	}

	@Override
	public TaskCommentLike getByComponentIdAndUserId(Long componentId,
			Long userId) throws MessageException {
		logger.info("Entered TaskCommentLike service getByComponentIdAndUserId method");

		if (componentId == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (userId == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Getting taskCommentLike with taskComment id = " + componentId
				+ " and user id = " + userId);

		return repository.getByComponentIdAndUserId(componentId, userId);
	}

	@Override
	public TaskCommentLike unlike(LikeDataBean likeDataBean)
			throws MessageException, Exception {
		logger.info("Entered TaskCommentLike service unlike method");

		if (likeDataBean.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (likeDataBean.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		// check whether the entry for same component id and user id already
		// exists in database which in this case
		// should exist
		TaskCommentLike taskCommentLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		TaskCommentLikeCount taskCommentLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// this if will never run in ideal condition
		if (taskCommentLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			taskCommentLike = new TaskCommentLike();
			taskCommentLike.setId(likeId);
			taskCommentLike.setLikeFlag(false);
			taskCommentLike.setDislikeFlag(false);

			taskCommentLike = repository.save(taskCommentLike);

			// deduct user points after unlike on any component
			repository.addMonetizationPoints(likeDataBean, "Unlike");

			if (taskCommentLikeCount == null) {

				TaskComment taskComment = new TaskComment();
				taskComment.setId(likeDataBean.getComponentId());

				TaskCommentLikeCountId taskLikeCountId = new TaskCommentLikeCountId();
				taskLikeCountId.setTaskComment(taskComment);

				taskCommentLikeCount = new TaskCommentLikeCount();
				taskCommentLikeCount.setId(taskLikeCountId);

				taskCommentLikeCount = new TaskCommentLikeCount();
				taskCommentLikeCount.setId(taskLikeCountId);
				taskCommentLikeCount.setLikeCount(0l);
				taskCommentLikeCount.setDislikeCount(0l);

				countService.create(taskCommentLikeCount);
			} else {

				taskCommentLikeCount.setLikeCount(taskCommentLikeCount.getLikeCount() - 1);
				countService.update(taskCommentLikeCount);
			}

		} else {

			// because this is the call for unlike so set both like and
			// dislike flag to false
			taskCommentLike.setLikeFlag(false);
			taskCommentLike.setDislikeFlag(false);

			// update like entry in database
			taskCommentLike = update(taskCommentLike);

			// deduct user points after unlike on any component
			repository.addMonetizationPoints(likeDataBean, "Unlike");

			if (taskCommentLikeCount != null) {

				// decrement the like count by 1
				taskCommentLikeCount.setLikeCount(taskCommentLikeCount.getLikeCount() - 1);

				// update the change in count entry for component in
				// database
				countService.update(taskCommentLikeCount);

			} else {
				// this will not run in ideal condition
				TaskComment taskComment = new TaskComment();
				taskComment.setId(likeDataBean.getComponentId());

				TaskCommentLikeCountId sentimentLikeCountId = new TaskCommentLikeCountId();
				sentimentLikeCountId.setTaskComment(taskComment);

				taskCommentLikeCount = new TaskCommentLikeCount();
				taskCommentLikeCount.setId(sentimentLikeCountId);

				taskCommentLikeCount = new TaskCommentLikeCount();
				taskCommentLikeCount.setId(sentimentLikeCountId);
				taskCommentLikeCount.setLikeCount(0l);
				taskCommentLikeCount.setDislikeCount(0l);

				countService.create(taskCommentLikeCount);
			}
		}
		return taskCommentLike;
	}

	@Override
	public TaskCommentLike dislike(LikeDataBean likeDataBean)
			throws MessageException, Exception {
		logger.info("Entered TaskCommentLike service unlike method");

		if (likeDataBean.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (likeDataBean.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		// check whether the entry for same component id and user id already
		// exists in database
		TaskCommentLike taskCommentLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		TaskCommentLikeCount taskCommentLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// if no entry exist for like in database
		if (taskCommentLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			taskCommentLike = new TaskCommentLike();
			taskCommentLike.setId(likeId);

			// set dislike flag true and like flag false
			taskCommentLike.setLikeFlag(false);
			taskCommentLike.setDislikeFlag(true);

			// save the new entry for like event
			taskCommentLike = repository.save(taskCommentLike);

			// added user points after disLike on any component
			repository.addMonetizationPoints(likeDataBean, "Dislike");

			// if count object is also null that means this is the first
			// dislike on the component
			if (taskCommentLikeCount == null) {

				TaskComment sentiment = new TaskComment();
				sentiment.setId(likeDataBean.getComponentId());

				TaskCommentLikeCountId sentimentLikeCountId = new TaskCommentLikeCountId();
				sentimentLikeCountId.setTaskComment(sentiment);

				taskCommentLikeCount = new TaskCommentLikeCount();
				taskCommentLikeCount.setId(sentimentLikeCountId);

				taskCommentLikeCount = new TaskCommentLikeCount();
				taskCommentLikeCount.setId(sentimentLikeCountId);
				// since this call is for dislike so set dislike count 1 and
				// like count 0
				taskCommentLikeCount.setLikeCount(0l);
				taskCommentLikeCount.setDislikeCount(1l);

				// save the new entry for count in database
				countService.create(taskCommentLikeCount);
			} else {

				// if this is not the first dislike on the component, then
				// increment the dislike count by 1 and
				// update in database
				taskCommentLikeCount
						.setDislikeCount(taskCommentLikeCount.getDislikeCount() + 1l);
				countService.update(taskCommentLikeCount);
			}

		} else {

			// if the like object already exists in database and the entry
			// was for like, decrement the like
			// count by 1
			if (taskCommentLike.isLikeFlag()) {
				taskCommentLikeCount.setLikeCount(taskCommentLikeCount.getLikeCount() - 1l);
			}

			// set like flag false
			taskCommentLike.setLikeFlag(false);
			// set dislike flag true
			taskCommentLike.setDislikeFlag(true);

			// update the like object in the database
			taskCommentLike = update(taskCommentLike);

			// added user points after disLike on any component
			repository.addMonetizationPoints(likeDataBean, "Dislike");

			if (taskCommentLikeCount != null) {

				// increment dislike count by 1
				taskCommentLikeCount
						.setDislikeCount(taskCommentLikeCount.getDislikeCount() + 1);

				// update the count object in the database
				countService.update(taskCommentLikeCount);

			} else {

				TaskComment taskComment = new TaskComment();
				taskComment.setId(likeDataBean.getComponentId());

				TaskCommentLikeCountId taskLikeCountId = new TaskCommentLikeCountId();
				taskLikeCountId.setTaskComment(taskComment);

				taskCommentLikeCount = new TaskCommentLikeCount();
				taskCommentLikeCount.setId(taskLikeCountId);

				taskCommentLikeCount = new TaskCommentLikeCount();
				taskCommentLikeCount.setId(taskLikeCountId);
				// since its a first entry for dislike on component hence
				// set like count to 0 and dislike count to 1
				taskCommentLikeCount.setLikeCount(0l);
				taskCommentLikeCount.setDislikeCount(1l);

				// create new entry in database
				countService.create(taskCommentLikeCount);
			}
		}
		return taskCommentLike;
	}

	@Override
	public TaskCommentLike undislike(LikeDataBean likeDataBean)
			throws MessageException, Exception {
		logger.info("Entered TaskCommentLike service undislike method");

		if (likeDataBean.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (likeDataBean.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		// check whether the entry for same component id and user id already
		// exists in database which in this case
		// should exist
		TaskCommentLike taskCommentLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		TaskCommentLikeCount taskCommentLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// this if will never run in ideal condition
		if (taskCommentLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			taskCommentLike = new TaskCommentLike();
			taskCommentLike.setId(likeId);
			taskCommentLike.setLikeFlag(false);
			taskCommentLike.setDislikeFlag(false);

			taskCommentLike = repository.save(taskCommentLike);

			// deduct user points after unDislike on any component
			repository.addMonetizationPoints(likeDataBean, "Undislike");

			if (taskCommentLikeCount == null) {

				TaskComment sentiment = new TaskComment();
				sentiment.setId(likeDataBean.getComponentId());

				TaskCommentLikeCountId sentimentLikeCountId = new TaskCommentLikeCountId();
				sentimentLikeCountId.setTaskComment(sentiment);

				taskCommentLikeCount = new TaskCommentLikeCount();
				taskCommentLikeCount.setId(sentimentLikeCountId);

				taskCommentLikeCount = new TaskCommentLikeCount();
				taskCommentLikeCount.setId(sentimentLikeCountId);
				taskCommentLikeCount.setLikeCount(0l);
				taskCommentLikeCount.setDislikeCount(0l);

				countService.create(taskCommentLikeCount);
			} else {

				taskCommentLikeCount
						.setDislikeCount(taskCommentLikeCount.getDislikeCount() - 1);
				countService.update(taskCommentLikeCount);
			}

		} else {

			// because this is the call for undislike so set both like and
			// dislike flag to false
			taskCommentLike.setLikeFlag(false);
			taskCommentLike.setDislikeFlag(false);

			// update like entry in database
			taskCommentLike = update(taskCommentLike);

			// deduct user points after unDislike on any component
			repository.addMonetizationPoints(likeDataBean, "Undislike");

			if (taskCommentLikeCount != null) {

				// decrement the like count by 1
				taskCommentLikeCount
						.setDislikeCount(taskCommentLikeCount.getDislikeCount() - 1);

				// update the change in count entry for component in
				// database
				countService.update(taskCommentLikeCount);

			} else {
				// this will not run in ideal condition

				TaskComment taskComment = new TaskComment();
				taskComment.setId(likeDataBean.getComponentId());

				TaskCommentLikeCountId taskLikeCountId = new TaskCommentLikeCountId();
				taskLikeCountId.setTaskComment(taskComment);

				taskCommentLikeCount = new TaskCommentLikeCount();
				taskCommentLikeCount.setId(taskLikeCountId);

				taskCommentLikeCount = new TaskCommentLikeCount();
				taskCommentLikeCount.setId(taskLikeCountId);
				taskCommentLikeCount.setLikeCount(0l);
				taskCommentLikeCount.setDislikeCount(0l);

				countService.create(taskCommentLikeCount);
			}
		}

		return taskCommentLike;
	}

}
