package com.ohmuk.folitics.service.newsfeed;

import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedSource;

/**
 * Service interface for {@link FeedSource}
 * @author Jahid Ali
 * 
 */

public interface IFeedSourceService {
    public FeedSource create(FeedSource feedSource) throws MessageException;

    public FeedSource read(Long id) throws MessageException;

    public List<FeedSource> readByName(String name) throws MessageException;

    public List<FeedSource> readAll() throws MessageException;

    public FeedSource update(FeedSource feedSource) throws MessageException;

    public FeedSource delete(Long id) throws MessageException;

    public FeedSource createDefault(String name, String url) throws MessageException;

    public Boolean disableFeedSource(Long id) throws MessageException;

}
