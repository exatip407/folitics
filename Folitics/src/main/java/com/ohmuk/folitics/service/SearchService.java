package com.ohmuk.folitics.service;

import java.net.UnknownHostException;

import org.elasticsearch.action.search.SearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohmuk.folitics.elasticsearch.service.IESService;

@Service
public class SearchService implements ISearchService {

	final static Logger logger = LoggerFactory.getLogger(SearchService.class);

	@Autowired
	IESService esService;

	@Override
	public SearchResponse searchDocument(String index, String type, String text) throws UnknownHostException {

		logger.info("Inside Search Service");

		SearchResponse response = esService.search(index, type, text);

		logger.info("Exiting Search Service");
		return response;
	}

}
