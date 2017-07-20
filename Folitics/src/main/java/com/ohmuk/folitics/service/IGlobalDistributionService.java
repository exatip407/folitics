package com.ohmuk.folitics.service;

import com.ohmuk.folitics.charting.beans.ChartResponse;
import com.ohmuk.folitics.exception.MessageException;

public interface IGlobalDistributionService {
    /**
     * Method is to get ChartData
     * @param categoryId
     * @return
     * @throws MessageException
     */
    public ChartResponse getChartData(Long categoryId) throws MessageException;

}
