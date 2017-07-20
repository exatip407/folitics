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
import com.ohmuk.folitics.hibernate.entity.share.OpinionShare;

@Component
@Repository
public class OpinionShareRepositoryImplementation implements IOpinionShareRepository{

	private static Logger logger = LoggerFactory
			.getLogger(OpinionShareRepositoryImplementation.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private volatile IUserMonetizationBusinessDeligate UserMonetizationBussinessDeligate;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public OpinionShare save(OpinionShare opinionShare) {
		
		logger.info("Entered opinionShare save method");
		logger.debug("Saving share for opinion id = "
				+ opinionShare.getComponentId());

		Long id = (Long) getSession().save(opinionShare);
		opinionShare = (OpinionShare) getSession().get(
				OpinionShare.class, id);

		logger.info("opinionShare saved. Exiting save method");
		return opinionShare;
	}

	@Override
	public OpinionShare update(OpinionShare opinionShare) {
		
		logger.info("Entered opinionShare update method");
		logger.debug("Saving share for opinion id = "
				+ opinionShare.getComponentId());

		opinionShare = (OpinionShare) getSession().merge(opinionShare);
		getSession().update(opinionShare);

		opinionShare = (OpinionShare) getSession().get(
				OpinionShare.class, opinionShare.getId());

		logger.info("opinionShare updated. Exiting update method");
		return opinionShare;
	}

	@Override
	public OpinionShare find(Long id) {
		
		logger.info("Entered opinionShare find method");
		logger.debug("Getting share for opinion id = " + id);

		OpinionShare opinionShare = (OpinionShare) getSession().get(
				OpinionShare.class, id);

		logger.info("Returning opinionShare. Exiting find method");
		return opinionShare;
	}

	@Override
	public List<OpinionShare> findAll() {
		
		logger.info("Entered opinionShare findAll method");
		logger.debug("Getting all shares");

		@SuppressWarnings("unchecked")
		List<OpinionShare> opinionShares = getSession().createCriteria(
				OpinionShare.class).list();

		logger.info("Returning all opinionShare. Exiting findAll method");
		return opinionShares;
	}

	@Override
	public void delete(Long id) {
		
		logger.info("Entered opinionShare delete method");
		logger.debug("Deleting shares with id = " + id);

		OpinionShare opinionShare = (OpinionShare) getSession().get(
				OpinionShare.class, id);
		getSession().delete(opinionShare);

		logger.info("Deleted opinionShare. Exiting delete method");
		
	}

	@Override
	public Set<OpinionShare> getSharesByComponentId(Long componentId) {
		
		logger.info("Entered opinionShare getSharesByComponentId method");
		logger.debug("Getting shares with component id = " + componentId);

		Criteria selectCriteria = getSession().createCriteria(
				OpinionShare.class);
		selectCriteria.add(Restrictions.eq("componentId", componentId));
		@SuppressWarnings("unchecked")
		Set<OpinionShare> opinionShares = (Set<OpinionShare>) selectCriteria
				.uniqueResult();
		
		logger.info("Returing opinionShares. Exiting getSharesByComponentId method");
		return opinionShares;
	}

	@Override
	public Set<OpinionShare> getSharesByUserIdAndComponentId(Long userId, Long componentId) {
		
		logger.info("Entered opinionShare getSharesByUserIdAndComponentId method");
		logger.debug("Getting shares with component id = " + componentId
				+ " and user id = " + userId);

		Criteria selectCriteria = getSession().createCriteria(
				OpinionShare.class);
		selectCriteria.add(Restrictions.eq("componentId", componentId)).add(
				Restrictions.eq("userId", userId));
		@SuppressWarnings("unchecked")
		Set<OpinionShare> opinionShares = (Set<OpinionShare>) selectCriteria
				.uniqueResult();
		
		logger.info("Returing OpinionShares. Exiting getSharesByUserIdAndComponentId method");
		return opinionShares;
	}

	@Override
	public void addMonetizationPoints(AirShareDataBean airShareDataBean, String action) throws Exception {

		logger.info("Entered opinionShare addMonetizationPoints method");
		
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
		
		logger.info("Exiting addMonetizationPoints method");
		
	}
}
