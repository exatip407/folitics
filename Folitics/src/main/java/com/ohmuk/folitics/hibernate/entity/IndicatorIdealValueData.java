package com.ohmuk.folitics.hibernate.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

@Entity
@Table(name = "IndicatorIdealValueData")
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@id")
public class IndicatorIdealValueData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long indicatorid;

    @Column(nullable = false)
    private Timestamp updateddate;

    @Column(nullable = false)
    private Long indicatoridealvalue;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIndicatorid() {
        return indicatorid;
    }

    public void setIndicatorid(Long indicatorid) {
        this.indicatorid = indicatorid;
    }

    public Timestamp getUpdateddate() {
        return updateddate;
    }

    public void setUpdateddate(Timestamp updateddate) {
        this.updateddate = updateddate;
    }

    public Long getIndicatoridealvalue() {
        return indicatoridealvalue;
    }

    public void setIndicatoridealvalue(Long indicatoridealvalue) {
        this.indicatoridealvalue = indicatoridealvalue;
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
