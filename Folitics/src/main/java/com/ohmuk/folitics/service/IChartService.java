package com.ohmuk.folitics.service;

import java.util.List;

import com.ohmuk.folitics.charting.Exception.ProcessingException;
import com.ohmuk.folitics.charting.Exception.ValidationException;
import com.ohmuk.folitics.charting.beans.ChartRequest;
import com.ohmuk.folitics.charting.beans.ChartResponse;
import com.ohmuk.folitics.hibernate.entity.Chart;

public interface IChartService {
	public boolean validate(ChartRequest chartRequest)
			throws ValidationException;

	public ChartResponse getChartData(ChartRequest chartRequest, Long id)
			throws ProcessingException, Exception;

	public List<Chart> readAll();
}
