package com.ohmuk.folitics.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.MonetizationConfig;

@Service
@Transactional
public class MonetizationConfigService implements IMonetizationConfigService {

	public static Logger logger = LoggerFactory
			.getLogger(MonetizationConfigService.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {

		return sessionFactory.getCurrentSession();
	}

	@Override
	public MonetizationConfig create(MonetizationConfig monetization)
			throws MessageException, Exception {

		logger.info("Inside create MonetizationService");

		if (monetization == null) {
			logger.error("MonetizationConfig found to be null");
			throw (new MessageException("MonetizationConfig can't be null"));
		}
		Long id = (Long) getSession().save(monetization);

		MonetizationConfig monetizationConfig = (MonetizationConfig) getSession()
				.get(MonetizationConfig.class, id);

		if (monetizationConfig == null) {
			logger.error("monetizationConfig found to be null");
			throw (new MessageException("monetizationConfig found to be null"));
		}

		logger.debug("Saved MonetizationConfig", monetizationConfig);
		logger.debug("Exiting create MonetizationService");
		return monetizationConfig;
	}

	@Override
	public MonetizationConfig read(Long id) throws MessageException, Exception {

		logger.info("Inside read MonetizationService");

		if (id == null) {
			logger.error("MonetizationConfig id found to be null");
			throw (new MessageException("MonetizationConfig id can't be null"));
		}

		MonetizationConfig monetization = (MonetizationConfig) getSession()
				.get(MonetizationConfig.class, id);

		if (monetization == null) {
			logger.error("No records found in database for the correspponding id"
					+ id);
			throw (new MessageException(
					"No records found in database for the correspponding id"
							+ id));
		}

		logger.debug("fatched MonetizationConfig", monetization);
		logger.debug("Exiting read MonetizationService");
		return monetization;
	}

	@Override
	public List<MonetizationConfig> readAll() throws MessageException,
			Exception {

		logger.info("Inside readAll MonetizationService");

		List<MonetizationConfig> monetizationConfig = getSession()
				.createCriteria(MonetizationConfig.class).list();

		logger.debug("fatched list of MonetizationConfig", monetizationConfig);
		logger.debug("Exiting readAll MonetizationService");

		System.out.println("result: " + monetizationConfig);

		if (monetizationConfig == null) {
			logger.error("monetization found to be null");
			throw (new MessageException("monetization found to be null"));
		}

		return monetizationConfig;
	}

	@Override
	public MonetizationConfig update(MonetizationConfig monetization)
			throws MessageException, Exception {

		logger.info("Inside update MonetizationService");

		if (monetization == null) {
			logger.error("MonetizationConfig found to be null");
			throw (new MessageException("MonetizationConfig can't be null"));
		}

		getSession().update(monetization);
		Long id = (long) monetization.getId();
		monetization = (MonetizationConfig) getSession().get(
				MonetizationConfig.class, id);

		logger.debug("Updated MonetizationConfig", monetization);
		logger.debug("Exiting update MonetizationService");

		return monetization;
	}

	@Override
	public MonetizationConfig deleteById(Long id) throws MessageException,
			Exception {

		logger.info("Inside deleteById MonetizationService");

		if (id == null) {
			logger.error("monetization id found to be null");
			throw (new MessageException("MonetizationConfig id can't be null"));
		}

		MonetizationConfig monetization = (MonetizationConfig) getSession()
				.get(MonetizationConfig.class, id);

		if (monetization == null) {
			logger.error("No records found in database for the correspponding id"
					+ id);
			throw (new MessageException(
					"No records found in database for the correspponding id"
							+ id));
		}

		monetization.setStatus("Deleted");
		getSession().update(monetization);

		monetization = (MonetizationConfig) getSession().get(
				MonetizationConfig.class, id);

		if (monetization != null) {

			logger.debug("MonetizationConfig deleted");
			logger.info("Exiting deleteById MonetizationService");

		} else {
			throw (new MessageException(
					"Something went wrong while deleting monetization by id"));
		}

		return monetization;
	}

	@Override
	public MonetizationConfig delete(MonetizationConfig monetization)
			throws MessageException, Exception {

		logger.info("Inside delete MonetizationService");

		if (monetization == null) {
			logger.error("MonetizationConfig found to be null");
			throw (new MessageException("MonetizationConfig can't be null"));
		}

		Long id = (long) monetization.getId();
		getSession().delete(monetization);

		monetization = (MonetizationConfig) getSession().get(
				MonetizationConfig.class, id);
		if (monetization == null) {

			logger.debug("MonetizationConfig deleted");
			logger.info("Exiting deleteById MonetizationService");

		} else {
			throw (new MessageException(
					"Something went wrong while deleting monetization"));
		}

		return monetization;
	}

	/*
	 * @Override public MonetizationConfig setPointStatus(Long id, String
	 * status) throws MessageException,Exception{
	 * 
	 * logger.info("Inside delete MonetizationService");
	 * 
	 * if(id==null){ logger.error("MonetizationConfig id found to be null");
	 * throw (new MessageException("MonetizationConfig id can't be null")); }
	 * 
	 * if(status==null){ logger.error(
	 * "MonetizationConfig status found to be null"); throw (new
	 * MessageException("MonetizationConfig status can't be null")); }
	 * 
	 * MonetizationConfig monetization = (MonetizationConfig)
	 * getSession().get(MonetizationConfig.class, id);
	 * 
	 * if(monetization==null){ logger.error(
	 * "No monetization found in db for corresponding id: "+id); throw (new
	 * MessageException("No monetization found in db for corresponding id: "
	 * +id)); }
	 * 
	 * monetization.setStatus(status); getSession().save(monetization);
	 * monetization = (MonetizationConfig)
	 * getSession().get(MonetizationConfig.class, id);
	 * 
	 * logger.debug("MonetizationConfig status has been updated deleted" );
	 * logger.info("Exiting setPointStatus MonetizationService");
	 * 
	 * return monetization; }
	 */

}
