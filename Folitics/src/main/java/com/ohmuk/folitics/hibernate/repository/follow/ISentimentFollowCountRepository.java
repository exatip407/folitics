package com.ohmuk.folitics.hibernate.repository.follow;

import com.ohmuk.folitics.hibernate.entity.follow.SentimentFollowCount;
import com.ohmuk.folitics.hibernate.entity.follow.SentimentFollowCountId;

/**
 * @author 
 *
 */
public interface ISentimentFollowCountRepository extends IFollowCountHibernateRepository<SentimentFollowCount> {

    /**
     * @param id
     * @return
     */
    public SentimentFollowCount find(SentimentFollowCountId id);

    /**
     * @param id
     */
    public void delete(SentimentFollowCountId id);

}
