package com.ohmuk.folitics.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohmuk.folitics.businessDelegate.interfaces.IResponseBusinessDelegate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.enums.ResponseType;
import com.ohmuk.folitics.hibernate.entity.Response;
import com.ohmuk.folitics.util.DateUtils;

/**
 * @author Abhishek
 *
 */
@Controller
@RequestMapping("/response")
public class ResponseController {

	@Autowired
	private volatile IResponseBusinessDelegate businessDelegate;

	private static Logger logger = LoggerFactory.getLogger(ResponseController.class);

	@RequestMapping
	public String getResponsePage() {
		return "response-page";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Response> getAdd() {
		List<Response> responses = new ArrayList<>();
		responses.add(getTestResponse());
		return new ResponseDto<Response>(true, responses);
	}

	/**
	 * Web service is to add {@link Response}
	 * 
	 * @param response
	 * @return {@link Response}
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<Response> add(@RequestBody Response response) {
		logger.info("Inside ResponseController add method");
		try {
			response.setEdited(DateUtils.getSqlTimeStamp());
			response.setTimestamp(DateUtils.getSqlTimeStamp());
			response = businessDelegate.create(response);
		} catch (Exception exception) {
			logger.error("Exception in adding response");
			logger.error("Exception: " + exception);
			logger.info("Exiting from ResponseController add method");
			return new ResponseDto<Response>(false);
		}
		if (response != null) {
			logger.debug("Response is save");
			logger.info("Exiting from ResponseController add method");
			return new ResponseDto<Response>(true, response);
		}
		logger.debug("Response is not saved");
		logger.info("Exiting from ResponseController add method");
		return new ResponseDto<Response>(false);
	}

	/**
	 * Web service is to update {@link Response}
	 * 
	 * @param response
	 * @return {@link Response}
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<Response> edit(@RequestBody Response response) {
		logger.info("Inside ResponseController edit method");
		try {
			response = businessDelegate.update(response);
		} catch (Exception exception) {
			logger.error("Exception in updating response");
			logger.error("Exception: " + exception);
			logger.info("Exiting from ResponseController edit method");
			return new ResponseDto<Response>(false);
		}
		if (response != null) {
			logger.debug("Response with id: " + response.getId() + " is updated");
			logger.info("Exiting from ResponseController edit method");
			return new ResponseDto<Response>(true, response);
		}
		logger.debug("Response is not update");
		logger.info("Exiting from ResponseController edit method");
		return new ResponseDto<Response>(false);
	}

	/**
	 * Web service is to get response{@link Response} by opinionId
	 * 
	 * @param id
	 * @return {@link Response}
	 */
	@RequestMapping(value = "/getByOpinionId", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<Response> getByOpinionId(Long id) {
		logger.info("Inside ResponseController getByOpinionId method");
		try {
			List<Response> responses = businessDelegate.getByOpinionId(id);
			if (responses != null) {
				logger.debug("Response with id: " + id + " has been found");
				logger.info("Exiting from ResponseController getByOpinionId method");
				return new ResponseDto<Response>(true,responses,"Successfully fetched the records");
			}
		} catch (Exception exception) {
			logger.error("Exception while fetching response by opinionId");
			logger.error("Exception: " + exception);
			logger.info("Exiting from ResponseController getByOpinionId method");
			return new ResponseDto<Response>(false);
		}
		logger.debug("Response with id: " + id + " has not found");
		logger.info("Exiting from ResponseController getByOpinionId method");
		return new ResponseDto<Response>(false);
	}
	
	/**
	 * Web service is to get response{@link Response} by userId
	 * 
	 * @param id
	 * @return {@link Response}
	 */
	@RequestMapping(value = "/getByUserId", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Response> getByUserId(Long id) {
		logger.info("Inside ResponseController getByTaskId method");
		try {
			List<Response> responses = businessDelegate.getByUserId(id);
			if (responses != null) {
				logger.debug("Response with id: " + id + " has been found");
				logger.info("Exiting from ResponseController getByUserId method");
				return new ResponseDto<Response>(true,responses,"Successfully fetched the records");
			}
		} catch (Exception exception) {
			logger.error("Exception while fetching response by userId");
			logger.error("Exception: " + exception);
			logger.info("Exiting from ResponseController getByUserId method");
			return new ResponseDto<Response>(false);
		}
		logger.debug("Response with id: " + id + " has not found");
		logger.info("Exiting from ResponseController getByUserId method");
		return new ResponseDto<Response>(false);
	}

	/**
	 * Web service is to soft delete {@link Response} by id
	 * 
	 * @param id
	 * @return {@link Response}
	 */
	@RequestMapping(value = "/deleteById", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<Response> deleteById(Long id) {
		logger.info("Inside ResponseController add method");
		try {
			if (businessDelegate.delete(id)) {
				logger.debug("Response with id: " + id + " is soft deleted");
				logger.info("Exiting from ResponseController deleteById method");
				return new ResponseDto<Response>(true);
			}
		} catch (Exception exception) {
			logger.error("Exception in soft delete response by id");
			logger.error("Exception: " + exception);
			logger.info("Exiting from ResponseController deleteById method");
			return new ResponseDto<Response>(false);
		}
		logger.debug("Response with id: " + id + " is not delete");
		logger.info("Exiting from ResponseController deleteById method");
		return new ResponseDto<Response>(false);
	}

	/**
	 * Web service is to soft deletd {@link Response}
	 * 
	 * @param response
	 * @return {@link Response}
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<Response> delete(@RequestBody Response response) {
		logger.info("Inside ResponseController add method");
		try {
			if (businessDelegate.delete(response)) {
				logger.debug("Response with id: " + response.getId() + " is deleted");
				logger.info("Exiting from ResponseController delete method");
				return new ResponseDto<Response>(true);
			}
		} catch (Exception exception) {
			logger.error("Exception in soft response");
			logger.error("Exception: " + exception);
			logger.info("Exiting from ResponseController delete method");
			return new ResponseDto<Response>(false);
		}
		logger.debug("Response is not delete");
		logger.info("Exiting from ResponseController delete method");
		return new ResponseDto<Response>(false);
	}

	/**
	 * Web service is to hard delete {@link Response} by id
	 * 
	 * @param id
	 * @return {@link Response}
	 */
	@RequestMapping(value = "/deleteFromDBByid", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Response> deleteFromDB(Long id) {
		logger.info("Inside ResponseController add method");
		try {
			if (businessDelegate.deleteFromDBById(id)) {
				logger.debug("Response with id: " + id + " is hard deleted");
				logger.info("Exiting from ResponseController deleteFromDB method");
				return new ResponseDto<Response>(true);
			}
		} catch (Exception exception) {
			logger.error("Exception in hard delete response by id");
			logger.error("Exception: " + exception);
			logger.info("Exiting from ResponseController deleteFromDB method");
			return new ResponseDto<Response>(false);
		}
		logger.debug("Response is not delete");
		logger.info("Exiting from ResponseController deleteFromDB method");
		return new ResponseDto<Response>(false);
	}

	/**
	 * Web businessDelegate is to hard delete {@link Response}
	 * 
	 * @param response
	 * @return {@link Response}
	 */
	@RequestMapping(value = "/deleteFromDB", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<Response> deleteFromDB(@RequestBody Response response) {
		logger.info("Inside ResponseController add method");
		try {
			if (businessDelegate.deleteFromDB(response)) {
				logger.debug("Response with id: " + response.getId() + " is hard deleted");
				logger.info("Exiting from ResponseController deleteFromDB method");
				return new ResponseDto<Response>(true);
			}
		} catch (Exception exception) {
			logger.error("Exception in hard delete response");
			logger.error("Exception: " + exception);
			logger.info("Exiting from ResponseController deleteFromDB method");
			return new ResponseDto<Response>(false);
		}
		logger.debug("Response is not delete");
		logger.info("Exiting from ResponseController deleteFromDB method");
		return new ResponseDto<Response>(false);
	}

	/**
	 * Web service is to get all {@link Response}
	 * 
	 * @return {@link Response}
	 */
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Response> getAll() {
		logger.info("Inside ResponseController getAll method");
		List<Response> responses = null;
		try {
			responses = businessDelegate.readAll();
		} catch (Exception exception) {
			logger.error("Exception in getting all response");
			logger.error("Exception: " + exception);
			logger.info("Exiting from ResponseController getAll method");
			return new ResponseDto<Response>(false);
		}
		if (responses != null) {
			logger.debug(responses.size() + " response is found");
			logger.info("Exiting from ResponseController getAll method");
			return new ResponseDto<Response>(true, responses);
		}
		logger.debug("No response is found");
		logger.info("Exiting from ResponseController getAll method");
		return new ResponseDto<Response>(false);
	}

	/**
	 * Web service is to get {@link Response} by id
	 * 
	 * @param id
	 * @return {@link Response}
	 */
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Response> find(Long id) {
		logger.info("Inside ResponseController find method");
		Response response = null;
		try {
			response = businessDelegate.getResponseById(id);
		} catch (Exception exception) {
			logger.error("Exception in finding response");
			logger.error("Exception: " + exception);
			logger.info("Exiting from ResponseController find method");
			return new ResponseDto<Response>(false);
		}
		if (response != null) {
			logger.debug("Response is found with id: " + id);
			logger.info("Exiting from ResponseController find method");
			return new ResponseDto<Response>(true, response);
		}
		logger.debug("Response with id: " + id + " is not found");
		logger.info("Exiting from ResponseController find method");
		return new ResponseDto<Response>(false);
	}

	@RequestMapping(value = "/getTestResponse", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Response getTestResponse() {
		return getDummyResponse();

	}

	private Response getDummyResponse() {
		Response response = new Response();
		// response.setId((new Random()).nextLong());
		// response.setSubject("Response Subject");
		// response.setText("Response Text");
		response.setFlag(ResponseType.AGREE.getValue());
		return response;
	}

}
