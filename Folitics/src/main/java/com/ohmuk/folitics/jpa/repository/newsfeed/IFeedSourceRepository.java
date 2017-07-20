package com.ohmuk.folitics.jpa.repository.newsfeed;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedSource;

/**
 * 
 * @author Jahid Ali
 *
 */
public interface IFeedSourceRepository extends JpaRepository<FeedSource, Long> {
    @Query("SELECT o FROM FeedSource o WHERE o.name= :name and o.disabled= false")
    List<FeedSource> findByName(@Param("name") String name);

    @Modifying
    @Query("Update FeedSource o set o.disabled= true WHERE o.id=:id ")
    @Transactional
    int disableFeedSource(@Param("id") Long id);
}
