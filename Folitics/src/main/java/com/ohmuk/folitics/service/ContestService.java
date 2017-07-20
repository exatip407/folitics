package com.ohmuk.folitics.service;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.enums.ContestType;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Contest;
import com.ohmuk.folitics.hibernate.entity.LuckyDraw;
import com.ohmuk.folitics.util.DateUtils;

@Service
@Transactional
public class ContestService implements IContestService {

	private static Logger logger = LoggerFactory
			.getLogger(ContestService.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {

		return sessionFactory.getCurrentSession();
	}

	@Override
	public Contest create(Contest contest) throws MessageException, Exception {

		logger.info("Inside create method in ContestService");
		logger.debug("Contest  to be saved whose id is:" + contest.getId()
				+ " and state:" + contest.getState());

		Long id = (Long) getSession().save(contest);
		contest = (Contest) getSession().get(Contest.class, id);

		logger.debug("contest saved" + contest);
		logger.info("Exiting create method from ContestService");

		return contest;

	}

	@Override
	public List<Contest> readAll() throws MessageException, Exception {

		logger.info("Inside readAll method in ContestService");
		Contest contest = new Contest();

		@SuppressWarnings("unchecked")
		List<Contest> contests = getSession().createCriteria(Contest.class)
				.list();
		Hibernate.initialize(contest.getLuckyDraw());

		if (contests.size() == 0) {
			logger.error("Contest does not exist, No record found in data base");
			throw new MessageException(
					"Contest does not exist, No record fond in data base");
		}
		logger.info("Exiting readAll method from ContestService");
		return contests;
	}

	@Override
	public Contest read(Long id) throws MessageException, Exception {

		logger.info("Inside read method in contest service");
		if (id == null) {

			logger.error("Id can't be null, can not read contest corrosponding to these id"
					+ id);

			throw (new MessageException(
					"Id can't be null, can not read contest corrosponding to these id :"
							+ id));
		}

		Contest contest = (Contest) getSession().get(Contest.class, id);
		// Hibernate.initialize(contest.getLuckyDraw());
		logger.debug("contest found for these id" + id);
		logger.info("Exiting read method from contest service");

		return contest;
	}

	@Override
	public Contest update(Contest contest) throws MessageException, Exception {

		logger.info("Inside update method in contest service ");

		if (contest == null) {

			logger.error("Input data can't be null, can not update data"
					+ contest);

			throw (new MessageException(
					" Input data can't be null, can not update data" + contest));

		}

		contest.setEditTime(DateUtils.getSqlTimeStamp());

		getSession().update(contest);

		contest = (Contest) getSession().get(Contest.class, contest.getId());

		logger.info("Exiting update method from contest service");

		return contest;

	}

	@Override
	public boolean deleteFromDb(Long id) throws MessageException, Exception {

		logger.info("Inside deleteFromDb method in contest service");

		if (id == null) {

			logger.error("Id can't be null, can not delete contest corrosponding to these id"
					+ id);

			throw (new MessageException(
					"Id can't be null, can not delete contest corrosponding to these id :"
							+ id));
		}

		Contest contest = (Contest) getSession().get(Contest.class, id);

		getSession().delete(contest);

		logger.info("Exiting deleteFromDb method from contest service");
		return true;

	}

	@Override
	public LuckyDraw addParticipants(LuckyDraw luckyDraw)
			throws MessageException, Exception {

		logger.info("Inside addParticipants method in contest service");

		getSession().update(luckyDraw);

		logger.info("Exiting addParticipants method from contest service");

		return luckyDraw;

	}

	@Override
	public List<Contest> readAllActiveContest() throws MessageException,
			Exception {

		logger.info("Inside readAllActiveContest method");

		Criteria criteria = getSession().createCriteria(Contest.class);

		criteria.add(Restrictions.eq("state", "Active"));

		@SuppressWarnings("unchecked")
		List<Contest> activeContest = criteria.list();

		if (activeContest.size() == 0) {
			logger.error("Active Contest does not exist, No record found in data base");
			throw new MessageException(
					"Active Contest does not exist, No record fond in data base");
		}

		logger.debug("size of active contest is:" + activeContest.size());
		logger.info("Exiting readAllActiveContest method");

		return activeContest;
	}

	@Override
	public LuckyDraw genrateWinner(LuckyDraw luckyDraw)
			throws MessageException, Exception {
		logger.info("Inside gentateWinner method in contest service");

		getSession().update(luckyDraw);

		logger.info("Exiting gentateWinner method from contest service");

		return luckyDraw;
	}

	@Override
	public boolean delete(Long id) throws MessageException, Exception {

		logger.info("Inside delete method in contest service");

		if (id == null) {

			logger.error("Id can't be null, can not delete contest corrosponding to these id"
					+ id);

			throw (new MessageException(
					"Id can't be null, can not delete contest corrosponding to these id :"
							+ id));
		}

		Contest contest = (Contest) getSession().get(Contest.class, id);
		contest.setState(ContestType.DELETED.getValue());

		contest = (Contest) getSession().merge(contest);
		getSession().update(contest);

		logger.info("Exiting delete method from contest service");
		return true;
	}

	@Override
	public List<Contest> getByDate(Timestamp startDate, Timestamp endDate)
			throws MessageException, Exception {
		logger.info("Inside getByDate method in ContestService");

		Criteria criteria = getSession().createCriteria(Contest.class);
		criteria.add(Restrictions.between("creationTime", startDate, endDate));
		@SuppressWarnings("unchecked")
		List<Contest> contests = criteria.list();

		if (contests.size() == 0) {
			logger.error("Contests does not exist, no records found in database");
			throw (new MessageException(
					"Contests does not exist, no records found in database"));
		}

		logger.debug("contests fetched, size of contests is:" + contests.size());
		logger.info("Exiting getByDate  method from ContestService");

		return contests;
	}

}