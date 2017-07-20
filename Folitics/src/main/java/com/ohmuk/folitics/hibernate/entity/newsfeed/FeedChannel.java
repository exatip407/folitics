package com.ohmuk.folitics.hibernate.entity.newsfeed;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ohmuk.folitics.component.newsfeed.RSSFeedUtil;
import com.ohmuk.folitics.util.DateUtils;

/**
 * Entity implementation class for Entity: FeedChannel
 * @author Jahid Ali
 */
@Entity
@Table(name = "feedchannel")
@NamedQuery(name = "FeedChannel.findAll", query = "SELECT r FROM FeedChannel r")
public class FeedChannel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private Timestamp edited;

    @Column(nullable = false)
    private Timestamp timestamp;

    @Column(nullable = true, length = RSSFeedUtil.MAX_LENGTH_TITLE)
    private String title;

    @Column(nullable = true, length = RSSFeedUtil.MAX_LENGTH_LINK)
    private String link;

    @Column(nullable = true, length = RSSFeedUtil.MAX_LENGTH_DESCRIPTION)
    private String description;

    @Column(nullable = true)
    private String language;

    @Column(nullable = true, length = RSSFeedUtil.MAX_LENGTH_COPYRIGHT)
    private String copyright;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "imageId")
    private FeedChannelImage feedChannelImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedSourceId", nullable = false)
    @JsonBackReference(value = "feedSource-channel")
    private FeedSource feedSource;

    @OneToMany(mappedBy = "feedChannel", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonManagedReference(value = "feedChannel-data")
    private Set<FeedData> feedDataLinks;

    public FeedSource getFeedSource() {
        return feedSource;
    }

    public void setFeedSource(FeedSource feedSource) {
        this.feedSource = feedSource;
    }

    public FeedChannel() {
        setTimestamp(DateUtils.getSqlTimeStamp());
        setEdited(DateUtils.getSqlTimeStamp());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getEdited() {
        return edited;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = RSSFeedUtil.truncateLength(copyright, RSSFeedUtil.MAX_LENGTH_COPYRIGHT);

    }

    public FeedChannelImage getFeedChannelImage() {
        return feedChannelImage;
    }

    public void setFeedChannelImage(FeedChannelImage feedChannelImage) {
        this.feedChannelImage = feedChannelImage;
    }

    public Set<FeedData> getFeedDataLinks() {
        return feedDataLinks;
    }

    public void setFeedDataLinks(Set<FeedData> feedDataLinks) {
        this.feedDataLinks = feedDataLinks;
    }
}
