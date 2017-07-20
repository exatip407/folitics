package com.ohmuk.folitics.service;

import java.util.List;

import com.ohmuk.folitics.hibernate.entity.IndicatorIdealValueData;

public interface IIndicatorIdealValueDataService {

    /**
     * Method is to add {@link IndicatorIdealValueData}
     * @param indicatoridealvaluedata
     * @return {@link IndicatorIdealValueData}
     * @throws Exception
     */
    public IndicatorIdealValueData create(IndicatorIdealValueData indicatoridealvaluedata) throws Exception;

    /**
     * Method is to get {@link IndicatorIdealValueData} by id
     * @param id
     * @return {@link IndicatorIdealValueData}
     * @throws Exception
     */
    public IndicatorIdealValueData read(Long id) throws Exception;

    /**
     * Method to get all {@link IndicatorIdealValueData}
     * @return {@link List < IndicatorIdealValueData > }
     * @throws Exception
     */
    public List<IndicatorIdealValueData> readAll() throws Exception;

    /**
     * Method is to update {@link IndicatorIdealValueData}
     * @param indicatoridealvaluedata
     * @return {@link IndicatorIdealValueData}
     * @throws Exception
     */
    public IndicatorIdealValueData update(IndicatorIdealValueData indicatoridealvaluedata) throws Exception;

    /**
     * Method is to soft delete {@link IndicatorIdealValueData} by id
     * @param id
     * @return boolean
     * @throws Exception
     */
    public boolean delete(Long id) throws Exception;

    /**
     * Method is to hard delete {@link IndicatorIdealValueData} by id
     * @author Mayank Sharma
     * @param id
     * @return boolean
     * @throws Exception
     */
    public boolean deleteFromDBById(Long id) throws Exception;

    /**
     * Method is to soft delete {@link IndicatorIdealValueData}
     * @param indicatoridealvaluedata
     * @return boolean
     * @throws Exception
     */
    public boolean delete(IndicatorIdealValueData indicatoridealvaluedata) throws Exception;

    /**
     * Method is to hard delete {@link IndicatorIdealValueData}
     * @author Mayank Sharma
     * @param indicatoridealvaluedata
     * @return boolean
     * @throws Exception
     */
    public boolean deleteFromDB(IndicatorIdealValueData indicatoridealvaluedata) throws Exception;

}
