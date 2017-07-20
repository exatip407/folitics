package com.ohmuk.folitics.hibernate.repository.air;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ohmuk.folitics.hibernate.entity.air.ResponseAirCount;
import com.ohmuk.folitics.hibernate.entity.air.ResponseAirCountId;

@Component
@Repository
public class ResponseAirCountRepositoryImplementation implements
		IResponseAirCountRepository {

	public static Logger logger = LoggerFactory
			.getLogger(ResponseAirCountRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public ResponseAirCount save(ResponseAirCount responseAirCount) {
		ResponseAirCountId id = (ResponseAirCountId) getSession().save(responseAirCount);
		responseAirCount = (ResponseAirCount) getSession().get(ResponseAirCount.class, id);
		return responseAirCount;
	}

	@Override
	public ResponseAirCount update(ResponseAirCount responseAirCount) {
		responseAirCount = (ResponseAirCount) getSession().merge(responseAirCount);
		getSession().update(responseAirCount);

		responseAirCount = (ResponseAirCount) getSession().get(ResponseAirCount.class,
				responseAirCount.getId());
		return responseAirCount;
	}

	@Override
	public List<ResponseAirCount> findAll() {
		@SuppressWarnings("unchecked")
		List<ResponseAirCount> responseAirCounts = getSession().createQuery(
				"FROM responseaircount").list();
		return responseAirCounts;
	}

	@Override
	public ResponseAirCount findByComponentId(Long responseId) {
		Query query = getSession()
				.createSQLQuery(
						"SELECT * FROM responseaircount s WHERE s.responseId = :responseId")
				.addEntity(ResponseAirCount.class).setParameter("responseId", responseId);

		ResponseAirCount responseAirCount = (ResponseAirCount) query.uniqueResult();
		return responseAirCount;
	}

	@Override
	public ResponseAirCount find(ResponseAirCountId id) {
		ResponseAirCount responseAirCount = (ResponseAirCount) getSession().get(
				ResponseAirCount.class, id);
		return responseAirCount;
	}

	@Override
	public void delete(ResponseAirCountId id) {
		ResponseAirCount responseAirCount = (ResponseAirCount) getSession().get(
				ResponseAirCount.class, id);
		getSession().delete(responseAirCount);

	}

}
