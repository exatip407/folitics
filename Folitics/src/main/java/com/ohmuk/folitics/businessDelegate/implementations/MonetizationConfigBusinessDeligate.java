package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.businessDelegate.interfaces.IMonetizationConfigBusinessDeligate;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.MonetizationConfig;
import com.ohmuk.folitics.service.IMonetizationConfigService;

@Component
public class MonetizationConfigBusinessDeligate implements IMonetizationConfigBusinessDeligate{
	
	public static Logger logger = LoggerFactory.getLogger(MonetizationConfigBusinessDeligate.class);
	
	@Autowired
	private IMonetizationConfigService monetizationService;


	@Override
	public MonetizationConfig create(MonetizationConfig monetization) throws MessageException, Exception {
		
		logger.info("Inside create MonetizationConfigBusinessDeligate ");
		
		monetization =  monetizationService.create(monetization);
		
		logger.debug("MonetizationConfig added: ", monetization);
		logger.info("Exiting create MonetizationConfigBusinessDeligate");
		
		return monetization;
	}

	@Override
	public MonetizationConfig read(Long id) throws MessageException, Exception {
		
		logger.info("Inside read MonetizationConfigBusinessDeligate ");
		
		MonetizationConfig monetization =  monetizationService.read(id);
		
		logger.debug("MonetizationConfig fatched: ", monetization);
		logger.info("Exiting read MonetizationConfigBusinessDeligate");
		
		return monetization;
	}


	@Override
	public List<MonetizationConfig> readAll() throws MessageException, Exception {
		
		logger.info("Inside readAll MonetizationConfigBusinessDeligate ");
		
		List<MonetizationConfig> monetization =  monetizationService.readAll();
		
		logger.debug("MonetizationConfig fatched: ", monetization);
		logger.info("Exiting readAll MonetizationConfigBusinessDeligate");
		
		return monetization;
	}

	@Override
	public MonetizationConfig update(MonetizationConfig monetization) throws MessageException, Exception {
		
		logger.info("Inside update MonetizationConfigBusinessDeligate ");
		
		monetization =  monetizationService.update(monetization);
		
		logger.debug("MonetizationConfig updated: ", monetization);
		logger.info("Exiting update MonetizationConfigBusinessDeligate");
		
		return monetization;
		
	}

	@Override
	public MonetizationConfig deleteById(Long id) throws MessageException, Exception {
		
		logger.info("Inside deleteById MonetizationConfigBusinessDeligate ");
		
		MonetizationConfig monetization =  monetizationService.deleteById(id);
		
		logger.info("Exiting deleteById MonetizationConfigBusinessDeligate");
		
		return monetization;
	}


	@Override
	public MonetizationConfig delete(MonetizationConfig monetization) throws MessageException, Exception {
		
		logger.info("Inside delete MonetizationConfigBusinessDeligate ");
		
		monetization =  monetizationService.delete(monetization);
		
		logger.debug("MonetizationConfig deleted: ", monetization);
		logger.info("Exiting delete MonetizationConfigBusinessDeligate");
		
		return monetization;
	}

	/*@Override
	public MonetizationConfig setPointStatus(Long id, String status) throws MessageException, Exception{
		
		logger.info("Inside setPointStatus MonetizationConfigBusinessDeligate ");
		
		MonetizationConfig monetization = monetizationService.setPointStatus(id, status);
		
		logger.debug("MonetizationConfig after updating point status: ", monetization);
		logger.info("Exiting setPointStatus MonetizationConfigBusinessDeligate");
		
		return null;
	}*/
	
}
