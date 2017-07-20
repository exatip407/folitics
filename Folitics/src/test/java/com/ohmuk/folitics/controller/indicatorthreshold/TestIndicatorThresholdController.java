package com.ohmuk.folitics.controller.indicatorthreshold;

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
import com.ohmuk.folitics.hibernate.entity.Category;
import com.ohmuk.folitics.hibernate.entity.IndicatorThreshold;
import com.ohmuk.folitics.utils.TestDataPreRequisites;
//import com.ohmuk.folitics.utils.TestDataUtils;
import com.ohmuk.folitics.utils.TestUtils;

@RunWith(BlockJUnit4ClassRunner.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestIndicatorThresholdController {
    private static RestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void TestIndicatorThresholdController() throws JsonProcessingException {
    	
        IndicatorThreshold indicatorThreshold  = testAddIndicatorThresholdApi();
        Long id = indicatorThreshold.getId();
        Long categoryId = indicatorThreshold.getCategory().getId();
        testEditIndicatorDataApi(id,categoryId);
        testDeleteIndicatorThresholdApi(id);
        testPermanentDeleteApi(id);
    }

    public IndicatorThreshold testAddIndicatorThresholdApi() throws JsonProcessingException {
        Map<String, Object> requestBody = getIndicatorThresholdRequestBody();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL + "indicatorthreshold/add");
        HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
        HttpEntity<ResponseDto<IndicatorThreshold>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<IndicatorThreshold>>() {
                });

        ResponseDto<IndicatorThreshold> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
        return (IndicatorThreshold) response.getBody().getMessages().get(0);
    }

    private void testDeleteIndicatorThresholdApi(Long id) throws JsonProcessingException {
        IndicatorThreshold indicatorthreshold = getIndicatorThresholdData(id);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + "indicatorthreshold/delete").queryParam("id", id);
        HttpEntity<IndicatorThreshold> entity = new HttpEntity<IndicatorThreshold>(indicatorthreshold);
        HttpEntity<ResponseDto<IndicatorThreshold>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDto<IndicatorThreshold>>() {
                });
        assertNotNull(response);
        assertTrue(response.getBody().getSuccess());
    }

    private void testEditIndicatorDataApi(Long id , Long categoryId) throws JsonProcessingException {
        Map<String, Object> requestBody = getIndicatorThresholdResponseBody(id,categoryId);
        requestBody.put("id", id);
        requestBody.put("direction", 15);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL + "indicatorthreshold/edit");
        HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
        HttpEntity<ResponseDto<IndicatorThreshold>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<IndicatorThreshold>>() {
                });
        ResponseDto<IndicatorThreshold> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
        IndicatorThreshold indicator = (IndicatorThreshold) (response.getBody().getMessages().get(0));
        assertTrue(15.00 == (indicator.getDirection()));
    }
    
    private void testPermanentDeleteApi(Long id) {
        
    	IndicatorThreshold  indicatorthreshold = getEntity(id);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.INDICATOR_THRESHOLD_DATA_CONTROLLER_APIS.PERMANENT_DELETE).queryParam("id", id);
        HttpEntity<Long> entity = new HttpEntity<Long>(id);
        HttpEntity<ResponseDto<IndicatorThreshold>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, entity, new ParameterizedTypeReference<ResponseDto<IndicatorThreshold>>() {
                });
        assertNotNull(response);
        assertTrue(response.getBody().getSuccess());
    }
    
    public IndicatorThreshold getEntity(Long id)
    {
    	UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
    			TestUtils.BASE_URL + TestUtils.INDICATOR_THRESHOLD_DATA_CONTROLLER_APIS.FIND)
    			.queryParam("id", id);
    	HttpEntity<HttpHeaders> httpEntity = TestUtils
    			.getHttpEntityHttpHeaders();
    	HttpEntity<ResponseDto<IndicatorThreshold>> response = restTemplate.exchange(
    			builder.build().encode().toUri(), HttpMethod.GET, httpEntity,
    			new ParameterizedTypeReference<ResponseDto<IndicatorThreshold>>() {
    			});
    	return (IndicatorThreshold) (response.getBody().getMessages().get(0));
    }
    
    public static Map<String, Object> getIndicatorThresholdRequestBody() {
        // Building the Request body data
        Map<String, Object> requestBody = new HashMap<String, Object>();
        Category category = null;
        try {
            category = TestDataPreRequisites.insertIndicator();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // requestBody.put("id",1);
        requestBody.put("createTime", 1454043467928l);
        requestBody.put("editTime", 1454043467928l);
        requestBody.put("direction", 5);
        requestBody.put("category", category);
        requestBody.put("state", "true");
        // requestBody.put("threshold", 4);
        requestBody.put("threshold_end", 4);
        requestBody.put("threshold_start", 4);
        requestBody.put("threshHoldCategory", "UnSatisfactory");

        return requestBody;
    }
    public static IndicatorThreshold getIndicatorThresholdData(Long id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.INDICATOR_THRESHOLD_DATA_CONTROLLER_APIS.FIND).queryParam("id", id);
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<IndicatorThreshold>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<IndicatorThreshold>>() {
                });
        return (IndicatorThreshold) (response.getBody().getMessages().get(0));
    }
    public static Map<String, Object> getIndicatorThresholdResponseBody(long id, Long categoryId) {
        // Building the Request body data
        Map<String, Object> requestBody = new HashMap<String, Object>();
        Category category = new Category();
        category.setId(categoryId);
        // requestBody.put("id",1);
        requestBody.put("createTime", 1454043467928l);
        requestBody.put("editTime", 1454043467928l);
        requestBody.put("direction", 5);
        requestBody.put("category", category);
        requestBody.put("state", "true");
        // requestBody.put("threshold", 4);
        requestBody.put("threshold_end", 4);
        requestBody.put("threshold_start", 4);
        requestBody.put("threshHoldCategory", "UnSatisfactory");
        return requestBody;
        /*
         * UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl( TestUtils.BASE_URL +
         * TestUtils.INDICATOR_THRESHOLD_DATA_CONTROLLER_APIS.FIND).queryParam("id", id); HttpEntity<HttpHeaders>
         * httpEntity = TestUtils.getHttpEntityHttpHeaders(); HttpEntity<ResponseDto<IndicatorThreshold>> response =
         * restTemplate.exchange(builder.build().encode() .toUri(), HttpMethod.GET, httpEntity, new
         * ParameterizedTypeReference<ResponseDto<IndicatorThreshold>>() { }); return (Map<String,Object>)
         * (response.getBody().getMessages().get(0));
         */

    }

}
