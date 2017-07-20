package com.ohmuk.folitics.businessDelegate.implementations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.businessDelegate.interfaces.IComponentBusinessDelegate;
import com.ohmuk.folitics.service.IComponentService;

@Component
public class ComponentBusinessDelegate implements IComponentBusinessDelegate {

    private static Logger logger = LoggerFactory.getLogger(ComponentBusinessDelegate.class);

    @Autowired
    private volatile IComponentService componentService;

    @Override
    public com.ohmuk.folitics.hibernate.entity.Component create(com.ohmuk.folitics.hibernate.entity.Component component)
            throws Exception {
        logger.info("Inside create method in business delegate");
        com.ohmuk.folitics.hibernate.entity.Component componentData = componentService.create(component);
        logger.info("Exiting create method in business delegate");
        return (com.ohmuk.folitics.hibernate.entity.Component) componentData;
    }

    @Override
    public com.ohmuk.folitics.hibernate.entity.Component read(Long id) throws Exception {
        logger.info("Inside read method in business delegate");
        com.ohmuk.folitics.hibernate.entity.Component componentData = componentService.read(id);
        logger.info("Exiting read method in business delegate");
        return componentData;
    }

    @Override
    public List<com.ohmuk.folitics.hibernate.entity.Component> readAll() throws Exception {
        logger.info("Inside readAll method in business delegate");
        List<com.ohmuk.folitics.hibernate.entity.Component> componentData = componentService.readAll();
        logger.info("Exiting readAll method in business delegate");
        return componentData;
    }

    @Override
    public com.ohmuk.folitics.hibernate.entity.Component update(com.ohmuk.folitics.hibernate.entity.Component component)
            throws Exception {
        logger.info("Inside update method in business delegate");
        com.ohmuk.folitics.hibernate.entity.Component componentData = componentService.update(component);
        logger.info("Exiting update method in business delegate");
        return componentData;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        logger.info("Inside soft delete by id method in business delegate");
        boolean sucess = componentService.delete(id);
        logger.info("Exiting hard delete by id method in business delegate");
        return sucess;
    }

    @Override
    public boolean delete(com.ohmuk.folitics.hibernate.entity.Component component) throws Exception {
        logger.info("Inside soft delete by object method in business delegate");
        boolean sucess = componentService.delete(component);
        logger.info("Exiting hard delete by object method in business delegate");
        return sucess;
    }

}
