package com.ohmuk.folitics.controller.follow;

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
import com.ohmuk.folitics.controller.sentiment.TestSentimentController;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.utils.TestDataPreRequisites;
import com.ohmuk.folitics.utils.TestUtils;

@RunWith(BlockJUnit4ClassRunner.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestFollowController {

    private RestTemplate restTemplate = new TestRestTemplate();
    private final String COMPONENT_TYPE = "sentiment";
    private final Long COMPONENT_ID = 1L;
    private final Long USER_ID = 123L;

    private Map<String, Object> getData() throws JsonProcessingException, IOException {

        Map<String, Object> prerequisiteMap = new HashMap<String, Object>();
        prerequisiteMap.put("componentId", 1l);
        prerequisiteMap.put("componentType", "sentiment");
        prerequisiteMap.put("userId", 123);

      // new  TestSentimentController().testAddMultipartSentiment(TestDataPreRequisites.createSentimentPrerequisites());
        
        
        return prerequisiteMap;

    }


    
    
    
    
    @Test
    public void AddFollowData() throws IOException {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL + "/follow/follow");

        HttpEntity<String> httpEntity = null;
        try {
            httpEntity = TestUtils.getHttpEntity(getData());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpEntity<ResponseDto<Object>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<Object>>() {
                });

    }

    @Test
    public void isComponentFollowedByUser() {

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(TestUtils.BASE_URL + "/follow/isComponentFollowedByUser/?componentId=" + COMPONENT_ID
                        + "&componentType=" + COMPONENT_TYPE + "&userId=" + USER_ID);

        HttpEntity<HttpHeaders> httpEntity = null;
        try {
            httpEntity = TestUtils.getHttpEntityHttpHeaders();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpEntity<ResponseDto<Object>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<Object>>() {
                });

        ResponseDto<Object> apiResponse = response.getBody();

    }

    @Test
    public void getFollowCount() {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + "/follow/getFollowCount/?componentId=" + COMPONENT_ID + "&componentType=" + COMPONENT_TYPE);

        HttpEntity<HttpHeaders> httpEntity = null;
        try {
            httpEntity = TestUtils.getHttpEntityHttpHeaders();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpEntity<ResponseDto<Object>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<Object>>() {
                });

        ResponseDto<Object> apiResponse = response.getBody();
    }

    @Test
    public void unfollow() throws IOException {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL + "/follow/unfollow");

        HttpEntity<String> httpEntity = null;
        try {
            httpEntity = TestUtils.getHttpEntity(getData());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpEntity<ResponseDto<Object>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<Object>>() {
                });
    }
    @Test
    public void getFollowers() throws IOException {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL + "/follow/getFollowers");

        HttpEntity<String> httpEntity = null;
        try {
            httpEntity = TestUtils.getHttpEntity(getData());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpEntity<ResponseDto<Object>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<Object>>() {
                });
    }
}
