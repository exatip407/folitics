package com.ohmuk.folitics.hibernate.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GovtSchemeData")
public class GovtSchemeData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String schemename;

    @Column(nullable = false)
    private String description1;

    @Column(nullable = false)
    private String description2;

    @Column(nullable = false)
    private Timestamp startdate;

    @Column(nullable = false)
    private Long isactive;

    @Column(nullable = false)
    private String webpageurl;

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

    public String getSchemename() {
        return schemename;
    }

    public void setSchemename(String schemename) {
        this.schemename = schemename;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public Timestamp getStartdate() {
        return startdate;
    }

    public void setStartdate(Timestamp startdate) {
        this.startdate = startdate;
    }

    public Long getIsactive() {
        return isactive;
    }

    public void setIsactive(Long isactive) {
        this.isactive = isactive;
    }

    public String getWebpageurl() {
        return webpageurl;
    }

    public void setWebpageurl(String webpageurl) {
        this.webpageurl = webpageurl;
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
