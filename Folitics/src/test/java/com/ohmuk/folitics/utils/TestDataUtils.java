package com.ohmuk.folitics.utils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.test.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.enums.CategoryType;
import com.ohmuk.folitics.enums.ComponentType;
import com.ohmuk.folitics.enums.FileType;
import com.ohmuk.folitics.enums.GenderType;
import com.ohmuk.folitics.enums.MilestoneType;
import com.ohmuk.folitics.enums.OpinionState;
import com.ohmuk.folitics.enums.OpinionType;
import com.ohmuk.folitics.enums.QualificationType;
import com.ohmuk.folitics.enums.ResponseType;
import com.ohmuk.folitics.enums.SentimentState;
import com.ohmuk.folitics.enums.SentimentType;
import com.ohmuk.folitics.hibernate.entity.Category;
import com.ohmuk.folitics.hibernate.entity.Component;
import com.ohmuk.folitics.hibernate.entity.GovtSchemeData;
import com.ohmuk.folitics.hibernate.entity.IndicatorData;
import com.ohmuk.folitics.hibernate.entity.IndicatorThreshold;
import com.ohmuk.folitics.hibernate.entity.IndicatorWeightedData;
import com.ohmuk.folitics.hibernate.entity.Opinion;
import com.ohmuk.folitics.hibernate.entity.Response;
import com.ohmuk.folitics.hibernate.entity.Sentiment;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.UserEducation;
import com.ohmuk.folitics.hibernate.entity.UserPrivacyData;
import com.ohmuk.folitics.hibernate.entity.UserProfile;
import com.ohmuk.folitics.hibernate.entity.UserRole;
import com.ohmuk.folitics.hibernate.entity.attachment.Attachment;
import com.ohmuk.folitics.hibernate.entity.poll.Poll;
import com.ohmuk.folitics.hibernate.entity.poll.PollOption;
import com.ohmuk.folitics.hibernate.entity.share.SentimentShare;
import com.ohmuk.folitics.hibernate.entity.task.Task;
import com.ohmuk.folitics.hibernate.entity.verdict.Verdict;
import com.ohmuk.folitics.utils.TestDataPreRequisites.ComponentPrerequisiteMapKeys;

/**
 * @author Abhishek
 *
 */
public class TestDataUtils {

    private static RestTemplate restTemplate = new TestRestTemplate();

    public static Map<String, Object> getParentCategoryRequestBody() {
        // Building the Request body data
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("name", "TestParentCategory ");
        requestBody.put("type", CategoryType.CATEGORY.getValue());
        requestBody.put("description", "This is test Parent category");
        requestBody.put("createdBy", 10l);
        return requestBody;
    }

    public static Map<String, Object> getCategoryRequestBody(List<Category> parentList) {
        return getCategoryRequestBody(parentList, CategoryType.SUBCATEGORY);
    }

    public static Map<String, Object> getCategoryRequestBody(List<Category> parentList, CategoryType type) {
        // Building the Request body data
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("name", "TestChildCategory");
        requestBody.put("type", type.getValue());
        requestBody.put("description", "This is test child category");
        requestBody.put("parents", parentList);
        requestBody.put("createdBy", 10l);
        return requestBody;
    }

