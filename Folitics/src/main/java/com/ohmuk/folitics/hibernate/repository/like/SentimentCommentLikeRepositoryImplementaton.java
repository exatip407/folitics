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

import com.ohmuk.folitics.beans.LikeDataBean;
import com.ohmuk.folitics.businessDelegate.interfaces.IUserMonetizationBusinessDeligate;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Module;
import com.ohmuk.folitics.hibernate.entity.UserMonetization;
import com.ohmuk.folitics.hibernate.entity.like.LikeId;
import com.ohmuk.folitics.hibernate.entity.like.SentimentCommentLike;

/**
 * Repository implementation for entity: {@link SentimentCommentLike}
 * 
 * @author Harish
 *
 */
@Component
@Repository
public class SentimentCommentLikeRepositoryImplementaton implements
		ISentimentCommentLikeRepository {

	private static Logger logger = LoggerFactory
			.getLogger(SentimentCommentLikeRepositoryImplementaton.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private volatile IUserMonetizationBusinessDeligate userMonetizationBussinessDeligate;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public SentimentCommentLike save(SentimentCommentLike sentimentCommentLike) {
		logger.info("Entered SentimentCommentLike save method");
		logger.debug("Saving like for sentimentComment id = "
				+ sentimentCommentLike.getId().getComponentId()
				+ " and user id = " + sentimentCommentLike.getId().getUserId());

		LikeId id = (LikeId) getSession().save(sentimentCommentLike);
		sentimentCommentLike = (SentimentCommentLike) getSession().get(
				SentimentCommentLike.class, id);

		logger.info("SentimentCommentLike saved. Exiting save method");
		return sentimentCommentLike;
	}

	@Override
	public SentimentCommentLike update(SentimentCommentLike sentimentCommentLike) {
		logger.info("Entered SentimentCommentLike update method");
		logger.debug("Updating like for sentimentComment id = "
				+ sentimentCommentLike.getId().getComponentId()
				+ " and user id = " + sentimentCommentLike.getId().getUserId());

		sentimentCommentLike = (SentimentCommentLike) getSession().merge(
				sentimentCommentLike);
		getSession().update(sentimentCommentLike);

		sentimentCommentLike = (SentimentCommentLike) getSession().get(
				SentimentCommentLike.class, sentimentCommentLike.getId());

		logger.info("Updated SentimentCommentLike. Exiting update method");
		return sentimentCommentLike;
	}

	@Override
	public SentimentCommentLike find(LikeId likeId) {
		logger.info("Entered SentimentCommentLike find method");
		logger.debug("Getting like for sentimentComment id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());

		SentimentCommentLike sentimentCommentLike = (SentimentCommentLike) getSession()
				.get(SentimentCommentLike.class, likeId);

		logger.info("Returning SentimentCommentLike. Exiting find method");
		return sentimentCommentLike;
	}

	@Override
	public List<SentimentCommentLike> findAll() {
		logger.info("Entered SentimentCommentLike findAll method");
		logger.debug("Getting all likes");

		Criteria selectAllCriteria = getSession().createCriteria(
				SentimentCommentLike.class);

		@SuppressWarnings("unchecked")
		List<SentimentCommentLike> sentimentCommentLikes = selectAllCriteria
				.list();

		logger.info("Returning all SentimentCommentLike. Exiting findAll method");
		return sentimentCommentLikes;
	}

	@Override
	public void delete(LikeId likeId) {
		logger.info("Entered SentimentCommentLike delete method");
		logger.debug("Deleting like for sentimentComment id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());

		SentimentCommentLike sentimentCommentLike = (SentimentCommentLike) getSession()
				.get(SentimentCommentLike.class, likeId);

		logger.info("Deleted SentimentCommentLike. Exiting delete method");
		getSession().delete(sentimentCommentLike);

	}

	@Override
	public SentimentCommentLike getByComponentIdAndUserId(Long componentId,
			Long userId) {
		logger.info("Entered SentimentCommentLike getByComponentIdAndUserId method");
		logger.debug("Getting like for sentimentComment id = " + componentId
				+ " and user id = " + userId);

		Criteria selectCriteria = getSession().createCriteria(
				SentimentCommentLike.class);
		selectCriteria.add(Restrictions.eq("id.componentId", componentId)).add(
				Restrictions.eq("id.userId", userId));
		SentimentCommentLike sentimentCommentLike = (SentimentCommentLike) selectCriteria
				.uniqueResult();

		logger.info("Returning SentimentCommentLike. Exiting getByComponentIdAndUserId method");
		return sentimentCommentLike;
	}

	@Override
	public void addMonetizationPoints(LikeDataBean likeDataBean, String action)
			throws Exception {
		Criteria criteria = getSession().createCriteria(Module.class);
		criteria.add(Restrictions.eq("componentType",
				likeDataBean.getComponentType()));

		Module module = (Module) criteria.uniqueResult();
		if (module == null) {

			logger.error("No module found in module table for component: "
					+ likeDataBean.getComponentType());
			throw new MessageException(
					"No module found in module table for component: "
							+ likeDataBean.getComponentType());

		}
		UserMonetization userMonetization = new UserMonetization();

		userMonetization.setAction(action);
		userMonetization.setComponentType(likeDataBean.getComponentType());

		userMonetization.setModule(module.getModule());
		userMonetization.setUserId(likeDataBean.getUserId());
		userMonetization.setActionComponentId(likeDataBean.getComponentId());

		userMonetizationBussinessDeligate.addAction(userMonetization);

	}

}
