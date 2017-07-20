package com.ohmuk.folitics.beans;

import java.io.Serializable;
import java.sql.Timestamp;

public class ChangeIndicatorDataBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String indicatorName;
    private Double valueFrom;
    private Double valueTo;
    private Timestamp lastChange;
    private Long pointAffected;

    /**
     * @return the indicatorName
     */
    public String getIndicatorName() {
        return indicatorName;
    }

    /**
     * @param indicatorName the indicatorName to set
     */
    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }


    /**
     * @return the valueFrom
     */
    public Double getValueFrom() {
        return valueFrom;
    }

    /**
     * @param valueFrom the valueFrom to set
     */
    public void setValueFrom(Double valueFrom) {
        this.valueFrom = valueFrom;
    }

    /**
     * @return the valueTo
     */
    public Double getValueTo() {
        return valueTo;
    }

    /**
     * @param valueTo the valueTo to set
     */
    public void setValueTo(Double valueTo) {
        this.valueTo = valueTo;
    }

    /**
     * @return the lastChange
     */
    public Timestamp getLastChange() {
        return lastChange;
    }

    /**
     * @param lastChange the lastChange to set
     */
    public void setLastChange(Timestamp lastChange) {
        this.lastChange = lastChange;
    }

    /**
     * @return the pointAffected
     */
    public Long getPointAffected() {
        return pointAffected;
    }

    /**
     * @param pointAffected the pointAffected to set
     */
    public void setPointAffected(Long pointAffected) {
        this.pointAffected = pointAffected;
    }

}
