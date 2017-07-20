package com.ohmuk.folitics.hibernate.service.like;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.beans.LikeDataBean;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.GovtSchemeData;
import com.ohmuk.folitics.hibernate.entity.air.GovtSchemeDataCountId;
import com.ohmuk.folitics.hibernate.entity.like.GovtSchemeDataLike;
import com.ohmuk.folitics.hibernate.entity.like.GovtSchemeDataLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.LikeId;
import com.ohmuk.folitics.hibernate.repository.like.IGovtSchemeDataLikeRepository;

@Service
@Transactional
public class GovtSchemeDataLikeService implements
		ILikeService<GovtSchemeDataLike> {

	private static Logger logger = LoggerFactory
			.getLogger(GovtSchemeDataLikeService.class);

	@Autowired
	private ILikeCountService<GovtSchemeDataLikeCount> countService;

	@Autowired
	private IGovtSchemeDataLikeRepository repository;

	@Override
	public GovtSchemeDataLike create(LikeDataBean likeDataBean)
			throws MessageException, Exception {
		logger.info("Entered GovtSchemeDataLike service create method");

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
		GovtSchemeDataLike govtSchemeDataLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		GovtSchemeDataLikeCount govtSchemeDataLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// if no entry exist for like in database
		if (govtSchemeDataLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			govtSchemeDataLike = new GovtSchemeDataLike();
			govtSchemeDataLike.setId(likeId);

			// set dislike flag false and like flag true
			govtSchemeDataLike.setDislikeFlag(false);
			govtSchemeDataLike.setLikeFlag(true);

			// save the new entry for like event
			govtSchemeDataLike = repository.save(govtSchemeDataLike);

			// added monetization points to user
			repository.addMonetizationPoints(likeDataBean, "Like");

			// if count object is also null that means this is the first
			// like on the component
			if (govtSchemeDataLikeCount == null) {

				GovtSchemeData govtSchemeData = new GovtSchemeData();
				govtSchemeData.setId(likeDataBean.getComponentId());

				GovtSchemeDataCountId govtSchemeDataCountId = new GovtSchemeDataCountId();
				govtSchemeDataCountId.setGovtSchemeData(govtSchemeData);

				govtSchemeDataLikeCount = new GovtSchemeDataLikeCount();
				govtSchemeDataLikeCount.setId(govtSchemeDataCountId);

				// since this call is for like so set like count 1 and
				// dislike count 0
				govtSchemeDataLikeCount.setLikeCount(1l);
				govtSchemeDataLikeCount.setDislikeCount(0l);

				// save the new entry for count in database
				countService.create(govtSchemeDataLikeCount);
			} else {
				// if this is not the first like on the component, then
				// increment the like count by 1 and update in
				// database
				govtSchemeDataLikeCount.setLikeCount(govtSchemeDataLikeCount.getLikeCount() + 1l);
				countService.update(govtSchemeDataLikeCount);
			}

		} else {

			// if the like object already exists in database and the entry
			// was for dislike, decrement the dislike
			// count by 1
			if (govtSchemeDataLike.isDislikeFlag()) {

				govtSchemeDataLikeCount
						.setDislikeCount(govtSchemeDataLikeCount.getDislikeCount() - 1l);
			}

			// increment like count by 1
			govtSchemeDataLikeCount.setLikeCount(govtSchemeDataLikeCount.getLikeCount() + 1l);
			// set dislike flag false
			govtSchemeDataLike.setDislikeFlag(false);
			// set like flag true
			govtSchemeDataLike.setLikeFlag(true);

			// update like entry in database
			govtSchemeDataLike = update(govtSchemeDataLike);
			// update count entry in database
			countService.update(govtSchemeDataLikeCount);

			// added monetization points to user
			repository.addMonetizationPoints(likeDataBean, "like");

		}
		return govtSchemeDataLike;

	}

	@Override
	public GovtSchemeDataLike read(LikeId likeId) throws MessageException {
		logger.info("Entered GovtSchemeDataLike service read method");

		if (likeId.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (likeId.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Getting GovtSchemeDataLike with component id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());
		return repository.find(likeId);
	}

	@Override
	public List<GovtSchemeDataLike> readAll() {
		logger.info("Entered GovtSchemeDataLike service readAll method");
		logger.debug("Getting all GovtSchemeDataLike");
		return repository.findAll();
	}

	@Override
	public GovtSchemeDataLike update(GovtSchemeDataLike govtSchemeDataLike)
			throws MessageException {
		logger.info("Entered GovtSchemeDataLike service update method");

		if (govtSchemeDataLike.getId().getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (govtSchemeDataLike.getId().getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Updating GovtSchemeDataLike with govtSchemeData id = "
				+ govtSchemeDataLike.getId().getComponentId() + " and user id = "
				+ govtSchemeDataLike.getId().getUserId());

		GovtSchemeDataLike originalgGovtSchemeDataLike = repository.find(govtSchemeDataLike.getId());

		if (originalgGovtSchemeDataLike == null) {
			return null;
		}

		return repository.update(govtSchemeDataLike);
	}

	@Override
	public GovtSchemeDataLike delete(LikeId likeId) throws MessageException {
		logger.info("Entered GovtSchemeDataLike service delete method");

		if (likeId.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (likeId.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Deleting GovtSchemeDataLike with govtSchemeData id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());

		// get the entry for count from database
		GovtSchemeDataLikeCount govtSchemeDataLikeCount = (GovtSchemeDataLikeCount) countService
				.getByComponentId(likeId.getComponentId());

		// delete the entry for count
		countService.delete(govtSchemeDataLikeCount);

		repository.delete(likeId);
		return repository.find(likeId);
	}

	@Override
	public GovtSchemeDataLike delete(GovtSchemeDataLike govtSchemeDataLike)
			throws MessageException {
		logger.info("Entered GovtSchemeDataLike service delete method");

		if (govtSchemeDataLike.getId().getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (govtSchemeDataLike.getId().getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Deleting GovtSchemeDataLike with govtSchemeData id = "
				+ govtSchemeDataLike.getId().getComponentId() + " and user id = "
				+ govtSchemeDataLike.getId().getUserId());

		return delete(govtSchemeDataLike.getId());
	}

	@Override
	public GovtSchemeDataLike getByComponentIdAndUserId(Long componentId,
			Long userId) throws MessageException {
		logger.info("Entered GovtSchemeDataLike service getByComponentIdAndUserId method");

		if (componentId == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (userId == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Getting govtSchemeDataLike with govtSchemeData id = " + componentId
				+ " and user id = " + userId);

		return repository.getByComponentIdAndUserId(componentId, userId);
	}

	@Override
	public GovtSchemeDataLike unlike(LikeDataBean likeDataBean)
			throws MessageException, Exception {
		logger.info("Entered GovtSchemeDataLike service unlike method");

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
		GovtSchemeDataLike govtSchemeDataLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		GovtSchemeDataLikeCount govtSchemeDataLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// this if will never run in ideal condition
		if (govtSchemeDataLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			govtSchemeDataLike = new GovtSchemeDataLike();
			govtSchemeDataLike.setId(likeId);
			govtSchemeDataLike.setLikeFlag(false);
			govtSchemeDataLike.setDislikeFlag(false);

			govtSchemeDataLike = repository.save(govtSchemeDataLike);

			// deduct user points after unlike on any component
			repository.addMonetizationPoints(likeDataBean, "Unlike");

			if (govtSchemeDataLikeCount == null) {

				GovtSchemeData govtSchemeData = new GovtSchemeData();
				govtSchemeData.setId(likeDataBean.getComponentId());

				GovtSchemeDataCountId govtSchemeDataCountId = new GovtSchemeDataCountId();
				govtSchemeDataCountId.setGovtSchemeData(govtSchemeData);

				govtSchemeDataLikeCount = new GovtSchemeDataLikeCount();
				govtSchemeDataLikeCount.setId(govtSchemeDataCountId);

				govtSchemeDataLikeCount = new GovtSchemeDataLikeCount();
				govtSchemeDataLikeCount.setId(govtSchemeDataCountId);
				govtSchemeDataLikeCount.setLikeCount(0l);
				govtSchemeDataLikeCount.setDislikeCount(0l);

				countService.create(govtSchemeDataLikeCount);
			} else {

				govtSchemeDataLikeCount.setLikeCount(govtSchemeDataLikeCount.getLikeCount() - 1);
				countService.update(govtSchemeDataLikeCount);
			}

		} else {

			// because this is the call for unlike so set both like and
			// dislike flag to false
			govtSchemeDataLike.setLikeFlag(false);
			govtSchemeDataLike.setDislikeFlag(false);

			// update like entry in database
			govtSchemeDataLike = update(govtSchemeDataLike);

			// deduct user points after unlike on any component
			repository.addMonetizationPoints(likeDataBean, "Unlike");

			if (govtSchemeDataLikeCount != null) {

				// decrement the like count by 1
				govtSchemeDataLikeCount.setLikeCount(govtSchemeDataLikeCount.getLikeCount() - 1);

				// update the change in count entry for component in
				// database
				countService.update(govtSchemeDataLikeCount);

			} else {
				// this will not run in ideal condition
				GovtSchemeData govtSchemeData = new GovtSchemeData();
				govtSchemeData.setId(likeDataBean.getComponentId());

				GovtSchemeDataCountId govtSchemeDataCountId = new GovtSchemeDataCountId();
				govtSchemeDataCountId.setGovtSchemeData(govtSchemeData);

				govtSchemeDataLikeCount = new GovtSchemeDataLikeCount();
				govtSchemeDataLikeCount.setId(govtSchemeDataCountId);

				govtSchemeDataLikeCount = new GovtSchemeDataLikeCount();
				govtSchemeDataLikeCount.setId(govtSchemeDataCountId);
				govtSchemeDataLikeCount.setLikeCount(0l);
				govtSchemeDataLikeCount.setDislikeCount(0l);

				countService.create(govtSchemeDataLikeCount);
			}
		}
		return govtSchemeDataLike;
	}

	@Override
	public GovtSchemeDataLike dislike(LikeDataBean likeDataBean)
			throws MessageException, Exception {
		logger.info("Entered GovtSchemeDataLike service unlike method");

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
		GovtSchemeDataLike govtSchemeDataLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		GovtSchemeDataLikeCount govtSchemeDataLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// if no entry exist for like in database
		if (govtSchemeDataLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			govtSchemeDataLike = new GovtSchemeDataLike();
			govtSchemeDataLike.setId(likeId);

			// set dislike flag true and like flag false
			govtSchemeDataLike.setLikeFlag(false);
			govtSchemeDataLike.setDislikeFlag(true);

			// save the new entry for like event
			govtSchemeDataLike = repository.save(govtSchemeDataLike);

			// added user points after disLike on any component
			repository.addMonetizationPoints(likeDataBean, "Dislike");

			// if count object is also null that means this is the first
			// dislike on the component
			if (govtSchemeDataLikeCount == null) {

				GovtSchemeData govtSchemeData = new GovtSchemeData();
				govtSchemeData.setId(likeDataBean.getComponentId());

				GovtSchemeDataCountId govtSchemeDataCountId = new GovtSchemeDataCountId();
				govtSchemeDataCountId.setGovtSchemeData(govtSchemeData);

				govtSchemeDataLikeCount = new GovtSchemeDataLikeCount();
				govtSchemeDataLikeCount.setId(govtSchemeDataCountId);

				govtSchemeDataLikeCount = new GovtSchemeDataLikeCount();
				govtSchemeDataLikeCount.setId(govtSchemeDataCountId);
				// since this call is for dislike so set dislike count 1 and
				// like count 0
				govtSchemeDataLikeCount.setLikeCount(0l);
				govtSchemeDataLikeCount.setDislikeCount(1l);

				// save the new entry for count in database
				countService.create(govtSchemeDataLikeCount);
			} else {

				// if this is not the first dislike on the component, then
				// increment the dislike count by 1 and
				// update in database
				govtSchemeDataLikeCount
						.setDislikeCount(govtSchemeDataLikeCount.getDislikeCount() + 1l);
				countService.update(govtSchemeDataLikeCount);
			}

		} else {

			// if the like object already exists in database and the entry
			// was for like, decrement the like
			// count by 1
			if (govtSchemeDataLike.isLikeFlag()) {
				govtSchemeDataLikeCount.setLikeCount(govtSchemeDataLikeCount.getLikeCount() - 1l);
			}

			// set like flag false
			govtSchemeDataLike.setLikeFlag(false);
			// set dislike flag true
			govtSchemeDataLike.setDislikeFlag(true);

			// update the like object in the database
			govtSchemeDataLike = update(govtSchemeDataLike);

			// added user points after disLike on any component
			repository.addMonetizationPoints(likeDataBean, "Dislike");

			if (govtSchemeDataLikeCount != null) {

				// increment dislike count by 1
				govtSchemeDataLikeCount
						.setDislikeCount(govtSchemeDataLikeCount.getDislikeCount() + 1);

				// update the count object in the database
				countService.update(govtSchemeDataLikeCount);

			} else {

				GovtSchemeData govtSchemeData = new GovtSchemeData();
				govtSchemeData.setId(likeDataBean.getComponentId());

				GovtSchemeDataCountId govtSchemeDataCountId = new GovtSchemeDataCountId();
				govtSchemeDataCountId.setGovtSchemeData(govtSchemeData);

				govtSchemeDataLikeCount = new GovtSchemeDataLikeCount();
				govtSchemeDataLikeCount.setId(govtSchemeDataCountId);

				govtSchemeDataLikeCount = new GovtSchemeDataLikeCount();
				govtSchemeDataLikeCount.setId(govtSchemeDataCountId);
				// since its a first entry for dislike on component hence
				// set like count to 0 and dislike count to 1
				govtSchemeDataLikeCount.setLikeCount(0l);
				govtSchemeDataLikeCount.setDislikeCount(1l);

				// create new entry in database
				countService.create(govtSchemeDataLikeCount);
			}
		}
		return govtSchemeDataLike;
	}

	@Override
	public GovtSchemeDataLike undislike(LikeDataBean likeDataBean)
			throws MessageException, Exception {
		logger.info("Entered GovtSchemeDataLike service undislike method");

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
		GovtSchemeDataLike govtSchemeDataLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		GovtSchemeDataLikeCount govtSchemeDataLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// this if will never run in ideal condition
		if (govtSchemeDataLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			govtSchemeDataLike = new GovtSchemeDataLike();
			govtSchemeDataLike.setId(likeId);
			govtSchemeDataLike.setLikeFlag(false);
			govtSchemeDataLike.setDislikeFlag(false);

			govtSchemeDataLike = repository.save(govtSchemeDataLike);

			// deduct user points after unDislike on any component
			repository.addMonetizationPoints(likeDataBean, "Undislike");

			if (govtSchemeDataLikeCount == null) {

				GovtSchemeData govtSchemeData = new GovtSchemeData();
				govtSchemeData.setId(likeDataBean.getComponentId());

				GovtSchemeDataCountId sentimentLikeCountId = new GovtSchemeDataCountId();
				sentimentLikeCountId.setGovtSchemeData(govtSchemeData);

				govtSchemeDataLikeCount = new GovtSchemeDataLikeCount();
				govtSchemeDataLikeCount.setId(sentimentLikeCountId);

				govtSchemeDataLikeCount = new GovtSchemeDataLikeCount();
				govtSchemeDataLikeCount.setId(sentimentLikeCountId);
				govtSchemeDataLikeCount.setLikeCount(0l);
				govtSchemeDataLikeCount.setDislikeCount(0l);

				countService.create(govtSchemeDataLikeCount);
			} else {

				govtSchemeDataLikeCount
						.setDislikeCount(govtSchemeDataLikeCount.getDislikeCount() - 1);
				countService.update(govtSchemeDataLikeCount);
			}

		} else {

			// because this is the call for undislike so set both like and
			// dislike flag to false
			govtSchemeDataLike.setLikeFlag(false);
			govtSchemeDataLike.setDislikeFlag(false);

			// update like entry in database
			govtSchemeDataLike = update(govtSchemeDataLike);

			// deduct user points after unDislike on any component
			repository.addMonetizationPoints(likeDataBean, "Undislike");

			if (govtSchemeDataLikeCount != null) {

				// decrement the like count by 1
				govtSchemeDataLikeCount
						.setDislikeCount(govtSchemeDataLikeCount.getDislikeCount() - 1);

				// update the change in count entry for component in
				// database
				countService.update(govtSchemeDataLikeCount);

			} else {
				// this will not run in ideal condition

				GovtSchemeData govtSchemeData = new GovtSchemeData();
				govtSchemeData.setId(likeDataBean.getComponentId());

				GovtSchemeDataCountId govtSchemeDataCountId = new GovtSchemeDataCountId();
				govtSchemeDataCountId.setGovtSchemeData(govtSchemeData);

				govtSchemeDataLikeCount = new GovtSchemeDataLikeCount();
				govtSchemeDataLikeCount.setId(govtSchemeDataCountId);

				govtSchemeDataLikeCount = new GovtSchemeDataLikeCount();
				govtSchemeDataLikeCount.setId(govtSchemeDataCountId);
				govtSchemeDataLikeCount.setLikeCount(0l);
				govtSchemeDataLikeCount.setDislikeCount(0l);

				countService.create(govtSchemeDataLikeCount);
			}
		}

		return govtSchemeDataLike;
	}

}
