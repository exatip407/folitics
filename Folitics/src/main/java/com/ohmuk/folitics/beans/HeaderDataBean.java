package com.ohmuk.folitics.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ohmuk.folitics.hibernate.entity.IndicatorData;

public class HeaderDataBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String thresholdCategory;
    private Double totalScore = 0.0;
    private Double totalIdealVaueScore = 0.0;
    private List<IndicatorData> indicatorDataList = new ArrayList<IndicatorData>();

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public Double getTotalIdealVaueScore() {
        return totalIdealVaueScore;
    }

    public void setTotalIdealVaueScore(Double totalIdealVaueScore) {
        this.totalIdealVaueScore = totalIdealVaueScore;
    }

    public String getThresholdCategory() {
        return thresholdCategory;
    }

    public void setThresholdCategory(String thresholdCategory) {
        this.thresholdCategory = thresholdCategory;
    }

    public List<IndicatorData> getIndicatorDataList() {
        return indicatorDataList;
    }

    public void setIndicatorDataList(List<IndicatorData> indicatorDataList) {
        this.indicatorDataList = indicatorDataList;
    }

}
