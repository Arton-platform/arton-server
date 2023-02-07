package com.arton.backend.elastic.persistence.repository;

import com.arton.backend.elastic.persistence.document.AccessLogDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;

@Repository
@RequiredArgsConstructor
public class CustomLogRepositoryImpl implements CustomLogRepository{
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<AccessLogDocument> getRecentTop10Keywords() {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("requestURI", "/performance/search"))
                .withQuery(rangeQuery("date").gte("2023-02-07T09:38:00").lte("2023-02-07T10:38:00"))
                .build();
        List<AccessLogDocument> documents = elasticsearchOperations
                .search(searchQuery, AccessLogDocument.class, IndexCoordinates.of("application-accesslog-2023-02-07")).stream().map(SearchHit::getContent).collect(Collectors.toList());
        for (AccessLogDocument document : documents) {
            System.out.println("document = " + document.getQueryString());
        }
        return documents;
    }
}
