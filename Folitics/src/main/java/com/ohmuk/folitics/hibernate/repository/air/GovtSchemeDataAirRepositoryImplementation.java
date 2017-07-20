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
import com.ohmuk.folitics.hibernate.entity.air.GovtSchemeDataAir;
import com.ohmuk.folitics.hibernate.entity.air.GovtSchemeDataAir;

@Component
@Repository
public class GovtSchemeDataAirRepositoryImplementation implements IGovtSchemeDataAirRepository{
	
	public static Logger logger = LoggerFactory
			.getLogger(GovtSchemeDataAirRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private volatile IUserMonetizationBusinessDeligate userMonetizationBussinessDeligate;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public GovtSchemeDataAir save(GovtSchemeDataAir govtSchemeDataAir) {
		logger.info("Entered GovtSchemeDataAir  save method");

		Long id = (Long) getSession().save(govtSchemeDataAir);
		govtSchemeDataAir = (GovtSchemeDataAir) getSession().get(GovtSchemeDataAir.class, id);

		logger.info("GovtSchemeDataAir  saved. Exiting save method");
		return govtSchemeDataAir;
	}

	@Override
	public GovtSchemeDataAir update(GovtSchemeDataAir govtSchemeDataAir) {
		logger.debug("Entered GovtSchemeDataAir  update method");

		govtSchemeDataAir = (GovtSchemeDataAir) getSession().merge(govtSchemeDataAir);
		getSession().update(govtSchemeDataAir);

		govtSchemeDataAir = (GovtSchemeDataAir) getSession().get(GovtSchemeDataAir.class, govtSchemeDataAir.getId());

		logger.debug("Updated GovtSchemeDataAir . Exiting update method");

		return govtSchemeDataAir;
	}

	@Override
	public List<GovtSchemeDataAir> findAll() {
		logger.debug("Entered GovtSchemeDataAir  findAll method");
		logger.debug("Getting all air count");
		@SuppressWarnings("unchecked")
		List<GovtSchemeDataAir> opinionAirs = getSession().createQuery("FROM govtschemedataair").list();

		logger.debug("Returning all GovtSchemeDataAir . Exiting findAll method");
		return opinionAirs;
	}

	@Override
	public void delete(Long id) {
		logger.debug("Entered GovtSchemeDataAir delete method");

		GovtSchemeDataAir govtSchemeDataAir = (GovtSchemeDataAir) getSession().get(GovtSchemeDataAir.class, id);
		getSession().delete(govtSchemeDataAir);

		logger.debug("Deleted GovtSchemeDataAir. Exiting delete method");
		
	}

	@Override
	public List<GovtSchemeDataAir> getByComponentIdAndUserId(Long componentId, Long userId) {
		logger.debug("Entered GovtSchemeDataAir  getByComponentIdAndUserId method");

		Query query = getSession()
				.createSQLQuery(
						"SELECT * FROM govtschemedataair s WHERE s.componentId = :componentId AND s.userId = :userId")
				.addEntity(GovtSchemeDataAir.class).setParameter("componentId", componentId).setParameter("userId", userId);

		@SuppressWarnings("unchecked")
		List<GovtSchemeDataAir> tasksAirs = (List<GovtSchemeDataAir>) query.list();

		logger.debug("Fetched GovtSchemeDataAir . Exiting getByComponentIdAndUserId method");
		return tasksAirs;
	}

	@Override
	public GovtSchemeDataAir find(Long id) {
		logger.debug("Entered GovtSchemeDataAir  find method");

		GovtSchemeDataAir govtSchemeDataAir = (GovtSchemeDataAir) getSession().get(GovtSchemeDataAir.class, id);

		logger.debug("Returning GovtSchemeDataAir . Exiting find method");
		return govtSchemeDataAir;
	}

	@Override
	public List<GovtSchemeDataAir> findAirsByComponentId(Long id) {
		logger.debug("Entered GovtSchemeDataAir  findAirsByComponentId method");

		Query query = getSession().createSQLQuery("SELECT * FROM govtschemedataair s WHERE s.id = :id")
				.addEntity(GovtSchemeDataAir.class).setParameter("id", id);

		@SuppressWarnings("unchecked")
		List<GovtSchemeDataAir> opinionAirs = (List<GovtSchemeDataAir>) query.list();

		logger.debug("Fetched GovtSchemeDataAir . Exiting findAirsByComponentId method");
		return opinionAirs;
	}

	@Override
	public void addMonetizationPoints(AirShareDataBean airShareDataBean, String action) throws Exception {
		logger.debug("Entered GovtSchemeDataAir  addMonetizationPoints method");

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

		logger.debug("Fetched GovtSchemeDataAir . Exiting addMonetizationPoints method");

		
	}

	

}
