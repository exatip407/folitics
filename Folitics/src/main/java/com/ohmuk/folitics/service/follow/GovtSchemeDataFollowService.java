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
import com.ohmuk.folitics.hibernate.entity.GovtSchemeData;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.air.GovtSchemeDataCountId;
import com.ohmuk.folitics.hibernate.entity.follow.GovtSchemeDataFollow;
import com.ohmuk.folitics.hibernate.entity.follow.GovtSchemeDataFollowCount;
import com.ohmuk.folitics.hibernate.repository.follow.IGovtSchemeDataFollowRepository;
import com.ohmuk.folitics.hibernate.repository.follow.IGovtSchemeDataFollowCountRepository;
import com.ohmuk.folitics.service.IUserService;
import com.ohmuk.folitics.util.DateUtils;

@Service
@Transactional
public class GovtSchemeDataFollowService implements
		IFollowService<GovtSchemeDataFollow> {

	private static final Logger logger = LoggerFactory
			.getLogger(GovtSchemeDataFollowService.class);

	@Autowired
	IGovtSchemeDataFollowRepository govtSchemeDataFollowRepository;

	@Autowired
	IUserService userService;

	@Autowired
	IGovtSchemeDataFollowCountRepository govtSchemedataFollowCountRepository;

	@Override
	public GovtSchemeDataFollow create(FollowDataBean followDataBean)
			throws MessageException, Exception {
		GovtSchemeDataFollow govtSchemeDataFollow;

		logger.info("Inside create FollowService");

		govtSchemeDataFollow = govtSchemeDataFollowRepository.findByComponentIdAndUserId(
				followDataBean.getComponentId(), followDataBean.getUserId());

		if (govtSchemeDataFollow != null) {

			logger.info("User is already available in table");
			govtSchemeDataFollow.setFollowing(true);

		} else {

			logger.info("User is not available in table");

			// Adding new Entry in Follow Table
			govtSchemeDataFollow = new GovtSchemeDataFollow(followDataBean);
			govtSchemeDataFollow.setFollowing(true);
			govtSchemeDataFollow.setCreateTime(DateUtils.getSqlTimeStamp());

			logger.info("Adding new user");

		}

		// Adding counter for component

		GovtSchemeDataFollowCount govtSchemeDataFollowCount = new GovtSchemeDataFollowCount();
		GovtSchemeData govtSchemeData = new GovtSchemeData();
		govtSchemeData.setId(followDataBean.getComponentId());

		GovtSchemeDataCountId followCountId = new GovtSchemeDataCountId();
		followCountId.setGovtSchemeData(govtSchemeData);

		govtSchemeDataFollowCount.setId(followCountId);

		GovtSchemeDataFollowCount govtSchemeDataFollowCount2 = govtSchemedataFollowCountRepository
				.find(followCountId);

		if (govtSchemeDataFollowCount2 != null) {
			govtSchemeDataFollowCount2
					.setFollowCount(govtSchemeDataFollowCount2.getFollowCount() + 1);
			govtSchemedataFollowCountRepository.save(govtSchemeDataFollowCount2);

		}

		else {

			govtSchemeDataFollowCount.setFollowCount(1l);
			govtSchemedataFollowCountRepository.save(govtSchemeDataFollowCount);
		}

		logger.debug("User is now following " + followDataBean.getComponentId());
		logger.info("Exiting follow");

		GovtSchemeDataFollow schemeFollow = govtSchemeDataFollowRepository
				.save(govtSchemeDataFollow);

		// add monetization points to user for Follow on any component
		govtSchemeDataFollowRepository.addMonetizationPoints(followDataBean, "Follow");

		return schemeFollow;
	}

	@Override
	public void delete(FollowDataBean followDataBean) throws MessageException,
			Exception {
		GovtSchemeDataFollow govtSchemeDataFollow = govtSchemeDataFollowRepository
				.findByComponentIdAndUserId(followDataBean.getComponentId(),
						followDataBean.getUserId());
		if (govtSchemeDataFollow != null) {
			govtSchemeDataFollowRepository.delete(govtSchemeDataFollow);
		}

	}

	@Override
	public GovtSchemeDataFollow update(FollowDataBean followDataBean)
			throws MessageException, Exception {
		GovtSchemeDataFollow govtSchemeDataFollow = govtSchemeDataFollowRepository
				.findByComponentIdAndUserId(followDataBean.getComponentId(),
						followDataBean.getUserId());

		if (govtSchemeDataFollow == null) {
			logger.error("No such Entry Found");
			throw (new MessageException("No such entry found"));
		}

		govtSchemeDataFollow.setFollowing(false);

		GovtSchemeDataFollowCount govtSchemeDataFollowCount = new GovtSchemeDataFollowCount();
		GovtSchemeData govtSchemeData = new GovtSchemeData();

		govtSchemeData.setId(followDataBean.getComponentId());
		GovtSchemeDataCountId followCountId = new GovtSchemeDataCountId();

		followCountId.setGovtSchemeData(govtSchemeData);
		govtSchemeDataFollowCount.setId(followCountId);

		GovtSchemeDataFollowCount govtSchemeDataFollowCount2 = govtSchemedataFollowCountRepository
				.find(followCountId);

		if (govtSchemeDataFollowCount2 != null) {
			govtSchemeDataFollowCount2
					.setFollowCount(govtSchemeDataFollowCount2.getFollowCount() - 1);
			govtSchemedataFollowCountRepository.save(govtSchemeDataFollowCount2);

		}

		logger.debug("User is now following " + followDataBean.getComponentId());
		logger.info("Exiting follow");

		GovtSchemeDataFollow schemeFollow = govtSchemeDataFollowRepository
				.save(govtSchemeDataFollow);

		// Deducted monetization points of user
		govtSchemeDataFollowRepository.addMonetizationPoints(followDataBean, "Unfollow");

		return schemeFollow;
	}

	@Override
	public List<User> getFollowersForComponent(FollowDataBean followDataBean)
			throws MessageException, Exception {
		logger.info("Inside getFollowersForComponent");
		logger.debug("Fetching followers for componentId : "
				+ followDataBean.getComponentId());

		List<User> users;
		List<Long> followerSet = new ArrayList<Long>();

		List<GovtSchemeDataFollow> followersList = govtSchemeDataFollowRepository
				.findByComponentIdAndIsFollowing(
						followDataBean.getComponentId(), true);

		if (!followersList.isEmpty()) {
			for (GovtSchemeDataFollow follow : followersList) {
				followerSet.add(follow.getFollowId().getUserId());
			}
		}
		users = userService.getAllUserWhereIdIn(followerSet);
		return users;
	}

	@Override
	public boolean getFollowing(FollowDataBean followDataBean)
			throws MessageException, Exception {
		logger.info("Inside class : GovtSchemeDataFollowService getFollow");
		return govtSchemeDataFollowRepository
				.findByComponentIdUserIdAndIsFollowing(followDataBean);
	}

}
