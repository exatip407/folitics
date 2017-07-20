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
import com.ohmuk.folitics.hibernate.entity.share.GovtSchemeDataShare;

@Component
@Repository
public class GovtSchemeDataShareRepositoryImplementation implements IGovtSchemeDataShareRepository {

	private static Logger logger = LoggerFactory.getLogger(GovtSchemeDataShareRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private volatile IUserMonetizationBusinessDeligate UserMonetizationBussinessDeligate;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public GovtSchemeDataShare save(GovtSchemeDataShare govtSchemeDataShare) {
		logger.info("Entered GovtSchemeDataShare save method");
		logger.debug("Saving share for govtSchemeData id = " + govtSchemeDataShare.getComponentId());

		Long id = (Long) getSession().save(govtSchemeDataShare);
		govtSchemeDataShare = (GovtSchemeDataShare) getSession().get(GovtSchemeDataShare.class, id);

		logger.info("GovtSchemeDataShare saved. Exiting save method");
		return govtSchemeDataShare;
	}

	@Override
	public GovtSchemeDataShare update(GovtSchemeDataShare govtSchemeDataShare) {
		logger.info("Entered GovtSchemeDataShare update method");
		logger.debug("Saving share for govtSchemeData id = " + govtSchemeDataShare.getComponentId());

		govtSchemeDataShare = (GovtSchemeDataShare) getSession().merge(govtSchemeDataShare);
		getSession().update(govtSchemeDataShare);

		govtSchemeDataShare = (GovtSchemeDataShare) getSession().get(GovtSchemeDataShare.class,
				govtSchemeDataShare.getId());

		logger.info("GovtSchemeDataShare updated. Exiting update method");
		return govtSchemeDataShare;
	}

	@Override
	public GovtSchemeDataShare find(Long id) {
		logger.info("Entered GovtSchemeDataShare find method");
		logger.debug("Getting share for govtSchemeData id = " + id);

		GovtSchemeDataShare GovtSchemeDataShare = (GovtSchemeDataShare) getSession().get(GovtSchemeDataShare.class, id);

		logger.info("Returning GovtSchemeDataShare. Exiting find method");
		return GovtSchemeDataShare;
	}

	@Override
	public List<GovtSchemeDataShare> findAll() {
		logger.info("Entered GovtSchemeDataShare findAll method");
		logger.debug("Getting all shares");

		@SuppressWarnings("unchecked")
		List<GovtSchemeDataShare> govtSchemeDataShares = getSession().createCriteria(GovtSchemeDataShare.class).list();

		logger.info("Returning all GovtSchemeDataShare. Exiting findAll method");
		return govtSchemeDataShares;
	}

	@Override
	public void delete(Long id) {
		logger.info("Entered GovtSchemeDataShare delete method");
		logger.debug("Deleting shares with id = " + id);

		GovtSchemeDataShare GovtSchemeDataShare = (GovtSchemeDataShare) getSession().get(GovtSchemeDataShare.class, id);
		getSession().delete(GovtSchemeDataShare);

		logger.info("Deleted GovtSchemeDataShare. Exiting delete method");

	}

	@Override
	public Set<GovtSchemeDataShare> getSharesByComponentId(Long componentId) {
		logger.info("Entered GovtSchemeDataShare getSharesByComponentId method");
		logger.debug("Getting shares with component id = " + componentId);

		Criteria selectCriteria = getSession().createCriteria(GovtSchemeDataShare.class);
		selectCriteria.add(Restrictions.eq("componentId", componentId));
		@SuppressWarnings("unchecked")
		Set<GovtSchemeDataShare> govtSchemeDataShares = (Set<GovtSchemeDataShare>) selectCriteria.uniqueResult();

		logger.info("Returing govtSchemeDataShares. Exiting getSharesByComponentId method");
		return govtSchemeDataShares;
	}

	@Override
	public Set<GovtSchemeDataShare> getSharesByUserIdAndComponentId(Long userId, Long componentId) {
		logger.info("Entered GovtSchemeDataShare getSharesByUserIdAndComponentId method");
		logger.debug("Getting shares with component id = " + componentId + " and user id = " + userId);

		Criteria selectCriteria = getSession().createCriteria(GovtSchemeDataShare.class);
		selectCriteria.add(Restrictions.eq("componentId", componentId)).add(Restrictions.eq("userId", userId));
		@SuppressWarnings("unchecked")
		Set<GovtSchemeDataShare> govtSchemeDataShares = (Set<GovtSchemeDataShare>) selectCriteria.uniqueResult();

		logger.info("Returing OpinionShares. Exiting getSharesByUserIdAndComponentId method");
		return govtSchemeDataShares;
	}

	@Override
	public void addMonetizationPoints(AirShareDataBean airShareDataBean, String action) throws Exception {
		logger.info("Entered GovtSchemeDataShare addMonetizationPoints method");

		Criteria criteria = getSession().createCriteria(Module.class);
		criteria.add(Restrictions.eq("componentType", airShareDataBean.getComponentType()));

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
		userMonetization.setActionComponentId(airShareDataBean.getComponentId());

		UserMonetizationBussinessDeligate.addAction(userMonetization);

		logger.info("Exiting addMonetizationPoints method");

	}

}
