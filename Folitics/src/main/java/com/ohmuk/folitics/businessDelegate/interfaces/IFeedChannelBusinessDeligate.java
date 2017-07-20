package com.ohmuk.folitics.businessDelegate.interfaces;

import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedChannel;

/**
 * BusinessDeligate interface for {@link FeedChannel}
 * 
 * @author
 * 
 */

public interface IFeedChannelBusinessDeligate {

	/**
	 * This method is to add FeedChannel
	 * 
	 * @author
	 * @return feedChannel
	 * @throws Exception
	 */
	public FeedChannel create(FeedChannel feedChannel) throws MessageException, Exception;

	/**
	 * This method is to read FeedChannel
	 * 
	 * @author
	 * @return feedChannel
	 * @throws Exception
	 */
	public FeedChannel read(Long id) throws MessageException, Exception;

	/**
	 * This method is to readAll FeedChannel
	 * 
	 * @author
	 * @return List<feedChannel>
	 * @throws Exception
	 */
	public List<FeedChannel> readAll() throws MessageException, Exception;

	/**
	 * This method is to update FeedChannel
	 * 
	 * @author
	 * @return feedChannel
	 * @throws Exception
	 */
	public FeedChannel update(FeedChannel feedChannel) throws MessageException, Exception;

	/**
	 * This method is to delete FeedChannel
	 * 
	 * @author
	 * @return feedChannel
	 * @throws Exception
	 */
	public FeedChannel delete(Long id) throws MessageException, Exception;

}
