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
import com.ohmuk.folitics.hibernate.entity.like.ResponseLike;

/**
 * Repository implementation for entity: {@link ResponseLike}
 * 
 * @author Harish
 *
 */
@Component
@Repository
public class ResponseLikeRepositoryImplementation implements
		IResponseLikeRepository {

	private static Logger logger = LoggerFactory
			.getLogger(ResponseLikeRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private volatile IUserMonetizationBusinessDeligate userMonetizationBussinessDeligate;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public ResponseLike save(ResponseLike responseLike) {
		logger.info("Entered ResponseLike save method");
		logger.debug("Saving like for task id = "
				+ responseLike.getId().getComponentId() + " and user id = "
				+ responseLike.getId().getUserId());

		LikeId id = (LikeId) getSession().save(responseLike);
		responseLike = (ResponseLike) getSession().get(ResponseLike.class, id);

		logger.info("ResponseLike saved. Exiting save method");
		return responseLike;
	}

	@Override
	public ResponseLike update(ResponseLike responseLike) {
		logger.info("Entered ResponseLike update method");
		logger.debug("Updating like for sentiment id = "
				+ responseLike.getId().getComponentId() + " and user id = "
				+ responseLike.getId().getUserId());

		responseLike = (ResponseLike) getSession().merge(responseLike);
		getSession().update(responseLike);

		responseLike = (ResponseLike) getSession().get(ResponseLike.class,
				responseLike.getId());

		logger.info("Updated ResponseLike. Exiting update method");
		return responseLike;
	}

	@Override
	public ResponseLike find(LikeId likeId) {
		logger.info("Entered ResponseLike find method");
		logger.debug("Getting like for task id = " + likeId.getComponentId()
				+ " and user id = " + likeId.getUserId());

		ResponseLike responseLike = (ResponseLike) getSession().get(
				ResponseLike.class, likeId);

		logger.info("Returning ResponseLike. Exiting find method");
		return responseLike;
	}

	@Override
	public List<ResponseLike> findAll() {
		logger.info("Entered ResponseLike findAll method");
		logger.debug("Getting all likes");

		Criteria selectAllCriteria = getSession().createCriteria(
				ResponseLike.class);
		@SuppressWarnings("unchecked")
		List<ResponseLike> responseLikes = selectAllCriteria.list();

		logger.info("Returning all ResponseLike. Exiting findAll method");
		return responseLikes;
	}

	@Override
	public void delete(LikeId likeId) {
		logger.info("Entered ResponseLike delete method");
		logger.debug("Deleting like for sentiment id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());

		ResponseLike responseLike = (ResponseLike) getSession().get(
				ResponseLike.class, likeId);

		logger.info("Deleted ResponseLike. Exiting delete method");
		getSession().delete(responseLike);

	}

	@Override
	public ResponseLike getByComponentIdAndUserId(Long componentId, Long userId) {
		logger.info("Entered ResponseLike getByComponentIdAndUserId method");
		logger.debug("Getting like for task id = " + componentId
				+ " and user id = " + userId);

		Criteria selectCriteria = getSession().createCriteria(
				ResponseLike.class);
		selectCriteria.add(Restrictions.eq("id.componentId", componentId)).add(
				Restrictions.eq("id.userId", userId));
		ResponseLike responseLike = (ResponseLike) selectCriteria
				.uniqueResult();

		logger.info("Returning ResponseLike. Exiting getByComponentIdAndUserId method");
		return responseLike;
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
