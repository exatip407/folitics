package com.ohmuk.folitics.hibernate.service.like;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.beans.LikeDataBean;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Chart;
import com.ohmuk.folitics.hibernate.entity.like.ChartLike;
import com.ohmuk.folitics.hibernate.entity.like.ChartLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.ChartLikeCountId;
import com.ohmuk.folitics.hibernate.entity.like.LikeId;
import com.ohmuk.folitics.hibernate.repository.like.IChartLikeRepository;

/**
 * @author Abhishek
 *
 */

@Service
@Transactional
public class ChartLikeService implements ILikeService<ChartLike> {

	private static Logger logger = LoggerFactory
			.getLogger(ChartLikeService.class);

	@Autowired
	private IChartLikeRepository repository;

	@Autowired
	private ILikeCountService<ChartLikeCount> countService;

	@Override
	public ChartLike create(LikeDataBean likeDataBean) throws MessageException,
			Exception {
		logger.info("Entered ChartLike service create method");

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
		ChartLike chartLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		ChartLikeCount chartLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// if no entry exist for like in database
		if (chartLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			chartLike = new ChartLike();
			chartLike.setId(likeId);

			// set dislike flag false and like flag true
			chartLike.setDislikeFlag(false);
			chartLike.setLikeFlag(true);

			// save the new entry for like event
			chartLike = repository.save(chartLike);

			// added monetization points to user
			repository.addMonetizationPoints(likeDataBean, "like");

			// if count object is also null that means this is the first
			// like on the component
			if (chartLikeCount == null) {

				Chart chart = new Chart();
				chart.setId(likeDataBean.getComponentId());

				ChartLikeCountId chartLikeCountId = new ChartLikeCountId();
				chartLikeCountId.setChart(chart);

				chartLikeCount = new ChartLikeCount();
				chartLikeCount.setId(chartLikeCountId);

				// since this call is for like so set like count 1 and
				// dislike count 0
				chartLikeCount.setLikeCount(1l);
				chartLikeCount.setDislikeCount(0l);

				// save the new entry for count in database
				countService.create(chartLikeCount);
			} else {
				// if this is not the first like on the component, then
				// increment the like count by 1 and update in
				// database
				chartLikeCount.setLikeCount(chartLikeCount.getLikeCount() + 1l);
				countService.update(chartLikeCount);
			}

		} else {

			// if the like object already exists in database and the entry
			// was for dislike, decrement the dislike
			// count by 1
			if (chartLike.isDislikeFlag()) {

				chartLikeCount
						.setDislikeCount(chartLikeCount.getDislikeCount() - 1l);
			}

			// increment like count by 1
			chartLikeCount.setLikeCount(chartLikeCount.getLikeCount() + 1l);
			// set dislike flag false
			chartLike.setDislikeFlag(false);
			// set like flag true
			chartLike.setLikeFlag(true);

			// update like entry in database
			chartLike = update(chartLike);
			// update count entry in database
			countService.update(chartLikeCount);

			// added monetization points to user
			repository.addMonetizationPoints(likeDataBean, "like");

		}

		return chartLike;
	}

