package com.ohmuk.folitics.controller.air;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.utils.TestUtils;

@RunWith(BlockJUnit4ClassRunner.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAirController {
	
	String COMPONENTSHARE_CONTROLLER_APIS_SHARE = "componentair/air";
	String COMPONENTSHARE_CONTROLLER_APIS_GET_USERS_FOR_SHARE = "componentair/getUserList";
	String COMPONENTSHARE_CONTROLLER_APIS_IS_COMPONENTSHARED = "componentair/isAired";
	String COMPONENTSHARE_CONTROLLER_APIS_getAirCount = "componentair/getAirCount";
		

	protected static final Logger LOGGER = LoggerFactory.getLogger(TestAirController.class);
    private RestTemplate restTemplate = new TestRestTemplate();
	@Test
    public void TestShareController() throws JsonProcessingException{
       long id = testSharecreate();
	   getUsersForShare(id);
	   isComponentShared(id);
	   getAirCountForComponent(id);
	    
    }

	public Long testSharecreate() throws JsonProcessingException{
		// TODO Auto-generated method stub
		  Map<String, Object> requestBody = testshare();
		  UriComponentsBuilder builder = UriComponentsBuilder
		          .fromHttpUrl(TestUtils.BASE_URL + COMPONENTSHARE_CONTROLLER_APIS_SHARE);
		  HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
		  HttpEntity<ResponseDto<Object>> response = restTemplate.exchange(builder.build().encode().toUri(),
		          HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<Object>>() {
		          });

		  ResponseDto<Object> apiResponse = response.getBody();
		  assertNotNull(apiResponse);
		  assertTrue(apiResponse.getSuccess());
		  
		  Map sentimentAirMap = (Map)response.getBody().getMessages().get(0);
		  return (Long) sentimentAirMap.get("id");

		
	}
	
	public void getUsersForShare(long id) throws JsonProcessingException{
		// TODO Auto-generated method stub
		  Map<String, Object> requestBody = testshare();
		  requestBody.put("id", id);
		  UriComponentsBuilder builder = UriComponentsBuilder
		          .fromHttpUrl(TestUtils.BASE_URL + COMPONENTSHARE_CONTROLLER_APIS_GET_USERS_FOR_SHARE);
		  HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
		  HttpEntity<ResponseDto<List<User>>> response = restTemplate.exchange(builder.build().encode().toUri(),
		          HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<List<User>>>() {
		          });

		  ResponseDto<List<User>> apiResponse = response.getBody();
		  assertNotNull(apiResponse);
		  assertTrue(apiResponse.getSuccess());
		
	}
	public void isComponentShared(long id) throws JsonProcessingException{
		// TODO Auto-generated method stub
		
		  Map<String, Object> requestBody = testshare();
		  requestBody.put("id", id);
		  UriComponentsBuilder builder = UriComponentsBuilder
		          .fromHttpUrl(TestUtils.BASE_URL + COMPONENTSHARE_CONTROLLER_APIS_IS_COMPONENTSHARED);
		  HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
		  HttpEntity<ResponseDto<Boolean>> response = restTemplate.exchange(builder.build().encode().toUri(),
		          HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<Boolean>>() {
		          });

		  ResponseDto<Boolean> apiResponse = response.getBody();
		  assertNotNull(apiResponse);
		  assertTrue(apiResponse.getSuccess());
		
	}
	
	public void getAirCountForComponent(long id) throws JsonProcessingException{
		// TODO Auto-generated method stub
		  Map<String, Object> requestBody = testshare();
		  requestBody.put("id", id);
		  UriComponentsBuilder builder = UriComponentsBuilder
		          .fromHttpUrl(TestUtils.BASE_URL + COMPONENTSHARE_CONTROLLER_APIS_getAirCount);
		  HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
		  HttpEntity<ResponseDto<Boolean>> response = restTemplate.exchange(builder.build().encode().toUri(),
		          HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<Boolean>>() {
		          });

		  ResponseDto<Boolean> apiResponse = response.getBody();
		  assertNotNull(apiResponse);
		  assertTrue(apiResponse.getSuccess());
		
	}
	

	private Map<String, Object> testshare() {
		// TODO Auto-generated method stub
		Map<String, Object> requestBody = new HashMap<String, Object>();
		requestBody.put("id", 1);
		requestBody.put("componentType", "sentiment");
        requestBody.put("componentId", 1);
        requestBody.put("userId", 1);
        requestBody.put("platform", "111");
        requestBody.put("description", "111");
		return requestBody;
	}
}
