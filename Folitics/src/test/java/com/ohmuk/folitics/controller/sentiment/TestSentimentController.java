package com.ohmuk.folitics.controller.sentiment;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.hibernate.entity.Category;
import com.ohmuk.folitics.hibernate.entity.Sentiment;
import com.ohmuk.folitics.utils.TestDataPreRequisites;
import com.ohmuk.folitics.utils.TestDataUtils;
import com.ohmuk.folitics.utils.TestUtils;

@RunWith(BlockJUnit4ClassRunner.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSentimentController {
    // Test RestTemplate to invoke the APIs.
    private RestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void TestSentimentCURD() throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
       Map<String, Object> prerequisiteMap = new HashMap<String, Object>();
        prerequisiteMap = TestDataPreRequisites.createSentimentPrerequisites();
       Long id = testAddMultipartSentiment(prerequisiteMap);
        testGetAllRelatedSentimentSuggetion(id);
        testGetAllSentimentIndicator(id);
        testDeleteSentimentApi(1l);
        testEditSentimentApi(1l);
    }

    // old method to test add sentiment with sentiment only
    /*
     * private Long testAddSentimentApi(Map<String, Object> prerequisiteMap) throws JsonProcessingException {
     * HttpEntity<String> httpEntity = TestUtils.getHttpEntity(TestDataUtils.getSentimentRequestBody(prerequisiteMap));
     * UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL +
     * TestUtils.SENTIMENT_CONTROLLER_APIS.ADD); HttpEntity<ResponseDto<Sentiment>> response =
     * restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST, httpEntity, new
     * ParameterizedTypeReference<ResponseDto<Sentiment>>() { }); ResponseDto<Sentiment> apiResponse =
     * response.getBody(); assertNotNull(apiResponse); assertTrue(apiResponse.getSuccess()); Sentiment sentiment =
     * (Sentiment) (response.getBody().getMessages().get(0)); sentiment = TestDataUtils.getSentiment(sentiment.getId());
     * assertNotNull(sentiment); return sentiment.getId(); }
     */

    private Long testAddMultipartSentiment(Map<String, Object> prerequisiteMap) throws IOException {

        HttpMessageConverter<Object> jackson = new MappingJackson2HttpMessageConverter();
        HttpMessageConverter<byte[]> resource = new ByteArrayHttpMessageConverter();
        FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        formHttpMessageConverter.addPartConverter(jackson);
        formHttpMessageConverter.addPartConverter(resource);

        RestTemplate restTemplate = new RestTemplate(Arrays.asList(jackson, resource, formHttpMessageConverter));

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
        HttpEntity<Resource> image = new HttpEntity<Resource>(file, imageHeaders);

        String mapAsJson = new ObjectMapper()
                .writeValueAsString(TestDataUtils.getSentimentRequestBody(prerequisiteMap));

        HttpHeaders sentimentHeaders = new HttpHeaders();
        sentimentHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> sentimentEntity = new HttpEntity<String>(mapAsJson, sentimentHeaders);

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", image);
        map.add("sentiment", sentimentEntity);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
                map, headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.SENTIMENT_CONTROLLER_APIS.ADD_SENTIMENT);
        HttpEntity<ResponseDto<Sentiment>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<Sentiment>>() {
                });
        ResponseDto<Sentiment> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
        Sentiment sentiment = (Sentiment) (response.getBody().getMessages().get(0));
        /*
         * sentiment = TestDataUtils.getSentiment(sentiment.getId()); assertNotNull(sentiment);
         */
        return sentiment.getId();
    }

    private void testEditSentimentApi(Long id) throws JsonProcessingException,
            IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Sentiment sentiment = TestDataUtils.getSentiment(id);
        
        Map<String, Object> requestBody = new HashMap<String, Object>();
        
        BeanInfo info = Introspector.getBeanInfo(sentiment.getClass());
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            Method reader = pd.getReadMethod();
            if (reader != null)
                requestBody.put(pd.getName(), reader.invoke(sentiment));
        }
        
        String updatedDescription = "This is test updated sentiment";
        requestBody.put("description", updatedDescription);
        
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.SENTIMENT_CONTROLLER_APIS.EDIT);
        
        Map<String, Object> map = new ObjectMapper().convertValue(requestBody, Map.class);
        HttpEntity<String> httpEntity = TestUtils.getHttpEntity(map);
        HttpEntity<ResponseDto<Sentiment>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<Sentiment>>() {
                });
        
        ResponseDto<Sentiment> apiResponse = response.getBody();
        
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
        sentiment = (Sentiment) (response.getBody().getMessages().get(0));
        assertTrue(updatedDescription.equals(sentiment.getDescription()));
    }

    private void testDeleteSentimentApi(Long id) throws JsonProcessingException {
        Sentiment sen = TestDataUtils.getSentiment(id);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.SENTIMENT_CONTROLLER_APIS.DELETE).queryParam("id", id);
        HttpEntity<?> entity = new HttpEntity<>(sen);
        HttpEntity<ResponseDto<Sentiment>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDto<Sentiment>>() {
                });
        assertNotNull(response);
        assertTrue(response.getBody().getSuccess());
    }
    /**
     * Give list of sentiment for suggestion 
     * @param id
     * @author Mayank Sharma
     */
    private void testGetAllRelatedSentimentSuggetion(Long id){
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.SENTIMENT_CONTROLLER_APIS.RELATED_SENTIMENT_SUGGESTION);
        Set<Long> ids = new HashSet<Long>();
        ids.add(id);
        HttpEntity<?> entity = new HttpEntity<>(ids);
        HttpEntity<ResponseDto<Sentiment>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDto<Sentiment>>() {
                });
        assertNotNull(response);
        assertTrue(response.getBody().getSuccess());
    }
    /**
     * Give list of indicator for sentiment
     * @param id
     * @author Mayank Sharma
     */
    private void testGetAllSentimentIndicator(Long id) throws JsonParseException{
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.SENTIMENT_CONTROLLER_APIS.GET_SENTIMENT_INDICATOR).queryParam("id", id);
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<Set<Category>>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<Set<Category>>>() {
                });
        assertNotNull(response);
        assertTrue(response.getBody().getSuccess());
    }
}
