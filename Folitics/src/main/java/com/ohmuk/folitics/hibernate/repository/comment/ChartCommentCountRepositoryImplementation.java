package com.ohmuk.folitics.hibernate.repository.comment;

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

import com.ohmuk.folitics.hibernate.entity.comment.ChartCommentCount;
import com.ohmuk.folitics.hibernate.entity.comment.ChartCommentCountId;

@Component
@Repository
public class ChartCommentCountRepositoryImplementation implements
		IChartCommentCountRepository {

	private static Logger logger = LoggerFactory
			.getLogger(ChartCommentCountRepositoryImplementation.class);
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public ChartCommentCount save(ChartCommentCount chartCommentCount) {
		logger.info("Entered ChartCommentCount save method");
		logger.debug("Saving comment count for chart id = "
				+ chartCommentCount.getId().getChart().getId());

		ChartCommentCountId id = (ChartCommentCountId) getSession().save(
				chartCommentCount);
		chartCommentCount = (ChartCommentCount) getSession().get(
				ChartCommentCount.class, id);

		logger.info("ChartCommentCount saved. Exiting save method");
		return chartCommentCount;
	}

	@Override
	public ChartCommentCount update(ChartCommentCount chartCommentCount) {
		
		logger.info("Entered ChartCommentCount update method");
		logger.debug("Updating comment count for chart id = "
				+ chartCommentCount.getId().getChart().getId());

		chartCommentCount = (ChartCommentCount) getSession().merge(
				chartCommentCount);
		getSession().update(chartCommentCount);

		chartCommentCount = (ChartCommentCount) getSession().get(
				ChartCommentCount.class, chartCommentCount.getId());

		logger.info("ChartCommentCount updated. Exiting update method");
		return chartCommentCount;
	}

	@Override
	public List<ChartCommentCount> findAll() {
		logger.info("Entered ChartCommentCount findAll method");

		Criteria selectAllCriteria = getSession().createCriteria(
				ChartCommentCount.class);
		@SuppressWarnings("unchecked")
		List<ChartCommentCount> chartCommentCounts = selectAllCriteria.list();

		logger.info("Returning all results from database. Exiting findAll method");
		return chartCommentCounts;
	}

	@Override
	public ChartCommentCount findByComponentId(Long chartId) {
		logger.info("Entered ChartCommentCount findByComponentId method");
		logger.debug("Finding like count for chart id = " + chartId);

		Criteria selectCriteria = getSession().createCriteria(
				ChartCommentCount.class);
		selectCriteria.add(Restrictions.eq("id.chart.id", chartId));
		ChartCommentCount chartCommentCount = (ChartCommentCount) selectCriteria
				.uniqueResult();

		logger.info("Returning ChartCommentCount. Exiting findByComponentId method");
		return chartCommentCount;
	}

	@Override
	public ChartCommentCount find(ChartCommentCountId id) {
		
		logger.info("Entered ChartCommentCount find method");
		logger.debug("Getting Comment count for chart id = " + id.getChart().getId());

		ChartCommentCount chartCommentCount = (ChartCommentCount) getSession()
				.get(ChartCommentCount.class, id);

		logger.info("Returning result from database. Exiting update method");
		return chartCommentCount;
	}

	@Override
	public void delete(ChartCommentCountId id) {
		logger.info("Entered ChartCommentCount delete method");
		logger.debug("Deleting Comment count for chart id = "
				+ id.getChart().getId());

		ChartCommentCount chartCommentCount = (ChartCommentCount) getSession()
				.get(ChartCommentCount.class, id);

		logger.info("Deleted ChartCommentCount. Exiting delete method");
		getSession().delete(chartCommentCount);

	}

}
