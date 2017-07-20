package com.ohmuk.folitics.jpa.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RestClientTest {

    protected static final Logger LOGGER = LoggerFactory.getLogger(RestClientTest.class);

    public static final String BASE_URL = "http://localhost:8080";

    /**
     * @param args
     */
    public static void main(String[] args) {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;

        try {

            httpClient = HttpClients.createDefault();
            httpPost = new HttpPost(BASE_URL + "/event/add");

            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("content-type", "application/json"));
            // nvps.add(new BasicNameValuePair("x-kii-appid", "xxxxx"));
            // nvps.add(new BasicNameValuePair("x-kii-appkey", "xxxxxxxxxxxxxx"));

            StringEntity input = new StringEntity(
                    "{\"id\": 100,\"name\": \"TestEvent\",\"description\": \"This is test event\",\"participants\": 2,\"date\": 1453038569131}");
            input.setContentType("application/json");
            httpPost.setEntity(input);

            for (NameValuePair h : nvps) {
                httpPost.addHeader(h.getName(), h.getValue());
            }

            response = httpClient.execute(httpPost);
            // http://localhost:8080/event/add
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
        } catch (MalformedURLException e) {

            LOGGER.error("Error while testing RestClient: ", e);

        } catch (IOException e) {

            LOGGER.error("Error while testing RestClient: ", e);

        } finally {
            try {
                response.close();
                httpClient.close();
            } catch (Exception e) {
                LOGGER.error("Error while testing RestClient: ", e);
            }
        }

    }
}