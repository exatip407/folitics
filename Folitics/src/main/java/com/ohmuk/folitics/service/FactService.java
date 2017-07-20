package com.ohmuk.folitics.service;

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

import com.ohmuk.folitics.enums.FactType;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Fact;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.util.DateUtils;

@Service
@Transactional
public class FactService implements IFactService {
	private static Logger logger = LoggerFactory.getLogger(FactService.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {

		return sessionFactory.getCurrentSession();
	}

	@Override
	public Fact create(Fact fact) throws MessageException, Exception {

		logger.info("Inside create method in FactService");

		if (fact == null) {

			logger.error("Fact found to be null, can not add fact" + fact);

			throw (new MessageException(
					" Fact found to be null, can not add fact" + fact));

		}
		logger.debug("Fact to be saved whose id is:" + fact.getId()
				+ " and parentType is:" + fact.getParentType());

		Long id = (Long) getSession().save(fact);
		fact = (Fact) getSession().get(Fact.class, id);

		logger.debug("Fact saved" + fact);
		logger.info("Exiting create method from FactService");

		return fact;
	}

	@Override
	public Fact read(Long id) throws MessageException, Exception {

		logger.info("Inside read method in FactService");
		if (id == null) {
			logger.error("Id can't be null, can not read Fact corrosponding to these id"
					+ id);
			throw (new MessageException(
					"Id can't be null, can not read Fact corrosponding to these id :"
							+ id));
		}
		Fact fact = (Fact) getSession().get(Fact.class, id);

		logger.debug("contest found for these id" + id);
		logger.info("Exiting read method from FactService");

		return fact;
	}

	@Override
	public List<Fact> readAll() throws MessageException, Exception {
		logger.info("Inside readAll method in FactService");

		@SuppressWarnings("unchecked")
		List<Fact> facts = getSession().createCriteria(Fact.class).list();

		if (facts.size() == 0) {
			logger.debug("Facts does not exist, No record found in data base");
			throw new MessageException(
					"Facts does not exist, No record found in data base");
		}
		logger.debug("size of  facts is:" + facts.size());
		logger.info("Exiting readAll method from FactService");

		return facts;
	}

	@Override
	public Fact update(Fact fact) throws MessageException, Exception {

		logger.info("Inside update method in FactService ");

		if (fact == null) {

			logger.error("Input data found to be null, can not update fact"
					+ fact);

			throw (new MessageException(
					" Input data found to be null, can not update fact" + fact));

		}
		logger.debug("Fact to be updated whose id is:" + fact.getId()
				+ " and parentType is:" + fact.getParentType());

		fact.setEditTime(DateUtils.getSqlTimeStamp());
		fact.setStatus(FactType.UPDATED.getValue());

		fact = (Fact) getSession().merge(fact);
		getSession().update(fact);

		fact = (Fact) getSession().get(Fact.class, fact.getId());

		logger.debug("Fact updated" + fact);
		logger.info("Exiting update method from FactService");

		return fact;
	}

	@Override
	public boolean deleteFromDB(Long id) throws MessageException, Exception {

		logger.info("Inside deleteFromDb method in FactService");

		if (id == null) {
			logger.error("Id found to be null, can not delete Fact corrosponding to these id"
					+ id);
			throw (new MessageException(
					"Id found to be null, can not delete Fact corrosponding to these id :"
							+ id));
		}
		logger.debug("Fact to be deleted whose id is:" + id);

		Fact fact = (Fact) getSession().get(Fact.class, id);
		getSession().delete(fact);

		logger.debug("fact deleted" + fact);
		logger.info("Exiting deleteFromDb method from FactService");
		return true;

	}

	@Override
	public boolean delete(Long id) throws MessageException, Exception {

		logger.info("Inside delete method in FactService");

		if (id == null) {
			logger.error("Id found to be null, can not delete Fact corrosponding to these id"
					+ id);
			throw (new MessageException(
					"Id found to be null, can not delete Fact corrosponding to these id :"
							+ id));
		}
		logger.debug("Fact to be deleted whose id is:" + id);

		Fact fact = (Fact) getSession().get(Fact.class, id);
		fact.setStatus(FactType.DELETED.getValue());

		fact = (Fact) getSession().merge(fact);
		getSession().update(fact);

		logger.info("Exiting delete method from FactService");
		return true;
	}

	@Override
	public List<Fact> readAll(Long userId) throws MessageException, Exception {

		logger.info("Inside readAll facts by userId method in Fact service");

		if (userId == null) {
			logger.error("userId found to be null, can not read Fact corrosponding to these id"
					+ userId);
			throw (new MessageException(
					"userId found to be null, can not read Fact corrosponding to these id :"
							+ userId));
		}
		logger.debug("Fething user for userId:" + userId);

		User user = (User) getSession().get(User.class, userId);

		logger.debug("User found for these " + userId);

		Criteria criteria = getSession().createCriteria(Fact.class);
		criteria.add(Restrictions.eqOrIsNull("user", user));

		@SuppressWarnings("unchecked")
		List<Fact> facts = criteria.list();

		if (facts.size() == 0) {
			logger.error("Facts does not exist for these userId :" + userId);
			throw new MessageException(
					"Facts does not exist for these userId :" + userId);
		}

		logger.debug("size of facts of these userId is:" + facts.size());
		logger.info("Exiting readAll facts by userId method from fact service");
		return facts;
	}

	@Override
	public List<Fact> readAll(String status) throws MessageException, Exception {

		logger.info("Inside readAll fact by status method in Fact service ");

		if (status == null) {
			logger.error("status found to be null, can not read Fact corrosponding to these status "
					+ status);
			throw (new MessageException(
					"userId found to be null, can not read Fact corrosponding to these status :"
							+ status));
		}

		Criteria criteria = getSession().createCriteria(Fact.class);

		criteria.add(Restrictions.eq("status", status));

		@SuppressWarnings("unchecked")
		List<Fact> facts = criteria.list();

		if (facts.size() == 0) {
			logger.error("Facts does not exist for these status :" + status);
			throw new MessageException(
					"Facts does not exist for these status :" + status);
		}
		logger.debug("size of  facts is:" + facts.size());
		logger.info("Exiting readAll fact by status method in Fact service ");

		return facts;
	}

	@Override
	public List<Fact> readAll(Long parentId, String parentType)
			throws Exception {

		logger.info("Inside readAll fact by parentId and parentType method in Fact service ");

		if (parentId == null) {
			logger.error("parentId found to be null, can not read Fact corrosponding to these parentId "
					+ parentId);
			throw (new MessageException(
					"parentId found to be null, can not read Fact corrosponding to these parentId :"
							+ parentId));
		}

		if (parentType == null) {
			logger.error("parentType found to be null, can not read Fact corrosponding to these parentType "
					+ parentType);
			throw (new MessageException(
					"parentType found to be null, can not read Fact corrosponding to these parentType :"
							+ parentType));
		}

		Criteria criteria = getSession().createCriteria(Fact.class);
		criteria.add(Restrictions.eqOrIsNull("parentId", parentId));
		criteria.add(Restrictions.eqOrIsNull("parentType", parentType));

		@SuppressWarnings("unchecked")
		List<Fact> facts = criteria.list();

		if (facts.size() == 0) {
			logger.error("Facts does not exist for these parentId :" + parentId
					+ " and parentType" + parentType);
			throw new MessageException(
					"Facts does not exist for these parentId :" + parentId
							+ " and parentType" + parentType);
		}

		logger.debug("size of  facts is:" + facts.size());
		logger.info("Exiting readAll fact by parentId and parentType method from Fact service ");
		return facts;
	}

	@Override
	public Fact approveFact(Fact fact) throws MessageException, Exception {

		logger.info("Inside approveFact method in FactService");

		fact = (Fact) getSession().merge(fact);
		getSession().update(fact);

		fact = (Fact) getSession().get(Fact.class, fact.getId());

		logger.info("Exiting approveFact method from FactService");
		return fact;
	}

	@Override
	public Fact rejectFact(Fact fact) throws MessageException, Exception {

		logger.info("Inside rejectFact method in FactService");

		fact = (Fact) getSession().merge(fact);
		getSession().update(fact);

		fact = (Fact) getSession().get(Fact.class, fact.getId());

		logger.info("Exiting rejectFact method from FactService");
		return fact;
	}

	@Override
	public List<Fact> paginationApi(int startId) throws MessageException,
			Exception {

		logger.info("Inside paginationApi method in FactService");

		int endId = startId + 10;

		Criteria criteria = getSession().createCriteria(Fact.class);
		criteria.setFirstResult(startId);
		criteria.setMaxResults(endId);

		@SuppressWarnings("unchecked")
		List<Fact> facts = criteria.list();

		if (facts.size() == 0) {
			logger.error("Facts does not exist between start id:" + startId
					+ " and end id:" + endId);
			throw new MessageException(
					"Facts does not exist between start id  :" + startId
							+ " and end id" + endId);
		}

		logger.debug("Results got from id:" + startId + " to id:" + endId);
		logger.info("Exiting paginationApi method from FactService");

		return facts;

	}

}
