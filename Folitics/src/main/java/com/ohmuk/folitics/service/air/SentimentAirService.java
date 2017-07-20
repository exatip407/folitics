package com.ohmuk.folitics.service.air;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.beans.AirShareDataBean;
import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Sentiment;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.air.SentimentAir;
import com.ohmuk.folitics.hibernate.entity.air.SentimentAirCount;
import com.ohmuk.folitics.hibernate.entity.air.SentimentCountId;
import com.ohmuk.folitics.hibernate.repository.air.ISentimentAirCountRepository;
import com.ohmuk.folitics.hibernate.repository.air.ISentimentAirRepository;
import com.ohmuk.folitics.service.IUserService;
import com.ohmuk.folitics.util.DateUtils;

@Service
@Transactional
public class SentimentAirService implements IAirService {
	private static Logger logger = LoggerFactory
			.getLogger(SentimentAirService.class);

	@Autowired
	ISentimentAirRepository repository;

	@Autowired
	ISentimentAirCountRepository iSentimentAirCountRepository;

	@Autowired
	IUserService userService;

	/**
	 * This method is used to create the air.
	 * 
	 * @param AirShareDataBean
	 * @return the entity which is created
	 * @throws MessageException
	 *             ,Exception
	 */
	@Override
	public Object create(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {

		logger.debug("Inside create Air Sentiment Service");

		SentimentAir air = new SentimentAir();
		air.setUserId(airShareDataBean.getUserId());
		air.setComponentId(airShareDataBean.getComponentId());
		air.setComponentType(airShareDataBean.getComponentType());
		air.setDescription(airShareDataBean.getDescription());

		repository.save(air);

		// add monetization points to user for Air on any component
		repository.addMonetizationPoints(airShareDataBean, "Air");

		// Adding counter for the component
		SentimentAirCount sentimentAirCount = new SentimentAirCount();
		Sentiment sentiment = new Sentiment();
		sentiment.setId(airShareDataBean.getComponentId());

		SentimentCountId sentimentCountId = new SentimentCountId();
		sentimentCountId.setSentiment(sentiment);

		sentimentAirCount.setId(sentimentCountId);

		SentimentAirCount sentimentAirCount2 = iSentimentAirCountRepository
				.find(sentimentCountId);
		// if count available for component adding counter else entering counter
		// for the first time for user and component
		if (sentimentAirCount2 != null) {
			sentimentAirCount2
					.setAirCount(sentimentAirCount2.getAirCount() + 1);
			iSentimentAirCountRepository.save(sentimentAirCount2);
		} else {
			sentimentAirCount.setAirCount(1l);
			iSentimentAirCountRepository.save(sentimentAirCount);
		}

		logger.debug("Exiting create share");
		return air;
	}

	/**
	 * This method is used to find if the given user has aired the component.
	 * 
	 * @param AirShareDataBean
	 * @return true if aired by user false if not
	 * @throws MessageException
	 *             ,Exception
	 */
	@Override
	public boolean isComponentAiredByUser(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {

		logger.debug("Exiting isComponentAiredByUser share");
		List<SentimentAir> shares = null;
		shares = repository
				.getByComponentIdAndUserId(airShareDataBean.getComponentId(),
						airShareDataBean.getUserId());

		if (shares == null || shares.size() < 1) {
			logger.debug("Component Not shared by user");
			return false;
		}

		logger.debug("Air fetched: " + shares.size());
		logger.debug("Exiting isComponentAiredByUser share");

		return true;
	}

	/**
	 * This method is used to find user list who aired the component.
	 * 
	 * @param AirShareDataBean
	 * @return List of user sharing the component
	 * @throws MessageException
	 *             ,Exception
	 */
	@Override
	public List<User> findUserListForComponent(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {

		logger.debug("Inside findUserListForComponent");

		List<SentimentAir> airs = repository
				.findAirsByComponentId(airShareDataBean.getId());

		logger.debug("Airs fatched: " + airs.size());
		List<Long> userIds = new ArrayList<Long>();
		for (SentimentAir air : airs) {
			userIds.add(air.getUserId());

		}
		List<User> users = userService.getAllUserWhereIdIn(userIds);

		logger.debug("Exiting findUserListForComponent");

		return users;
	}

	/**
	 * This method is used to update the air. Only description will be updated.
	 * 
	 * @param AirShareDataBean
	 * @return the entity which is updated
	 * @throws MessageException
	 *             ,Exception
	 */
	@Override
	public SentimentAir update(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {

		logger.debug("Inside Update Air");

		SentimentAir originalData = repository.find(airShareDataBean.getId());
		if (originalData == null) {
			logger.error("No Record found in database for update");
			throw (new MessageException(
					"No Record found in database for update"));
		}
		originalData.setEditTime(DateUtils.getSqlTimeStamp());
		originalData.setDescription(airShareDataBean.getDescription());
		SentimentAir air = repository.save(originalData);

		if (air == null) {
			logger.error("Something went wrong, record not updated");
			throw (new MessageException(
					"Something went wrong, record not updated"));
		}

		logger.debug("Updated air  : " + air);
		logger.debug("Exiting Update");

		return air;
	}

	/**
	 * This method is used to soft delete the air from DB.
	 * 
	 * @param AirShareDataBean
	 * @return the entity which is softly deleted
	 * @throws MessageException
	 *             ,Exception
	 */
	@Override
	public SentimentAir delete(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {

		logger.debug("Inside soft delete air by ID");

		if (airShareDataBean.getId() == null) {
			logger.error("Air ID found to be null");
			throw (new MessageException("Air ID can't be null"));
		}

		SentimentAir air = repository.find(airShareDataBean.getId());

		if (air == null) {
			logger.error("Something went wrong, record not found for given id: "
					+ airShareDataBean.getId() + ", Can't delete record.");
			throw (new MessageException(
					"Something went wrong, record not found for given id: "
							+ airShareDataBean.getId()
							+ ", Can't delete record."));
		}

		air.setStatus(ComponentState.DELETED.getValue());
		repository.save(air);

		logger.debug("Deleted air:" + air);
		logger.debug("Exiting soft delete air by ID");

		return air;
	}

	/**
	 * This method is used to delete the air from DB.
	 * 
	 * @param AirShareDataBean
	 * @return Null object of deleted successfully
	 * @throws MessageException
	 *             ,Exception
	 */

	@Override
	public SentimentAir deleteFromDB(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {

		logger.debug("Inside soft delete air by ID");

		if (airShareDataBean.getId() == null) {
			logger.error("Air ID found to be null");
			throw (new MessageException("Air ID can't be null"));
		}

		SentimentAir air = repository.find(airShareDataBean.getId());

		if (air == null) {
			logger.error("Something went wrong, record not found for given id: "
					+ airShareDataBean.getId() + ", Can't delete record.");
			throw (new MessageException(
					"Something went wrong, record not found for given id: "
							+ airShareDataBean.getId()
							+ ", Can't delete record."));
		}
		repository.delete(air.getId());

		logger.debug("Deleted air :" + air);
		logger.debug("Exiting soft delete air by ID");

		return air;
	}
}
