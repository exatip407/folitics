package com.ohmuk.folitics.controller.luckydraw;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.enums.ContestType;
import com.ohmuk.folitics.enums.FileType;
import com.ohmuk.folitics.hibernate.entity.Contest;
import com.ohmuk.folitics.hibernate.entity.LuckyDraw;
import com.ohmuk.folitics.util.DateUtils;
import com.ohmuk.folitics.utils.TestUtils;

@RunWith(BlockJUnit4ClassRunner.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestLuckyDrawController {

	static Long id;
	static Long contestId;
	private RestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void TestLuckyDraw() throws IOException {

		Contest contest = testAddContestWithLuckyDrawApi();
		contestId = contest.getId();
		Set<LuckyDraw> luckyDraw = contest.getLuckyDraw();

		for (LuckyDraw l : luckyDraw) {
			id = l.getId();
			break;
		}

		testFindAllLuckyDraw();
		testEditLuckyDrawtApi(id);
		testGetLuckyDraw(id);
		findAllActiveLuckyDraw();
		testLuckyDrawSoftDeleteApi(id);
		testDeleteLuckyDrawApi(id);
	}

	public void testFindAllLuckyDraw() throws JsonProcessingException {

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL
						+ TestUtils.LUCKYDRAW_CONTROLLER_APIS.FIND_All);

		HttpEntity<HttpHeaders> httpEntity = TestUtils
				.getHttpEntityHttpHeaders();

		HttpEntity<ResponseDto<Contest>> response = restTemplate.exchange(
				builder.build().encode().toUri(), HttpMethod.GET, httpEntity,
				new ParameterizedTypeReference<ResponseDto<Contest>>() {
				});

		ResponseDto<Contest> apiResponse = response.getBody();

		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());
	}

	private void testDeleteLuckyDrawApi(Long id) {

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
				TestUtils.BASE_URL
						+ TestUtils.LUCKYDRAW_CONTROLLER_APIS.HARD_DELETE)
				.queryParam("id", id);

		HttpEntity<Long> entity = new HttpEntity<Long>(id);
		HttpEntity<ResponseDto<Contest>> response = restTemplate.exchange(
				builder.build().encode().toUri(), HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseDto<Contest>>() {
				});

		assertNotNull(response);
		assertTrue(response.getBody().getSuccess());
	}

	private void testEditLuckyDrawtApi(Long id) throws JsonProcessingException {
		Map<String, Object> requestBody = getLuckyDrawRequestBody();

		String description = "updated description";
		requestBody.put("id", id);
		requestBody.put("description", description);

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL
						+ TestUtils.LUCKYDRAW_CONTROLLER_APIS.EDIT);
		HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
		HttpEntity<ResponseDto<LuckyDraw>> response = restTemplate.exchange(
				builder.build().encode().toUri(), HttpMethod.POST, httpEntity,
				new ParameterizedTypeReference<ResponseDto<LuckyDraw>>() {
				});
		ResponseDto<LuckyDraw> apiResponse = response.getBody();
		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());
		LuckyDraw luckyDraw = (LuckyDraw) (response.getBody().getMessages()
				.get(0));
		assertTrue(description.equals(luckyDraw.getDescription()));
	}

	public Map<String, Object> getLuckyDrawRequestBody()
			throws JsonProcessingException {
		// Building the Request body data

		// Map<String, Object> requestBody1 = getContestRequestBody();
		Contest contest = new Contest();
		contest.setId(contestId);

		Map<String, Object> requestBody = new HashMap<String, Object>();
		requestBody.put("id", 1);
		requestBody.put("creationTime", 1454043467928l);
		requestBody.put("description", "Fantastic");
		requestBody.put("editTime", 1454043467928l);
		requestBody.put("expiryTime", 1454043467928l);
		requestBody.put("prizeAmount", "crore");
		requestBody.put("contestType", ContestType.BUMPER);
		requestBody.put("state", ContestType.ACTIVE);
		requestBody.put("participationPoints", 100);
		requestBody.put("contest", contest);

		return requestBody;
	}

	public Map<String, Object> getContestRequestBody()
			throws JsonProcessingException {

		Map<String, Object> requestBody = new HashMap<String, Object>();

		requestBody.put("creationTime", 1454043467928l);
		requestBody.put("description", "Fantastic");
		requestBody.put("editTime", 1454043467928l);
		requestBody.put("expiryTime", 1454043467928l);
		requestBody.put("name", "contest");
		requestBody.put("state", ContestType.ACTIVE);
		requestBody.put("imageType", FileType.JPEG);
		requestBody.put("termsAndCondition", "easy understanding");

		return requestBody;

	}

	public LuckyDraw testGetLuckyDraw(Long id) {

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
				TestUtils.BASE_URL + TestUtils.LUCKYDRAW_CONTROLLER_APIS.FIND)
				.queryParam("id", id);

		HttpEntity<HttpHeaders> httpEntity = TestUtils
				.getHttpEntityHttpHeaders();

		HttpEntity<ResponseDto<LuckyDraw>> response = restTemplate.exchange(
				builder.build().encode().toUri(), HttpMethod.GET, httpEntity,
				new ParameterizedTypeReference<ResponseDto<LuckyDraw>>() {
				});

		return (LuckyDraw) (response.getBody().getMessages().get(0));
	}

	public void findAllActiveLuckyDraw() throws JsonProcessingException {

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL
						+ "/luckyDraw/readAllActiveLuckyDraw");

		HttpEntity<HttpHeaders> httpEntity = TestUtils
				.getHttpEntityHttpHeaders();

		HttpEntity<ResponseDto<LuckyDraw>> response = restTemplate.exchange(
				builder.build().encode().toUri(), HttpMethod.GET, httpEntity,
				new ParameterizedTypeReference<ResponseDto<LuckyDraw>>() {
				});

		ResponseDto<LuckyDraw> apiResponse = response.getBody();

		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());
	}

	private void testLuckyDrawSoftDeleteApi(Long id)
			throws JsonProcessingException {
		LuckyDraw luckyDraw = testGetLuckyDraw(id);
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(
						TestUtils.BASE_URL
								+ TestUtils.LUCKYDRAW_CONTROLLER_APIS.DELETE)
				.queryParam("id", id);
		HttpEntity<LuckyDraw> entity = new HttpEntity<LuckyDraw>(luckyDraw);
		HttpEntity<ResponseDto<LuckyDraw>> response = restTemplate.exchange(
				builder.build().encode().toUri(), HttpMethod.POST, entity,
				new ParameterizedTypeReference<ResponseDto<LuckyDraw>>() {
				});
		assertNotNull(response);
		assertTrue(response.getBody().getSuccess());
	}

	public Contest testAddContestWithLuckyDrawApi()
			throws JsonProcessingException, IOException {

		HttpMessageConverter<Object> jackson = new MappingJackson2HttpMessageConverter();
		HttpMessageConverter<byte[]> resource = new ByteArrayHttpMessageConverter();
		FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
		formHttpMessageConverter.addPartConverter(jackson);
		formHttpMessageConverter.addPartConverter(resource);

		RestTemplate restTemplate = new RestTemplate(Arrays.asList(jackson,
				resource, formHttpMessageConverter));

		Path path = Paths.get(TestUtils.TEST_IMAGE);
		byte[] data = Files.readAllBytes(path);

		Resource file = new ByteArrayResource(data) {
			@Override
			public String getFilename() {
				return "testimage.jpg";
			}
		};

		HttpHeaders imageHeaders = new HttpHeaders();
		imageHeaders.setContentType(MediaType.IMAGE_JPEG);
		HttpEntity<Resource> image = new HttpEntity<Resource>(file,
				imageHeaders);

		Map<String, Object> requestBody = getContestRequestBodyLuckyDraw();

		String mapAsJson = new ObjectMapper().writeValueAsString(requestBody);

		HttpHeaders contestHeaders = new HttpHeaders();
		contestHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> contestEntity = new HttpEntity<String>(mapAsJson,
				contestHeaders);

		LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("file", image);
		map.add("contest", contestEntity);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
				map, headers);

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL
						+ TestUtils.CONTEST_CONTROLLER_APIS.ADD);
		HttpEntity<ResponseDto<Contest>> response = restTemplate.exchange(
				builder.build().encode().toUri(), HttpMethod.POST, httpEntity,
				new ParameterizedTypeReference<ResponseDto<Contest>>() {
				});
		ResponseDto<Contest> apiResponse = response.getBody();
		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());
		Contest contest = (Contest) (response.getBody().getMessages().get(0));

		return contest;

	}

	public Map<String, Object> getContestRequestBodyLuckyDraw()
			throws JsonProcessingException {

		Map<String, Object> requestBody = new HashMap<String, Object>();

		LuckyDraw luckyDraw = new LuckyDraw();

		luckyDraw.setContestType(ContestType.BUMPER.getValue());
		luckyDraw.setCreationTime(DateUtils.getSqlTimeStamp());
		luckyDraw.setDescription("very intresting");
		luckyDraw.setEditTime(DateUtils.getSqlTimeStamp());
		luckyDraw.setExpiryTime(DateUtils.getSqlTimeStamp());
		luckyDraw.setParticipationPoints(100l);
		luckyDraw.setPrizeAmount("crore");
		luckyDraw.setState(ContestType.ACTIVE.getValue());

		LuckyDraw luckyDraw1 = new LuckyDraw();

		luckyDraw1.setContestType(ContestType.MEGA.getValue());
		luckyDraw1.setCreationTime(DateUtils.getSqlTimeStamp());
		luckyDraw1.setDescription("very intresting");
		luckyDraw1.setEditTime(DateUtils.getSqlTimeStamp());
		luckyDraw1.setExpiryTime(DateUtils.getSqlTimeStamp());
		luckyDraw1.setParticipationPoints(100l);
		luckyDraw1.setPrizeAmount("crore");
		luckyDraw1.setState(ContestType.ACTIVE.getValue()); //
		LuckyDraw luckyDraw2 = new LuckyDraw();

		luckyDraw2.setContestType(ContestType.MINI.getValue());
		luckyDraw2.setCreationTime(DateUtils.getSqlTimeStamp());
		luckyDraw2.setDescription("very intresting");
		luckyDraw2.setEditTime(DateUtils.getSqlTimeStamp());
		luckyDraw2.setExpiryTime(DateUtils.getSqlTimeStamp());
		luckyDraw2.setParticipationPoints(100l);
		luckyDraw2.setPrizeAmount("crore");
		luckyDraw2.setState(ContestType.ACTIVE.getValue());

		Set<LuckyDraw> luckyDraws = new HashSet<LuckyDraw>();
		luckyDraws.add(luckyDraw);
		luckyDraws.add(luckyDraw1);
		luckyDraws.add(luckyDraw2);

		requestBody.put("creationTime", 1454043467928l);
		requestBody.put("description", "Fantastic");
		requestBody.put("editTime", 1454043467928l);
		requestBody.put("expiryTime", 1454043467928l);
		requestBody.put("luckyDraw", luckyDraws);
		requestBody.put("name", "contest");
		requestBody.put("state", ContestType.ACTIVE);
		requestBody.put("imageType", FileType.JPEG);
		requestBody.put("termsAndCondition", "easy understanding");

		return requestBody;

	}

}
