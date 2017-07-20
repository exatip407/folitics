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
import com.ohmuk.folitics.hibernate.entity.GovtSchemeData;
import com.ohmuk.folitics.hibernate.entity.Opinion;
import com.ohmuk.folitics.hibernate.entity.Sentiment;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.share.GovtSchemeDataShare;
import com.ohmuk.folitics.hibernate.entity.share.GovtSchemeDataShareCount;
import com.ohmuk.folitics.hibernate.entity.share.GovtSchemeDataShareCountId;
import com.ohmuk.folitics.hibernate.entity.share.SentimentShareCountId;
import com.ohmuk.folitics.hibernate.repository.share.IShareHibernateRepository;
import com.ohmuk.folitics.service.IUserService;
import com.ohmuk.folitics.util.DateUtils;

/**
 * Service implementation for {@link Opinion}
 * 
 * @author Abhishek
 *
 */
@Service
@Transactional
public class GovtSchemeDataShareService implements IShareService<GovtSchemeDataShare> {

	private static Logger logger = LoggerFactory.getLogger(GovtSchemeDataShareService.class);

	@Autowired
	IShareHibernateRepository<GovtSchemeDataShare> repository;

	@Autowired
	IUserService userRepository;

	@Autowired
	private IShareCountService<GovtSchemeDataShareCount> shareCountService;

