package com.ohmuk.folitics.hibernate.service.like;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.beans.LikeDataBean;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.like.LikeId;
import com.ohmuk.folitics.hibernate.entity.like.TaskLike;
import com.ohmuk.folitics.hibernate.entity.like.TaskLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.TaskLikeCountId;
import com.ohmuk.folitics.hibernate.entity.task.Task;
import com.ohmuk.folitics.hibernate.repository.like.ITaskLikeRepository;

@Service
@Transactional
public class TaskLikeService implements ILikeService<TaskLike> {

	private static Logger logger = LoggerFactory
			.getLogger(TaskLikeService.class);

	@Autowired
	private ILikeCountService<TaskLikeCount> countService;

	@Autowired
	private ITaskLikeRepository repository;

	@Override
	public TaskLike create(LikeDataBean likeDataBean) throws Exception {

		logger.info("Entered TaskLike service create method");

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
		TaskLike taskLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		TaskLikeCount taskLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// if no entry exist for like in database
		if (taskLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			taskLike = new TaskLike();
			taskLike.setId(likeId);

			// set dislike flag false and like flag true
			taskLike.setDislikeFlag(false);
			taskLike.setLikeFlag(true);

			// save the new entry for like event
			taskLike = repository.save(taskLike);

			// added monetization points to user
			repository.addMonetizationPoints(likeDataBean, "Like");

			// if count object is also null that means this is the first
			// like on the component
			if (taskLikeCount == null) {

				Task task = new Task();
				task.setId(likeDataBean.getComponentId());

				TaskLikeCountId taskLikeCountId = new TaskLikeCountId();
				taskLikeCountId.setTask(task);

				taskLikeCount = new TaskLikeCount();
				taskLikeCount.setId(taskLikeCountId);

				// since this call is for like so set like count 1 and
				// dislike count 0
				taskLikeCount.setLikeCount(1l);
				taskLikeCount.setDislikeCount(0l);

				// save the new entry for count in database
				countService.create(taskLikeCount);
			} else {
				// if this is not the first like on the component, then
				// increment the like count by 1 and update in
				// database
				taskLikeCount.setLikeCount(taskLikeCount.getLikeCount() + 1l);
				countService.update(taskLikeCount);
			}

		} else {

			// if the like object already exists in database and the entry
			// was for dislike, decrement the dislike
			// count by 1
			if (taskLike.isDislikeFlag()) {

				taskLikeCount
						.setDislikeCount(taskLikeCount.getDislikeCount() - 1l);
			}

			// increment like count by 1
			taskLikeCount.setLikeCount(taskLikeCount.getLikeCount() + 1l);
			// set dislike flag false
			taskLike.setDislikeFlag(false);
			// set like flag true
			taskLike.setLikeFlag(true);

			// update like entry in database
			taskLike = update(taskLike);
			// update count entry in database
			countService.update(taskLikeCount);

			// added monetization points to user
			repository.addMonetizationPoints(likeDataBean, "like");

		}
		return taskLike;

	}

