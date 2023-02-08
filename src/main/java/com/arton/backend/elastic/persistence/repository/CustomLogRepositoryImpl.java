package com.arton.backend.elastic.persistence.repository;

import com.arton.backend.elastic.persistence.document.AccessLogDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Repository
@RequiredArgsConstructor
public class CustomLogRepositoryImpl implements CustomLogRepository{
    private final ElasticsearchOperations elasticsearchOperations;

    /**
     * 시간을 비교할때 Long으로 넘겨줘야함..
     * 기본적으로 dynamic finder가 long으로 비교한다고 한다.
     * @return
     */
    @Override
    public List<AccessLogDocument> getRecentTop10Keywords() {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery()
                        .must(matchQuery("message", "/performance/search"))
                                .must(matchPhraseQuery("logger_name", "LOGSTASH"))
                        .must(rangeQuery("@timestamp")
                                .gte(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                                .lte(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())))
                .build();
        List<AccessLogDocument> documents = elasticsearchOperations
                . search(searchQuery, AccessLogDocument.class, IndexCoordinates.of("logstash*")).stream().map(SearchHit::getContent).collect(Collectors.toList());
        return documents;
    }
}
