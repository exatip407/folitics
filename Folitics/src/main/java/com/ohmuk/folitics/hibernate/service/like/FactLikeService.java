package com.ohmuk.folitics.hibernate.service.like;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.beans.LikeDataBean;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Fact;
import com.ohmuk.folitics.hibernate.entity.like.FactLike;
import com.ohmuk.folitics.hibernate.entity.like.FactLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.FactLikeCountId;
import com.ohmuk.folitics.hibernate.entity.like.LikeId;
import com.ohmuk.folitics.hibernate.repository.like.IFactLikeRepository;

@Service
@Transactional
public class FactLikeService implements ILikeService<FactLike> {

	private static Logger logger = LoggerFactory
			.getLogger(FactLikeService.class);

	@Autowired
	private IFactLikeRepository repository;

	@Autowired
	private ILikeCountService<FactLikeCount> countService;

	@Override
	public FactLike create(LikeDataBean likeDataBean) throws MessageException,
			Exception {
		logger.info("Entered FactLike service create method");

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
		FactLike factLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		FactLikeCount factLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// if no entry exist for like in database
		if (factLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			factLike = new FactLike();
			factLike.setId(likeId);

			// set dislike flag false and like flag true
			factLike.setDislikeFlag(false);
			factLike.setLikeFlag(true);

			// save the new entry for like event
			factLike = repository.save(factLike);

			// added monetization points to user
			repository.addMonetizationPoints(likeDataBean, "like");

			// if count object is also null that means this is the first
			// like on the component
			if (factLikeCount == null) {

				Fact fact = new Fact();
				fact.setId(likeDataBean.getComponentId());

				FactLikeCountId factLikeCountId = new FactLikeCountId();
				factLikeCountId.setFact(fact);

				factLikeCount = new FactLikeCount();
				factLikeCount.setId(factLikeCountId);

				// since this call is for like so set like count 1 and
				// dislike count 0
				factLikeCount.setLikeCount(1l);
				factLikeCount.setDislikeCount(0l);

				// save the new entry for count in database
				countService.create(factLikeCount);
			} else {
				// if this is not the first like on the component, then
				// increment the like count by 1 and update in
				// database
				factLikeCount.setLikeCount(factLikeCount.getLikeCount() + 1l);
				countService.update(factLikeCount);
			}

		} else {

			// if the like object already exists in database and the entry
			// was for dislike, decrement the dislike
			// count by 1
			if (factLike.isDislikeFlag()) {

				factLikeCount
						.setDislikeCount(factLikeCount.getDislikeCount() - 1l);
			}

			// increment like count by 1
			factLikeCount.setLikeCount(factLikeCount.getLikeCount() + 1l);
			// set dislike flag false
			factLike.setDislikeFlag(false);
			// set like flag true
			factLike.setLikeFlag(true);

			// update like entry in database
			factLike = update(factLike);
			// update count entry in database
			countService.update(factLikeCount);

			// added monetization points to user
			repository.addMonetizationPoints(likeDataBean, "like");

		}

		return factLike;
	}