	@Override
	public ChartLike read(LikeId likeId) throws MessageException {
		logger.info("Entered ChartLike service read method");

		if (likeId.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (likeId.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Getting ChartLike with component id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());
		return repository.find(likeId);
	}

	@Override
	public List<ChartLike> readAll() {
		logger.info("Entered ChartLike service readAll method");
		logger.debug("Getting all ChartLike");
		return repository.findAll();
	}

	@Override
	public ChartLike update(ChartLike chartLike) throws MessageException {
		logger.info("Entered ChartLike service update method");

		if (chartLike.getId().getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (chartLike.getId().getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Updating ChartLike with chart id = "
				+ chartLike.getId().getComponentId() + " and user id = "
				+ chartLike.getId().getUserId());

		ChartLike originalChartLike = repository.find(chartLike.getId());

		if (originalChartLike == null) {
			return null;
		}

		return repository.update(chartLike);
	}

	@Override
	public ChartLike delete(LikeId likeId) throws MessageException {
		logger.info("Entered ChartLike service delete method");

		if (likeId.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (likeId.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Deleting ChartLike with chart id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());

		// get the entry for count from database
		ChartLikeCount sentimentLikeCount = (ChartLikeCount) countService
				.getByComponentId(likeId.getComponentId());

		// delete the entry for count
		countService.delete(sentimentLikeCount);

		repository.delete(likeId);
		return repository.find(likeId);
	}

	@Override
	public ChartLike delete(ChartLike chartLike) throws MessageException {
		logger.info("Entered ChartLike service delete method");

		if (chartLike.getId().getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (chartLike.getId().getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Deleting ChartLike with chart id = "
				+ chartLike.getId().getComponentId() + " and user id = "
				+ chartLike.getId().getUserId());

		return delete(chartLike.getId());
	}

	@Override
	public ChartLike getByComponentIdAndUserId(Long componentId, Long userId)
			throws MessageException {
		logger.info("Entered ChartLike service getByComponentIdAndUserId method");

		if (componentId == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (userId == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Getting ChartLike with chart id = " + componentId
				+ " and user id = " + userId);

		return repository.getByComponentIdAndUserId(componentId, userId);
	}

	@Override
	public ChartLike unlike(LikeDataBean likeDataBean) throws MessageException,
			Exception {
		logger.info("Entered ChartLike service unlike method");

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
		ChartLike chartLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		ChartLikeCount chartLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// this if will never run in ideal condition
		if (chartLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			chartLike = new ChartLike();
			chartLike.setId(likeId);
			chartLike.setLikeFlag(false);
			chartLike.setDislikeFlag(false);

			chartLike = repository.save(chartLike);

			// deduct user points after unlike on any component
			repository.addMonetizationPoints(likeDataBean, "Unlike");

			if (chartLikeCount == null) {

				Chart chart = new Chart();
				chart.setId(likeDataBean.getComponentId());

				ChartLikeCountId chartLikeCountId = new ChartLikeCountId();
				chartLikeCountId.setChart(chart);

				chartLikeCount = new ChartLikeCount();
				chartLikeCount.setId(chartLikeCountId);

				chartLikeCount = new ChartLikeCount();
				chartLikeCount.setId(chartLikeCountId);
				chartLikeCount.setLikeCount(0l);
				chartLikeCount.setDislikeCount(0l);

				countService.create(chartLikeCount);
			} else {

				chartLikeCount.setLikeCount(chartLikeCount.getLikeCount() - 1);
				countService.update(chartLikeCount);
			}

		} else {

			// because this is the call for unlike so set both like and
			// dislike flag to false
			chartLike.setLikeFlag(false);
			chartLike.setDislikeFlag(false);

			// update like entry in database
			chartLike = update(chartLike);

			// deduct user points after unlike on any component
			repository.addMonetizationPoints(likeDataBean, "Unlike");

			if (chartLikeCount != null) {

				// decrement the like count by 1
				chartLikeCount.setLikeCount(chartLikeCount.getLikeCount() - 1);

				// update the change in count entry for component in
				// database
				countService.update(chartLikeCount);

			} else {
				// this will not run in ideal condition
				Chart chart = new Chart();
				chart.setId(likeDataBean.getComponentId());

				ChartLikeCountId chartLikeCountId = new ChartLikeCountId();
				chartLikeCountId.setChart(chart);

				chartLikeCount = new ChartLikeCount();
				chartLikeCount.setId(chartLikeCountId);

				chartLikeCount = new ChartLikeCount();
				chartLikeCount.setId(chartLikeCountId);
				chartLikeCount.setLikeCount(0l);
				chartLikeCount.setDislikeCount(0l);

				countService.create(chartLikeCount);
			}
		}

		return chartLike;
	}

	@Override
	public ChartLike dislike(LikeDataBean likeDataBean)
			throws MessageException, Exception {
		logger.info("Entered ChartLike service unlike method");

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
		ChartLike chartLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		ChartLikeCount chartLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// if no entry exist for like in database
		if (chartLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			chartLike = new ChartLike();
			chartLike.setId(likeId);

			// set dislike flag true and like flag false
			chartLike.setLikeFlag(false);
			chartLike.setDislikeFlag(true);

			// save the new entry for like event
			chartLike = repository.save(chartLike);

			// added user points after disLike on any component
			repository.addMonetizationPoints(likeDataBean, "Dislike");

			// if count object is also null that means this is the first
			// dislike on the component
			if (chartLikeCount == null) {

				Chart chart = new Chart();
				chart.setId(likeDataBean.getComponentId());

				ChartLikeCountId chartLikeCountId = new ChartLikeCountId();
				chartLikeCountId.setChart(chart);

				chartLikeCount = new ChartLikeCount();
				chartLikeCount.setId(chartLikeCountId);

				chartLikeCount = new ChartLikeCount();
				chartLikeCount.setId(chartLikeCountId);

				// since this call is for dislike so set dislike count 1 and
				// like count 0
				chartLikeCount.setLikeCount(0l);
				chartLikeCount.setDislikeCount(1l);

				// save the new entry for count in database
				countService.create(chartLikeCount);
			} else {

				// if this is not the first dislike on the component, then
				// increment the dislike count by 1 and
				// update in database
				chartLikeCount
						.setDislikeCount(chartLikeCount.getDislikeCount() + 1l);
				countService.update(chartLikeCount);
			}

		} else {

			// if the like object already exists in database and the entry
			// was for like, decrement the like
			// count by 1
			if (chartLike.isLikeFlag()) {
				chartLikeCount.setLikeCount(chartLikeCount.getLikeCount() - 1l);
			}

			// set like flag false
			chartLike.setLikeFlag(false);
			// set dislike flag true
			chartLike.setDislikeFlag(true);

			// update the like object in the database
			chartLike = update(chartLike);

			// added user points after disLike on any component
			repository.addMonetizationPoints(likeDataBean, "Dislike");

			if (chartLikeCount != null) {

				// increment dislike count by 1
				chartLikeCount
						.setDislikeCount(chartLikeCount.getDislikeCount() + 1);

				// update the count object in the database
				countService.update(chartLikeCount);

			} else {

				Chart chart = new Chart();
				chart.setId(likeDataBean.getComponentId());

				ChartLikeCountId chartLikeCountId = new ChartLikeCountId();
				chartLikeCountId.setChart(chart);

				chartLikeCount = new ChartLikeCount();
				chartLikeCount.setId(chartLikeCountId);

				chartLikeCount = new ChartLikeCount();
				chartLikeCount.setId(chartLikeCountId);
				// since its a first entry for dislike on component hence
				// set like count to 0 and dislike count to 1
				chartLikeCount.setLikeCount(0l);
				chartLikeCount.setDislikeCount(1l);

				// create new entry in database
				countService.create(chartLikeCount);
			}
		}
		return chartLike;

	}

	@Override
	public ChartLike undislike(LikeDataBean likeDataBean)
			throws MessageException, Exception {
		logger.info("Entered ChartLike service undislike method");

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
		ChartLike chartLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		ChartLikeCount chartLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// this if will never run in ideal condition
		if (chartLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			chartLike = new ChartLike();
			chartLike.setId(likeId);
			chartLike.setLikeFlag(false);
			chartLike.setDislikeFlag(false);

			chartLike = repository.save(chartLike);

			// deduct user points after unDislike on any component
			repository.addMonetizationPoints(likeDataBean, "Undislike");

			if (chartLikeCount == null) {

				Chart chart = new Chart();
				chart.setId(likeDataBean.getComponentId());

				ChartLikeCountId chartLikeCountId = new ChartLikeCountId();
				chartLikeCountId.setChart(chart);

				chartLikeCount = new ChartLikeCount();
				chartLikeCount.setId(chartLikeCountId);

				chartLikeCount = new ChartLikeCount();
				chartLikeCount.setId(chartLikeCountId);
				chartLikeCount.setLikeCount(0l);
				chartLikeCount.setDislikeCount(0l);

				countService.create(chartLikeCount);
			} else {

				chartLikeCount
						.setDislikeCount(chartLikeCount.getDislikeCount() - 1);
				countService.update(chartLikeCount);
			}

		} else {

			// because this is the call for undislike so set both like and
			// dislike flag to false
			chartLike.setLikeFlag(false);
			chartLike.setDislikeFlag(false);

			// update like entry in database
			chartLike = update(chartLike);

			// deduct user points after unDislike on any component
			repository.addMonetizationPoints(likeDataBean, "Undislike");

			if (chartLikeCount != null) {

				// decrement the like count by 1
				chartLikeCount
						.setDislikeCount(chartLikeCount.getDislikeCount() - 1);

				// update the change in count entry for component in
				// database
				countService.update(chartLikeCount);

			} else {
				// this will not run in ideal condition

				Chart chart = new Chart();
				chart.setId(likeDataBean.getComponentId());

				ChartLikeCountId chartLikeCountId = new ChartLikeCountId();
				chartLikeCountId.setChart(chart);

				chartLikeCount = new ChartLikeCount();
				chartLikeCount.setId(chartLikeCountId);

				chartLikeCount = new ChartLikeCount();
				chartLikeCount.setId(chartLikeCountId);
				chartLikeCount.setLikeCount(0l);
				chartLikeCount.setDislikeCount(0l);

				countService.create(chartLikeCount);
			}
		}

		return chartLike;
	}

}
