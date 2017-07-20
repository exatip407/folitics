package com.ohmuk.folitics.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.hibernate.entity.Category;
import com.ohmuk.folitics.hibernate.entity.Component;

/**
 * @author Abhishek
 *
 */
@Service
@Transactional
public class ComponentService implements IComponentService {

    private static Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    @Override
    public Component create(Component component) throws Exception {
        logger.info("Inside component service create method");
        Long id  = (Long) getSession().save(component);
        logger.info("Exiting from component service create method");
        return read(id);
    }

    @Override
    public Component read(Long id) throws Exception {
        logger.info("Inside component service read method ");
        Component component = (Component) getSession().get(Category.class, id);
        logger.info("Exiting from component service read method");
        return component;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Component> readAll() throws Exception {
        logger.info("Inside component service read all method");
        Criteria criteria = getSession().createCriteria(Category.class);
        logger.info("Exiting component service read all method");
        return criteria.list();
    }

  
    @Override
    public Component update(Component component) throws Exception {
        logger.info("Inside component service ");
        getSession().update(component);
        Component UpdatedComponent = read(component.getId());
        logger.info("Exiting from component service ");
        return UpdatedComponent;
    }
  
    @Override
    public boolean delete(Long id) throws Exception{
        logger.info("Inside component service delete method: ");
        Component component = read(id);
        getSession().delete(component);
        logger.debug("Exiting from component service delete method");
        return true;
    }
  
    @Override
    public boolean delete(Component component) throws Exception{
        logger.info("Inside component service controller delete method");
        delete(component.getId());
        logger.info("Exiting from component service delete method");
        return true;
    }
}
