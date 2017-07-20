package com.ohmuk.folitics.hibernate.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.ohmuk.folitics.util.DateUtils;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

/**
 * Entity implementation class for Entity: SentimentOpinionStat
 * @author Abhishek
 *
 */
@Entity
@Table(name = "sentimentopinionstat")
@JsonIdentityInfo(generator = JSOGGenerator.class, property = "@parentId")
@PrimaryKeyJoinColumn(name = "id")
public class SentimentOpinionStat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    
    @Column(nullable = true)
    private Long favorPoints;

    @Column(nullable = true)
    private Long againstPoints;

    @Column(nullable = false)
    @NotNull(message = "error.sentimentOpinionStat.createTime.notNull")
    private Timestamp createTime;

    @Column(nullable = false)
    @NotNull(message = "error.sentimentOpinionStat.editTime.notNull")
    private Timestamp editTime;

    public SentimentOpinionStat() {
        setCreateTime(DateUtils.getSqlTimeStamp());
        setEditTime(DateUtils.getSqlTimeStamp());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFavorPoints() {
        return favorPoints;
    }

    public void setFavorPoints(Long favorPoints) {
        this.favorPoints = favorPoints;
    }

    public Long getAgainstPoints() {
        return againstPoints;
    }

    public void setAgainstPoints(Long againstPoints) {
        this.againstPoints = againstPoints;
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

}
