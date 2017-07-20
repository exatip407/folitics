package com.ohmuk.folitics.service;

import java.sql.Timestamp;
import java.util.List;

import com.ohmuk.folitics.hibernate.entity.Response;

/**
 * @author Abhishek
 *
 */
public interface IResponseService {
    /**
     * Method is to add {@link Response} by id
     * @param response
     * @return {@link Response}
     * @throws Exception
     */
    public Response create(Response response) throws Exception;

    /**
     * Method is to get {@link Response} by id
     * @param id
     * @return
     * @throws Exception
     */
    public Response getResponseById(Long id) throws Exception;

    /**
     * Method is to get all {@link Response}
     * 
     * @return {@link Response}
     * @throws Exception
     */
    public List<Response> readAll() throws Exception;

    /**
     * Method is to update {@link Response}
     * @param response
     * @return {@link Response}
     * @throws Exception
     */
    public Response update(Response response) throws Exception;

    
    /**
     * Method is to get response{@link Response} by opinionId
     * @param id
     * @return boolean
     * @throws Exception
     */
    public List<Response> getByOpinionId(Long id) throws Exception;
    
    /**
     * Method is to get response {@link Response} by userId
     * @param id
     * @return boolean
     * @throws Exception
     */
    public List<Response> getByUserId(Long id) throws Exception;
    
    /**
     * Method is to delete {@link Response} by id
     * @param id
     * @return boolean
     * @throws Exception
     */
    public boolean delete(Long id) throws Exception;

    /**
     * Method is to delete {@link Response}
     * @param response
     * @return boolean
     * @throws Exception
     */
    public boolean delete(Response response) throws Exception;

    /**
     * Method is to hard delete {@link Response} by id
     * @param id
     * @return boolean
     * @throws Exception
     */
    public boolean deleteFromDBById(Long id) throws Exception;

    /**
     * Method is to hard delete {@link Response}
     * @param response
     * @return boolean
     * @throws Exception
     */
    public boolean deleteFromDB(Response response) throws Exception;
    
    /**
	 * Method is to aggregate user point
	 * 
	 * @param startTime
	 * @param endTime
	 * @throws Exception
	 */

	public List<Response> userPointsAggregations(Response response,Double userPoints) throws Exception;
}
