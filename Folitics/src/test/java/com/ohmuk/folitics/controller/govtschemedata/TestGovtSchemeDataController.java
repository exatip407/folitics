package com.ohmuk.folitics.controller.govtschemedata;

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
import com.ohmuk.folitics.hibernate.entity.GovtSchemeData;
import com.ohmuk.folitics.utils.TestDataUtils;
import com.ohmuk.folitics.utils.TestUtils;

@RunWith(BlockJUnit4ClassRunner.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestGovtSchemeDataController {
    private RestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void TestGovtSchemeDataController() throws JsonProcessingException {

         Long id=testAddGovtSchemeDataApi();
         findAllGovernmentScheme();
         testEditGovtSchemeDataApi(id);
         testDeleteGovtSchemeDataApi(id);
         testGovernmentDeleteApi(id);
    }

    public Long testAddGovtSchemeDataApi() throws JsonProcessingException {
        Map<String, Object> requestBody = TestDataUtils.getGovtSchemeDataRequestBody();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL + "govtschemedata/add");
        HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
        HttpEntity<ResponseDto<GovtSchemeData>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<GovtSchemeData>>() {
                });

        ResponseDto<GovtSchemeData> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
        return ((GovtSchemeData) response.getBody().getMessages().get(0)).getId();

    }

    private void testDeleteGovtSchemeDataApi(Long id) throws JsonProcessingException {
        GovtSchemeData govtschemedata = TestDataUtils.getGovtSchemeData(id);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + "govtschemedata/deletebyid").queryParam("id", id);
        HttpEntity<GovtSchemeData> entity = new HttpEntity<GovtSchemeData>(govtschemedata);
        HttpEntity<ResponseDto<GovtSchemeData>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDto<GovtSchemeData>>() {
                });
        assertNotNull(response);
        assertTrue(response.getBody().getSuccess());
    }

    private void testEditGovtSchemeDataApi(Long id) throws JsonProcessingException {
        Map<String, Object> requestBody = TestDataUtils.getGovtSchemeDataResponseBody();
        requestBody.put("id", id);
        requestBody.put("state", "updated");
        requestBody.put("isactive", 10);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL + "govtschemedata/edit");
        HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
        HttpEntity<ResponseDto<GovtSchemeData>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<GovtSchemeData>>() {
                });
        ResponseDto<GovtSchemeData> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
        GovtSchemeData govtschemedata = (GovtSchemeData) (response.getBody().getMessages().get(0));
        assertTrue(10 == (govtschemedata.getIsactive()));
    }

    public void findAllGovernmentScheme() throws JsonProcessingException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.GOVT_SCHEME_DATA_CONTROLLER_APIS.GET_ALL);
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<GovtSchemeData>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<GovtSchemeData>>() {
                });

        ResponseDto<GovtSchemeData> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
    }

    private void testGovernmentDeleteApi(Long id) {
        GovtSchemeData govtSchemeData = getGovernmentScheme(id);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.GOVT_SCHEME_DATA_CONTROLLER_APIS.PERMANENT_DELETE).queryParam("id", id);
        HttpEntity<GovtSchemeData> entity = new HttpEntity<GovtSchemeData>(govtSchemeData);
        HttpEntity<ResponseDto<GovtSchemeData>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDto<GovtSchemeData>>() {
                });
        assertNotNull(response);
        assertTrue(response.getBody().getSuccess());
    }

    public GovtSchemeData getGovernmentScheme(Long id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.GOVT_SCHEME_DATA_CONTROLLER_APIS.FIND).queryParam("id", id);
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<GovtSchemeData>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<GovtSchemeData>>() {
                });
        return (GovtSchemeData) (response.getBody().getMessages().get(0));
    }
}
