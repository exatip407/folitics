package com.ohmuk.folitics.jpa.repository.newsfeed;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedChannel;

/**
 * Apis for working with attachments
 * 
 * @author Jahid Ali
 *
 */
public interface FeedChannelRepository extends JpaRepository<FeedChannel, Long> {

}
