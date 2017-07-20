package com.ohmuk.folitics.hibernate.service.like;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.beans.LikeDataBean;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Response;
import com.ohmuk.folitics.hibernate.entity.like.LikeId;
import com.ohmuk.folitics.hibernate.entity.like.ResponseLike;
import com.ohmuk.folitics.hibernate.entity.like.ResponseLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.ResponseLikeCountId;
import com.ohmuk.folitics.hibernate.repository.like.IResponseLikeRepository;

@Service
@Transactional
public class ResponseLikeService implements ILikeService<ResponseLike> {

	private static Logger logger = LoggerFactory
			.getLogger(ResponseLikeService.class);

	@Autowired
	private ILikeCountService<ResponseLikeCount> countService;

	@Autowired
	private IResponseLikeRepository repository;

	@Override
	public ResponseLike create(LikeDataBean likeDataBean)
			throws MessageException, Exception {
		logger.info("Entered ResponseLike service create method");

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
		ResponseLike responseLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		ResponseLikeCount responseLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// if no entry exist for like in database
		if (responseLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			responseLike = new ResponseLike();
			responseLike.setId(likeId);

			// set dislike flag false and like flag true
			responseLike.setDislikeFlag(false);
			responseLike.setLikeFlag(true);

			// save the new entry for like event
			responseLike = repository.save(responseLike);

			// added monetization points to user
			repository.addMonetizationPoints(likeDataBean, "Like");

			// if count object is also null that means this is the first
			// like on the component
			if (responseLikeCount == null) {

				Response response = new Response();
				response.setId(likeDataBean.getComponentId());

				ResponseLikeCountId responseLikeCountId = new ResponseLikeCountId();
				responseLikeCountId.setResponse(response);

				responseLikeCount = new ResponseLikeCount();
				responseLikeCount.setId(responseLikeCountId);

				// since this call is for like so set like count 1 and
				// dislike count 0
				responseLikeCount.setLikeCount(1l);
				responseLikeCount.setDislikeCount(0l);

				// save the new entry for count in database
				countService.create(responseLikeCount);
			} else {
				// if this is not the first like on the component, then
				// increment the like count by 1 and update in
				// database
				responseLikeCount.setLikeCount(responseLikeCount.getLikeCount() + 1l);
				countService.update(responseLikeCount);
			}

		} else {

			// if the like object already exists in database and the entry
			// was for dislike, decrement the dislike
			// count by 1
			if (responseLike.isDislikeFlag()) {

				responseLikeCount
						.setDislikeCount(responseLikeCount.getDislikeCount() - 1l);
			}

			// increment like count by 1
			responseLikeCount.setLikeCount(responseLikeCount.getLikeCount() + 1l);
			// set dislike flag false
			responseLike.setDislikeFlag(false);
			// set like flag true
			responseLike.setLikeFlag(true);

			// update like entry in database
			responseLike = update(responseLike);
			// update count entry in database
			countService.update(responseLikeCount);

			// added monetization points to user
			repository.addMonetizationPoints(likeDataBean, "like");

		}
		return responseLike;

	}

