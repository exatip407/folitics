package com.ohmuk.folitics.controller.indicatordata;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
import com.ohmuk.folitics.hibernate.entity.Category;
import com.ohmuk.folitics.hibernate.entity.IndicatorData;
import com.ohmuk.folitics.hibernate.entity.IndicatorWeightedData;
import com.ohmuk.folitics.utils.TestDataPreRequisites;
import com.ohmuk.folitics.utils.TestDataUtils;
import com.ohmuk.folitics.utils.TestUtils;

@RunWith(BlockJUnit4ClassRunner.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestIndicatorDataController {
    private RestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void TestIndicatorDataController() throws JsonProcessingException {
    	IndicatorData inidcatorData = testAddIndicatorDataApi();
    	Long id = inidcatorData.getId();
    	Long categoryId = inidcatorData.getCategory().getId();
        testDeleteIndicatorDataApi(id);
        testEditIndicatorDataApi(id,categoryId);
        findAllIndicatorData();
        testPermanentDeleteIndicatorDataApi(id);
    }

    public IndicatorData testAddIndicatorDataApi() throws JsonProcessingException {

    	 Category category = null;
 		try {
 			category = TestDataPreRequisites.insertIndicator();
 		} catch (JsonProcessingException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
    	Long id = TestDataUtils.testPreAddIndicatorThresholdApi(category);
        Long id1 = TestDataUtils.testPreAddIndicatorWeightedDataApi(category);

        Map<String, Object> requestBody = TestDataUtils.getIndicatorDataRequestBody(category);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.INDICATOR_DATA_CONTROLLER_APIS.ADD);
        HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
        HttpEntity<ResponseDto<IndicatorData>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<IndicatorData>>() {
                });

        ResponseDto<IndicatorData> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
        return (IndicatorData) response.getBody().getMessages().get(0);

    }

    private void testDeleteIndicatorDataApi(Long id) throws JsonProcessingException {
        IndicatorData indicatordata = TestDataUtils.getIndicatorData(id);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.INDICATOR_DATA_CONTROLLER_APIS.DELETE).queryParam("id", id);
        HttpEntity<IndicatorData> entity = new HttpEntity<IndicatorData>(indicatordata);
        HttpEntity<ResponseDto<IndicatorData>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDto<IndicatorData>>() {
                });
        assertNotNull(response);
        assertTrue(response.getBody().getSuccess());
    }

    private void testEditIndicatorDataApi(Long id,Long categoryID) throws JsonProcessingException {
        Map<String, Object> requestBody = TestDataUtils.getIndicatorDataResponseBody(categoryID);
        requestBody.put("id", id);
        requestBody.put("score", 100);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.INDICATOR_DATA_CONTROLLER_APIS.EDIT);
        HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
        HttpEntity<ResponseDto<IndicatorData>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<IndicatorData>>() {
                });
        ResponseDto<IndicatorData> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
        IndicatorData indicator = (IndicatorData) (response.getBody().getMessages().get(0));
        assertTrue(100.00 == (indicator.getScore()));
    }

    public Long testAddIndicatorWeightedDataApi() throws JsonProcessingException {
        Map<String, Object> requestBody = TestDataUtils.getIndicatorWeightedDataRequestBody();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.INDICATOR_WEIGHTED_DATA_CONTROLLER_APIS.ADD);
        HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
        HttpEntity<ResponseDto<IndicatorWeightedData>> response = restTemplate.exchange(builder.build().encode()
                .toUri(), HttpMethod.POST, httpEntity,
                new ParameterizedTypeReference<ResponseDto<IndicatorWeightedData>>() {
                });

        ResponseDto<IndicatorWeightedData> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
        return ((IndicatorWeightedData) response.getBody().getMessages().get(0)).getId();

    }
    
    public void findAllIndicatorData() throws JsonProcessingException {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(TestUtils.BASE_URL + TestUtils.INDICATOR_DATA_CONTROLLER_APIS.GET_ALL);
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<IndicatorData>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<IndicatorData>>() {
                });

        ResponseDto<IndicatorData> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
    }
    
    
    private void testPermanentDeleteIndicatorDataApi(Long id) {
        IndicatorData indicatorData = TestDataUtils.getIndicatorData(id);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.INDICATOR_DATA_CONTROLLER_APIS.PERMANENT_DELETE).queryParam("id", id);
        HttpEntity<IndicatorData> entity = new HttpEntity<IndicatorData>(indicatorData);
        HttpEntity<ResponseDto<IndicatorData>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDto<IndicatorData>>() {
                });
        assertNotNull(response);
        assertTrue(response.getBody().getSuccess());
    }
    
    public IndicatorData getIndicatorData(Long id)
    {
    	UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
    			TestUtils.BASE_URL + TestUtils.INDICATOR_DATA_CONTROLLER_APIS.FIND)
    			.queryParam("id", id);
    	HttpEntity<HttpHeaders> httpEntity = TestUtils
    			.getHttpEntityHttpHeaders();
    	HttpEntity<ResponseDto<IndicatorData>> response = restTemplate.exchange(
    			builder.build().encode().toUri(), HttpMethod.GET, httpEntity,
    			new ParameterizedTypeReference<ResponseDto<IndicatorData>>() {
    			});
    	return (IndicatorData) (response.getBody().getMessages().get(0));
    }
}