    public static Category getCategory(Long id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.CATEGORY_CONTROLLER_APIS.FIND).queryParam("id", id);
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<Category>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<Category>>() {
                });
        return (Category) (response.getBody().getMessages().get(0));
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getSentimentRequestBody(Map<String, Object> prerequisiteMap)
            throws JsonProcessingException {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap = getSentimentRequestBody();
        returnMap.put("categories", (Set<Category>) prerequisiteMap.get(ComponentPrerequisiteMapKeys.CATEGORIES));
        returnMap.put("polls", (Set<Poll>) prerequisiteMap.get(ComponentPrerequisiteMapKeys.POLL));
        return returnMap;
    }

    public static Map<String, Object> getSentimentRequestBody() throws JsonProcessingException {
        // Building the Request body data
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("createdBy", 1);
        //requestBody.put("data", new String("test").getBytes());
        requestBody.put("description", "Description for test Sentiment");
        requestBody.put("endTime", System.currentTimeMillis() + 86400000 + 86400000);
        requestBody.put("imageType", FileType.PNG.getValue());
        //requestBody.put("milestoneType", MilestoneType.MEGA.getValue());
        requestBody.put("startTime", System.currentTimeMillis() + 86400000);
        requestBody.put("state", SentimentState.ALIVE.getValue());
        requestBody.put("subject", "Subject for test Sentiment");
        //requestBody.put("thumbnail", new String("test").getBytes());
        //requestBody.put("title", "title");
        requestBody.put("type", SentimentType.TEMPORARY.getValue());
        requestBody.put("componentType", ComponentType.SENTIMENT.getValue());

        return requestBody;
    }

    public static Sentiment getSentiment(Long id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.SENTIMENT_CONTROLLER_APIS.FIND).queryParam("id", id);
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<Sentiment>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<Sentiment>>() {
                });
        return (Sentiment) (response.getBody().getMessages().get(0));
    }

    public static Task getTask(Long id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.TASK_CONTROLLER_APIS.FIND).queryParam("id", id);
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<Task>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<Task>>() {
                });
        return (Task) (response.getBody().getMessages().get(0));

    }

    public static Map<String, Object> getOpinionRequestBody(Map<String, Object> prerequisiteMap) {

        @SuppressWarnings("unchecked")
        Set<Category> setOfCategories = (Set<Category>) prerequisiteMap
                .get(TestDataPreRequisites.ComponentPrerequisiteMapKeys.CATEGORIES);

        Attachment attachment = new Attachment();
        attachment.setDescription("This is attachment description");

        Sentiment sentiment = (Sentiment) prerequisiteMap
                .get(TestDataPreRequisites.ComponentPrerequisiteMapKeys.SENTIMENT);

        Component component = new Component();
        component.setId(sentiment.getId());
        component.setComponentType(ComponentType.SENTIMENT.getValue());

        // Building the Request body data
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("subject", "Subject of Opinion");
        requestBody.put("text", "Text of the opinion");
        requestBody.put("type", OpinionType.PROGOVT.getValue());
        requestBody.put("category", setOfCategories.iterator().next());
        requestBody.put(TestDataPreRequisites.ComponentPrerequisiteMapKeys.COMPONENT, component);
        requestBody.put(TestDataPreRequisites.ComponentPrerequisiteMapKeys.SENTIMENT, sentiment);
        requestBody.put(TestDataPreRequisites.ComponentPrerequisiteMapKeys.USER,
                prerequisiteMap.get(TestDataPreRequisites.ComponentPrerequisiteMapKeys.USER));
        requestBody.put(TestDataPreRequisites.ComponentPrerequisiteMapKeys.ATTACHMENT, attachment);
        requestBody.put("componentType", ComponentType.OPINION.getValue());
        requestBody.put("reponses", null);
        // requestBody.put("links", setOfLink);

        return requestBody;
    }

    public static Map<String, Object> getOpinionRequestBody() {
        // Building the Request body data
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("state", OpinionState.NEW.getValue());
        requestBody.put("subject", "Subject of Opinion");
        requestBody.put("text", "Text of the opinion");
        requestBody.put("type", OpinionType.PROGOVT.getValue());
        requestBody.put("reponses", null);
        requestBody.put("componentType", ComponentType.OPINION.getValue());
        return requestBody;
    }

    public static Opinion getOpinion(Long id) throws JsonParseException, JsonMappingException, IOException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.OPINION_CONTROLLER_APIS.FIND).queryParam("id", id);
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<Opinion>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<Opinion>>() {
                });
        Opinion opinion = response.getBody().getMessages().get(0);
        
        /*ObjectMapper mapper = new ObjectMapper();

        Opinion op = mapper
                .readValue(mapper.writeValueAsString(response.getBody().getMessages().get(0)), Opinion.class);
*/
        // return (Opinion) (response.getBody().getMessages().get(0));

        return opinion;
    }

    public static Map<String, Object> getOpinionLikeRequestBody(Map<String, Object> prerequisiteMap, Boolean like) {
        // Building the Request body data
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("compomentType", ComponentType.OPINION.getValue());
        requestBody.put("userid",
                ((User) prerequisiteMap.get(TestDataPreRequisites.ComponentPrerequisiteMapKeys.USER)).getId());
        requestBody.put("opinionId",
                ((Opinion) prerequisiteMap.get(TestDataPreRequisites.ComponentPrerequisiteMapKeys.OPNION)).getId());
        return requestBody;
    }

    public static Map<String, Object> getUserRequestBody() throws IOException {
        return getUser("username1");
    }

    public static Map<String, Object> getUserConnectionRequestBody() throws IOException {
        return getUser("userConnection");
    }

    public static Map<String, Object> getUser(String username) throws IOException {

        UserRole userRole = getUserRole();

        Map<String, Object> requestBody = new HashMap<String, Object>();
        long randomNo = (long) (Math.random()*1000000);
        requestBody.put("username", username+randomNo);
        requestBody.put("password", "12345");
        requestBody.put("emailId", "testfoliticsuser@gmail.com");
        requestBody.put("status", "VerifiedByEmail");
        requestBody.put("name", "name");
        requestBody.put("gender", GenderType.MALE.getValue());
        requestBody.put("userAssociation", null);
        requestBody.put("userEmailNotificationSettings", null);
        requestBody.put("userUINotification", null);
        requestBody.put("role", userRole);
        requestBody.put("dob", System.currentTimeMillis() - 886400000);
        requestBody.put("maritalStatus", "Married");
        requestBody.put("points", 400);
        requestBody.put("religion", "Hinduism");
        requestBody.put("state", "Madhya Pradesh");
       // requestBody.put("userEducation", getUserEducation());
        
        return requestBody;
    }

    private static UserProfile getUserPrivacyData() {
        UserProfile userProfile = new UserProfile();
        //userProfile.setDOB(new Timestamp(System.currentTimeMillis() + 86400000));
        return null;
    }

    public static UserRole getUserRole() {
        UserRole userRole = new UserRole();
        userRole.setId(1l);
        userRole.setUserRole("USER");
        return userRole;
    }

    public static Set<UserEducation> getUserEducation() {
        Set<UserEducation> userEducations = new HashSet<UserEducation>();
        UserEducation userEducation = new UserEducation();
        userEducation.setCourseName(QualificationType.GRADUATE.getValue());
        userEducation.setStartDate(new Timestamp(System.currentTimeMillis()));
        userEducation.setEndDate(new Timestamp(System.currentTimeMillis()));
        userEducation.setInstituteName("IIT");
        userEducations.add(userEducation);
        return userEducations;
    }

    /*
     * public static OpinionLike getOpinionLike(Long componentLikeId) { UriComponentsBuilder builder =
     * UriComponentsBuilder .fromHttpUrl(TestUtils.BASE_URL + TestUtils.COMPONENTLIKE_CONTROLLER_APIS.FIND)
     * .queryParam("componentType", ComponentType.OPINION.getValue()) .queryParam("componentLikeId", componentLikeId);
     * HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders(); HttpEntity<ResponseDto<OpinionLike>>
     * response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, httpEntity, new
     * ParameterizedTypeReference<ResponseDto<OpinionLike>>() { }); return (OpinionLike)
     * (response.getBody().getMessages().get(0)); }
     */

    /*
     * public static Map<String, Object> getUserRequestBody() { Map<String, Object> requestBody = new HashMap<String,
     * Object>(); requestBody.put("firstName", "FirstName"); requestBody.put("lastName", "LastName");
     * requestBody.put("username", "username"); requestBody.put("password", "12345"); requestBody.put("status",
     * "Active"); requestBody.put("emailId", "testfoliticsuser@gmail.com"); requestBody.put("mobileNo", 9876543210l);
     * requestBody.put("autoPasswordGenerated", false); return requestBody; }
     */

    public static User getUser(Long id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.USER_CONTROLLER_APIS.FIND).queryParam("id", id);
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<User>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<User>>() {
                });
        return (User) (response.getBody().getMessages().get(0));
    }

    public static Map<String, Object> getResponseRequestBody(Map<String, Object> prerequisiteMap) {
        Map<String, Object> requestBody = new HashMap<String, Object>();

        User user = (User) prerequisiteMap.get("user");
        Opinion opinion = (Opinion) prerequisiteMap.get("opinion");
        requestBody.put("flag", ResponseType.AGREE.getValue());
        requestBody.put("content", "Response Subject");
        requestBody.put("edited", "2015-02-12T15:00:00.0");
        requestBody.put("timestamp", "2015-02-12T15:00:00.0");
        requestBody.put("user", user);
        requestBody.put("opinion", opinion);
        requestBody.put("state", "Active");
        requestBody.put("componentType", ComponentType.RESPONSE.getValue());
        requestBody.put(TestDataPreRequisites.ComponentPrerequisiteMapKeys.COMPONENT,
                prerequisiteMap.get(ComponentPrerequisiteMapKeys.OPNION));
        // requestBody.put("links", setOfLink);

        return requestBody;
    }

    public static Map<String, Object> getResponseRequestBody() {
        Map<String, Object> requestBody = new HashMap<String, Object>();

        requestBody.put("subject", "Response Subject");
        requestBody.put("text", "Response Text");
        // requestBody.put("links", setOfLink);
        requestBody.put("componentType", ComponentType.RESPONSE.getValue());
        return requestBody;
    }

    public static Response getResponse(Long id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.RESPONSE_CONTROLLER_APIS.FIND).queryParam("id", id);
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<Response>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<Response>>() {
                });
        return (Response) (response.getBody().getMessages().get(0));
    }

    public static Map<String, Object> getComponentRequestBody(Map<String, Object> prerequisiteMap) {
        Sentiment sen = (Sentiment) prerequisiteMap.get(TestDataPreRequisites.ComponentPrerequisiteMapKeys.SENTIMENT);
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("itemId", sen.getId());
        requestBody.put("componentType", ComponentType.SENTIMENT.getValue());
        requestBody.put("opinions", null);
        return requestBody;
    }

    public static Component getComponent(Long id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.COMPONENT_CONTROLLER_APIS.FIND).queryParam("id", id);
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<Component>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<Component>>() {
                });
        return (Component) (response.getBody().getMessages().get(0));
    }

    public static Map<String, Object> getPollRequestBody(Map<String, Object> prerequisitemap) {
        List<User> users = new ArrayList<User>();
        users.add((User) prerequisitemap.get(ComponentPrerequisiteMapKeys.USER));

        PollOption option1 = new PollOption();
        option1.setCreatedBy(10l);
        option1.setPollOption("Option 1");
        option1.setUsers(users);
        PollOption option2 = new PollOption();
        option2.setCreatedBy(10l);
        option2.setPollOption("Option 2");
        option2.setUsers(users);
        List<PollOption> pollOption = new ArrayList<PollOption>();
        pollOption.add(option1);
        pollOption.add(option2);

        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("question", "What is the test question?");
        requestBody.put("description", "this is the description");
        requestBody.put("createdBy", 10l);
        requestBody.put("options", pollOption);
        return requestBody;
    }

    public static Map<String, Object> getPollRequestBody() {
        PollOption option1 = new PollOption();
        option1.setCreatedBy(10l);
        option1.setPollOption("Option 1");
        PollOption option2 = new PollOption();
        option2.setCreatedBy(10l);
        option2.setPollOption("Option 2");
        List<PollOption> pollOption = new ArrayList<PollOption>();
        pollOption.add(option1);
        pollOption.add(option2);

        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("question", "What is the test question?");
        requestBody.put("description", "this is the description");
        requestBody.put("createdBy", 10l);
        requestBody.put("options", pollOption);
        return requestBody;
    }

    public static Map<String, Object> getPollRequestBody(Poll poll) {
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("id", poll.getId());
        requestBody.put("createdBy", poll.getCreatedBy());
        requestBody.put("createTime", poll.getCreateTime());
        requestBody.put("editedBy", poll.getEditedBy());
        requestBody.put("editTime", poll.getEditTime());
        requestBody.put("description", poll.getDescription());
        requestBody.put("question", poll.getQuestion());
        requestBody.put("sentiment", poll.getSentiment());
        requestBody.put("state", poll.getState());
        requestBody.put("options", poll.getOptions());

        return requestBody;
    }

    public static Poll getPoll(Long id) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.POLL_CONTROLLER_APIS.FIND).queryParam("id", id);

        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();

        HttpEntity<ResponseDto<Poll>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<Poll>>() {
                });

        return (Poll) (response.getBody().getMessages().get(0));
    }

    public static Map<String, Object> getMilestoneRequestBody() throws JsonProcessingException {
        // Building the Request body data
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("milestone", 30);
        requestBody.put("url", "https://www.google.co.in");
        requestBody.put("description", "Test Description");
        requestBody.put("milestone_type", "Mega Test");
        return requestBody;
    }

    public static Map<String, Object> getIndicatorDataRequestBody(Category category) throws JsonProcessingException {
        Map<String, Object> requestBody = new HashMap<String, Object>();

        requestBody.put("createTime", 1454043467928l);
        requestBody.put("deleted", 1);
        requestBody.put("delta", 1);
        requestBody.put("idealValueRange", "kkkk");
        requestBody.put("weightedIdealValue", 1);
        requestBody.put("weightedValue", 1);
        requestBody.put("editTime", 1454043467928l);
        requestBody.put("effectfromdate", 1454043467928l);
        requestBody.put("category", category);
        requestBody.put("indicatorvalue", 3);
        requestBody.put("score", 4);
        requestBody.put("state", "true");
        requestBody.put("thresholdcategory", "On Track");
        requestBody.put("updateddate", 1454043467928l);
        return requestBody;

    }

    public static Map<String, Object> getIndicatorDataResponseBody(Long categoryID) {
        // Building the Request body data
        Map<String, Object> requestBody = new HashMap<String, Object>();
        Category category = new Category();
        category.setId(categoryID);
        requestBody.put("createTime", 1454043467928l);
        requestBody.put("deleted", 1);
        requestBody.put("delta", 1);
        requestBody.put("idealValueRange", "kkkk");
        requestBody.put("weightedIdealValue", 1);
        requestBody.put("weightedValue", 1);
        requestBody.put("editTime", 1454043467928l);
        requestBody.put("effectfromdate", 1454043467928l);
        requestBody.put("category", category);
        requestBody.put("indicatorvalue", 3);
        requestBody.put("score", 1);
        requestBody.put("state", "true");
        requestBody.put("thresholdcategory", "On Track");
        requestBody.put("updateddate", 1454043467928l);
        return requestBody;
    }

    public static IndicatorData getIndicatorData(Long id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.INDICATOR_DATA_CONTROLLER_APIS.FIND).queryParam("id", id);
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<IndicatorData>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<IndicatorData>>() {
                });
        return (IndicatorData) (response.getBody().getMessages().get(0));
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

    public static Map<String, Object> getIndicatorThresholdRequestBody() {
        // Building the Request body data
        Map<String, Object> requestBody = new HashMap<String, Object>();
        Category category = new Category();
        category.setId(1l);
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

    public static Map<String, Object> getIndicatorWeightedDataRequestBody() throws JsonProcessingException {
        Map<String, Object> requestBody = new HashMap<String, Object>();
        Category category = new Category();
        category.setId(3l);
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

    public static Map<String, Object> getGovtSchemeDataRequestBody() throws JsonProcessingException {

        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("createTime", 1454043467928l);
        requestBody.put("isactive", 1);
        requestBody.put("editTime", 1454043467928l);
        requestBody.put("startdate", 1454043467928l);
        requestBody.put("state", "true");
        requestBody.put("schemename", "scname");
        requestBody.put("description1", "desc");
        requestBody.put("description2", "desccc");
        requestBody.put("webpageurl", "wpage");
        return requestBody;

    }

    public static GovtSchemeData getGovtSchemeData(Long id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.GOVT_SCHEME_DATA_CONTROLLER_APIS.FIND).queryParam("id", id);
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<GovtSchemeData>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<GovtSchemeData>>() {
                });
        return (GovtSchemeData) (response.getBody().getMessages().get(0));
    }

    public static Map<String, Object> getGovtSchemeDataResponseBody() {
        // Building the Request body data
        Map<String, Object> requestBody = new HashMap<String, Object>();

        // requestBody.put("id",1);
        requestBody.put("createTime", 1454043467928l);
        requestBody.put("isactive", 1);
        requestBody.put("editTime", 1454043467928l);
        requestBody.put("startdate", 1454043467928l);
        requestBody.put("state", "trueee");
        requestBody.put("schemename", "scname");
        requestBody.put("description1", "desc");
        requestBody.put("description2", "desccc");
        requestBody.put("webpageurl", "wpage");
        return requestBody;
    }

    public static Long testPreAddIndicatorThresholdApi(Category category) throws JsonProcessingException {
        Map<String, Object> requestBody = TestDataUtils.getPreIndicatorThresholdRequestBody(category);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.INDICATOR_THRESHOLD_DATA_CONTROLLER_APIS.ADD);
        HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
        HttpEntity<ResponseDto<IndicatorThreshold>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<IndicatorThreshold>>() {
                });

        ResponseDto<IndicatorThreshold> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
        return ((IndicatorThreshold) response.getBody().getMessages().get(0)).getId();
    }

    public static Map<String, Object> getPreIndicatorThresholdRequestBody(Category category) {
        // Building the Request body data
        Map<String, Object> requestBody = new HashMap<String, Object>();
        // requestBody.put("id",1);
        requestBody.put("createTime", 1454043467928l);
        requestBody.put("editTime", 1454043467928l);
        requestBody.put("direction", 5);
        requestBody.put("category", category);
        requestBody.put("state", "true");
        // requestBody.put("threshold", 4);
        requestBody.put("threshold_end", 5);
        requestBody.put("threshold_start", 1);
        requestBody.put("threshHoldCategory", "On Track");

        return requestBody;
    }

    public static Long testPreAddIndicatorWeightedDataApi(Category category) throws JsonProcessingException {
        Map<String, Object> requestBody = TestDataUtils.getPreIndicatorWeightedDataRequestBody(category);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.INDICATOR_WEIGHTED_DATA_CONTROLLER_APIS.ADD);
        HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
        HttpEntity<ResponseDto<IndicatorWeightedData>> response = restTemplate.exchange(builder.build().encode()
                .toUri(), HttpMethod.POST, httpEntity,
                new ParameterizedTypeReference<ResponseDto<IndicatorWeightedData>>() {
                });

        ResponseDto<IndicatorWeightedData> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
        return ((IndicatorWeightedData) response.getBody().getMessages().get(0)).getId();

    }

    public static Map<String, Object> getPreIndicatorWeightedDataRequestBody(Category category)
            throws JsonProcessingException {
        Map<String, Object> requestBody = new HashMap<String, Object>();
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

    public static Object getLike(Map<String, Object> prerequisiteMap) {

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(TestUtils.BASE_URL + TestUtils.LIKE_CONTROLLER_APIS.FIND)
                .queryParam("componentType", "sentiment")
                .queryParam("componentId",
                        ((Sentiment) prerequisiteMap.get(ComponentPrerequisiteMapKeys.SENTIMENT)).getId())
                .queryParam("userId", ((User) prerequisiteMap.get(ComponentPrerequisiteMapKeys.USER)).getId());

        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();

        HttpEntity<ResponseDto<Object>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<Object>>() {
                });
        return response.getBody().getMessages().get(0);
    }

    public static Map<String, Object> getShareRequestBody(Map<String, Object> prerequisiteMap) {

        Map<String, Object> requestBody = new HashMap<String, Object>();

        requestBody.put("componentType", ComponentType.SENTIMENT.getValue().toLowerCase());
        requestBody.put("componentId",
                ((Sentiment) prerequisiteMap.get(ComponentPrerequisiteMapKeys.SENTIMENT)).getId());
        requestBody.put("userId", ((User) prerequisiteMap.get(ComponentPrerequisiteMapKeys.USER)).getId());
        requestBody.put("platform", "facebook");
        requestBody.put("description", "this is the description");

        return requestBody;
    }

    public static SentimentShare getShare(Long id) {

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(TestUtils.BASE_URL + TestUtils.SHARE_CONTROLLER_APIS.FIND)
                .queryParam("componentType", ComponentType.SENTIMENT.getValue().toLowerCase()).queryParam("id", id);

        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();

        HttpEntity<ResponseDto<Object>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<Object>>() {
                });
        return (SentimentShare) response.getBody().getMessages().get(0);
    }

    public static Map<String, Object> getVerdictRequestBody(Map<String, Object> prerequisiteMap) {

        Sentiment sentiment = new Sentiment();
        sentiment = (Sentiment) prerequisiteMap.get(ComponentPrerequisiteMapKeys.SENTIMENT);

        Map<String, Object> requestBody = new HashMap<String, Object>();

        requestBody.put("sentiment", sentiment);
        requestBody.put("verdictAgeGroups", null);
        requestBody.put("verdictSexes", null);
        requestBody.put("verdictMaritalStatuses", null);
        requestBody.put("verdictReligions", null);
        requestBody.put("verdictRegions", null);
        requestBody.put("verdictQualifications", null);

        return requestBody;
    }

    public static Verdict getVerdict(Long id) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.VERDICT_CONTROLLER_APIS.FIND).queryParam("id", id);

        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();

        HttpEntity<ResponseDto<Verdict>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<Verdict>>() {
                });

        return (Verdict) response.getBody().getMessages().get(0);
    }
}
