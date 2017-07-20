package com.ohmuk.folitics.charting.interfaces;

import com.ohmuk.folitics.charting.Exception.ValidationException;
import com.ohmuk.folitics.charting.beans.ChartRequest;

public interface IChartObject {

    public boolean validate(ChartRequest chartRequest) throws ValidationException;

    public String getQuery(ChartRequest chartRequest);

}
