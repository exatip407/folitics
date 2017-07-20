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
import com.ohmuk.folitics.hibernate.entity.IndicatorIdealValueData;
import com.ohmuk.folitics.util.DateUtils;

@Service
@Transactional
public class IndicatorIdealValueDataService implements IIndicatorIdealValueDataService {

    private static Logger logger = LoggerFactory.getLogger(IndicatorMetaDataService.class);

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    /**
     * Method is to add {@link IndicatorIdealValueData}
     * @param indicatoridealvaluedata
     * @return {@link IndicatorIdealValueData}
     * @throws Exception
     */
    @Override
    public IndicatorIdealValueData create(IndicatorIdealValueData indicatoridealvaluedata) throws Exception {
        logger.info("Inside IndicatorIdealValueDataService create method");
        Long id = (Long) getSession().save(indicatoridealvaluedata);
        logger.info("Exiting from IndicatorIdealValueDataService create method");
        return read(id);
    }

    /**
     * Method is to get {@link IndicatorIdealValueData} by id
     * @param id
     * @return {@link IndicatorIdealValueData}
     * @throws Exception
     */
    @Override
    public IndicatorIdealValueData read(Long id) throws Exception {
        logger.info("Inside IndicatorIdealValueDataService read method");
        logger.info("Exiting from IndicatorIdealValueDataService read method");
        return (IndicatorIdealValueData) getSession().get(IndicatorIdealValueData.class, id);
    }

    /**
     * Method to get all {@link IndicatorIdealValueData}
     * @return {@link List < IndicatorIdealValueData > }
     * @throws Exception
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<IndicatorIdealValueData> readAll() throws Exception {
        logger.info("Inside IndicatorIdealValueDataService readAll method");
        logger.info("Exiting from IndicatorIdealValueDataService readAll method");
        return getSession().createCriteria(IndicatorIdealValueData.class).list();
    }

    /**
     * Method is to update {@link IndicatorIdealValueData}
     * @param indicatoridealvaluedata
     * @return {@link IndicatorIdealValueData}
     * @throws Exception
     */
    @Override
    public IndicatorIdealValueData update(IndicatorIdealValueData indicatorIdealValueData) throws Exception {
        logger.info("Inside IndicatorIdealValueDataService update method");
        indicatorIdealValueData.setEditTime(DateUtils.getSqlTimeStamp());
        getSession().update(indicatorIdealValueData);
        logger.info("Exiting from IndicatorIdealValueDataService update method");
        return indicatorIdealValueData;
    }

    /**
     * Method is to soft delete {@link IndicatorIdealValueData} by id
     * @param id
     * @return boolean
     * @throws Exception
     */
    @Override
    public boolean delete(Long id) throws Exception {
        logger.info("Inside IndicatorIdealValueDataService delete method");
        IndicatorIdealValueData indicatorIdealValueData = read(id);
        indicatorIdealValueData.setState(ComponentState.DELETED.getValue());
        getSession().update(indicatorIdealValueData);
        logger.info("Exiting from IndicatorIdealValueDataService delete method");
        return true;

    }

    /**
     * Method is to soft delete {@link IndicatorIdealValueData}
     * @param indicatoridealvaluedata
     * @return boolean
     * @throws Exception
     */
    @Override
    public boolean delete(IndicatorIdealValueData indicatorIdealValueData) throws Exception {
        logger.info("Inside IndicatorIdealValueDataService delete method");
        indicatorIdealValueData = read(indicatorIdealValueData.getId());
        indicatorIdealValueData.setState(ComponentState.DELETED.getValue());
        getSession().update(indicatorIdealValueData);
        logger.info("Exiting from IndicatorIdealValueDataService delete method");
        return true;
    }

    /**
     * Method is to hard delete {@link IndicatorIdealValueData} by id
     * @param id
     * @return boolean
     * @throws Exception
     */
    @Override
    public boolean deleteFromDBById(Long id) throws Exception {
        logger.info("Inside IndicatorIdealValueDataService deleteFromDBById method");
        IndicatorIdealValueData indicatorIdealValueData = read(id);
        getSession().delete(indicatorIdealValueData);
        logger.info("Exiting from IndicatorIdealValueDataService deleteFromDBById method");
        return false;
    }

    /**
     * Method is to hard delete {@link IndicatorIdealValueData}
     * @param indicatoridealvaluedata
     * @return boolean
     * @throws Exception
     */
    @Override
    public boolean deleteFromDB(IndicatorIdealValueData indicatoridealvaluedata) throws Exception {
        logger.info("Inside IndicatorIdealValueDataService deleteFromDB method");
        indicatoridealvaluedata = read(indicatoridealvaluedata.getId());
        getSession().delete(indicatoridealvaluedata);
        logger.info("Exiting from IndicatorIdealValueDataService deleteFromDB method");
        return false;
    }

}
