package com.ohmuk.folitics.hibernate.repository.verdict;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;

/**
 * Hibernate repository implementation for {@link Verdict}
 * 
 * @author Abhishek
 *
 */
@Component
@Repository
public class VerdictRepository implements IVerdictRepository {

	private static Logger logger = LoggerFactory
			.getLogger(VerdictRepository.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.hibernate.repository.verdict.IVerdictRepository#save
	 * (com.ohmuk.folitics.jpa.entity.verdict .Verdict)
	 */
	@Override
	public Verdict save(Verdict verdict) {

		logger.debug("Entered VerdictRepository save method");
		logger.debug("Trying to save verdict for sentiment id = "
				+ verdict.getSentiment().getId());

		Long id = (Long) getSession().save(verdict);

		logger.debug("Saved verdict object and got id = " + id
				+ " and now getting verdict object from database");
		verdict = (Verdict) getSession().get(Verdict.class, id);

		logger.debug("Got verdict object from database. Exiting VerdictRepository save method");
		return verdict;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.hibernate.repository.verdict.IVerdictRepository#find
	 * (java.lang.Long)
	 */
	@Override
	public Verdict find(Long id) {

		logger.debug("Entered VerdictRepository find method");
		logger.debug("Trying to get verdict with id = " + id);

		Verdict verdict = (Verdict) getSession().get(Verdict.class, id);

		logger.debug("Got verdict object from database. Exiting VerdictRepository find method");

		return verdict;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.hibernate.repository.verdict.IVerdictRepository#findAll
	 * ()
	 */
	@Override
	public List<Verdict> findAll() {

		logger.debug("Entered VerdictRepository findAll method");
		logger.debug("Trying to get all verdicts");

		Criteria selectAllCriteria = getSession().createCriteria(Verdict.class);
		@SuppressWarnings("unchecked")
		List<Verdict> verdicts = selectAllCriteria.list();

		logger.debug("Got all verdict objects from database. Exiting VerdictRepository findAll method");

		return verdicts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ohmuk.folitics.hibernate.repository.verdict.IVerdictRepository#
	 * getVerdictForSentiment(java.lang.Long)
	 */
	@Override
	public Verdict getVerdictForSentiment(Long sentimentId) {

		logger.debug("Entered VerdictRepository getVerdictForSentiment method");
		logger.debug("Trying to get verdict with sentiment id = " + sentimentId);

		Criteria selectCriteria = getSession().createCriteria(Verdict.class);
		selectCriteria.add(Restrictions.eq("sentiment.id", sentimentId));
		Verdict verdict = (Verdict) selectCriteria.uniqueResult();

		logger.debug("Got verdict object from database. Exiting VerdictRepository getVerdictForSentiment method");

		return verdict;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.hibernate.repository.verdict.IVerdictRepository#update
	 * (com.ohmuk.folitics.jpa.entity.verdict .Verdict)
	 */
	@Override
	public Verdict update(Verdict verdict) {

		logger.debug("Entered VerdictRepository update method");
		logger.debug("Merging the object first with id = " + verdict.getId()
				+ " and sentiment id = " + verdict.getSentiment().getId());

		verdict = (Verdict) getSession().merge(verdict);

		logger.debug("Now updating the verdict object in database with id = "
				+ verdict.getId() + " and sentiment id = "
				+ verdict.getSentiment().getId());
		getSession().update(verdict);

		logger.debug("Getting the verdict object from database");

		verdict = (Verdict) getSession().get(Verdict.class, verdict.getId());

		logger.debug("Got verdict object from database. Exiting VerdictRepository update method");

		return verdict;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.hibernate.repository.verdict.IVerdictRepository#delete
	 * (java.lang.Long)
	 */
	@Override
	public void delete(Long id) {

		logger.debug("Entered VerdictRepository delete method");
		logger.debug("Trying to get verdict with id = " + id);

		Verdict verdict = (Verdict) getSession().get(Verdict.class, id);

		logger.debug("Now trying to delete the Verdict object with id = "
				+ verdict.getId() + " and sentimentId = "
				+ verdict.getSentiment().getId());

		getSession().delete(verdict);

		logger.debug("Deleted verdict object from database. Exiting VerdictRepository delete method");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ohmuk.folitics.hibernate.repository.verdict.IVerdictRepository#delete
	 * (com.ohmuk.folitics.jpa.entity.verdict .Verdict)
	 */
	@Override
	public void delete(Verdict verdict) {

		logger.debug("Entered VerdictRepository delete method");
		logger.debug("Trying to get verdict with id = " + verdict.getId()
				+ " and sentiment id = " + verdict.getSentiment().getId());

		verdict = (Verdict) getSession().get(Verdict.class, verdict.getId());

		logger.debug("Now trying to delete the Verdict object with id = "
				+ verdict.getId() + " and sentimentId = "
				+ verdict.getSentiment().getId());

		getSession().delete(verdict);

		logger.debug("Deleted verdict object from database. Exiting VerdictRepository delete method");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Verdict> readLatestNVerdicts(Integer noOfSentiments) {

		logger.debug("Entered VerdictRepository delete method");

		Criteria criteria = getSession()
				.createCriteria(Verdict.class, "verdict")
				.createAlias("verdict.sentiment", "sentiment")
				.addOrder(Order.desc("sentiment.createTime"))
				.setProjection(
						Projections.projectionList().add(
								Projections.groupProperty("verdict.id")))
				.setMaxResults(noOfSentiments);

		List<Long> list = criteria.list();

		List<Verdict> verdicts = new ArrayList<Verdict>();

		for (Long id : list) {

			verdicts.add(find(id));
		}

		logger.debug("Deleted verdict object from database. Exiting VerdictRepository delete method");

		return verdicts;
	}

}
