package com.ohmuk.folitics.charting.beans;

import com.ohmuk.folitics.charting.interfaces.IChartData;

public class VerdictChartBean implements IChartData {
    private String xAxis;
    private double yAxisValue;
    private String flag;
    private String color;

    /**
     * @return the xAxis
     */
    public String getxAxis() {
        return xAxis;
    }

    /**
     * @param xAxis the xAxis to set
     */
    public void setxAxis(String xAxis) {
        this.xAxis = xAxis;
    }

    /**
     * @return the yAxisValue
     */
    public double getyAxisValue() {
        return yAxisValue;
    }

    /**
     * @param yAxisValue the yAxisValue to set
     */
    public void setyAxisValue(double yAxisValue) {
        this.yAxisValue = yAxisValue;
    }

    /**
     * @return the flag
     */
    public String getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

}