	@Override
	public TaskLike read(LikeId likeId) throws MessageException {

		logger.info("Entered TaskLike service read method");

		if (likeId.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (likeId.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Getting TaskLike with component id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());
		return repository.find(likeId);
	}

	@Override
	public List<TaskLike> readAll() {

		logger.info("Entered TaskLike service readAll method");
		logger.debug("Getting all TaskLike");
		return repository.findAll();
	}

	@Override
	public TaskLike update(TaskLike taskLike) throws MessageException {

		logger.info("Entered TaskLike service update method");

		if (taskLike.getId().getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (taskLike.getId().getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Updating TaskLike with task id = "
				+ taskLike.getId().getComponentId() + " and user id = "
				+ taskLike.getId().getUserId());

		TaskLike originalTaskLike = repository.find(taskLike.getId());

		if (originalTaskLike == null) {
			return null;
		}

		return repository.update(taskLike);
	}

	@Override
	public TaskLike delete(LikeId likeId) throws MessageException {

		logger.info("Entered TaskLike service delete method");

		if (likeId.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (likeId.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Deleting TaskLike with task id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());

		// get the entry for count from database
		TaskLikeCount taskLikeCount = (TaskLikeCount) countService
				.getByComponentId(likeId.getComponentId());

		// delete the entry for count
		countService.delete(taskLikeCount);

		repository.delete(likeId);
		return repository.find(likeId);
	}

	@Override
	public TaskLike delete(TaskLike taskLike) throws MessageException {
		logger.info("Entered TaskLike service delete method");

		if (taskLike.getId().getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (taskLike.getId().getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Deleting TaskLike with sentiment id = "
				+ taskLike.getId().getComponentId() + " and user id = "
				+ taskLike.getId().getUserId());

		return delete(taskLike.getId());
	}

	@Override
	public TaskLike getByComponentIdAndUserId(Long componentId, Long userId)
			throws MessageException {
		logger.info("Entered TaskLike service getByComponentIdAndUserId method");

		if (componentId == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (userId == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		logger.debug("Getting taskLike with task id = " + componentId
				+ " and user id = " + userId);

		return repository.getByComponentIdAndUserId(componentId, userId);
	}

	@Override
	public TaskLike unlike(LikeDataBean likeDataBean) throws MessageException,
			Exception {

		logger.info("Entered TaskLike service unlike method");

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
		TaskLike taskLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		TaskLikeCount taskLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// this if will never run in ideal condition
		if (taskLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			taskLike = new TaskLike();
			taskLike.setId(likeId);
			taskLike.setLikeFlag(false);
			taskLike.setDislikeFlag(false);

			taskLike = repository.save(taskLike);

			// deduct user points after unlike on any component
			repository.addMonetizationPoints(likeDataBean, "Unlike");

			if (taskLikeCount == null) {

				Task task = new Task();
				task.setId(likeDataBean.getComponentId());

				TaskLikeCountId taskLikeCountId = new TaskLikeCountId();
				taskLikeCountId.setTask(task);

				taskLikeCount = new TaskLikeCount();
				taskLikeCount.setId(taskLikeCountId);

				taskLikeCount = new TaskLikeCount();
				taskLikeCount.setId(taskLikeCountId);
				taskLikeCount.setLikeCount(0l);
				taskLikeCount.setDislikeCount(0l);

				countService.create(taskLikeCount);
			} else {

				taskLikeCount.setLikeCount(taskLikeCount.getLikeCount() - 1);
				countService.update(taskLikeCount);
			}

		} else {

			// because this is the call for unlike so set both like and
			// dislike flag to false
			taskLike.setLikeFlag(false);
			taskLike.setDislikeFlag(false);

			// update like entry in database
			taskLike = update(taskLike);

			// deduct user points after unlike on any component
			repository.addMonetizationPoints(likeDataBean, "Unlike");

			if (taskLikeCount != null) {

				// decrement the like count by 1
				taskLikeCount.setLikeCount(taskLikeCount.getLikeCount() - 1);

				// update the change in count entry for component in
				// database
				countService.update(taskLikeCount);

			} else {
				// this will not run in ideal condition
				Task task = new Task();
				task.setId(likeDataBean.getComponentId());

				TaskLikeCountId sentimentLikeCountId = new TaskLikeCountId();
				sentimentLikeCountId.setTask(task);

				taskLikeCount = new TaskLikeCount();
				taskLikeCount.setId(sentimentLikeCountId);

				taskLikeCount = new TaskLikeCount();
				taskLikeCount.setId(sentimentLikeCountId);
				taskLikeCount.setLikeCount(0l);
				taskLikeCount.setDislikeCount(0l);

				countService.create(taskLikeCount);
			}
		}
		return taskLike;
	}

	@Override
	public TaskLike dislike(LikeDataBean likeDataBean) throws MessageException,
			Exception {

		logger.info("Entered TaskLike service unlike method");

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
		TaskLike taskLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		TaskLikeCount taskLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// if no entry exist for like in database
		if (taskLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			taskLike = new TaskLike();
			taskLike.setId(likeId);

			// set dislike flag true and like flag false
			taskLike.setLikeFlag(false);
			taskLike.setDislikeFlag(true);

			// save the new entry for like event
			taskLike = repository.save(taskLike);

			// added user points after disLike on any component
			repository.addMonetizationPoints(likeDataBean, "Dislike");

			// if count object is also null that means this is the first
			// dislike on the component
			if (taskLikeCount == null) {

				Task sentiment = new Task();
				sentiment.setId(likeDataBean.getComponentId());

				TaskLikeCountId sentimentLikeCountId = new TaskLikeCountId();
				sentimentLikeCountId.setTask(sentiment);

				taskLikeCount = new TaskLikeCount();
				taskLikeCount.setId(sentimentLikeCountId);

				taskLikeCount = new TaskLikeCount();
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
			if (taskLike.isLikeFlag()) {
				taskLikeCount.setLikeCount(taskLikeCount.getLikeCount() - 1l);
			}

			// set like flag false
			taskLike.setLikeFlag(false);
			// set dislike flag true
			taskLike.setDislikeFlag(true);

			// update the like object in the database
			taskLike = update(taskLike);

			// added user points after disLike on any component
			repository.addMonetizationPoints(likeDataBean, "Dislike");

			if (taskLikeCount != null) {

				// increment dislike count by 1
				taskLikeCount
						.setDislikeCount(taskLikeCount.getDislikeCount() + 1);

				// update the count object in the database
				countService.update(taskLikeCount);

			} else {

				Task task = new Task();
				task.setId(likeDataBean.getComponentId());

				TaskLikeCountId taskLikeCountId = new TaskLikeCountId();
				taskLikeCountId.setTask(task);

				taskLikeCount = new TaskLikeCount();
				taskLikeCount.setId(taskLikeCountId);

				taskLikeCount = new TaskLikeCount();
				taskLikeCount.setId(taskLikeCountId);
				// since its a first entry for dislike on component hence
				// set like count to 0 and dislike count to 1
				taskLikeCount.setLikeCount(0l);
				taskLikeCount.setDislikeCount(1l);

				// create new entry in database
				countService.create(taskLikeCount);
			}
		}
		return taskLike;

	}

	@Override
	public TaskLike undislike(LikeDataBean likeDataBean)
			throws MessageException, Exception {

		logger.info("Entered TaskLike service undislike method");

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
		TaskLike taskLike = getByComponentIdAndUserId(
				likeDataBean.getComponentId(), likeDataBean.getUserId());

		// get the entry for count from database for the component id
		TaskLikeCount taskLikeCount = countService
				.getByComponentId(likeDataBean.getComponentId());

		// this if will never run in ideal condition
		if (taskLike == null) {

			LikeId likeId = new LikeId();
			likeId.setComponentId(likeDataBean.getComponentId());
			likeId.setUserId(likeDataBean.getUserId());

			taskLike = new TaskLike();
			taskLike.setId(likeId);
			taskLike.setLikeFlag(false);
			taskLike.setDislikeFlag(false);

			taskLike = repository.save(taskLike);

			// deduct user points after unDislike on any component
			repository.addMonetizationPoints(likeDataBean, "Undislike");

			if (taskLikeCount == null) {

				Task sentiment = new Task();
				sentiment.setId(likeDataBean.getComponentId());

				TaskLikeCountId sentimentLikeCountId = new TaskLikeCountId();
				sentimentLikeCountId.setTask(sentiment);

				taskLikeCount = new TaskLikeCount();
				taskLikeCount.setId(sentimentLikeCountId);

				taskLikeCount = new TaskLikeCount();
				taskLikeCount.setId(sentimentLikeCountId);
				taskLikeCount.setLikeCount(0l);
				taskLikeCount.setDislikeCount(0l);

				countService.create(taskLikeCount);
			} else {

				taskLikeCount
						.setDislikeCount(taskLikeCount.getDislikeCount() - 1);
				countService.update(taskLikeCount);
			}

		} else {

			// because this is the call for undislike so set both like and
			// dislike flag to false
			taskLike.setLikeFlag(false);
			taskLike.setDislikeFlag(false);

			// update like entry in database
			taskLike = update(taskLike);

			// deduct user points after unDislike on any component
			repository.addMonetizationPoints(likeDataBean, "Undislike");

			if (taskLikeCount != null) {

				// decrement the like count by 1
				taskLikeCount
						.setDislikeCount(taskLikeCount.getDislikeCount() - 1);

				// update the change in count entry for component in
				// database
				countService.update(taskLikeCount);

			} else {
				// this will not run in ideal condition

				Task task = new Task();
				task.setId(likeDataBean.getComponentId());

				TaskLikeCountId taskLikeCountId = new TaskLikeCountId();
				taskLikeCountId.setTask(task);

				taskLikeCount = new TaskLikeCount();
				taskLikeCount.setId(taskLikeCountId);

				taskLikeCount = new TaskLikeCount();
				taskLikeCount.setId(taskLikeCountId);
				taskLikeCount.setLikeCount(0l);
				taskLikeCount.setDislikeCount(0l);

				countService.create(taskLikeCount);
			}
		}

		return taskLike;
	}
}
