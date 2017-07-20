package com.ohmuk.folitics.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.hibernate.entity.Category;
import com.ohmuk.folitics.hibernate.entity.IndicatorThreshold;
import com.ohmuk.folitics.util.DateUtils;

/**
 * 
 * @author Mayank Sharma
 * 
 */
@Service
@Transactional
public class IndicatorThresholdService implements IIndicatorThresholdService {

	private static Logger logger = LoggerFactory
			.getLogger(IndicatorThresholdService.class);

	@Autowired
	private SessionFactory _sessionFactory;

	private Session getSession() {
		return _sessionFactory.getCurrentSession();
	}

	/**
	 * This method is used to add IndicatorThreshold by calling
	 * 
	 * @author Mayank Sharma
	 * @return IndicatorThreshold
	 * @throws Exception
	 */
	@Override
	public IndicatorThreshold create(IndicatorThreshold indicatorthreshold)
			throws Exception {
		logger.info("Inside IndicatorThresholdService create method");
		Long id = (Long) getSession().save(indicatorthreshold);
		logger.info("Exiting from IndicatorThresholdService create method");
		return read(id);
	}

	/**
	 * This method is to get IndicatorThreshold by id
	 * 
	 * @author Mayank Sharma
	 * @return IndicatorThreshold
	 * @throws Exception
	 */
	@Override
	public IndicatorThreshold read(Long id) throws Exception {
		logger.info("Inside IndicatorThresholdService read method");
		return (IndicatorThreshold) getSession().get(IndicatorThreshold.class,
				id);
	}

	/**
	 * This method is to get all IndicatorThreshold
	 * 
	 * @author Mayank Sharma
	 * @return IndicatorThreshold
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<IndicatorThreshold> readAll() throws Exception {
		logger.info("Inside IndicatorThresholdService create method");
		return getSession().createCriteria(IndicatorThreshold.class).list();
	}

	/**
	 * This method is to update IndicatorThreshold by calling
	 * 
	 * @author Mayank Sharma
	 * @return IndicatorThreshold
	 * @throws Exception
	 */
	@Override
	public IndicatorThreshold update(IndicatorThreshold indicatorthreshold)
			throws Exception {
		logger.info("Inside IndicatorThresholdService update method");
		indicatorthreshold.setEditTime(DateUtils.getSqlTimeStamp());
		getSession().update(indicatorthreshold);
		logger.info("Exiting from IndicatorThresholdService update method");
		return indicatorthreshold;
	}

	/**
	 * This method is to soft delete IndicatorThreshold
	 * 
	 * @author Mayank Sharma
	 * @return IndicatorThreshold
	 * @throws Exception
	 */
	@Override
	public boolean delete(Long id) throws Exception {
		logger.info("Inside IndicatorThresholdService delete(Long id) method");
		IndicatorThreshold indicatorThreshold = (IndicatorThreshold) getSession()
				.get(IndicatorThreshold.class, id);
		indicatorThreshold.setState(ComponentState.DELETED.getValue());
		getSession().update(indicatorThreshold);
		logger.info("Exiting from IndicatorThresholdService delete(Long id) method");
		return true;

	}

	/**
	 * This method is to hard delete IndicatorThreshold by id
	 * 
	 * @author Mayank Sharma
	 * @return IndicatorThreshold
	 * @throws Exception
	 */
	@Override
	public boolean deleteFromDB(Long id) throws Exception {
		logger.info("Inside IndicatorThresholdService deleteFromDB(Long id) method");
		IndicatorThreshold indicatorThreshold = read(id);
		getSession().delete(indicatorThreshold);
		logger.info("Exiting from IndicatorThresholdService deleteFromDB(Long id) method");
		return true;
	}

	/**
	 * This method is to soft delete IndicatorThreshold
	 * 
	 * @author Mayank Sharma
	 * @return IndicatorThreshold
	 * @throws Exception
	 */
	@Override
	public boolean delete(IndicatorThreshold indicatorthreshold)
			throws Exception {
		logger.info("Inside IndicatorThresholdService delete method");
		indicatorthreshold.setState(ComponentState.DELETED.getValue());
		getSession().update(indicatorthreshold);
		logger.info("Exiting from IndicatorThresholdService delete method");
		return true;
	}

	/**
	 * Method is to find {@link IndicatorThreshold} by latest {@link Category}
	 * 
	 * @param category
	 * @return {@link IndicatorThreshold}
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<IndicatorThreshold> findByCategoryLatest(Category category) {

		SQLQuery query = getSession()
				.createSQLQuery(
						"select * from indicatorthreshold  where id in (select max(id) from indicatorthreshold where indicatorId = "
								+ category.getId()
								+ " group by threshHoldCategory)");
		query.addEntity(IndicatorThreshold.class);
		List<IndicatorThreshold> indicatorThresholds = query.list();


		return indicatorThresholds;
	}
	
	

	/**
	 * Method is to find {@link IndicatorThreshold} by latest {@link Category}
	 * 
	 * @param category
	 * @return {@link IndicatorThreshold}
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<IndicatorThreshold> findByCategory(Long categoryId) {

		SQLQuery query = getSession()
				.createSQLQuery(
						"select * from indicatorthreshold where indicatorId ="+categoryId+" order by editTime");
		query.addEntity(IndicatorThreshold.class);
		List<IndicatorThreshold> indicatorThresholds = query.list();


		return indicatorThresholds;
	}

}
