package com.ohmuk.folitics.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Category;
import com.ohmuk.folitics.hibernate.entity.IndicatorData;
import com.ohmuk.folitics.util.DateUtils;

@Service
@Transactional
public class IndicatorDataService implements IIndicatorDataService {

    @Autowired
    IIndicatorThresholdService indicatorThresholdRepository;

    @Autowired
    IIndicatorWeightedDataService indicatorWeightedDataRepository;

    @Autowired
    ICategoryService categoryRepository;

    private static Logger logger = LoggerFactory.getLogger(IndicatorDataService.class);

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    /**
     * Method is to add {@link IndicatorData}
     * @param IndicatorData
     * @throws MessageException
     */
    @Override
    public IndicatorData create(IndicatorData indicatordata) throws MessageException {

        Long indicatorDataId = (Long) getSession().save(indicatordata);
        logger.debug("indicatordata is save of id: " + indicatorDataId);
        logger.info("Exiting from indicatorDataService create method");
        return (IndicatorData) getSession().get(IndicatorData.class, indicatorDataId);
    }

    /**
     * Method is to get {@link IndicatorData} by id
     * @param id
     * @return {@link IndicatorData}
     */
    @Override
    public IndicatorData read(Long id) throws Exception {
        logger.info("Inside IndicatorDataService create method");
        IndicatorData indicatorData = (IndicatorData) getSession().get(IndicatorData.class, id);
        return indicatorData;
    }

    /**
     * Method is to hard delete {@link IndicatorData} by id
     * @param id
     * @return {@link IndicatorData}
     */
    @Override
    public boolean deleteFromDB(Long id) throws Exception {
        logger.info("Inside IndicatorDataService create method");
        IndicatorData indicatorData = (IndicatorData) getSession().get(IndicatorData.class, id);
        getSession().delete(indicatorData);
        return true;
    }

    /**
     * Method is to get all {@link IndicatorData}
     * @return List< {@link Category} >
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<IndicatorData> readAll() throws Exception {
        logger.info("Inside IndicatorDataService create method");
        return getSession().createCriteria(IndicatorData.class).list();
    }

    /**
     * Method is to update {@link IndicatorData}
     * @return boolean
     */
    @Override
    public IndicatorData update(IndicatorData indicatordata) throws Exception {
        logger.info("Inside IndicatorDataService create method");
        indicatordata.setEditTime(DateUtils.getSqlTimeStamp());
        getSession().update(indicatordata);
        return indicatordata;
    }

    /**
     * Method is soft delete {@link IndicatorData} by id
     * @param id
     * @return boolean
     * @throws Exception
     */
    @Override
    public boolean delete(Long id) throws Exception {
        logger.info("Inside IndicatorDataService create method");
        IndicatorData indicatorData = (IndicatorData) getSession().get(IndicatorData.class, id);
        indicatorData.setState(ComponentState.DELETED.getValue());
        getSession().update(indicatorData);
        return true;

    }

    /**
     * Method is to soft delete {@link IndicatorData}
     * @param indicatordata
     * @return boolean
     * @throws Exception
     */
    @Override
    public boolean delete(IndicatorData indicatorData) throws Exception {
        logger.info("Inside IndicatorDataService create method");
        indicatorData.setState(ComponentState.DELETED.getValue());
        getSession().update(indicatorData);
        return true;
    }

    /**
     * Method is to find {@link IndicatorData} by latest {@link Category}
     * @param category
     * @return IndicatorData
     */
    @Override
    public IndicatorData findByCategoryLatest(Category category) {
        ProjectionList proj = Projections.projectionList();
        proj.add(Projections.max("id"));
        DetachedCriteria latestIndicatorId = DetachedCriteria.forClass(IndicatorData.class).setProjection(proj)
                .add(Restrictions.eq("category", category));

        Criteria crit = getSession().createCriteria(IndicatorData.class, "fl");
        crit.add(Property.forName("fl.category").eq(category));
        crit.add(Restrictions.eq("category", category));
        List<IndicatorData> l = crit.list();
        System.out.println(l.get(0).getId());
        return l.get(0);
    }

    /**
     * Method is to get all {@link IndicatorData} order by recently added
     * @return List<IndicatorData>
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<IndicatorData> findAllIndicatorDataLatest() {
        SQLQuery query = getSession()
                .createSQLQuery(
                        "Select * from IndicatorData where id in (select max(id) from IndicatorData group by indicatorId) order by editTime");
        query.addEntity(IndicatorData.class);
        List<IndicatorData> indicatorDataList = query.list();

        return indicatorDataList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<IndicatorData> findIndicatorDataForVerdict() {
        SQLQuery query = getSession()
                .createSQLQuery(
                        "SELECT * FROM   IndicatorData s WHERE( SELECT  COUNT(*) FROM IndicatorData  f WHERE f.indicatorId = s.indicatorId AND   f.id >= s.id  ) <= 2 order by indicatorId");
        query.addEntity(IndicatorData.class);
        List<IndicatorData> indicatorDataList = query.list();

        return indicatorDataList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<IndicatorData> findByCategory(Long categoryId) {
        SQLQuery query = getSession().createSQLQuery(
                "select * from IndicatorData where indicatorId ="+categoryId+" order by editTime");
        query.addEntity(IndicatorData.class);
        List<IndicatorData> indicatorDataList = query.list();

        return indicatorDataList;
    }

}
