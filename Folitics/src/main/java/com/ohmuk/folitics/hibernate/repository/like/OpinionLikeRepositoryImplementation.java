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
import com.ohmuk.folitics.hibernate.entity.like.OpinionLike;
import com.ohmuk.folitics.hibernate.entity.like.SentimentLikeCount;

/**
 * Repository implementation for entity: {@link opinionLike}
 * 
 * @author Harish
 *
 */
@Component
@Repository
public class OpinionLikeRepositoryImplementation implements
		IOpinionLikeRepository {

	private static Logger logger = LoggerFactory
			.getLogger(OpinionLikeRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private volatile IUserMonetizationBusinessDeligate userMonetizationBussinessDeligate;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public OpinionLike save(OpinionLike opinionLike) {

		logger.info("Entered OpinionLike save method");
		logger.debug("Saving like for sentiment id = "
				+ opinionLike.getLikeId().getComponentId() + " and user id = "
				+ opinionLike.getLikeId().getUserId());

		LikeId id = (LikeId) getSession().save(opinionLike);
		opinionLike = (OpinionLike) getSession().get(OpinionLike.class, id);

		logger.info("OpinionLike saved. Exiting save method");
		return opinionLike;
	}

	@Override
	public OpinionLike update(OpinionLike opinionLike) {
		logger.info("Entered OpinionLike update method");
		logger.debug("Updating like for sentiment id = "
				+ opinionLike.getLikeId().getComponentId() + " and user id = "
				+ opinionLike.getLikeId().getUserId());

		opinionLike = (OpinionLike) getSession().merge(opinionLike);
		getSession().update(opinionLike);

		opinionLike = (OpinionLike) getSession().get(OpinionLike.class,
				opinionLike.getLikeId());

		logger.info("Updated opinionLike Exiting update method");
		return opinionLike;
	}

	@Override
	public OpinionLike find(LikeId likeId) {
		logger.info("Entered OpinionLike find method");
		logger.debug("Getting like for sentiment id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());

		OpinionLike OpinionLike = (OpinionLike) getSession().get(
				OpinionLike.class, likeId);

		logger.info("Returning opinionLike Exiting find method");
		return OpinionLike;
	}

	@Override
	public List<OpinionLike> findAll() {
		logger.info("Entered OpinionLike findAll method");
		logger.debug("Getting all likes");

		Criteria selectAllCriteria = getSession().createCriteria(
				OpinionLike.class);
		@SuppressWarnings("unchecked")
		List<OpinionLike> opinionLikes = selectAllCriteria.list();

		logger.info("Returning all opinionLike Exiting findAll method");
		return opinionLikes;
	}

	@Override
	public void delete(LikeId likeId) {
		logger.info("Entered OpinionLike delete method");
		logger.debug("Deleting like for sentiment id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());

		OpinionLike OpinionLike = (OpinionLike) getSession().get(
				OpinionLike.class, likeId);

		logger.info("Deleted opinionLike Exiting delete method");
		getSession().delete(OpinionLike);
	}

	@Override
	public OpinionLike getByComponentIdAndUserId(Long componentId, Long userId) {
		logger.info("Entered OpinionLike getByComponentIdAndUserId method");
		logger.debug("Getting like for sentiment id = " + componentId
				+ " and user id = " + userId);

		Criteria selectCriteria = getSession().createCriteria(OpinionLike.class);
		
		selectCriteria.add(Restrictions.eq("id.componentId", componentId)).add(
				Restrictions.eq("id.userId", userId));
		OpinionLike OpinionLike = (OpinionLike) selectCriteria.uniqueResult();

		logger.info("Returning opinionLike Exiting getByComponentIdAndUserId method");
		return OpinionLike;
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
