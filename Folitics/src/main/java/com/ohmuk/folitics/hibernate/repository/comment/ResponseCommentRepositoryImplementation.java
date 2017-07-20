package com.ohmuk.folitics.hibernate.repository.comment;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ohmuk.folitics.beans.CommentBean;
import com.ohmuk.folitics.businessDelegate.interfaces.IUserMonetizationBusinessDeligate;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Module;
import com.ohmuk.folitics.hibernate.entity.UserMonetization;
import com.ohmuk.folitics.hibernate.entity.comment.ResponseComment;

/**
 * Repository implementation for entity: {@link ResponseComment}
 * 
 * @author Harish
 *
 */

@Component
@Repository
public class ResponseCommentRepositoryImplementation implements
		IResponseCommentRepository {

	private static Logger logger = LoggerFactory
			.getLogger(ResponseCommentRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private volatile IUserMonetizationBusinessDeligate userMonetizationBussinessDeligate;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public ResponseComment save(ResponseComment responseComment) {
		logger.info("Entered ResponseComment save method");
		logger.debug("Saving Comment for task id = "
				+ responseComment.getComponentId() + " and user id = "
				+ responseComment.getUserId());

		Long id = (Long) getSession().save(responseComment);
		responseComment = (ResponseComment) getSession().get(
				ResponseComment.class, id);

		logger.info("ResponseComment saved. Exiting save method");
		return responseComment;
	}

	@Override
	public ResponseComment update(ResponseComment responseComment) {
		logger.info("Entered ResponseComment update method");
		logger.debug("Updating like for Response id = "
				+ responseComment.getComponentId() + " and user id = "
				+ responseComment.getUserId());

		responseComment = (ResponseComment) getSession().merge(responseComment);
		getSession().update(responseComment);

		responseComment = (ResponseComment) getSession().get(
				ResponseComment.class, responseComment.getId());

		logger.info("Updated ResponseComment. Exiting update method");
		return responseComment;
	}

	@Override
	public List<ResponseComment> findAll() {

		logger.info("Entered ResponseComment findAll method");
		logger.debug("Getting all likes");

		Criteria selectAllCriteria = getSession().createCriteria(
				ResponseComment.class);
		@SuppressWarnings("unchecked")
		List<ResponseComment> responseComments = selectAllCriteria.list();

		logger.info("Returning all ResponseComment. Exiting findAll method");
		return responseComments;
	}

	@Override
	public void delete(Long id) {
		logger.info("Entered ResponseComment delete method");

		ResponseComment responseComment = (ResponseComment) getSession().get(
				ResponseComment.class, id);

		logger.info("Deleted ResponseComment. Exiting delete method");
		getSession().delete(responseComment);

	}

	@Override
	public List<ResponseComment> getByComponentIdAndUserId(Long componentId,
			Long userId) throws MessageException {
		logger.info("Entered ResponseComment getByComponentIdAndUserId method");
		Query query = getSession()
				.createSQLQuery(
						"SELECT * FROM  responseComment t WHERE t.componentId = :componentId AND t.userId = :userId")
				.addEntity(ResponseComment.class)
				.setParameter("componentId", componentId)
				.setParameter("userId", userId);

		@SuppressWarnings("unchecked")
		List<ResponseComment> responseComments = (List<ResponseComment>) query
				.list();

		if (responseComments.size() == 0) {
			logger.error("No record found for these componentId :"
					+ componentId + "and userId: " + userId);
			throw new MessageException(
					"No record found for these componentId :" + componentId
							+ "and userId: " + userId);
		}

		logger.info("Exiting ResponseComment getByComponentIdAndUserId method");

		return responseComments;
	}

	@Override
	public List<ResponseComment> getAllCommentsForComponent(Long componentId)
			throws MessageException {
		logger.info("Entered ResponseComment getAllCommentsForComponent method");

		Criteria criteria = getSession().createCriteria(ResponseComment.class);
		criteria.add(Restrictions.eq("componentId", componentId));

		@SuppressWarnings("unchecked")
		List<ResponseComment> responseComments = criteria.list();

		if (responseComments.size() == 0) {
			logger.error("comments does not exist for these componentId :"
					+ componentId);
			throw new MessageException(
					"comments does not exist for these componentId :"
							+ componentId);
		}
		logger.info("Exiting ResponseComment getAllCommentsForComponent method");
		return responseComments;
	}

	@Override
	public List<ResponseComment> getAllCommentsForUserId(Long userId)
			throws MessageException {
		logger.info("Entered ResponseComment getAllCommentsForUserId method");

		Criteria criteria = getSession().createCriteria(ResponseComment.class);
		criteria.add(Restrictions.eq("userId", userId));

		@SuppressWarnings("unchecked")
		List<ResponseComment> responseComments = criteria.list();

		if (responseComments.size() == 0) {
			logger.error("comments does not exist for these userId :" + userId);
			throw new MessageException(
					"comments does not exist for these UserId :" + userId);
		}
		logger.info("Exiting ResponseComment getAllCommentsForUserId method");
		return responseComments;
	}

	@Override
	public ResponseComment find(Long id) {
		logger.info("Entered ResponseComment find method");

		ResponseComment responseComment = (ResponseComment) getSession().get(
				ResponseComment.class, id);

		logger.info("Returning ResponseComment. Exiting find method");
		return responseComment;
	}

	@Override
	public void addMonetizationPoints(CommentBean commentBean, String action)
			throws Exception {
		Criteria criteria = getSession().createCriteria(Module.class);
		criteria.add(Restrictions.eq("componentType",
				commentBean.getComponentType()));

		Module module = (Module) criteria.uniqueResult();
		if (module == null) {

			logger.error("No module found in module table for component: "
					+ commentBean.getComponentType());
			throw new MessageException(
					"No module found in module table for component: "
							+ commentBean.getComponentType());

		}
		UserMonetization userMonetization = new UserMonetization();

		userMonetization.setAction(action);
		userMonetization.setComponentType(commentBean.getComponentType());

		userMonetization.setModule(module.getModule());
		userMonetization.setUserId(commentBean.getUserId());
		userMonetization.setActionComponentId(commentBean.getComponentId());

		userMonetizationBussinessDeligate.addAction(userMonetization);

	}

}
