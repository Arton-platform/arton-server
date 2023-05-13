package com.arton.backend.infra.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

    @Value("${elastic.server.ip}")
    private String ip;

    @Value("${elastic.username}")
    private String username;

    @Value("${elastic.password}")
    private String password;

    @Override
    public RestHighLevelClient elasticsearchClient() {
        // http port 와 통신할 주소
        ClientConfiguration configuration = ClientConfiguration
                .builder()
                .connectedTo(ip)
                .withBasicAuth(username, password)
                .build();
        return RestClients.create(configuration).rest();
    }
}
