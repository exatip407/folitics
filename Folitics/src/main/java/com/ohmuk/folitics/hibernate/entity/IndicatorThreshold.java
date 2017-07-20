package com.ohmuk.folitics.hibernate.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.ohmuk.folitics.enums.ComponentState;
import com.ohmuk.folitics.util.DateUtils;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

@Entity
@Table(name = "indicatorthreshold")
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")
public class IndicatorThreshold implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "indicatorId", nullable = false)
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Column(nullable = false)
    private Long direction;

    @Column(nullable = false)
    private Timestamp editTime;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private Timestamp createTime;

    @Column(nullable = false)
    private Double threshold_start;

    @Column(nullable = false)
    private Double threshold_end;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(nullable = false, length = 25, columnDefinition = "enum('Fooling US','UnSatisfactory','On Track' , 'Best Performance')")
    private String threshHoldCategory;

    public String getThreshHoldCategory() {
        return threshHoldCategory;
    }

    public void setThreshHoldCategory(String threshHoldCategory) {
        this.threshHoldCategory = threshHoldCategory;
    }

    public IndicatorThreshold() {
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

    public Double getThreshold_start() {
        return threshold_start;
    }

    public void setThreshold_start(Double threshold_start) {
        this.threshold_start = threshold_start;
    }

    public Double getThreshold_end() {
        return threshold_end;
    }

    public void setThreshold_end(Double threshold_end) {
        this.threshold_end = threshold_end;
    }

    public Long getDirection() {
        return direction;
    }

    public void setDirection(Long direction) {
        this.direction = direction;
    }

}
