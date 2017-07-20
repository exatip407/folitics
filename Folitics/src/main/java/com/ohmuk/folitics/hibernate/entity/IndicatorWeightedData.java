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
import com.voodoodyne.jackson.jsog.JSOGGenerator;

@Entity
@Table(name = "IndicatorWeightedData")
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")
public class IndicatorWeightedData implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "indicatorId", nullable = false, referencedColumnName = "id")
    private Category category;

    @Column(nullable = false)
    private Timestamp updateddate;

    @Column(nullable = false)
    private Long weightage;

    @Column(nullable = false)
    private Timestamp effectivefromdate;

    @Column(nullable = false)
    private Long deleted;

    @Column(nullable = false)
    private Timestamp editTime;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private Timestamp createTime;

    @Column(nullable = false)
    private Integer impactOnChart;

    public Integer getImpactOnChart() {
        return impactOnChart;
    }

    public void setImpactOnChart(Integer impactOnChart) {
        this.impactOnChart = impactOnChart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Timestamp getUpdateddate() {
        return updateddate;
    }

    public void setUpdateddate(Timestamp updateddate) {
        this.updateddate = updateddate;
    }

    public Long getWeightage() {
        return weightage;
    }

    public void setWeightage(Long weightage) {
        this.weightage = weightage;
    }

    public Timestamp getEffectivefromdate() {
        return effectivefromdate;
    }

    public void setEffectivefromdate(Timestamp effectivefromdate) {
        this.effectivefromdate = effectivefromdate;
    }

    public Long getDeleted() {
        return deleted;
    }

    public void setDeleted(Long deleted) {
        this.deleted = deleted;
    }

    public Timestamp getEditTime() {
        return editTime;
    }

    public void setEditTime(Timestamp editTime) {
        this.editTime = editTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

}
