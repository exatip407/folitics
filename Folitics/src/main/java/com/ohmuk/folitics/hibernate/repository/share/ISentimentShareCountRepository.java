package com.ohmuk.folitics.hibernate.repository.share;

import com.ohmuk.folitics.hibernate.entity.share.SentimentShareCount;
import com.ohmuk.folitics.hibernate.entity.share.SentimentShareCountId;

/**
 * Interface for entity: {@link SentimentShareount} repository
 * @author Abhishek
 *
 */
public interface ISentimentShareCountRepository extends IShareCountHibernateRepository<SentimentShareCount> {

    /**
     * This method is for finding the record for entity: {@link SentimentShareCount} by id
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.share.SentimentShareCountId id
     * @return com.ohmuk.folitics.jpa.entity.share.SentimentShareCount
     */	
    public SentimentShareCount find(SentimentShareCountId id);

    /**
     * This method is for deleting the record from database for entity: {@link SentimentShareCount} by id
     * 
     * @author Abhishek
     * @param com.ohmuk.folitics.jpa.entity.share.SentimentShareCountId id
     */
    public void delete(SentimentShareCountId id);

}
