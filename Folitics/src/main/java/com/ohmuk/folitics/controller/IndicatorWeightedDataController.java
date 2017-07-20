package com.ohmuk.folitics.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohmuk.folitics.businessDelegate.interfaces.IIndicatorWeightedDataBusinessDelegate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.hibernate.entity.IndicatorWeightedData;

/**
 * 
 * @author Mayank Sharma
 *
 */
@Controller
@RequestMapping("/indicatorweighteddata")
public class IndicatorWeightedDataController {
	@Autowired
	private volatile IIndicatorWeightedDataBusinessDelegate businessDelegate;

	private static Logger logger = LoggerFactory
			.getLogger(IndicatorWeightedDataController.class);

	/**
	 * Web service is to add IndicatorWeightedData
	 * 
	 * @param indicatorweighteddata
	 * @return ResponseDto<IndicatorWeightedData>
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<IndicatorWeightedData> add(
			@RequestBody IndicatorWeightedData indicatorweighteddata) {
		logger.info("Inside IndicatorWeightedDataController add method");
		try {
			indicatorweighteddata = businessDelegate
					.create(indicatorweighteddata);
		} catch (Exception exception) {
			logger.error("Exception is adding IndicatorWeightedData");
			logger.error("Exception: " + exception);
			logger.info("Exiting from IndicatorWeightedDataController add method");
			logger.debug("IndicatorWeightedData is not saved");
			return new ResponseDto<IndicatorWeightedData>(false);
		}
		if (indicatorweighteddata != null) {
			logger.debug("IndicatorWeightedData is saved");
			logger.info("Exiting from IndicatorWeightedDataController add method");
			return new ResponseDto<IndicatorWeightedData>(true,
					indicatorweighteddata);
		}
		logger.info("Exiting from IndicatorWeightedDataController add method");
		return new ResponseDto<IndicatorWeightedData>(false);
	}

	/**
	 * Web service is to get all IndicatorWeightedData
	 * 
	 * @return ResponseDto<IndicatorWeightedData>
	 */
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<IndicatorWeightedData> getAll() {
		logger.info("Inside IndicatorWeightedDataController getAll method");
		List<IndicatorWeightedData> components = null;
		try {
			components = businessDelegate.readAll();
		} catch (Exception exception) {
			logger.error("Exception is adding IndicatorWeightedData");
			logger.error("Exception: " + exception);
			logger.info("Exiting from IndicatorWeightedDataController getAll method");
			return new ResponseDto<IndicatorWeightedData>(false);
		}
		if (components != null) {
			logger.debug("IndicatorWeightedData's is found");
			logger.info("Exiting from IndicatorWeightedDataController getAll method");
			return new ResponseDto<IndicatorWeightedData>(true, components);
		}
		logger.debug("IndicatorWeightedData's is not found");
		logger.info("Exiting from IndicatorWeightedDataController getAll method");
		return new ResponseDto<IndicatorWeightedData>(false);
	}

