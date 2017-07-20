package com.ohmuk.folitics.controller.EventReport;

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
import com.ohmuk.folitics.hibernate.entity.EventReportScore;
import com.ohmuk.folitics.utils.TestUtils;

public class TestEventReportScore {
	private RestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void TestEventReportScoreController() throws JsonProcessingException {

		Long id = testAddEventReportScoreApi();
		testEditEventReportScoreApi(id);
		findAllEventReportScore();
		testPermanentDeleteEventReportScoreApi(id);

	}

	private Long testAddEventReportScoreApi() throws JsonProcessingException {

		Map<String, Object> requestBody = getEventReportScoreBody();
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL
						+ EVENT_REPORT_SCORE_DATA_CONTROLLER_APIS.ADD);
		HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
		HttpEntity<ResponseDto<EventReportScore>> response = restTemplate
				.exchange(
						builder.build().encode().toUri(),
						HttpMethod.POST,
						httpEntity,
						new ParameterizedTypeReference<ResponseDto<EventReportScore>>() {
						});

		ResponseDto<EventReportScore> apiResponse = response.getBody();
		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());
		return ((EventReportScore) response.getBody().getMessages().get(0))
				.getId();
	}

	private void testDeleteEventReportScoreApi(Long id)
			throws JsonProcessingException {
		EventReportScore EventReportScore = getEventReportScore(id);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
				TestUtils.BASE_URL
						+ EVENT_REPORT_SCORE_DATA_CONTROLLER_APIS.DELETE)
				.queryParam("id", id);
		HttpEntity<EventReportScore> entity = new HttpEntity<EventReportScore>(
				EventReportScore);
		HttpEntity<ResponseDto<EventReportScore>> response = restTemplate
				.exchange(
						builder.build().encode().toUri(),
						HttpMethod.POST,
						entity,
						new ParameterizedTypeReference<ResponseDto<EventReportScore>>() {
						});
		assertNotNull(response);
		assertTrue(response.getBody().getSuccess());
	}

	private void testEditEventReportScoreApi(Long id)
			throws JsonProcessingException {
		Map<String, Object> requestBody = getEventReportScoreResponseBody(id);
		requestBody.put("id", id);
		requestBody.put("weblink", "www.yahoo.com");

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL
						+ EVENT_REPORT_SCORE_DATA_CONTROLLER_APIS.EDIT);
		HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
		HttpEntity<ResponseDto<EventReportScore>> response = restTemplate
				.exchange(
						builder.build().encode().toUri(),
						HttpMethod.POST,
						httpEntity,
						new ParameterizedTypeReference<ResponseDto<EventReportScore>>() {
						});
		ResponseDto<EventReportScore> apiResponse = response.getBody();
		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());
		EventReportScore EventReportScore = (EventReportScore) (response
				.getBody().getMessages().get(0));
		assertTrue("www.yahoo.com".equals((EventReportScore.getWeblink())));
	}

	public void findAllEventReportScore() throws JsonProcessingException {
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL
						+ TestUtils.EVENT_REPORT_SCORE_DATA_CONTROLLER_APIS.GET_ALL);
		HttpEntity<HttpHeaders> httpEntity = TestUtils
				.getHttpEntityHttpHeaders();
		HttpEntity<ResponseDto<EventReportScore>> response = restTemplate
				.exchange(
						builder.build().encode().toUri(),
						HttpMethod.GET,
						httpEntity,
						new ParameterizedTypeReference<ResponseDto<EventReportScore>>() {
						});

		ResponseDto<EventReportScore> apiResponse = response.getBody();
		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());
	}

	private void testPermanentDeleteEventReportScoreApi(Long id) {
		EventReportScore eventReportScore = getEventReportScore(id);
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(
						TestUtils.BASE_URL
								+ TestUtils.EVENT_REPORT_SCORE_DATA_CONTROLLER_APIS.PERMANENT_DELETE)
				.queryParam("id", id);
		HttpEntity<EventReportScore> entity = new HttpEntity<EventReportScore>(
				eventReportScore);
		HttpEntity<ResponseDto<EventReportScore>> response = restTemplate
				.exchange(
						builder.build().encode().toUri(),
						HttpMethod.POST,
						entity,
						new ParameterizedTypeReference<ResponseDto<EventReportScore>>() {
						});
		assertNotNull(response);
		assertTrue(response.getBody().getSuccess());
	}

	public static Map<String, Object> getEventReportScoreBody() {
		// Building the Request body data
		Map<String, Object> requestBody = new HashMap<String, Object>();

		// requestBody.put("id",1);
		requestBody.put("time", "2012-12-12T00:00:00");
		requestBody.put("text", 123);
		requestBody.put("uploadedby", 1454043467928l);
		requestBody.put("score", 1);
		requestBody.put("mediaid", 12345);
		requestBody.put("eventtypeid", 11111);
		requestBody.put("eventtype", "2012-12-12T00:00:00");
		requestBody.put("weblink", "www");
		requestBody.put("deleted", 1);
		requestBody.put("editTime", "2012-12-12T00:00:00");
		requestBody.put("createTime", "2012-12-12T00:00:00");
		requestBody.put("state", "New");
		return requestBody;
	}

	public static Map<String, Object> getEventReportScoreResponseBody(long id) {
		// Building the Request body data
		Map<String, Object> requestBody = getEventReportScoreBody();

		requestBody.put("id", id);
		return requestBody;
	}

	public com.ohmuk.folitics.hibernate.entity.EventReportScore getEventReportScore(
			long id) {
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(
						TestUtils.BASE_URL
								+ TestUtils.EVENT_REPORT_SCORE_DATA_CONTROLLER_APIS.FIND)
				.queryParam("id", id);
		HttpEntity<HttpHeaders> httpEntity = TestUtils
				.getHttpEntityHttpHeaders();
		HttpEntity<ResponseDto<EventReportScore>> response = restTemplate
				.exchange(
						builder.build().encode().toUri(),
						HttpMethod.GET,
						httpEntity,
						new ParameterizedTypeReference<ResponseDto<EventReportScore>>() {
						});
		return (EventReportScore) (response.getBody().getMessages().get(0));

	}

	public interface EVENT_REPORT_SCORE_DATA_CONTROLLER_APIS {
		String ADD = "/event/add";
		String EDIT = "/event/edit";
		String GET_ALL = "/event/getall";
		String DELETE_BY_ID = "/event/deletebyid";
		String PERMANENT_DELETE = "/event/deleteFromDB";
		String DELETE = "/event/delete";
		String FIND = "/event/find";
	}
}
