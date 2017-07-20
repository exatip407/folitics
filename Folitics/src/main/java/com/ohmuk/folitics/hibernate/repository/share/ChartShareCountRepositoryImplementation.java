package com.ohmuk.folitics.hibernate.repository.share;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ohmuk.folitics.hibernate.entity.share.ChartShareCount;
import com.ohmuk.folitics.hibernate.entity.share.ChartShareCountId;

/**
 * Repository implementation for entity: {@link ChartShareCount}
 * 
 * @author Harish
 *
 */

@Component
@Repository
public class ChartShareCountRepositoryImplementation implements
		IChartShareCountRepository {

	private static Logger logger = LoggerFactory
			.getLogger(ChartShareCountRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public ChartShareCount save(ChartShareCount chartShareCount) {
		logger.debug("Entered ChartShareCount save method");
		logger.debug("Saving share count for Chart id = "
				+ chartShareCount.getId().getChart().getId());

		ChartShareCountId id = (ChartShareCountId) getSession().save(
				chartShareCount);
		chartShareCount = (ChartShareCount) getSession().get(
				ChartShareCount.class, id);

		logger.debug("ChartShareCount saved. Exiting save method");
		return chartShareCount;
	}

	@Override
	public ChartShareCount update(ChartShareCount chartShareCount) {
		logger.debug("Entered ChartShareCount update method");
		logger.debug("Updating share count for Chart id = "
				+ chartShareCount.getId().getChart().getId());

		chartShareCount = (ChartShareCount) getSession().merge(chartShareCount);
		getSession().update(chartShareCount);

		chartShareCount = (ChartShareCount) getSession().get(
				ChartShareCount.class, chartShareCount.getId());

		logger.debug("Updated ChartShareCount. Exiting update method");
		return chartShareCount;
	}

	@Override
	public List<ChartShareCount> findAll() {
		logger.debug("Entered ChartShareCount findAll method");
		logger.debug("Getting all share count");

		@SuppressWarnings("unchecked")
		List<ChartShareCount> chartShareCounts = getSession().createCriteria(
				ChartShareCount.class).list();

		logger.debug("Returning all ChartShareCount. Exiting findAll method");
		return chartShareCounts;
	}

	@Override
	public ChartShareCount findByComponentId(Long chartId) {
		logger.debug("Entered ChartShareCount findByComponentId method");
		logger.debug("Getting share count for Chart id = " + chartId);

		Criteria selectCriteria = getSession().createCriteria(
				ChartShareCount.class);
		selectCriteria.add(Restrictions.eq("id.Chart.id", chartId));
		ChartShareCount chartShareCount = (ChartShareCount) selectCriteria
				.uniqueResult();

		logger.debug("Returning ChartShareCount. Exiting findByComponentId method");
		return chartShareCount;
	}

	@Override
	public ChartShareCount find(ChartShareCountId id) {

		logger.debug("Entered ChartShareCount find method");
		logger.debug("Getting share count for Chart id = "
				+ id.getChart().getId());

		ChartShareCount chartShareCount = (ChartShareCount) getSession().get(
				ChartShareCount.class, id);

		logger.debug("Returning ChartShareCount. Exiting find method");
		return chartShareCount;
	}

	@Override
	public void delete(ChartShareCountId id) {
		logger.debug("Entered ChartShareCount delete method");
		logger.debug("Deleting share count for Chart id = "
				+ id.getChart().getId());

		ChartShareCount chartShareCount = (ChartShareCount) getSession().get(
				ChartShareCount.class, id);

		logger.debug("Deleted ChartShareCount. Exiting delete method");
		getSession().delete(chartShareCount);

	}

}
