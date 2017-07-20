package com.ohmuk.folitics.controller.response;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.hibernate.entity.Opinion;
import com.ohmuk.folitics.hibernate.entity.Response;
import com.ohmuk.folitics.hibernate.entity.poll.Poll;
import com.ohmuk.folitics.utils.TestDataPreRequisites;
import com.ohmuk.folitics.utils.TestDataUtils;
import com.ohmuk.folitics.utils.TestUtils;

/**
 * @author Abhishek
 *
 */
@RunWith(BlockJUnit4ClassRunner.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestResponseController {

	// Test RestTemplate to invoke the APIs.
	private RestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void TestResponseCURD() throws IOException {
		Map<String, Object> prerequisiteMap = new HashMap<String, Object>();
		prerequisiteMap = TestDataPreRequisites.createResponsePrerequisite();
		Response response = testAddResponseApi(prerequisiteMap);
		testEditResponseApi(response.getId(), prerequisiteMap);
		// TestDataPreRequisites.deleteResponsePrerequisite(prerequisiteMap);
		testGetByOpinionIdResponseApi(response.getOpinion().getId());
		testGetByUserIdResponseApi(response.getUser().getId());
		testDeleteResponseApi(response.getId());
		testPermanentDeleteResponseApi(response.getId());
	}

	private Response testAddResponseApi(Map<String, Object> prerequisiteMap) throws JsonProcessingException {
		HttpEntity<String> httpEntity = TestUtils.getHttpEntity(TestDataUtils.getResponseRequestBody(prerequisiteMap));
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL + TestUtils.RESPONSE_CONTROLLER_APIS.ADD);
		HttpEntity<ResponseDto<Response>> response = restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<Response>>() {
				});

		ResponseDto<Response> apiResponse = response.getBody();
		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());
		Response responseObject = (Response) (response.getBody().getMessages().get(0));
		// commented to avoid infinite recursive json
		// responseObject = TestDataUtils.getResponse(responseObject.getId());
		assertNotNull(responseObject);
		return responseObject;
	}

	private void testEditResponseApi(Long id, Map<String, Object> prerequisiteMap) throws JsonProcessingException {
		Map<String, Object> requestBody = TestDataUtils.getResponseRequestBody(prerequisiteMap);
		String updatedDescription = "this is updated text";
		requestBody.put("id", id);
		requestBody.put("text", updatedDescription);
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL + TestUtils.RESPONSE_CONTROLLER_APIS.EDIT);
		HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
		HttpEntity<ResponseDto<Response>> response = restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<Response>>() {
				});
		ResponseDto<Response> apiResponse = response.getBody();
		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());
		Response responseObject = (Response) (response.getBody().getMessages().get(0));
		// assertTrue(updatedDescription.equals(responseObject.getText()));
	}

	private void testDeleteResponseApi(Long id) throws JsonProcessingException {
		Response responseObject = TestDataUtils.getResponse(id);
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL + TestUtils.RESPONSE_CONTROLLER_APIS.DELETE).queryParam("id", id);
		HttpEntity<?> entity = new HttpEntity<>(responseObject);
		HttpEntity<ResponseDto<Response>> response = restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDto<Response>>() {
				});
		assertNotNull(response);
		assertTrue(response.getBody().getSuccess());
	}

	private void testPermanentDeleteResponseApi(Long id) {
		// Response responseObject = TestDataUtils.getResponse(id);
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL + TestUtils.RESPONSE_CONTROLLER_APIS.PERMANENTDELETE)
				.queryParam("id", id);
		HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
		HttpEntity<ResponseDto<Response>> response = restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<Response>>() {
				});
		assertNotNull(response);
		assertTrue(response.getBody().getSuccess());
	}

	private void testGetByOpinionIdResponseApi(Long id) {
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL + TestUtils.RESPONSE_CONTROLLER_APIS.GET_BY_OPINION_ID)
				.queryParam("id", id);
		HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
		HttpEntity<ResponseDto<Response>> response = restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<Response>>() {
				});
		assertNotNull(response);
		assertTrue(response.getBody().getSuccess());
	}

	private void testGetByUserIdResponseApi(Long id) {
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL + TestUtils.RESPONSE_CONTROLLER_APIS.GET_BY_USER_ID)
				.queryParam("id", id);
		HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
		HttpEntity<ResponseDto<Response>> response = restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<Response>>() {
				});
		assertNotNull(response);
		assertTrue(response.getBody().getSuccess());
	}

}
