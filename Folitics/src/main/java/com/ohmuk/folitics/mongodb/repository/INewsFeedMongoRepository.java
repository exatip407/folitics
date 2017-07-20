package com.ohmuk.folitics.mongodb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ohmuk.folitics.mongodb.entity.NewsFeed;

public interface INewsFeedMongoRepository extends MongoRepository<NewsFeed, String> {
    public NewsFeed findById(String id);

    public List<NewsFeed> findByFeedSourceId(Long feedSourceId);
    
    public List<NewsFeed> findBySentimentId(Long sentimentId);

    public List<NewsFeed> findByFeedChannelId(Long feedSourceId, Long feedChannelId);

    public NewsFeed findByFeedDataId(Long feedSourceId, Long feedChannelId, Long feedDataId);

}
