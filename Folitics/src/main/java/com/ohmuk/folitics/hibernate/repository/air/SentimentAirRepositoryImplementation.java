package com.ohmuk.folitics.hibernate.repository.air;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
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
import com.ohmuk.folitics.hibernate.entity.air.SentimentAir;

/**
 * Repository implementation for entity: {@link SentimentAir}
 * 
 * @author Abhishek
 *
 */

@Component
@Repository
public class SentimentAirRepositoryImplementation implements
		ISentimentAirRepository {
	
private static	Logger logger=LoggerFactory.getLogger(SentimentAirRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private volatile IUserMonetizationBusinessDeligate userMonetizationBussinessDeligate;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public SentimentAir save(SentimentAir sentimentAir) {

		Long id = (Long) getSession().save(sentimentAir);
		sentimentAir = (SentimentAir) getSession().get(SentimentAir.class, id);
		return sentimentAir;
	}

	@Override
	public SentimentAir find(Long id) {

		SentimentAir sentimentAir = (SentimentAir) getSession().get(
				SentimentAir.class, id);
		return sentimentAir;
	}

	@Override
	public List<SentimentAir> findAll() {

		@SuppressWarnings("unchecked")
		List<SentimentAir> sentimentLikes = getSession().createQuery(
				"FROM sentimentair").list();
		return sentimentLikes;
	}

	@Override
	public void delete(Long likeId) {

		SentimentAir sentimentAir = (SentimentAir) getSession().get(
				SentimentAir.class, likeId);
		getSession().delete(sentimentAir);
	}

	@Override
	public List<SentimentAir> getByComponentIdAndUserId(Long componentId,
			Long userId) {

		Query query = getSession()
				.createSQLQuery(
						"SELECT * FROM sentimentair s WHERE s.componentId = :componentId AND s.userId = :userId")
				.addEntity(SentimentAir.class)
				.setParameter("componentId", componentId)
				.setParameter("userId", userId);

		List<SentimentAir> sentimentShares = (List<SentimentAir>) query.list();
		return sentimentShares;
	}

	@Override
	public SentimentAir update(SentimentAir sentimentAir) {

		sentimentAir = (SentimentAir) getSession().merge(sentimentAir);
		getSession().update(sentimentAir);

		sentimentAir = (SentimentAir) getSession().get(SentimentAir.class,
				sentimentAir.getId());

		return sentimentAir;
	}

	@Override
	public List<SentimentAir> findAirsByComponentId(Long id) {

		Query query = getSession()
				.createSQLQuery("SELECT * FROM sentimentair s WHERE s.id = :id")
				.addEntity(SentimentAir.class).setParameter("id", id);

		@SuppressWarnings("unchecked")
		List<SentimentAir> sentimentShares = (List<SentimentAir>) query.list();
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

		userMonetizationBussinessDeligate.addAction(userMonetization);

	}

}
