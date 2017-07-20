package com.ohmuk.folitics.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.hibernate.entity.IndicatorMetaData;
import com.ohmuk.folitics.util.DateUtils;

@Service
@Transactional
public class IndicatorMetaDataService implements IIndicatorMetaDataService {

    private static Logger logger = LoggerFactory.getLogger(IndicatorMetaDataService.class);

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    /**
     * Method is to add {@link IndicatorMetaData}
     * @param indicatormetadata
     * @return {@link IndicatorMetaData}
     * @throws Exception
     */
    @Override
    public IndicatorMetaData create(IndicatorMetaData indicatorMetaData) throws Exception {
        logger.info("Indside IndicatorMetaDataService create method");
        Long id = (Long) getSession().save(indicatorMetaData);
        logger.info("Exiting from IndicatorMetaDataService create method");
        return read(id);
    }

    /**
     * Method is to get {@link IndicatorMetaData} by id
     * @param id
     * @return {@link IndicatorMetaData}
     * @throws Exception
     */
    @Override
    public IndicatorMetaData read(Long id) throws Exception {
        logger.info("Indside IndicatorMetaDataService read method");
        logger.info("Exiting from IndicatorMetaDataService read method");
        return (IndicatorMetaData) getSession().get(IndicatorMetaData.class, id);
    }

    /**
     * Method is to get all {@link IndicatorMetaData}
     * @return {@link List< IndicatorMetaData >}
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<IndicatorMetaData> readAll() throws Exception {
        logger.info("Indside IndicatorMetaDataService readAll method");
        logger.info("Exiting from IndicatorMetaDataService readAll method");
        return getSession().createCriteria(IndicatorMetaData.class).list();
    }

    /**
     * Method is to update {@link IndicatorMetaData}
     * @param indicatormetadata
     * @return {@link IndicatorMetaData}
     * @throws Exception
     */
    @Override
    public IndicatorMetaData update(IndicatorMetaData indicatorMetaData) throws Exception {
        logger.info("Indside IndicatorMetaDataService update method");
        indicatorMetaData.setEditTime(DateUtils.getSqlTimeStamp());
        getSession().update(indicatorMetaData);
        logger.info("Exiting from IndicatorMetaDataService update method");
        return indicatorMetaData;
    }

    /**
     * Method is to delete {@link IndicatorMetaData} by id
     * @param id
     * @return boolean
     * @throws Exception
     */
    @Override
    public boolean delete(Long id) throws Exception {
        logger.info("Indside IndicatorMetaDataService delete method");
        IndicatorMetaData indicatorMetaData = read(id);
        indicatorMetaData.setState(ComponentState.DELETED.getValue());
        getSession().update(indicatorMetaData);
        logger.info("Exiting from IndicatorMetaDataService delete method");
        return true;
    }

    /**
     * Method is to delete {@link IndicatorMetaData}
     * @param indicatormetadata
     * @return boolean
     * @throws Exception
     */
    @Override
    public boolean delete(IndicatorMetaData indicatorMetaData) throws Exception {
        logger.info("Indside IndicatorMetaDataService delete method");
        indicatorMetaData.setState(ComponentState.DELETED.getValue());
        getSession().update(indicatorMetaData);
        logger.info("Exiting from IndicatorMetaDataService delete method");
        return true;
    }

    /**
     * Method is to hard delete {@link IndicatorMetaData}
     * @author Mayank Sharma
     * @param indicatorMetaData
     * @return boolean
     * @throws Exception
     */
    @Override
    public boolean deleteFromDB(IndicatorMetaData indicatorMetaData) throws Exception {
        logger.info("Indside IndicatorMetaDataService deleteFromDB method");
        deleteFromDBById(indicatorMetaData.getId());
        logger.info("Exiting from IndicatorMetaDataService deleteFromDB method");
        return true;
    }

    /**
     * Method is to hard delete {@link IndicatorMetaData} by id
     * @author Mayank Sharma
     * @param id
     * @return boolean
     * @throws Exception
     */
    @Override
    public boolean deleteFromDBById(Long id) throws Exception {
        logger.info("Indside IndicatorMetaDataService deleteFromDBById method");
        IndicatorMetaData indicatorMetaData = read(id);
        getSession().delete(indicatorMetaData);
        logger.info("Exiting from IndicatorMetaDataService deleteFromDBById method");
        return true;
    }
}
