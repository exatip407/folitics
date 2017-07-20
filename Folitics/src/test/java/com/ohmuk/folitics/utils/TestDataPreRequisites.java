package com.ohmuk.folitics.utils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.test.TestRestTemplate;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.enums.ComponentType;
import com.ohmuk.folitics.hibernate.entity.Category;
import com.ohmuk.folitics.hibernate.entity.Component;
import com.ohmuk.folitics.hibernate.entity.Opinion;
import com.ohmuk.folitics.hibernate.entity.Sentiment;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.poll.Poll;
import com.ohmuk.folitics.hibernate.entity.task.Department;
import com.ohmuk.folitics.hibernate.entity.task.PeopleMet;
import com.ohmuk.folitics.util.DateUtils;

public class TestDataPreRequisites {

    public interface ComponentPrerequisiteMapKeys {
        String CATEGORIES = "categories";
        String SENTIMENT = "sentiment";
        String COMPONENT = "component";
        String USER = "user";
        String OPNION = "opinion";
        String POLL = "poll";
        String ATTACHMENT = "attachment";
    }

    private static RestTemplate restTemplate = new TestRestTemplate();

    public static Map<String, Object> createSentimentPrerequisites() throws JsonProcessingException {
        Set<Poll> polls = insertPoll();
        Map<String, Set<Category>> mapOfCategories = insertCategory();
        Map<String, Object> prerequisiteMap = new HashMap<String, Object>();
        prerequisiteMap.put(ComponentPrerequisiteMapKeys.CATEGORIES, mapOfCategories.get("category"));
        prerequisiteMap.put(ComponentPrerequisiteMapKeys.POLL, polls);
        return prerequisiteMap;
    }

    public static void deleteSentimentPrerequisite(Map<String, Object> prerequisiteMap) {
        deleteCategory((Set<Category>) prerequisiteMap.get(ComponentPrerequisiteMapKeys.CATEGORIES));
        removePoll((Set<Poll>) prerequisiteMap.get(ComponentPrerequisiteMapKeys.POLL));
    }

    public static Map<String, Object> createOpinionPrerequisite() throws IOException {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> sentimentPrerequisiteMap = new HashMap<String, Object>();
        sentimentPrerequisiteMap = TestDataPreRequisites.createSentimentPrerequisites();
        Sentiment sentiment = insertSentiment(sentimentPrerequisiteMap);
        User user = insertUser();
        returnMap.put(ComponentPrerequisiteMapKeys.CATEGORIES,
                sentimentPrerequisiteMap.get(ComponentPrerequisiteMapKeys.CATEGORIES));
        returnMap.put(ComponentPrerequisiteMapKeys.SENTIMENT, sentiment);
        returnMap.put(ComponentPrerequisiteMapKeys.USER, user);
        return returnMap;
    }

    public static Map<String, Object> createOpinionLikePrerequisite() throws IOException {
        Map<String, Object> returnMap = createOpinionPrerequisite();
        Opinion opnion = insertOpinion(returnMap);
        returnMap.put(ComponentPrerequisiteMapKeys.OPNION, opnion);
        return returnMap;
    }

    public static Map<String, Object> createMilestonePrerequisite() {
        Map<String, Object> prerequisiteMap = new HashMap<String, Object>();
        prerequisiteMap.put("createTime", DateUtils.getSqlTimeStamp());
        prerequisiteMap.put("editTime", DateUtils.getSqlTimeStamp());
        prerequisiteMap.put("startTime", DateUtils.getSqlTimeStamp());
        prerequisiteMap.put("endTime", DateUtils.getSqlTimeStamp());
        prerequisiteMap.put("totalPoints", 30);
        prerequisiteMap.put("opinionResponseAggregationPoints", 10);
        prerequisiteMap.put("indicatorChangePoints", 10);
        prerequisiteMap.put("eventReportPoints", 10);
        return prerequisiteMap;

    }

    public static void deleteOpinionPrerequisite(Map<String, Object> prerequisiteMap) {
        deleteSentiment((Sentiment) prerequisiteMap.get(ComponentPrerequisiteMapKeys.SENTIMENT));
        deleteCategory((Set<Category>) prerequisiteMap.get(ComponentPrerequisiteMapKeys.CATEGORIES));
        deleteUser((User) prerequisiteMap.get(ComponentPrerequisiteMapKeys.USER));
    }

