package com.ohmuk.folitics.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohmuk.folitics.businessDelegate.interfaces.IStatelookupBusinessDeligate;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.hibernate.entity.Statelookup;

@Controller
@RequestMapping("/statelookup")
public class StatelookupController {
	@Autowired
	private static Logger logger = LoggerFactory
			.getLogger(StatelookupController.class);

	@RequestMapping
	public String getStateLookupPage() {
		return "StateLookup-Page";
	}

	@Autowired
	private volatile IStatelookupBusinessDeligate businessDelegate;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody ResponseDto<Statelookup> search(String state) {
		logger.info("Inside StatelookupController search method");
		List<Statelookup>statelookup;
		try {
			statelookup = businessDelegate.search(state);
		} catch (Exception exception) {
			logger.error("Exception in reading search statelookup: ");
			logger.error("Exception: " + exception);
			logger.info("Exiting from StatelookupController search method");
			return new ResponseDto<Statelookup>(false);
		}
		if (statelookup != null) {
			return new ResponseDto<Statelookup>(true, statelookup);
		}
		logger.debug("No State found");
		logger.info("Exiting from StatelookupController add method");
		return new ResponseDto<Statelookup>(false);
	}

}
