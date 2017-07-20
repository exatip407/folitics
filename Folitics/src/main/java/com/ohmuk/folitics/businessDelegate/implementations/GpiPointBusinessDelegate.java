package com.ohmuk.folitics.businessDelegate.implementations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.businessDelegate.interfaces.IGpiPointBusinessDelegate;
import com.ohmuk.folitics.enums.OpinionType;
import com.ohmuk.folitics.enums.ResponseType;
import com.ohmuk.folitics.hibernate.entity.EventReportScore;
import com.ohmuk.folitics.hibernate.entity.GPIPoint;
import com.ohmuk.folitics.hibernate.entity.IndicatorData;
import com.ohmuk.folitics.hibernate.entity.Opinion;
import com.ohmuk.folitics.hibernate.entity.Response;
import com.ohmuk.folitics.service.AntiProValue;
import com.ohmuk.folitics.service.IGPIPointService;

@Component
public class GpiPointBusinessDelegate implements IGpiPointBusinessDelegate {

	private static Logger logger = LoggerFactory.getLogger(GpiPointBusinessDelegate.class);

	@Autowired
	private volatile IGPIPointService gpiPointService;

	public static int opinionWeightage = 5;
	public static int responseWeightage = 1;

	@Override
	public GPIPoint create(GPIPoint gpiPoint) throws Exception {
		logger.info("Inside create method in business delegate");
		GPIPoint gpiPointData = gpiPointService.create(gpiPoint);
		logger.info("Exiting create method in business delegate");
		return gpiPointData;
	}

	@Override
	public GPIPoint read(Long id) throws Exception {
		logger.info("Inside getGPIPointById method in business delegate");
		GPIPoint gpiPointData = gpiPointService.read(id);
		logger.info("Exiting getGPIPointById method in business delegate");
		return gpiPointData;
	}

	@Override
	public List<GPIPoint> readAll() throws Exception {
		logger.info("Inside readAll method in business delegate");
		List<GPIPoint> gpiPointData = gpiPointService.readAll();
		logger.info("Exiting readAll method in business delegate");
		return gpiPointData;
	}

	@Override
	public GPIPoint update(GPIPoint gpiPoint) throws Exception {
		logger.info("Inside update method in business delegate");
		GPIPoint gpiPointData = gpiPointService.update(gpiPoint);
		logger.info("Exiting update method in business delegate");
		return gpiPointData;
	}

	@Override
	public boolean hardDelete(Long id) throws Exception {
		logger.info("Inside deleteFromDBById method in business delegate");
		boolean sucess = gpiPointService.hardDelete(id);
		logger.info("Exiting deleteFromDBById method in business delegate");
		return sucess;
	}

	@Override
	public boolean delete(GPIPoint gpiPoint) throws Exception {
		logger.info("Inside delete method in business delegate");
		boolean sucess = gpiPointService.delete(gpiPoint);
		logger.info("Exiting delete method in business delegate");
		return sucess;
	}

	@Override
	public List<GPIPoint> getALLGPIpoints() throws Exception {
		logger.info("Inside getALLGPIpoints method in business delegate");
		List<GPIPoint> gpiPoints = gpiPointService.getALLGPIpoints();
		logger.info("Exiting getALLGPIpoints method in business delegate");
		return gpiPoints;
	}

	@Override
	public boolean delete(Long id) throws Exception {
		logger.info("Inside delete method in business delegate");
		boolean sucess = gpiPointService.delete(id);
		logger.info("Exiting delete method in business delegate");
		return sucess;
	}

	/**
	 * This method is to aggregate reportEvent
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	private Long reportEventAggregation(Timestamp startTime, Timestamp endTime) throws Exception {
		logger.info("Inside reportEventAggregation method in business delegate");
		Long score = 0l;
		List<EventReportScore> evetnReportScoreList = gpiPointService.reportEventAggregation(startTime, endTime);
		for (EventReportScore eventReportScore : evetnReportScoreList) {
			score += eventReportScore.getScore();
		}
		logger.info("Exiting reportEventAggregation method in business delegate");
		return score;
	}

	/**
	 * This method is to aggregate gpi point
	 * 
	 * @param startTime
	 * @param endTime
	 * @throws Exception
	 */

	public void gpiPointsAggregations(Timestamp startTime, Timestamp endTime) throws Exception {
		logger.info("Inside GPIPointService gpiPointsAggregations method");
		AntiProValue antiProValue = opinionAggregations(startTime, endTime);
		Double indicatorData = indicatorChangeValueAggregation(startTime, endTime);
		Long score = reportEventAggregation(startTime, endTime);
		double aggregatedValue = antiProValue.favour - antiProValue.favour + indicatorData + score;
	}