    public static Map<String, Object> createResponsePrerequisite() throws IOException {
        Map<String, Object> prerequisiteMap = new HashMap<String, Object>();
        Map<String, Set<Category>> categories = insertCategory();
        prerequisiteMap = TestDataPreRequisites.createSentimentPrerequisites();
        Sentiment sentiment = insertSentiment(prerequisiteMap);
        User user = insertUser();
        prerequisiteMap.put("sentiment", sentiment);
    //    prerequisiteMap.put("categories", categories.get("subcategory"));
        prerequisiteMap.put("user", user);
        Opinion opinion = insertOpinion(prerequisiteMap);
        prerequisiteMap.put("opinion", opinion);
        
        return prerequisiteMap;
    }

    public static void deleteResponsePrerequisite(Map<String, Object> prerequisiteMap) {
        removeOpnion((Opinion) prerequisiteMap.get(ComponentPrerequisiteMapKeys.OPNION));
        deleteOpinionPrerequisite(prerequisiteMap);
    }

    public static void deleteOpinionLikePrerequisite(Map<String, Object> prerequisiteMap) {
        removeOpnion((Opinion) prerequisiteMap.get(ComponentPrerequisiteMapKeys.OPNION));
        deleteOpinionPrerequisite(prerequisiteMap);
    }

