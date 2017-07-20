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
import com.ohmuk.folitics.hibernate.entity.comment.ChartComment;

/**
 * Repository implementation for entity: {@link ChartComment}
 * 
 * @author Harish
 *
 */

@Component
@Repository
public class ChartCommentRepositoryImplementation implements
		IChartCommentRepository {

	private static Logger logger = LoggerFactory
			.getLogger(ChartCommentRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private volatile IUserMonetizationBusinessDeligate userMonetizationBussinessDeligate;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public ChartComment save(ChartComment chartComment) {

		logger.info("Entered ChartComment save method");
		logger.debug("Saving Comment for chart id = "
				+ chartComment.getComponentId() + " and user id = "
				+ chartComment.getUserId());

		Long id = (Long) getSession().save(chartComment);
		chartComment = (ChartComment) getSession().get(ChartComment.class, id);

		logger.info("ChartComment saved. Exiting save method");
		return chartComment;
	}

	@Override
	public ChartComment update(ChartComment chartComment) {
		logger.info("Entered ChartComment update method");
		logger.debug("Updating like for chart id = "
				+ chartComment.getComponentId() + " and user id = "
				+ chartComment.getUserId());

		chartComment = (ChartComment) getSession().merge(chartComment);
		getSession().update(chartComment);

		chartComment = (ChartComment) getSession().get(ChartComment.class,
				chartComment.getId());

		logger.info("Updated ChartComment. Exiting update method");
		return chartComment;
	}

	@Override
	public List<ChartComment> findAll() {
		logger.info("Entered ChartComment findAll method");
		logger.debug("Getting all likes");

		Criteria selectAllCriteria = getSession().createCriteria(
				ChartComment.class);
		@SuppressWarnings("unchecked")
		List<ChartComment> taskComments = selectAllCriteria.list();

		logger.info("Returning all ChartComment. Exiting findAll method");
		return taskComments;
	}

	@Override
	public void delete(Long id) {

		logger.info("Entered ChartComment delete method");

		ChartComment chartComment = (ChartComment) getSession().get(
				ChartComment.class, id);

		
		logger.info("Deleted ChartComment. Exiting delete method");
		getSession().delete(chartComment);

	}

	@Override
	public List<ChartComment> getByComponentIdAndUserId(Long componentId,
			Long userId) throws MessageException {
		logger.info("Entered ChartComment getByComponentIdAndUserId method");
		Query query = getSession()
				.createSQLQuery(
						"SELECT * FROM chartcomment t WHERE t.componentId = :componentId AND t.userId = :userId")
				.addEntity(ChartComment.class)
				.setParameter("componentId", componentId)
				.setParameter("userId", userId);

		@SuppressWarnings("unchecked")
		List<ChartComment> chartComments = (List<ChartComment>) query.list();

		if (chartComments.size() == 0) {
			logger.error("No record found for these componentId :"
					+ componentId + "and userId: " + userId);
			throw new MessageException(
					"No record found for these componentId :" + componentId
							+ "and userId: " + userId);
		}

		logger.info("Exiting ChartComment getByComponentIdAndUserId method");

		return chartComments;
	}

	@Override
	public List<ChartComment> getAllCommentsForComponent(Long componentId)
			throws MessageException {
		logger.info("Entered ChartComment getAllCommentsForComponent method");

		Criteria criteria = getSession().createCriteria(ChartComment.class);
		criteria.add(Restrictions.eq("componentId", componentId));

		@SuppressWarnings("unchecked")
		List<ChartComment> taskComments = criteria.list();

		if (taskComments.size() == 0) {
			logger.error("comments does not exist for these componentId :"
					+ componentId);
			throw new MessageException(
					"comments does not exist for these componentId :"
							+ componentId);
		}
		logger.info("Exiting ChartComment getAllCommentsForComponent method");
		return taskComments;
	}

	@Override
	public List<ChartComment> getAllCommentsForUserId(Long userId)
			throws MessageException {
		logger.info("Entered ChartComment getAllCommentsForUserId method");

		Criteria criteria = getSession().createCriteria(ChartComment.class);
		criteria.add(Restrictions.eq("userId", userId));

		@SuppressWarnings("unchecked")
		List<ChartComment> taskComments = criteria.list();

		if (taskComments.size() == 0) {
			logger.error("comments does not exist for these userId :" + userId);
			throw new MessageException(
					"comments does not exist for these UserId :" + userId);
		}
		logger.info("Exiting ChartComment getAllCommentsForUserId method");
		return taskComments;
	}

	@Override
	public ChartComment find(Long id) {
		logger.info("Entered ChartComment find method");

		ChartComment chartComment = (ChartComment) getSession().get(
				ChartComment.class, id);

		logger.info("Returning ChartComment. Exiting find method");
		return chartComment;
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
