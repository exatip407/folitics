package com.ohmuk.folitics.controller.indicatorweighteddata;

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
import com.ohmuk.folitics.hibernate.entity.GovtSchemeData;
import com.ohmuk.folitics.hibernate.entity.IndicatorData;
import com.ohmuk.folitics.hibernate.entity.IndicatorWeightedData;
import com.ohmuk.folitics.hibernate.entity.Opinion;
import com.ohmuk.folitics.utils.TestDataPreRequisites;

import com.ohmuk.folitics.utils.TestUtils;

@RunWith(BlockJUnit4ClassRunner.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestIndicatorWeightedDataController {
	private static RestTemplate restTemplate = new TestRestTemplate();

@Test
public void TestIndicatorWeightedDataController() throws JsonProcessingException {
	IndicatorWeightedData inidcatorData = testAddIndicatorWeightedDataApi();
	Long id = inidcatorData.getId();
	Long categoryId = inidcatorData.getCategory().getId();
    
	//Long id=testAddIndicatorWeightedDataApi();
	testDeleteIndicatorWeightedDataApi(id);
	testEditIndicatorDataApi(id,categoryId);
	findAllIndicatorWeightedData();
	testPermanentDeleteOpinionApi(id);
}

public IndicatorWeightedData testAddIndicatorWeightedDataApi() throws JsonProcessingException {
  Map<String, Object> requestBody = getIndicatorWeightedDataRequestBody();
  UriComponentsBuilder builder = UriComponentsBuilder
          .fromHttpUrl(TestUtils.BASE_URL + "indicatorweighteddata/add");
  HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
  HttpEntity<ResponseDto<IndicatorWeightedData>> response = restTemplate.exchange(builder.build().encode().toUri(),
          HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<IndicatorWeightedData>>() {
          });

  ResponseDto<IndicatorWeightedData> apiResponse = response.getBody();
  assertNotNull(apiResponse);
  assertTrue(apiResponse.getSuccess());
  return (IndicatorWeightedData) response.getBody().getMessages().get(0);
	
}


private void testDeleteIndicatorWeightedDataApi(Long id) throws JsonProcessingException {
	IndicatorWeightedData indicatorweighteddata = getIndicatorWeightedData(id);
	UriComponentsBuilder builder = UriComponentsBuilder
	          .fromHttpUrl(TestUtils.BASE_URL + "indicatorweighteddata/delete").queryParam("id", id);
	  HttpEntity<IndicatorWeightedData> entity = new HttpEntity<IndicatorWeightedData>(indicatorweighteddata);
    HttpEntity<ResponseDto<IndicatorWeightedData>> response = restTemplate.exchange(builder.build().encode().toUri(),
            HttpMethod.POST,  entity, new ParameterizedTypeReference<ResponseDto<IndicatorWeightedData>>() {
            });
    assertNotNull(response);
    assertTrue(response.getBody().getSuccess());
}



private void testEditIndicatorDataApi(Long id,Long categoryID) throws JsonProcessingException {
    Map<String, Object> requestBody = getIndicatorWeightedDataResponseBody(categoryID);
    String updatedDescription = "New";
    requestBody.put("id", id);
    requestBody.put("weightage", 100);
	UriComponentsBuilder builder = UriComponentsBuilder
	          .fromHttpUrl(TestUtils.BASE_URL + "indicatorweighteddata/edit");
    HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
    HttpEntity<ResponseDto<IndicatorWeightedData>> response = restTemplate.exchange(builder.build().encode().toUri(),
            HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<IndicatorWeightedData>>() {
            });
    ResponseDto<IndicatorWeightedData> apiResponse = response.getBody();
    assertNotNull(apiResponse);
    assertTrue(apiResponse.getSuccess());
    IndicatorWeightedData indicatorweighteddata = (IndicatorWeightedData) (response.getBody().getMessages().get(0));
    assertTrue(100.00 == (indicatorweighteddata.getWeightage()));
}

public void findAllIndicatorWeightedData() throws JsonProcessingException {
    UriComponentsBuilder builder = UriComponentsBuilder
            .fromHttpUrl(TestUtils.BASE_URL + TestUtils.INDICATOR_WEIGHTED_DATA_CONTROLLER_APIS.GET_ALL);
    HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
    HttpEntity<ResponseDto<IndicatorWeightedData>> response = restTemplate.exchange(builder.build().encode().toUri(),
            HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<IndicatorWeightedData>>() {
            });

    ResponseDto<IndicatorWeightedData> apiResponse = response.getBody();
    assertNotNull(apiResponse);
    assertTrue(apiResponse.getSuccess());
}
private void testPermanentDeleteOpinionApi(Long id) {
    
    IndicatorWeightedData  indicatorWeightedData = getEntity(id);
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
            TestUtils.BASE_URL + TestUtils.INDICATOR_WEIGHTED_DATA_CONTROLLER_APIS.PERMANENT_DELETE).queryParam("id", id);
    HttpEntity<IndicatorWeightedData> entity = new HttpEntity<IndicatorWeightedData>(indicatorWeightedData);
    HttpEntity<ResponseDto<IndicatorWeightedData>> response = restTemplate.exchange(builder.build().encode().toUri(),
            HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDto<IndicatorWeightedData>>() {
            });
    assertNotNull(response);
    assertTrue(response.getBody().getSuccess());
}


public IndicatorWeightedData getEntity(Long id)
{
	UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
			TestUtils.BASE_URL + TestUtils.INDICATOR_WEIGHTED_DATA_CONTROLLER_APIS.FIND)
			.queryParam("id", id);
	HttpEntity<HttpHeaders> httpEntity = TestUtils
			.getHttpEntityHttpHeaders();
	HttpEntity<ResponseDto<IndicatorWeightedData>> response = restTemplate.exchange(
			builder.build().encode().toUri(), HttpMethod.GET, httpEntity,
			new ParameterizedTypeReference<ResponseDto<IndicatorWeightedData>>() {
			});
	return (IndicatorWeightedData) (response.getBody().getMessages().get(0));
}
public static Map<String, Object> getIndicatorWeightedDataRequestBody() throws JsonProcessingException {
    Map<String, Object> requestBody = new HashMap<String, Object>();
    // Building the Request body data
    Category category = null;
    try {
        category = TestDataPreRequisites.insertIndicator();
    } catch (JsonProcessingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    requestBody.put("createTime", 1454043467928l);
    requestBody.put("deleted", 1);
    requestBody.put("editTime", 1454043467928l);
    requestBody.put("effectivefromdate", 1454043467928l);
    requestBody.put("category", category);
    requestBody.put("state", "true");
    requestBody.put("weightage", 3);
    requestBody.put("impactOnChart", 1);
    requestBody.put("updateddate", 1454043467928l);
    return requestBody;

}

public static IndicatorWeightedData getIndicatorWeightedData(Long id) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
            TestUtils.BASE_URL + TestUtils.INDICATOR_WEIGHTED_DATA_CONTROLLER_APIS.FIND).queryParam("id", id);
    HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
    HttpEntity<ResponseDto<IndicatorWeightedData>> response = restTemplate.exchange(builder.build().encode()
            .toUri(), HttpMethod.GET, httpEntity,
            new ParameterizedTypeReference<ResponseDto<IndicatorWeightedData>>() {
            });
    return (IndicatorWeightedData) (response.getBody().getMessages().get(0));
}

public static Map<String, Object> getIndicatorWeightedDataResponseBody(Long categoryID) {
    // Building the Request body data
    Map<String, Object> requestBody = new HashMap<String, Object>();
    Category category = new Category();
    category.setId(categoryID);
    // requestBody.put("id",1);
    requestBody.put("createTime", 1454043467928l);
    requestBody.put("deleted", 1);
    requestBody.put("editTime", 1454043467928l);
    requestBody.put("effectivefromdate", 1454043467928l);
    requestBody.put("category", category);
    requestBody.put("state", "true");
    requestBody.put("weightage", 3);
    requestBody.put("impactOnChart", 1);
    requestBody.put("updateddate", 1454043467928l);
    return requestBody;
}

}
