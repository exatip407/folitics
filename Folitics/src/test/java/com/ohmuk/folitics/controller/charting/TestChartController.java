package com.ohmuk.folitics.controller.charting;

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
import com.ohmuk.folitics.charting.beans.LineChartData;
import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.hibernate.entity.EventReportScore;
import com.ohmuk.folitics.hibernate.entity.GPIPoint;
import com.ohmuk.folitics.util.DateUtils;
import com.ohmuk.folitics.utils.TestDataPreRequisites;
import com.ohmuk.folitics.utils.TestUtils;

@RunWith(BlockJUnit4ClassRunner.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestChartController {

    private RestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void AddchartData() {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.GPI_POINTS_CONTROLLER_APIS.ADD);

        Map<String, Object> prerequisiteMap = new HashMap<String, Object>();
        long l = 86400000l;

        for (int i = 0; i < 10; i++) {
            prerequisiteMap = new HashMap<String, Object>();
            prerequisiteMap.put("createTime", DateUtils.getSqlTimeStamp(l * i));
            prerequisiteMap.put("editTime", DateUtils.getSqlTimeStamp(l * i));
            prerequisiteMap.put("startTime", DateUtils.getSqlTimeStamp(l * i));
            prerequisiteMap.put("endTime", DateUtils.getSqlTimeStamp(l * i));
            prerequisiteMap.put("totalPoints", 30 * i);
            prerequisiteMap.put("opinionResponseAggregationPoints", 10 * i);
            prerequisiteMap.put("indicatorChangePoints", 10 * i);
            prerequisiteMap.put("eventReportPoints", 10 * i);

            HttpEntity<String> httpEntity = null;
            try {
                httpEntity = TestUtils.getHttpEntity(prerequisiteMap);
            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            HttpEntity<ResponseDto<GPIPoint>> response = restTemplate.exchange(builder.build().encode().toUri(),
                    HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseDto<GPIPoint>>() {
                    });

        }

    }

    public void testGetchart() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(TestUtils.BASE_URL + TestUtils.CHART_CONTROLLER_APIS.GET)
                .queryParam("ChartID", "GPIChart").queryParam("ChartSecondaryID", "GPIChart");
        HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
        HttpEntity<ResponseDto<LineChartData>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ResponseDto<LineChartData>>() {
                });
        
        ResponseDto<LineChartData> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.getSuccess());
        /*UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.SENTIMENT_CONTROLLER_APIS.FIND).queryParam("id",9 );*/
        //HttpEntity<HttpHeaders> httpEntity = TestUtils.getHttpEntityHttpHeaders();
       
    }
}
