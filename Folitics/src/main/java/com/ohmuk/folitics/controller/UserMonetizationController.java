package com.ohmuk.folitics.controller;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohmuk.folitics.businessDelegate.interfaces.IUserMonetizationBusinessDeligate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.UserMonetization;

@Controller
@RequestMapping("/userMonetization")
public class UserMonetizationController {

	@Autowired
	private IUserMonetizationBusinessDeligate userMonetizationBusinessDeligate;

	static final Logger logger = LoggerFactory
			.getLogger(UserMonetizationController.class);

	/**
	 * Method to add action configuration in entity: momnetizationConfig
	 * 
	 * @author
	 * @param monetizationDataBean
	 * @return ResponseDto<UserMonetization>
	 */
	@RequestMapping(value = "/addAction", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<UserMonetization> addAction(
			@RequestBody UserMonetization userMonetization) {

		logger.info("Inside addAction UserMonetizationController");

		try {
			if (userMonetization == null) {
				logger.error("userMonetization found to be null");
				throw (new MessageException("userMonetization can't be null"));
			}

			userMonetization = userMonetizationBusinessDeligate
					.addAction(userMonetization);

			if (userMonetization != null) {

				logger.info("Exiting addAction UserMonetizationController");

				return new ResponseDto<UserMonetization>(true,
						userMonetization,
						" Successfully added userMonetization");
			}
		} catch (MessageException e) {
			logger.error("CustomException while adding userMonetization " + e);
			return new ResponseDto<UserMonetization>(e.getMessage(), false);

		} catch (Exception e) {
			logger.error("Exception while adding userMonetization " + e);
			return new ResponseDto<UserMonetization>(e.getMessage(), false);

		}

		return new ResponseDto<UserMonetization>(
				"Something went wrong while adding userMonetization", false);

	}

	/**
	 * Method to get configuration from entity: c
	 * 
	 * @author
	 * @param Long
	 *            id
	 * @return ResponseDto<UserMonetization>
	 */
	@RequestMapping(value = "/getById", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<UserMonetization> getById(Long id) {

		logger.info("Inside getById UserMonetizationController");

		try {
			if (id == null) {
				logger.error("Monetization Id can't be null");
				throw (new MessageException("Monetization Id found to be null"));
			}

			UserMonetization userMonetization = userMonetizationBusinessDeligate
					.getById(id);

			if (userMonetization != null) {

				logger.info("Exiting getById UserMonetizationController");

				return new ResponseDto<UserMonetization>(true,
						userMonetization,
						"Successfully fetched monetization record by monetization id");
			}

		} catch (MessageException e) {
			logger.error("CustomException while fatching record of monetization by id:"
					+ e);
			return new ResponseDto<UserMonetization>(e.getMessage(), false);

		} catch (Exception e) {
			logger.error("Exception while fatching record of monetization by id:"
					+ e);
			return new ResponseDto<UserMonetization>(e.getMessage(), false);
		}

		return new ResponseDto<UserMonetization>(
				"Something went wrong while fetching monetization record by monetization id",
				false);

	}

	/**
	 * @author
	 * @param Long
	 *            id
	 * @return ResponseDto<UserMonetization>
	 */
	@RequestMapping(value = "/getByUserId", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<UserMonetization> getByUserId(Long id) {

		logger.info("Inside getByUserId UserMonetizationController");

		try {
			if (id == null) {
				logger.error("User id can't be null");
				throw (new MessageException("User id found to be null"));
			}

			List<UserMonetization> userMonetization = userMonetizationBusinessDeligate
					.getByUserId(id);

			if (userMonetization.size() > 0) {

				logger.info("Inside getByUserId UserMonetizationController");

				return new ResponseDto<UserMonetization>(true,
						userMonetization,
						"Successfully fetched monetization record by user id");
			}

		} catch (MessageException e) {
			logger.error("CustomException while fatching record of monetization by user id:"
					+ e);
			return new ResponseDto<UserMonetization>(e.getMessage(), false);

		} catch (Exception e) {
			logger.error("Exception while fatching record of monetization by user id:"
					+ e);
			return new ResponseDto<UserMonetization>(e.getMessage(), false);
		}

		return new ResponseDto<UserMonetization>(
				"Something went wrong while fetching monetization records by user id",
				false);

	}

	/**
	 * @author
	 * @param String
	 *            startDate, String endDate
	 * @return ResponseDto<UserMonetization>
	 */
	@RequestMapping(value = "/getByDate", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<UserMonetization> getByDate(
			@RequestParam Timestamp startDate, @RequestParam Timestamp endDate) {

		logger.info("Inside getByDate UserMonetizationController");

		try {
			if (startDate == null) {
				logger.error("Start Date can't be null");
				throw (new MessageException("Start Date found to be null"));
			}

			if (endDate == null) {
				logger.error("End Date can't be null");
				throw (new MessageException("End Date found to be null"));
			}

			List<UserMonetization> userMonetization = userMonetizationBusinessDeligate
					.getByDate(startDate, endDate);

			logger.info("Exiting getByDate UserMonetizationController");

			return new ResponseDto<UserMonetization>(true, userMonetization,
					"Successfully fetched monetization record by date");

		} catch (MessageException e) {
			logger.error("CustomException while fetching record of monetization by date:"
					+ e);
			return new ResponseDto<UserMonetization>(e.getMessage(), false);

		} catch (Exception e) {
			logger.error("Exception while fatching record of monetization by date:"
					+ e);
			return new ResponseDto<UserMonetization>(e.getMessage(), false);
		}

	}

}
