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

import com.ohmuk.folitics.hibernate.entity.comment.GovtSchemeDataCommentCount;
import com.ohmuk.folitics.hibernate.entity.comment.GovtSchemeDataCommentCountId;
import com.ohmuk.folitics.hibernate.entity.like.SentimentLikeCount;

@Component
@Repository
public class GovtSchemeDataCommentCountRepositoryImplementation implements
		IGovtSchemeDataCommentCountRepository {

	private static Logger logger = LoggerFactory
			.getLogger(GovtSchemeDataCommentCountRepositoryImplementation.class);
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public GovtSchemeDataCommentCount save(
			GovtSchemeDataCommentCount govtSchemeDataCommentCount) {
		logger.info("Entered GovtSchemeDataCommentCount save method");
		logger.debug("Saving comment count for task id = "
				+ govtSchemeDataCommentCount.getId().getGovtSchemeData()
						.getId());

		GovtSchemeDataCommentCountId id = (GovtSchemeDataCommentCountId) getSession()
				.save(govtSchemeDataCommentCount);
		govtSchemeDataCommentCount = (GovtSchemeDataCommentCount) getSession()
				.get(GovtSchemeDataCommentCount.class, id);

		logger.info("GovtSchemeDataCommentCount saved. Exiting save method");
		return govtSchemeDataCommentCount;
	}

	@Override
	public GovtSchemeDataCommentCount update(
			GovtSchemeDataCommentCount govtSchemeDataCommentCount) {
		logger.info("Entered GovtSchemeDataCommentCount update method");
		logger.debug("Updating comment count for task id = "
				+ govtSchemeDataCommentCount.getId().getGovtSchemeData()
						.getId());

		govtSchemeDataCommentCount = (GovtSchemeDataCommentCount) getSession()
				.merge(govtSchemeDataCommentCount);
		getSession().update(govtSchemeDataCommentCount);

		govtSchemeDataCommentCount = (GovtSchemeDataCommentCount) getSession()
				.get(GovtSchemeDataCommentCount.class,
						govtSchemeDataCommentCount.getId());

		logger.info("GovtSchemeDataCommentCount updated. Exiting update method");
		return govtSchemeDataCommentCount;
	}

	@Override
	public List<GovtSchemeDataCommentCount> findAll() {
		logger.info("Entered GovtSchemeDataCommentCount findAll method");

		Criteria selectAllCriteria = getSession().createCriteria(
				SentimentLikeCount.class);
		@SuppressWarnings("unchecked")
		List<GovtSchemeDataCommentCount> schemesCommentCounts = selectAllCriteria
				.list();

		logger.info("Returning all results from database. Exiting findAll method");
		return schemesCommentCounts;
	}

	@Override
	public GovtSchemeDataCommentCount findByComponentId(Long SchemeId) {
		logger.info("Entered GovtSchemeDataCommentCount findByComponentId method");
		logger.debug("Finding like count for govtSchemeData id = " + SchemeId);

		Criteria selectCriteria = getSession().createCriteria(
				GovtSchemeDataCommentCount.class);
		selectCriteria.add(Restrictions.eq("id.govtSchemeData.id", SchemeId));
		GovtSchemeDataCommentCount govtSchemeDataCommentCount = (GovtSchemeDataCommentCount) selectCriteria
				.uniqueResult();

		logger.info("Returning GovtSchemeDataCommentCount. Exiting findByComponentId method");
		return govtSchemeDataCommentCount;
	}

	@Override
	public GovtSchemeDataCommentCount find(GovtSchemeDataCommentCountId id) {
		logger.info("Entered GovtSchemeDataCommentCount find method");
		logger.debug("Getting Comment count for Scheme id = "
				+ id.getGovtSchemeData().getId());

		GovtSchemeDataCommentCount govtSchemeDataCommentCount = (GovtSchemeDataCommentCount) getSession()
				.get(GovtSchemeDataCommentCount.class, id);

		logger.info("Returning result from database. Exiting update method");
		return govtSchemeDataCommentCount;
	}

	@Override
	public void delete(GovtSchemeDataCommentCountId id) {
		logger.info("Entered GovtSchemeDataCommentCount delete method");
		logger.debug("Deleting Comment count for Scheme id = "
				+ id.getGovtSchemeData().getId());

		GovtSchemeDataCommentCount govtSchemeDataCommentCount = (GovtSchemeDataCommentCount) getSession()
				.get(GovtSchemeDataCommentCount.class, id);

		logger.info("Deleted GovtSchemeDataCommentCount. Exiting delete method");
		getSession().delete(govtSchemeDataCommentCount);

	}

}
