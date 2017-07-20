package com.ohmuk.folitics.controller;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ohmuk.folitics.businessDelegate.interfaces.ISearchBusinessDeligate;
import com.ohmuk.folitics.elasticsearch.ElasticSearchClient;

@Controller
@RequestMapping("/search")
public class SearchController {

	static final Logger logger = LoggerFactory.getLogger(SearchController.class);

	@Autowired
	private volatile ISearchBusinessDeligate searchBusinessDeligate;

	@Autowired
	private ElasticSearchClient client;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody Map<Integer,Object> searchDocument(String index, String type, String text)
			throws UnknownHostException {

		int i=0;
		
		logger.info("Inside search controller");

		SearchResponse response = searchBusinessDeligate.searchDocument(index, type, text);

		SearchHit[] results = response.getHits().getHits();

		logger.info("Exiting search controller");
		System.out.println("result" + response.getHits().getHits());
		Map<Integer,Object> result = new HashMap<Integer,Object>();
		
		for (SearchHit hit : results) {
			  System.out.println(hit.getSource());
			  logger.info(hit.getId());//prints out the id of the document
			  
			  i+=1;
			  result.put(i,hit.getSource());   //the retrieved document
			}
		/*logger.info("result" + response.getHits().getHits());
		String jsonString = results[0].sourceAsString();
		System.out.println("results[0].sourceAsString(): " + results[0].sourceAsString());
*/
		return result;

		/*
		 * System.out.println("Current results: " + results.length); for
		 * (SearchHit hit : results) {
		 * System.out.println("------------------------------"); Map<String,
		 * Object> result = hit.getSource(); System.out.println(result);
		 */
	}

}
