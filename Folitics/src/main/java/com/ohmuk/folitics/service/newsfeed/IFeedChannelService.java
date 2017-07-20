package com.ohmuk.folitics.service.newsfeed;

import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedChannel;

/**
 * Service interface for {@link FeedChannel}
 * @author Jahid Ali
 * 
 */

public interface IFeedChannelService {
    public FeedChannel create(FeedChannel feedChannel) throws MessageException, Exception;

    public FeedChannel read(Long id) throws MessageException, Exception;

    public List<FeedChannel> readAll() throws MessageException, Exception;

    public FeedChannel update(FeedChannel feedChannel) throws MessageException, Exception;

    public FeedChannel delete(Long id) throws MessageException, Exception;
}
