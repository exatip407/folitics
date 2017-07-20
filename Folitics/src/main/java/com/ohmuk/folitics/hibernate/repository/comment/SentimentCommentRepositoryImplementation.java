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
import com.ohmuk.folitics.hibernate.entity.comment.SentimentComment;
import com.ohmuk.folitics.hibernate.entity.comment.SentimentComment;

/**
 * Repository implementation for entity: {@link SentimentComment}
 * 
 * @author Harish
 *
 */

@Component
@Repository
public class SentimentCommentRepositoryImplementation implements
		ISentimentCommentRepository {

	private static Logger logger = LoggerFactory
			.getLogger(SentimentCommentRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private volatile IUserMonetizationBusinessDeligate userMonetizationBussinessDeligate;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public SentimentComment save(SentimentComment sentimentComment) {
		logger.info("Entered SentimentComment save method");
		logger.debug("Saving Comment for sentiment id = "
				+ sentimentComment.getComponentId() + " and user id = "
				+ sentimentComment.getUserId());

		Long id = (Long) getSession().save(sentimentComment);
		sentimentComment = (SentimentComment) getSession().get(SentimentComment.class, id);

		logger.info("SentimentComment saved. Exiting save method");
		return sentimentComment;
	}

	@Override
	public SentimentComment update(SentimentComment sentimentComment) {
		logger.info("Entered SentimentComment update method");
		logger.debug("Updating like for sentiment id = "
				+ sentimentComment.getComponentId() + " and user id = "
				+ sentimentComment.getUserId());

		sentimentComment = (SentimentComment) getSession().merge(sentimentComment);
		getSession().update(sentimentComment);

		sentimentComment = (SentimentComment) getSession().get(SentimentComment.class,
				sentimentComment.getId());

		logger.info("Updated SentimentComment. Exiting update method");
		return sentimentComment;
	}

	@Override
	public List<SentimentComment> findAll() {
		logger.info("Entered SentimentComment findAll method");
		logger.debug("Getting all likes");

		Criteria selectAllCriteria = getSession().createCriteria(
				SentimentComment.class);
		@SuppressWarnings("unchecked")
		List<SentimentComment> taskComments = selectAllCriteria.list();

		logger.info("Returning all SentimentComment. Exiting findAll method");
		return taskComments;
	}

	@Override
	public void delete(Long id) {
		logger.info("Entered SentimentComment delete method");

		SentimentComment sentimentComment = (SentimentComment) getSession().get(
				SentimentComment.class, id);

		logger.info("Deleted SentimentComment. Exiting delete method");
		getSession().delete(sentimentComment);

	}

	@Override
	public List<SentimentComment> getByComponentIdAndUserId(Long componentId,
			Long userId) throws MessageException {
		logger.info("Entered SentimentComment getByComponentIdAndUserId method");
		Query query = getSession()
				.createSQLQuery(
						"SELECT * FROM sentimentcomment t WHERE t.componentId = :componentId AND t.userId = :userId")
				.addEntity(SentimentComment.class)
				.setParameter("componentId", componentId)
				.setParameter("userId", userId);

		@SuppressWarnings("unchecked")
		List<SentimentComment> sentimentComments = (List<SentimentComment>) query.list();

		if (sentimentComments.size() == 0) {
			logger.error("No record found for these componentId :"
					+ componentId + "and userId: " + userId);
			throw new MessageException(
					"No record found for these componentId :" + componentId
							+ "and userId: " + userId);
		}

		logger.info("Exiting SentimentComment getByComponentIdAndUserId method");

		return sentimentComments;
	}

	@Override
	public List<SentimentComment> getAllCommentsForComponent(Long componentId)
			throws MessageException {
		logger.info("Entered SentimentComment getAllCommentsForComponent method");

		Criteria criteria = getSession().createCriteria(SentimentComment.class);
		criteria.add(Restrictions.eq("componentId", componentId));

		@SuppressWarnings("unchecked")
		List<SentimentComment> sentimentComments = criteria.list();

		if (sentimentComments.size() == 0) {
			logger.error("comments does not exist for these componentId :"
					+ componentId);
			throw new MessageException(
					"comments does not exist for these componentId :"
							+ componentId);
		}
		logger.info("Exiting SentimentComment getAllCommentsForComponent method");
		return sentimentComments;
	}

	@Override
	public List<SentimentComment> getAllCommentsForUserId(Long userId)
			throws MessageException {
		logger.info("Entered SentimentComment getAllCommentsForUserId method");

		Criteria criteria = getSession().createCriteria(SentimentComment.class);
		criteria.add(Restrictions.eq("userId", userId));

		@SuppressWarnings("unchecked")
		List<SentimentComment> sentimentComments = criteria.list();

		if (sentimentComments.size() == 0) {
			logger.error("comments does not exist for these userId :" + userId);
			throw new MessageException(
					"comments does not exist for these UserId :" + userId);
		}
		logger.info("Exiting SentimentComment getAllCommentsForUserId method");
		return sentimentComments;
	}

	@Override
	public SentimentComment find(Long id) {
		logger.info("Entered SentimentComment find method");

		SentimentComment sentimentComment = (SentimentComment) getSession().get(
				SentimentComment.class, id);

		logger.info("Returning SentimentComment. Exiting find method");
		return sentimentComment;
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
