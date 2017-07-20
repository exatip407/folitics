package com.ohmuk.folitics.hibernate.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.util.DateUtils;

@Entity
@Table(name = "eventReportScore")
public class EventReportScore implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Timestamp time;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private String uploadedby;

    @Column(nullable = false)
    private Long score;

    @Column(nullable = false)
    private Long mediaid;

    @Column(nullable = false)
    private Long eventtypeid;

    @Column(nullable = false)
    private Timestamp eventtype;

    @Column(nullable = false)
    private String weblink;

    @Column(nullable = false)
    private Long deleted;

    @Column(nullable = false)
    private Timestamp editTime;

    @Column(nullable = false)
    private Timestamp createTime;

    public EventReportScore() {
        setCreateTime(DateUtils.getSqlTimeStamp());
        setEditTime(DateUtils.getSqlTimeStamp());
        setState(ComponentState.ACTIVE.getValue());
    }

    public Timestamp getEditTime() {
        return editTime;
    }

    public void setEditTime(Timestamp editTime) {
        this.editTime = editTime;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(nullable = false, length = 25, columnDefinition = "enum('New','Edited','Deleted')")
    private String state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUploadedby() {
        return uploadedby;
    }

    public void setUploadedby(String uploadedby) {
        this.uploadedby = uploadedby;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getMediaid() {
        return mediaid;
    }

    public void setMediaid(Long mediaid) {
        this.mediaid = mediaid;
    }

    public Long getEventtypeid() {
        return eventtypeid;
    }

    public void setEventtypeid(Long eventtypeid) {
        this.eventtypeid = eventtypeid;
    }

    public Timestamp getEventtype() {
        return eventtype;
    }

    public void setEventtype(Timestamp eventtype) {
        this.eventtype = eventtype;
    }

    public String getWeblink() {
        return weblink;
    }

    public void setWeblink(String weblink) {
        this.weblink = weblink;
    }

    public Long getDeleted() {
        return deleted;
    }

    public void setDeleted(Long deleted) {
        this.deleted = deleted;
    }

}
