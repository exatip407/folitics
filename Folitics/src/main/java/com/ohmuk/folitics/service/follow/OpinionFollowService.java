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
import com.ohmuk.folitics.hibernate.entity.Opinion;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.follow.OpinionFollow;
import com.ohmuk.folitics.hibernate.entity.follow.OpinionFollowCount;
import com.ohmuk.folitics.hibernate.entity.follow.OpinionFollowCountId;
import com.ohmuk.folitics.hibernate.repository.follow.IOpinionFollowCountRepository;
import com.ohmuk.folitics.hibernate.repository.follow.IOpinionFollowRepository;
import com.ohmuk.folitics.service.IUserService;
import com.ohmuk.folitics.util.DateUtils;

@Service
@Transactional
public class OpinionFollowService implements IFollowService<OpinionFollow> {

	private static final Logger logger = LoggerFactory
			.getLogger(OpinionFollowService.class);

	@Autowired
	IOpinionFollowRepository opinionFollowRepository;

	@Autowired
	IUserService userService;

	@Autowired
	IOpinionFollowCountRepository opinionFollowCountRepository;

	@Override
	public OpinionFollow create(FollowDataBean followDataBean)
			throws MessageException, Exception {

		OpinionFollow opinionFollow;

		logger.info("Inside create FollowService");

		opinionFollow = opinionFollowRepository.findByComponentIdAndUserId(
				followDataBean.getComponentId(), followDataBean.getUserId());

		if (opinionFollow != null) {

			logger.info("User is already available in table");
			opinionFollow.setFollowing(true);

		} else {

			logger.info("User is not available in table");

			// Adding new Entry in Follow Table
			opinionFollow = new OpinionFollow(followDataBean);
			opinionFollow.setFollowing(true);
			opinionFollow.setCreateTime(DateUtils.getSqlTimeStamp());

			logger.info("Adding new user");

		}

		// Adding counter for component

		OpinionFollowCount opinionFollowCount = new OpinionFollowCount();
		Opinion opinion = new Opinion();
		opinion.setId(followDataBean.getComponentId());

		OpinionFollowCountId followCountId = new OpinionFollowCountId();
		followCountId.setOpinion(opinion);

		opinionFollowCount.setId(followCountId);

		OpinionFollowCount opinionFollowCount2 = opinionFollowCountRepository
				.find(followCountId);

		if (opinionFollowCount2 != null) {
			opinionFollowCount2.setFollowCount(opinionFollowCount2
					.getFollowCount() + 1);
			opinionFollowCountRepository.save(opinionFollowCount2);

		}

		else {

			opinionFollowCount.setFollowCount(1l);
			opinionFollowCountRepository.save(opinionFollowCount);
		}

		logger.debug("User is now following " + followDataBean.getComponentId());
		logger.info("Exiting follow");

		OpinionFollow follow = opinionFollowRepository.save(opinionFollow);

		// add monetization points to user for Follow on any component
		opinionFollowRepository.addMonetizationPoints(followDataBean, "Follow");

		return follow;

	}

	@Override
	public void delete(FollowDataBean followDataBean) throws MessageException,
			Exception {
		OpinionFollow follower = opinionFollowRepository
				.findByComponentIdAndUserId(followDataBean.getComponentId(),
						followDataBean.getUserId());
		if (follower != null) {
			opinionFollowRepository.delete(follower);
		}

	}

	@Override
	public OpinionFollow update(FollowDataBean followDataBean)
			throws MessageException, Exception {

		OpinionFollow follower = opinionFollowRepository
				.findByComponentIdAndUserId(followDataBean.getComponentId(),
						followDataBean.getUserId());

		if (follower == null) {
			logger.error("No such Entry Found");
			throw (new MessageException("No such entry found"));
		}

		follower.setFollowing(false);

		OpinionFollowCount opinionFollowCount = new OpinionFollowCount();
		Opinion opinion = new Opinion();
		opinion.setId(followDataBean.getComponentId());
		OpinionFollowCountId followCountId = new OpinionFollowCountId();
		followCountId.setOpinion(opinion);
		opinionFollowCount.setId(followCountId);
		OpinionFollowCount opinionFollowCount2 = opinionFollowCountRepository
				.find(followCountId);

		if (opinionFollowCount2 != null) {

			if (opinionFollowCount2.getFollowCount() == 0) {

				opinionFollowCountRepository.save(opinionFollowCount2);
			} else {
				opinionFollowCount2.setFollowCount(opinionFollowCount2
						.getFollowCount() - 1);
				opinionFollowCountRepository.save(opinionFollowCount2);
			}

		}

		logger.debug("User is now following " + followDataBean.getComponentId());
		logger.info("Exiting follow");

		OpinionFollow opinionFollow = opinionFollowRepository.save(follower);

		// Deducted monetization points of user
		opinionFollowRepository.addMonetizationPoints(followDataBean, "Unfollow");

		return opinionFollow;

	}

	@Override
	public List<User> getFollowersForComponent(FollowDataBean followDataBean)
			throws MessageException, Exception {

		logger.info("Inside getFollowersForComponent");
		logger.debug("Fetching followers for componentId : "
				+ followDataBean.getComponentId());

		List<User> users;
		List<Long> followerSet = new ArrayList<Long>();

		List<OpinionFollow> followersList = opinionFollowRepository
				.findByComponentIdAndIsFollowing(
						followDataBean.getComponentId(), true);

		if (!followersList.isEmpty()) {
			for (OpinionFollow follow : followersList) {
				followerSet.add(follow.getFollowId().getUserId());
			}
		}
		users = userService.getAllUserWhereIdIn(followerSet);
		return users;
	}

	@Override
	public boolean getFollowing(FollowDataBean followDataBean)
			throws MessageException, Exception {

		logger.info("Inside class : SentimentFollowService getFollow");
		return opinionFollowRepository
				.findByComponentIdUserIdAndIsFollowing(followDataBean);
	}

}
