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
import com.ohmuk.folitics.hibernate.entity.Opinion;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.share.OpinionShare;
import com.ohmuk.folitics.hibernate.entity.share.OpinionShareCount;
import com.ohmuk.folitics.hibernate.entity.share.OpinionShare;
import com.ohmuk.folitics.hibernate.entity.share.OpinionShareCount;
import com.ohmuk.folitics.hibernate.entity.share.OpinionShareCountId;
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
public class OpinionShareService implements IShareService<OpinionShare> {

	private static Logger logger = LoggerFactory.getLogger(OpinionShareService.class);

	@Autowired
	IShareHibernateRepository<OpinionShare> repository;

	@Autowired
	IUserService userRepository;

	@Autowired
	private IShareCountService<OpinionShareCount> shareCountService;

	@Override
	public OpinionShare create(AirShareDataBean airShareDataBean) throws MessageException, Exception {
		logger.info("Inside create Share Opinion");

		if (airShareDataBean.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (airShareDataBean.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}
		OpinionShare opinionShare = new OpinionShare();

		opinionShare.setUserId(airShareDataBean.getUserId());
		opinionShare.setComponentId(airShareDataBean.getUserId());
		opinionShare.setPlatform(airShareDataBean.getPlatform());

		opinionShare = repository.save(opinionShare);

		// added monetization points to user
		repository.addMonetizationPoints(airShareDataBean, "Share");

		// get the entry for count from database for the component id
		OpinionShareCount opinionShareCount = shareCountService.getByComponentId(airShareDataBean.getComponentId());

		// if the share count object is null that means this is the first
		// share
		if (opinionShareCount == null) {

			Opinion opinion = new Opinion();
			opinion.setId(airShareDataBean.getComponentId());

			opinionShareCount = new OpinionShareCount();
			OpinionShareCountId opinionShareCountId = new OpinionShareCountId();
			opinionShareCountId.setOpinion(opinion);
			opinionShareCount.setId(opinionShareCountId);

			// if the component is shared on facebook then set the
			// facebookShareCount to 1 and set
			// twitterShareCount to 0
			if (airShareDataBean.getPlatform().equalsIgnoreCase(SharePlatforms.FACEBOOK.getValue())) {

				opinionShareCount.setFacebookShareCount(1l);
				opinionShareCount.setTwitterShareCount(0l);

			}
			// if the component is shared on twitter then set the
			// twitterShareCount to 1 and set
			// facebookShareCount to 0
			else if (airShareDataBean.getPlatform().equalsIgnoreCase(SharePlatforms.TWITTER.getValue())) {

				opinionShareCount.setFacebookShareCount(0l);
				opinionShareCount.setTwitterShareCount(1l);
			}

			// save the newly created count object in database
			shareCountService.create(opinionShareCount);

		}
		// if the entry already exist for this component in database
		else {

			// if component is shared on facebook then increment the
			// facebookShareCount by 1
			if (airShareDataBean.getPlatform().equalsIgnoreCase(SharePlatforms.FACEBOOK.getValue())) {

				opinionShareCount.setFacebookShareCount(opinionShareCount.getFacebookShareCount() + 1l);

			}
			// if component is shared on twitter then increment the
			// twitterShareCount by 1
			else if (airShareDataBean.getPlatform().equalsIgnoreCase(SharePlatforms.TWITTER.getValue())) {

				opinionShareCount.setTwitterShareCount(opinionShareCount.getTwitterShareCount() + 1l);
			}

			// update the count in the database
			shareCountService.update(opinionShareCount);

		}
		logger.info("Exiting create share");
		return opinionShare;
	}

	@Override
	public OpinionShare read(Long id) throws MessageException {
		if (id == null) {
			logger.error("share ID found to be null");
			throw (new MessageException("Share ID can't be null"));
		}

		OpinionShare share = repository.find(id);

		if (share == null) {
			logger.error("Something went wrong, record not found for given id: " + id + ", Can't delete record.");
			throw (new MessageException(
					"Something went wrong, record not found for given id: " + id + ", Can't delete record."));
		}
		return share;
	}

	@Override
	public List<OpinionShare> readAll() {
		return repository.findAll();
	}

	@Override
	public OpinionShare update(AirShareDataBean airShareDataBean) throws MessageException, Exception {
		logger.info("Inside Update Share");

		if (airShareDataBean.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (airShareDataBean.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}

		OpinionShare originalData = repository.find(airShareDataBean.getId());

		if (originalData == null) {
			logger.error("No Record found in database for update");
			throw (new MessageException("No Record found in database for update"));
		}
		originalData.setEditTime(DateUtils.getSqlTimeStamp());
		// originalData.setStatus(ComponentState.UNSHARED.getValue());
		OpinionShare share = repository.save(originalData);

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

		Set<OpinionShare> shares = repository.getSharesByComponentId(airShareDataBean.getComponentId());

		logger.debug("Share fatched: " + shares.size());
		List<Long> userIds = new ArrayList<Long>();
		for (OpinionShare share : shares) {
			userIds.add(share.getUserId());

		}
		List<User> users = userRepository.getAllUserWhereIdIn(userIds);

		logger.debug("Exiting Read share");

		return users;
	}

	@Override
	public boolean isComponentSharedByUser(AirShareDataBean airShareDataBean) throws MessageException, Exception {
		logger.info("Inside isShared");

		Set<OpinionShare> shares = null;
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
	public OpinionShare delete(Long id) throws MessageException, Exception {
		logger.info("Inside delete share by ID");

		if (id == null) {
			logger.error("share ID found to be null");
			throw (new MessageException("Share ID can't be null"));
		}

		OpinionShareCount opinionShareCount = shareCountService.getByComponentId(id);

		if (opinionShareCount != null) {

			shareCountService.delete(opinionShareCount);

		}

		repository.delete(id);

		OpinionShare share = repository.find(id);

		logger.debug("Deleted share :" + share);
		logger.debug("Exiting delete share by ID");

		return share;
	}

}