	/**
	 * This method is to aggregate indicator value by time
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	private Double indicatorChangeValueAggregation(Timestamp startTime, Timestamp endTime) throws Exception {
		logger.info("Inside indicatorChangeValueAggregation method in business delegate");

		List<IndicatorData> indicatorDataList = gpiPointService.indicatorChangeValueAggregation(startTime, endTime);
		double score = 0.0;
		for (IndicatorData indicatorData : indicatorDataList) {
			score += indicatorData.getWeightedValue();
		}
		logger.info("Exiting indicatorChangeValueAggregation method in business delegate");
		return score;
	}

	/**
	 * This method is to aggregate opinion
	 * 
	 * @param startTime
	 * @param endTime
	 * @return AntiProValue
	 * @throws Exception
	 */
	private AntiProValue opinionAggregations(Timestamp startTime, Timestamp endTime) throws Exception {
		logger.info("Inside  opinionAggregations method in business delegate");
		List<Opinion> opinions = new ArrayList<Opinion>();
		long favour = 0;
		long against = 0;
		AntiProValue antiProValue = new AntiProValue();

		opinions = getOpinions(startTime, endTime);

		for (Opinion opinion : opinions) {
			int opinionType = 0;
			if (opinion.getType().equals(OpinionType.PROGOVT.getValue())) {
				opinionType = 1;
				antiProValue.favour += opinionWeightage;
			} else {
				opinionType = -1;
				antiProValue.against += opinionWeightage;
			}

			if (opinion.getResponses() != null) {
				for (Response response : opinion.getResponses()) {

					opinionResponseAggregations(response, startTime, endTime, antiProValue, opinionType);

				}
			}
		}
		logger.info("Exiting from  opinionAggregations method in business delegate");
		return antiProValue;
	}

	/**
	 * This mehtod is to aggregate opinion response
	 * 
	 * @param response
	 * @param startTime
	 * @param endTime
	 * @param antiProValue
	 * @param opinionType
	 * @return
	 * @throws Exception
	 */
	private boolean opinionResponseAggregations(Response response, Timestamp startTime, Timestamp endTime,
			AntiProValue antiProValue, int opinionType) throws Exception {
		logger.info("Inside  opinionResponseAggregations method in business delegate");
		int localOpinionType = -1;

		// Logic to create opinion type
		// If parent is pro
		// Response is disagree
		// then the localOpinion become anti
		// Response is agree
		// then the localOpinion becomes Pro
		// If parent is anti
		// Response is agree
		// then the localOpinion become anti
		// Response is disagree
		// then the localOpinion becomes pro

		if (response.getFlag().equals(ResponseType.AGREE.getValue())) {
			if (opinionType == -1) {
				localOpinionType = -1;

			} else if (opinionType == 1) {
				localOpinionType = 1;

			}
		} else if (response.getFlag().equals(ResponseType.DISAGREE.getValue())) {
			if (opinionType == -1) {
				localOpinionType = 1;

			} else if (opinionType == 1) {
				localOpinionType = -1;

			}
		}

		// if the response is created during the timestamp in consideration then
		// only take the responsevalue into consideration
		if (response.getTimestamp() != null && response.getTimestamp().after(startTime)
				&& response.getTimestamp().before(startTime)) {
			if (localOpinionType == -1) {
				antiProValue.against += responseWeightage;

			} else

			{
				antiProValue.favour += responseWeightage;
			}

		}
		if (response.getResponses() != null) {
			for (Response responseResponse : response.getResponses()) {
				opinionResponseAggregations(responseResponse, startTime, endTime, antiProValue, localOpinionType);
			}
		}
		logger.info("Exiting from  opinionResponseAggregations method in business delegate");
		return true;
	}

	/**
	 * This method is to get opinion by timestamp
	 * 
	 * @param startTime
	 * @param endTime
	 * @return List<Opinion>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List<Opinion> getOpinions(Timestamp startTime, Timestamp endTime) throws Exception {
		logger.info("Inside  getOpinions method  in business delegate");
		List<Opinion> listOfOpinions = new ArrayList<Opinion>();
		Opinion opinion = new Opinion();
		opinion.setType(OpinionType.PROGOVT.getValue());
		List<Response> listResponse = new ArrayList<Response>();
		List<Response> listResponse1 = new ArrayList<Response>();

		Response response = new Response();
		response.setFlag(ResponseType.AGREE.getValue());
		listResponse.add(response);

		response = new Response();
		response.setFlag(ResponseType.AGREE.getValue());

		Response response1 = new Response();
		response1.setFlag(ResponseType.DISAGREE.getValue());
		listResponse1.add(response1);

		response1 = new Response();
		response1.setFlag(ResponseType.AGREE.getValue());
		listResponse1.add(response1);

		response.setResponses(listResponse1);
		listResponse.add(response);

		response = new Response();
		response.setFlag(ResponseType.AGREE.getValue());

		opinion.setResponses(listResponse);
		listOfOpinions.add(opinion);

		listOfOpinions = gpiPointService.getOpinions(startTime, endTime);

		logger.info("Exiting from  getOpinions method  in business delegate");
		return listOfOpinions;
	}

}
