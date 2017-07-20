package com.ohmuk.folitics.service.module.like;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.hibernate.entity.ComponentModuleStorage;

@Service
@Transactional
public class ComponentModuleService implements IComponentModuleService {
	
	final static Logger logger = LoggerFactory.getLogger(ComponentModuleStorage.class);

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

    @Override
    public ComponentModuleStorage read(Long id) {
    	
    	logger.info("Inside read ComponentModuleStorage");

		ComponentModuleStorage componentModuleStorage = (ComponentModuleStorage) getSession().get(ComponentModuleService.class, id);

		logger.info("Existing from ComponentModuleStorage read method");

		return componentModuleStorage;

    }

    @Override
    public List<ComponentModuleStorage> readAll() {
    	
    	logger.info("Inside ComponentModuleStorage readAll method");

		List<ComponentModuleStorage> componentModuleStorages = getSession().createCriteria(ComponentModuleStorage.class).list();

		logger.info("Existing from FeedChannelService readAll method");

		return componentModuleStorages;

    }

}
