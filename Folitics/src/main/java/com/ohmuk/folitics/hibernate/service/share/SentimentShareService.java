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
import com.ohmuk.folitics.hibernate.entity.Sentiment;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.share.SentimentShare;
import com.ohmuk.folitics.hibernate.entity.share.SentimentShareCount;
import com.ohmuk.folitics.hibernate.entity.share.SentimentShareCountId;
import com.ohmuk.folitics.hibernate.repository.share.IShareHibernateRepository;
import com.ohmuk.folitics.service.IUserService;
import com.ohmuk.folitics.util.DateUtils;

/**
 * Service implementation for {@link Sentiment}
 * 
 * @author Abhishek
 *
 */
@Service
@Transactional
public class SentimentShareService implements IShareService<SentimentShare> {

	private static Logger logger = LoggerFactory
			.getLogger(SentimentShareService.class);

	@Autowired
	IShareHibernateRepository<SentimentShare> repository;

	@Autowired
	IUserService userRepository;

	@Autowired
	private IShareCountService<SentimentShareCount> shareCountService;

	@Override
	public SentimentShare create(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {

		logger.info("Inside create Share Sentiment");

		if (airShareDataBean.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (airShareDataBean.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}
		SentimentShare sentimentShare = new SentimentShare();

		sentimentShare.setUserId(airShareDataBean.getUserId());
		sentimentShare.setComponentId(airShareDataBean.getUserId());
		sentimentShare.setPlatform(airShareDataBean.getPlatform());

		sentimentShare = repository.save(sentimentShare);
		
		//added monetization points to user 
		repository.addMonetizationPoints(airShareDataBean, "Share");

		// get the entry for count from database for the component id
		SentimentShareCount sentimentShareCount = shareCountService
				.getByComponentId(airShareDataBean.getComponentId());

		// if the share count object is null that means this is the first
		// share
		if (sentimentShareCount == null) {

			Sentiment sentiment = new Sentiment();
			sentiment.setId(airShareDataBean.getComponentId());

			sentimentShareCount = new SentimentShareCount();
			SentimentShareCountId sentimentShareCountId = new SentimentShareCountId();
			sentimentShareCountId.setSentiment(sentiment);
			sentimentShareCount.setId(sentimentShareCountId);

			// if the component is shared on facebook then set the
			// facebookShareCount to 1 and set
			// twitterShareCount to 0
			if (airShareDataBean.getPlatform().equalsIgnoreCase(
					SharePlatforms.FACEBOOK.getValue())) {

				sentimentShareCount.setFacebookShareCount(1l);
				sentimentShareCount.setTwitterShareCount(0l);

			}
			// if the component is shared on twitter then set the
			// twitterShareCount to 1 and set
			// facebookShareCount to 0
			else if (airShareDataBean.getPlatform().equalsIgnoreCase(
					SharePlatforms.TWITTER.getValue())) {

				sentimentShareCount.setFacebookShareCount(0l);
				sentimentShareCount.setTwitterShareCount(1l);
			}

			// save the newly created count object in database
			shareCountService.create(sentimentShareCount);

		}
		// if the entry already exist for this component in database
		else {

			// if component is shared on facebook then increment the
			// facebookShareCount by 1
			if (airShareDataBean.getPlatform().equalsIgnoreCase(
					SharePlatforms.FACEBOOK.getValue())) {

				sentimentShareCount.setFacebookShareCount(sentimentShareCount
						.getFacebookShareCount() + 1l);

			}
			// if component is shared on twitter then increment the
			// twitterShareCount by 1
			else if (airShareDataBean.getPlatform().equalsIgnoreCase(
					SharePlatforms.TWITTER.getValue())) {

				sentimentShareCount.setTwitterShareCount(sentimentShareCount
						.getTwitterShareCount() + 1l);
			}

			// update the count in the database
			shareCountService.update(sentimentShareCount);

		}
		logger.info("Exiting create share");
		return sentimentShare;

	}

	@Override
	public SentimentShare read(Long id) throws MessageException {

		if (id == null) {
			logger.error("share ID found to be null");
			throw (new MessageException("Share ID can't be null"));
		}

		SentimentShare share = repository.find(id);

		if (share == null) {
			logger.error("Something went wrong, record not found for given id: "
					+ id + ", Can't delete record.");
			throw (new MessageException(
					"Something went wrong, record not found for given id: "
							+ id + ", Can't delete record."));
		}
		return share;
	}

	@Override
	public List<SentimentShare> readAll() {

		return repository.findAll();
	}

	
	@Override
	public boolean isComponentSharedByUser(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {

		logger.info("Inside isShared");

		Set<SentimentShare> shares = null;
		shares = repository
				.getSharesByUserIdAndComponentId(
				airShareDataBean.getUserId(), airShareDataBean.getComponentId());

		if (shares == null || shares.size() < 1) {
			logger.debug("Component Not shared by user");
			return false;
		}

		logger.debug("Share fatched: " + shares.size());
		logger.debug("Exiting isComponentSharedByUser share");

		return true;
	}

	@Override
	public List<User> findUserListForComponent(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {

		logger.info("Inside findUserListForComponent");

		Set<SentimentShare> shares = repository
				.getSharesByComponentId(airShareDataBean.getComponentId());

		logger.debug("Share fatched: " + shares.size());
		List<Long> userIds = new ArrayList<Long>();
		for (SentimentShare share : shares) {
			userIds.add(share.getUserId());

		}
		List<User> users = userRepository.getAllUserWhereIdIn(userIds);

		logger.debug("Exiting Read share");

		return users;
	}

	/**
	 * This method is used to update the record.
	 * 
	 * @param indicatorweighteddata
	 * @return IndicateWData : Update IndicatorWeightedData
	 * @throws Exception
	 */
	@Override
	public SentimentShare update(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {

		logger.info("Inside Update Share");

		if (airShareDataBean.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (airShareDataBean.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		SentimentShare originalData = repository.find(airShareDataBean.getId());

		if (originalData == null) {
			logger.error("No Record found in database for update");
			throw (new MessageException(
					"No Record found in database for update"));
		}
		originalData.setEditTime(DateUtils.getSqlTimeStamp());
		// originalData.setStatus(ComponentState.UNSHARED.getValue());
		SentimentShare share = repository.save(originalData);

		if (share == null) {
			logger.error("Something went wrong, record not updated");
			throw (new MessageException(
					"Something went wrong, record not updated"));
		}

		logger.debug("Updated IndicatorWeightedData  : " + share);
		logger.debug("Exiting Update IndicatorWeightedData");

		return share;
	}

	/**
	 * This method is used to delete the record by ID.
	 * 
	 * @param share
	 * @return share : deleteByID share
	 * @throws Exception
	 */
	@Override
	public SentimentShare delete(Long id) throws MessageException, Exception {

		logger.info("Inside delete share by ID");

		if (id == null) {
			logger.error("share ID found to be null");
			throw (new MessageException("Share ID can't be null"));
		}

		SentimentShareCount sentimentShareCount = shareCountService
				.getByComponentId(id);

		if (sentimentShareCount != null) {

			shareCountService.delete(sentimentShareCount);

		}

		repository.delete(id);

		SentimentShare share = repository.find(id);

		logger.debug("Deleted share :" + share);
		logger.debug("Exiting delete share by ID");

		return share;
	}

}
