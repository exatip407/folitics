package com.ohmuk.folitics.hibernate.service.like;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.beans.LikeDataBean;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Sentiment;
import com.ohmuk.folitics.hibernate.entity.like.LikeId;
import com.ohmuk.folitics.hibernate.entity.like.SentimentLike;
import com.ohmuk.folitics.hibernate.entity.like.SentimentLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.SentimentLikeCountId;
import com.ohmuk.folitics.hibernate.repository.like.ISentimentLikeRepository;

/**
 * @author Abhishek
 *
 */

@Service
@Transactional
public class SentimentLikeService implements ILikeService<SentimentLike> {

	private static Logger logger = LoggerFactory
			.getLogger(SentimentLikeService.class);

	@Autowired
	private ISentimentLikeRepository repository;

	@Autowired
	private ILikeCountService<SentimentLikeCount> countService;

	@Override
	public SentimentLike create(LikeDataBean likeDataBean) throws Exception {

		logger.info("Entered SentimentLike service create method");

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
		SentimentLike sentimentLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		SentimentLikeCount sentimentLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// if no entry exist for like in database
		if (sentimentLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			sentimentLike = new SentimentLike();
			sentimentLike.setLikeId(likeId);

			// set dislike flag false and like flag true
			sentimentLike.setDislikeFlag(false);
			sentimentLike.setLikeFlag(true);

			// save the new entry for like event
			sentimentLike = repository.save(sentimentLike);

			// added monetization points to user
//			repository.addMonetizationPoints(likeDataBean, "like");

			// if count object is also null that means this is the first
			// like on the component
			if (sentimentLikeCount == null) {

				Sentiment sentiment = new Sentiment();
				sentiment.setId(likeDataBean.getComponentId());

				SentimentLikeCountId sentimentLikeCountId = new SentimentLikeCountId();
				sentimentLikeCountId.setSentiment(sentiment);

				sentimentLikeCount = new SentimentLikeCount();
				sentimentLikeCount.setId(sentimentLikeCountId);

				// since this call is for like so set like count 1 and
				// dislike count 0
				sentimentLikeCount.setLikeCount(1l);
				sentimentLikeCount.setDislikeCount(0l);

				// save the new entry for count in database
				countService.create(sentimentLikeCount);
			} else {
				// if this is not the first like on the component, then
				// increment the like count by 1 and update in
				// database
				sentimentLikeCount.setLikeCount(sentimentLikeCount
						.getLikeCount() + 1l);
				countService.update(sentimentLikeCount);
			}

		} else {

			// if the like object already exists in database and the entry
			// was for dislike, decrement the dislike
			// count by 1
			if (sentimentLike.isDislikeFlag()) {

				sentimentLikeCount.setDislikeCount(sentimentLikeCount
						.getDislikeCount() - 1l);
			}

			// increment like count by 1
			sentimentLikeCount
					.setLikeCount(sentimentLikeCount.getLikeCount() + 1l);
			// set dislike flag false
			sentimentLike.setDislikeFlag(false);
			// set like flag true
			sentimentLike.setLikeFlag(true);

			// update like entry in database
			sentimentLike = update(sentimentLike);
			// update count entry in database
			countService.update(sentimentLikeCount);

			// added monetization points to user
			repository.addMonetizationPoints(likeDataBean, "like");

		}

		return sentimentLike;

	}

