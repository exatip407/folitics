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
import com.ohmuk.folitics.hibernate.entity.comment.GovtSchemeDataComment;

/**
 * Repository implementation for entity: {@link GovtSchemeDataComment}
 * 
 * @author Harish
 *
 */

@Component
@Repository
public class GovtSchemeDataCommentRepositoryImplementation implements
		IGovtSchemeDataCommentRepository {

	private static Logger logger = LoggerFactory
			.getLogger(GovtSchemeDataCommentRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private volatile IUserMonetizationBusinessDeligate userMonetizationBussinessDeligate;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public GovtSchemeDataComment save(
			GovtSchemeDataComment govtSchemeDataComment) {

		logger.info("Entered GovtSchemeDataComment save method");
		logger.debug("Saving Comment for scheme id = "
				+ govtSchemeDataComment.getComponentId() + " and user id = "
				+ govtSchemeDataComment.getUserId());

		Long id = (Long) getSession().save(govtSchemeDataComment);
		govtSchemeDataComment = (GovtSchemeDataComment) getSession().get(
				GovtSchemeDataComment.class, id);

		logger.info("GovtSchemeDataComment saved. Exiting save method");
		return govtSchemeDataComment;
	}

	@Override
	public GovtSchemeDataComment update(
			GovtSchemeDataComment govtSchemeDataComment) {

		logger.info("Entered GovtSchemeDataComment update method");
		logger.debug("Updating like for sentiment id = "
				+ govtSchemeDataComment.getComponentId() + " and user id = "
				+ govtSchemeDataComment.getUserId());

		govtSchemeDataComment = (GovtSchemeDataComment) getSession().merge(
				govtSchemeDataComment);
		getSession().update(govtSchemeDataComment);

		govtSchemeDataComment = (GovtSchemeDataComment) getSession().get(
				GovtSchemeDataComment.class, govtSchemeDataComment.getId());

		logger.info("Updated GovtSchemeDataComment. Exiting update method");
		return govtSchemeDataComment;
	}

	@Override
	public List<GovtSchemeDataComment> findAll() {

		logger.info("Entered GovtSchemeDataComment findAll method");
		logger.debug("Getting all likes");

		Criteria selectAllCriteria = getSession().createCriteria(
				GovtSchemeDataComment.class);
		@SuppressWarnings("unchecked")
		List<GovtSchemeDataComment> schemesComments = selectAllCriteria.list();

		logger.info("Returning all GovtSchemeDataComment. Exiting findAll method");
		return schemesComments;
	}

	@Override
	public void delete(Long id) {
		logger.info("Entered GovtSchemeDataComment delete method");

		GovtSchemeDataComment taskLike = (GovtSchemeDataComment) getSession()
				.get(GovtSchemeDataComment.class, id);

		logger.info("Deleted GovtSchemeDataComment. Exiting delete method");
		getSession().delete(taskLike);

	}

	@Override
	public List<GovtSchemeDataComment> getByComponentIdAndUserId(
			Long componentId, Long userId) throws MessageException {

		logger.info("Entered GovtSchemeDataComment getByComponentIdAndUserId method");
		Query query = getSession()
				.createSQLQuery(
						"SELECT * FROM govtschemedatacomment t WHERE t.componentId = :componentId AND t.userId = :userId")
				.addEntity(GovtSchemeDataComment.class)
				.setParameter("componentId", componentId)
				.setParameter("userId", userId);

		@SuppressWarnings("unchecked")
		List<GovtSchemeDataComment> taskComments = (List<GovtSchemeDataComment>) query
				.list();

		if (taskComments.size() == 0) {
			logger.error("No record found for these componentId :"
					+ componentId + "and userId: " + userId);
			throw new MessageException(
					"No record found for these componentId :" + componentId
							+ "and userId: " + userId);
		}

		logger.info("Exiting GovtSchemeDataComment getByComponentIdAndUserId method");

		return taskComments;
	}

	@Override
	public List<GovtSchemeDataComment> getAllCommentsForComponent(
			Long componentId) throws MessageException {

		logger.info("Entered GovtSchemeDataComment getAllCommentsForComponent method");

		Criteria criteria = getSession().createCriteria(
				GovtSchemeDataComment.class);
		criteria.add(Restrictions.eq("componentId", componentId));

		@SuppressWarnings("unchecked")
		List<GovtSchemeDataComment> taskComments = criteria.list();

		if (taskComments.size() == 0) {
			logger.error("comments does not exist for these componentId :"
					+ componentId);
			throw new MessageException(
					"comments does not exist for these componentId :"
							+ componentId);
		}
		logger.info("Exiting GovtSchemeDataComment getAllCommentsForComponent method");
		return taskComments;

	}

	@Override
	public List<GovtSchemeDataComment> getAllCommentsForUserId(Long userId)
			throws MessageException {

		logger.info("Entered GovtSchemeDataComment getAllCommentsForUserId method");

		Criteria criteria = getSession().createCriteria(
				GovtSchemeDataComment.class);
		criteria.add(Restrictions.eq("userId", userId));

		@SuppressWarnings("unchecked")
		List<GovtSchemeDataComment> schemeComments = criteria.list();

		if (schemeComments.size() == 0) {
			logger.error("comments does not exist for these userId :" + userId);
			throw new MessageException(
					"comments does not exist for these UserId :" + userId);
		}
		logger.info("Exiting GovtSchemeDataComment getAllCommentsForUserId method");
		return schemeComments;
	}

	@Override
	public GovtSchemeDataComment find(Long id) {

		logger.info("Entered GovtSchemeDataComment find method");

		GovtSchemeDataComment schemeComment = (GovtSchemeDataComment) getSession()
				.get(GovtSchemeDataComment.class, id);

		logger.info("Returning GovtSchemeDataComment. Exiting find method");
		return schemeComment;
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
