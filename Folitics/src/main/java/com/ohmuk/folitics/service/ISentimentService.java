package com.ohmuk.folitics.service;

import java.util.List;
import java.util.Set;

import com.ohmuk.folitics.hibernate.entity.Link;
import com.ohmuk.folitics.hibernate.entity.Sentiment;
import com.ohmuk.folitics.hibernate.entity.SentimentOpinionStat;

/**
 * @author Abhishek
 *
 */
public interface ISentimentService extends IBaseService {

	/**
	 * Method is to save {@link Sentiment}
	 * 
	 * @param sentiment
	 * @return Sentiment
	 * @throws Exception
	 */
	public Sentiment save(Sentiment sentiment) throws Exception;

	/**
	 * Method is to get {@link Sentiment} by id
	 * 
	 * @param id
	 * @return Sentiment
	 * @throws Exception
	 */
	public Sentiment read(Long id) throws Exception;

	/**
	 * Method is to get all {@link Sentiment}
	 * 
	 * @return List<Sentiment>
	 * @throws Exception
	 */
	public List<Sentiment> readAll() throws Exception;

	/**
	 * Method is to update sentiment
	 * 
	 * @param sentiment
	 * @return Sentiment
	 * @throws Exception
	 */
	public Sentiment update(Sentiment sentiment) throws Exception;

	/**
	 * Method is to soft delete {@link Sentiment}
	 * 
	 * @param id
	 * @return boolean
	 * @throws Exception
	 */
	public boolean delete(Long id) throws Exception;

	/**
	 * Method is to soft delete {@link Sentiment} by id
	 * 
	 * @param sentiment
	 * @return boolean
	 * @throws Exception
	 */
	public boolean delete(Sentiment sentiment) throws Exception;

	/**
	 * Method is to hard delete {@link Sentiment}
	 * 
	 * @param Sentiment
	 * @return boolean
	 * @throws Exception
	 */
	public boolean deleteFromDB(Sentiment sentiment) throws Exception;

	/**
	 * Method is to update {@link Sentiment} status by id
	 * 
	 * @param id
	 * @param status
	 * @return boolean
	 * @throws Exception
	 */
	public boolean updateSentimentStatus(Long id, String status)
			throws Exception;

	/**
	 * Method is to clone {@link Sentiment}
	 * 
	 * @param sentiment
	 * @return Sentiment
	 * @throws Exception
	 */
	public Sentiment clone(Sentiment sentiment) throws Exception;

	/**
	 * Method is to get all {@link Sentiment} not id sentimentIds
	 * 
	 * @author Mayank Sharma
	 * @param sentimentIds
	 * @return List<Sentiment>
	 * @throws Exception
	 */
	public List<Sentiment> getAllSentimentNotIn(Set<Long> sentimentIds)
			throws Exception;

	/**
	 * Method is to get all sources for {@link Sentiment}
	 * 
	 * @param sentiment
	 * @return List<Link>
	 * @throws Exception
	 */
	public List<Link> getAllSourcesForSentiment(Sentiment sentiment)
			throws Exception;

	public List<Sentiment> findByType(String type)throws Exception;
	
	public Set<Sentiment> getRelatedSentiment(Long sentimentId) throws Exception;
	
	public SentimentOpinionStat getSentimentOpinionStat(Long sentimentId) throws Exception;
}
