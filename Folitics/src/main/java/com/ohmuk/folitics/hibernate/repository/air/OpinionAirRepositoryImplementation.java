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
import com.ohmuk.folitics.hibernate.entity.air.OpinionAir;

@Component
@Repository
public class OpinionAirRepositoryImplementation implements IOpinionAirRepository {

	public static Logger logger = LoggerFactory.getLogger(OpinionAirRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private volatile IUserMonetizationBusinessDeligate userMonetizationBussinessDeligate;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public OpinionAir save(OpinionAir opinionAir) {
		logger.debug("Entered OpinionAir  save method");

		Long id = (Long) getSession().save(opinionAir);
		opinionAir = (OpinionAir) getSession().get(OpinionAir.class, id);

		logger.debug("OpinionAir  saved. Exiting save method");
		return opinionAir;
	}

	@Override
	public OpinionAir update(OpinionAir opinionAir) {

		logger.debug("Entered OpinionAir  update method");

		opinionAir = (OpinionAir) getSession().merge(opinionAir);
		getSession().update(opinionAir);

		opinionAir = (OpinionAir) getSession().get(OpinionAir.class, opinionAir.getId());

		logger.debug("Updated OpinionAir . Exiting update method");

		return opinionAir;
	}

	@Override
	public List<OpinionAir> findAll() {

		logger.debug("Entered OpinionAir  findAll method");
		logger.debug("Getting all air count");
		@SuppressWarnings("unchecked")
		List<OpinionAir> opinionAirs = getSession().createQuery("FROM opinionair").list();

		logger.debug("Returning all OpinionAir . Exiting findAll method");
		return opinionAirs;
	}

	@Override
	public void delete(Long AirId) {

		logger.debug("Entered OpinionAir delete method");

		OpinionAir opinionAir = (OpinionAir) getSession().get(OpinionAir.class, AirId);
		getSession().delete(opinionAir);

		logger.debug("Deleted OpinionAir. Exiting delete method");

	}

	@Override
	public List<OpinionAir> getByComponentIdAndUserId(Long componentId, Long userId) {

		logger.debug("Entered OpinionAir  getByComponentIdAndUserId method");

		Query query = getSession()
				.createSQLQuery(
						"SELECT * FROM opinionair s WHERE s.componentId = :componentId AND s.userId = :userId")
				.addEntity(OpinionAir.class).setParameter("componentId", componentId).setParameter("userId", userId);

		@SuppressWarnings("unchecked")
		List<OpinionAir> tasksAirs = (List<OpinionAir>) query.list();

		logger.debug("Fetched OpinionAir . Exiting getByComponentIdAndUserId method");
		return tasksAirs;
	}

	@Override
	public OpinionAir find(Long id) {

		logger.debug("Entered OpinionAir  find method");

		OpinionAir opinionAir = (OpinionAir) getSession().get(OpinionAir.class, id);

		logger.debug("Returning OpinionAir . Exiting find method");
		return opinionAir;
	}

	@Override
	public List<OpinionAir> findAirsByComponentId(Long id) {

		logger.debug("Entered OpinionAir  findAirsByComponentId method");

		Query query = getSession().createSQLQuery("SELECT * FROM opinionair s WHERE s.id = :id")
				.addEntity(OpinionAir.class).setParameter("id", id);

		@SuppressWarnings("unchecked")
		List<OpinionAir> opinionAirs = (List<OpinionAir>) query.list();

		logger.debug("Fetched OpinionAir . Exiting findAirsByComponentId method");
		return opinionAirs;
	}

	@Override
	public void addMonetizationPoints(AirShareDataBean airShareDataBean, String action) throws Exception {

		logger.debug("Entered OpinionAir  addMonetizationPoints method");

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

		userMonetizationBussinessDeligate.addAction(userMonetization);

		logger.debug("Fetched OpinionAir . Exiting addMonetizationPoints method");

	}

}
