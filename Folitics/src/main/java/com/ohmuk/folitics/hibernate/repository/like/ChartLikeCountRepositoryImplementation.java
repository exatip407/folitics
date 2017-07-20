package com.ohmuk.folitics.hibernate.repository.like;

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

import com.ohmuk.folitics.hibernate.entity.like.ChartLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.ChartLikeCountId;

/**
 * Repository implementation for entity: {@link ChartLikeCount}
 * 
 * @author Harish
 *
 */

@Component
@Repository
public class ChartLikeCountRepositoryImplementation implements
		IChartCountLikeRepository {

	private static Logger logger = LoggerFactory
			.getLogger(ChartLikeCountRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public ChartLikeCount save(ChartLikeCount chartLikeCount) {
		logger.info("Entered ChartLikeCount save method");
		logger.debug("Saving like count for sentiment id = "
				+ chartLikeCount.getId().getChart().getId());

		ChartLikeCountId id = (ChartLikeCountId) getSession().save(
				chartLikeCount);
		chartLikeCount = (ChartLikeCount) getSession().get(
				ChartLikeCount.class, id);

		logger.info("ChartLikeCount saved. Exiting save method");
		return chartLikeCount;
	}

	@Override
	public ChartLikeCount update(ChartLikeCount chartLikeCount) {
		logger.info("Entered ChartLikeCount update method");
		logger.debug("Updating like count for sentiment id = "
				+ chartLikeCount.getId().getChart().getId());

		chartLikeCount = (ChartLikeCount) getSession().merge(chartLikeCount);
		getSession().update(chartLikeCount);

		chartLikeCount = (ChartLikeCount) getSession().get(
				ChartLikeCount.class, chartLikeCount.getId());

		logger.info("ChartLikeCount updated. Exiting update method");
		return chartLikeCount;
	}

	@Override
	public List<ChartLikeCount> findAll() {
		logger.info("Entered ChartLikeCount findAll method");

		Criteria selectAllCriteria = getSession().createCriteria(
				ChartLikeCount.class);
		@SuppressWarnings("unchecked")
		List<ChartLikeCount> chartLikeCounts = selectAllCriteria.list();

		logger.info("Returning all results from database. Exiting findAll method");
		return chartLikeCounts;
	}

	@Override
	public ChartLikeCount findByComponentId(Long chartId) {
		logger.info("Entered ChartLikeCount findByComponentId method");
		logger.debug("Finding like count for Chart id = " + chartId);

		Criteria selectCriteria = getSession().createCriteria(
				ChartLikeCount.class);
		selectCriteria.add(Restrictions.eq("id.chart.id", chartId));
		ChartLikeCount chartLikeCount = (ChartLikeCount) selectCriteria
				.uniqueResult();

		logger.info("Returning ChartLikeCount. Exiting findByComponentId method");
		return chartLikeCount;
	}

	@Override
	public ChartLikeCount find(ChartLikeCountId id) {
		logger.info("Entered ChartLikeCount find method");
		logger.debug("Getting like count for sentiment id = "
				+ id.getChart().getId());

		ChartLikeCount chartLikeCount = (ChartLikeCount) getSession().get(
				ChartLikeCount.class, id);

		logger.info("Returning result from database. Exiting update method");
		return chartLikeCount;
	}

	@Override
	public void delete(ChartLikeCountId id) {
		logger.info("Entered ChartLikeCount delete method");
		logger.debug("Deleting like count for sentiment id = "
				+ id.getChart().getId());

		ChartLikeCount chartLikeCount = (ChartLikeCount) getSession().get(
				ChartLikeCount.class, id);

		logger.info("Deleted ChartLikeCount. Exiting delete method");
		getSession().delete(chartLikeCount);

	}

}
