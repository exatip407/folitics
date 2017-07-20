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
@Table(name = "IndicatorMetaData")
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")
public class IndicatorMetaData implements Serializable {

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

    @Column(nullable = false)
    private Long DataUpdateFrequency;

    @Column(nullable = false)
    private Long direction;

    @Column(nullable = false)
    private Long datatype;

    @Column(nullable = false)
    private String indicatorDetailLink;

    @Column(nullable = false)
    private Timestamp editTime;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private Timestamp createTime;

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

    public Long getDataUpdateFrequency() {
        return DataUpdateFrequency;
    }

    public void setDataUpdateFrequency(Long dataUpdateFrequency) {
        DataUpdateFrequency = dataUpdateFrequency;
    }

    public Long getDirection() {
        return direction;
    }

    public void setDirection(Long direction) {
        this.direction = direction;
    }

    public Long getDatatype() {
        return datatype;
    }

    public void setDatatype(Long datatype) {
        this.datatype = datatype;
    }

    public String getIndicatorDetailLink() {
        return indicatorDetailLink;
    }

    public void setIndicatorDetailLink(String indicatorDetailLink) {
        this.indicatorDetailLink = indicatorDetailLink;
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
