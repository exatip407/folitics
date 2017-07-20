package com.ohmuk.folitics.controller.poll;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.hibernate.entity.poll.Poll;
import com.ohmuk.folitics.utils.TestDataPreRequisites;
import com.ohmuk.folitics.utils.TestDataUtils;
import com.ohmuk.folitics.utils.TestUtils;
import com.voodoodyne.jackson.jsog.JSOGRefSerializer;

@RunWith(BlockJUnit4ClassRunner.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestPollController {
	// Test RestTemplate to invoke the APIs.
	private RestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void TestPollCURD() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			IntrospectionException, IOException {
		Map<String, Object> prerequisitemap = new HashMap<String, Object>();
		prerequisitemap = TestDataPreRequisites.createPollPrerequisite();
		Poll poll1 = testAddPollApi(prerequisitemap);
		Poll poll2 = testAddPollApi(prerequisitemap);

		/*testActivePollsApi();
		testEditPollApi(3l);
		testDeletePollApi(poll1.getId());
		testPermanentDeletePollApi(poll1.getId());
		testPermanentDeletePollApi(poll2.getId());*/
	}

	private Poll testAddPollApi(Map<String, Object> prerequisitemap) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL + TestUtils.POLL_CONTROLLER_APIS.ADD);
		HttpEntity<String> httpEntityPoll = TestUtils.getHttpEntity(TestDataUtils.getPollRequestBody(prerequisitemap));
		HttpEntity<ResponseDto<Poll>> responsePoll = restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.POST, httpEntityPoll, new ParameterizedTypeReference<ResponseDto<Poll>>() {
				});
		ResponseDto<Poll> apiPollResponse = responsePoll.getBody();
		assertNotNull(apiPollResponse);
		// assertTrue(apiPollResponse.getSuccess());
		Poll poll1 = (Poll) (responsePoll.getBody().getMessages().get(0));
		Poll poll2 = TestDataUtils.getPoll(poll1.getId());
		assertNotNull(poll2);
		return poll2;
	}

	private void testEditPollApi(Long id) throws JsonProcessingException, IntrospectionException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Poll poll = TestDataUtils.getPoll(id);

		// Map<String, Object> requestBody =
		// TestDataUtils.getPollRequestBody(prerequisitemap);

		/*
		 * BeanInfo info = Introspector.getBeanInfo(poll.getClass()); for
		 * (PropertyDescriptor pd : info.getPropertyDescriptors()) { Method
		 * reader = pd.getReadMethod(); if (reader != null)
		 * requestBody.put(pd.getName(), reader.invoke(poll)); }
		 */

		String updatedQuestion = "Is this updated question?";
		poll.setQuestion(updatedQuestion);

		/*
		 * requestBody.put("id", id); requestBody.put("question",
		 * updatedQuestion); requestBody.put("editedBy", 11l);
		 */

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL + TestUtils.POLL_CONTROLLER_APIS.EDIT);

		Map<String, Object> map = new ObjectMapper().convertValue(poll, Map.class);
		HttpEntity<String> httpEntity = TestUtils.getHttpEntity(map);

		HttpEntity<ResponseDto<Poll>> response = restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<Poll>>() {
				});

		ResponseDto<Poll> apiResponse = response.getBody();

		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());

		Poll poll2 = (Poll) (response.getBody().getMessages().get(0));
		assertTrue(updatedQuestion.equals(poll2.getQuestion()));
	}

	private void testDeletePollApi(Long id) throws JsonProcessingException {

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL + TestUtils.POLL_CONTROLLER_APIS.DELETE_BY_ID).queryParam("id", id);

		HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();

		HttpEntity<ResponseDto<Poll>> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
				httpEntity, new ParameterizedTypeReference<ResponseDto<Poll>>() {
				});

		assertNotNull(response);
		assertTrue(response.getBody().getSuccess());
	}

	private void testPermanentDeletePollApi(Long id) throws JsonProcessingException {

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL + TestUtils.POLL_CONTROLLER_APIS.PERMANENT_DELETE_BY_ID)
				.queryParam("id", id);

		HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();

		HttpEntity<ResponseDto<Poll>> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
				httpEntity, new ParameterizedTypeReference<ResponseDto<Poll>>() {
				});

		assertNotNull(response);
		assertTrue(response.getBody().getSuccess());
	}

	private void testActivePollsApi() {
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL + TestUtils.POLL_CONTROLLER_APIS.GET_ACTIVE_POLLS);
		HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
		HttpEntity<ResponseDto<Poll>> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
				httpEntity, new ParameterizedTypeReference<ResponseDto<Poll>>() {
				});
		assertNotNull(response);
		assertTrue(response.getBody().getSuccess());
	}

	/*
	 * private void testGetPollForSentiment(Map<String, Object> prerequisitemap)
	 * { Sentiment sentiment = (Sentiment)
	 * prerequisitemap.get(ComponentPrerequisiteMapKeys.SENTIMENT);
	 * UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
	 * TestUtils.BASE_URL +
	 * TestUtils.POLL_CONTROLLER_APIS.GET_POLLS_FOR_SENTIMENT).queryParam("id",
	 * sentiment.getId()); HttpEntity<HttpHeaders> httpEntity =
	 * TestUtils.getHttpEntityHttpHeaders(); HttpEntity<ResponseDto<Poll>>
	 * response = restTemplate.exchange(builder.build().encode().toUri(),
	 * HttpMethod.GET, httpEntity, new
	 * ParameterizedTypeReference<ResponseDto<Poll>>() { });
	 * assertNotNull(response); assertTrue(response.getBody().getSuccess()); }
	 */
}
