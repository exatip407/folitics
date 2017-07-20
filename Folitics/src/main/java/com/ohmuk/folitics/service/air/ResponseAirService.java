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
import com.ohmuk.folitics.hibernate.entity.Response;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.air.ResponseAir;
import com.ohmuk.folitics.hibernate.entity.air.ResponseAirCount;
import com.ohmuk.folitics.hibernate.entity.air.ResponseAirCountId;
import com.ohmuk.folitics.hibernate.repository.air.IResponseAirCountRepository;
import com.ohmuk.folitics.hibernate.repository.air.IResponseAirRepository;
import com.ohmuk.folitics.service.IUserService;
import com.ohmuk.folitics.util.DateUtils;

@Service
@Transactional
public class ResponseAirService implements IAirService {

	private static Logger logger = LoggerFactory
			.getLogger(TaskAirService.class);

	@Autowired
	IResponseAirRepository repository;

	@Autowired
	IResponseAirCountRepository responseAirCountRepository;

	@Autowired
	IUserService userService;

	
	@Override
	public Object create(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {
		logger.debug("Inside create Air Response Service");

		ResponseAir responseAir = new ResponseAir();
		responseAir.setUserId(airShareDataBean.getUserId());
		responseAir.setComponentId(airShareDataBean.getComponentId());
		responseAir.setComponentType(airShareDataBean.getComponentType());
		responseAir.setDescription(airShareDataBean.getDescription());

		repository.save(responseAir);

		// add monetization points to user for Air on any component
		repository.addMonetizationPoints(airShareDataBean, "Air");

		// Adding counter for the component
		ResponseAirCount responseAirCount = new ResponseAirCount();
		Response response = new Response();
		response.setId(airShareDataBean.getComponentId());

		ResponseAirCountId responseAirCountId = new ResponseAirCountId();
		responseAirCountId.setResponse(response);

		responseAirCount.setId(responseAirCountId);

		ResponseAirCount responseAirCount2 = responseAirCountRepository.find(responseAirCountId);
		
		// if count avaialble for component adding counter else entering counter
		// for the first time for user and component
		if (responseAirCount2 != null) {
			responseAirCount2.setAirCount(responseAirCount2.getAirCount() + 1);
			responseAirCountRepository.save(responseAirCount2);
		} else {
			responseAirCount.setAirCount(1l);
			responseAirCountRepository.save(responseAirCount);
		}

		logger.debug("Exiting create air");
		return responseAir;
	}
	

	@Override
	public Object update(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {
		logger.debug("Inside Update Air");

		ResponseAir originalData = repository.find(airShareDataBean.getId());
		if (originalData == null) {
			logger.error("No Record found in database for update");
			throw (new MessageException(
					"No Record found in database for update"));
		}
		originalData.setEditTime(DateUtils.getSqlTimeStamp());
		originalData.setDescription(airShareDataBean.getDescription());
		ResponseAir air = repository.save(originalData);

		if (air == null) {
			logger.error("Something went wrong, record not updated");
			throw (new MessageException(
					"Something went wrong, record not updated"));
		}

		logger.debug("Updated air  : " + air);
		logger.debug("Exiting Update");

		return air;
	}
	
	@Override
	public List<User> findUserListForComponent(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {
		logger.debug("Inside findUserListForComponent");

		List<ResponseAir> responseAirs = repository
				.findAirsByComponentId(airShareDataBean.getId());

		logger.debug("Airs fetched: " + responseAirs.size());
		List<Long> userIds = new ArrayList<Long>();
		for (ResponseAir air : responseAirs) {
			userIds.add(air.getUserId());

		}
		List<User> users = userService.getAllUserWhereIdIn(userIds);

		logger.debug("Exiting findUserListForComponent");

		return users;
	}

	@Override
	public boolean isComponentAiredByUser(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {
		logger.debug("Exiting isComponentAiredByUser share");
		List<ResponseAir> responseAirs = null;
		responseAirs = repository
				.getByComponentIdAndUserId(airShareDataBean.getComponentId(),
						airShareDataBean.getUserId());

		if (responseAirs == null || responseAirs.size() < 1) {
			logger.debug("Component Not air by user");
			return false;
		}

		logger.debug("Air fetched: " + responseAirs.size());
		logger.debug("Exiting isComponentAiredByUser share");

		return true;
	}

	@Override
	public Object deleteFromDB(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {
		logger.debug("Inside hard delete air by ID");

		if (airShareDataBean.getId() == null) {
			logger.error("Air ID found to be null");
			throw (new MessageException("Air ID can't be null"));
		}

		ResponseAir air = repository.find(airShareDataBean.getId());

		if (air == null) {
			logger.error("Something went wrong, record not found for given id: "
					+ airShareDataBean.getId() + ", Can't delete record.");
			throw (new MessageException(
					"Something went wrong, record not found for given id: "
							+ airShareDataBean.getId()
							+ ", Can't delete record."));
		}

		air.setStatus(ComponentState.DELETED.getValue());
		repository.delete(air.getId());

		logger.debug("Deleted air :" + air);
		logger.debug("Exiting soft delete air by ID");

		return air;
	}

	@Override
	public Object delete(AirShareDataBean airShareDataBean)
			throws MessageException, Exception {
		logger.debug("Inside soft delete air by ID");

		if (airShareDataBean.getId() == null) {
			logger.error("Air ID found to be null");
			throw (new MessageException("Air ID can't be null"));
		}

		ResponseAir air = repository.find(airShareDataBean.getId());

		if (air == null) {
			logger.error("Something went wrong, record not found for given id: "
					+ airShareDataBean.getId() + ", Can't delete record.");
			throw (new MessageException(
					"Something went wrong, record not found for given id: "
							+ airShareDataBean.getId()
							+ ", Can't delete record."));
		}

		air.setStatus(ComponentState.DELETED.getValue());
		repository.update(air);

		logger.debug("Deleted air:" + air);
		logger.debug("Exiting soft delete air by ID");

		return air;
	}

}
