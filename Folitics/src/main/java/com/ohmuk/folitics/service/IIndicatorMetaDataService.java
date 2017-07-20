package com.ohmuk.folitics.service;

import java.util.List;

import com.ohmuk.folitics.hibernate.entity.IndicatorMetaData;

public interface IIndicatorMetaDataService {
    /**
     * Method is to add {@link IndicatorMetaData}
     * @param indicatormetadata
     * @return {@link IndicatorMetaData}
     * @throws Exception
     */
    public IndicatorMetaData create(IndicatorMetaData indicatormetadata) throws Exception;

    /**
     * Method is to get {@link IndicatorMetaData} by id
     * @param id
     * @return {@link IndicatorMetaData}
     * @throws Exception
     */
    public IndicatorMetaData read(Long id) throws Exception;

    /**
     * Method is to get all {@link IndicatorMetaData}
     * @return {@link List< IndicatorMetaData >}
     * @throws Exception
     */
    public List<IndicatorMetaData> readAll() throws Exception;

    /**
     * Method is to update {@link IndicatorMetaData}
     * @param indicatormetadata
     * @return {@link IndicatorMetaData}
     * @throws Exception
     */
    public IndicatorMetaData update(IndicatorMetaData indicatormetadata) throws Exception;

    /**
     * Method is to delete {@link IndicatorMetaData} by id
     * @param id
     * @return boolean
     * @throws Exception
     */
    public boolean delete(Long id) throws Exception;

    /**
     * Method is to delete {@link IndicatorMetaData}
     * @param indicatormetadata
     * @return boolean
     * @throws Exception
     */
    public boolean delete(IndicatorMetaData indicatormetadata) throws Exception;

    /**
     * Method is to hard delete {@link IndicatorMetaData}
     * @author Mayank Sharma
     * @param indicatorMetaData
     * @return boolean
     * @throws Exception
     */
    public boolean deleteFromDB(IndicatorMetaData indicatorMetaData) throws Exception;

    /**
     * Method is to hard delete {@link IndicatorMetaData} by id
     * @param id
     * @return boolean
     * @throws Exception
     */
    public boolean deleteFromDBById(Long id) throws Exception;

}
