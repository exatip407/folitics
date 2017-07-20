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
import com.ohmuk.folitics.hibernate.entity.comment.OpinionComment;

/**
 * Repository implementation for entity: {@link OpinionComment}
 * 
 * @author Harish
 *
 */

@Component
@Repository
public class OpinionCommentRepositoryImplementation implements
		IOpinionCommentRepository {

	private static Logger logger = LoggerFactory
			.getLogger(OpinionCommentRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private volatile IUserMonetizationBusinessDeligate userMonetizationBussinessDeligate;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public OpinionComment save(OpinionComment opinionComment) {
		logger.info("Entered OpinionComment save method");
		logger.debug("Saving Comment for opinion id = "
				+ opinionComment.getComponentId() + " and user id = "
				+ opinionComment.getUserId());

		Long id = (Long) getSession().save(opinionComment);
		opinionComment = (OpinionComment) getSession().get(
				OpinionComment.class, id);

		logger.info("OpinionComment saved. Exiting save method");
		return opinionComment;
	}

	@Override
	public OpinionComment update(OpinionComment opinionComment) {
		logger.info("Entered OpinionComment update method");
		logger.debug("Updating like for Response id = "
				+ opinionComment.getComponentId() + " and user id = "
				+ opinionComment.getUserId());

		opinionComment = (OpinionComment) getSession().merge(opinionComment);
		getSession().update(opinionComment);

		opinionComment = (OpinionComment) getSession().get(
				OpinionComment.class, opinionComment.getId());

		logger.info("Updated OpinionComment. Exiting update method");
		return opinionComment;
	}

	@Override
	public List<OpinionComment> findAll() {
		logger.info("Entered OpinionComment findAll method");
		logger.debug("Getting all likes");

		Criteria selectAllCriteria = getSession().createCriteria(
				OpinionComment.class);
		@SuppressWarnings("unchecked")
		List<OpinionComment> responseComments = selectAllCriteria.list();

		logger.info("Returning all OpinionComment. Exiting findAll method");
		return responseComments;
	}

	@Override
	public void delete(Long id) {
		logger.info("Entered OpinionComment delete method");

		OpinionComment opinionComment = (OpinionComment) getSession().get(
				OpinionComment.class, id);

		logger.info("Deleted OpinionComment. Exiting delete method");
		getSession().delete(opinionComment);

	}

	@Override
	public List<OpinionComment> getByComponentIdAndUserId(Long componentId,
			Long userId) throws MessageException {
		logger.info("Entered OpinionComment getByComponentIdAndUserId method");
		Query query = getSession()
				.createSQLQuery(
						"SELECT * FROM  opinionComment t WHERE t.componentId = :componentId AND t.userId = :userId")
				.addEntity(OpinionComment.class)
				.setParameter("componentId", componentId)
				.setParameter("userId", userId);

		@SuppressWarnings("unchecked")
		List<OpinionComment> opinionComments = (List<OpinionComment>) query
				.list();

		if (opinionComments.size() == 0) {
			logger.error("No record found for these componentId :"
					+ componentId + "and userId: " + userId);
			throw new MessageException(
					"No record found for these componentId :" + componentId
							+ "and userId: " + userId);
		}

		logger.info("Exiting OpinionComment getByComponentIdAndUserId method");

		return opinionComments;
	}

	@Override
	public List<OpinionComment> getAllCommentsForComponent(Long componentId)
			throws MessageException {
		logger.info("Entered OpinionComment getAllCommentsForComponent method");

		Criteria criteria = getSession().createCriteria(OpinionComment.class);
		criteria.add(Restrictions.eq("componentId", componentId));

		@SuppressWarnings("unchecked")
		List<OpinionComment> opinionComments = criteria.list();

		if (opinionComments.size() == 0) {
			logger.error("comments does not exist for these componentId :"
					+ componentId);
			throw new MessageException(
					"comments does not exist for these componentId :"
							+ componentId);
		}
		logger.info("Exiting OpinionComment getAllCommentsForComponent method");
		return opinionComments;
	}

	@Override
	public List<OpinionComment> getAllCommentsForUserId(Long userId)
			throws MessageException {
		logger.info("Entered OpinionComment getAllCommentsForUserId method");

		Criteria criteria = getSession().createCriteria(OpinionComment.class);
		criteria.add(Restrictions.eq("userId", userId));

		@SuppressWarnings("unchecked")
		List<OpinionComment> responseComments = criteria.list();

		if (responseComments.size() == 0) {
			logger.error("comments does not exist for these userId :" + userId);
			throw new MessageException(
					"comments does not exist for these UserId :" + userId);
		}
		logger.info("Exiting OpinionComment getAllCommentsForUserId method");
		return responseComments;
	}

	@Override
	public OpinionComment find(Long id) {
		logger.info("Entered OpinionComment find method");

		OpinionComment opinionComment = (OpinionComment) getSession().get(
				OpinionComment.class, id);

		logger.info("Returning OpinionComment. Exiting find method");
		return opinionComment;
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
