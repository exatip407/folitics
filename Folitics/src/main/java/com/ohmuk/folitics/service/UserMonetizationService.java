package com.ohmuk.folitics.service;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.UserMonetization;

@Service
@Transactional
public class UserMonetizationService implements IUserMonetizationService {

	static final Logger logger = LoggerFactory
			.getLogger(UserMonetizationService.class);

	@Autowired
	private SessionFactory session_Factory;

	private Session getSession() {

		return session_Factory.getCurrentSession();
	}

	@Override
	public UserMonetization addAction(UserMonetization userMonetization)
			throws MessageException, Exception {

		logger.info("Inside addAction UserMonetizationService");

		userMonetization.setStatus("Available");
		Long id = (Long) getSession().save(userMonetization);

		userMonetization = (UserMonetization) getSession().get(
				UserMonetization.class, id);

		if (userMonetization == null) {
			logger.error("userMonetization found to be null");
			throw (new MessageException("userMonetization found to be null"));
		}

		logger.debug("Monetization added: ", userMonetization);
		logger.info("Exiting addAction UserMonetizationService");

		return userMonetization;

	}

	@Override
	public UserMonetization getById(Long id) throws MessageException, Exception {

		logger.info("Inside getById UserMonetizationService");

		if (id == null) {
			logger.error("monetizationConfig id found to be null");
			throw (new MessageException("monetizationConfig id can't be null"));
		}

		Criteria criteria = getSession().createCriteria(UserMonetization.class);
		criteria.add(Restrictions.eqOrIsNull("id", id));
		UserMonetization userMonetization = (UserMonetization) criteria
				.uniqueResult();

		System.out.println("Result: " + userMonetization);

		if (userMonetization == null) {
			logger.error("userMonetization found to be null, no records found in database");
			throw (new MessageException(
					"userMonetization can't be null, no records found in database"));
		}

		logger.debug("Monetization record fetched: ", userMonetization);
		logger.info("Exiting getById UserMonetizationService");

		return userMonetization;
	}

	@Override
	public List<UserMonetization> getByUserId(Long id) throws MessageException,
			Exception {

		logger.info("Inside getByUserId UserMonetizationService");

		if (id == null) {
			logger.error("User id found to be null");
			throw (new MessageException("User id can't be null"));
		}

		User user = (User) getSession().get(User.class, id);

		if (user == null) {
			logger.error("Unknown user id, no record found in database for corresponding id "
					+ id);
			throw (new MessageException(
					"Unknown user id, no record found in database for corresponding id "
							+ id));
		}

		System.out.println("User Details:  " + user);

		Criteria criteria = getSession().createCriteria(UserMonetization.class);
		criteria.add(Restrictions.eqOrIsNull("userId", id));
		@SuppressWarnings("unchecked")
		List<UserMonetization> userMonetization = criteria.list();

		if (userMonetization == null) {
			logger.error("userMonetization found to be null, no records found in database");
			throw (new MessageException(
					"userMonetization can't be null, no records found in database"));
		}

		logger.debug("Monetization record fetched: ", userMonetization);
		logger.info("Exiting getByUserId UserMonetizationService");

		return userMonetization;
	}

	@Override
	public List<UserMonetization> getByDate(Timestamp startdate,
			Timestamp endDate) throws MessageException, Exception {

		logger.info("Inside getByDate UserMonetizationService");

		Criteria criteria = getSession().createCriteria(UserMonetization.class);
		criteria.add(Restrictions.between("createdTime", startdate, endDate));
		@SuppressWarnings("unchecked")
		List<UserMonetization> userMonetization = criteria.list();

		if (userMonetization .size()== 0) {
			logger.error("userMonetization found to be null, no records found in database");
			throw (new MessageException(
					"userMonetization found to be null, no records found in database"));
		}

		logger.debug("Monetization record fetched: ", userMonetization);
		logger.info("Exiting getByDate UserMonetizationService");

		return userMonetization;
	}

}
