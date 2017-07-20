package com.ohmuk.folitics.hibernate.repository.share;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ohmuk.folitics.beans.AirShareDataBean;
import com.ohmuk.folitics.businessDelegate.interfaces.IUserMonetizationBusinessDeligate;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Module;
import com.ohmuk.folitics.hibernate.entity.UserMonetization;
import com.ohmuk.folitics.hibernate.entity.share.ChartShare;

@Component
@Repository
public class ChartShareRepositoryImplementation implements
		IChartShareRepository {

	private static Logger logger = LoggerFactory
			.getLogger(ChartShareRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private volatile IUserMonetizationBusinessDeligate UserMonetizationBussinessDeligate;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public ChartShare save(ChartShare chartShare) {
		logger.info("Entered ChartShare save method");
		logger.debug("Saving share for sentiment id = "
				+ chartShare.getComponentId());

		Long id = (Long) getSession().save(chartShare);
		chartShare = (ChartShare) getSession().get(ChartShare.class, id);

		logger.info("ChartShare saved. Exiting save method");
		return chartShare;
	}

	@Override
	public ChartShare update(ChartShare chartShare) {
		logger.info("Entered ChartShare update method");
		logger.debug("Saving share for sentiment id = "
				+ chartShare.getComponentId());

		chartShare = (ChartShare) getSession().merge(chartShare);
		getSession().update(chartShare);

		chartShare = (ChartShare) getSession().get(ChartShare.class,
				chartShare.getId());

		logger.info("ChartShare updated. Exiting update method");
		return chartShare;
	}

	@Override
	public ChartShare find(Long id) {
		logger.info("Entered ChartShare find method");
		logger.debug("Getting share for sentiment id = " + id);

		ChartShare chartShare = (ChartShare) getSession().get(ChartShare.class,
				id);

		logger.info("Returning ChartShare. Exiting find method");
		return chartShare;
	}

	@Override
	public List<ChartShare> findAll() {
		logger.info("Entered ChartShare findAll method");
		logger.debug("Getting all shares");

		@SuppressWarnings("unchecked")
		List<ChartShare> chartShares = getSession().createCriteria(
				ChartShare.class).list();

		logger.info("Returning all ChartShare. Exiting findAll method");
		return chartShares;
	}

	@Override
	public void delete(Long id) {
		logger.info("Entered ChartShare delete method");
		logger.debug("Deleting shares with id = " + id);

		ChartShare chartShare = (ChartShare) getSession().get(ChartShare.class,
				id);
		getSession().delete(chartShare);

		logger.info("Deleted ChartShare. Exiting delete method");

	}

	@Override
	public Set<ChartShare> getSharesByComponentId(Long componentId) {
		logger.info("Entered ChartShare getSharesByComponentId method");
		logger.debug("Getting shares with component id = " + componentId);

		Criteria selectCriteria = getSession().createCriteria(ChartShare.class);
		selectCriteria.add(Restrictions.eq("componentId", componentId));
		@SuppressWarnings("unchecked")
		Set<ChartShare> sentimentShares = (Set<ChartShare>) selectCriteria
				.uniqueResult();

		logger.info("Returing ChartShare. Exiting getSharesByComponentId method");
		return sentimentShares;
	}

	@Override
	public Set<ChartShare> getSharesByUserIdAndComponentId(Long userId,
			Long componentId) {
		logger.info("Entered ChartShare getSharesByUserIdAndComponentId method");
		logger.debug("Getting shares with component id = " + componentId
				+ " and user id = " + userId);

		Criteria selectCriteria = getSession().createCriteria(ChartShare.class);
		selectCriteria.add(Restrictions.eq("componentId", componentId)).add(
				Restrictions.eq("userId", userId));
		@SuppressWarnings("unchecked")
		Set<ChartShare> chartShares = (Set<ChartShare>) selectCriteria
				.uniqueResult();

		logger.info("Returing chartShares. Exiting getSharesByUserIdAndComponentId method");
		return chartShares;
	}

	@Override
	public void addMonetizationPoints(AirShareDataBean airShareDataBean,
			String action) throws Exception {
		Criteria criteria = getSession().createCriteria(Module.class);
		criteria.add(Restrictions.eq("componentType",
				airShareDataBean.getComponentType()));

		Module module = (Module) criteria.uniqueResult();
		if (module == null) {

			logger.error("No module found in module table for component: "
					+ airShareDataBean.getComponentType());
			throw new MessageException(
					"No module found in module table for component: "
							+ airShareDataBean.getComponentType());

		}
		UserMonetization userMonetization = new UserMonetization();

		userMonetization.setAction(action);
		userMonetization.setComponentType(airShareDataBean.getComponentType());

		userMonetization.setModule(module.getModule());
		userMonetization.setUserId(airShareDataBean.getUserId());
		userMonetization
				.setActionComponentId(airShareDataBean.getComponentId());

		UserMonetizationBussinessDeligate.addAction(userMonetization);

	}

}