	@Override
	public GovtSchemeDataShare create(AirShareDataBean airShareDataBean) throws MessageException, Exception {
		
		logger.info("Inside create Share Sentiment");

		if (airShareDataBean.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (airShareDataBean.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}
		GovtSchemeDataShare govtSchemeDataShare = new GovtSchemeDataShare();

		govtSchemeDataShare.setUserId(airShareDataBean.getUserId());
		govtSchemeDataShare.setComponentId(airShareDataBean.getUserId());
		govtSchemeDataShare.setPlatform(airShareDataBean.getPlatform());

		govtSchemeDataShare = repository.save(govtSchemeDataShare);

		// added monetization points to user
		repository.addMonetizationPoints(airShareDataBean, "Share");

		// get the entry for count from database for the component id
		GovtSchemeDataShareCount govtSchemeDataShareCount = shareCountService
				.getByComponentId(airShareDataBean.getComponentId());

		// if the share count object is null that means this is the first
		// share
		if (govtSchemeDataShareCount == null) {

			GovtSchemeData govtSchemeData = new GovtSchemeData();
			govtSchemeData.setId(airShareDataBean.getComponentId());

			govtSchemeDataShareCount = new GovtSchemeDataShareCount();
			GovtSchemeDataShareCountId sentimentShareCountId = new GovtSchemeDataShareCountId();
			sentimentShareCountId.setGovtSchemeData(govtSchemeData);
			govtSchemeDataShareCount.setId(sentimentShareCountId);

			// if the component is shared on facebook then set the
			// facebookShareCount to 1 and set
			// twitterShareCount to 0
			if (airShareDataBean.getPlatform().equalsIgnoreCase(SharePlatforms.FACEBOOK.getValue())) {

				govtSchemeDataShareCount.setFacebookShareCount(1l);
				govtSchemeDataShareCount.setTwitterShareCount(0l);

			}
			// if the component is shared on twitter then set the
			// twitterShareCount to 1 and set
			// facebookShareCount to 0
			else if (airShareDataBean.getPlatform().equalsIgnoreCase(SharePlatforms.TWITTER.getValue())) {

				govtSchemeDataShareCount.setFacebookShareCount(0l);
				govtSchemeDataShareCount.setTwitterShareCount(1l);
			}

			// save the newly created count object in database
			shareCountService.create(govtSchemeDataShareCount);

		}
		// if the entry already exist for this component in database
		else {

			// if component is shared on facebook then increment the
			// facebookShareCount by 1
			if (airShareDataBean.getPlatform().equalsIgnoreCase(SharePlatforms.FACEBOOK.getValue())) {

				govtSchemeDataShareCount.setFacebookShareCount(govtSchemeDataShareCount.getFacebookShareCount() + 1l);

			}
			// if component is shared on twitter then increment the
			// twitterShareCount by 1
			else if (airShareDataBean.getPlatform().equalsIgnoreCase(SharePlatforms.TWITTER.getValue())) {

				govtSchemeDataShareCount.setTwitterShareCount(govtSchemeDataShareCount.getTwitterShareCount() + 1l);
			}

			// update the count in the database
			shareCountService.update(govtSchemeDataShareCount);

		}
		logger.info("Exiting create share");
		return govtSchemeDataShare;
	}

	@Override
	public GovtSchemeDataShare read(Long id) throws MessageException {
		if (id == null) {
			logger.error("share ID found to be null");
			throw (new MessageException("Share ID can't be null"));
		}

		GovtSchemeDataShare share = repository.find(id);

		if (share == null) {
			logger.error("Something went wrong, record not found for given id: " + id + ", Can't delete record.");
			throw (new MessageException(
					"Something went wrong, record not found for given id: " + id + ", Can't delete record."));
		}
		return share;
	}

	@Override
	public List<GovtSchemeDataShare> readAll() {
		return repository.findAll();
	}

	@Override
	public GovtSchemeDataShare update(AirShareDataBean airShareDataBean) throws MessageException, Exception {
		logger.info("Inside Update Share");

		if (airShareDataBean.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (airShareDataBean.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		GovtSchemeDataShare originalData = repository.find(airShareDataBean.getId());

		if (originalData == null) {
			logger.error("No Record found in database for update");
			throw (new MessageException("No Record found in database for update"));
		}
		originalData.setEditTime(DateUtils.getSqlTimeStamp());
		// originalData.setStatus(ComponentState.UNSHARED.getValue());
		GovtSchemeDataShare share = repository.save(originalData);

		if (share == null) {
			logger.error("Something went wrong, record not updated");
			throw (new MessageException("Something went wrong, record not updated"));
		}

		logger.debug("Updated IndicatorWeightedData  : " + share);
		logger.debug("Exiting Update IndicatorWeightedData");

		return share;
	}

	@Override
	public List<User> findUserListForComponent(AirShareDataBean airShareDataBean) throws MessageException, Exception {
		logger.info("Inside findUserListForComponent");

		Set<GovtSchemeDataShare> shares = repository.getSharesByComponentId(airShareDataBean.getComponentId());

		logger.debug("Share fatched: " + shares.size());
		List<Long> userIds = new ArrayList<Long>();
		for (GovtSchemeDataShare share : shares) {
			userIds.add(share.getUserId());

		}
		List<User> users = userRepository.getAllUserWhereIdIn(userIds);

		logger.debug("Exiting Read share");

		return users;
	}

	@Override
	public boolean isComponentSharedByUser(AirShareDataBean airShareDataBean) throws MessageException, Exception {
		logger.info("Inside isShared");

		Set<GovtSchemeDataShare> shares = null;
		shares = repository.getSharesByUserIdAndComponentId(airShareDataBean.getUserId(),
				airShareDataBean.getComponentId());

		if (shares == null || shares.size() < 1) {
			logger.debug("Component Not shared by user");
			return false;
		}

		logger.debug("Share fatched: " + shares.size());
		logger.debug("Exiting isComponentSharedByUser share");

		return true;
	}

	@Override
	public GovtSchemeDataShare delete(Long id) throws MessageException, Exception {
		logger.info("Inside delete share by ID");

		if (id == null) {
			logger.error("share ID found to be null");
			throw (new MessageException("Share ID can't be null"));
		}

		GovtSchemeDataShareCount govtSchemeDataShareCount = shareCountService.getByComponentId(id);

		if (govtSchemeDataShareCount != null) {

			shareCountService.delete(govtSchemeDataShareCount);

		}

		repository.delete(id);

		GovtSchemeDataShare share = repository.find(id);

		logger.debug("Deleted share :" + share);
		logger.debug("Exiting delete share by ID");

		return share;
	}

}
