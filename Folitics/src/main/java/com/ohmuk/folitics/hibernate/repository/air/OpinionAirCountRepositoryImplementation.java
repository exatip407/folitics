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

import com.ohmuk.folitics.hibernate.entity.air.OpinionAirCount;
import com.ohmuk.folitics.hibernate.entity.air.OpinionCountId;

@Component
@Repository
public class OpinionAirCountRepositoryImplementation implements IOpinionAirCountRepository {

	public static Logger logger = LoggerFactory.getLogger(OpinionAirCountRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public OpinionAirCount save(OpinionAirCount opinionAirCount) {

		logger.debug("Entered OpinionAirCount save method");
		logger.debug("Saving air count for opinion id = " + opinionAirCount.getId().getOpinion().getId());

		OpinionCountId id = (OpinionCountId) getSession().save(opinionAirCount);
		opinionAirCount = (OpinionAirCount) getSession().get(OpinionAirCount.class, id);

		logger.debug("OpinionAirCount saved. Exiting save method");
		return opinionAirCount;
	}

	@Override
	public OpinionAirCount update(OpinionAirCount opinionAirCount) {

		logger.debug("Entered OpinionAirCount update method");
		logger.debug("Updating air count for opinion id = " + opinionAirCount.getId().getOpinion().getId());

		opinionAirCount = (OpinionAirCount) getSession().merge(opinionAirCount);
		getSession().update(opinionAirCount);

		opinionAirCount = (OpinionAirCount) getSession().get(OpinionAirCount.class, opinionAirCount.getId());

		logger.debug("Updated OpinionAirCount. Exiting update method");
		return opinionAirCount;
	}

	@Override
	public List<OpinionAirCount> findAll() {

		logger.debug("Entered OpinionAirCount findAll method");
		logger.debug("Getting all air count");
		@SuppressWarnings("unchecked")
		List<OpinionAirCount> sentimentAirCounts = getSession().createQuery("FROM opinionaircount").list();

		logger.debug("Returning all OpinionAirCount. Exiting findAll method");
		return sentimentAirCounts;
	}

	@Override
	public OpinionAirCount findByComponentId(Long opinionId) {

		logger.debug("Entered OpinionAirCount findByComponentId method");
		logger.debug("Getting air count for opinion id = " + opinionId);
		Query query = getSession()
				.createSQLQuery("SELECT * FROM opinionaircount s WHERE s.sentimentId = :sentimentId")
				.addEntity(OpinionAirCount.class).setParameter("sentimentId", opinionId);

		OpinionAirCount opinionAirCount = (OpinionAirCount) query.uniqueResult();
		logger.debug("Returning OpinionAirCount. Exiting findByComponentId method");
		return opinionAirCount;
	}

	@Override
	public OpinionAirCount find(OpinionCountId opinionId) {

		logger.debug("Entered OpinionAirCount find method");
		logger.debug("Getting air count for opinion id = " + opinionId.getOpinion().getId());
		OpinionAirCount opinionAirCount = (OpinionAirCount) getSession().get(OpinionAirCount.class, opinionId);

		logger.debug("Returning OpinionAirCount. Exiting find method");
		return opinionAirCount;
	}

	@Override
	public void delete(OpinionCountId id) {

		logger.debug("Entered OpinionAirCount delete method");
		logger.debug("Deleting air count for opinion id = " + id.getOpinion().getId());
		OpinionAirCount opinionAirCount = (OpinionAirCount) getSession().get(OpinionAirCount.class, id);
		getSession().delete(opinionAirCount);

		logger.debug("Deleted OpinionAirCount. Exiting delete method");

	}

}
