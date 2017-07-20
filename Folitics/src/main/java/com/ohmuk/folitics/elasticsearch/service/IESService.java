package com.ohmuk.folitics.elasticsearch.service;

import java.net.UnknownHostException;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;

import com.ohmuk.folitics.hibernate.entity.task.Personality;

public interface IESService {

    /**
     * 
     * @param index
     * @param type
     * @param id
     * @param personality
     * @return
     * @throws UnknownHostException
     */
    public boolean save(String index, String type, String id, String personality) throws UnknownHostException;

    /**
     * 
     * @param index
     * @param type
     * @param id
     * @param personality
     * @return
     * @throws Exception
     */
    public boolean update(String index, String type, String id, String personality) throws Exception;

    /**
     * 
     * @param index
     * @param type
     * @param id
     * @return
     * @throws UnknownHostException
     * @throws Exception
     */
    public boolean delete(String index, String type, String id) throws UnknownHostException, Exception;

    /**
     * Method is to get all personality
     * @return String
     * @throws Exception
     */
    public List<Personality> getAllPersonalities() throws Exception;
    
    /**
     * 
     * @param index
     * @param type
     * @return SearchResponse
     * @throws UnknownHostException
     */
    public SearchResponse search(String index, String type, String text) throws UnknownHostException;
}
