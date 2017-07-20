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
import com.ohmuk.folitics.hibernate.entity.like.ChartLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.FactLike;
import com.ohmuk.folitics.hibernate.entity.like.LikeId;

/**
 * Repository implementation for entity: {@link FactLike}
 * 
 * @author Harish
 *
 */
@Component
@Repository
public class FactLikeRepositoryImplementation implements IFactLikeRepository {

	private static Logger logger = LoggerFactory
			.getLogger(FactLikeRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private volatile IUserMonetizationBusinessDeligate userMonetizationBussinessDeligate;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public FactLike save(FactLike factLike) {
		logger.info("Entered FactLike save method");
		logger.debug("Saving like for fact id = "
				+ factLike.getId().getComponentId() + " and user id = "
				+ factLike.getId().getUserId());

		LikeId id = (LikeId) getSession().save(factLike);
		factLike = (FactLike) getSession().get(FactLike.class, id);

		logger.info("FactLike saved. Exiting save method");
		return factLike;
	}

	@Override
	public FactLike update(FactLike factLike) {
		logger.info("Entered FactLike update method");
		logger.debug("Updating like for fact id = "
				+ factLike.getId().getComponentId() + " and user id = "
				+ factLike.getId().getUserId());

		factLike = (FactLike) getSession().merge(factLike);
		getSession().update(factLike);

		factLike = (FactLike) getSession().get(FactLike.class,
				factLike.getId());

		logger.info("Updated FactLike. Exiting update method");
		return factLike;
	}

	@Override
	public FactLike find(LikeId likeId) {
		logger.info("Entered FactLike find method");
		logger.debug("Getting like for fact id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());

		FactLike factLike = (FactLike) getSession().get(FactLike.class,
				likeId);

		logger.info("Returning FactLike. Exiting find method");
		return factLike;
	}

	@Override
	public List<FactLike> findAll() {
		logger.info("Entered FactLike findAll method");
		logger.debug("Getting all likes");

		Criteria selectAllCriteria = getSession().createCriteria(
				FactLike.class);
		
		@SuppressWarnings("unchecked")
		List<FactLike> chartLikes = selectAllCriteria.list();

		logger.info("Returning all FactLike. Exiting findAll method");
		return chartLikes;
	}

	@Override
	public void delete(LikeId likeId) {
		logger.info("Entered FactLike delete method");
		logger.debug("Deleting like for fact id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());

		FactLike factLike = (FactLike) getSession().get(FactLike.class,
				likeId);

		logger.info("Deleted FactLike. Exiting delete method");
		getSession().delete(factLike);

	}

	@Override
	public FactLike getByComponentIdAndUserId(Long componentId, Long userId) {
		logger.info("Entered FactLike getByComponentIdAndUserId method");
		logger.debug("Getting like for fact id = " + componentId
				+ " and user id = " + userId);

		Criteria selectCriteria = getSession().createCriteria(FactLike.class);
		selectCriteria.add(Restrictions.eq("id.componentId", componentId)).add(
				Restrictions.eq("id.userId", userId));
		FactLike factLike = (FactLike) selectCriteria.uniqueResult();

		logger.info("Returning FactLike. Exiting getByComponentIdAndUserId method");
		return factLike;
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
