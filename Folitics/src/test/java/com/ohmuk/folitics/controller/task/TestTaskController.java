package com.ohmuk.folitics.controller.task;

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
import java.util.List;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.MethodSorters;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.hibernate.entity.task.Department;
import com.ohmuk.folitics.hibernate.entity.task.Task;
import com.ohmuk.folitics.hibernate.entity.task.TaskCategory;
import com.ohmuk.folitics.utils.TestDataPreRequisites;
import com.ohmuk.folitics.utils.TestDataUtils;
import com.ohmuk.folitics.utils.TestUtils;
import com.ohmuk.folitics.utils.TestUtils.TASK_CONTROLLER_APIS;

/**
 * Test Controller for module Task
 * @author Sarvesh
 *
 */

@RunWith(BlockJUnit4ClassRunner.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestTaskController {

    private RestTemplate restTemplate;

    @Test
    public void TestController() throws IOException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, IntrospectionException {

        Map<String, Object> prerequisiteMap = TestDataPreRequisites.createTaskPrerequisites();

        HttpMessageConverter<Object> jackson = new MappingJackson2HttpMessageConverter();
        HttpMessageConverter<byte[]> resource = new ByteArrayHttpMessageConverter();
        FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        formHttpMessageConverter.addPartConverter(jackson);
        formHttpMessageConverter.addPartConverter(resource);

        restTemplate = new RestTemplate(Arrays.asList(jackson, resource, formHttpMessageConverter));

        Task task = testAddTaskApi(prerequisiteMap);

        // testEditTaskApi(task.getId());
        /*testSearchTaskApi(task.getId());
        testGetAllTasksApi();
        testGetTaskCategories();
        testGetTaskCategories();
        testGetAllDepartments();
        testDeleteTaskApi(task.getId());*/

    }

    private Task testAddTaskApi(Map<String, Object> prerequisiteMap) throws IOException {

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

        String mapAsJson = new ObjectMapper().writeValueAsString(prerequisiteMap);

        HttpHeaders taskHeaders = new HttpHeaders();
        taskHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> taskEntity = new HttpEntity<String>(mapAsJson, taskHeaders);

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", image);
        map.add("task", taskEntity);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
                map, headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.TASK_CONTROLLER_APIS.ADD);
        HttpEntity<ResponseDto<Task>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<Task>>() {
                });
        ResponseDto<Task> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
        Task task = (Task) (response.getBody().getMessages().get(0));
        assertNotNull(task);
        return task;

    }

    private void testDeleteTaskApi(Long id) throws IOException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TASK_CONTROLLER_APIS.DELETE).queryParam("id", id);

        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();

        HttpEntity<ResponseDto<Task>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<Task>>() {
                });

        ResponseDto<Task> apiResponse = response.getBody();

        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
    }

    private void testEditTaskApi(Long id) throws IntrospectionException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, IOException {

        Task task = TestDataUtils.getTask(id);

        String updatedDescription = "This is test updated task";

        Map<String, Object> requestBody = new HashMap<String, Object>();

        BeanInfo info = Introspector.getBeanInfo(task.getClass());
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            Method reader = pd.getReadMethod();
            if (reader != null)
                requestBody.put(pd.getName(), reader.invoke(task));
        }

        requestBody.put("description", updatedDescription);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.TASK_CONTROLLER_APIS.EDIT);

        HttpEntity<String> httpEntity = TestUtils.getHttpEntity(requestBody);

        HttpEntity<ResponseDto<Task>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<Task>>() {
                });

        ResponseDto<Task> apiResponse = response.getBody();

        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
        task = (Task) (response.getBody().getMessages().get(0));
        assertTrue(updatedDescription.equals(task.getDescription()));
    }

    private void testSearchTaskApi(Long id) {
        Task task = TestDataUtils.getTask(id);
        assertNotNull(task);
    }

    private void testGetAllTasksApi() {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.TASK_CONTROLLER_APIS.FIND_All);

        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();

        HttpEntity<ResponseDto<List<Task>>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<List<Task>>>() {
                });

        ResponseDto<List<Task>> apiResponse = response.getBody();

        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
    }

    private void testGetTaskCategories() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.TASK_CONTROLLER_APIS.GET_CATEGORIES);

        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();

        HttpEntity<ResponseDto<List<TaskCategory>>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<List<TaskCategory>>>() {
                });

        ResponseDto<List<TaskCategory>> apiResponse = response.getBody();

        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
    }

    private void testGetAllDepartments() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.TASK_CONTROLLER_APIS.GET_DEPARTMENTS);

        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();

        HttpEntity<ResponseDto<List<Department>>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<List<Department>>>() {
                });

        ResponseDto<List<Department>> apiResponse = response.getBody();

        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());

    }
}
