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
import com.ohmuk.folitics.hibernate.entity.like.GovtSchemeDataLike;
import com.ohmuk.folitics.hibernate.entity.like.LikeId;

/**
 * Repository implementation for entity: {@link GovtSchemeDataLike}
 * 
 * @author Harish
 *
 */
@Component
@Repository
public class GovtSchemeDataLikeRepositoryImplementation implements
		IGovtSchemeDataLikeRepository {

	private static Logger logger = LoggerFactory
			.getLogger(ChartLikeRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private volatile IUserMonetizationBusinessDeligate userMonetizationBussinessDeligate;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public GovtSchemeDataLike save(GovtSchemeDataLike govtSchemeDataLike) {
		logger.info("Entered GovtSchemeDataLike save method");
		logger.debug("Saving like for sentiment id = "
				+ govtSchemeDataLike.getId().getComponentId()
				+ " and user id = " + govtSchemeDataLike.getId().getUserId());

		LikeId id = (LikeId) getSession().save(govtSchemeDataLike);
		govtSchemeDataLike = (GovtSchemeDataLike) getSession().get(
				GovtSchemeDataLike.class, id);

		logger.info("GovtSchemeDataLike saved. Exiting save method");
		return govtSchemeDataLike;
	}

	@Override
	public GovtSchemeDataLike update(GovtSchemeDataLike govtSchemeDataLike) {
		logger.info("Entered GovtSchemeDataLike update method");
		logger.debug("Updating like for sentiment id = "
				+ govtSchemeDataLike.getId().getComponentId()
				+ " and user id = " + govtSchemeDataLike.getId().getUserId());

		govtSchemeDataLike = (GovtSchemeDataLike) getSession().merge(
				govtSchemeDataLike);
		getSession().update(govtSchemeDataLike);

		govtSchemeDataLike = (GovtSchemeDataLike) getSession().get(
				GovtSchemeDataLike.class, govtSchemeDataLike.getId());

		logger.info("Updated GovtSchemeDataLike. Exiting update method");
		return govtSchemeDataLike;
	}

	@Override
	public GovtSchemeDataLike find(LikeId likeId) {
		logger.info("Entered GovtSchemeDataLike find method");
		logger.debug("Getting like for sentiment id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());

		GovtSchemeDataLike govtSchemeDataLike = (GovtSchemeDataLike) getSession()
				.get(GovtSchemeDataLike.class, likeId);

		logger.info("Returning GovtSchemeDataLike. Exiting find method");
		return govtSchemeDataLike;
	}

	@Override
	public List<GovtSchemeDataLike> findAll() {
		logger.info("Entered GovtSchemeDataLike findAll method");
		logger.debug("Getting all likes");

		Criteria selectAllCriteria = getSession().createCriteria(
				GovtSchemeDataLike.class);
		@SuppressWarnings("unchecked")
		List<GovtSchemeDataLike> govtSchemeDataLikes = selectAllCriteria.list();

		logger.info("Returning all GovtSchemeDataLike. Exiting findAll method");
		return govtSchemeDataLikes;
	}

	@Override
	public void delete(LikeId likeId) {
		logger.info("Entered GovtSchemeDataLike delete method");
		logger.debug("Deleting like for sentiment id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());

		GovtSchemeDataLike govtSchemeDataLike = (GovtSchemeDataLike) getSession()
				.get(GovtSchemeDataLike.class, likeId);

		logger.info("Deleted GovtSchemeDataLike. Exiting delete method");
		getSession().delete(govtSchemeDataLike);

	}

	@Override
	public GovtSchemeDataLike getByComponentIdAndUserId(Long componentId,
			Long userId) {
		logger.info("Entered GovtSchemeDataLike getByComponentIdAndUserId method");
		logger.debug("Getting like for sentiment id = " + componentId
				+ " and user id = " + userId);

		Criteria selectCriteria = getSession().createCriteria(
				GovtSchemeDataLike.class);
		selectCriteria.add(Restrictions.eq("id.componentId", componentId)).add(
				Restrictions.eq("id.userId", userId));
		GovtSchemeDataLike govtSchemeDataLike = (GovtSchemeDataLike) selectCriteria
				.uniqueResult();

		logger.info("Returning GovtSchemeDataLike. Exiting getByComponentIdAndUserId method");
		return govtSchemeDataLike;
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
