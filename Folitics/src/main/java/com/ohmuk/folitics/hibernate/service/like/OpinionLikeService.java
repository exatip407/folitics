package com.ohmuk.folitics.hibernate.service.like;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.beans.LikeDataBean;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Opinion;
import com.ohmuk.folitics.hibernate.entity.like.LikeId;
import com.ohmuk.folitics.hibernate.entity.like.OpinionLike;
import com.ohmuk.folitics.hibernate.entity.like.OpinionLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.OpinionLikeCountId;
import com.ohmuk.folitics.hibernate.repository.like.IOpinionLikeRepository;

/**
 * @author Harish
 *
 */

@Service
@Transactional
public class OpinionLikeService implements ILikeService<OpinionLike> {

	private static Logger logger = LoggerFactory.getLogger(SentimentLikeService.class);

	@Autowired
	private IOpinionLikeRepository repository;

	@Autowired
	private ILikeCountService<OpinionLikeCount> countService;

	@Override
	public OpinionLike create(LikeDataBean likeDataBean) throws MessageException, Exception {
		logger.info("Entered OpinionLike service create method");

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
		OpinionLike opinionLike = getByComponentIdAndUserId(likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		OpinionLikeCount opinionLikeCount = countService.getByComponentId(likeDataBean.getComponentId());

		// if no entry exist for like in database
		if (opinionLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			opinionLike = new OpinionLike();
			opinionLike.setLikeId(likeId);

			// set dislike flag false and like flag true
			opinionLike.setDislikeFlag(false);
			opinionLike.setLikeFlag(true);

			// save the new entry for like event
			opinionLike = repository.save(opinionLike);

			// added monetization points to user
			repository.addMonetizationPoints(likeDataBean, "Like");

			// if count object is also null that means this is the first
			// like on the component
			if (opinionLikeCount == null) {

				Opinion opinion = new Opinion();
				opinion.setId(likeDataBean.getComponentId());

				OpinionLikeCountId sentimentLikeCountId = new OpinionLikeCountId();
				sentimentLikeCountId.setOpinion(opinion);

				opinionLikeCount = new OpinionLikeCount();
				opinionLikeCount.setId(sentimentLikeCountId);

				// since this call is for like so set like count 1 and
				// dislike count 0
				opinionLikeCount.setLikeCount(1l);
				opinionLikeCount.setDislikeCount(0l);

				// save the new entry for count in database
				countService.create(opinionLikeCount);
			} else {
				// if this is not the first like on the component, then
				// increment the like count by 1 and update in
				// database
				opinionLikeCount.setLikeCount(opinionLikeCount.getLikeCount() + 1l);
				countService.update(opinionLikeCount);
			}

		} else {

			// if the like object already exists in database and the entry
			// was for dislike, decrement the dislike
			// count by 1
			if (opinionLike.isDislikeFlag()) {

				opinionLikeCount.setDislikeCount(opinionLikeCount.getDislikeCount() - 1l);
			}

			// increment like count by 1
			opinionLikeCount.setLikeCount(opinionLikeCount.getLikeCount() + 1l);
			// set dislike flag false
			opinionLike.setDislikeFlag(false);
			// set like flag true
			opinionLike.setLikeFlag(true);

			// update like entry in database
			opinionLike = update(opinionLike);
			// update count entry in database
			countService.update(opinionLikeCount);

		}
		return opinionLike;

	}

	@Override
	public OpinionLike read(LikeId likeId) throws MessageException {
		logger.info("Entered OpinionLike service read method");

		if (likeId.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (likeId.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Getting OpinionLike with component id = " + likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());
		return repository.find(likeId);
	}

	@Override
	public List<OpinionLike> readAll() {
		logger.info("Entered OpinionLike service readAll method");
		logger.debug("Getting all OpinionLike");
		return repository.findAll();
	}

	@Override
	public OpinionLike update(OpinionLike opinionLike) throws MessageException {
		logger.info("Entered OpinionLike service update method");

		if (opinionLike.getLikeId().getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (opinionLike.getLikeId().getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Updating OpinionLike with sentiment id = " + opinionLike.getLikeId().getComponentId()
				+ " and user id = " + opinionLike.getLikeId().getUserId());

		OpinionLike originalOpinionLike = repository.find(opinionLike.getLikeId());

		if (originalOpinionLike == null) {
			return null;
		}

		return repository.update(opinionLike);
	}

	@Override
	public OpinionLike delete(LikeId likeId) throws MessageException {
		logger.info("Entered OpinionLike service delete method");

		if (likeId.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (likeId.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Deleting OpinionLike with sentiment id = " + likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());

		// get the entry for count from database
		OpinionLikeCount sentimentLikeCount = (OpinionLikeCount) countService.getByComponentId(likeId.getComponentId());

		// delete the entry for count
		countService.delete(sentimentLikeCount);

		repository.delete(likeId);
		return repository.find(likeId);
	}

	@Override
	public OpinionLike delete(OpinionLike opinionLike) throws MessageException {
		logger.info("Entered OpinionLike service delete method");

		if (opinionLike.getLikeId().getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (opinionLike.getLikeId().getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Deleting OpinionLike with sentiment id = " + opinionLike.getLikeId().getComponentId()
				+ " and user id = " + opinionLike.getLikeId().getUserId());

		return delete(opinionLike.getLikeId());
	}

	@Override
	public OpinionLike getByComponentIdAndUserId(Long componentId, Long userId) throws MessageException {
		logger.info("Entered OpinionLike service getByComponentIdAndUserId method");

		if (componentId == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (userId == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Getting OpinionLike with sentiment id = " + componentId + " and user id = " + userId);

		return repository.getByComponentIdAndUserId(componentId, userId);
	}

	@Override
	public OpinionLike unlike(LikeDataBean likeDataBean) throws MessageException, Exception {
		logger.info("Entered OpinionLike service unlike method");

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
		OpinionLike OpinionLike = getByComponentIdAndUserId(likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		OpinionLikeCount opinionLikeCount = countService.getByComponentId(likeDataBean.getComponentId());

		// this if will never run in ideal condition
		if (OpinionLike != null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			OpinionLike = new OpinionLike();
			OpinionLike.setLikeId(likeId);
			OpinionLike.setLikeFlag(false);
			OpinionLike.setDislikeFlag(false);

			OpinionLike = repository.save(OpinionLike);

			// deduct user points after unlike on any component
			repository.addMonetizationPoints(likeDataBean, "Unlike");

			if (opinionLikeCount == null) {

				Opinion opinion = new Opinion();
				opinion.setId(likeDataBean.getComponentId());

				OpinionLikeCountId sentimentLikeCountId = new OpinionLikeCountId();
				sentimentLikeCountId.setOpinion(opinion);

				opinionLikeCount = new OpinionLikeCount();
				opinionLikeCount.setId(sentimentLikeCountId);

				opinionLikeCount = new OpinionLikeCount();
				opinionLikeCount.setId(sentimentLikeCountId);
				opinionLikeCount.setLikeCount(0l);
				opinionLikeCount.setDislikeCount(0l);

				countService.create(opinionLikeCount);
			} else {

				opinionLikeCount.setLikeCount(opinionLikeCount.getLikeCount() - 1);
				countService.update(opinionLikeCount);
			}

		} else {

			// because this is the call for unlike so set both like and
			// dislike flag to false
			OpinionLike.setLikeFlag(false);
			OpinionLike.setDislikeFlag(false);

			// update like entry in database
			OpinionLike = update(OpinionLike);

			// deduct user points after unlike on any component
			repository.addMonetizationPoints(likeDataBean, "Unlike");

			if (opinionLikeCount != null) {

				// decrement the like count by 1
				opinionLikeCount.setLikeCount(opinionLikeCount.getLikeCount() - 1);

				// update the change in count entry for component in
				// database
				countService.update(opinionLikeCount);

			} else {
				// this will not run in ideal condition
				Opinion opinion = new Opinion();
				opinion.setId(likeDataBean.getComponentId());

				OpinionLikeCountId opinionLikeCountId = new OpinionLikeCountId();
				opinionLikeCountId.setOpinion(opinion);

				opinionLikeCount = new OpinionLikeCount();
				opinionLikeCount.setId(opinionLikeCountId);

				opinionLikeCount = new OpinionLikeCount();
				opinionLikeCount.setId(opinionLikeCountId);
				opinionLikeCount.setLikeCount(0l);
				opinionLikeCount.setDislikeCount(0l);

				countService.create(opinionLikeCount);
			}
		}

		return OpinionLike;
	}

	@Override
	public OpinionLike dislike(LikeDataBean likeDataBean) throws MessageException, Exception {
		logger.info("Entered OpinionLike service unlike method");

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
		OpinionLike OpinionLike = getByComponentIdAndUserId(likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		OpinionLikeCount opinionLikeCount = countService.getByComponentId(likeDataBean.getComponentId());

		// if no entry exist for like in database
		if (OpinionLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			OpinionLike = new OpinionLike();
			OpinionLike.setLikeId(likeId);

			// set dislike flag true and like flag false
			OpinionLike.setLikeFlag(false);
			OpinionLike.setDislikeFlag(true);

			// save the new entry for like event
			OpinionLike = repository.save(OpinionLike);

			// added user points after disLike on any component
			repository.addMonetizationPoints(likeDataBean, "Dislike");

			// if count object is also null that means this is the first
			// dislike on the component
			if (opinionLikeCount == null) {

				Opinion opinion = new Opinion();
				opinion.setId(likeDataBean.getComponentId());

				OpinionLikeCountId sentimentLikeCountId = new OpinionLikeCountId();
				sentimentLikeCountId.setOpinion(opinion);

				opinionLikeCount = new OpinionLikeCount();
				opinionLikeCount.setId(sentimentLikeCountId);

				opinionLikeCount = new OpinionLikeCount();
				opinionLikeCount.setId(sentimentLikeCountId);

				// since this call is for dislike so set dislike count 1 and
				// like count 0
				opinionLikeCount.setLikeCount(0l);
				opinionLikeCount.setDislikeCount(1l);

				// save the new entry for count in database
				countService.create(opinionLikeCount);
			} else {

				// if this is not the first dislike on the component, then
				// increment the dislike count by 1 and
				// update in database
				opinionLikeCount.setDislikeCount(opinionLikeCount.getDislikeCount() + 1l);
				countService.update(opinionLikeCount);
			}

		} else {

			// if the like object already exists in database and the entry
			// was for like, decrement the like
			// count by 1
			if (OpinionLike.isLikeFlag()) {
				opinionLikeCount.setLikeCount(opinionLikeCount.getLikeCount() - 1l);
			}

			// set like flag false
			OpinionLike.setLikeFlag(false);
			// set dislike flag true
			OpinionLike.setDislikeFlag(true);

			// update the like object in the database
			OpinionLike = update(OpinionLike);

			if (opinionLikeCount != null) {

				// increment dislike count by 1
				opinionLikeCount.setDislikeCount(opinionLikeCount.getDislikeCount() + 1);

				// update the count object in the database
				countService.update(opinionLikeCount);

			} else {

				Opinion opinion = new Opinion();
				opinion.setId(likeDataBean.getComponentId());

				OpinionLikeCountId opinionLikeCountId = new OpinionLikeCountId();
				opinionLikeCountId.setOpinion(opinion);

				opinionLikeCount = new OpinionLikeCount();
				opinionLikeCount.setId(opinionLikeCountId);

				opinionLikeCount = new OpinionLikeCount();
				opinionLikeCount.setId(opinionLikeCountId);
				// since its a first entry for dislike on component hence
				// set like count to 0 and dislike count to 1
				opinionLikeCount.setLikeCount(0l);
				opinionLikeCount.setDislikeCount(1l);

				// create new entry in database
				countService.create(opinionLikeCount);
			}
		}
		return OpinionLike;

	}

	@Override
	public OpinionLike undislike(LikeDataBean likeDataBean) throws MessageException, Exception {
		logger.info("Entered OpinionLike service undislike method");

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
		OpinionLike OpinionLike = getByComponentIdAndUserId(likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		OpinionLikeCount opinionLikeCount = countService.getByComponentId(likeDataBean.getComponentId());

		// this if will never run in ideal condition
		if (OpinionLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			OpinionLike = new OpinionLike();
			OpinionLike.setLikeId(likeId);
			OpinionLike.setLikeFlag(false);
			OpinionLike.setDislikeFlag(false);

			OpinionLike = repository.save(OpinionLike);

			// deduct user points after unDislike on any component
			repository.addMonetizationPoints(likeDataBean, "Undislike");

			if (opinionLikeCount == null) {

				Opinion opinion = new Opinion();
				opinion.setId(likeDataBean.getComponentId());

				OpinionLikeCountId opinionLikeCountId = new OpinionLikeCountId();
				opinionLikeCountId.setOpinion(opinion);

				opinionLikeCount = new OpinionLikeCount();
				opinionLikeCount.setId(opinionLikeCountId);

				opinionLikeCount = new OpinionLikeCount();
				opinionLikeCount.setId(opinionLikeCountId);
				opinionLikeCount.setLikeCount(0l);
				opinionLikeCount.setDislikeCount(0l);

				countService.create(opinionLikeCount);
			} else {

				opinionLikeCount.setDislikeCount(opinionLikeCount.getDislikeCount() - 1);
				countService.update(opinionLikeCount);
			}

		} else {

			// because this is the call for undislike so set both like and
			// dislike flag to false
			OpinionLike.setLikeFlag(false);
			OpinionLike.setDislikeFlag(false);

			// update like entry in database
			OpinionLike = update(OpinionLike);

			// deduct user points after unDislike on any component
			repository.addMonetizationPoints(likeDataBean, "Undislike");

			if (opinionLikeCount != null) {

				// decrement the like count by 1
				opinionLikeCount.setDislikeCount(opinionLikeCount.getDislikeCount() - 1);

				// update the change in count entry for component in
				// database
				countService.update(opinionLikeCount);

			} else {
				// this will not run in ideal condition

				Opinion opinion = new Opinion();
				opinion.setId(likeDataBean.getComponentId());

				OpinionLikeCountId opinionLikeCountId = new OpinionLikeCountId();
				opinionLikeCountId.setOpinion(opinion);

				opinionLikeCount = new OpinionLikeCount();
				opinionLikeCount.setId(opinionLikeCountId);

				opinionLikeCount = new OpinionLikeCount();
				opinionLikeCount.setId(opinionLikeCountId);
				opinionLikeCount.setLikeCount(0l);
				opinionLikeCount.setDislikeCount(0l);

				countService.create(opinionLikeCount);
			}
		}

		return OpinionLike;
	}

}