	/**
	 * Web service is to edit IndicatorWeightedData
	 * 
	 * @param indicatorweighteddata
	 * @return ResponseDto<IndicatorWeightedData>
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<IndicatorWeightedData> edit(
			@RequestBody IndicatorWeightedData indicatorweighteddata) {
		logger.info("Inside IndicatorWeightedDataController edit method");
		try {
			indicatorweighteddata = businessDelegate
					.update(indicatorweighteddata);
		} catch (Exception exception) {
			logger.error("Exception is adding IndicatorWeightedData");
			logger.error("Exception: " + exception);
			logger.info("Exiting from IndicatorWeightedDataController edit method");
			return new ResponseDto<IndicatorWeightedData>(false);
		}
		if (indicatorweighteddata != null) {
			logger.debug("IndicatorWeightedData is updated");
			logger.info("Exiting from IndicatorWeightedDataController edit method");
			return new ResponseDto<IndicatorWeightedData>(true,
					indicatorweighteddata);
		}
		logger.debug("IndicatorWeightedData is not updated");
		logger.info("Exiting from IndicatorWeightedDataController edit method");
		return new ResponseDto<IndicatorWeightedData>(false);
	}

	/**
	 * Web service is to delete IndicatorWeightedData by id
	 * 
	 * @param id
	 * @return ResponseDto<IndicatorWeightedData>
	 */
	@RequestMapping(value = "/deletebyid", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<IndicatorWeightedData> delete(Long id) {
		logger.info("Inside IndicatorWeightedDataController add method");
		try {
			if (businessDelegate.delete(id)) {
				logger.debug("IndicatorWeightedData is deleted");
				logger.info("Exiting from IndicatorWeightedDataController delete method");
				return new ResponseDto<IndicatorWeightedData>(true);
			}
		} catch (Exception exception) {
			logger.error("Exception is adding IndicatorWeightedData");
			logger.error("Exception: " + exception);
			logger.info("Exiting from IndicatorWeightedDataController delete method");
			return new ResponseDto<IndicatorWeightedData>(false);
		}
		logger.debug("IndicatorWeightedData is not deleted");
		logger.info("Exiting from IndicatorWeightedDataController delete method");
		return new ResponseDto<IndicatorWeightedData>(false);
	}

	/**
	 * Web service is to delete IndicatorWeightedData
	 * 
	 * @param indicatorweighteddata
	 * @return ResponseDto<IndicatorWeightedData>
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<IndicatorWeightedData> delete(
			@RequestBody IndicatorWeightedData indicatorweighteddata) {
		logger.info("Inside IndicatorWeightedDataController delete method");
		try {
			if (businessDelegate.delete(indicatorweighteddata)) {
				logger.debug("IndicatorWeightedData is deleted");
				logger.info("Exiting from IndicatorWeightedDataController delete method");
				return new ResponseDto<IndicatorWeightedData>(true);
			}
		} catch (Exception exception) {
			logger.error("Exception is adding IndicatorWeightedData");
			logger.error("Exception: " + exception);
			logger.info("Exiting from IndicatorWeightedDataController delete method");
			return new ResponseDto<IndicatorWeightedData>(false);
		}
		logger.debug("IndicatorWeightedData is not deleted");
		logger.info("Exiting from IndicatorWeightedDataController delete method");
		return new ResponseDto<IndicatorWeightedData>(false);
	}

	/**
	 * Web service is to find IndicatorWeightedData by id
	 * 
	 * @param id
	 * @return ResponseDto<IndicatorWeightedData>
	 */
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<IndicatorWeightedData> find(Long id) {
		logger.info("Inside IndicatorWeightedDataController find method");
		IndicatorWeightedData indicatorweighteddata = null;
		try {
			indicatorweighteddata = businessDelegate.read(id);
		} catch (Exception exception) {
			logger.error("Exception is adding IndicatorWeightedData");
			logger.error("Exception: " + exception);
			logger.info("Exiting from IndicatorWeightedDataController find method");
			return new ResponseDto<IndicatorWeightedData>(false);
		}
		if (indicatorweighteddata != null) {
			logger.debug("IndicatorWeightedData is found with id: "
					+ indicatorweighteddata.getId());
			logger.info("Exiting from IndicatorWeightedDataController find method");
			return new ResponseDto<IndicatorWeightedData>(true,
					indicatorweighteddata);
		}
		logger.debug("IndicatorWeightedData is not found");
		logger.info("Exiting from IndicatorWeightedDataController find method");
		return new ResponseDto<IndicatorWeightedData>(false);
	}

	/**
	 * Web service is to hard delete IndicatorWeightedData by id
	 * 
	 * @param id
	 * @return ResponseDto<IndicatorWeightedData>
	 */
	@RequestMapping(value = "/deleteFromDB", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<IndicatorWeightedData> deleteFromDB(Long id) {
		logger.info("Inside IndicatorWeightedDataController deleteFromDB method");
		try {
			if (businessDelegate.deleteFromDB(id)) {
				logger.debug("IndicatorWeightedData is hard deleted");
				logger.info("Exiting from IndicatorWeightedDataController deleteFromDB method");
				return new ResponseDto<IndicatorWeightedData>(true);
			}
		} catch (Exception exception) {
			logger.error("Exception is adding IndicatorWeightedData");
			logger.error("Exception: " + exception);
			logger.info("Exiting from IndicatorWeightedDataController deleteFromDB method");
			return new ResponseDto<IndicatorWeightedData>(false);
		}
		logger.debug("IndicatorWeightedData is not deleted");
		logger.info("Exiting from IndicatorWeightedDataController deleteFromDB method");
		return new ResponseDto<IndicatorWeightedData>(false);
	}
}
