package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.businessDelegate.interfaces.IStatelookupBusinessDeligate;
import com.ohmuk.folitics.hibernate.entity.Statelookup;
import com.ohmuk.folitics.service.IStatelookupService;
@Component
@Transactional
public class StatelookupBusinessDelegate implements
		IStatelookupBusinessDeligate {
	
	

	private static Logger logger = LoggerFactory
			.getLogger(StatelookupBusinessDelegate.class);

	@Autowired
	private volatile IStatelookupService statelookupService;

	public List<Statelookup>search(String state) throws Exception {
		logger.info("Inside search method in business delegate");
		List<Statelookup> statelookup = statelookupService.search(state);

		logger.info("Exiting search method in business delegate");
		return statelookup;
	}

	
}