    public static Map<String, Set<Category>> insertCategory() throws JsonProcessingException {
        Map<String, Set<Category>> returnMap = new HashMap<String, Set<Category>>();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.CATEGORY_CONTROLLER_APIS.ADD);
        // category
        HttpEntity<String> httpEntityCategory = TestUtils.getHttpEntity(TestDataUtils.getParentCategoryRequestBody());
        HttpEntity<ResponseDto<Category>> responseCategory = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntityCategory, new ParameterizedTypeReference<ResponseDto<Category>>() {
                });
        ResponseDto<Category> apiCategoryResponse = responseCategory.getBody();
        Category categoryParent1 = (Category) (responseCategory.getBody().getMessages().get(0));
        Category categoryParent2 = TestDataUtils.getCategory(categoryParent1.getId());

        // subcategory 1

        Set<Category> categoryReturnList = new HashSet<Category>();
        categoryReturnList.add(categoryParent1);
        returnMap.put("category", categoryReturnList);
        return returnMap;
    }

    public static Category insertIndicator() throws JsonProcessingException {
        Map<String, Set<Category>> returnMap = new HashMap<String, Set<Category>>();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.CATEGORY_CONTROLLER_APIS.ADD);
        // category
        HttpEntity<String> httpEntityCategory = TestUtils.getHttpEntity(TestDataUtils.getParentCategoryRequestBody());
        HttpEntity<ResponseDto<Category>> responseCategory = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntityCategory, new ParameterizedTypeReference<ResponseDto<Category>>() {
                });
        ResponseDto<Category> apiCategoryResponse = responseCategory.getBody();
        Category categoryParent1 = (Category) (responseCategory.getBody().getMessages().get(0));
        // Category categoryParent2 = TestDataUtils.getCategory(categoryParent1.getId());
        return categoryParent1;
    }

    public static void deleteCategory(Set<Category> prerequisiteSet) {
        for (Category category : prerequisiteSet) {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                    TestUtils.BASE_URL + TestUtils.CATEGORY_CONTROLLER_APIS.PERMANENT_DELETE).queryParam("id",
                    category.getId());
            HttpEntity<Category> entity = new HttpEntity<Category>(category);
            HttpEntity<ResponseDto<Category>> response = restTemplate.exchange(builder.build().encode().toUri(),
                    HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDto<Category>>() {
                    });
            response.getBody().getSuccess();
        }
    }

    public static Set<Poll> insertPoll() throws JsonProcessingException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.POLL_CONTROLLER_APIS.ADD);
        HttpEntity<String> httpEntityPoll = TestUtils.getHttpEntity(TestDataUtils.getPollRequestBody());
        HttpEntity<ResponseDto<Poll>> responsePoll = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntityPoll, new ParameterizedTypeReference<ResponseDto<Poll>>() {
                });
        ResponseDto<Poll> apiPollResponse = responsePoll.getBody();
        assertNotNull(apiPollResponse);
        assertTrue(apiPollResponse.getSuccess());
        Poll poll1 = (Poll) (responsePoll.getBody().getMessages().get(0));
        //Poll poll2 = TestDataUtils.getPoll(poll1.getId());
        Set<Poll> polls = new HashSet<Poll>();
        polls.add(poll1);
        return polls;
    }

    private static void removePoll(Set<Poll> set) {
        for (Poll poll : set) {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                    TestUtils.BASE_URL + TestUtils.POLL_CONTROLLER_APIS.PERMANENT_DELETE)
                    .queryParam("id", poll.getId());
            HttpEntity<Poll> entity = new HttpEntity<Poll>(poll);
            HttpEntity<ResponseDto<Poll>> response = restTemplate.exchange(builder.build().encode().toUri(),
                    HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDto<Poll>>() {
                    });
            assertNotNull(response);
            assertTrue(response.getBody().getSuccess());
        }
    }

    public static Sentiment insertSentiment(Map<String, Object> prerequisiteMap) throws IOException {

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
        Sentiment sentiment = (Sentiment) (response.getBody().getMessages().get(0));
        return sentiment;
    }

    public static void deleteSentiment(Sentiment sentiment) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.SENTIMENT_CONTROLLER_APIS.PERMANENT_DELETE).queryParam("id",
                sentiment.getId());
        HttpEntity<?> entity = new HttpEntity<>(sentiment);
        HttpEntity<ResponseDto<Sentiment>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDto<Sentiment>>() {
                });
        response.getBody().getSuccess();
    }

    public static User insertUser() throws IOException {
        HttpEntity<String> httpEntity = TestUtils.getHttpEntity(TestDataUtils.getUserRequestBody());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.USER_CONTROLLER_APIS.ADD);
        HttpEntity<ResponseDto<User>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<User>>() {
                });
        User user = (User) (response.getBody().getMessages().get(0));
        return user;
    }

    public static Opinion insertOpinion(Map<String, Object> prerequisiteMap) throws IOException, JsonProcessingException {
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
//        HttpEntity<String> httpEntity = TestUtils.getHttpEntity(TestDataUtils.getOpinionRequestBody(prerequisiteMap));
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.OPINION_CONTROLLER_APIS.ADD);
        HttpEntity<ResponseDto<Opinion>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<Opinion>>() {
                });
        Opinion opinion = (Opinion) (response.getBody().getMessages().get(0));
        return opinion;
    }

    public static void deleteUser(User user) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.USER_CONTROLLER_APIS.PERMANENT_DELETE).queryParam("id", user.getId());
        HttpEntity<?> entity = new HttpEntity<>(user);
        HttpEntity<ResponseDto<User>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDto<User>>() {
                });
    }

    public static Component getComponent(Sentiment sentiment) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(TestUtils.BASE_URL + TestUtils.COMPONENT_CONTROLLER_APIS.GETFROMITEM)
                .queryParam("id", sentiment.getId()).queryParam("componentType", ComponentType.SENTIMENT.getValue());
        HttpEntity<?> entity = new HttpEntity<>(sentiment);
        HttpEntity<ResponseDto<Component>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDto<Component>>() {
                });
        Component component = (Component) response.getBody().getMessages().get(0);
        return component;
    }

    public static void removeComponent(Component component) {
        // get component
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.COMPONENT_CONTROLLER_APIS.FIND).queryParam("id", component.getId());
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<Component>> componentresponse = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<Component>>() {
                });
        Component comp = (Component) (componentresponse.getBody().getMessages().get(0));
    }

    public static void removeOpnion(Opinion opinion) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.OPINION_CONTROLLER_APIS.PERMANENT_DELETE).queryParam("id",
                opinion.getId());
        HttpEntity<Opinion> entity = new HttpEntity<Opinion>(opinion);
        HttpEntity<ResponseDto<Opinion>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDto<Opinion>>() {
                });
    }

    public static void deleteOpinion(Opinion opinion) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.OPINION_CONTROLLER_APIS.DELETE_BY_ID).queryParam("id", opinion.getId());
        HttpEntity<?> entity = new HttpEntity<>(TestUtils.getHttpHeadersApplicationJson());
        HttpEntity<ResponseDto<Opinion>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDto<Opinion>>() {
                });
    }

    public static Map<String, Object> createPollPrerequisite() throws IOException {
        Map<String, Object> prerequisitemap = new HashMap<String, Object>();
        prerequisitemap.put(ComponentPrerequisiteMapKeys.USER, insertUser());
        return prerequisitemap;
    }

    public static void deletePollPrerequisite(Map<String, Object> prerequisitemap) {

        deleteUser((User) prerequisitemap.get(ComponentPrerequisiteMapKeys.USER));
    }

    public static Map<String, Object> createLikePrerequisite() throws IOException {

        return createOpinionPrerequisite();
    }

    public static void deleteLikePrerequisite(Map<String, Object> prerequisiteMap) {

        deleteOpinionPrerequisite(prerequisiteMap);
    }

    public static Map<String, Object> createSharePrerequisite() throws IOException {

        return createOpinionPrerequisite();
    }

    public static void deleteSharePrerequisite(Map<String, Object> prerequisiteMap) {

        deleteOpinionPrerequisite(prerequisiteMap);
    }

    public static Map<String, Object> createVerdictPrerequisite() throws IOException {

        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> sentimentPrerequisiteMap = new HashMap<String, Object>();
        sentimentPrerequisiteMap = TestDataPreRequisites.createSentimentPrerequisites();
       Sentiment sentiment = insertSentiment(sentimentPrerequisiteMap);
        returnMap.put(ComponentPrerequisiteMapKeys.CATEGORIES,
                sentimentPrerequisiteMap.get(ComponentPrerequisiteMapKeys.CATEGORIES));
        returnMap.put(ComponentPrerequisiteMapKeys.SENTIMENT, sentiment);
        return returnMap;
    }

    @SuppressWarnings("unchecked")
    public static void deleteVerdictPrerequisite(Map<String, Object> prerequisiteMap) {

        deleteSentiment((Sentiment) prerequisiteMap.get(ComponentPrerequisiteMapKeys.SENTIMENT));
        deleteCategory((Set<Category>) prerequisiteMap.get(ComponentPrerequisiteMapKeys.CATEGORIES));
    }

    public static Map<String, Object> createTaskPrerequisites() throws IOException {
        User user = insertUser();
        user.setId(1l);
        PeopleMet peopleMet = new PeopleMet();
        List<User> userList = new ArrayList<User>();
        userList.add(user);
        peopleMet.setName("Mr XYZ");
        peopleMet.setActionTaken("None");
        peopleMet.setLocation("Example Location");
        peopleMet.setDepartmentName("Department Name");
        List<PeopleMet> peopleMetList = new ArrayList<PeopleMet>();
        peopleMetList.add(peopleMet);

        Department department = new Department();
        department.setId(1L);
        List<Department> departmentList = new ArrayList<Department>();
        departmentList.add(department);
        Map<String, Object> prerequisiteMap = new HashMap<String, Object>();

        prerequisiteMap.put("subject", "Test Subject");
        prerequisiteMap.put("description", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        prerequisiteMap.put("createdBy", user);
        prerequisiteMap.put("participants", userList);
        prerequisiteMap.put("isLeader", false);
        prerequisiteMap.put("isNGO", true);
        prerequisiteMap.put("isDept", false);
        prerequisiteMap.put("email", "testfolitic@ohmuk.com");
        prerequisiteMap.put("peopleMet", peopleMetList);
        prerequisiteMap.put("city", "ZBSSD");
        prerequisiteMap.put("address", "XXXXXXXXXXXXXXXXX_YYYYYYYYYYY_JDKJKDJKDJKDJKD");
        prerequisiteMap.put("departments", departmentList);

        return prerequisiteMap;
    }

}
