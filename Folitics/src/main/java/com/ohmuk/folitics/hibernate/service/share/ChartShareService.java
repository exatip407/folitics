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
import com.ohmuk.folitics.hibernate.entity.Chart;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.share.ChartShare;
import com.ohmuk.folitics.hibernate.entity.share.ChartShareCount;
import com.ohmuk.folitics.hibernate.entity.share.ChartShareCountId;
import com.ohmuk.folitics.hibernate.repository.share.IShareHibernateRepository;
import com.ohmuk.folitics.service.IUserService;
import com.ohmuk.folitics.util.DateUtils;

/**
 * Service implementation for {@link Chart}
 * 
 * @author Harish
 *
 */
@Service
@Transactional
public class ChartShareService implements IShareService<ChartShare> {

	private static Logger logger = LoggerFactory
			.getLogger(ChartShareService.class);

	@Autowired
	IShareHibernateRepository<ChartShare> repository;

	@Autowired
	IUserService userRepository;

	@Autowired
	private IShareCountService<ChartShareCount> shareCountService;

	@Override
	public ChartShare create(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {
		logger.info("Inside create Share Chart");

		if (airShareDataBean.getComponentId() == null) {
			logger.error("componentId found to be null");
			throw (new MessageException("componentId can't be null"));
		}

		if (airShareDataBean.getUserId() == null) {
			logger.error("userId found to be null");
			throw (new MessageException("userId can't be null"));
		}
		ChartShare chartShare = new ChartShare();

		chartShare.setUserId(airShareDataBean.getUserId());
		chartShare.setComponentId(airShareDataBean.getUserId());
		chartShare.setPlatform(airShareDataBean.getPlatform());

		chartShare = repository.save(chartShare);

		// added monetization points to user
		repository.addMonetizationPoints(airShareDataBean, "Share");

		// get the entry for count from database for the component id
		ChartShareCount chartShareCount = shareCountService
				.getByComponentId(airShareDataBean.getComponentId());

		// if the share count object is null that means this is the first
		// share
		if (chartShareCount == null) {

			Chart chart = new Chart();
			chart.setId(airShareDataBean.getComponentId());

			chartShareCount = new ChartShareCount();
			ChartShareCountId chartShareCountId = new ChartShareCountId();
			chartShareCountId.setChart(chart);
			chartShareCount.setId(chartShareCountId);

			// if the component is shared on facebook then set the
			// facebookShareCount to 1 and set
			// twitterShareCount to 0
			if (airShareDataBean.getPlatform().equalsIgnoreCase(
					SharePlatforms.FACEBOOK.getValue())) {

				chartShareCount.setFacebookShareCount(1l);
				chartShareCount.setTwitterShareCount(0l);

			}
			// if the component is shared on twitter then set the
			// twitterShareCount to 1 and set
			// facebookShareCount to 0
			else if (airShareDataBean.getPlatform().equalsIgnoreCase(
					SharePlatforms.TWITTER.getValue())) {

				chartShareCount.setFacebookShareCount(0l);
				chartShareCount.setTwitterShareCount(1l);
			}

			// save the newly created count object in database
			shareCountService.create(chartShareCount);

		}
		// if the entry already exist for this component in database
		else {

			// if component is shared on facebook then increment the
			// facebookShareCount by 1
			if (airShareDataBean.getPlatform().equalsIgnoreCase(
					SharePlatforms.FACEBOOK.getValue())) {

				chartShareCount.setFacebookShareCount(chartShareCount
						.getFacebookShareCount() + 1l);

			}
			// if component is shared on twitter then increment the
			// twitterShareCount by 1
			else if (airShareDataBean.getPlatform().equalsIgnoreCase(
					SharePlatforms.TWITTER.getValue())) {

				chartShareCount.setTwitterShareCount(chartShareCount
						.getTwitterShareCount() + 1l);
			}

			// update the count in the database
			shareCountService.update(chartShareCount);

		}
		logger.info("Exiting create share");
		return chartShare;

	}

	@Override
	public ChartShare read(Long id) throws MessageException {
		if (id == null) {
			logger.error("share ID found to be null");
			throw (new MessageException("Share ID can't be null"));
		}

		ChartShare share = repository.find(id);

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
	public List<ChartShare> readAll() {
		return repository.findAll();
	}

	@Override
	public ChartShare update(AirShareDataBean airShareDataBean)
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

		ChartShare originalData = repository.find(airShareDataBean.getId());

		if (originalData == null) {
			logger.error("No Record found in database for update");
			throw (new MessageException(
					"No Record found in database for update"));
		}
		originalData.setEditTime(DateUtils.getSqlTimeStamp());
		// originalData.setStatus(ComponentState.UNSHARED.getValue());
		ChartShare share = repository.save(originalData);

		if (share == null) {
			logger.error("Something went wrong, record not updated");
			throw (new MessageException(
					"Something went wrong, record not updated"));
		}

		logger.debug("Updated IndicatorWeightedData  : " + share);
		logger.debug("Exiting Update IndicatorWeightedData");

		return share;
	}

	@Override
	public List<User> findUserListForComponent(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {
		logger.info("Inside findUserListForComponent");

		Set<ChartShare> shares = repository
				.getSharesByComponentId(airShareDataBean.getComponentId());

		logger.debug("Share fatched: " + shares.size());
		List<Long> userIds = new ArrayList<Long>();
		for (ChartShare share : shares) {
			userIds.add(share.getUserId());

		}
		List<User> users = userRepository.getAllUserWhereIdIn(userIds);

		logger.debug("Exiting Read share");

		return users;
	}

	@Override
	public boolean isComponentSharedByUser(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {
		logger.info("Inside isShared");

		Set<ChartShare> shares = null;
		shares = repository
				.getSharesByUserIdAndComponentId(airShareDataBean.getUserId(),
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
	public ChartShare delete(Long id) throws MessageException, Exception {
		logger.info("Inside delete share by ID");

		if (id == null) {
			logger.error("share ID found to be null");
			throw (new MessageException("Share ID can't be null"));
		}

		ChartShareCount chartShareCount = shareCountService
				.getByComponentId(id);

		if (chartShareCount != null) {

			shareCountService.delete(chartShareCount);

		}

		repository.delete(id);

		ChartShare share = repository.find(id);

		logger.debug("Deleted share :" + share);
		logger.debug("Exiting delete share by ID");

		return share;
	}

}
