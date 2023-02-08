package com.arton.backend.search.persistence.repository;

import com.arton.backend.search.application.data.RealTimeKeywordDto;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.search.application.data.SearchPageDto;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Repository
@RequiredArgsConstructor
public class CustomLogRepositoryImpl implements CustomLogRepository{
    private final ElasticsearchOperations elasticsearchOperations;
    private final RestHighLevelClient client;
    /**
     * 시간을 비교할때 Long으로 넘겨줘야함..
     * 기본적으로 dynamic finder가 long으로 비교한다고 한다.
     * pageRequest 설정안하면 기본 10개만 가져와서 편리함.
     * @return
     */
    @Override
    public SearchPageDto getRecentTop10Keywords() {
        // now
        LocalDateTime now = LocalDateTime.now();
        // 검색어로 집계하며 10순위까지만 뽑기
        TermsAggregationBuilder agg = AggregationBuilders
                .terms("keyword")
                .field("keyword.keyword")
                .size(10);
        // now_hour:00 ~ now 동안 검색어 집계
        QueryBuilder query = QueryBuilders.boolQuery()
                .must(matchQuery("message", "/performance/search"))
                .must(matchPhraseQuery("logger_name", "LOGSTASH"))
                .must(rangeQuery("@timestamp")
                        .gte(now.truncatedTo(ChronoUnit.HOURS).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .lte(now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        // 쿼리 전송
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(query).aggregation(agg);
        SearchRequest request = new SearchRequest().indices("logstash*").source(sourceBuilder);
        SearchResponse searchResponse;
        try {
            searchResponse = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new CustomException(ErrorCode.REAL_TIME_SEARCH_ERROR.getMessage(), ErrorCode.REAL_TIME_SEARCH_ERROR);
        }
        // 집계 필드로 넣었던 값.
        ParsedStringTerms keyword = (ParsedStringTerms) searchResponse.getAggregations().asMap().get("keyword");
        // DTO 변환
        List<RealTimeKeywordDto> realTimeKeywords = keyword.getBuckets().stream().map(RealTimeKeywordDto::bucketToDTO).collect(Collectors.toList());
        // base Time
        String basedTime = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm '기준'"));
        return SearchPageDto.builder().basedTime(basedTime).keywords(realTimeKeywords).build();
    }
}
