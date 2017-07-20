package com.ohmuk.folitics.component.newsfeed.scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewsFeedScrapper {
    protected static final Logger LOGGER = LoggerFactory.getLogger(NewsFeedScrapper.class);

    public static final String getPlainText(Document document) {
        return document.text();
    }

    public static final Document scrap(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            return document;
        } catch (Exception e) {
            LOGGER.error("Could not connect to server with link : "+url, e);
        }
        return null;
    }

    public static final String getHtmlText(Document document) {
        return document.html().toString();
    }
}
