package com.ohmuk.folitics.elasticsearch.service.impl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohmuk.folitics.elasticsearch.ElasticSearchClient;
import com.ohmuk.folitics.elasticsearch.service.IESService;
import com.ohmuk.folitics.hibernate.entity.task.Personality;

@Service
public class ESService implements IESService {
	@Autowired
	private ElasticSearchClient client;

	@Override
	public boolean save(String index, String type, String id, String personality) throws UnknownHostException {
		IndexResponse response = client.getClient().prepareIndex(index, type, id).setSource(personality).get();
		if (null != response.getShardInfo()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean update(String index, String type, String id, String personality) throws Exception {
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index(index);
		updateRequest.type(type);
		updateRequest.id(id);
		updateRequest.doc(XContentFactory.jsonBuilder().startObject()
				/* .field("gender", "male") */
				.endObject());
		client.getClient().update(updateRequest).get();
		return true;
	}

	@Override
	public boolean delete(String index, String type, String id) throws Exception {
		client.getClient().prepareDelete(index, type, id).get();
		return true;
	}

	@Override
	public List<Personality> getAllPersonalities() throws Exception {

		SearchResponse response;
		try {
			response = ElasticSearchClient.getClient().prepareSearch().setIndices("module").setTypes("personality")
					.execute().actionGet();
		} catch (Throwable eThrowable) {
			return new ArrayList<Personality>();
		}
		List<Personality> result = new ArrayList<Personality>();
		for (SearchHit hit : response.getHits()) {
			result.add(new ObjectMapper().readValue(hit.getSourceAsString(), Personality.class));
		}
		return result;

	}

	@SuppressWarnings("static-access")
	@Override
	public SearchResponse search(String index, String type, String text) throws UnknownHostException {

		System.out.println("aa");
		SearchResponse response = client.getClient().prepareSearch(index).setTypes(type).execute()
				.actionGet();

		return response;
	}

}
