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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohmuk.folitics.businessDelegate.interfaces.IMonetizationConfigBusinessDeligate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.MonetizationConfig;

@Controller
@RequestMapping("/monetizationConfig")
public class MonetizationConfigController {

	public static Logger logger = LoggerFactory
			.getLogger(MonetizationConfigController.class);

	@Autowired
	private IMonetizationConfigBusinessDeligate monetizationBusinessDeligate;

	/**
	 * @author
	 * @param monetization
	 * @return ResponseDto<MonetizationConfig>
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<MonetizationConfig> add( @RequestBody MonetizationConfig monetization) {

		logger.info("Inside create MonitizationController");
		try {
			monetization = monetizationBusinessDeligate.create(monetization);
			if (monetization != null){
				
				logger.info("Exiting create MonitizationController");
				
				return new ResponseDto<MonetizationConfig>(true, monetization,
						"Successfully added coupons");
			}

		} catch (MessageException e) {
			logger.error("CustomException while adding: " + e);
			return new ResponseDto<MonetizationConfig>(e.getMessage(), false);

		} catch (Exception e) {
			logger.error("Exception while adding: " + e);
			return new ResponseDto<MonetizationConfig>(e.getMessage(), false);
		}

		logger.info("Exiting create MonitizationController");

		return new ResponseDto<MonetizationConfig>(
				"Something went wrong while adding monetization", false);
	}

	/**
	 * @author
	 * @param Long
	 *            id
	 * @return ResponseDto<MonetizationConfig>
	 */
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<MonetizationConfig> read(
			@Validated @RequestParam long id) {

		logger.info("Inside read MonitizationController");
		
		try {
			MonetizationConfig monetization = monetizationBusinessDeligate
					.read(id);
			if (monetization != null){
				
				logger.info("Exiting read MonitizationController");
		
				return new ResponseDto<MonetizationConfig>(true, monetization,
						"Successfully fetched coupons");
			}

		} catch (MessageException e) {
			logger.error("CustomException while fetching: " + e);
			return new ResponseDto<MonetizationConfig>(e.getMessage(), false);

		} catch (Exception e) {
			logger.error("Exception while fetching:" + e);
			return new ResponseDto<MonetizationConfig>(e.getMessage(), false);
		}

		logger.info("Exiting read MonitizationController");

		return new ResponseDto<MonetizationConfig>(
				"Something went wrong while fatching monetization", false);
	}

	/**
	 * @author
	 * @param
	 * @return ResponseDto<MonetizationConfig>
	 */
	@RequestMapping(value = "/readAll", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<MonetizationConfig> readAll() {

		logger.info("Inside readAll MonitizationController");

		try {
			List<MonetizationConfig> monetization = monetizationBusinessDeligate
					.readAll();
			if (monetization.size()>0){
				
				logger.info("Exiting readAll MonitizationController");
				
				return new ResponseDto<MonetizationConfig>(true, monetization,
						"Successfully fetched monetization list ");
			}

		} catch (MessageException e) {
			logger.error("CustomException while adding: " + e);
			return new ResponseDto<MonetizationConfig>(e.getMessage(), false);

		} catch (Exception e) {
			logger.error("Exception while adding:" + e);
			return new ResponseDto<MonetizationConfig>(e.getMessage(), false);
		}

		return new ResponseDto<MonetizationConfig>(
				"Something went wrong while fatching list of monetization",
				false);
	}

	/**
	 * @author
	 * @param monetization
	 * @return ResponseDto<MonetizationConfig>
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<MonetizationConfig> edit(
			@Validated @RequestBody MonetizationConfig monetization) {

		logger.info("Inside edit MonitizationController");

		try {
			monetization = monetizationBusinessDeligate.update(monetization);
			if (monetization != null){
				
				logger.info("Exiting edit MonitizationController");
				
				return new ResponseDto<MonetizationConfig>(true, monetization,
						"Successfully updated monetization");
				
			}

		} catch (MessageException e) {
			logger.error("CustomException while editing: " + e);
			return new ResponseDto<MonetizationConfig>(e.getMessage(), false);

		} catch (Exception e) {
			logger.error("Exception while editing:" + e);
			return new ResponseDto<MonetizationConfig>(e.getMessage(), false);
		}

		return new ResponseDto<MonetizationConfig>(
				"Something went wrong while updating monetization", false);
	}

	/**
	 * @author
	 * @param monetization
	 * @return ResponseDto<MonetizationConfig>
	 */
	@RequestMapping(value = "/deleteById", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<MonetizationConfig> deleteById(
			@Validated @RequestParam Long id) {

		logger.info("Inside deleteById MonitizationController");

		try {
			MonetizationConfig monetization = monetizationBusinessDeligate
					.deleteById(id);
			if (monetization != null){
				
				logger.info("Exiting deleteById MonitizationController");
				
				return new ResponseDto<MonetizationConfig>(true, monetization,
						"Successfully deleted monetization by id");
		}

		} catch (MessageException e) {
			logger.error("CustomException while deleting: " + e);
			return new ResponseDto<MonetizationConfig>(e.getMessage(), false);

		} catch (Exception e) {
			logger.error("Exception while deleting :" + e);
			return new ResponseDto<MonetizationConfig>(e.getMessage(), false);
		}

		return new ResponseDto<MonetizationConfig>(
				"Something went wrong while deleting monetization by id", false);
	}

	/**
	 * @author
	 * @param monetization
	 * @return ResponseDto<MonetizationConfig>
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody ResponseDto<MonetizationConfig> delete(
			@Validated @RequestBody MonetizationConfig monetization) {

		logger.info("Inside delete MonitizationController");

		try {
			monetization = monetizationBusinessDeligate.delete(monetization);
			if (monetization == null){
				
				logger.info("Inside delete MonitizationController");
			
				return new ResponseDto<MonetizationConfig>(true, monetization,
						"Successfully deleted monetization");
			}

		} catch (MessageException e) {
			logger.error("CustomException while deleting: " + e);
			return new ResponseDto<MonetizationConfig>(e.getMessage(), false);

		} catch (Exception e) {
			logger.error("Exception while deleting:" + e);
			return new ResponseDto<MonetizationConfig>(e.getMessage(), false);
		}

		return new ResponseDto<MonetizationConfig>(
				"Something went wrong while deleting monetization", false);
	}

	/**
	 * @author
	 * @param monetization
	 * @return ResponseDto<MonetizationConfig>
	 */
	/*
	 * @RequestMapping(value="/setPointStatus", method=RequestMethod.POST)
	 * public @ResponseBody ResponseDto<MonetizationConfig> delete(@Validated
	 * @RequestBody Long id, String status){
	 * 
	 * logger.info("Inside delete MonitizationController");
	 * 
	 * try{ MonetizationConfig monetization =
	 * monetizationBusinessDeligate.setPointStatus(id, status);
	 * if(monetization!=null) return new
	 * ResponseDto<MonetizationConfig>(true,monetization,
	 * "Successfully added coupons");
	 * 
	 * }catch(MessageException e){ logger.error(
	 * "CustomException while updating status of point: "+e);
	 * 
	 * }catch(Exception e){ logger.error(
	 * "Exception while updating status of point:"+e); }
	 * 
	 * return new ResponseDto<MonetizationConfig>(
	 * "Something went wrong while searching" ,false); }
	 */

}
