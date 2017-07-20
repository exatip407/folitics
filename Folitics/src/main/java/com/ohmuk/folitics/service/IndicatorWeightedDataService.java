package com.ohmuk.folitics.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.hibernate.entity.Category;
import com.ohmuk.folitics.hibernate.entity.IndicatorWeightedData;
import com.ohmuk.folitics.util.DateUtils;

/**
 * 
 * @author Mayank Sharma
 * 
 */
@Service
@Transactional
public class IndicatorWeightedDataService implements
		IIndicatorWeightedDataService {

	private static Logger logger = LoggerFactory
			.getLogger(IndicatorWeightedDataService.class);

	@Autowired
	private SessionFactory _sessionFactory;

	private Session getSession() {
		return _sessionFactory.getCurrentSession();
	}

	/**
	 * Method is to add IndicatorWeightedData by calling
	 * 
	 * @param indicatorweighteddata
	 * @return IndicatorWeightedData
	 * @throws Exception
	 */
	@Override
	public IndicatorWeightedData create(
			IndicatorWeightedData indicatorweighteddata) throws Exception {
		logger.info("Inside IndicatorWeightedDataService create method");
		Long id = (Long) getSession().save(indicatorweighteddata);
		logger.info("Exiting from IndicatorWeightedDataService create method");
		return read(id);
	}

	/**
	 * Method is to get IndicatorWeightedData by id
	 * 
	 * @param id
	 * @return IndicatorWeightedData
	 * @throws Exception
	 */
	@Override
	public IndicatorWeightedData read(Long id) throws Exception {
		logger.info("Inside IndicatorWeightedDataService read method");
		logger.info("Exiting from IndicatorWeightedDataService read method");
		return (IndicatorWeightedData) getSession().get(
				IndicatorWeightedData.class, id);
	}

	/**
	 * Method is to get all IndicatorWeightedData by calling
	 * 
	 * @return IndicatorWeightedData
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<IndicatorWeightedData> readAll() throws Exception {
		logger.info("Inside IndicatorWeightedDataService readAll method");
		logger.info("Exiting from IndicatorWeightedDataService readAll method");
		return getSession().createCriteria(IndicatorWeightedData.class).list();
	}

	/**
	 * Method is to hard delete IndicatorWeightedData by id
	 * 
	 * @param id
	 * @return boolean
	 * @throws Exception
	 */
	@Override
	public boolean deleteFromDB(Long id) throws Exception {
		logger.info("Inside IndicatorWeightedDataService deleteFromDB method");
		IndicatorWeightedData indicatorWeightedData = read(id);
		getSession().delete(indicatorWeightedData);
		logger.info("Exiting from IndicatorWeightedDataService deleteFromDB method");
		return true;
	}

	/**
	 * Method is to update IndicatorWeightedData by calling
	 * 
	 * @param indicatorweighteddata
	 * @return IndicatorWeightedData
	 * @throws Exception
	 */
	@Override
	public IndicatorWeightedData update(
			IndicatorWeightedData indicatorweighteddata) throws Exception {
		logger.info("Inside IndicatorWeightedDataService update method");
		indicatorweighteddata.setEditTime(DateUtils.getSqlTimeStamp());
		getSession().update(indicatorweighteddata);
		logger.info("Exiting from IndicatorWeightedDataService update method");
		return indicatorweighteddata;

	}

	/**
	 * Method is to soft delete IndicatorWeightedData by id
	 * 
	 * @param id
	 * @return boolean
	 * @throws Exception
	 */
	@Override
	public boolean delete(Long id) throws Exception {
		logger.info("Inside IndicatorWeightedDataService delete method");
		IndicatorWeightedData indicatorWeightedData = read(id);
		indicatorWeightedData.setState(ComponentState.DELETED.getValue());
		getSession().update(indicatorWeightedData);
		logger.info("Exiting from IndicatorWeightedDataService delete method");
		return true;
	}

	/**
	 * Method is to soft delete IndicatorWeightedData by calling
	 * 
	 * @param indicatorweighteddata
	 * @return boolean
	 * @throws Exception
	 */
	@Override
	public boolean delete(IndicatorWeightedData indicatorweighteddata)
			throws Exception {
		logger.info("Inside IndicatorWeightedDataService delete method");
		indicatorweighteddata.setState(ComponentState.DELETED.getValue());
		getSession().update(indicatorweighteddata);
		logger.info("Exiting from IndicatorWeightedDataService delete method");
		return true;
	}

	/**
	 * Method to find {@link IndicatorWeightedData} by CategoryLatest
	 * 
	 * @author Mayank Sharma
	 * @param category
	 * @return {@link IndicatorWeightedData}
	 */
	@Override
	public IndicatorWeightedData findByCategoryLatest(Category category)
			throws Exception {
		ProjectionList proj = Projections.projectionList();
		proj.add(Projections.max("id"));
		DetachedCriteria latestIndicatorId = DetachedCriteria
				.forClass(IndicatorWeightedData.class).setProjection(proj)
				.add(Restrictions.eq("category", category));

		Criteria crit = getSession().createCriteria(
				IndicatorWeightedData.class, "fl");
		crit.add(Property.forName("fl.category").eq(category));
		crit.add(Restrictions.eq("category", category));
		List<IndicatorWeightedData> l = crit.list();
		System.out.println(l.get(0).getId());
		return l.get(0);
	}

}
