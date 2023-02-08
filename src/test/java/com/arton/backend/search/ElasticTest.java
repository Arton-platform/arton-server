package com.arton.backend.search;

import com.arton.backend.search.persistence.repository.PerformanceSearchRepository;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
@EnableElasticsearchRepositories(basePackageClasses = {PerformanceSearchRepository.class})
public class ElasticTest {
    private static final String version = "7.15.2";
    private static final DockerImageName ELASTICSEARCH_IMAGE =
            DockerImageName
                    .parse("docker.elastic.co/elasticsearch/elasticsearch")
                    .withTag(version);
    private static final ElasticsearchContainer container;

    // testContainer 띄우기
    static {
        container = new ElasticsearchContainer(ELASTICSEARCH_IMAGE);
        container.start();
    }

    // 띄운 컨테이너로 ESCilent 재정의
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(container.getHttpHostAddress())
                .build();
        return RestClients.create(clientConfiguration).rest();
    }

}