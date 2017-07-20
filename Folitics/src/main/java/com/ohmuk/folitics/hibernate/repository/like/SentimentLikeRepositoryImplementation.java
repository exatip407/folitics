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
import com.ohmuk.folitics.hibernate.entity.like.SentimentLike;
import com.ohmuk.folitics.hibernate.entity.like.SentimentLikeCount;

/**
 * Repository implementation for entity: {@link SentimentLike}
 * 
 * @author Abhishek
 *
 */
@Component
@Repository
public class SentimentLikeRepositoryImplementation implements
		ISentimentLikeRepository {

	private static Logger logger = LoggerFactory
			.getLogger(SentimentLikeRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private volatile IUserMonetizationBusinessDeligate userMonetizationBussinessDeligate;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public SentimentLike save(SentimentLike sentimentLike) {

		logger.info("Entered SentimentLike save method");
		logger.debug("Saving like for sentiment id = "
				+ sentimentLike.getLikeId().getComponentId()
				+ " and user id = " + sentimentLike.getLikeId().getUserId());

		LikeId id = (LikeId) getSession().save(sentimentLike);
		sentimentLike = (SentimentLike) getSession().get(SentimentLike.class,
				id);

		logger.info("SentimentLike saved. Exiting save method");
		return sentimentLike;
	}

	@Override
	public SentimentLike find(LikeId likeId) {

		logger.info("Entered SentimentLike find method");
		logger.debug("Getting like for sentiment id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());

		SentimentLike sentimentLike = (SentimentLike) getSession().get(
				SentimentLike.class, likeId);

		logger.info("Returning SentimentLike. Exiting find method");
		return sentimentLike;
	}

	@Override
	public List<SentimentLike> findAll() {

		logger.info("Entered SentimentLike findAll method");
		logger.debug("Getting all likes");

		Criteria selectAllCriteria = getSession().createCriteria(
				SentimentLikeCount.class);
		@SuppressWarnings("unchecked")
		List<SentimentLike> sentimentLikes = selectAllCriteria.list();

		logger.info("Returning all SentimentLike. Exiting findAll method");
		return sentimentLikes;
	}

	@Override
	public void delete(LikeId likeId) {

		logger.info("Entered SentimentLike delete method");
		logger.debug("Deleting like for sentiment id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());

		SentimentLike sentimentLike = (SentimentLike) getSession().get(
				SentimentLike.class, likeId);

		logger.info("Deleted SentimentLike. Exiting delete method");
		getSession().delete(sentimentLike);
	}

	@Override
	public SentimentLike getByComponentIdAndUserId(Long componentId, Long userId) {

		logger.info("Entered SentimentLike getByComponentIdAndUserId method");
		logger.debug("Getting like for sentiment id = " + componentId
				+ " and user id = " + userId);

		Criteria selectCriteria = getSession().createCriteria(
				SentimentLike.class);
		selectCriteria.add(Restrictions.eq("id.componentId", componentId)).add(
				Restrictions.eq("id.userId", userId));
		SentimentLike sentimentLike = (SentimentLike) selectCriteria
				.uniqueResult();

		logger.info("Returning SentimentLike. Exiting getByComponentIdAndUserId method");
		return sentimentLike;
	}

	@Override
	public SentimentLike update(SentimentLike sentimentLike) {

		logger.info("Entered SentimentLike update method");
		logger.debug("Updating like for sentiment id = "
				+ sentimentLike.getLikeId().getComponentId()
				+ " and user id = " + sentimentLike.getLikeId().getUserId());

		sentimentLike = (SentimentLike) getSession().merge(sentimentLike);
		getSession().update(sentimentLike);

		sentimentLike = (SentimentLike) getSession().get(SentimentLike.class,
				sentimentLike.getLikeId());

		logger.info("Updated SentimentLike. Exiting update method");
		return sentimentLike;
	}

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

//		userMonetizationBussinessDeligate.addAction(userMonetization);
	}

}
