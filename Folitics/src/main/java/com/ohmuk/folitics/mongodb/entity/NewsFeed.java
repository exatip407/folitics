package com.ohmuk.folitics.mongodb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "NewsFeed")
public class NewsFeed {
	@Id
	private String id;
	@Field
	private Long feedSourceId;
	@Field
	private Long feedChannelId;
	@Field
	private Long feedDataId;
	@Field
	@TextIndexed(weight = 2)
	private String link;
	@Field
	private String htmlText;
	@Field
	@TextIndexed
	private String plainText;
	@Field
	@TextIndexed(weight = 3)
	private String category;

	@Field
	private Long sentimentId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getFeedSourceId() {
		return feedSourceId;
	}

	public void setFeedSourceId(Long feedSourceId) {
		this.feedSourceId = feedSourceId;
	}

	public Long getFeedChannelId() {
		return feedChannelId;
	}

	public void setFeedChannelId(Long feedChannelId) {
		this.feedChannelId = feedChannelId;
	}

	public Long getFeedDataId() {
		return feedDataId;
	}

	public void setFeedDataId(Long feedDataId) {
		this.feedDataId = feedDataId;
	}

	public String getHtmlText() {
		return htmlText;
	}

	public void setHtmlText(String htmlText) {
		this.htmlText = htmlText;
	}

	public String getPlainText() {
		return plainText;
	}

	public void setPlainText(String plainText) {
		this.plainText = plainText;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the sentimentId
	 */
	public Long getSentimentId() {
		return sentimentId;
	}

	/**
	 * @param sentimentId
	 *            the sentimentId to set
	 */
	public void setSentimentId(Long sentimentId) {
		this.sentimentId = sentimentId;
	}
}