	@Override
	public SentimentLike read(LikeId likeId) throws MessageException {

		logger.info("Entered SentimentLike service read method");

		if (likeId.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (likeId.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Getting SentimentLike with component id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());
		return repository.find(likeId);
	}

	@Override
	public List<SentimentLike> readAll() {

		logger.info("Entered SentimentLike service readAll method");
		logger.debug("Getting all SentimentLike");
		return repository.findAll();
	}

	@Override
	public SentimentLike update(SentimentLike sentimentLike)
			throws MessageException {

		logger.info("Entered SentimentLike service update method");

		if (sentimentLike.getLikeId().getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (sentimentLike.getLikeId().getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Updating SentimentLike with sentiment id = "
				+ sentimentLike.getLikeId().getComponentId()
				+ " and user id = " + sentimentLike.getLikeId().getUserId());

		SentimentLike originalSentimentLike = repository.find(sentimentLike
				.getLikeId());

		if (originalSentimentLike == null) {
			return null;
		}

		return repository.update(sentimentLike);
	}

	@Override
	public SentimentLike delete(LikeId likeId) throws MessageException {

		logger.info("Entered SentimentLike service delete method");

		if (likeId.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (likeId.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Deleting SentimentLike with sentiment id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());

		// get the entry for count from database
		SentimentLikeCount sentimentLikeCount = (SentimentLikeCount) countService
				.getByComponentId(likeId.getComponentId());

		// delete the entry for count
		countService.delete(sentimentLikeCount);

		repository.delete(likeId);
		return repository.find(likeId);
	}

	@Override
	public SentimentLike delete(SentimentLike sentimentLike)
			throws MessageException {

		logger.info("Entered SentimentLike service delete method");

		if (sentimentLike.getLikeId().getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (sentimentLike.getLikeId().getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Deleting SentimentLike with sentiment id = "
				+ sentimentLike.getLikeId().getComponentId()
				+ " and user id = " + sentimentLike.getLikeId().getUserId());

		return delete(sentimentLike.getLikeId());
	}

	@Override
	public SentimentLike getByComponentIdAndUserId(Long componentId, Long userId)
			throws MessageException {

		logger.info("Entered SentimentLike service getByComponentIdAndUserId method");

		if (componentId == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (userId == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Getting SentimentLike with sentiment id = " + componentId
				+ " and user id = " + userId);

		return repository.getByComponentIdAndUserId(componentId, userId);
	}

	@Override
	public SentimentLike unlike(LikeDataBean likeDataBean) throws Exception {

		logger.info("Entered SentimentLike service unlike method");

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
		SentimentLike sentimentLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		SentimentLikeCount sentimentLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// this if will never run in ideal condition
		if (sentimentLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			sentimentLike = new SentimentLike();
			sentimentLike.setLikeId(likeId);
			sentimentLike.setLikeFlag(false);
			sentimentLike.setDislikeFlag(false);

			sentimentLike = repository.save(sentimentLike);

			// deduct user points after unlike on any component
			repository.addMonetizationPoints(likeDataBean, "Unlike");

			if (sentimentLikeCount == null) {

				Sentiment sentiment = new Sentiment();
				sentiment.setId(likeDataBean.getComponentId());

				SentimentLikeCountId sentimentLikeCountId = new SentimentLikeCountId();
				sentimentLikeCountId.setSentiment(sentiment);

				sentimentLikeCount = new SentimentLikeCount();
				sentimentLikeCount.setId(sentimentLikeCountId);

				sentimentLikeCount = new SentimentLikeCount();
				sentimentLikeCount.setId(sentimentLikeCountId);
				sentimentLikeCount.setLikeCount(0l);
				sentimentLikeCount.setDislikeCount(0l);

				countService.create(sentimentLikeCount);
			} else {

				sentimentLikeCount.setLikeCount(sentimentLikeCount
						.getLikeCount() - 1);
				countService.update(sentimentLikeCount);
			}

		} else {

			// because this is the call for unlike so set both like and
			// dislike flag to false
			sentimentLike.setLikeFlag(false);
			sentimentLike.setDislikeFlag(false);

			// update like entry in database
			sentimentLike = update(sentimentLike);

			// deduct user points after unlike on any component
			repository.addMonetizationPoints(likeDataBean, "Unlike");

			if (sentimentLikeCount != null) {

				// decrement the like count by 1
				sentimentLikeCount.setLikeCount(sentimentLikeCount
						.getLikeCount() - 1);

				// update the change in count entry for component in
				// database
				countService.update(sentimentLikeCount);

			} else {
				// this will not run in ideal condition
				Sentiment sentiment = new Sentiment();
				sentiment.setId(likeDataBean.getComponentId());

				SentimentLikeCountId sentimentLikeCountId = new SentimentLikeCountId();
				sentimentLikeCountId.setSentiment(sentiment);

				sentimentLikeCount = new SentimentLikeCount();
				sentimentLikeCount.setId(sentimentLikeCountId);

				sentimentLikeCount = new SentimentLikeCount();
				sentimentLikeCount.setId(sentimentLikeCountId);
				sentimentLikeCount.setLikeCount(0l);
				sentimentLikeCount.setDislikeCount(0l);

				countService.create(sentimentLikeCount);
			}
		}

		return sentimentLike;
	}

	@Override
	public SentimentLike dislike(LikeDataBean likeDataBean) throws Exception {

		logger.info("Entered SentimentLike service unlike method");

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
		SentimentLike sentimentLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		SentimentLikeCount sentimentLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// if no entry exist for like in database
		if (sentimentLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			sentimentLike = new SentimentLike();
			sentimentLike.setLikeId(likeId);

			// set dislike flag true and like flag false
			sentimentLike.setLikeFlag(false);
			sentimentLike.setDislikeFlag(true);

			// save the new entry for like event
			sentimentLike = repository.save(sentimentLike);

			// added user points after disLike on any component
			repository.addMonetizationPoints(likeDataBean, "Dislike");

			// if count object is also null that means this is the first
			// dislike on the component
			if (sentimentLikeCount == null) {

				Sentiment sentiment = new Sentiment();
				sentiment.setId(likeDataBean.getComponentId());

				SentimentLikeCountId sentimentLikeCountId = new SentimentLikeCountId();
				sentimentLikeCountId.setSentiment(sentiment);

				sentimentLikeCount = new SentimentLikeCount();
				sentimentLikeCount.setId(sentimentLikeCountId);

				sentimentLikeCount = new SentimentLikeCount();
				sentimentLikeCount.setId(sentimentLikeCountId);

				// since this call is for dislike so set dislike count 1 and
				// like count 0
				sentimentLikeCount.setLikeCount(0l);
				sentimentLikeCount.setDislikeCount(1l);

				// save the new entry for count in database
				countService.create(sentimentLikeCount);
			} else {

				// if this is not the first dislike on the component, then
				// increment the dislike count by 1 and
				// update in database
				sentimentLikeCount.setDislikeCount(sentimentLikeCount
						.getDislikeCount() + 1l);
				countService.update(sentimentLikeCount);
			}

		} else {

			// if the like object already exists in database and the entry
			// was for like, decrement the like
			// count by 1
			if (sentimentLike.isLikeFlag()) {
				sentimentLikeCount.setLikeCount(sentimentLikeCount
						.getLikeCount() - 1l);
			}

			// set like flag false
			sentimentLike.setLikeFlag(false);
			// set dislike flag true
			sentimentLike.setDislikeFlag(true);

			// update the like object in the database
			sentimentLike = update(sentimentLike);

			// added user points after disLike on any component
			repository.addMonetizationPoints(likeDataBean, "Dislike");

			if (sentimentLikeCount != null) {

				// increment dislike count by 1
				sentimentLikeCount.setDislikeCount(sentimentLikeCount
						.getDislikeCount() + 1);

				// update the count object in the database
				countService.update(sentimentLikeCount);

			} else {

				Sentiment sentiment = new Sentiment();
				sentiment.setId(likeDataBean.getComponentId());

				SentimentLikeCountId sentimentLikeCountId = new SentimentLikeCountId();
				sentimentLikeCountId.setSentiment(sentiment);

				sentimentLikeCount = new SentimentLikeCount();
				sentimentLikeCount.setId(sentimentLikeCountId);

				sentimentLikeCount = new SentimentLikeCount();
				sentimentLikeCount.setId(sentimentLikeCountId);
				// since its a first entry for dislike on component hence
				// set like count to 0 and dislike count to 1
				sentimentLikeCount.setLikeCount(0l);
				sentimentLikeCount.setDislikeCount(1l);

				// create new entry in database
				countService.create(sentimentLikeCount);
			}
		}
		return sentimentLike;

	}

	@Override
	public SentimentLike undislike(LikeDataBean likeDataBean) throws Exception {

		logger.info("Entered SentimentLike service undislike method");

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
		SentimentLike sentimentLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		SentimentLikeCount sentimentLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// this if will never run in ideal condition
		if (sentimentLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			sentimentLike = new SentimentLike();
			sentimentLike.setLikeId(likeId);
			sentimentLike.setLikeFlag(false);
			sentimentLike.setDislikeFlag(false);

			sentimentLike = repository.save(sentimentLike);

			// deduct user points after unDislike on any component
			repository.addMonetizationPoints(likeDataBean, "Undislike");

			if (sentimentLikeCount == null) {

				Sentiment sentiment = new Sentiment();
				sentiment.setId(likeDataBean.getComponentId());

				SentimentLikeCountId sentimentLikeCountId = new SentimentLikeCountId();
				sentimentLikeCountId.setSentiment(sentiment);

				sentimentLikeCount = new SentimentLikeCount();
				sentimentLikeCount.setId(sentimentLikeCountId);

				sentimentLikeCount = new SentimentLikeCount();
				sentimentLikeCount.setId(sentimentLikeCountId);
				sentimentLikeCount.setLikeCount(0l);
				sentimentLikeCount.setDislikeCount(0l);

				countService.create(sentimentLikeCount);
			} else {

				sentimentLikeCount.setDislikeCount(sentimentLikeCount
						.getDislikeCount() - 1);
				countService.update(sentimentLikeCount);
			}

		} else {

			// because this is the call for undislike so set both like and
			// dislike flag to false
			sentimentLike.setLikeFlag(false);
			sentimentLike.setDislikeFlag(false);

			// update like entry in database
			sentimentLike = update(sentimentLike);

			// deduct user points after unDislike on any component
			repository.addMonetizationPoints(likeDataBean, "Undislike");

			if (sentimentLikeCount != null) {

				// decrement the like count by 1
				sentimentLikeCount.setDislikeCount(sentimentLikeCount
						.getDislikeCount() - 1);

				// update the change in count entry for component in
				// database
				countService.update(sentimentLikeCount);

			} else {
				// this will not run in ideal condition

				Sentiment sentiment = new Sentiment();
				sentiment.setId(likeDataBean.getComponentId());

				SentimentLikeCountId sentimentLikeCountId = new SentimentLikeCountId();
				sentimentLikeCountId.setSentiment(sentiment);

				sentimentLikeCount = new SentimentLikeCount();
				sentimentLikeCount.setId(sentimentLikeCountId);

				sentimentLikeCount = new SentimentLikeCount();
				sentimentLikeCount.setId(sentimentLikeCountId);
				sentimentLikeCount.setLikeCount(0l);
				sentimentLikeCount.setDislikeCount(0l);

				countService.create(sentimentLikeCount);
			}
		}

		return sentimentLike;
	}

}
