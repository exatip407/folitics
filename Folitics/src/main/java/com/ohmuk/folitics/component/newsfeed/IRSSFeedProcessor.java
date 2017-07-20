package com.ohmuk.folitics.component.newsfeed;

import java.util.Set;

import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedChannel;
import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedChannelImage;
import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedData;
import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedSource;

public interface IRSSFeedProcessor {

    FeedChannelImage getImage();

    FeedChannel getChannel(FeedSource feedSource);

    Set<FeedData> getData();

}
