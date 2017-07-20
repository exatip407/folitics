package com.ohmuk.folitics.controller.monetizationConfig;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
import com.ohmuk.folitics.hibernate.entity.MonetizationConfig;
import com.ohmuk.folitics.utils.TestUtils;
import com.ohmuk.folitics.utils.TestUtils.MONETIZATION_CONFIG_CONTROLLER_APIS;

public class TestMonetizationConfigController {

	private RestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void TestMonetizationConfigControllerAPI() throws JsonProcessingException {

		Long id = testAddMonetizationConfigApi();
		testFindByIdApi(id);
		testEditMonetizationConfigApi(id);
		testDeleteByIdApi(id);
		testDeleteMonetizationConfigApi(id);
	}

	private Long testAddMonetizationConfigApi() throws JsonProcessingException {

		Map<String, Object> requestBody = getMonetizationConfigBody();
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL + MONETIZATION_CONFIG_CONTROLLER_APIS.ADD);
		HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
		HttpEntity<ResponseDto<MonetizationConfig>> response = restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<MonetizationConfig>>() {
				});

		ResponseDto<MonetizationConfig> apiResponse = response.getBody();
		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());
		return ((MonetizationConfig) response.getBody().getMessages().get(0)).getId();
	}

	private void testFindByIdApi(Long id) throws JsonProcessingException {
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL + MONETIZATION_CONFIG_CONTROLLER_APIS.READ).queryParam("id", id);
		HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
		HttpEntity<ResponseDto<MonetizationConfig>> response = restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<MonetizationConfig>>() {
				});

		ResponseDto<MonetizationConfig> apiResponse = response.getBody();
		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());

	}

	private void testEditMonetizationConfigApi(Long id) throws JsonProcessingException {
		Map<String, Object> requestBody = editMonetizationConfigBody();
		requestBody.put("id", id);
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL + MONETIZATION_CONFIG_CONTROLLER_APIS.EDIT);
		HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
		HttpEntity<ResponseDto<MonetizationConfig>> response = restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<MonetizationConfig>>() {
				});
		ResponseDto<MonetizationConfig> apiResponse = response.getBody();
		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());
	}

	private void testDeleteByIdApi(Long id) throws JsonProcessingException {
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL + MONETIZATION_CONFIG_CONTROLLER_APIS.DELETE_BY_ID)
				.queryParam("id", id);
		HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
		HttpEntity<ResponseDto<MonetizationConfig>> response = restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<MonetizationConfig>>() {
				});

		ResponseDto<MonetizationConfig> apiResponse = response.getBody();
		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());

	}

	private void testDeleteMonetizationConfigApi(Long id) throws JsonProcessingException {

		Map<String, Object> requestBody = getMonetizationConfigBody();
		requestBody.put("id", id);
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL + MONETIZATION_CONFIG_CONTROLLER_APIS.DELETE);
		HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
		HttpEntity<ResponseDto<MonetizationConfig>> response = restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<MonetizationConfig>>() {
				});

		ResponseDto<MonetizationConfig> apiResponse = response.getBody();
		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());
	}

	private Map<String, Object> getMonetizationConfigBody() {
		// Building the Request body data
		Map<String, Object> requestBody = new HashMap<String, Object>();
		requestBody.put("action", "Share");
		requestBody.put("componentType", "Sentiment");
		requestBody.put("createdTime", "2012-12-18T12:12:12");
		requestBody.put("module", "GPI");
		requestBody.put("points", 25);
		requestBody.put("status", "Active");

		return requestBody;
	}

	private Map<String, Object> editMonetizationConfigBody() {
		// Building the Request body data
		Map<String, Object> requestBody = new HashMap<String, Object>();
		requestBody.put("action", "Share");
		requestBody.put("createdTime", "2012-12-18T12:12:12");
		requestBody.put("componentType", "Polls");
		requestBody.put("module", "GA");
		requestBody.put("points", 35);
		requestBody.put("status", "Active");

		return requestBody;
	}
}
