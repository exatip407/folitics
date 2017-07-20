package com.ohmuk.folitics.businessDelegate.interfaces;

import java.net.UnknownHostException;

import org.elasticsearch.action.search.SearchResponse;

import com.ohmuk.folitics.hibernate.entity.Sentiment;

public interface ISearchBusinessDeligate {
	
	/**
     * Method is to search
     * 
     * @param index , type
     * @return SearchResponse
     * @throws Exception
     */
	public SearchResponse searchDocument(String index, String type, String text) throws UnknownHostException;

}
