package com.ohmuk.folitics.service;

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
import com.ohmuk.folitics.hibernate.entity.LuckyDraw;
import com.ohmuk.folitics.util.DateUtils;

@Service
@Transactional
public class LuckyDrawService implements ILuckyDrawService {
	private static Logger logger = LoggerFactory
			.getLogger(LuckyDrawService.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {

		return sessionFactory.getCurrentSession();
	}

	@Override
	public LuckyDraw read(Long id) throws MessageException, Exception {

		logger.info("Inside read method in luckyDraw service");
		if (id == null) {

			logger.error("Id can't be null, can not read luckyDraw corrosponding to these id"
					+ id);

			throw (new MessageException(
					"Id can't be null, can not read luckyDraw corrosponding to these id :"
							+ id));
		}

		LuckyDraw luckyDraw = (LuckyDraw) getSession().get(LuckyDraw.class, id);
		Hibernate.initialize(luckyDraw.getContestParticipants());
		logger.debug("Luckudraw is found for these id" + id);
		logger.info("Exiting read method from luckyDraw service");
		return luckyDraw;

	}

	@Override
	public List<LuckyDraw> readAll() throws MessageException, Exception {
		logger.info("Inside readAll method in luckyDraw service");

		@SuppressWarnings("unchecked")
		List<LuckyDraw> luckyDraws = getSession().createCriteria(
				LuckyDraw.class).list();

		if (luckyDraws.size() == 0) {
			logger.error("Luckydraw does not exist, No record found in data base");
			throw new MessageException(
					"Luckydraw does not exist, No record fond in data base");
		}
		logger.info("Exiting readAll method from luckyDraw service");

		return luckyDraws;

	}

	@Override
	public LuckyDraw update(LuckyDraw luckyDraw) throws MessageException,
			Exception {

		logger.info("Inside update method in luckyDraw service");

		luckyDraw.setEditTime(DateUtils.getSqlTimeStamp());

		luckyDraw = (LuckyDraw) getSession().merge(luckyDraw);
		getSession().update(luckyDraw);

		logger.debug("LuckyDraw updated" + luckyDraw);

		luckyDraw = (LuckyDraw) getSession().get(LuckyDraw.class,
				luckyDraw.getId());

		logger.info("Exiting update method from luckyDraw service");

		return luckyDraw;
	}

	@Override
	public boolean deleteFromDb(Long luckyDrawId) throws MessageException,
			Exception {
		logger.info("Inside delete method in luckyDraw service");
		if (luckyDrawId == null) {

			logger.error("Id can't be null, can not delete luckyDraw corrosponding to these id"
					+ luckyDrawId);

			throw (new MessageException(
					"Id can't be null, can not delete luckyDraw corrosponding to these id :"
							+ luckyDrawId));
		}

		LuckyDraw luckyDraw = (LuckyDraw) getSession().get(LuckyDraw.class,
				luckyDrawId);
		if (luckyDraw == null) {

			logger.error("Contest for luckyDrawId " + luckyDrawId
					+ " does not exist");

			throw (new MessageException("Contest for luckyDrawId "
					+ luckyDrawId + " does not exist"));
		}
		getSession().delete(luckyDraw);

		logger.info("Exiting delete method from luckyDraw service");

		return true;
	}

	@Override
	public List<LuckyDraw> readAllActiveLuckydraw() throws MessageException,
			Exception {
		logger.info("Inside readAllActiveLuckydraw method in luckyDraw service");

		Criteria criteria = getSession().createCriteria(LuckyDraw.class);

		criteria.add(Restrictions.eq("state", "Active"));

		@SuppressWarnings("unchecked")
		List<LuckyDraw> activeLuckyDraws = criteria.list();

		if (activeLuckyDraws.size() == 0) {
			logger.error("Active Luckydraws does not exist, No record found in data base");
			throw new MessageException(
					"Active Luckydraws does not exist, No record fond in data base");
		}

		logger.debug("size of active LuckyDraw is:" + activeLuckyDraws.size());
		logger.info("Exiting readAllActiveLuckydraw method from luckyDraw service");

		return activeLuckyDraws;
	}

	@Override
	public boolean delete(Long luckyDrawId) throws MessageException, Exception {
		logger.info("Inside delete method in  luckydraw service");
		if (luckyDrawId == null) {

			logger.error("Id can't be null, can not delete luckyDraw corrosponding to these id"
					+ luckyDrawId);

			throw (new MessageException(
					"Id can't be null, can not delete luckyDraw corrosponding to these id :"
							+ luckyDrawId));
		}
		LuckyDraw luckyDraw = (LuckyDraw) getSession().get(LuckyDraw.class,
				luckyDrawId);
		if (luckyDraw == null) {

			logger.error("Contest for luckyDrawId " + luckyDrawId
					+ " does not exist");

			throw (new MessageException("Contest for luckyDrawId "
					+ luckyDrawId + " does not exist"));
		}
		luckyDraw.setState(ContestType.DELETED.getValue());

		luckyDraw = (LuckyDraw) getSession().merge(luckyDraw);
		getSession().update(luckyDraw);

		logger.info("Exiting delete method from luckyDraw service");
		return true;
	}

}
