package com.ohmuk.folitics.controller.rssfeed;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Random;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.hibernate.entity.newsfeed.FeedSource;
import com.ohmuk.folitics.mongodb.entity.NewsFeed;
import com.ohmuk.folitics.utils.TestUtils;

@RunWith(BlockJUnit4ClassRunner.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestRSSFeedController {

    protected static final Logger LOGGER = LoggerFactory.getLogger(TestRSSFeedController.class);

    // Test RestTemplate to invoke the APIs.
    private RestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testRSSFeedAggration() {
        FeedSource feedSource = testRSSFeedAddSource();
        LOGGER.info("Feedsouce : " + feedSource);
        testRSSFeedLoad(feedSource);
        testRSSFeedfindSource(feedSource, Boolean.FALSE);
        testRSSFeedFindAllSources();
        testRSSFeedSearchText(feedSource);
        testRSSFeedDisableSource(feedSource);
        testRSSFeedfindSource(feedSource, Boolean.TRUE);
        testRSSFeedClearFeed(feedSource);
    }

    private int getRandomNumber() {
        Random rand = new Random();
        return rand.nextInt(100);
    }

    public FeedSource testRSSFeedAddSource() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(TestUtils.BASE_URL + TestUtils.RSS_FEED_CONTROLLER_APIS.ADD_SOURCE)
                .queryParam("sourceName", "NDTVTest" + getRandomNumber())
                .queryParam("sourceURL", "http://feeds.feedburner.com/NdtvNews-TopStories");
        HttpEntity<?> entity = new HttpEntity<>(TestUtils.getHttpHeadersApplicationJson());
        HttpEntity<ResponseDto<FeedSource>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDto<FeedSource>>() {
                });
        assertNotNull(response);
        assertTrue(response.getBody().getSuccess());
        return (FeedSource) (response.getBody().getMessages().get(0));
    }

    private void testRSSFeedLoad(FeedSource feedSource) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.RSS_FEED_CONTROLLER_APIS.LOAD_FEED).queryParam("sourceName",
                feedSource.getName());
        HttpEntity<?> entity = new HttpEntity<>(TestUtils.getHttpHeadersApplicationJson());
        HttpEntity<ResponseDto<String>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDto<String>>() {
                });
        assertNotNull(response);
        assertTrue(response.getBody().getSuccess());
    }

    private void testRSSFeedfindSource(FeedSource feedSource, boolean checkDisabled) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.RSS_FEED_CONTROLLER_APIS.FIND_SOURCE_BY_NAME).queryParam("sourceName",
                feedSource.getName());
        HttpEntity<?> entity = new HttpEntity<>(TestUtils.getHttpHeadersApplicationJson());
        HttpEntity<ResponseDto<List<FeedSource>>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDto<List<FeedSource>>>() {
                });
        assertNotNull(response);
        if (!checkDisabled) {
            assertTrue(response.getBody().getSuccess());
            List<FeedSource> fss = (List<FeedSource>) (response.getBody().getMessages().get(0));
            assertTrue(fss.get(0).getName().equals(feedSource.getName()));
        } else {
            assertTrue(!response.getBody().getSuccess());
        }
    }

    private void testRSSFeedFindAllSources() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TestUtils.BASE_URL
                + TestUtils.RSS_FEED_CONTROLLER_APIS.GET_ALL_SOURCES);
        HttpEntity<?> entity = new HttpEntity<>(TestUtils.getHttpHeadersApplicationJson());
        HttpEntity<ResponseDto<List<FeedSource>>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, entity, new ParameterizedTypeReference<ResponseDto<List<FeedSource>>>() {
                });
        assertNotNull(response);
        assertTrue(response.getBody().getSuccess());
        List<FeedSource> fs = (List<FeedSource>) (response.getBody().getMessages().get(0));
        assertTrue(fs.size() > 0);
    }

    private void testRSSFeedDisableSource(FeedSource feedSource) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.RSS_FEED_CONTROLLER_APIS.DISABLE_SOURCE).queryParam("id",
                feedSource.getId());
        HttpEntity<?> entity = new HttpEntity<>(TestUtils.getHttpHeadersApplicationJson());
        HttpEntity<ResponseDto<String>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDto<String>>() {
                });
        assertNotNull(response);
        assertTrue(response.getBody().getSuccess());
        String ret = (String) (response.getBody().getMessages().get(0));
        String expectedValue = "Feed source disbale with id: " + feedSource.getId();
        assertTrue(expectedValue.equals(ret));
    }

    private void testRSSFeedSearchText(FeedSource feedSource) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.RSS_FEED_CONTROLLER_APIS.SEARCH_FEED).queryParam("keyword", "NDTV");
        HttpEntity<?> entity = new HttpEntity<>(TestUtils.getHttpHeadersApplicationJson());
        HttpEntity<ResponseDto<List<NewsFeed>>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDto<List<NewsFeed>>>() {
                });
        assertNotNull(response);
        assertTrue(response.getBody().getSuccess());
        List<NewsFeed> nfs = (List<NewsFeed>) (response.getBody().getMessages().get(0));
        assertTrue(nfs.size() > 0);
    }

    private void testRSSFeedClearFeed(FeedSource feedSource) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                TestUtils.BASE_URL + TestUtils.RSS_FEED_CONTROLLER_APIS.CLEAR_FEED)
                .queryParam("id", feedSource.getId());
        HttpEntity<?> entity = new HttpEntity<>(TestUtils.getHttpHeadersApplicationJson());
        HttpEntity<ResponseDto<String>> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDto<String>>() {
                });
        assertNotNull(response);
        assertTrue(response.getBody().getSuccess());
        String ret = (String) (response.getBody().getMessages().get(0));
        String expectedValue = "All feed has been cleared for feed source id : " + feedSource.getId();
        assertTrue(expectedValue.equals(ret));
    }
}
