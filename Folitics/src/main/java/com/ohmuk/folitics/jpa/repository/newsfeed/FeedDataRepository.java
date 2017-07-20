package com.ohmuk.folitics.jpa.repository.newsfeed;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedData;

/**
 * Apis for working with attachments
 * 
 * @author Jahid Ali
 *
 */
public interface FeedDataRepository extends JpaRepository<FeedData, Long> {
    FeedData findByLink(String link);
}
