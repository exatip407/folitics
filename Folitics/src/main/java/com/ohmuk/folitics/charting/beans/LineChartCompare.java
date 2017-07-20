package com.ohmuk.folitics.charting.beans;

import java.util.List;

public class LineChartCompare {
    public List<LineChartData> chartDatas;
    public List<LineChartData> compareChartDatas;

    /**
     * @return the chartDatas
     */
    public List<LineChartData> getChartDatas() {
        return chartDatas;
    }

    /**
     * @param chartDatas the chartDatas to set
     */
    public void setChartDatas(List<LineChartData> chartDatas) {
        this.chartDatas = chartDatas;
    }

    /**
     * @return the compareChartDatas
     */
    public List<LineChartData> getCompareChartDatas() {
        return compareChartDatas;
    }

    /**
     * @param compareChartDatas the compareChartDatas to set
     */
    public void setCompareChartDatas(List<LineChartData> compareChartDatas) {
        this.compareChartDatas = compareChartDatas;
    }

}
