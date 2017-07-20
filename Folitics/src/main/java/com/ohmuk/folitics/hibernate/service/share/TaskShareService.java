package com.ohmuk.folitics.hibernate.service.share;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.beans.AirShareDataBean;
import com.ohmuk.folitics.enums.SharePlatforms;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.share.TaskShare;
import com.ohmuk.folitics.hibernate.entity.share.TaskShareCount;
import com.ohmuk.folitics.hibernate.entity.share.TaskShareCountId;
import com.ohmuk.folitics.hibernate.entity.task.Task;
import com.ohmuk.folitics.hibernate.repository.share.IShareHibernateRepository;
import com.ohmuk.folitics.service.IUserService;
import com.ohmuk.folitics.util.DateUtils;

@Service
@Transactional
public class TaskShareService implements IShareService<TaskShare> {

	private static Logger logger = LoggerFactory.getLogger(TaskShareService.class);

	@Autowired
	IShareHibernateRepository<TaskShare> repository;

	@Autowired
	IUserService userRepository;

	@Autowired
	private IShareCountService<TaskShareCount> shareCountService;

	@Override
	public TaskShare create(AirShareDataBean airShareDataBean) throws MessageException, Exception {

		logger.info("Inside create Share Task");

		if (airShareDataBean.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (airShareDataBean.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}
		TaskShare taskShare = new TaskShare();

		taskShare.setUserId(airShareDataBean.getUserId());
		taskShare.setComponentId(airShareDataBean.getUserId());
		taskShare.setPlatform(airShareDataBean.getPlatform());

		taskShare = repository.save(taskShare);

		// added monetization points to user
		repository.addMonetizationPoints(airShareDataBean, "Share");

		// get the entry for count from database for the component id
		TaskShareCount taskShareCount = shareCountService.getByComponentId(airShareDataBean.getComponentId());

		// if the share count object is null that means this is the first
		// share
		if (taskShareCount == null) {

			Task task = new Task();
			task.setId(airShareDataBean.getComponentId());

			taskShareCount = new TaskShareCount();
			TaskShareCountId sentimentShareCountId = new TaskShareCountId();
			sentimentShareCountId.setTask(task);
			taskShareCount.setId(sentimentShareCountId);

			// if the component is shared on facebook then set the
			// facebookShareCount to 1 and set
			// twitterShareCount to 0
			if (airShareDataBean.getPlatform().equalsIgnoreCase(SharePlatforms.FACEBOOK.getValue())) {

				taskShareCount.setFacebookShareCount(1l);
				taskShareCount.setTwitterShareCount(0l);

			}
			// if the component is shared on twitter then set the
			// twitterShareCount to 1 and set
			// facebookShareCount to 0
			else if (airShareDataBean.getPlatform().equalsIgnoreCase(SharePlatforms.TWITTER.getValue())) {

				taskShareCount.setFacebookShareCount(0l);
				taskShareCount.setTwitterShareCount(1l);
			}

			// save the newly created count object in database
			shareCountService.create(taskShareCount);

		}
		// if the entry already exist for this component in database
		else {

			// if component is shared on facebook then increment the
			// facebookShareCount by 1
			if (airShareDataBean.getPlatform().equalsIgnoreCase(SharePlatforms.FACEBOOK.getValue())) {

				taskShareCount.setFacebookShareCount(taskShareCount.getFacebookShareCount() + 1l);

			}
			// if component is shared on twitter then increment the
			// twitterShareCount by 1
			else if (airShareDataBean.getPlatform().equalsIgnoreCase(SharePlatforms.TWITTER.getValue())) {

				taskShareCount.setTwitterShareCount(taskShareCount.getTwitterShareCount() + 1l);
			}

			// update the count in the database
			shareCountService.update(taskShareCount);

		}
		logger.info("Exiting create share");
		return taskShare;

	}

	@Override
	public TaskShare read(Long id) throws MessageException {

		if (id == null) {
			logger.error("share ID found to be null");
			throw (new MessageException("Share ID can't be null"));
		}

		TaskShare taskShare = repository.find(id);

		if (taskShare == null) {
			logger.error("Something went wrong, record not found for given id: " + id + ", Can't delete record.");
			throw (new MessageException(
					"Something went wrong, record not found for given id: " + id + ", Can't delete record."));
		}
		return taskShare;
	}

	@Override
	public List<TaskShare> readAll() {
		return repository.findAll();
	}

	@Override
	public TaskShare update(AirShareDataBean airShareDataBean) throws MessageException, Exception {

		logger.info("Inside Update Share");

		if (airShareDataBean.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (airShareDataBean.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		TaskShare originalData = repository.find(airShareDataBean.getId());

		if (originalData == null) {
			logger.error("No Record found in database for update");
			throw (new MessageException("No Record found in database for update"));
		}
		originalData.setEditTime(DateUtils.getSqlTimeStamp());
		// originalData.setStatus(ComponentState.UNSHARED.getValue());
		TaskShare taskShare = repository.save(originalData);

		if (taskShare == null) {
			logger.error("Something went wrong, record not updated");
			throw (new MessageException("Something went wrong, record not updated"));
		}

		logger.debug("Updated IndicatorWeightedData  : " + taskShare);
		logger.debug("Exiting Update IndicatorWeightedData");

		return taskShare;
	}

	@Override
	public List<User> findUserListForComponent(AirShareDataBean airShareDataBean) throws MessageException, Exception {

		logger.info("Inside findUserListForComponent");

		Set<TaskShare> taskShares = repository.getSharesByComponentId(airShareDataBean.getComponentId());

		logger.debug("Share fatched: " + taskShares.size());
		List<Long> userIds = new ArrayList<Long>();
		for (TaskShare share : taskShares) {
			userIds.add(share.getUserId());

		}
		List<User> users = userRepository.getAllUserWhereIdIn(userIds);

		logger.debug("Exiting Read share");

		return users;
	}

	@Override
	public boolean isComponentSharedByUser(AirShareDataBean airShareDataBean) throws MessageException, Exception {

		logger.info("Inside isShared");

		Set<TaskShare> taskShares = null;
		taskShares = repository.getSharesByUserIdAndComponentId(airShareDataBean.getUserId(),
				airShareDataBean.getComponentId());

		if (taskShares == null || taskShares.size() < 1) {
			logger.debug("Component Not shared by user");
			return false;
		}

		logger.debug("Share fatched: " + taskShares.size());
		logger.debug("Exiting isComponentSharedByUser share");

		return true;
	}

	@Override
	public TaskShare delete(Long id) throws MessageException, Exception {

		logger.info("Inside delete share by ID");

		if (id == null) {
			logger.error("share ID found to be null");
			throw (new MessageException("Share ID can't be null"));
		}

		repository.delete(id);

		TaskShare share = repository.find(id);

		logger.debug("Deleted share :" + share);
		logger.debug("Exiting delete share by ID");

		return share;
	}

}
