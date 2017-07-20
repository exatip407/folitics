package com.ohmuk.folitics.service;

import java.util.List;

import com.ohmuk.folitics.hibernate.entity.Category;
import com.ohmuk.folitics.hibernate.entity.IndicatorWeightedData;

/**
 * 
 * @author Mayank Sharma
 *
 */
public interface IIndicatorWeightedDataService {
    /**
     * Method is to add IndicatorWeightedData by calling
     * 
     * @param indicatorweighteddata
     * @return IndicatorWeightedData
     * @throws Exception
     */
    public IndicatorWeightedData create(IndicatorWeightedData indicatorweighteddata) throws Exception;

    /**
     * Method is to get IndicatorWeightedData by id
     * @param id
     * @return IndicatorWeightedData
     * @throws Exception
     */
    public IndicatorWeightedData read(Long id) throws Exception;

    /**
     * Method is to get all IndicatorWeightedData by calling
     * @return IndicatorWeightedData
     * @throws Exception
     */
    public List<IndicatorWeightedData> readAll() throws Exception;

    /**
     * Method is to update IndicatorWeightedData by calling
     * @param indicatorweighteddata
     * @return IndicatorWeightedData
     * @throws Exception
     */
    public IndicatorWeightedData update(IndicatorWeightedData indicatorweighteddata) throws Exception;

    /**
     * Method is to soft delete IndicatorWeightedData by id
     * @param id
     * @return boolean
     * @throws Exception
     */
    public boolean delete(Long id) throws Exception;

    /**
     * Method is to soft delete IndicatorWeightedData by calling
     * @param indicatorweighteddata
     * @return boolean
     * @throws Exception
     */
    public boolean delete(IndicatorWeightedData indicatorweighteddata) throws Exception;

    /**
     * Method is to hard delete IndicatorWeightedData by calling
     * @param id
     * @return IndicatorWeightedData
     * @throws Exception
     */
    public boolean deleteFromDB(Long id) throws Exception;

    /**
     * Method to find IndicatorWeightedData by CategoryLatest
     * @param category
     * @return IndicatorWeightedData
     */
    public IndicatorWeightedData findByCategoryLatest(Category category) throws Exception;

}
