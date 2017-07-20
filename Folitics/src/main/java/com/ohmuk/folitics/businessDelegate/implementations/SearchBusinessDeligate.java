package com.ohmuk.folitics.businessDelegate.implementations;

import java.net.UnknownHostException;

import javax.transaction.Transactional;

import org.elasticsearch.action.search.SearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.businessDelegate.interfaces.ISearchBusinessDeligate;
import com.ohmuk.folitics.service.ISearchService;

@Component
@Transactional
public class SearchBusinessDeligate implements ISearchBusinessDeligate{
	
	final static Logger logger  = LoggerFactory.getLogger(SearchBusinessDeligate.class);

	@Autowired
	private ISearchService searchService;
	
	@Override
	public SearchResponse searchDocument(String index, String type, String text) throws UnknownHostException{
		
		logger.info("Inside Search BusinessDeligate");
		
		SearchResponse response =  searchService.searchDocument(index, type,text);

		logger.info("Exiting Search BusinessDeligate");
		return response;
	}

}
