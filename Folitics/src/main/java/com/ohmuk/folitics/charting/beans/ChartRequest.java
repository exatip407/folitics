package com.ohmuk.folitics.charting.beans;

import java.util.Map;

public class ChartRequest {

    private String chartID;
    private String chartSubID;
    private Map<String, Object> chartParameters;

    public String getChartSubID() {
        return chartSubID;
    }

    public void setChartSubID(String chartSubID) {
        this.chartSubID = chartSubID;
    }

    public Map<String, Object> getChartParameters() {
        return chartParameters;
    }

    public void setChartParameters(Map<String, Object> chartParameters) {
        this.chartParameters = chartParameters;
    }

    public String getChartID() {
        return chartID;
    }

    public void setChartID(String chartID) {
        this.chartID = chartID;
    }

}
