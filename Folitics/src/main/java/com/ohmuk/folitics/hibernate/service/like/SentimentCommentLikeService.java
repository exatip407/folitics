package com.ohmuk.folitics.hibernate.service.like;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.beans.LikeDataBean;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.comment.SentimentComment;
import com.ohmuk.folitics.hibernate.entity.like.LikeId;
import com.ohmuk.folitics.hibernate.entity.like.SentimentCommentLike;
import com.ohmuk.folitics.hibernate.entity.like.SentimentCommentLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.SentimentCommentLikeCountId;
import com.ohmuk.folitics.hibernate.repository.like.ISentimentCommentLikeRepository;

@Service
@Transactional
public class SentimentCommentLikeService implements
		ILikeService<SentimentCommentLike> {
	private static Logger logger = LoggerFactory
			.getLogger(SentimentCommentLikeService.class);

	@Autowired
	private ILikeCountService<SentimentCommentLikeCount> countService;

	@Autowired
	private ISentimentCommentLikeRepository repository;

	@Override
	public SentimentCommentLike create(LikeDataBean likeDataBean)
			throws MessageException, Exception {
		logger.info("Entered SentimentCommentLike service create method");

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
		SentimentCommentLike sentimentCommentLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		SentimentCommentLikeCount sentimentCommentLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// if no entry exist for like in database
		if (sentimentCommentLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			sentimentCommentLike = new SentimentCommentLike();
			sentimentCommentLike.setId(likeId);

			// set dislike flag false and like flag true
			sentimentCommentLike.setDislikeFlag(false);
			sentimentCommentLike.setLikeFlag(true);

			// save the new entry for like event
			sentimentCommentLike = repository.save(sentimentCommentLike);

			// added monetization points to user
			repository.addMonetizationPoints(likeDataBean, "Like");

			// if count object is also null that means this is the first
			// like on the component
			if (sentimentCommentLikeCount == null) {

				SentimentComment sentimentComment = new SentimentComment();
				sentimentComment.setId(likeDataBean.getComponentId());

				SentimentCommentLikeCountId taskCommentLikeCountId = new SentimentCommentLikeCountId();
				taskCommentLikeCountId.setSentimentComment(sentimentComment);

				sentimentCommentLikeCount = new SentimentCommentLikeCount();
				sentimentCommentLikeCount.setId(taskCommentLikeCountId);

				// since this call is for like so set like count 1 and
				// dislike count 0
				sentimentCommentLikeCount.setLikeCount(1l);
				sentimentCommentLikeCount.setDislikeCount(0l);

				// save the new entry for count in database
				countService.create(sentimentCommentLikeCount);
			} else {
				// if this is not the first like on the component, then
				// increment the like count by 1 and update in
				// database
				sentimentCommentLikeCount.setLikeCount(sentimentCommentLikeCount
						.getLikeCount() + 1l);
				countService.update(sentimentCommentLikeCount);
			}

		} else {

			// if the like object already exists in database and the entry
			// was for dislike, decrement the dislike
			// count by 1
			if (sentimentCommentLike.isDislikeFlag()) {

				sentimentCommentLikeCount.setDislikeCount(sentimentCommentLikeCount
						.getDislikeCount() - 1l);
			}

			// increment like count by 1
			sentimentCommentLikeCount.setLikeCount(sentimentCommentLikeCount
					.getLikeCount() + 1l);
			// set dislike flag false
			sentimentCommentLike.setDislikeFlag(false);
			// set like flag true
			sentimentCommentLike.setLikeFlag(true);

			// update like entry in database
			sentimentCommentLike = update(sentimentCommentLike);
			// update count entry in database
			countService.update(sentimentCommentLikeCount);

			// added monetization points to user
			repository.addMonetizationPoints(likeDataBean, "like");

		}
		return sentimentCommentLike;
	}

	@Override
	public SentimentCommentLike read(LikeId likeId) throws MessageException {
		logger.info("Entered SentimentCommentLike service read method");

		if (likeId.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (likeId.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Getting SentimentCommentLike with component id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());
		return repository.find(likeId);
	}

	@Override
	public List<SentimentCommentLike> readAll() {
		logger.info("Entered SentimentCommentLike service readAll method");
		logger.debug("Getting all SentimentCommentLike");
		return repository.findAll();
	}

	@Override
	public SentimentCommentLike update(SentimentCommentLike sentimentCommentLike)
			throws MessageException {
		logger.info("Entered SentimentCommentLike service update method");

		if (sentimentCommentLike.getId().getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (sentimentCommentLike.getId().getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Updating SentimentCommentLike with sentimentComment id = "
				+ sentimentCommentLike.getId().getComponentId() + " and user id = "
				+ sentimentCommentLike.getId().getUserId());

		SentimentCommentLike originalTaskLike = repository.find(sentimentCommentLike
				.getId());

		if (originalTaskLike == null) {
			return null;
		}

		return repository.update(sentimentCommentLike);
	}

	@Override
	public SentimentCommentLike delete(LikeId likeId) throws MessageException {
		logger.info("Entered SentimentCommentLike service delete method");

		if (likeId.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (likeId.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Deleting SentimentCommentLike with sentimentComment id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());

		// get the entry for count from database
		SentimentCommentLikeCount sentimentCommentLikeCount = (SentimentCommentLikeCount) countService
				.getByComponentId(likeId.getComponentId());

		// delete the entry for count
		countService.delete(sentimentCommentLikeCount);

		repository.delete(likeId);
		return repository.find(likeId);
	}

	@Override
	public SentimentCommentLike delete(SentimentCommentLike sentimentCommentLike)
			throws MessageException {
		logger.info("Entered SentimentCommentLike service delete method");

		if (sentimentCommentLike.getId().getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (sentimentCommentLike.getId().getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Deleting SentimentCommentLike with sentiment id = "
				+ sentimentCommentLike.getId().getComponentId() + " and user id = "
				+ sentimentCommentLike.getId().getUserId());

		return delete(sentimentCommentLike.getId());
	}

	@Override
	public SentimentCommentLike getByComponentIdAndUserId(Long componentId,
			Long userId) throws MessageException {
		logger.info("Entered SentimentCommentLike service getByComponentIdAndUserId method");

		if (componentId == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (userId == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Getting sentimentCommentLike with sentimentComment id = "
				+ componentId + " and user id = " + userId);

		return repository.getByComponentIdAndUserId(componentId, userId);
	}

	@Override
	public SentimentCommentLike unlike(LikeDataBean likeDataBean)
			throws MessageException, Exception {
		logger.info("Entered SentimentCommentLike service unlike method");

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
		SentimentCommentLike sentimentCommentLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		SentimentCommentLikeCount sentimentCommentLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// this if will never run in ideal condition
		if (sentimentCommentLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			sentimentCommentLike = new SentimentCommentLike();
			sentimentCommentLike.setId(likeId);
			sentimentCommentLike.setLikeFlag(false);
			sentimentCommentLike.setDislikeFlag(false);

			sentimentCommentLike = repository.save(sentimentCommentLike);

			// deduct user points after unlike on any component
			repository.addMonetizationPoints(likeDataBean, "Unlike");

			if (sentimentCommentLikeCount == null) {

				SentimentComment sentimentComment = new SentimentComment();
				sentimentComment.setId(likeDataBean.getComponentId());

				SentimentCommentLikeCountId taskLikeCountId = new SentimentCommentLikeCountId();
				taskLikeCountId.setSentimentComment(sentimentComment);

				sentimentCommentLikeCount = new SentimentCommentLikeCount();
				sentimentCommentLikeCount.setId(taskLikeCountId);

				sentimentCommentLikeCount = new SentimentCommentLikeCount();
				sentimentCommentLikeCount.setId(taskLikeCountId);
				sentimentCommentLikeCount.setLikeCount(0l);
				sentimentCommentLikeCount.setDislikeCount(0l);

				countService.create(sentimentCommentLikeCount);
			} else {

				sentimentCommentLikeCount.setLikeCount(sentimentCommentLikeCount
						.getLikeCount() - 1);
				countService.update(sentimentCommentLikeCount);
			}

		} else {

			// because this is the call for unlike so set both like and
			// dislike flag to false
			sentimentCommentLike.setLikeFlag(false);
			sentimentCommentLike.setDislikeFlag(false);

			// update like entry in database
			sentimentCommentLike = update(sentimentCommentLike);

			// deduct user points after unlike on any component
			repository.addMonetizationPoints(likeDataBean, "Unlike");

			if (sentimentCommentLikeCount != null) {

				// decrement the like count by 1
				sentimentCommentLikeCount.setLikeCount(sentimentCommentLikeCount
						.getLikeCount() - 1);

				// update the change in count entry for component in
				// database
				countService.update(sentimentCommentLikeCount);

			} else {
				// this will not run in ideal condition
				SentimentComment sentimentComment = new SentimentComment();
				sentimentComment.setId(likeDataBean.getComponentId());

				SentimentCommentLikeCountId sentimentLikeCountId = new SentimentCommentLikeCountId();
				sentimentLikeCountId.setSentimentComment(sentimentComment);

				sentimentCommentLikeCount = new SentimentCommentLikeCount();
				sentimentCommentLikeCount.setId(sentimentLikeCountId);

				sentimentCommentLikeCount = new SentimentCommentLikeCount();
				sentimentCommentLikeCount.setId(sentimentLikeCountId);
				sentimentCommentLikeCount.setLikeCount(0l);
				sentimentCommentLikeCount.setDislikeCount(0l);

				countService.create(sentimentCommentLikeCount);
			}
		}
		return sentimentCommentLike;
	}

	@Override
	public SentimentCommentLike dislike(LikeDataBean likeDataBean)
			throws MessageException, Exception {
		logger.info("Entered SentimentCommentLike service unlike method");

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
		SentimentCommentLike sentimentCommentLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		SentimentCommentLikeCount sentimentCommentLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// if no entry exist for like in database
		if (sentimentCommentLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			sentimentCommentLike = new SentimentCommentLike();
			sentimentCommentLike.setId(likeId);

			// set dislike flag true and like flag false
			sentimentCommentLike.setLikeFlag(false);
			sentimentCommentLike.setDislikeFlag(true);

			// save the new entry for like event
			sentimentCommentLike = repository.save(sentimentCommentLike);

			// added user points after disLike on any component
			repository.addMonetizationPoints(likeDataBean, "Dislike");

			// if count object is also null that means this is the first
			// dislike on the component
			if (sentimentCommentLikeCount == null) {

				SentimentComment sentiment = new SentimentComment();
				sentiment.setId(likeDataBean.getComponentId());

				SentimentCommentLikeCountId sentimentLikeCountId = new SentimentCommentLikeCountId();
				sentimentLikeCountId.setSentimentComment(sentiment);

				sentimentCommentLikeCount = new SentimentCommentLikeCount();
				sentimentCommentLikeCount.setId(sentimentLikeCountId);

				sentimentCommentLikeCount = new SentimentCommentLikeCount();
				sentimentCommentLikeCount.setId(sentimentLikeCountId);
				// since this call is for dislike so set dislike count 1 and
				// like count 0
				sentimentCommentLikeCount.setLikeCount(0l);
				sentimentCommentLikeCount.setDislikeCount(1l);

				// save the new entry for count in database
				countService.create(sentimentCommentLikeCount);
			} else {

				// if this is not the first dislike on the component, then
				// increment the dislike count by 1 and
				// update in database
				sentimentCommentLikeCount.setDislikeCount(sentimentCommentLikeCount
						.getDislikeCount() + 1l);
				countService.update(sentimentCommentLikeCount);
			}

		} else {

			// if the like object already exists in database and the entry
			// was for like, decrement the like
			// count by 1
			if (sentimentCommentLike.isLikeFlag()) {
				sentimentCommentLikeCount.setLikeCount(sentimentCommentLikeCount
						.getLikeCount() - 1l);
			}

			// set like flag false
			sentimentCommentLike.setLikeFlag(false);
			// set dislike flag true
			sentimentCommentLike.setDislikeFlag(true);

			// update the like object in the database
			sentimentCommentLike = update(sentimentCommentLike);

			// added user points after disLike on any component
			repository.addMonetizationPoints(likeDataBean, "Dislike");

			if (sentimentCommentLikeCount != null) {

				// increment dislike count by 1
				sentimentCommentLikeCount.setDislikeCount(sentimentCommentLikeCount
						.getDislikeCount() + 1);

				// update the count object in the database
				countService.update(sentimentCommentLikeCount);

			} else {

				SentimentComment sentimentComment = new SentimentComment();
				sentimentComment.setId(likeDataBean.getComponentId());

				SentimentCommentLikeCountId taskLikeCountId = new SentimentCommentLikeCountId();
				taskLikeCountId.setSentimentComment(sentimentComment);

				sentimentCommentLikeCount = new SentimentCommentLikeCount();
				sentimentCommentLikeCount.setId(taskLikeCountId);

				sentimentCommentLikeCount = new SentimentCommentLikeCount();
				sentimentCommentLikeCount.setId(taskLikeCountId);
				// since its a first entry for dislike on component hence
				// set like count to 0 and dislike count to 1
				sentimentCommentLikeCount.setLikeCount(0l);
				sentimentCommentLikeCount.setDislikeCount(1l);

				// create new entry in database
				countService.create(sentimentCommentLikeCount);
			}
		}
		return sentimentCommentLike;
	}

	@Override
	public SentimentCommentLike undislike(LikeDataBean likeDataBean)
			throws MessageException, Exception {
		logger.info("Entered SentimentCommentLike service undislike method");

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
		SentimentCommentLike sentimentCommentLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		SentimentCommentLikeCount sentimentCommentLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// this if will never run in ideal condition
		if (sentimentCommentLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			sentimentCommentLike = new SentimentCommentLike();
			sentimentCommentLike.setId(likeId);
			sentimentCommentLike.setLikeFlag(false);
			sentimentCommentLike.setDislikeFlag(false);

			sentimentCommentLike = repository.save(sentimentCommentLike);

			// deduct user points after unDislike on any component
			repository.addMonetizationPoints(likeDataBean, "Undislike");

			if (sentimentCommentLikeCount == null) {

				SentimentComment sentiment = new SentimentComment();
				sentiment.setId(likeDataBean.getComponentId());

				SentimentCommentLikeCountId sentimentLikeCountId = new SentimentCommentLikeCountId();
				sentimentLikeCountId.setSentimentComment(sentiment);

				sentimentCommentLikeCount = new SentimentCommentLikeCount();
				sentimentCommentLikeCount.setId(sentimentLikeCountId);

				sentimentCommentLikeCount = new SentimentCommentLikeCount();
				sentimentCommentLikeCount.setId(sentimentLikeCountId);
				sentimentCommentLikeCount.setLikeCount(0l);
				sentimentCommentLikeCount.setDislikeCount(0l);

				countService.create(sentimentCommentLikeCount);
			} else {

				sentimentCommentLikeCount.setDislikeCount(sentimentCommentLikeCount
						.getDislikeCount() - 1);
				countService.update(sentimentCommentLikeCount);
			}

		} else {

			// because this is the call for undislike so set both like and
			// dislike flag to false
			sentimentCommentLike.setLikeFlag(false);
			sentimentCommentLike.setDislikeFlag(false);

			// update like entry in database
			sentimentCommentLike = update(sentimentCommentLike);

			// deduct user points after unDislike on any component
			repository.addMonetizationPoints(likeDataBean, "Undislike");

			if (sentimentCommentLikeCount != null) {

				// decrement the like count by 1
				sentimentCommentLikeCount.setDislikeCount(sentimentCommentLikeCount
						.getDislikeCount() - 1);

				// update the change in count entry for component in
				// database
				countService.update(sentimentCommentLikeCount);

			} else {
				// this will not run in ideal condition

				SentimentComment sentimentComment = new SentimentComment();
				sentimentComment.setId(likeDataBean.getComponentId());

				SentimentCommentLikeCountId taskLikeCountId = new SentimentCommentLikeCountId();
				taskLikeCountId.setSentimentComment(sentimentComment);

				sentimentCommentLikeCount = new SentimentCommentLikeCount();
				sentimentCommentLikeCount.setId(taskLikeCountId);

				sentimentCommentLikeCount = new SentimentCommentLikeCount();
				sentimentCommentLikeCount.setId(taskLikeCountId);
				sentimentCommentLikeCount.setLikeCount(0l);
				sentimentCommentLikeCount.setDislikeCount(0l);

				countService.create(sentimentCommentLikeCount);
			}
		}

		return sentimentCommentLike;
	}

}