	@Override
	public ResponseLike read(LikeId likeId) throws MessageException {
		logger.info("Entered ResponseLike service read method");

		if (likeId.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (likeId.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Getting ResponseLike with component id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());
		return repository.find(likeId);
	}

	@Override
	public List<ResponseLike> readAll() {
		logger.info("Entered ResponseLike service readAll method");
		logger.debug("Getting all ResponseLike");
		return repository.findAll();
	}

	@Override
	public ResponseLike update(ResponseLike responseLike) throws MessageException {

		logger.info("Entered ResponseLike service update method");

		if (responseLike.getId().getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (responseLike.getId().getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Updating ResponseLike with response id = "
				+ responseLike.getId().getComponentId() + " and user id = "
				+ responseLike.getId().getUserId());

		ResponseLike originalTaskLike = repository.find(responseLike.getId());

		if (originalTaskLike == null) {
			return null;
		}

		return repository.update(responseLike);
	}

	@Override
	public ResponseLike delete(LikeId likeId) throws MessageException {
		logger.info("Entered ResponseLike service delete method");

		if (likeId.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (likeId.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Deleting ResponseLike with response id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());

		// get the entry for count from database
		ResponseLikeCount responseLikeCount = (ResponseLikeCount) countService
				.getByComponentId(likeId.getComponentId());

		// delete the entry for count
		countService.delete(responseLikeCount);

		repository.delete(likeId);
		return repository.find(likeId);
	}

	@Override
	public ResponseLike delete(ResponseLike responseLike) throws MessageException {
		logger.info("Entered ResponseLike service delete method");

		if (responseLike.getId().getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (responseLike.getId().getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Deleting ResponseLike with response id = "
				+ responseLike.getId().getComponentId() + " and user id = "
				+ responseLike.getId().getUserId());

		return delete(responseLike.getId());
	}

	@Override
	public ResponseLike getByComponentIdAndUserId(Long componentId, Long userId)
			throws MessageException {
		logger.info("Entered ResponseLike service getByComponentIdAndUserId method");

		if (componentId == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (userId == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Getting responseLike with response id = " + componentId
				+ " and user id = " + userId);

		return repository.getByComponentIdAndUserId(componentId, userId);
	}

	@Override
	public ResponseLike unlike(LikeDataBean likeDataBean)
			throws MessageException, Exception {
		
		logger.info("Entered ResponseLike service unlike method");

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
		ResponseLike responseLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		ResponseLikeCount responseLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// this if will never run in ideal condition
		if (responseLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			responseLike = new ResponseLike();
			responseLike.setId(likeId);
			responseLike.setLikeFlag(false);
			responseLike.setDislikeFlag(false);

			responseLike = repository.save(responseLike);

			// deduct user points after unlike on any component
			repository.addMonetizationPoints(likeDataBean, "Unlike");

			if (responseLikeCount == null) {

				Response response = new Response();
				response.setId(likeDataBean.getComponentId());

				ResponseLikeCountId responseLikeCountId = new ResponseLikeCountId();
				responseLikeCountId.setResponse(response);

				responseLikeCount = new ResponseLikeCount();
				responseLikeCount.setId(responseLikeCountId);

				responseLikeCount = new ResponseLikeCount();
				responseLikeCount.setId(responseLikeCountId);
				responseLikeCount.setLikeCount(0l);
				responseLikeCount.setDislikeCount(0l);

				countService.create(responseLikeCount);
			} else {

				responseLikeCount.setLikeCount(responseLikeCount.getLikeCount() - 1);
				countService.update(responseLikeCount);
			}

		} else {

			// because this is the call for unlike so set both like and
			// dislike flag to false
			responseLike.setLikeFlag(false);
			responseLike.setDislikeFlag(false);

			// update like entry in database
			responseLike = update(responseLike);

			// deduct user points after unlike on any component
			repository.addMonetizationPoints(likeDataBean, "Unlike");

			if (responseLikeCount != null) {

				// decrement the like count by 1
				responseLikeCount.setLikeCount(responseLikeCount.getLikeCount() - 1);

				// update the change in count entry for component in
				// database
				countService.update(responseLikeCount);

			} else {
				// this will not run in ideal condition
				Response response = new Response();
				response.setId(likeDataBean.getComponentId());

				ResponseLikeCountId responseLikeCountId = new ResponseLikeCountId();
				responseLikeCountId.setResponse(response);

				responseLikeCount = new ResponseLikeCount();
				responseLikeCount.setId(responseLikeCountId);

				responseLikeCount = new ResponseLikeCount();
				responseLikeCount.setId(responseLikeCountId);
				responseLikeCount.setLikeCount(0l);
				responseLikeCount.setDislikeCount(0l);

				countService.create(responseLikeCount);
			}
		}
		return responseLike;
	}

	@Override
	public ResponseLike dislike(LikeDataBean likeDataBean)
			throws MessageException, Exception {
		logger.info("Entered ResponseLike service unlike method");

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
		ResponseLike responseLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		ResponseLikeCount taskLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// if no entry exist for like in database
		if (responseLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			responseLike = new ResponseLike();
			responseLike.setId(likeId);

			// set dislike flag true and like flag false
			responseLike.setLikeFlag(false);
			responseLike.setDislikeFlag(true);

			// save the new entry for like event
			responseLike = repository.save(responseLike);

			// added user points after disLike on any component
			repository.addMonetizationPoints(likeDataBean, "Dislike");

			// if count object is also null that means this is the first
			// dislike on the component
			if (taskLikeCount == null) {

				Response response = new Response();
				response.setId(likeDataBean.getComponentId());

				ResponseLikeCountId sentimentLikeCountId = new ResponseLikeCountId();
				sentimentLikeCountId.setResponse(response);

				taskLikeCount = new ResponseLikeCount();
				taskLikeCount.setId(sentimentLikeCountId);

				taskLikeCount = new ResponseLikeCount();
				taskLikeCount.setId(sentimentLikeCountId);
				// since this call is for dislike so set dislike count 1 and
				// like count 0
				taskLikeCount.setLikeCount(0l);
				taskLikeCount.setDislikeCount(1l);

				// save the new entry for count in database
				countService.create(taskLikeCount);
			} else {

				// if this is not the first dislike on the component, then
				// increment the dislike count by 1 and
				// update in database
				taskLikeCount
						.setDislikeCount(taskLikeCount.getDislikeCount() + 1l);
				countService.update(taskLikeCount);
			}

		} else {

			// if the like object already exists in database and the entry
			// was for like, decrement the like
			// count by 1
			if (responseLike.isLikeFlag()) {
				taskLikeCount.setLikeCount(taskLikeCount.getLikeCount() - 1l);
			}

			// set like flag false
			responseLike.setLikeFlag(false);
			// set dislike flag true
			responseLike.setDislikeFlag(true);

			// update the like object in the database
			responseLike = update(responseLike);

			// added user points after disLike on any component
			repository.addMonetizationPoints(likeDataBean, "Dislike");

			if (taskLikeCount != null) {

				// increment dislike count by 1
				taskLikeCount
						.setDislikeCount(taskLikeCount.getDislikeCount() + 1);

				// update the count object in the database
				countService.update(taskLikeCount);

			} else {

				Response response = new Response();
				response.setId(likeDataBean.getComponentId());

				ResponseLikeCountId taskLikeCountId = new ResponseLikeCountId();
				taskLikeCountId.setResponse(response);

				taskLikeCount = new ResponseLikeCount();
				taskLikeCount.setId(taskLikeCountId);

				taskLikeCount = new ResponseLikeCount();
				taskLikeCount.setId(taskLikeCountId);
				// since its a first entry for dislike on component hence
				// set like count to 0 and dislike count to 1
				taskLikeCount.setLikeCount(0l);
				taskLikeCount.setDislikeCount(1l);

				// create new entry in database
				countService.create(taskLikeCount);
			}
		}
		return responseLike;

	}

	@Override
	public ResponseLike undislike(LikeDataBean likeDataBean)
			throws MessageException, Exception {
		logger.info("Entered ResponseLike service undislike method");

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
		ResponseLike responseLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		ResponseLikeCount responseLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// this if will never run in ideal condition
		if (responseLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			responseLike = new ResponseLike();
			responseLike.setId(likeId);
			responseLike.setLikeFlag(false);
			responseLike.setDislikeFlag(false);

			responseLike = repository.save(responseLike);

			// deduct user points after unDislike on any component
			repository.addMonetizationPoints(likeDataBean, "Undislike");

			if (responseLikeCount == null) {

				Response response = new Response();
				response.setId(likeDataBean.getComponentId());

				ResponseLikeCountId responseLikeCountId = new ResponseLikeCountId();
				responseLikeCountId.setResponse(response);

				responseLikeCount = new ResponseLikeCount();
				responseLikeCount.setId(responseLikeCountId);

				responseLikeCount = new ResponseLikeCount();
				responseLikeCount.setId(responseLikeCountId);
				responseLikeCount.setLikeCount(0l);
				responseLikeCount.setDislikeCount(0l);

				countService.create(responseLikeCount);
			} else {

				responseLikeCount
						.setDislikeCount(responseLikeCount.getDislikeCount() - 1);
				countService.update(responseLikeCount);
			}

		} else {

			// because this is the call for undislike so set both like and
			// dislike flag to false
			responseLike.setLikeFlag(false);
			responseLike.setDislikeFlag(false);

			// update like entry in database
			responseLike = update(responseLike);

			// deduct user points after unDislike on any component
			repository.addMonetizationPoints(likeDataBean, "Undislike");

			if (responseLikeCount != null) {

				// decrement the like count by 1
				responseLikeCount
						.setDislikeCount(responseLikeCount.getDislikeCount() - 1);

				// update the change in count entry for component in
				// database
				countService.update(responseLikeCount);

			} else {
				// this will not run in ideal condition

				Response response = new Response();
				response.setId(likeDataBean.getComponentId());

				ResponseLikeCountId taskLikeCountId = new ResponseLikeCountId();
				taskLikeCountId.setResponse(response);

				responseLikeCount = new ResponseLikeCount();
				responseLikeCount.setId(taskLikeCountId);

				responseLikeCount = new ResponseLikeCount();
				responseLikeCount.setId(taskLikeCountId);
				responseLikeCount.setLikeCount(0l);
				responseLikeCount.setDislikeCount(0l);

				countService.create(responseLikeCount);
			}
		}

		return responseLike;
	}

}
