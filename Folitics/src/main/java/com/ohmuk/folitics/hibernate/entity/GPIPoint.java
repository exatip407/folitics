package com.ohmuk.folitics.hibernate.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.util.DateUtils;

/**
 * Entity implementation class for Entity: GPIPoint
 *
 */
@Entity
@Table(name = "gpipoint")
public class GPIPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Timestamp createTime;

    @Column(nullable = false)
    private Timestamp editTime;

    @Column(nullable = false)
    private Timestamp startTime;

    @Column(nullable = false)
    private Timestamp endTime;

    @Column(nullable = false)
    private Long totalPoints;

    @Column(nullable = false)
    private Long opinionResponseAggregationPoints;

    @Column(nullable = false)
    private Long indicatorChangePoints;

    @Column(nullable = false)
    private Long eventReportPoints;

    @OneToOne(mappedBy = "gpi", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Milestone milestone;

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    @Column(nullable = false, length = 25, columnDefinition = "enum('Active','Disabled','Deleted')")
    private String state;

    public GPIPoint() {
        setCreateTime(DateUtils.getSqlTimeStamp());
        setEditTime(DateUtils.getSqlTimeStamp());
        setState(ComponentState.ACTIVE.getValue());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getEditTime() {
        return editTime;
    }

    public void setEditTime(Timestamp editTime) {
        this.editTime = editTime;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Long getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Long totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Long getOpinionResponseAggregationPoints() {
        return opinionResponseAggregationPoints;
    }

    public void setOpinionResponseAggregationPoints(Long opinionResponseAggregationPoints) {
        this.opinionResponseAggregationPoints = opinionResponseAggregationPoints;
    }

    public Long getIndicatorChangePoints() {
        return indicatorChangePoints;
    }

    public void setIndicatorChangePoints(Long indicatorChangePoints) {
        this.indicatorChangePoints = indicatorChangePoints;
    }

    public Long getEventReportPoints() {
        return eventReportPoints;
    }

    public void setEventReportPoints(Long eventReportPoints) {
        this.eventReportPoints = eventReportPoints;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
