package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ohmuk.folitics.businessDelegate.interfaces.IComponentModuleBusinessDeligate;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.ComponentModuleStorage;
import com.ohmuk.folitics.jpa.repository.module.IComponentModuleStorageRepository;
import com.ohmuk.folitics.service.IComponentService;
import com.ohmuk.folitics.service.module.like.IComponentModuleService;

public class ComponentModuleBusinessDeligate implements IComponentModuleBusinessDeligate{

	final static Logger logger = LoggerFactory.getLogger(ComponentModuleBusinessDeligate.class);

	@Autowired
	private IComponentModuleService componentModuleService;
	
	@Override
	public ComponentModuleStorage read(Long id) throws MessageException {

		logger.info("Inside read ComponentModuleBusinessDeligate");

		ComponentModuleStorage attachment = componentModuleService.read(id);

		logger.info("Exiting read ComponentModuleBusinessDeligate");

		return attachment;
	}

	@Override
	public List<ComponentModuleStorage> readAll() throws MessageException {

		logger.info("Inside readAll ComponentModuleBusinessDeligate");

		List<ComponentModuleStorage> attachment = componentModuleService.readAll();

		logger.info("Exiting readAll ComponentModuleBusinessDeligate");

		return attachment;
	}

}
