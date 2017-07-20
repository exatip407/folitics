package com.ohmuk.folitics.service;

import java.util.List;

import com.ohmuk.folitics.beans.HeaderDataBean;
import com.ohmuk.folitics.beans.TrackPromiseBean;
import com.ohmuk.folitics.exception.MessageException;
import com.ohmuk.folitics.hibernate.entity.Category;
import com.ohmuk.folitics.hibernate.entity.IndicatorData;

public interface IIndicatorDataService {
    /**
     * Method is to add {@link IndicatorData}
     * @param indicatordata
     * @return {@link IndicatorData}
     * @throws MessageException
     */
    public IndicatorData create(IndicatorData indicatordata) throws MessageException, Exception;

    /**
     * Method is to get {@link IndicatorData}
     * @param id
     * @return {@link IndicatorData}
     * @throws Exception
     */
    public IndicatorData read(Long id) throws Exception;

    /**
     * Method is to get all {@link IndicatorData}
     * @return {@link IndicatorData}
     * @throws Exception
     */
    public List<IndicatorData> readAll() throws Exception;

    /**
     * Method is to update {@link IndicatorData}
     * @param indicatordata
     * @return {@link IndicatorData}
     * @throws Exception
     */
    public IndicatorData update(IndicatorData indicatordata) throws Exception;

    /**
     * Method is soft delete {@link IndicatorData} by id
     * @param id
     * @return boolean
     * @throws Exception
     */
    public boolean delete(Long id) throws Exception;

    /**
     * Method is to soft delete {@link IndicatorData}
     * @param indicatordata
     * @return boolean
     * @throws Exception
     */
    public boolean delete(IndicatorData indicatordata) throws Exception;

   


    /**
     * Method is to hard delete {@link IndicatorData} by id
     * @param id
     * @return boolean
     * @throws Exception
     */
    public boolean deleteFromDB(Long id) throws Exception;

    /**
     * Method is to find {@link IndicatorData} by latest {@link Category}
     * @param category
     * @return IndicatorData
     */
    public IndicatorData findByCategoryLatest(Category category);
    
    /**
     * Method is to get all {@link IndicatorData} order by recently added
     * @return List<IndicatorData>
     */
    public List<IndicatorData> findAllIndicatorDataLatest();

	List<IndicatorData> findIndicatorDataForVerdict();

	List<IndicatorData> findByCategory(Long categoryId);
}
