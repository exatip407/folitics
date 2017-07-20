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
import com.ohmuk.folitics.hibernate.entity.like.ChartLike;
import com.ohmuk.folitics.hibernate.entity.like.LikeId;

/**
 * Repository implementation for entity: {@link ChartLike}
 * 
 * @author Harish
 *
 */
@Component
@Repository
public class ChartLikeRepositoryImplementation implements IChartLikeRepository {

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
	public ChartLike save(ChartLike chartLike) {
		logger.info("Entered ChartLike save method");
		logger.debug("Saving like for sentiment id = "
				+ chartLike.getId().getComponentId() + " and user id = "
				+ chartLike.getId().getUserId());

		LikeId id = (LikeId) getSession().save(chartLike);
		chartLike = (ChartLike) getSession().get(ChartLike.class, id);

		logger.info("ChartLike saved. Exiting save method");
		return chartLike;
	}

	@Override
	public ChartLike update(ChartLike chartLike) {
		logger.info("Entered ChartLike update method");
		logger.debug("Updating like for sentiment id = "
				+ chartLike.getId().getComponentId() + " and user id = "
				+ chartLike.getId().getUserId());

		chartLike = (ChartLike) getSession().merge(chartLike);
		getSession().update(chartLike);

		chartLike = (ChartLike) getSession().get(ChartLike.class,
				chartLike.getId());

		logger.info("Updated ChartLike. Exiting update method");
		return chartLike;
	}

	@Override
	public ChartLike find(LikeId likeId) {

		logger.info("Entered ChartLike find method");
		logger.debug("Getting like for sentiment id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());

		ChartLike chartLike = (ChartLike) getSession().get(ChartLike.class,
				likeId);

		logger.info("Returning ChartLike. Exiting find method");
		return chartLike;
	}

	@Override
	public List<ChartLike> findAll() {
		logger.info("Entered ChartLike findAll method");
		logger.debug("Getting all likes");

		Criteria selectAllCriteria = getSession().createCriteria(
				ChartLike.class);
		@SuppressWarnings("unchecked")
		List<ChartLike> chartLikes = selectAllCriteria.list();

		logger.info("Returning all ChartLike. Exiting findAll method");
		return chartLikes;
	}

	@Override
	public void delete(LikeId likeId) {
		logger.info("Entered ChartLike delete method");
		logger.debug("Deleting like for sentiment id = "
				+ likeId.getComponentId() + " and user id = "
				+ likeId.getUserId());

		ChartLike chartLike = (ChartLike) getSession().get(ChartLike.class,
				likeId);

		logger.info("Deleted ChartLike. Exiting delete method");
		getSession().delete(chartLike);

	}

	@Override
	public ChartLike getByComponentIdAndUserId(Long componentId, Long userId) {
		logger.info("Entered ChartLike getByComponentIdAndUserId method");
		logger.debug("Getting like for sentiment id = " + componentId
				+ " and user id = " + userId);

		Criteria selectCriteria = getSession().createCriteria(ChartLike.class);
		selectCriteria.add(Restrictions.eq("id.componentId", componentId)).add(
				Restrictions.eq("id.userId", userId));
		ChartLike chartLike = (ChartLike) selectCriteria.uniqueResult();

		logger.info("Returning ChartLike. Exiting getByComponentIdAndUserId method");
		return chartLike;
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
