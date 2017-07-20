package com.ohmuk.folitics.charting.beans;

import java.util.ArrayList;
import java.util.List;

public class ChartResponse {

	public List<LineChartData> data;

	public List<VerdictChartBean> verdictData;

	public List<String> chartMeta = new ArrayList<String>();

	/* public LineChartCompare lineChartCompare; */

	/*    *//**
	 * @return the lineChartCompare
	 */
	/*
	 * public LineChartCompare getLineChartCompare() { return lineChartCompare;
	 * }
	 *//**
	 * @param lineChartCompare
	 *            the lineChartCompare to set
	 */
	/*
	 * public void setLineChartCompare(LineChartCompare lineChartCompare) {
	 * this.lineChartCompare = lineChartCompare; }
	 */

	/**
	 * @return the data
	 */
	public List<LineChartData> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(List<LineChartData> data) {
		this.data = data;
	}

	/**
	 * @return the verdictData
	 */
	public List<VerdictChartBean> getVerdictData() {
		return verdictData;
	}

	/**
	 * @param verdictData
	 *            the verdictData to set
	 */
	public void setVerdictData(List<VerdictChartBean> verdictData) {
		this.verdictData = verdictData;
	}

	/**
	 * @return the chartMeta
	 */
	public List<String> getChartMeta() {
		return chartMeta;
	}

	/**
	 * @param chartMeta
	 *            the chartMeta to set
	 */
	public void setChartMeta(List<String> chartMeta) {
		this.chartMeta = chartMeta;
	}

}
