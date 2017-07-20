package com.ohmuk.folitics.controller.user;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.hibernate.entity.User;
import com.ohmuk.folitics.hibernate.entity.UserEmailNotificationSettings;
import com.ohmuk.folitics.utils.TestDataUtils;
import com.ohmuk.folitics.utils.TestUtils;

/**
 * @author Abhishek
 *
 */
@RunWith(BlockJUnit4ClassRunner.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUserController {
    // Test RestTemplate to invoke the APIs.
    protected RestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void TestController() throws JsonProcessingException, IOException {

        User user = testAddUserApi();
        Long id = user.getId();

        Long SecondUserId = testAddMultipartUser();

        testEditUserApi(user);
        testUserImageUpload(id);
        testGetAllUserApi();
        testFindUserApi(id);
        testAddConnection(id, SecondUserId);
        testSaveUserEmailNotificationSettings(id);
        testSaveUserUINotification(id);
        //testEditUserEmailNotificationSettings(id);
        //testEditUserUINotification(id);
        getAllUserEmailNotificationSettings(id);
        testUpdateConnectionStatus(id, SecondUserId, "Accepted");
        testGetUserConnection(id);
        getAllUserUINotificationSettings(id);

        testBlockUser(id, SecondUserId);
        testUnBlockUser(id, SecondUserId);

        testDeleteConnectionApi(id, SecondUserId);
        testSoftDeleteByObjectApi(id);
        testSoftDeleteByIdApi(id);
        testPermanentDeleteUserByIdApi(id);
        testPermanentDeleteUserByObjectApi(SecondUserId);
    }

    private Long testAddMultipartUser() throws IOException {

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

        String mapAsJson = new ObjectMapper().writeValueAsString(TestDataUtils.getUserRequestBody());

        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> userEntity = new HttpEntity<String>(mapAsJson, userHeaders);

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("userImage", image);
        map.add("user", userEntity);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
                map, headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.USER_CONTROLLER_APIS.ADD_MULTIPART);
        HttpEntity<ResponseDto<User>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<User>>() {
                });
        ResponseDto<User> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
        User user = (User) (response.getBody().getMessages().get(0));
        return user.getId();
    }

    protected User testAddUserApi() throws IOException {
        HttpEntity<String> httpEntity = TestUtils.getHttpEntity(TestDataUtils.getUserConnectionRequestBody());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.USER_CONTROLLER_APIS.ADD);
        HttpEntity<ResponseDto<User>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<User>>() {
                });
        ResponseDto<User> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
        User user = (User) (response.getBody().getMessages().get(0));
        user = TestDataUtils.getUser(user.getId());
        assertNotNull(user);
        return user;
    }

    protected void testEditUserApi(User user) throws IOException {
        // Map<String, Object> requestBody = TestDataUtils.getUserRequestBody();
        String updatedDescription = user.getUsername()+ "updated";
        user.setUsername(user.getUsername() + "updated");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.USER_CONTROLLER_APIS.EDIT);
        HttpEntity<String> httpEntity = TestUtils.getHttpEntity(new ObjectMapper().convertValue(user, Map.class));
        HttpEntity<ResponseDto<User>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<User>>() {
                });
        ResponseDto<User> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
        User updatedUser = (User) (response.getBody().getMessages().get(0));
        assertTrue(updatedDescription.equals(user.getUsername()));
    }

    protected void testResetPasswordApi(Long id) {
        User user = TestDataUtils.getUser(id);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.USER_CONTROLLER_APIS.RESET_PASSWORD).queryParam("id", id);
        HttpEntity<?> entity = new HttpEntity<>(user);
        HttpEntity<ResponseDto<User>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDto<User>>() {
                });
        assertNotNull(response);
        assertTrue(response.getBody().getSuccess());
    }

    protected void testSoftDeleteByObjectApi(Long id) throws JsonProcessingException {
        User user = TestDataUtils.getUser(id);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.USER_CONTROLLER_APIS.DELETE);
        HttpEntity<?> entity = new HttpEntity<>(user);
        HttpEntity<ResponseDto<User>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDto<User>>() {
                });
        assertNotNull(response);
        assertTrue(response.getBody().getSuccess());
    }

    protected void testPermanentDeleteUserByIdApi(Long id) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.USER_CONTROLLER_APIS.PERMANENT_DELETE_BY_ID).queryParam("id", id);
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<User>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<User>>() {
                });

        assertTrue(response.getBody().getSuccess());
    }

    protected void testPermanentDeleteUserByObjectApi(Long id) {
        User user = TestDataUtils.getUser(id);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.USER_CONTROLLER_APIS.PERMANENT_DELETE);
        HttpEntity<?> entity = new HttpEntity<>(user);
        HttpEntity<ResponseDto<User>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDto<User>>() {
                });
        assertTrue(response.getBody().getSuccess());
    }

    protected void testUserImageUpload(Long userId) {
        try {
            HttpMessageConverter<Object> jackson = new MappingJackson2HttpMessageConverter();
            HttpMessageConverter<byte[]> resource = new ByteArrayHttpMessageConverter();
            FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
            formHttpMessageConverter.addPartConverter(jackson);
            formHttpMessageConverter.addPartConverter(resource);

            RestTemplate restTemplateTemp = new RestTemplate(Arrays.asList(jackson, resource, formHttpMessageConverter));

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

            LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("userImage", image);
            map.add("userId", userId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
                    map, headers);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                    + TestUtils.USER_CONTROLLER_APIS.UPLOADIMAGE);
            HttpEntity<ResponseDto<User>> response = restTemplateTemp.exchange(builder.build().encode().toUri(),
                    HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<User>>() {
                    });
            ResponseDto<User> apiResponse = response.getBody();
            assertNotNull(apiResponse);
            assertTrue(apiResponse.getSuccess());
            User user = (User) (response.getBody().getMessages().get(0));
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(Boolean.FALSE);
        }
    }

    protected void testSaveUserEmailNotificationSettings(Long userId) throws JsonProcessingException {

        Map<String, Object> requestBody = getUserEmailNotificationSettingsData(userId);
        HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.USER_CONTROLLER_APIS.SAVE_USER_EMAIL_NOTIFICATION).queryParam("userId",
                userId);
        
        HttpEntity<ResponseDto<Long>> response = restTemplate.exchange(builder.build()
                .encode().toUri(), HttpMethod.POST, httpEntity,
                new ParameterizedTypeReference<ResponseDto<Long>>() {
                });
        assertTrue(response.getBody().getSuccess());
    }

    protected void testEditUserEmailNotificationSettings(Long userId) throws JsonProcessingException {
        Map<String, Object> requestBody = getUserEmailNotificationSettingsData(userId);
        String componentType = "updated";
        // requestBody.put("id", id);
        requestBody.put("componentType", componentType);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.USER_CONTROLLER_APIS.UPDATE_USER_EMAIL_NOTIFICATION);
        HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
        HttpEntity<ResponseDto<UserEmailNotificationSettings>> response = restTemplate.exchange(builder.build()
                .encode().toUri(), HttpMethod.POST, httpEntity,
                new ParameterizedTypeReference<ResponseDto<UserEmailNotificationSettings>>() {
                });
        assertTrue(response.getBody().getSuccess());
    }

    public void getAllUserEmailNotificationSettings(Long id) throws JsonProcessingException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.USER_CONTROLLER_APIS.GETALL_USER_EMAIL_NOTIFICATION).queryParam(
                "userId", id);
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<UserEmailNotificationSettings>> response = restTemplate.exchange(builder.build()
                .encode().toUri(), HttpMethod.GET, httpEntity,
                new ParameterizedTypeReference<ResponseDto<UserEmailNotificationSettings>>() {
                });

        ResponseDto<UserEmailNotificationSettings> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
    }

    protected void testSaveUserUINotification(Long userId) throws JsonProcessingException {

        Map<String, Object> requestBody = getUserUINotificationSettingsData(userId);
        HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.USER_CONTROLLER_APIS.SAVE_USER_UI_NOTIFICATION).queryParam("userId",
                userId);
        HttpEntity<ResponseDto<Long>> response = restTemplate.exchange(builder.build()
                .encode().toUri(), HttpMethod.POST, httpEntity,
                new ParameterizedTypeReference<ResponseDto<Long>>() {
                });

        assertTrue(response.getBody().getSuccess());

    }

    protected void testEditUserUINotification(Long userId) throws JsonProcessingException {
        Map<String, Object> requestBody = getUserUINotificationSettingsData(userId);
        String componentType = "updated";
        // requestBody.put("id", id);
        requestBody.put("componentType", componentType);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.USER_CONTROLLER_APIS.UPDATE_USER_UI_NOTIFICATION);
        HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
        HttpEntity<ResponseDto<UserEmailNotificationSettings>> response = restTemplate.exchange(builder.build()
                .encode().toUri(), HttpMethod.POST, httpEntity,
                new ParameterizedTypeReference<ResponseDto<UserEmailNotificationSettings>>() {
                });
        assertTrue(response.getBody().getSuccess());
    }

    public void getAllUserUINotificationSettings(Long id) throws JsonProcessingException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.USER_CONTROLLER_APIS.GETALL_USER_UI_NOTIFICATION).queryParam("userId",
                id);
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<UserEmailNotificationSettings>> response = restTemplate.exchange(builder.build()
                .encode().toUri(), HttpMethod.GET, httpEntity,
                new ParameterizedTypeReference<ResponseDto<UserEmailNotificationSettings>>() {
                });

        ResponseDto<UserEmailNotificationSettings> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
    }

    protected void testBlockUser(Long userId, Long blockUserId) throws JsonProcessingException {

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(TestUtils.BASE_URL + TestUtils.USER_CONTROLLER_APIS.BLOCK_USER)
                .queryParam("userId", userId).queryParam("blockUserId", blockUserId);
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<User>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<User>>() {
                });
        assertTrue(response.getBody().getSuccess());
    }

    protected void testUnBlockUser(Long userId, Long blockUserId) throws JsonProcessingException {

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(TestUtils.BASE_URL + TestUtils.USER_CONTROLLER_APIS.UN_BLOCK_USER)
                .queryParam("userId", userId).queryParam("blockUserId", blockUserId);
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<UserEmailNotificationSettings>> response = restTemplate.exchange(builder.build()
                .encode().toUri(), HttpMethod.GET, httpEntity,
                new ParameterizedTypeReference<ResponseDto<UserEmailNotificationSettings>>() {
                });
        assertTrue(response.getBody().getSuccess());
    }

    public void testGetUserConnection(Long id) throws JsonProcessingException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.USER_CONTROLLER_APIS.GET_USER_CONNECTION).queryParam("userId", id);
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<User>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<User>>() {
                });

        ResponseDto<User> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
    }

    protected void testAddConnection(Long userId, Long connectionId) throws JsonProcessingException {

        Map<String, Object> requestBody = getUserUINotificationSettingsData(userId);
        HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(TestUtils.BASE_URL + TestUtils.USER_CONTROLLER_APIS.ADD_CONNECTION)
                .queryParam("userId", userId).queryParam("connectionId", connectionId);
        HttpEntity<ResponseDto<Long>> response = restTemplate.exchange(builder.build()
                .encode().toUri(), HttpMethod.GET, httpEntity,
                new ParameterizedTypeReference<ResponseDto<Long>>() {
                });

        assertTrue(response.getBody().getSuccess());

    }

    protected void testUpdateConnectionStatus(Long userId, Long connectionId, String status)
            throws JsonProcessingException {
        Map<String, Object> requestBody = getUserUINotificationSettingsData(userId);
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(TestUtils.BASE_URL + TestUtils.USER_CONTROLLER_APIS.UPDATE_CONNECTION_STATUS)
                .queryParam("userId", userId).queryParam("connectionId", connectionId)
                .queryParam("connectionStatus", status);
        HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);
        HttpEntity<ResponseDto<UserEmailNotificationSettings>> response = restTemplate.exchange(builder.build()
                .encode().toUri(), HttpMethod.GET, httpEntity,
                new ParameterizedTypeReference<ResponseDto<UserEmailNotificationSettings>>() {
                });
        assertTrue(response.getBody().getSuccess());
    }

    protected void testDeleteConnectionApi(Long userId, Long connectionId) throws JsonProcessingException {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(TestUtils.BASE_URL + TestUtils.USER_CONTROLLER_APIS.DELETE_CONNECTION)
                .queryParam("userId", userId).queryParam("connectionId", connectionId);
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<User>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<User>>() {
                });
        assertTrue(response.getBody().getSuccess());
    }

    protected void testFindUserApi(Long id) throws JsonProcessingException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.USER_CONTROLLER_APIS.FIND).queryParam("id", id);
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<User>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<User>>() {
                });
        assertTrue(response.getBody().getSuccess());
    }

    protected void testGetAllUserApi() throws JsonProcessingException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.USER_CONTROLLER_APIS.GET_ALL);
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<UserEmailNotificationSettings>> response = restTemplate.exchange(builder.build()
                .encode().toUri(), HttpMethod.GET, httpEntity,
                new ParameterizedTypeReference<ResponseDto<UserEmailNotificationSettings>>() {
                });

        assertTrue(response.getBody().getSuccess());
    }

    protected void testSoftDeleteByIdApi(Long id) throws JsonProcessingException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.USER_CONTROLLER_APIS.DELETE_BY_ID).queryParam("id", id);
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<User>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<User>>() {
                });

        assertTrue(response.getBody().getSuccess());
    }

    public static Map<String, Object> getUserEmailNotificationSettingsData(Long userId) {
        Map<String, Object> requestBody = new HashMap<String, Object>();
        User user = new User();
        user.setId(userId);
        requestBody.put("id", 1l);
        requestBody.put("componentType", "fsd");
        requestBody.put("notificationType", true);
        requestBody.put("user", user);
        return requestBody;
    }

    public static Map<String, Object> getUserPrivacySettingsDataData() {
        Map<String, Object> requestBody = new HashMap<String, Object>();
        User user = new User();
        user.setId(1l);
        requestBody.put("notificationType", "All");
        requestBody.put("userDataField", "fsd");
        requestBody.put("user", user);
        return requestBody;
    }

    public static Map<String, Object> getUserUINotificationSettingsData(Long userId) {
        Map<String, Object> requestBody = new HashMap<String, Object>();
        User user = new User();
        user.setId(userId);
        requestBody.put("id", 1l);
        requestBody.put("componentType", "fsd");
        requestBody.put("notificationType", true);
        requestBody.put("user", user);
        return requestBody;
    }

}
