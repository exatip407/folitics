package com.ohmuk.folitics.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.hibernate.entity.Opinion;
import com.ohmuk.folitics.hibernate.entity.Response;
import com.ohmuk.folitics.hibernate.entity.User;

/**
 * @author Abhishek
 *
 */
@Service
@Transactional
public class ResponseService implements IResponseService {

	private static Logger logger = LoggerFactory.getLogger(PollService.class);

	@Autowired
	private SessionFactory _sessionFactory;

	private Session getSession() {
		return _sessionFactory.getCurrentSession();
	}

	@Override
	public Response create(Response response) throws Exception {
		logger.info("Inside ResponseService create method");
		Long id = (Long) getSession().save(response);
		logger.info("Exiting from ResponseService create method");
		return getResponseById(id);
	}

	@Override
	public Response getResponseById(Long id) throws Exception {
		logger.info("Inside ResponseService getResponseById method");
		Response response = (Response) getSession().get(Response.class, id);
		logger.info("Exiting from ResponseService getResponseById method");
		return response;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Response> readAll() throws Exception {
		logger.info("Inside ResponseService readAll method");
		List<Response> responses = getSession().createCriteria(Response.class).list();
		logger.info("Exiting from ResponseService readAll method");
		return responses;
	}

	@Override
	public Response update(Response response) throws Exception {
		logger.info("Inside ResponseService update method");
		getSession().update(response);
		logger.info("Exiting from ResponseService update method");
		return response;
	}

	@Override
	public List<Response> getByOpinionId(Long id) throws Exception {
		logger.info("Inside ResponseService getByOpinionId method");
		Opinion opinion = (Opinion) getSession().get(Opinion.class, id);
		Criteria criteria = getSession().createCriteria(Response.class);
		criteria.add(Restrictions.eqOrIsNull("opinion", opinion));
		List<Response> response = criteria.list();
		logger.info("Exiting from ResponseService getByOpinionId method");
		return response;
	}

	@Override
	public List<Response> getByUserId(Long id) throws Exception {
		logger.info("Inside ResponseService getByUserId method");
		User user = (User) getSession().get(User.class, id);
		Criteria criteria = getSession().createCriteria(Response.class);
		criteria.add(Restrictions.eqOrIsNull("user", user));
		List<Response> response = criteria.list();
		logger.info("Exiting from ResponseService getByUserId method");
		return response;
	}

	@Override
	public boolean delete(Long id) throws Exception {
		logger.info("Inside ResponseService delete method");
		Response response = getResponseById(id);
		response.setState(ComponentState.DELETED.getValue());
		getSession().update(response);
		logger.info("Exiting from ResponseService delete method");
		return true;
	}

	@Override
	public boolean delete(Response response) throws Exception {
		logger.info("Inside ResponseService delete method");
		response = getResponseById(response.getId());
		response.setState(ComponentState.DELETED.getValue());
		getSession().update(response);
		logger.info("Exiting from ResponseService delete method");
		return true;
	}

	@Override
	public boolean deleteFromDBById(Long id) throws Exception {
		logger.info("Inside ResponseService deleteFromDBById method");
		Response response = getResponseById(id);
		getSession().delete(response);
		logger.info("Exiting from ResponseService deleteFromDBById method");
		return true;
	}

	@Override
	public boolean deleteFromDB(Response response) throws Exception {
		logger.info("Inside ResponseService deleteFromDB method");
		response = getResponseById(response.getId());
		getSession().delete(response);
		logger.info("Exiting from ResponseService deleteFromDB method");
		return true;
	}

	@Override
	public List<Response> userPointsAggregations(Response response,Double userPoints) throws Exception {
		logger.info("Inside ResponseService userPointsAggregations method");
		Long userId =response.getUser().getId();
		getSession().update(getSession().get(User.class, userId));
		List<Response> responses = readAll();
		logger.info("Exiting from ResponseService deleteFromDB method");
		return responses;
	}
}
