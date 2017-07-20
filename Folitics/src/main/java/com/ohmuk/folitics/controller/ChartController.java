package com.ohmuk.folitics.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ohmuk.folitics.charting.ChartConstants;
import com.ohmuk.folitics.charting.Exception.ProcessingException;
import com.ohmuk.folitics.charting.Exception.ValidationException;
import com.ohmuk.folitics.charting.beans.ChartRequest;
import com.ohmuk.folitics.charting.beans.ChartResponse;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.service.IChartService;

/**
 * @author Sarvesh
 * 
 */
@RestController
@RequestMapping("/chart")
public class ChartController {

	@Autowired
	Map<String, IChartService> chartDirectorMap;

	private static Logger logger = LoggerFactory
			.getLogger(ChartController.class);

	/**
	 * This web service is to genrate chart data by chart name, chart meta data
	 * and indicator id
	 * 
	 * @param allRequestParams
	 * @return ResponseDto<ChartResponse>
	 */
	@RequestMapping(value = "/getChart", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<ChartResponse> getChart(
			@RequestParam Map<String, Object> allRequestParams) {

		logger.info("Inside ChartController get chart method");
		ChartResponse chartResponse = null;
		String chartID = (String) allRequestParams
				.get(ChartConstants.CHART_ID_PARAM_NAME);
		String chartSubID = (String) allRequestParams
				.get(ChartConstants.CHART_SECONDARY_ID_PARAMNAME);
		Long categoryID = Long.parseLong((String) allRequestParams.get("id"));
		IChartService chartDirector = chartDirectorMap.get(chartID);
		ChartRequest chartRequest = new ChartRequest();

		chartRequest.setChartID(chartID);
		chartRequest.setChartSubID(chartSubID);
		chartRequest.setChartParameters(allRequestParams);

		try {
			boolean validated = chartDirector.validate(chartRequest);
		} catch (ValidationException exception) {
			logger.error("Exception in validating chart request");
			logger.error("Exception: " + exception);
			return new ResponseDto<ChartResponse>(false);
		}

		try {
			chartResponse = chartDirector
					.getChartData(chartRequest, categoryID);
		} catch (ProcessingException exception) {
			logger.error("Exception in processing chart request");
			logger.error("Exception: " + exception);
			return new ResponseDto<ChartResponse>(false);
		} catch (Exception exception) {
			logger.error("Exception in genrating chart data");
			logger.error("Exception: " + exception);
			return new ResponseDto<ChartResponse>(false);
		}
		return new ResponseDto<ChartResponse>(true, chartResponse);

	}

}
