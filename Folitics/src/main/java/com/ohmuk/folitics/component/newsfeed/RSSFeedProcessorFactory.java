package com.ohmuk.folitics.component.newsfeed;

import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedSource;

public class RSSFeedProcessorFactory {

    public static final IRSSFeedProcessor getRSSFeedProcessor(String processorType, FeedSource feedSource) {
        if (RSSFeedProcessorEnum.ROME.compareTo(RSSFeedProcessorEnum.getRSSFeedProcessor(processorType)) == 0) {
            return new RomeFeedProcessor(feedSource);
        }
        return null;
    }
}
