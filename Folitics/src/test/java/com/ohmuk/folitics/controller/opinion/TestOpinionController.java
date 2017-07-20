package com.ohmuk.folitics.controller.opinion;

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
import java.util.Map;

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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.hibernate.entity.Opinion;
import com.ohmuk.folitics.hibernate.entity.Sentiment;
import com.ohmuk.folitics.utils.TestDataPreRequisites;
import com.ohmuk.folitics.utils.TestDataUtils;
import com.ohmuk.folitics.utils.TestUtils;

@RunWith(BlockJUnit4ClassRunner.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestOpinionController {

    // Test RestTemplate to invoke the APIs.
    private RestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void TestCURD() throws IOException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, IntrospectionException {
        Map<String, Object> prerequisiteMap = TestDataPreRequisites.createOpinionPrerequisite();
        Long id = testAddOpinionApi(prerequisiteMap);
        testEditOpinionApi(id, prerequisiteMap);
        testDeleteOpinionApi(id);
        testPermanentDeleteOpinionApi(id);
        TestDataPreRequisites.deleteOpinionPrerequisite(prerequisiteMap);
    }

    private Long testAddOpinionApi(Map<String, Object> prerequisiteMap) throws IOException {

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

        String mapAsJson = new ObjectMapper().writeValueAsString(TestDataUtils.getOpinionRequestBody(prerequisiteMap));

        HttpHeaders opinionHeaders = new HttpHeaders();
        opinionHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> opinionEntity = new HttpEntity<String>(mapAsJson, opinionHeaders);

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", image);
        map.add("opinion", opinionEntity);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
                map, headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.OPINION_CONTROLLER_APIS.ADD);
        HttpEntity<ResponseDto<Opinion>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<Opinion>>() {
                });
        ResponseDto<Opinion> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
        Opinion opinion = (Opinion) (response.getBody().getMessages().get(0));
        opinion = TestDataUtils.getOpinion(opinion.getId());
        assertNotNull(opinion);
        return opinion.getId();
    }

    private void testEditOpinionApi(Long id, Map<String, Object> prerequisiteMap) throws IntrospectionException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {

        Opinion opinion = TestDataUtils.getOpinion(id);

        Sentiment sentiment = (Sentiment) prerequisiteMap
                .get(TestDataPreRequisites.ComponentPrerequisiteMapKeys.SENTIMENT);

        opinion.setSentiment(sentiment);

        String updatedDescription = "This is test updated opinion";

        Map<String, Object> requestBody = new HashMap<String, Object>();

        BeanInfo info = Introspector.getBeanInfo(opinion.getClass());
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            Method reader = pd.getReadMethod();
            if (reader != null)
                requestBody.put(pd.getName(), reader.invoke(opinion));
        }

        requestBody.put("text", updatedDescription);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.OPINION_CONTROLLER_APIS.EDIT);
        
        Map<String, Object> map = new ObjectMapper().convertValue(requestBody, Map.class);

        HttpEntity<String> httpEntity = TestUtils.getHttpEntity(map);
        
        
        
        HttpEntity<ResponseDto<Opinion>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<Opinion>>() {
                });

        ResponseDto<Opinion> apiResponse = response.getBody();

        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
        opinion = (Opinion) (response.getBody().getMessages().get(0));
        assertTrue(updatedDescription.equals(opinion.getText()));
    }

    private void testDeleteOpinionApi(Long id) throws IOException {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.OPINION_CONTROLLER_APIS.DELETE_BY_ID).queryParam("id", id);

        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();

        HttpEntity<ResponseDto<Opinion>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<Opinion>>() {
                });
        
        assertNotNull(response);
        assertTrue(response.getBody().getSuccess());
    }

    private void testPermanentDeleteOpinionApi(Long id) throws JsonParseException, JsonMappingException, IOException {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.OPINION_CONTROLLER_APIS.PERMANENT_DELETE_BY_ID).queryParam("id", id);

        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();

        HttpEntity<ResponseDto<Opinion>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<Opinion>>() {
                });
        
        assertNotNull(response);
        assertTrue(response.getBody().getSuccess());
    }

}
