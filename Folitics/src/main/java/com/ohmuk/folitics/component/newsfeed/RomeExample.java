package com.ohmuk.folitics.component.newsfeed;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;


public class RomeExample {

    protected static final Logger LOGGER = LoggerFactory.getLogger(RomeExample.class);

    public static void main(String[] args) {
        URL feedSource;
        try {
            feedSource = new URL("http://www.covers.com/rss/rss_articles.aspx?topic=sport/soccer/usa/mls");
            // feedSource = new URL("http://feeds.reuters.com/reuters/INtopNews");

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedSource));
            // SyndFeed feed = input.build(new XmlReader(new
            // File("E:/TIG/repository/new/ohmuk_folitics_master/src/main/resources/rssfeed/sampleFeed.xml")));

            RSSFeedUtil.printRSSFeedHeader(feed);
            RSSFeedUtil.printRSSFeedItems(feed);

        } catch (MalformedURLException e) {
            LOGGER.error("Error while using ROME", e);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error while using ROME", e);
        } catch (FeedException e) {
            LOGGER.error("Error while using ROME", e);
        } catch (IOException e) {
            LOGGER.error("Error while using ROME", e);
        }

    }
}
