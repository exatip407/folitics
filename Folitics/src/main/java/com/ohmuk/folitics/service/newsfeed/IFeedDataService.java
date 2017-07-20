package com.ohmuk.folitics.service.newsfeed;

import java.util.List;

import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedData;

/**
 * Service interface for {@link FeedData}
 * @author Jahid Ali
 * 
 */

public interface IFeedDataService {
    public FeedData create(FeedData feedData) throws MessageException;

    public FeedData read(Long id) throws MessageException;

    public List<FeedData> readAll() throws MessageException;

    public FeedData update(FeedData feedData) throws MessageException;

    public FeedData delete(Long id) throws MessageException;

    public FeedData findByLink(String link) throws MessageException;

}
