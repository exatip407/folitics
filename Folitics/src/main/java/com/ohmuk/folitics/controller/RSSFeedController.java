package com.ohmuk.folitics.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohmuk.folitics.component.newsfeed.RSSFeedAggegrator;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedSource;
import com.ohmuk.folitics.mongodb.entity.NewsFeed;

/**
 * @author Jahid
 *
 */
@Controller
@RequestMapping("/rssfeed")
public class RSSFeedController {

    final static Logger logger = LoggerFactory.getLogger(RSSFeedController.class);

    @Autowired
    private volatile RSSFeedAggegrator rssFeedAggegrator;

    @RequestMapping(value = "/addsource", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<FeedSource> addsource(String sourceName, String sourceURL) {
        FeedSource fd;
        try {
            fd = rssFeedAggegrator.addFeedSource(sourceName, sourceURL);

            if (fd != null)
                return new ResponseDto<FeedSource>(true, fd);
        } catch (Exception e) {
            logger.error("Something went wrong while adding sources " + e);
            e.printStackTrace();
        }
        return new ResponseDto<FeedSource>(false);
    }

    @RequestMapping(value = "/disablesource", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<String> disableSource(Long id) {
        try {
            if (rssFeedAggegrator.disableFeedSource(id))
                return new ResponseDto<String>(true, "Feed source disbale with id: " + id);
        } catch (Exception e) {
            logger.error("Something went wrong while adding sources " + e);
            e.printStackTrace();
        }
        return new ResponseDto<String>(false, "Feed source could not be disabled : " + id);
    }

    @RequestMapping(value = "/loadfeed", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<String> loadFeed(String sourceName) {
        List<String> messages = new ArrayList<String>();
        if (rssFeedAggegrator.aggregateFeed(sourceName, messages)) {
            return new ResponseDto<String>(true, messages);
        }
        return new ResponseDto<String>(false, messages);
    }

    @RequestMapping(value = "/clearfeed", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<String> clearfeed(Long id) {
        try {
            if (rssFeedAggegrator.clearFeed(id)) {

                return new ResponseDto<String>(true, "All feed has been cleared for feed source id : " + id);
            }
        } catch (EmptyResultDataAccessException ed) {
            return new ResponseDto<String>(false, "Error while clearing the feed for feed source id: " + id);
        } catch (Exception e) {
            logger.error("Something went wrong while adding sources " + e);
            e.printStackTrace();
        }
        return new ResponseDto<String>(false);
    }

    @RequestMapping(value = "/getallsources", method = RequestMethod.GET)
    public @ResponseBody ResponseDto<List<FeedSource>> getAllFeedSource() {
        try {
            List<FeedSource> fds = rssFeedAggegrator.fidAllFeedSources();
            if (fds != null && !fds.isEmpty())
                return new ResponseDto<List<FeedSource>>(true, fds);
        } catch (Exception e) {
            logger.error("Something went wrong while adding sources " + e);
            e.printStackTrace();
        }
        return new ResponseDto<List<FeedSource>>(false);
    }

    @RequestMapping(value = "/findsource", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<List<FeedSource>> findFeedSource(String sourceName) {
        try {
            List<FeedSource> fds = rssFeedAggegrator.findFeedSoruce(sourceName);
            if (fds != null && !fds.isEmpty())
                return new ResponseDto<List<FeedSource>>(true, fds);
        } catch (Exception e) {
            logger.error("Something went wrong while adding sources " + e);
            e.printStackTrace();
        }
        return new ResponseDto<List<FeedSource>>(false);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseDto<List<NewsFeed>> searchNewsFeed(String keyword) {
        List<NewsFeed> nfs = rssFeedAggegrator.searchText(keyword);
        if (nfs != null && !nfs.isEmpty()) {
            return new ResponseDto<List<NewsFeed>>(true, nfs);
        }
        return new ResponseDto<List<NewsFeed>>(false);
    }
}
