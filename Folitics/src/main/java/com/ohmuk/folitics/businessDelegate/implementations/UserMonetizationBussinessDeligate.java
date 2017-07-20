package com.ohmuk.folitics.businessDelegate.implementations;

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
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.businessDelegate.interfaces.IUserMonetizationBusinessDeligate;
import com.ohmuk.folitics.enums.Module;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.LuckyDraw;
import com.ohmuk.folitics.hibernate.entity.MonetizationConfig;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.UserMonetization;
import com.ohmuk.folitics.service.IUserMonetizationService;

@Component
@Transactional
public class UserMonetizationBussinessDeligate implements
		IUserMonetizationBusinessDeligate {

	static final Logger logger = LoggerFactory
			.getLogger(UserMonetizationBussinessDeligate.class);

	@Autowired
	private IUserMonetizationService userMonetizationService;

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {

		return sessionFactory.getCurrentSession();
	}

	@Override
	public UserMonetization addAction(UserMonetization userMonetization)
			throws MessageException, Exception {

		logger.info("Inside addAction UserMonetizationBussinessDeligate");

		Criteria criteria_monetization = getSession().createCriteria(
				MonetizationConfig.class);
		criteria_monetization.add(Restrictions.eqOrIsNull("module",
				userMonetization.getModule()));
		criteria_monetization.add(Restrictions.eqOrIsNull("action",
				userMonetization.getAction()));
		criteria_monetization.add(Restrictions.eqOrIsNull("componentType",
				userMonetization.getComponentType()));
		MonetizationConfig monetizationConfig = (MonetizationConfig) criteria_monetization
				.uniqueResult();

		if (monetizationConfig == null) {
			logger.error("MonetizationConfig found to be null i.e. no records found in database for respectively component Type: "
					+ userMonetization.getComponentType()
					+ " and module: "
					+ userMonetization.getModule()
					+ " for action: "
					+ userMonetization.getAction());
			throw (new MessageException(
					"MonetizationConfig found to be null i.e. no records found in database for respectively component Type: "
							+ userMonetization.getComponentType()
							+ " and module: "
							+ userMonetization.getModule()
							+ " for action: " + userMonetization.getAction()));
		}

		if (userMonetization.getModule() != Module.CONTEST.getValue()) {

			Long points = monetizationConfig.getPoints();
			userMonetization.setPoints(points);
			User user = (User) getSession().get(User.class,
					userMonetization.getUserId());

			if (user == null) {
				logger.error("Unknown user id, no record found in database for corresponding id "
						+ userMonetization.getUserId());
				throw (new MessageException(
						"Unknown user id, no record found in database for corresponding id "
								+ userMonetization.getUserId()));
			}

			userMonetization = userMonetizationService
					.addAction(userMonetization);

			Double currentPoints = user.getPoints();

			currentPoints = currentPoints + points;

			logger.debug("User's Current points: " + currentPoints);

			user.setPoints(currentPoints);
			getSession().update(user);

			logger.info("Exiting addAction UserMonetizationBussinessDeligate");

		} else {

			Criteria criteria_luckyDraw = getSession().createCriteria(
					LuckyDraw.class);
			criteria_luckyDraw.add(Restrictions.eqOrIsNull("id",
					userMonetization.getActionComponentId()));
			LuckyDraw luckyDraw = (LuckyDraw) criteria_luckyDraw.uniqueResult();

			Long monetizationPoint = monetizationConfig.getPoints();
			Long points = luckyDraw.getParticipationPoints();
			userMonetization.setPoints(points);
			points = points * monetizationPoint;

			User user = (User) getSession().get(User.class,
					userMonetization.getUserId());

			if (user == null) {
				logger.error("Unknown user id, no record found in database for corresponding id "
						+ userMonetization.getUserId());
				throw (new MessageException(
						"Unknown user id, no record found in database for corresponding id "
								+ userMonetization.getUserId()));
			}

			userMonetization = userMonetizationService
					.addAction(userMonetization);

			Double currentPoints = user.getPoints();

			if (currentPoints >= points) {
				logger.error("You don't have sufficient point to take participate in contest");
				throw (new MessageException(
						"You don't have sufficient point to take participate in contest"));
			}

			currentPoints = currentPoints + points;

			logger.debug("User's current points: " + currentPoints);

			user.setPoints(currentPoints);
			getSession().update(user);

			logger.info("Exiting addContest UserMonetizationBussinessDeligate");
		}

		return userMonetization;
	}

	/*
	 * @Override public UserMonetization addContest(User user,LuckyDraw
	 * luckyDraw) throws MessageException, Exception {
	 * 
	 * logger.info("Inside addContest UserMonetizationBussinessDeligate");
	 * 
	 * UserMonetization userMonetization =
	 * userMonetizationService.addContest(user,luckyDraw);
	 * 
	 * logger.info("Exiting addContest UserMonetizationBussinessDeligate");
	 * 
	 * return userMonetization; }
	 */

	@Override
	public UserMonetization getById(Long id) throws MessageException, Exception {

		logger.info("Inside getById UserMonetizationBussinessDeligate");

		UserMonetization userMonetization = userMonetizationService.getById(id);

		logger.info("Exiting getById UserMonetizationBussinessDeligate");

		return userMonetization;
	}

	@Override
	public List<UserMonetization> getByUserId(Long id) throws MessageException,
			Exception {

		logger.info("Inside getByUserId UserMonetizationBussinessDeligate");

		List<UserMonetization> userMonetization = userMonetizationService
				.getByUserId(id);

		logger.info("Exiting getByUserId UserMonetizationBussinessDeligate");

		return userMonetization;
	}

	@Override
	public List<UserMonetization> getByDate(Timestamp startDate,
			Timestamp endDate) throws MessageException, Exception {

		logger.info("Inside getByDate UserMonetizationBussinessDeligate");

		List<UserMonetization> userMonetization = userMonetizationService
				.getByDate(startDate, endDate);

		logger.info("Exiting getByDate UserMonetizationBussinessDeligate");

		return userMonetization;
	}
}