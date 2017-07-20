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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ohmuk.folitics.util.DateUtils;

/**
 * Entity implementation class for Entity: NewsSource
 * @author Jahid Ali
 */
@Entity
@Table(name = "feedsource")
@NamedQuery(name = "FeedSource.findAll", query = "SELECT r FROM FeedSource r")
public class FeedSource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private Timestamp edited;

    @Column(nullable = false)
    private Timestamp timestamp;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = true, length = 1000)
    private String description;

    @Column(nullable = false, length = 1000)
    private String feedURL;

    @Column(nullable = false)
    private Boolean disabled;

    @Column(nullable = false)
    private Integer records;

    @Column(nullable = false)
    private String scheduleCron;

    @OneToMany(mappedBy = "feedSource", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonManagedReference(value = "feedSource-channel")
    private Set<FeedChannel> feedChannels;

    public Set<FeedChannel> getFeedChannels() {
        return feedChannels;
    }

    public void setFeedChannels(Set<FeedChannel> feedChannels) {
        this.feedChannels = feedChannels;
    }

    public FeedSource() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFeedURL() {
        return feedURL;
    }

    public void setFeedURL(String feedURL) {
        this.feedURL = feedURL;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Integer getRecords() {
        return records;
    }

    public void setRecords(Integer records) {
        this.records = records;
    }

    public String getScheduleCron() {
        return scheduleCron;
    }

    public void setScheduleCron(String scheduleCron) {
        this.scheduleCron = scheduleCron;
    }

    public static final FeedSource getFeedSource(String name, String url) {
        FeedSource feedSource = new FeedSource();
        feedSource.setDescription(name);
        feedSource.setDisabled(Boolean.FALSE);
        feedSource.setFeedURL(url);
        feedSource.setName(name);
        feedSource.setRecords(50);// Default
        feedSource.setScheduleCron("0 0 0/2 1/1 * ? *");// Default
        return feedSource;
    }
}
