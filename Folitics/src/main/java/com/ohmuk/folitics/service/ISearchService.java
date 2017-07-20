package com.ohmuk.folitics.service;

import java.net.UnknownHostException;

import org.elasticsearch.action.search.SearchResponse;

public interface ISearchService {

	
	/**
     * Method is to search
     * 
     * @param index , type
     * @return SearchResponse
     * @throws Exception
     */
	public SearchResponse searchDocument(String index, String type, String text) throws UnknownHostException;
}
