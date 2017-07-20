package com.ohmuk.folitics.controller.milestone;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
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
import com.ohmuk.folitics.hibernate.entity.GPIPoint;
import com.ohmuk.folitics.hibernate.entity.Milestone;
import com.ohmuk.folitics.utils.TestDataPreRequisites;
import com.ohmuk.folitics.utils.TestDataUtils;
import com.ohmuk.folitics.utils.TestUtils;

/**
 * 
 * @author Mayank Sharma
 *
 */
@RunWith(BlockJUnit4ClassRunner.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestMilestoneController {
    private RestTemplate restTemplate = new TestRestTemplate();

    /**
     * All crud operation for milestone
     * @throws JsonProcessingException
     */
    @Test
    public void TestMilestoneCrud() throws JsonProcessingException {
        Map<String, Object> prerequisiteMap = TestDataPreRequisites.createMilestonePrerequisite();
        Long id = insertGpiPoint();
        testAddMilestonApi(id);
        testDeleteMilestoneApi(id);
        deleteGpiPoint(id);
        findAllMilestone();
    }

    /**
     * Delete milestone
     * 
     * @param id
     * @throws JsonProcessingException
     */
    private void testDeleteMilestoneApi(Long id) throws JsonProcessingException {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(TestUtils.BASE_URL + TestUtils.MILESTONE_CONTROLLER_APIS.DELETE_BY_GPI)
                .queryParam("gpiId", id);
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<Milestone>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<Milestone>>() {
                });

        ResponseDto<Milestone> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());

    }

    /**
     * Add milestone
     * 
     * @param id
     * @throws JsonProcessingException
     */
    private void testAddMilestonApi(Long id) throws JsonProcessingException {
        Map<String, Object> requestBody = TestDataUtils.getMilestoneRequestBody();
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(TestUtils.BASE_URL + TestUtils.MILESTONE_CONTROLLER_APIS.ADD_BY_GPI).queryParam("gpiId", id);
        HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
        HttpEntity<ResponseDto<Milestone>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<Milestone>>() {
                });

        ResponseDto<Milestone> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());

    }

    /**
     * Add gpi point
     * @return
     * @throws JsonProcessingException
     */
    public Long insertGpiPoint() throws JsonProcessingException {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(TestUtils.BASE_URL + TestUtils.GPI_POINTS_CONTROLLER_APIS.ADD);
        HttpEntity<String> httpEntity = TestUtils.getHttpEntity(TestDataPreRequisites.createMilestonePrerequisite());
        HttpEntity<ResponseDto<GPIPoint>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<GPIPoint>>() {
                });

        ResponseDto<GPIPoint> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
        return ((GPIPoint) response.getBody().getMessages().get(0)).getId();

    }

    /**
     * Delete gpi point
     * @param id
     * @throws JsonProcessingException
     */
    public void deleteGpiPoint(Long id) throws JsonProcessingException {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(TestUtils.BASE_URL + TestUtils.GPI_POINTS_CONTROLLER_APIS.HARD_DELETE_BY_ID)
                .queryParam("id", id);
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<GPIPoint>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<GPIPoint>>() {
                });

        ResponseDto<GPIPoint> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());

    }

    /**
     * Get all milestone
     * @throws JsonProcessingException
     */
    public void findAllMilestone() throws JsonProcessingException {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(TestUtils.BASE_URL + TestUtils.MILESTONE_CONTROLLER_APIS.FIND_ALL);
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<Milestone>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<Milestone>>() {
                });

        ResponseDto<Milestone> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
    }
}
