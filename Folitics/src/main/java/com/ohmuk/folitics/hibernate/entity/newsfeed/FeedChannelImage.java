package com.ohmuk.folitics.hibernate.entity.newsfeed;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ohmuk.folitics.component.newsfeed.RSSFeedUtil;
import com.ohmuk.folitics.util.DateUtils;

/**
 * Entity implementation class for Entity: FeedChannelImage
 * @author Jahid Ali
 */
@Entity
@Table(name = "feedchannelimage")
@NamedQuery(name = "FeedChannelImage.findAll", query = "SELECT r FROM FeedChannelImage r")
public class FeedChannelImage implements Serializable {

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

    @Column(nullable = true, length = RSSFeedUtil.MAX_LENGTH_LINK)
    private String url;

    public FeedChannelImage() {
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
        this.title = title;
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
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
