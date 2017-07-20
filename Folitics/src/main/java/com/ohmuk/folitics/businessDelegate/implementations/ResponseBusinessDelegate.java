package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.businessDelegate.interfaces.IResponseBusinessDelegate;
import com.ohmuk.folitics.hibernate.entity.Response;
import com.ohmuk.folitics.notification.InterfaceNotificationService;
import com.ohmuk.folitics.notification.NotificationMapping;
import com.ohmuk.folitics.service.IResponseService;

@Component
public class ResponseBusinessDelegate implements IResponseBusinessDelegate {
	private static Logger logger = LoggerFactory
			.getLogger(ResponseBusinessDelegate.class);

	final int PRO_AGREE = 1;
	final int PRO_DISAGREE = 1;
	final int ANTI_AGREE = -1;
	final int ANTI_DISAGREE = -1;

	@Autowired
	private volatile IResponseService responseService;

	@Autowired
	private volatile InterfaceNotificationService notificationService;

	@Override
	public Response create(Response response) throws Exception {

		logger.info("Inside create method in business delegate");
		Response responseData = responseService.create(response);
		//call for notification
		if (responseData != null) {
			NotificationMapping notificationMapping = new NotificationMapping();
			notificationMapping.setUserId(responseData.getUser().getId());
			notificationMapping.setComponentId(responseData.getId());
			notificationMapping.setComponentType("response");

			notificationService.responseNotification(notificationMapping,responseData);
		}
		logger.info("Exiting create method in business delegate");
		return responseData;
	}

	@Override
	public Response getResponseById(Long id) throws Exception {
		logger.info("Inside getResponseById method in business delegate");
		Response responseData = responseService.getResponseById(id);
		logger.info("Exiting getResponseById method in business delegate");
		return responseData;
	}

	@Override
	public List<Response> readAll() throws Exception {
		logger.info("Inside readAll method in business delegate");
		List<Response> responseData = responseService.readAll();
		logger.info("Exiting readAll method in business delegate");
		return responseData;
	}

	@Override
	public Response update(Response response) throws Exception {
		logger.info("Inside update method in business delegate");
		Response responseData = responseService.update(response);
		logger.info("Exiting update method in business delegate");
		return responseData;
	}

	@Override
	public List<Response> getByOpinionId(Long id) throws Exception {
		logger.info("Inside getByOpinionId method in business delegate");
		List<Response> responseData = responseService.getByOpinionId(id);
		logger.info("Exiting getByOpinionId method in business delegate");
		return responseData;
	}

	@Override
	public List<Response> getByUserId(Long id) throws Exception {
		logger.info("Inside getByUserId method in business delegate");
		List<Response> responseData = responseService.getByUserId(id);
		logger.info("Exiting getByUserId method in business delegate");
		return responseData;
	}

	@Override
	public boolean delete(Long id) throws Exception {
		logger.info("Inside delete method in business delegate");
		boolean sucess = responseService.delete(id);
		logger.info("Exiting delete method in business delegate");
		return sucess;
	}

	@Override
	public boolean delete(Response response) throws Exception {
		logger.info("Inside delete method in business delegate");
		boolean sucess = responseService.delete(response);
		logger.info("Exiting delete method in business delegate");
		return sucess;
	}

	@Override
	public boolean deleteFromDBById(Long id) throws Exception {
		logger.info("Inside deleteFromDBById method in business delegate");
		boolean sucess = responseService.deleteFromDBById(id);
		logger.info("Exiting deleteFromDBById method in business delegate");
		return sucess;
	}

	@Override
	public boolean deleteFromDB(Response response) throws Exception {
		logger.info("Inside deleteFromDB method in business delegate");
		boolean sucess = responseService.deleteFromDB(response);
		logger.info("Exiting deleteFromDB method in business delegate");
		return sucess;
	}

	/**
	 * This method is to aggregate user point
	 * 
	 * @param startTime
	 * @param endTime
	 * @throws Exception
	 */

	public List<Response> userPointsAggregations(Response response)
			throws Exception {
		logger.info("Inside deleteFromDB method in business delegate");
		String type = response.getOpinion().getType();
		Double userPoints = response.getUser().getPoints();
		String flag = response.getFlag();
		if (type == "pro") {
			if (flag == "Agree") {
				userPoints = userPoints + PRO_AGREE;
			} else {
				userPoints = userPoints + PRO_DISAGREE;
			}
		} else {
			if (flag == "Agree") {
				userPoints = userPoints + ANTI_AGREE;
			} else {
				userPoints = userPoints + ANTI_DISAGREE;
			}
		}

		List<Response> responses = responseService.userPointsAggregations(
				response, userPoints);
		logger.info("Exiting deleteFromDB method in business delegate");
		return responses;

	}

	public void userAggregation(String type, Double userPoint, String flag) {

	}
}
