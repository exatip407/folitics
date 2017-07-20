package com.ohmuk.folitics.controller.userMonetization;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.hibernate.entity.UserMonetization;
import com.ohmuk.folitics.utils.TestUtils;
import com.ohmuk.folitics.utils.TestUtils.USER_MONETIZATION_CONTROLLER_APIS;

public class TestUserMonetizationController {
	private RestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void TestUserMonetization() throws JsonProcessingException {

		Long id = testAddActionApi();
		testFindByIdApi(id);
		testFindByUserIdApi(1L);
		Timestamp start = Timestamp.valueOf("2012-12-18 17:42:12");
		Timestamp end = Timestamp.valueOf("2012-12-18 17:42:12");
		testFindByDateApi(start, end);

	}

	private Long testAddActionApi() throws JsonProcessingException {

		Map<String, Object> requestBody = getMonetionDataBeanBody();
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL + USER_MONETIZATION_CONTROLLER_APIS.ADD_ACTION);
		HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
		HttpEntity<ResponseDto<UserMonetization>> response = restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<UserMonetization>>() {
				});

		ResponseDto<UserMonetization> apiResponse = response.getBody();
		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());
		return ((UserMonetization) response.getBody().getMessages().get(0)).getId();
	}

	private void testFindByIdApi(Long id) throws JsonProcessingException {
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL + USER_MONETIZATION_CONTROLLER_APIS.GET_BY_ID).queryParam("id", id);
		HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
		HttpEntity<ResponseDto<UserMonetization>> response = restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<UserMonetization>>() {
				});

		ResponseDto<UserMonetization> apiResponse = response.getBody();
		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());

	}

	private void testFindByUserIdApi(Long id) throws JsonProcessingException {
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL + USER_MONETIZATION_CONTROLLER_APIS.GET_BY_USER_ID)
				.queryParam("id", id);
		HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
		HttpEntity<ResponseDto<UserMonetization>> response = restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<UserMonetization>>() {
				});

		ResponseDto<UserMonetization> apiResponse = response.getBody();
		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());

	}

	private void testFindByDateApi(Timestamp startDate, Timestamp endDate) throws JsonProcessingException {
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL + USER_MONETIZATION_CONTROLLER_APIS.GET_BY_DATE)
				.queryParam("startDate", startDate).queryParam("endDate", endDate);
		HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
		HttpEntity<ResponseDto<UserMonetization>> response = restTemplate.exchange(builder.build().toUri(),
				HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<UserMonetization>>() {
				});

		ResponseDto<UserMonetization> apiResponse = response.getBody();
		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());

	}

	private Map<String, Object> getLuckDrawBody() {
		Map<String, Object> requestBody = new HashMap<String, Object>();
		return requestBody;
	}

	private Map<String, Object> getMonetionDataBeanBody() {
		// Building the Request body data
		Map<String, Object> requestBody = new HashMap<String, Object>();

		// requestBody.put("createTime", "2012-12-12T00:00:00");
		requestBody.put("userId", 1);
		requestBody.put("actionComponentId", 1);
		requestBody.put("createdTime", "2012-12-18T12:12:12");
		requestBody.put("module", "GPI");
		requestBody.put("action", "Like");
		requestBody.put("componentType", "Image");
		requestBody.put("status", "Active");

		return requestBody;
	}

}
