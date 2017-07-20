package com.ohmuk.folitics.mongodb.service;

import java.util.List;

import com.ohmuk.folitics.mongodb.entity.NewsFeed;

/**
 * Service interface for {@link NewsFeed}
 * 
 * @author Jahid Ali
 * 
 */

public interface INewsFeedMongodbService {
	public NewsFeed create(NewsFeed newsFeed);

	public NewsFeed read(String id);

	public List<NewsFeed> readAll();

	public NewsFeed update(NewsFeed newsFeed);

	public NewsFeed delete(String id);

	public List<NewsFeed> searchText(String keyWord);

	public NewsFeed findById(String id);

	public List<NewsFeed> findByFeedSourceId(Long feedSourceId);

	public List<NewsFeed> findBySentimentId(Long sentimentId);

	public List<NewsFeed> findByFeedChannelId(Long feedSourceId,
			Long feedChannelId);

	public List<NewsFeed> findByFeedDataId(Long feedSourceId,
			Long feedChannelId, Long feedDataId);

	public int deleteByFeedSourceId(Long feedSourceId);

	public int deleteByFeedChannelId(Long feedSourceId, Long feedChannelId);

	public int deleteByFeedDataId(Long feedSourceId, Long feedChannelId,
			Long feedDataId);

	public NewsFeed updateWithSentiment(String newsFeedId, Long sentimentId);

	public void saveNewsFeed();

}
