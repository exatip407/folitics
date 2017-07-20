package com.ohmuk.folitics.hibernate.entity.newsfeed;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ohmuk.folitics.component.newsfeed.RSSFeedUtil;
import com.ohmuk.folitics.util.DateUtils;

/**
 * Entity implementation class for Entity: NewsData
 * @author Jahid Ali
 */
@Entity
@Table(name = "feeddata", indexes = { @Index(name = "feeddata_index_link", columnList = "link", unique = true),
        @Index(name = "feeddata_index_link", columnList = "link", unique = true),
        @Index(name = "feeddata_index_uri", columnList = "uri", unique = true) })
@NamedQuery(name = "FeedData.findAll", query = "SELECT r FROM FeedData r")
public class FeedData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private Timestamp edited;

    @Column(nullable = false)
    private Timestamp timestamp;

    @Column(nullable = true, length = RSSFeedUtil.MAX_LENGTH_LINK)
    private String uri;

    @Column(nullable = true, length = RSSFeedUtil.MAX_LENGTH_TITLE)
    private String title;

    @Column(nullable = true, length = RSSFeedUtil.MAX_LENGTH_LINK)
    private String link;

    @Column(nullable = true, length = RSSFeedUtil.MAX_LENGTH_DESCRIPTION)
    private String description;

    @Lob
    @Column(nullable = true)
    private String contents;

    @Column(nullable = true)
    private Date publishedDate;

    @Column(nullable = true)
    private Date updatedDate;

    @Column(nullable = true)
    private String author;

    @Lob
    @Column(nullable = true)
    private String feedItemXML;

    @Lob
    @Column(nullable = true)
    private String category;

    @Lob
    @Column(nullable = true)
    private String tags;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedchannelId", nullable = false)
    @JsonBackReference(value = "feedChannel-data")
    private FeedChannel feedChannel;

    @Column(nullable = false)
    private Boolean scrapError = false;

    @Column(nullable = false)
    private Boolean scrapped = false;

    @Column(nullable = false)
    private Integer attemps;

    public FeedData() {
        setTimestamp(DateUtils.getSqlTimeStamp());
        setEdited(DateUtils.getSqlTimeStamp());
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getEdited() {
        return this.edited;
    }

    public void setEdited(Timestamp edited) {
        this.edited = edited;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = RSSFeedUtil.truncateLength(RSSFeedUtil.removeHTML(title), RSSFeedUtil.MAX_LENGTH_TITLE);
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = RSSFeedUtil.truncateLength(RSSFeedUtil.removeHTML(description),
                RSSFeedUtil.MAX_LENGTH_DESCRIPTION);
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFeedItemXML() {
        return feedItemXML;
    }

    public void setFeedItemXML(String feedItemXML) {
        this.feedItemXML = feedItemXML;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public FeedChannel getFeedChannel() {
        return feedChannel;
    }

    public void setFeedChannel(FeedChannel feedChannel) {
        this.feedChannel = feedChannel;
    }

    public Boolean getScrapped() {
        return scrapped;
    }

    public void setScrapped(Boolean scrapped) {
        this.scrapped = scrapped;
    }

    public Boolean getScrapError() {
        return scrapError;
    }

    public void setScrapError(Boolean scrapError) {
        this.scrapError = scrapError;
    }

    public Integer getAttemps() {
        return attemps;
    }

    public void setAttemps(Integer attemps) {
        this.attemps = attemps;
    }

}
