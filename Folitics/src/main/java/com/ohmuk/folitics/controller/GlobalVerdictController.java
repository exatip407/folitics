package com.ohmuk.folitics.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohmuk.folitics.businessDelegate.interfaces.IGlobalVerdictBusinessDelegate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.hibernate.entity.verdict.global.GlobalVerdict;

/**
 * Controller for Verdict module
 * 
 * @author Abhishek
 *
 */
@Controller
@RequestMapping("/globalVerdict")
public class GlobalVerdictController {

	final static Logger logger = Logger
			.getLogger(GlobalVerdictController.class);

	@Autowired
	private IGlobalVerdictBusinessDelegate businessDelegate;

	@RequestMapping
	public String getVerdictPage() {
		return "globalVerdict-page";
	}

	/**
	 * Spring web service(GET) to get {@link GlobalVerdict} object from database
	 * 
	 * @author Abhishek
	 * @return 
	 *         com.ohmuk.folitics.dto.ResponseDto<com.ohmuk.folitics.hibernate.entity
	 *         .verdict.global.GlobalVerdict>
	 */
	@RequestMapping(value = "/getGlobalVerdict", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<GlobalVerdict> getGlobalVerdict() {

		logger.debug("Entered GlobalVerdictController getGlobalVerdict method");

		GlobalVerdict globalVerdict = businessDelegate.read();

		if (globalVerdict != null) {

			return new ResponseDto<GlobalVerdict>(true, globalVerdict);
		} else {

			return new ResponseDto<GlobalVerdict>(
					"GlobalVerdict object found null", false);
		}
	}
}
