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

import com.ohmuk.folitics.hibernate.entity.air.GovtSchemeDataAirCount;
import com.ohmuk.folitics.hibernate.entity.air.GovtSchemeDataCountId;


@Component
@Repository
public class GovtSchemeDataAirCountRepositoryImplementation implements IGovtSchemeDataAirCountRepository {

	public static Logger logger = LoggerFactory.getLogger(OpinionAirCountRepositoryImplementation.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public GovtSchemeDataAirCount save(GovtSchemeDataAirCount GovtSchemeDataAirCount) {
		GovtSchemeDataCountId id = (GovtSchemeDataCountId) getSession().save(GovtSchemeDataAirCount);
        GovtSchemeDataAirCount = (GovtSchemeDataAirCount) getSession().get(GovtSchemeDataAirCount.class, id);
        return GovtSchemeDataAirCount;
	}

	@Override
	public GovtSchemeDataAirCount update(GovtSchemeDataAirCount govtSchemeDataAirCount) {
		govtSchemeDataAirCount = (GovtSchemeDataAirCount) getSession().merge(govtSchemeDataAirCount);
        getSession().update(govtSchemeDataAirCount);

        govtSchemeDataAirCount = (GovtSchemeDataAirCount) getSession()
                .get(GovtSchemeDataAirCount.class, govtSchemeDataAirCount.getId());
        return govtSchemeDataAirCount;
	}

	@Override
	public List<GovtSchemeDataAirCount> findAll() {

		 @SuppressWarnings("unchecked")
	        List<GovtSchemeDataAirCount> sentimentAirCounts = getSession().createQuery("FROM sentimentaircount").list();
	        return sentimentAirCounts;
	}

	@Override
	public GovtSchemeDataAirCount findByComponentId(Long govtSchemeDataId) {
		Query query = getSession()
                .createSQLQuery("SELECT * FROM govtschemedataaircount s WHERE s.govtSchemeDataId = :govtSchemeDataId")
                .addEntity(GovtSchemeDataAirCount.class).setParameter("govtSchemeDataId", govtSchemeDataId);

        GovtSchemeDataAirCount GovtSchemeDataAirCount = (GovtSchemeDataAirCount) query.uniqueResult();
        return GovtSchemeDataAirCount;
	}

	@Override
	public GovtSchemeDataAirCount find(GovtSchemeDataCountId id) {
		GovtSchemeDataAirCount GovtSchemeDataAirCount = (GovtSchemeDataAirCount) getSession().get(GovtSchemeDataAirCount.class, id);
        return GovtSchemeDataAirCount;
	}

	@Override
	public void delete(GovtSchemeDataCountId id) {
		GovtSchemeDataAirCount GovtSchemeDataAirCount = (GovtSchemeDataAirCount) getSession().get(GovtSchemeDataAirCount.class, id);
        getSession().delete(GovtSchemeDataAirCount);
		
	}

}
