package com.ohmuk.folitics.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohmuk.folitics.businessDelegate.interfaces.ILuckyDrawBusinessDelegate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.LuckyDraw;

@Controller
@RequestMapping("/luckyDraw")
public class LuckyDrawController {

	private static Logger logger = LoggerFactory
			.getLogger(LuckyDrawController.class);

	@Autowired
	private ILuckyDrawBusinessDelegate businessDelegate;

	/**
	 * This method is used to display all LuckyDraw.
	 * 
	 * @return return list of LuckyDraw
	 */

	@RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseDto<LuckyDraw> getAll() {

		logger.info("Inside getAll  method");

		try {

			List<LuckyDraw> luckyDraws = businessDelegate.readAll();

			if (luckyDraws != null) {

				logger.info("Exiting getAll method");

				return new ResponseDto<LuckyDraw>(true, luckyDraws,
						"succesfully fethed Luckydraw ");
			}

		} catch (MessageException exception) {

			logger.error("CustomException while fething all LuckyDraw",
					exception);

			return new ResponseDto<LuckyDraw>(exception.getMessage(), false);
		}

		catch (Exception exception) {

			logger.error("Exception while fething LuckyDraw", exception);

			return new ResponseDto<LuckyDraw>(exception.getMessage(), false);
		}

		return new ResponseDto<LuckyDraw>("Luckydraw not found", false);
	}

	/**
	 * This method is used to update LuckyDraw.
	 * 
	 * @param LuckyDraw
	 * @return : updated LuckyDraw
	 */

	@RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<LuckyDraw> edit(
			@Validated @RequestBody LuckyDraw luckyDraw) {

		logger.info("Inside edit method in luckyDraw controller");

		try {
			luckyDraw = businessDelegate.update(luckyDraw);

			if (luckyDraw != null) {

				logger.info("Exiting update method");

				return new ResponseDto<LuckyDraw>(true, luckyDraw,
						"succesfully updated contest");
			}
		} catch (MessageException exception) {

			logger.error("CustomException while updating luckyDraw", exception);

			return new ResponseDto<LuckyDraw>(exception.getMessage(), false);
		} catch (Exception exception) {

			logger.error("Exception while updating luckyDraw", exception);

			return new ResponseDto<LuckyDraw>(exception.getMessage(), false);
		}
		return new ResponseDto<LuckyDraw>("LuckyDraw not updated", false);
	}

	/**
	 * This method is used find LuckyDraw by id.
	 * 
	 * @param: LuckyDraw id
	 * @return : corresponding LuckyDraw
	 */

	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<LuckyDraw> find(Long id) {

		LuckyDraw luckyDraw;

		logger.info("Inside find method");

		try {
			luckyDraw = businessDelegate.read(id);

			if (luckyDraw != null) {

				logger.info("Exiting find method");

				return new ResponseDto<LuckyDraw>(true, luckyDraw,
						"luckyDraw succesfully found");
			}

		} catch (MessageException exception) {

			logger.error("CustomException while finding luckyDraw by id",
					exception);

			return new ResponseDto<LuckyDraw>(exception.getMessage(), false);

		} catch (Exception exception) {

			logger.error("Exception while finding luckyDraw by id", exception);

			return new ResponseDto<LuckyDraw>(exception.getMessage(), false);
		}

		return new ResponseDto<LuckyDraw>("luckyDraw not found", false);
	}

	/**
	 * This method is used to Hard delete LuckyDraw by id.
	 * 
	 * @param id
	 * @return LuckyDraw
	 */

	@RequestMapping(value = "/deleteFromDb", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<LuckyDraw> deleteFromDb(Long id) {
		try {
			logger.info(" Inside deleteFromDb method in controller");

			boolean success = businessDelegate.deleteFromDb(id);

			if (success == true) {

				logger.debug("LuckyDraw deleted succesfully for these id:-"
						+ id);
				logger.info(" Exiting deleteFromDb method in controller");
				return new ResponseDto<LuckyDraw>(true);
			} else {
				logger.error("something went wrong while  deleting LuckyDraw for these id:-"
						+ id);
				return new ResponseDto<LuckyDraw>(false);
			}

		} catch (MessageException exception) {
			logger.error("CustomException while  deleting LuckyDraw with id",
					exception);
			return new ResponseDto<LuckyDraw>(exception.getMessage(), false);

		}

		catch (Exception exception) {
			logger.error("Exception while  deleting LuckyDraw with id",
					exception);
			return new ResponseDto<LuckyDraw>(exception.getMessage(), false);
		}

	}

	/**
	 * This method is used to Soft delete LuckyDraw by id.
	 * 
	 * @param id
	 * @return LuckyDraw
	 */

	@RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseDto<LuckyDraw> delete(Long id) {
		try {
			logger.info(" Inside delete method in controller");

			boolean success = businessDelegate.delete(id);

			if (success == true) {

				logger.debug("LuckyDraw deleted succesfully for these id:-"
						+ id);
				logger.info(" Exiting delete method in controller");
				return new ResponseDto<LuckyDraw>(true);
			} else {
				logger.error("something went wrong while  deleting LuckyDraw for these id:-"
						+ id);
				return new ResponseDto<LuckyDraw>(false);
			}

		} catch (MessageException exception) {
			logger.error("CustomException while  deleting LuckyDraw with id",
					exception);
			return new ResponseDto<LuckyDraw>(exception.getMessage(), false);

		}

		catch (Exception exception) {
			logger.error("Exception while  deleting LuckyDraw with id",
					exception);
			return new ResponseDto<LuckyDraw>(exception.getMessage(), false);
		}

	}

	/**
	 * This method is used to display all active LuckyDraw.
	 * 
	 * @return list of active LuckyDraw
	 */

	@RequestMapping(value = "/readAllActiveLuckyDraw", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseDto<LuckyDraw> readAllActiveLuckyDraw() {

		logger.info("Inside readAllActiveLuckyDraw method");

		try {

			List<LuckyDraw> luckyDraws = businessDelegate
					.readAllActiveLuckyDraw();

			logger.info("Exiting readAllActiveLuckyDraw method");

			return new ResponseDto<LuckyDraw>(true, luckyDraws,
					"succesfully fethed active LuckyDraw");

		} catch (MessageException exception) {

			logger.error("CustomException while fething all active LuckyDraws",
					exception);

			return new ResponseDto<LuckyDraw>(exception.getMessage(), false);
		}

		catch (Exception exception) {

			logger.error("Exception while fething all active LuckyDraws",
					exception);

			return new ResponseDto<LuckyDraw>(exception.getMessage(), false);
		}

	}

}
