package com.ohmuk.folitics.controller.verdict;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
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
import com.ohmuk.folitics.hibernate.entity.Opinion;
import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.utils.TestDataPreRequisites;
import com.ohmuk.folitics.utils.TestDataUtils;
import com.ohmuk.folitics.utils.TestUtils;

@RunWith(BlockJUnit4ClassRunner.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestVerdictController {

    private RestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void TestCURD() throws IOException {

        Map<String, Object> prerequisiteMap = TestDataPreRequisites.createVerdictPrerequisite();

        Long id = testCreateVerdictApi(prerequisiteMap);

        // testEditVerdictApi(id, prerequisiteMap);

        testDeleteVerdictApi(id);

        TestDataPreRequisites.deleteVerdictPrerequisite(prerequisiteMap);
    }

    private Long testCreateVerdictApi(Map<String, Object> prerequisiteMap) throws JsonProcessingException {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.VERDICT_CONTROLLER_APIS.ADD);

        HttpEntity<String> httpEntityCategory = TestUtils.getHttpEntity(TestDataUtils
                .getVerdictRequestBody(prerequisiteMap));

        HttpEntity<ResponseDto<Verdict>> responseVerdict = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntityCategory, new ParameterizedTypeReference<ResponseDto<Verdict>>() {
                });

        ResponseDto<Verdict> apiVerdictResponse = responseVerdict.getBody();

        assertNotNull(apiVerdictResponse);
        assertTrue(apiVerdictResponse.getSuccess());
        Verdict verdict = (Verdict) (responseVerdict.getBody().getMessages().get(0));

        assertNotNull(verdict);

        return verdict.getId();
    }

    private void testEditVerdictApi(Long id, Map<String, Object> prerequisiteMap) throws JsonProcessingException {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.VERDICT_CONTROLLER_APIS.EDIT);

        HttpEntity<String> httpEntityCategory = TestUtils.getHttpEntity(TestDataUtils
                .getVerdictRequestBody(prerequisiteMap));

        HttpEntity<ResponseDto<Verdict>> responseVerdict = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntityCategory, new ParameterizedTypeReference<ResponseDto<Verdict>>() {
                });

        ResponseDto<Verdict> apiVerdictResponse = responseVerdict.getBody();

        assertNotNull(apiVerdictResponse);
        assertTrue(apiVerdictResponse.getSuccess());
        Verdict verdict = (Verdict) (responseVerdict.getBody().getMessages().get(0));

        assertNotNull(verdict);

    }

    private void testDeleteVerdictApi(Long id) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.VERDICT_CONTROLLER_APIS.DELETE_BY_ID).queryParam("id", id);

        /*HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();

        HttpEntity<ResponseDto<Verdict>> responseVerdict = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<Verdict>>() {
                });*/
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();

        HttpEntity<ResponseDto<Verdict>> responseVerdict = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<Verdict>>() {
                });

        ResponseDto<Verdict> apiVerdictResponse = responseVerdict.getBody();

        assertNotNull(apiVerdictResponse);
        assertTrue(apiVerdictResponse.getSuccess());
    }
}
