package com.ohmuk.folitics.businessDelegate.interfaces;

import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedChannel;
import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedData;

/**
 * BusinessDeligate interface for {@link FeedData}
 * 
 * @author
 * 
 */
public interface IFeedDataBusinessDeligate {
	
	/**
	 * This method is to add FeedData
	 * 
	 * @author
	 * @return feedData
	 * @throws MessageException 
	 */
	public FeedData create(FeedData feedData) throws MessageException ;

	/**
	 * This method is to read FeedData
	 * 
	 * @author
	 * @return feedData
	 * @throws Exception
	 */
    public FeedData read(Long id) throws MessageException;

    /**
	 * This method is to readAll FeedData
	 * 
	 * @author
	 * @return List<feedData>
	 * @throws Exception
	 */
    public List<FeedData> readAll() throws MessageException;

    /**
	 * This method is to update FeedData
	 * 
	 * @author
	 * @return feedData
	 * @throws Exception
	 */
    public FeedData update(FeedData feedData) throws MessageException;

    /**
	 * This method is to delete FeedData
	 * 
	 * @author
	 * @return feedData
	 * @throws Exception
	 */
    public FeedData delete(Long id) throws MessageException;

    /**
	 * This method is to findByLink FeedData
	 * 
	 * @author
	 * @return feedData
	 * @throws Exception
	 */
    public FeedData findByLink(String link) throws MessageException;
	
}
