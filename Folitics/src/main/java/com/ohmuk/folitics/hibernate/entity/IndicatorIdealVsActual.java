package com.ohmuk.folitics.hibernate.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "IndicatorIdealVsActual")
public class IndicatorIdealVsActual {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long indicatorid;

    @Column(nullable = false)
    private Timestamp editTime;

    @Column(nullable = false)
    private Timestamp createTime;

    @Column(nullable = false)
    private Long indicatorIdealValue;

    @Column(nullable = false)
    private Long indicatorActualValue;
    

    /**
     * @return the indicatorid
     */
    public Long getIndicatorid() {
        return indicatorid;
    }

    /**
     * @param indicatorid the indicatorid to set
     */
    public void setIndicatorid(Long indicatorid) {
        this.indicatorid = indicatorid;
    }

    /**
     * @return the editTime
     */
    public Timestamp getEditTime() {
        return editTime;
    }

    /**
     * @param editTime the editTime to set
     */
    public void setEditTime(Timestamp editTime) {
        this.editTime = editTime;
    }

    /**
     * @return the createTime
     */
    public Timestamp getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the indicatorIdealValue
     */
    public Long getIndicatorIdealValue() {
        return indicatorIdealValue;
    }

    /**
     * @param indicatorIdealValue the indicatorIdealValue to set
     */
    public void setIndicatorIdealValue(Long indicatorIdealValue) {
        this.indicatorIdealValue = indicatorIdealValue;
    }

    /**
     * @return the indicatorActualValue
     */
    public Long getIndicatorActualValue() {
        return indicatorActualValue;
    }

    /**
     * @param indicatorActualValue the indicatorActualValue to set
     */
    public void setIndicatorActualValue(Long indicatorActualValue) {
        this.indicatorActualValue = indicatorActualValue;
    }
    
}
