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

import com.ohmuk.folitics.hibernate.entity.comment.ResponseCommentCount;
import com.ohmuk.folitics.hibernate.entity.comment.ResponseCommentCountId;
import com.ohmuk.folitics.hibernate.entity.comment.TaskCommentCountId;

@Component
@Repository
public class ResponseCommentCountRepositoryImplementation implements
		IResponseCommentCountRepository {

	private static Logger logger = LoggerFactory
			.getLogger(ResponseCommentCountRepositoryImplementation.class);
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public ResponseCommentCount save(ResponseCommentCount responseCommentCount) {
		logger.info("Entered ResponseCommentCount save method");
		logger.debug("Saving comment count for response id = "
				+ responseCommentCount.getId().getResponse().getId());

		TaskCommentCountId id = (TaskCommentCountId) getSession().save(
				responseCommentCount);
		responseCommentCount = (ResponseCommentCount) getSession().get(
				ResponseCommentCount.class, id);

		logger.info("ResponseCommentCount saved. Exiting save method");
		return responseCommentCount;
	}

	@Override
	public ResponseCommentCount update(ResponseCommentCount responseCommentCount) {
		logger.info("Entered ResponseCommentCount update method");
		logger.debug("Updating comment count for response id = "
				+ responseCommentCount.getId().getResponse().getId());

		responseCommentCount = (ResponseCommentCount) getSession().merge(
				responseCommentCount);
		getSession().update(responseCommentCount);

		responseCommentCount = (ResponseCommentCount) getSession().get(
				ResponseCommentCount.class, responseCommentCount.getId());

		logger.info("ResponseCommentCount updated. Exiting update method");
		return responseCommentCount;
	}

	@Override
	public List<ResponseCommentCount> findAll() {
		logger.info("Entered ResponseCommentCount findAll method");

		Criteria selectAllCriteria = getSession().createCriteria(
				ResponseCommentCount.class);
		@SuppressWarnings("unchecked")
		List<ResponseCommentCount> taskCommentCounts = selectAllCriteria.list();

		logger.info("Returning all results from database. Exiting findAll method");
		return taskCommentCounts;
	}

	@Override
	public ResponseCommentCount findByComponentId(Long responseId) {
		logger.info("Entered ResponseCommentCount findByComponentId method");
		logger.debug("Finding comment count for response id = " + responseId);

		Criteria selectCriteria = getSession().createCriteria(
				ResponseCommentCount.class);
		selectCriteria.add(Restrictions.eq("id.response.id", responseId));
		ResponseCommentCount responseCommentCount = (ResponseCommentCount) selectCriteria
				.uniqueResult();

		logger.info("Returning ResponseCommentCount. Exiting findByComponentId method");
		return responseCommentCount;
	}

	@Override
	public ResponseCommentCount find(ResponseCommentCountId id) {
		logger.info("Entered ResponseCommentCount find method");
		logger.debug("Getting comment count for response id = "
				+ id.getResponse().getId());

		ResponseCommentCount responseCommentCount = (ResponseCommentCount) getSession()
				.get(ResponseCommentCount.class, id);

		logger.info("Returning result from database. Exiting update method");
		return responseCommentCount;
	}

	@Override
	public void delete(ResponseCommentCountId id) {
		logger.info("Entered ResponseCommentCount delete method");
		logger.debug("Deleting comment count for response id = "
				+ id.getResponse().getId());

		ResponseCommentCount responseCommentCount = (ResponseCommentCount) getSession()
				.get(ResponseCommentCount.class, id);

		logger.info("Deleted ResponseCommentCount. Exiting delete method");
		getSession().delete(responseCommentCount);

	}

}
