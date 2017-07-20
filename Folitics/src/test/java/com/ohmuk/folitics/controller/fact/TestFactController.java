package com.ohmuk.folitics.controller.fact;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
import com.ohmuk.folitics.enums.FactType;
import com.ohmuk.folitics.hibernate.entity.Fact;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.utils.TestUtils;
import com.ohmuk.folitics.utils.TestUtils.FACT_CONTROLLER_APIS;

@RunWith(BlockJUnit4ClassRunner.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestFactController {
	private RestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void testFactController() throws JsonProcessingException {

		Long id = testAddFactApi();

		getAllFacts();
		getAllFactsByStatus("Pending");
		getAllFactsByUserId(1l);
		getAllFactsByParentIdAndParentType(1l, "Scheme");

		testGetFact(id);
		testEditFactApi(id);
		testFactSoftDeleteApi(id);
		testApproveFactApi(id);
		testRejectFactApi(id);
		testFactPermanentDeleteApi(id);

	}

	public Long testAddFactApi() throws JsonProcessingException {
		Map<String, Object> requestBody = factRequestBody();
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL
						+ TestUtils.FACT_CONTROLLER_APIS.ADD);
		HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
		HttpEntity<ResponseDto<Fact>> response = restTemplate.exchange(builder
				.build().encode().toUri(), HttpMethod.POST, httpEntity,
				new ParameterizedTypeReference<ResponseDto<Fact>>() {
				});

		ResponseDto<Fact> apiResponse = response.getBody();
		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());
		return ((Fact) response.getBody().getMessages().get(0)).getId();

	}

	public void getAllFacts() throws JsonProcessingException {

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL
						+ TestUtils.FACT_CONTROLLER_APIS.GET_ALL);

		HttpEntity<HttpHeaders> httpEntity = TestUtils
				.getHttpEntityHttpHeaders();

		HttpEntity<ResponseDto<Fact>> response = restTemplate.exchange(builder
				.build().encode().toUri(), HttpMethod.GET, httpEntity,
				new ParameterizedTypeReference<ResponseDto<Fact>>() {
				});

		ResponseDto<Fact> apiResponse = response.getBody();

		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());
	}

	public void getAllFactsByStatus(String status)
			throws JsonProcessingException {

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
				TestUtils.BASE_URL
						+ TestUtils.FACT_CONTROLLER_APIS.GET_ALL_BY_STATUS)
				.queryParam("status", status);

		HttpEntity<HttpHeaders> httpEntity = TestUtils
				.getHttpEntityHttpHeaders();

		HttpEntity<ResponseDto<Fact>> response = restTemplate.exchange(builder
				.build().encode().toUri(), HttpMethod.GET, httpEntity,
				new ParameterizedTypeReference<ResponseDto<Fact>>() {
				});

		ResponseDto<Fact> apiResponse = response.getBody();

		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());
	}

	public void getAllFactsByUserId(Long userId) throws JsonProcessingException {

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
				TestUtils.BASE_URL + "/fact/getAllFactsByUserId").queryParam(
				"userId", userId);

		HttpEntity<HttpHeaders> httpEntity = TestUtils
				.getHttpEntityHttpHeaders();

		HttpEntity<ResponseDto<Fact>> response = restTemplate.exchange(builder
				.build().encode().toUri(), HttpMethod.GET, httpEntity,
				new ParameterizedTypeReference<ResponseDto<Fact>>() {
				});

		ResponseDto<Fact> apiResponse = response.getBody();

		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());
	}

	public void getAllFactsByParentIdAndParentType(Long parentId,
			String parentType) throws JsonProcessingException {

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(
						TestUtils.BASE_URL
								+ "/fact/getAllFactsByParentIdAndParentType")
				.queryParam("parentId", parentId)
				.queryParam("parentType", parentType);

		HttpEntity<HttpHeaders> httpEntity = TestUtils
				.getHttpEntityHttpHeaders();

		HttpEntity<ResponseDto<Fact>> response = restTemplate.exchange(builder
				.build().encode().toUri(), HttpMethod.GET, httpEntity,
				new ParameterizedTypeReference<ResponseDto<Fact>>() {
				});

		ResponseDto<Fact> apiResponse = response.getBody();

		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());
	}

	public Fact testGetFact(Long id) {

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
				TestUtils.BASE_URL + "/fact/getFact").queryParam("id", id);

		HttpEntity<HttpHeaders> httpEntity = TestUtils
				.getHttpEntityHttpHeaders();

		HttpEntity<ResponseDto<Fact>> response = restTemplate.exchange(builder
				.build().encode().toUri(), HttpMethod.GET, httpEntity,
				new ParameterizedTypeReference<ResponseDto<Fact>>() {
				});

		return (Fact) (response.getBody().getMessages().get(0));
	}

	private void testEditFactApi(Long id) throws JsonProcessingException {
		Map<String, Object> requestBody = factRequestBody();
		String status = "Updated";
		requestBody.put("id", id);
		requestBody.put("status", status);

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL
						+ TestUtils.FACT_CONTROLLER_APIS.EDIT);
		HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
		HttpEntity<ResponseDto<Fact>> response = restTemplate.exchange(builder
				.build().encode().toUri(), HttpMethod.POST, httpEntity,
				new ParameterizedTypeReference<ResponseDto<Fact>>() {
				});
		ResponseDto<Fact> apiResponse = response.getBody();
		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());
		Fact fact = (Fact) (response.getBody().getMessages().get(0));
		assertTrue(status.equals(fact.getStatus()));
	}

	private void testFactSoftDeleteApi(Long id) {

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
				TestUtils.BASE_URL + TestUtils.FACT_CONTROLLER_APIS.DELETE)
				.queryParam("id", id);

		HttpEntity<Long> entity = new HttpEntity<Long>(id);
		HttpEntity<ResponseDto<Fact>> response = restTemplate.exchange(builder
				.build().encode().toUri(), HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseDto<Fact>>() {
				});

		assertNotNull(response);
		assertTrue(response.getBody().getSuccess());
	}

	private void testFactPermanentDeleteApi(Long id) {

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
				TestUtils.BASE_URL + FACT_CONTROLLER_APIS.DELETE_FROM_DB)
				.queryParam("id", id);

		HttpEntity<Long> entity = new HttpEntity<Long>(id);
		HttpEntity<ResponseDto<Fact>> response = restTemplate.exchange(builder
				.build().encode().toUri(), HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseDto<Fact>>() {
				});

		assertNotNull(response);
		assertTrue(response.getBody().getSuccess());
	}

	private Fact testApproveFactApi(Long id) throws JsonProcessingException {

		Map<String, Object> requestBody = factRequestBody();
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL
						+ FACT_CONTROLLER_APIS.APPROVE_FACT);

		HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
		HttpEntity<ResponseDto<Fact>> response = restTemplate.exchange(builder
				.build().encode().toUri(), HttpMethod.POST, httpEntity,
				new ParameterizedTypeReference<ResponseDto<Fact>>() {
				});
		return (Fact) (response.getBody().getMessages().get(0));

	}

	private Fact testRejectFactApi(Long id) {

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
				TestUtils.BASE_URL + FACT_CONTROLLER_APIS.REJECT_FACT)
				.queryParam("factId", id);

		HttpEntity<HttpHeaders> httpEntity = TestUtils
				.getHttpEntityHttpHeaders();
		HttpEntity<ResponseDto<Fact>> response = restTemplate.exchange(builder
				.build().encode().toUri(), HttpMethod.GET, httpEntity,
				new ParameterizedTypeReference<ResponseDto<Fact>>() {
				});
		return (Fact) (response.getBody().getMessages().get(0));

	}

	public static Map<String, Object> factRequestBody()
			throws JsonProcessingException {

		Map<String, Object> requestBody = new HashMap<String, Object>();
		User user = new User();
		user.setId(1l);
		requestBody.put("id", 1);
		requestBody.put("creationTime", 1454043467928l);
		requestBody.put("description", "desc");
		requestBody.put("editTime", 1454043467928l);
		requestBody.put("parentId", 1);
		requestBody.put("parentName", "dsfs");
		requestBody.put("parentType", "Scheme");
		requestBody.put("user", user);
		requestBody.put("status", FactType.PENDING);
		requestBody.put("subject", "wpage");
		return requestBody;

	}

}
