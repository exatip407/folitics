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

import com.ohmuk.folitics.hibernate.entity.like.ResponseLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.ResponseLikeCountId;
import com.ohmuk.folitics.hibernate.entity.like.SentimentLikeCount;

/**
 * Repository implementation for entity: {@link ResponseLikeCount}
 * 
 * @author Harish
 *
 */

@Component
@Repository
public class ResponseLikeCountRepositoryImplementation implements
		IResponseLikeCountRepository {

	private static Logger logger = LoggerFactory
			.getLogger(ResponseLikeCountRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public ResponseLikeCount save(ResponseLikeCount responseLikeCount) {
		logger.info("Entered ResponseLikeCount save method");
		logger.debug("Saving like count for task id = "
				+ responseLikeCount.getId().getResponse().getId());

		ResponseLikeCountId id = (ResponseLikeCountId) getSession().save(responseLikeCount);
		responseLikeCount = (ResponseLikeCount) getSession().get(
				ResponseLikeCount.class, id);

		logger.info("ResponseLikeCount saved. Exiting save method");
		return responseLikeCount;
	}

	@Override
	public ResponseLikeCount update(ResponseLikeCount responseLikeCount) {
		logger.info("Entered ResponseLikeCount update method");
		logger.debug("Updating like count for task id = "
				+ responseLikeCount.getId().getResponse().getId());

		responseLikeCount = (ResponseLikeCount) getSession().merge(responseLikeCount);
		getSession().update(responseLikeCount);

		responseLikeCount = (ResponseLikeCount) getSession().get(
				ResponseLikeCount.class, responseLikeCount.getId());

		logger.info("ResponseLikeCount updated. Exiting update method");
		return responseLikeCount;
	}

	@Override
	public List<ResponseLikeCount> findAll() {
		logger.info("Entered ResponseLikeCount findAll method");

		Criteria selectAllCriteria = getSession().createCriteria(
				ResponseLikeCount.class);
		@SuppressWarnings("unchecked")
		List<ResponseLikeCount> responseLikeCounts = selectAllCriteria.list();

		logger.info("Returning all results from database. Exiting findAll method");
		return responseLikeCounts;
	}

	@Override
	public ResponseLikeCount findByComponentId(Long responseId) {
		logger.info("Entered ResponseLikeCount findByComponentId method");
		logger.debug("Finding like count for task id = " + responseId);

		Criteria selectCriteria = getSession().createCriteria(
				ResponseLikeCount.class);
		selectCriteria.add(Restrictions.eq("id.task.id", responseId));
		ResponseLikeCount responseLikeCount = (ResponseLikeCount) selectCriteria
				.uniqueResult();

		logger.info("Returning ResponseLikeCount. Exiting findByComponentId method");
		return responseLikeCount;
	}

	@Override
	public ResponseLikeCount find(ResponseLikeCountId id) {
		logger.info("Entered ResponseLikeCount find method");
		logger.debug("Getting like count for response id = " + id.getResponse().getId());

		ResponseLikeCount responseLikeCount = (ResponseLikeCount) getSession().get(
				ResponseLikeCount.class, id);

		logger.info("Returning result from database. Exiting update method");
		return responseLikeCount;
	}

	@Override
	public void delete(ResponseLikeCountId id) {
		logger.info("Entered ResponseLikeCount delete method");
		logger.debug("Deleting like count for task id = "
				+ id.getResponse().getId());

		ResponseLikeCount responseLikeCount = (ResponseLikeCount) getSession().get(
				ResponseLikeCount.class, id);

		logger.info("Deleted ResponseLikeCount. Exiting delete method");
		getSession().delete(responseLikeCount);

	}

}
