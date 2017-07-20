package com.ohmuk.folitics.charting.interfaces;

import java.util.List;

import com.ohmuk.folitics.charting.beans.LineChartData;

public interface IDataBaseInterface {

	public List<LineChartData> getData(String query);
}