	@Override
	public FactLike read(LikeId likeId) throws MessageException {
		logger.info("Entered FactLike service read method");

		if (likeId.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (likeId.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Getting FactLike with component id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());
		return repository.find(likeId);
	}

	@Override
	public List<FactLike> readAll() {
		logger.info("Entered FactLike service readAll method");
		logger.debug("Getting all FactLike");
		return repository.findAll();
	}

	@Override
	public FactLike update(FactLike factLike) throws MessageException {
		logger.info("Entered FactLike service update method");

		if (factLike.getId().getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (factLike.getId().getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Updating FactLike with sentiment id = "
				+ factLike.getId().getComponentId()
				+ " and user id = " + factLike.getId().getUserId());

		FactLike originalFactLike = repository.find(factLike
				.getId());

		if (originalFactLike == null) {
			return null;
		}

		return repository.update(factLike);
	}

	@Override
	public FactLike delete(LikeId likeId) throws MessageException {
		logger.info("Entered FactLike service delete method");

		if (likeId.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (likeId.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Deleting FactLike with sentiment id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());

		// get the entry for count from database
		FactLikeCount factLikeCount = (FactLikeCount) countService
				.getByComponentId(likeId.getComponentId());

		// delete the entry for count
		countService.delete(factLikeCount);

		repository.delete(likeId);
		return repository.find(likeId);
	}

	@Override
	public FactLike delete(FactLike factLike) throws MessageException {

		logger.info("Entered FactLike service delete method");

		if (factLike.getId().getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (factLike.getId().getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Deleting FactLike with sentiment id = "
				+ factLike.getId().getComponentId()
				+ " and user id = " + factLike.getId().getUserId());

		return delete(factLike.getId());
	}

	@Override
	public FactLike getByComponentIdAndUserId(Long componentId, Long userId)
			throws MessageException {
		logger.info("Entered FactLike service getByComponentIdAndUserId method");

		if (componentId == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (userId == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Getting FactLike with sentiment id = " + componentId
				+ " and user id = " + userId);

		return repository.getByComponentIdAndUserId(componentId, userId);
	}

	@Override
	public FactLike unlike(LikeDataBean likeDataBean) throws MessageException,
			Exception {
		logger.info("Entered FactLike service unlike method");

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
		FactLike factLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		FactLikeCount factLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// this if will never run in ideal condition
		if (factLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			factLike = new FactLike();
			factLike.setId(likeId);
			factLike.setLikeFlag(false);
			factLike.setDislikeFlag(false);

			factLike = repository.save(factLike);

			// deduct user points after unlike on any component
			repository.addMonetizationPoints(likeDataBean, "Unlike");

			if (factLikeCount == null) {

				Fact fact = new Fact();
				fact.setId(likeDataBean.getComponentId());

				FactLikeCountId factLikeCountId = new FactLikeCountId();
				factLikeCountId.setFact(fact);

				factLikeCount = new FactLikeCount();
				factLikeCount.setId(factLikeCountId);

				factLikeCount = new FactLikeCount();
				factLikeCount.setId(factLikeCountId);
				factLikeCount.setLikeCount(0l);
				factLikeCount.setDislikeCount(0l);

				countService.create(factLikeCount);
			} else {

				factLikeCount.setLikeCount(factLikeCount
						.getLikeCount() - 1);
				countService.update(factLikeCount);
			}

		} else {

			// because this is the call for unlike so set both like and
			// dislike flag to false
			factLike.setLikeFlag(false);
			factLike.setDislikeFlag(false);

			// update like entry in database
			factLike = update(factLike);

			// deduct user points after unlike on any component
			repository.addMonetizationPoints(likeDataBean, "Unlike");

			if (factLikeCount != null) {

				// decrement the like count by 1
				factLikeCount.setLikeCount(factLikeCount
						.getLikeCount() - 1);

				// update the change in count entry for component in
				// database
				countService.update(factLikeCount);

			} else {
				// this will not run in ideal condition
				Fact fact = new Fact();
				fact.setId(likeDataBean.getComponentId());

				FactLikeCountId factLikeCountId = new FactLikeCountId();
				factLikeCountId.setFact(fact);

				factLikeCount = new FactLikeCount();
				factLikeCount.setId(factLikeCountId);

				factLikeCount = new FactLikeCount();
				factLikeCount.setId(factLikeCountId);
				factLikeCount.setLikeCount(0l);
				factLikeCount.setDislikeCount(0l);

				countService.create(factLikeCount);
			}
		}

		return factLike;
	}

	@Override
	public FactLike dislike(LikeDataBean likeDataBean) throws MessageException,
			Exception {
		logger.info("Entered FactLike service unlike method");

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
		FactLike factLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		FactLikeCount factLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// if no entry exist for like in database
		if (factLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			factLike = new FactLike();
			factLike.setId(likeId);

			// set dislike flag true and like flag false
			factLike.setLikeFlag(false);
			factLike.setDislikeFlag(true);

			// save the new entry for like event
			factLike = repository.save(factLike);

			// added user points after disLike on any component
			repository.addMonetizationPoints(likeDataBean, "Dislike");

			// if count object is also null that means this is the first
			// dislike on the component
			if (factLikeCount == null) {

				Fact fact = new Fact();
				fact.setId(likeDataBean.getComponentId());

				FactLikeCountId sentimentLikeCountId = new FactLikeCountId();
				sentimentLikeCountId.setFact(fact);

				factLikeCount = new FactLikeCount();
				factLikeCount.setId(sentimentLikeCountId);

				factLikeCount = new FactLikeCount();
				factLikeCount.setId(sentimentLikeCountId);

				// since this call is for dislike so set dislike count 1 and
				// like count 0
				factLikeCount.setLikeCount(0l);
				factLikeCount.setDislikeCount(1l);

				// save the new entry for count in database
				countService.create(factLikeCount);
			} else {

				// if this is not the first dislike on the component, then
				// increment the dislike count by 1 and
				// update in database
				factLikeCount.setDislikeCount(factLikeCount
						.getDislikeCount() + 1l);
				countService.update(factLikeCount);
			}

		} else {

			// if the like object already exists in database and the entry
			// was for like, decrement the like
			// count by 1
			if (factLike.isLikeFlag()) {
				factLikeCount.setLikeCount(factLikeCount
						.getLikeCount() - 1l);
			}

			// set like flag false
			factLike.setLikeFlag(false);
			// set dislike flag true
			factLike.setDislikeFlag(true);

			// update the like object in the database
			factLike = update(factLike);

			// added user points after disLike on any component
			repository.addMonetizationPoints(likeDataBean, "Dislike");

			if (factLikeCount != null) {

				// increment dislike count by 1
				factLikeCount.setDislikeCount(factLikeCount
						.getDislikeCount() + 1);

				// update the count object in the database
				countService.update(factLikeCount);

			} else {

				Fact fact = new Fact();
				fact.setId(likeDataBean.getComponentId());

				FactLikeCountId factLikeCountId = new FactLikeCountId();
				factLikeCountId.setFact(fact);

				factLikeCount = new FactLikeCount();
				factLikeCount.setId(factLikeCountId);

				factLikeCount = new FactLikeCount();
				factLikeCount.setId(factLikeCountId);
				// since its a first entry for dislike on component hence
				// set like count to 0 and dislike count to 1
				factLikeCount.setLikeCount(0l);
				factLikeCount.setDislikeCount(1l);

				// create new entry in database
				countService.create(factLikeCount);
			}
		}
		return factLike;
	}

	@Override
	public FactLike undislike(LikeDataBean likeDataBean)
			throws MessageException, Exception {
		logger.info("Entered FactLike service undislike method");

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
		FactLike factLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		FactLikeCount factLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// this if will never run in ideal condition
		if (factLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			factLike = new FactLike();
			factLike.setId(likeId);
			factLike.setLikeFlag(false);
			factLike.setDislikeFlag(false);

			factLike = repository.save(factLike);

			// deduct user points after unDislike on any component
			repository.addMonetizationPoints(likeDataBean, "Undislike");

			if (factLikeCount == null) {

				Fact fact = new Fact();
				fact.setId(likeDataBean.getComponentId());

				FactLikeCountId factLikeCountId = new FactLikeCountId();
				factLikeCountId.setFact(fact);

				factLikeCount = new FactLikeCount();
				factLikeCount.setId(factLikeCountId);

				factLikeCount = new FactLikeCount();
				factLikeCount.setId(factLikeCountId);
				factLikeCount.setLikeCount(0l);
				factLikeCount.setDislikeCount(0l);

				countService.create(factLikeCount);
			} else {

				factLikeCount.setDislikeCount(factLikeCount
						.getDislikeCount() - 1);
				countService.update(factLikeCount);
			}

		} else {

			// because this is the call for undislike so set both like and
			// dislike flag to false
			factLike.setLikeFlag(false);
			factLike.setDislikeFlag(false);

			// update like entry in database
			factLike = update(factLike);

			// deduct user points after unDislike on any component
			repository.addMonetizationPoints(likeDataBean, "Undislike");

			if (factLikeCount != null) {

				// decrement the like count by 1
				factLikeCount.setDislikeCount(factLikeCount
						.getDislikeCount() - 1);

				// update the change in count entry for component in
				// database
				countService.update(factLikeCount);

			} else {
				// this will not run in ideal condition

				Fact fact = new Fact();
				fact.setId(likeDataBean.getComponentId());

				FactLikeCountId factLikeCountId = new FactLikeCountId();
				factLikeCountId.setFact(fact);

				factLikeCount = new FactLikeCount();
				factLikeCount.setId(factLikeCountId);

				factLikeCount = new FactLikeCount();
				factLikeCount.setId(factLikeCountId);
				factLikeCount.setLikeCount(0l);
				factLikeCount.setDislikeCount(0l);

				countService.create(factLikeCount);
			}
		}

		return factLike;
	}

}
