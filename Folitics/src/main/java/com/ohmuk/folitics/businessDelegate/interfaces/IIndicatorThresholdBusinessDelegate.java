package com.ohmuk.folitics.businessDelegate.interfaces;

import java.util.List;

import com.ohmuk.folitics.hibernate.entity.Category;
import com.ohmuk.folitics.hibernate.entity.IndicatorThreshold;

public interface IIndicatorThresholdBusinessDelegate {
    
    /**
     * Method is to add {@link IndicatorThreshold}
     * @param indicatorthreshold
     * @return {@link IndicatorThreshold}
     * @throws Exception
     */
    public IndicatorThreshold create(IndicatorThreshold indicatorthreshold) throws Exception;

    /**
     * Methos is to get {@link IndicatorThreshold} by id
     * @param id
     * @return {@link IndicatorThreshold}
     * @throws Exception
     */
    public IndicatorThreshold read(Long id) throws Exception;

    /**
     * Method is to get all {@link IndicatorThreshold}
     * @return {@link IndicatorThreshold}
     * @throws Exception
     */
    public List<IndicatorThreshold> readAll() throws Exception;

    /**
     * Method is to update {@link IndicatorThreshold}
     * @param indicatorthreshold
     * @return {@link IndicatorThreshold}
     * @throws Exception
     */
    public IndicatorThreshold update(IndicatorThreshold indicatorthreshold) throws Exception;

    /**
     * Method is to soft delete {@link IndicatorThreshold} by id
     * @param id
     * @return {@link IndicatorThreshold}
     * @throws Exception
     */
    public boolean delete(Long id) throws Exception;

    /**
     * Method is to delete {@link IndicatorThreshold}
     * @param indicatorthreshold
     * @return {@link IndicatorThreshold}
     * @throws Exception
     */
    public boolean delete(IndicatorThreshold indicatorthreshold) throws Exception;

    /**
     * Method is to hard delete {@link IndicatorThreshold} by id
     * @param id
     * @return {@link IndicatorThreshold}
     * @throws Exception
     */
    public boolean deleteFromDB(Long id) throws Exception;

    /**
     * Method is to find {@link IndicatorThreshold} by latest {@link Category}
     * @param category
     * @return {@link IndicatorThreshold}
     * @throws Exception
     */
    public List<IndicatorThreshold> findByCategoryLatest(Category category) throws Exception;
    
    
    public List<IndicatorThreshold> findByCategory(Category category) throws Exception;
    
    


}
