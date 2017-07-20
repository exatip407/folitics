package com.ohmuk.folitics.hibernate.repository.air;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ohmuk.folitics.hibernate.entity.air.SentimentAirCount;
import com.ohmuk.folitics.hibernate.entity.air.SentimentCountId;
@Component
@Repository
public class SentimentAirCountRepositoryImplementation implements ISentimentAirCountRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public SentimentAirCount save(SentimentAirCount sentimentAirCount) {

        SentimentCountId id = (SentimentCountId) getSession().save(sentimentAirCount);
        sentimentAirCount = (SentimentAirCount) getSession().get(SentimentAirCount.class, id);
        return sentimentAirCount;
    }

    @Override
    public SentimentAirCount update(SentimentAirCount sentimentAirCount) {

        sentimentAirCount = (SentimentAirCount) getSession().merge(sentimentAirCount);
        getSession().update(sentimentAirCount);

        sentimentAirCount = (SentimentAirCount) getSession()
                .get(SentimentAirCount.class, sentimentAirCount.getId());
        return sentimentAirCount;
    }

    @Override
    public SentimentAirCount find(SentimentCountId id) {

        SentimentAirCount sentimentAirCount = (SentimentAirCount) getSession().get(SentimentAirCount.class, id);
        return sentimentAirCount;
    }

    @Override
    public List<SentimentAirCount> findAll() {

        @SuppressWarnings("unchecked")
        List<SentimentAirCount> sentimentAirCounts = getSession().createQuery("FROM sentimentaircount").list();
        return sentimentAirCounts;
    }

    public void delete(SentimentCountId id) {

        SentimentAirCount sentimentAirCount = (SentimentAirCount) getSession().get(SentimentAirCount.class, id);
        getSession().delete(sentimentAirCount);
    }

    @Override
    public SentimentAirCount findByComponentId(Long sentimentId) {

        Query query = getSession()
                .createSQLQuery("SELECT * FROM sentimentaircount s WHERE s.sentimentId = :sentimentId")
                .addEntity(SentimentAirCount.class).setParameter("sentimentId", sentimentId);

        SentimentAirCount sentimentAirCount = (SentimentAirCount) query.uniqueResult();
        return sentimentAirCount;
    }

}
