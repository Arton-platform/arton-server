package com.arton.backend.infra.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {
    @Override
    public RestHighLevelClient elasticsearchClient() {
        // http port 와 통신할 주소
//        ClientConfiguration configuration = ClientConfiguration.builder().connectedTo("localhost:9200").build();
        ClientConfiguration configuration = ClientConfiguration.builder().connectedTo("13.125.194.137:9200").build();
        return RestClients.create(configuration).rest();
    }
}
