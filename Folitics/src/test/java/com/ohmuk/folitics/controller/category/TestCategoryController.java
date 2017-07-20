package com.ohmuk.folitics.controller.category;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.ohmuk.folitics.enums.CategoryType;
import com.ohmuk.folitics.hibernate.entity.Category;
import com.ohmuk.folitics.utils.TestDataUtils;
import com.ohmuk.folitics.utils.TestUtils;

@RunWith(BlockJUnit4ClassRunner.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestCategoryController {

	// Test RestTemplate to invoke the sAPIs.
	private RestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void TestCategoryCURD() throws JsonProcessingException {

		
		Map<String, List<Category>> returnMap = new HashMap<String, List<Category>>();

        returnMap = testAddCategoryApi();

        List<Category> categories = new ArrayList<Category>();
        categories = returnMap.get("category");

        List<Category> subCategories = new ArrayList<Category>();
        subCategories = returnMap.get("subcategory");

        List<Category> indicators = new ArrayList<Category>();
        indicators = returnMap.get("indicator");

        testEditCategoryApi(categories.get(0).getId(), returnMap);

        testGetAllCategoryApi();

        testSearchByNameCategoryApi("TestParentCategory");

        testDeleteCategoryApi(indicators.get(1).getId());

        testGetActiveCategoryApi();

        testDeleteCategoryApi(subCategories.get(0).getId());

        testDeleteCategoryApi(categories.get(0).getId());

        testPermanentDeleteApi(categories.get(0).getId());
    }

    public Map<String, List<Category>> testAddCategoryApi() throws JsonProcessingException {

        Map<String, List<Category>> returnMap = new HashMap<String, List<Category>>();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.CATEGORY_CONTROLLER_APIS.ADD);

        // category
        HttpEntity<String> httpEntityCategory = TestUtils.getHttpEntity(TestDataUtils.getParentCategoryRequestBody());

        HttpEntity<ResponseDto<Category>> responseCategory = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntityCategory, new ParameterizedTypeReference<ResponseDto<Category>>() {
                });

        ResponseDto<Category> apiCategoryResponse = responseCategory.getBody();

        assertNotNull(apiCategoryResponse);
        assertTrue(apiCategoryResponse.getSuccess());
        Category categoryParent1 = (Category) (responseCategory.getBody().getMessages().get(0));
        Category categoryParent2 = TestDataUtils.getCategory(categoryParent1.getId());
        assertNotNull(categoryParent2);

        // subcategory 1
        List<Category> categoryList = new ArrayList<Category>();

        categoryList.add(categoryParent1);

        HttpEntity<String> httpEntitySubCategory1 = TestUtils.getHttpEntity(TestDataUtils
                .getCategoryRequestBody(categoryList));

        HttpEntity<ResponseDto<Category>> responseSubCategory1 = restTemplate.exchange(
                builder.build().encode().toUri(), HttpMethod.POST, httpEntitySubCategory1,
                new ParameterizedTypeReference<ResponseDto<Category>>() {
                });

        ResponseDto<Category> apiSubCategory1Response = responseSubCategory1.getBody();

        assertNotNull(apiSubCategory1Response);
        assertTrue(apiSubCategory1Response.getSuccess());
        Category subCategory1 = (Category) (responseSubCategory1.getBody().getMessages().get(0));
        // categoryChild = TestDataUtils.getCategory(categoryChild.getId());
        assertNotNull(subCategory1.getParents());

        // subcategory2
        HttpEntity<String> httpEntitySubCategory2 = TestUtils.getHttpEntity(TestDataUtils
                .getCategoryRequestBody(categoryList));

        HttpEntity<ResponseDto<Category>> responseSubCategory2 = restTemplate.exchange(
                builder.build().encode().toUri(), HttpMethod.POST, httpEntitySubCategory2,
                new ParameterizedTypeReference<ResponseDto<Category>>() {
                });

        ResponseDto<Category> apiSubCategory2Response = responseSubCategory2.getBody();

        assertNotNull(apiSubCategory2Response);
        assertTrue(apiSubCategory2Response.getSuccess());
        Category subCategory2 = (Category) (responseSubCategory2.getBody().getMessages().get(0));
        // categoryChild = TestDataUtils.getCategory(categoryChild.getId());
        assertNotNull(subCategory2.getParents());

        // indicator1
        List<Category> subcategoryList = new ArrayList<Category>();
        subcategoryList.add(subCategory1);
        subcategoryList.add(subCategory2);

        HttpEntity<String> httpEntityIndicator1 = TestUtils.getHttpEntity(TestDataUtils.getCategoryRequestBody(
                subcategoryList, CategoryType.INDICATOR));

        HttpEntity<ResponseDto<Category>> responseIndicator1 = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntityIndicator1, new ParameterizedTypeReference<ResponseDto<Category>>() {
                });

        ResponseDto<Category> apiIndicator1Response = responseIndicator1.getBody();

        assertNotNull(apiIndicator1Response);
        assertTrue(apiIndicator1Response.getSuccess());
        Category indicator1 = (Category) (responseIndicator1.getBody().getMessages().get(0));
        indicator1 = TestDataUtils.getCategory(indicator1.getId());
      //  assertNotNull(indicator1.getParents());

        // indicator2
        HttpEntity<String> httpEntityIndicator2 = TestUtils.getHttpEntity(TestDataUtils.getCategoryRequestBody(
                subcategoryList, CategoryType.INDICATOR));

        HttpEntity<ResponseDto<Category>> responseIndicator2 = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntityIndicator2, new ParameterizedTypeReference<ResponseDto<Category>>() {
                });

        ResponseDto<Category> apiIndicator2Response = responseIndicator2.getBody();

        assertNotNull(apiIndicator2Response);
        assertTrue(apiIndicator2Response.getSuccess());
        Category indicator2 = (Category) (responseIndicator2.getBody().getMessages().get(0));
        indicator2 = TestDataUtils.getCategory(indicator2.getId());
      //  assertNotNull(indicator2.getParents());

        List<Category> categoryReturnList = new ArrayList<Category>();
        categoryReturnList.add(categoryParent1);

        List<Category> subCategoryList = new ArrayList<Category>();
        subCategoryList.add(subCategory1);
        subCategoryList.add(subCategory2);

        List<Category> indicatorList = new ArrayList<Category>();
        indicatorList.add(indicator1);
        indicatorList.add(indicator2);

        returnMap.put("category", categoryReturnList);
        returnMap.put("subcategory", subCategoryList);
        returnMap.put("indicator", indicatorList);
        return returnMap;
    }
	public void testEditCategoryApi(Long id, Map<String, List<Category>> prerequisiteMap)
			throws JsonProcessingException {

		List<Category> subcategoryList = new ArrayList<Category>(prerequisiteMap.get("subcategory"));

		Map<String, Object> requestBody = TestDataUtils.getParentCategoryRequestBody();

		String updatedDescription = "This is test updated category";
		requestBody.put("id", id);
		requestBody.put("description", updatedDescription);
		requestBody.put("parents", null);
		requestBody.put("childs", subcategoryList);
		requestBody.put("editedBy", 11l);

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL + TestUtils.CATEGORY_CONTROLLER_APIS.EDIT);

		HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
		HttpEntity<ResponseDto<Category>> response = restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<Category>>() {
				});

		ResponseDto<Category> apiResponse = response.getBody();

		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());
		Category category = (Category) (response.getBody().getMessages().get(0));
		assertTrue(updatedDescription.equals(category.getDescription()));
	}

	private void testGetAllCategoryApi() {

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL + TestUtils.CATEGORY_CONTROLLER_APIS.GETALL);

		HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();

		HttpEntity<ResponseDto<Category>> response = restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<Category>>() {
				});

		ResponseDto<Category> apiResponse = response.getBody();
		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());
	}

	private void testSearchByNameCategoryApi(String name) {

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL + TestUtils.CATEGORY_CONTROLLER_APIS.SEARCH).queryParam("name", name);

		HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();

		HttpEntity<ResponseDto<Category>> response = restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<Category>>() {
				});

		ResponseDto<Category> apiResponse = response.getBody();

		assertNotNull(apiResponse);
		assertTrue(apiResponse.getSuccess());
	}

	public void testDeleteCategoryApi(Long id) throws JsonProcessingException {

		Category cat = TestDataUtils.getCategory(id);

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL + TestUtils.CATEGORY_CONTROLLER_APIS.DELETE_BY_ID).queryParam("id", id);

		HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();

		HttpEntity<ResponseDto<Category>> response = restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<Category>>() {
				});

		assertNotNull(response);
		assertTrue(response.getBody().getSuccess());
	}

	public void testPermanentDeleteApi(Long id) throws JsonProcessingException {

		Category cat = TestDataUtils.getCategory(id);

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL + TestUtils.CATEGORY_CONTROLLER_APIS.PERMANENT_DELETE)
				.queryParam("id", id);

		HttpEntity<Category> entity = new HttpEntity<Category>(cat);

		HttpEntity<ResponseDto<Category>> response = restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDto<Category>>() {
				});

		assertNotNull(response);
		assertTrue(response.getBody().getSuccess());
	}

	private void testGetActiveCategoryApi() {

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUtils.BASE_URL + TestUtils.CATEGORY_CONTROLLER_APIS.GET_ACTIVE_CATEOGRY);

		HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();

		HttpEntity<ResponseDto<Category>> response = restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<Category>>() {
				});

		assertNotNull(response);
		assertTrue(response.getBody().getSuccess());
	}
}
