package com.ohmuk.folitics.service.follow;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.beans.FollowDataBean;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.follow.TaskFollow;
import com.ohmuk.folitics.hibernate.entity.follow.TaskFollowCount;
import com.ohmuk.folitics.hibernate.entity.follow.TaskFollowCountId;
import com.ohmuk.folitics.hibernate.entity.task.Task;
import com.ohmuk.folitics.hibernate.repository.follow.ITaskFollowCountRepository;
import com.ohmuk.folitics.hibernate.repository.follow.ITaskFollowRepository;
import com.ohmuk.folitics.service.IUserService;
import com.ohmuk.folitics.util.DateUtils;

@Service
@Transactional
public class TaskFollowService implements IFollowService<TaskFollow> {

	private static final Logger logger = LoggerFactory
			.getLogger(TaskFollowService.class);

	@Autowired
	ITaskFollowRepository taskFollowRepository;

	@Autowired
	IUserService userService;

	@Autowired
	ITaskFollowCountRepository taskFollowCountRepository;

	@Override
	public TaskFollow create(FollowDataBean followDataBean)
			throws MessageException, Exception {
		TaskFollow taskFollower;

		logger.info("Inside create FollowService");

		taskFollower = taskFollowRepository.findByComponentIdAndUserId(
				followDataBean.getComponentId(), followDataBean.getUserId());

		if (taskFollower != null) {

			logger.info("User is already available in table");
			taskFollower.setFollowing(true);

		} else {

			logger.info("User is not available in table");

			// Adding new Entry in Follow Table
			taskFollower = new TaskFollow(followDataBean);
			taskFollower.setFollowing(true);
			taskFollower.setCreateTime(DateUtils.getSqlTimeStamp());

			logger.info("Adding new user");

		}

		// Adding counter for component

		TaskFollowCount taskFollowCount = new TaskFollowCount();
		Task task = new Task();
		task.setId(followDataBean.getComponentId());

		TaskFollowCountId followCountId = new TaskFollowCountId();
		followCountId.setTask(task);

		taskFollowCount.setId(followCountId);

		TaskFollowCount taskFollowCount2 = taskFollowCountRepository
				.find(followCountId);

		if (taskFollowCount2 != null) {
			taskFollowCount2
					.setFollowCount(taskFollowCount2.getFollowCount() + 1);
			taskFollowCountRepository.save(taskFollowCount2);

		}

		else {

			taskFollowCount.setFollowCount(1l);
			taskFollowCountRepository.save(taskFollowCount);
		}

		logger.debug("User is now following " + followDataBean.getComponentId());
		logger.info("Exiting follow");

		TaskFollow taskFollow = taskFollowRepository.save(taskFollower);

		// add monetization points to user for Follow on any component
		taskFollowRepository.addMonetizationPoints(followDataBean, "Follow");

		return taskFollow;

	}

	@Override
	public void delete(FollowDataBean followDataBean) throws MessageException,
			Exception {

		TaskFollow taskFollow = taskFollowRepository
				.findByComponentIdAndUserId(followDataBean.getComponentId(),
						followDataBean.getUserId());
		if (taskFollow != null) {
			taskFollowRepository.delete(taskFollow);
		}

	}

	@Override
	public TaskFollow update(FollowDataBean followDataBean)
			throws MessageException, Exception {

		TaskFollow taskFollow = taskFollowRepository
				.findByComponentIdAndUserId(followDataBean.getComponentId(),
						followDataBean.getUserId());

		if (taskFollow == null) {
			logger.error("No such Entry Found");
			throw (new MessageException("No such entry found"));
		}

		taskFollow.setFollowing(false);

		TaskFollowCount taskFollowCount = new TaskFollowCount();
		Task task = new Task();

		task.setId(followDataBean.getComponentId());
		TaskFollowCountId followCountId = new TaskFollowCountId();

		followCountId.setTask(task);
		taskFollowCount.setId(followCountId);

		TaskFollowCount tasktFollowCount2 = taskFollowCountRepository
				.find(followCountId);

		if (tasktFollowCount2 != null) {
			tasktFollowCount2
					.setFollowCount(tasktFollowCount2.getFollowCount() - 1);
			taskFollowCountRepository.save(tasktFollowCount2);

		}

		logger.debug("User is now following " + followDataBean.getComponentId());
		logger.info("Exiting follow");

		TaskFollow tFollow = taskFollowRepository.save(taskFollow);

		// Deducted monetization points of user
		taskFollowRepository.addMonetizationPoints(followDataBean, "Unfollow");

		return tFollow;

	}

	@Override
	public List<User> getFollowersForComponent(FollowDataBean followDataBean)
			throws MessageException, Exception {

		logger.info("Inside getFollowersForComponent");
		logger.debug("Fetching followers for componentId : "
				+ followDataBean.getComponentId());

		List<User> users;
		List<Long> followerSet = new ArrayList<Long>();

		List<TaskFollow> followersList = taskFollowRepository
				.findByComponentIdAndIsFollowing(
						followDataBean.getComponentId(), true);

		if (!followersList.isEmpty()) {
			for (TaskFollow follow : followersList) {
				followerSet.add(follow.getFollowId().getUserId());
			}
		}
		users = userService.getAllUserWhereIdIn(followerSet);
		return users;
	}

	@Override
	public boolean getFollowing(FollowDataBean followDataBean)
			throws MessageException, Exception {

		logger.info("Inside class : TaskFollowService getFollow");
		return taskFollowRepository
				.findByComponentIdUserIdAndIsFollowing(followDataBean);
	}

}
