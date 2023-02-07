package com.arton.backend.elastic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class ElasticSearchTest {

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void directConnectionTest() throws JsonProcessingException {
        String str = "{\n" +
                "  \"query\": {\n" +
                "    \"bool\": {\n" +
                "      \"must\": [\n" +
                "        {\n" +
                "          \"match\": {\n" +
                "          \"requestURI\": \"/performance/search\"\n" +
                "        }\n" +
                "        },\n" +
                "        {\n" +
                "          \"range\": {\n" +
                "            \"date\": {\n" +
                "              \"gte\": \"2023-02-07T09:38:00\",\n" +
                "              \"lte\": \"2023-02-07T10:00:00\"\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  },\n" +
                "  \"fields\":[\n" +
                "      \"queryString\",\n" +
                "      \"requestURI\",\n" +
                "      \"date\"\n" +
                "      ],\n" +
                "      \"_source\": false\n" +
                "}";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request =
                new HttpEntity<String>(str, httpHeaders);

        String personResultAsJsonStr =
                restTemplate.postForObject("http://localhost:9200/application-accesslog-2023-02-07/_search", request, String.class);
        System.out.println("personResultAsJsonStr = " + personResultAsJsonStr);
    }
}
