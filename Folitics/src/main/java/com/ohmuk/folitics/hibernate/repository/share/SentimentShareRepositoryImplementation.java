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
import com.ohmuk.folitics.hibernate.entity.share.SentimentShare;

@Component
@Repository
public class SentimentShareRepositoryImplementation implements
		ISentimentShareRepository {

	private static Logger logger = LoggerFactory
			.getLogger(SentimentShareRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private volatile IUserMonetizationBusinessDeligate UserMonetizationBussinessDeligate;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public SentimentShare save(SentimentShare sentimentShare) {

		logger.info("Entered SentimentShare save method");
		logger.debug("Saving share for sentiment id = "
				+ sentimentShare.getComponentId());

		Long id = (Long) getSession().save(sentimentShare);
		sentimentShare = (SentimentShare) getSession().get(
				SentimentShare.class, id);

		logger.info("SentimentShare saved. Exiting save method");
		return sentimentShare;
	}

	@Override
	public SentimentShare update(SentimentShare sentimentShare) {

		logger.info("Entered SentimentShare update method");
		logger.debug("Saving share for sentiment id = "
				+ sentimentShare.getComponentId());

		sentimentShare = (SentimentShare) getSession().merge(sentimentShare);
		getSession().update(sentimentShare);

		sentimentShare = (SentimentShare) getSession().get(
				SentimentShare.class, sentimentShare.getId());

		logger.info("SentimentShare updated. Exiting update method");
		return sentimentShare;
	}

	@Override
	public SentimentShare find(Long id) {

		logger.info("Entered SentimentShare find method");
		logger.debug("Getting share for sentiment id = " + id);

		SentimentShare sentimentShare = (SentimentShare) getSession().get(
				SentimentShare.class, id);

		logger.info("Returning SentimentShare. Exiting find method");
		return sentimentShare;
	}

	@Override
	public List<SentimentShare> findAll() {

		logger.info("Entered SentimentShare findAll method");
		logger.debug("Getting all shares");

		@SuppressWarnings("unchecked")
		List<SentimentShare> sentimentShares = getSession().createCriteria(
				SentimentShare.class).list();

		logger.info("Returning all SentimentShare. Exiting findAll method");
		return sentimentShares;
	}

	@Override
	public void delete(Long id) {

		logger.info("Entered SentimentShare delete method");
		logger.debug("Deleting shares with id = " + id);

		SentimentShare sentimentShare = (SentimentShare) getSession().get(
				SentimentShare.class, id);
		getSession().delete(sentimentShare);

		logger.info("Deleted SentimentShare. Exiting delete method");
	}

	@Override
	public Set<SentimentShare> getSharesByComponentId(Long componentId) {

		logger.info("Entered SentimentShare getSharesByComponentId method");
		logger.debug("Getting shares with component id = " + componentId);

		Criteria selectCriteria = getSession().createCriteria(
				SentimentShare.class);
		selectCriteria.add(Restrictions.eq("componentId", componentId));
		@SuppressWarnings("unchecked")
		Set<SentimentShare> sentimentShares = (Set<SentimentShare>) selectCriteria
				.uniqueResult();

		/*
		 * Query query = getSession().createQuery(
		 * "FROM SentimentShare S WHERE S.componentId = :sentimentId")
		 * .setParameter("sentimentId", componentId);
		 * 
		 * @SuppressWarnings("unchecked") Set<SentimentShare> sentimentShares =
		 * (Set<SentimentShare>) query.list();
		 */

		logger.info("Returing SentimentShare. Exiting getSharesByComponentId method");
		return sentimentShares;
	}

	@Override
	public Set<SentimentShare> getSharesByUserIdAndComponentId(Long userId,
			Long componentId) {

		logger.info("Entered SentimentShare getSharesByUserIdAndComponentId method");
		logger.debug("Getting shares with component id = " + componentId
				+ " and user id = " + userId);

		Criteria selectCriteria = getSession().createCriteria(
				SentimentShare.class);
		selectCriteria.add(Restrictions.eq("componentId", componentId)).add(
				Restrictions.eq("userId", userId));
		@SuppressWarnings("unchecked")
		Set<SentimentShare> sentimentShares = (Set<SentimentShare>) selectCriteria
				.uniqueResult();

		/*
		 * Query query = getSession() .createSQLQuery(
		 * "FROM SentimentShare S WHERE S.componentId = :sentimentId AND S.userId = :userId"
		 * ) .addEntity(SentimentLike.class).setParameter("sentimentId",
		 * componentId).setParameter("userId", userId);
		 * 
		 * @SuppressWarnings("unchecked") Set<SentimentShare> sentimentShares =
		 * (Set<SentimentShare>) query.list();
		 */

		logger.info("Returing SentimentShares. Exiting getSharesByUserIdAndComponentId method");
		return sentimentShares;
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
