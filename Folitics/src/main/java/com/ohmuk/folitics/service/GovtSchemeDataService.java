package com.ohmuk.folitics.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.hibernate.entity.GovtSchemeData;

/**
 * 
 * @author Mayank Sharma
 *
 */
@Service
@Transactional
public class GovtSchemeDataService implements IGovtSchemeDataService {

    private static Logger logger = LoggerFactory.getLogger(GovtSchemeDataService.class);

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    @Override
    public GovtSchemeData create(GovtSchemeData govtschemedata) throws Exception {
        logger.info("Inside GovtSchemeDataService service create method");
        Long id = (Long) getSession().save(govtschemedata);
        logger.info("Exiting from GovtSchemeDataService service create method");
        return read(id);
    }

   
    @Override
    public GovtSchemeData read(Long id) throws Exception {
        logger.info("Inside GovtSchemeDataService service read method");
        GovtSchemeData govtSchemeData = (GovtSchemeData) getSession().get(GovtSchemeData.class, id);
        logger.info("Exiting from GovtSchemeDataService service read method");
        return govtSchemeData;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GovtSchemeData> readAll() throws Exception {
        logger.info("Inside GovtSchemeDataService service readAll method");
        return getSession().createCriteria(GovtSchemeData.class).addOrder(Order.desc("createTime")).list();
    }

  
    @Override
    public boolean deleteFromDB(Long id) throws Exception {
        logger.info("Inside GovtSchemeDataService service deleteFromDB method");
        GovtSchemeData govtSchemeData = read(id);
        getSession().delete(govtSchemeData);
        logger.info("Exiting from GovtSchemeDataService service deleteFromDB method");
        return true;
    }

    @Override
    public GovtSchemeData update(GovtSchemeData govtschemedata) throws Exception {
        logger.info("Inside GovtSchemeDataService service update method");
        getSession().update(govtschemedata);
        logger.info("Exiting from GovtSchemeDataService service update method");
        return govtschemedata;

    }

    @Override
    public boolean delete(Long id) throws Exception {
        logger.info("Inside GovtSchemeDataService service delete method");
        GovtSchemeData govtschemedata = (GovtSchemeData) getSession().get(GovtSchemeData.class, id);
        govtschemedata.setState(ComponentState.DELETED.getValue());
        getSession().update(govtschemedata);
        logger.info("Exiting from GovtSchemeDataService service delete method");
        return true;
    }

  
    @Override
    public boolean delete(GovtSchemeData govtschemedata) throws Exception {
        logger.info("Inside GovtSchemeDataService service delete method");
        govtschemedata.setState(ComponentState.DELETED.getValue());
        getSession().update(govtschemedata);
        logger.info("Exiting from GovtSchemeDataService service delete method");
        return true;
    }

}
