package com.ohmuk.folitics.elasticsearch;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.stereotype.Component;

@Component
public class ElasticSearchClient {
    public static Client client;

    public static Client getClient() throws UnknownHostException {
        if (null == client) {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
            return client;
        } else {
            return client;
        }
    }

}
