package com.ohmuk.folitics.businessDelegate.interfaces;

import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedSource;

/**
 * BusinessDeligate interface for {@link FeedSource}
 * 
 * @author
 * 
 */
public interface IFeedSourceBusinessDeligate {

	/**
	 * This method is to add FeedSource
	 * 
	 * @author
	 * @return feedSource
	 * @throws MessageException 
	 */
	public FeedSource create(FeedSource feedSource) throws MessageException;

	/**
	 * This method is to read FeedSource
	 * 
	 * @author
	 * @return feedSource
	 */
    public FeedSource read(Long id)throws MessageException;

    /**
	 * This method is to read FeedSource
	 * 
	 * @author
	 * @return List<feedSource>
	 */
    public List<FeedSource> readByName(String name)throws MessageException;

    /**
	 * This method is to readAll FeedSource
	 * 
	 * @author
	 * @return List<feedSource>
	 */
    public List<FeedSource> readAll()throws MessageException;

    /**
	 * This method is to update FeedSource
	 * 
	 * @author
	 * @return feedSource
	 */
    public FeedSource update(FeedSource feedSource) throws MessageException;

    /**
	 * This method is to delete FeedSource
	 * 
	 * @author
	 * @return feedSource
	 */
    public FeedSource delete(Long id)throws MessageException;

    /**
	 * This method is to createDefault FeedSource
	 * 
	 * @author
	 * @return feedSource
	 */
    public FeedSource createDefault(String name, String url)throws MessageException;

    /**
	 * This method is to disableFeedSource FeedSource
	 * 
	 * @author
	 * @return Boolean
	 */
    public Boolean disableFeedSource(Long id)throws MessageException;
}
