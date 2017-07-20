package com.ohmuk.folitics.hibernate.repository.like;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ohmuk.folitics.hibernate.entity.like.SentimentLikeCount;
import com.ohmuk.folitics.hibernate.entity.like.SentimentLikeCountId;

/**
 * Repository implementation for entity: {@link SentimentLikeCount}
 * @author Abhishek
 *
 */

@Component
@Repository
public class SentimentLikeCountRepositoryImplementation implements ISentimentLikeCountRepository {

    private static Logger logger = LoggerFactory.getLogger(SentimentLikeCountRepositoryImplementation.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public SentimentLikeCount save(SentimentLikeCount sentimentLikeCount) {

        logger.info("Entered SentimentLikeCount save method");
        logger.debug("Saving like count for sentiment id = " + sentimentLikeCount.getId().getSentiment().getId());

        SentimentLikeCountId id = (SentimentLikeCountId) getSession().save(sentimentLikeCount);
        sentimentLikeCount = (SentimentLikeCount) getSession().get(SentimentLikeCount.class, id);

        logger.info("SentimentLikeCount saved. Exiting save method");
        return sentimentLikeCount;
    }

    @Override
    public SentimentLikeCount update(SentimentLikeCount sentimentLikeCount) {

        logger.info("Entered SentimentLikeCount update method");
        logger.debug("Updating like count for sentiment id = " + sentimentLikeCount.getId().getSentiment().getId());

        sentimentLikeCount = (SentimentLikeCount) getSession().merge(sentimentLikeCount);
        getSession().update(sentimentLikeCount);

        sentimentLikeCount = (SentimentLikeCount) getSession()
                .get(SentimentLikeCount.class, sentimentLikeCount.getId());

        logger.info("SentimentLikeCount updated. Exiting update method");
        return sentimentLikeCount;
    }

    @Override
    public SentimentLikeCount find(SentimentLikeCountId id) {

        logger.info("Entered SentimentLikeCount find method");
        logger.debug("Getting like count for sentiment id = " + id.getSentiment().getId());

        SentimentLikeCount sentimentLikeCount = (SentimentLikeCount) getSession().get(SentimentLikeCount.class, id);

        logger.info("Returning result from database. Exiting update method");
        return sentimentLikeCount;
    }

    @Override
    public List<SentimentLikeCount> findAll() {

        logger.info("Entered SentimentLikeCount findAll method");

        Criteria selectAllCriteria = getSession().createCriteria(SentimentLikeCount.class);
        @SuppressWarnings("unchecked")
        List<SentimentLikeCount> sentimentLikeCounts = selectAllCriteria.list();

        logger.info("Returning all results from database. Exiting findAll method");
        return sentimentLikeCounts;
    }

    public void delete(SentimentLikeCountId id) {

        logger.info("Entered SentimentLikeCount delete method");
        logger.debug("Deleting like count for sentiment id = " + id.getSentiment().getId());

        SentimentLikeCount sentimentLikeCount = (SentimentLikeCount) getSession().get(SentimentLikeCount.class, id);

        logger.info("Deleted SentimentLikeCount. Exiting delete method");
        getSession().delete(sentimentLikeCount);
    }

    @Override
    public SentimentLikeCount findByComponentId(Long sentimentId) {

        logger.info("Entered SentimentLikeCount findByComponentId method");
        logger.debug("Finding like count for sentiment id = " + sentimentId);

        Criteria selectCriteria = getSession().createCriteria(SentimentLikeCount.class);
        selectCriteria.add(Restrictions.eq("id.sentiment.id", sentimentId));
        SentimentLikeCount sentimentLikeCount = (SentimentLikeCount) selectCriteria.uniqueResult();

        /*
         * Query query = getSession().createQuery("FROM SentimentLikeCount S WHERE S.sentimentId = :sentimentId")
         * .setParameter("sentimentId", sentimentId); SentimentLikeCount sentimentLikeCount = (SentimentLikeCount)
         * query.uniqueResult();
         */

        logger.info("Returning SentimentLikeCount. Exiting findByComponentId method");
        return sentimentLikeCount;
    }

}
