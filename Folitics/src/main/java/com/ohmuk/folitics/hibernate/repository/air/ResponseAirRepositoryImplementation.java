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
import com.ohmuk.folitics.hibernate.entity.air.ResponseAir;
import com.ohmuk.folitics.hibernate.entity.air.TaskAir;

/**
 * Repository implementation for entity: {@link TaskAir}
 * 
 * @author Harish
 *
 */

@Component
@Repository
public class ResponseAirRepositoryImplementation implements
		IResponseAirRepository {

	@Autowired
	private SessionFactory sessionFactory;

	public static Logger logger = LoggerFactory
			.getLogger(ResponseAirRepositoryImplementation.class);

	@Autowired
	private volatile IUserMonetizationBusinessDeligate userMonetizationBussinessDeligate;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public ResponseAir save(ResponseAir responseAir) {

		logger.info("Entered ResponseAir  save method");

		Long id = (Long) getSession().save(responseAir);
		responseAir = (ResponseAir) getSession().get(ResponseAir.class, id);

		logger.info("ResponseAir  saved. Exiting save method");
		return responseAir;
	}

	@Override
	public ResponseAir update(ResponseAir responseAir) {
		logger.info("Entered ResponseAir  update method");

		responseAir = (ResponseAir) getSession().merge(responseAir);
		getSession().update(responseAir);

		responseAir = (ResponseAir) getSession().get(ResponseAir.class,
				responseAir.getId());

		logger.info("Updated ResponseAir . Exiting update method");

		return responseAir;
	}

	@Override
	public List<ResponseAir> findAll() {

		logger.info("Entered ResponseAir  findAll method");
		logger.debug("Getting all air count");

		@SuppressWarnings("unchecked")
		List<ResponseAir> responseAirs = getSession().createQuery(
				"FROM responseair").list();

		logger.info("Returning all ResponseAir . Exiting findAll method");
		return responseAirs;
	}

	@Override
	public void delete(Long id) {
		logger.info("Entered ResponseAir delete method");

		ResponseAir responseAir = (ResponseAir) getSession().get(
				ResponseAir.class, id);
		getSession().delete(responseAir);

		logger.info("Deleted ResponseAir. Exiting delete method");

	}

	@Override
	public List<ResponseAir> getByComponentIdAndUserId(Long componentId,
			Long userId) {
		logger.info("Entered ResponseAir  getByComponentIdAndUserId method");

		Query query = getSession()
				.createSQLQuery(
						"SELECT * FROM responseair s WHERE s.componentId = :componentId AND s.userId = :userId")
				.addEntity(ResponseAir.class)
				.setParameter("componentId", componentId)
				.setParameter("userId", userId);

		@SuppressWarnings("unchecked")
		List<ResponseAir> responseAirs = (List<ResponseAir>) query.list();

		logger.info("Fetched ResponseAir . Exiting getByComponentIdAndUserId method");
		return responseAirs;
	}

	@Override
	public ResponseAir find(Long id) {
		logger.info("Entered ResponseAir  find method");

		ResponseAir responseAir = (ResponseAir) getSession().get(
				ResponseAir.class, id);

		logger.info("Returning ResponseAir . Exiting find method");
		return responseAir;
	}

	@Override
	public List<ResponseAir> findAirsByComponentId(Long id) {
		logger.info("Entered ResponseAir  findAirsByComponentId method");

		Query query = getSession()
				.createSQLQuery("SELECT * FROM responseair s WHERE s.id = :id")
				.addEntity(ResponseAir.class).setParameter("id", id);

		@SuppressWarnings("unchecked")
		List<ResponseAir> opinionAirs = (List<ResponseAir>) query.list();

		logger.info("Fetched ResponseAir . Exiting findAirsByComponentId method");
		return opinionAirs;
	}

	@Override
	public void addMonetizationPoints(AirShareDataBean airShareDataBean,
			String action) throws Exception {
		logger.info("Entered ResponseAir  addMonetizationPoints method");

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

		logger.info(" Exiting addMonetizationPoints method");

	}

}
