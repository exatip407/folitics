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
import com.ohmuk.folitics.hibernate.entity.Sentiment;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.follow.SentimentFollow;
import com.ohmuk.folitics.hibernate.entity.follow.SentimentFollowCount;
import com.ohmuk.folitics.hibernate.entity.follow.SentimentFollowCountId;
import com.ohmuk.folitics.hibernate.repository.follow.ISentimentFollowCountRepository;
import com.ohmuk.folitics.hibernate.repository.follow.ISentimentFollowHibernateRepository;
import com.ohmuk.folitics.service.IUserService;
import com.ohmuk.folitics.util.DateUtils;

/**
 * @author Sarvesh
 *
 */
@Service
@Transactional
public class SentimentFollowService implements IFollowService<SentimentFollow> {

	@Autowired
	ISentimentFollowHibernateRepository iFollowRepository;

	@Autowired
	IUserService iUserService;

	@Autowired
	ISentimentFollowCountRepository countRepository;

	private static final Logger logger = LoggerFactory
			.getLogger(SentimentFollowService.class);

	@Override
	public SentimentFollow create(FollowDataBean followDataBean)
			throws MessageException, Exception {

		SentimentFollow follower;

		logger.info("Inside create FollowService");

		follower = iFollowRepository.findByComponentIdAndUserId(
				followDataBean.getComponentId(), followDataBean.getUserId());

		if (follower != null) {

			logger.info("User is already available in table");
			follower.setFollowing(true);

		} else {

			logger.info("User is not available in table");

			// Adding new Entry in Follow Table
			follower = new SentimentFollow(followDataBean);
			follower.setFollowing(true);
			follower.setCreateTime(DateUtils.getSqlTimeStamp());

			logger.info("Adding new user");

		}

		// Adding counter for component

		SentimentFollowCount sentimentFollowCount = new SentimentFollowCount();
		Sentiment sentiment = new Sentiment();
		sentiment.setId(followDataBean.getComponentId());

		SentimentFollowCountId followCountId = new SentimentFollowCountId();
		followCountId.setSentiment(sentiment);

		sentimentFollowCount.setId(followCountId);

		SentimentFollowCount sentimentFollowCount2 = countRepository
				.find(followCountId);

		if (sentimentFollowCount2 != null) {
			sentimentFollowCount2.setFollowCount(sentimentFollowCount2
					.getFollowCount() + 1);
			countRepository.save(sentimentFollowCount2);

		}

		else {

			sentimentFollowCount.setFollowCount(1l);
			countRepository.save(sentimentFollowCount);
		}

		logger.debug("User is now following " + followDataBean.getComponentId());
		logger.info("Exiting follow");

		SentimentFollow sentimentFollow = iFollowRepository.save(follower);

		// add monetization points to user for Follow on any component
		iFollowRepository.addMonetizationPoints(followDataBean, "Follow");

		return sentimentFollow;

	}

	@Override
	public void delete(FollowDataBean followDataBean) throws MessageException,
			Exception {

		SentimentFollow follower = iFollowRepository
				.findByComponentIdAndUserId(followDataBean.getComponentId(),
						followDataBean.getUserId());
		if (follower != null) {
			iFollowRepository.delete(follower);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.service.follow.IFollowService#update(com.ohmuk.folitics
	 * .beans.FollowDataBean)
	 */
	@Override
	public SentimentFollow update(FollowDataBean followDataBean)
			throws MessageException, Exception {

		SentimentFollow follower = iFollowRepository
				.findByComponentIdAndUserId(followDataBean.getComponentId(),
						followDataBean.getUserId());

		if (follower == null) {
			logger.error("No such Entry Found");
			throw (new MessageException("No such entry found"));
		}

		follower.setFollowing(false);

		SentimentFollowCount sentimentFollowCount = new SentimentFollowCount();
		Sentiment sentiment = new Sentiment();
		sentiment.setId(followDataBean.getComponentId());
		SentimentFollowCountId followCountId = new SentimentFollowCountId();
		followCountId.setSentiment(sentiment);
		sentimentFollowCount.setId(followCountId);
		SentimentFollowCount sentimentFollowCount2 = countRepository
				.find(followCountId);

		if (sentimentFollowCount2 != null) {

			if (sentimentFollowCount2.getFollowCount() == 0) {

				countRepository.save(sentimentFollowCount2);
			} else {
				sentimentFollowCount2.setFollowCount(sentimentFollowCount2
						.getFollowCount() - 1);
				countRepository.save(sentimentFollowCount2);
			}

		}

		logger.debug("User is now following " + followDataBean.getComponentId());
		logger.info("Exiting follow");

		SentimentFollow sentimentFollow = iFollowRepository.save(follower);

		// Deducted monetization points of user
		iFollowRepository.addMonetizationPoints(followDataBean, "Unfollow");

		return sentimentFollow;

	}

	@Override
	public List<User> getFollowersForComponent(FollowDataBean followDataBean)
			throws MessageException, Exception {

		logger.info("Inside getFollowersForComponent");
		logger.debug("Fetching followers for componentId : "
				+ followDataBean.getComponentId());

		List<User> users;
		List<Long> followerSet = new ArrayList<Long>();

		List<SentimentFollow> followersList = iFollowRepository
				.findByComponentIdAndIsFollowing(
						followDataBean.getComponentId(), true);

		if (!followersList.isEmpty()) {
			for (SentimentFollow follow : followersList) {
				followerSet.add(follow.getFollowId().getUserId());
			}
		}
		users = iUserService.getAllUserWhereIdIn(followerSet);
		return users;
	}

	@Override
	public boolean getFollowing(FollowDataBean followDataBean)
			throws MessageException, Exception {
		logger.info("Inside class : SentimentFollowService getFollow");
		return iFollowRepository
				.findByComponentIdUserIdAndIsFollowing(followDataBean);
	}

}
