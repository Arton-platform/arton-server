package com.arton.backend.search.adapter.out.persistence.repository;

import com.arton.backend.search.adapter.out.persistence.document.AccessLogDocument;
import com.arton.backend.search.application.data.RealTimeKeywordDto;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.search.application.data.RealTimeKeywordDtoV2;
import com.arton.backend.search.application.data.SearchPageDto;
import com.arton.backend.search.application.data.SearchPageDtoV2;
import com.arton.backend.search.domain.IndexName;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.range.DateRangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.ParsedDateRange;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
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

    /**
     * (현재시간~현재 시간:0분:0초)/(현재 시간:0분:0초 ~ 과거) 증가율이 높은순으로 반환.
     * 검색어로 집계하며 10순위까지만 뽑기
     * @return
     */
    @Override
    public SearchPageDtoV2 getAdvancedTop10Keywords() {
        LocalDateTime now = LocalDateTime.now();
        // 현재 시간 0분 0초 ~ 현재 카운트
        DateRangeAggregationBuilder agg = AggregationBuilders
                .dateRange("time-count").field("@timestamp")
                .addUnboundedTo("parent", now.truncatedTo(ChronoUnit.HOURS).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .addRange("child", now.truncatedTo(ChronoUnit.HOURS).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(), now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .subAggregation(AggregationBuilders.terms("keyword").field("keyword.keyword").size(10));

        QueryBuilder query = QueryBuilders.boolQuery()
                .must(matchQuery("message", "/performance/search"))
                .must(matchPhraseQuery("logger_name", "LOGSTASH"));
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
        ParsedDateRange timeCount = (ParsedDateRange) searchResponse.getAggregations().asMap().get("time-count");
        // 집계 2개 가져오기
        Map<Object, Aggregations> collect = timeCount.getBuckets().stream().collect(Collectors.toMap(MultiBucketsAggregation.Bucket::getKeyAsString, Range.Bucket::getAggregations));
        // 과거~현재시간0분0초까지 집계
        ParsedStringTerms keyword = (ParsedStringTerms) collect.get("parent").asMap().get("keyword");
        List<RealTimeKeywordDto> realTimeKeywords = keyword.getBuckets().stream().map(RealTimeKeywordDto::bucketToDTO).collect(Collectors.toList());

        // 현재시간0분0초~현재시간 집계
        ParsedStringTerms childKeyword = (ParsedStringTerms) collect.get("child").asMap().get("keyword");
        List<RealTimeKeywordDto> realTimeKeywordsChild = childKeyword.getBuckets().stream().map(RealTimeKeywordDto::bucketToDTO).collect(Collectors.toList());

        // map
        Map<String, Double> map = new HashMap<>();
        for (RealTimeKeywordDto realTimeKeywordDto : realTimeKeywordsChild) {
            map.put(realTimeKeywordDto.getKeyword(), realTimeKeywordDto.getCount().doubleValue());
        }
        // divide
        for (RealTimeKeywordDto realTimeKeyword : realTimeKeywords) {
            if (map.containsKey(realTimeKeyword.getKeyword()))
                map.put(realTimeKeyword.getKeyword(), map.get(realTimeKeyword.getKeyword()) / realTimeKeyword.getCount());
        }
        // to DTO
        List<RealTimeKeywordDtoV2> result = new ArrayList<>();
        for (String s : map.keySet()) {
            result.add(RealTimeKeywordDtoV2.builder().keyword(s).score(map.get(s)).build());
        }
        Collections.sort(result,
                Comparator.comparing(RealTimeKeywordDtoV2::getScore).reversed());
        int cnt = 1;
        for (RealTimeKeywordDtoV2 realTimeKeywordDtoV2 : result) {
            realTimeKeywordDtoV2.setRank(cnt++);
        }
        // base Time
        String basedTime = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm '기준'"));
        return SearchPageDtoV2.builder().basedTime(basedTime).keywords(result).build();
    }

    @Override
    public void deleteKeyword(String keyword) {
        LocalDateTime now = LocalDateTime.now();
        // 현재 시간 0분 0초 ~ 현재 카운트
        QueryBuilder range = QueryBuilders.rangeQuery("@timestamp").gte(now.truncatedTo(ChronoUnit.HOURS).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .lt(now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

        QueryBuilder query = QueryBuilders.boolQuery()
                .must(matchQuery("message", "/performance/search"))
                .must(matchPhraseQuery("logger_name", "LOGSTASH"))
                .must(termQuery("keyword", keyword))
                .filter(range);
        // 쿼리 전송
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withQuery(query);
        NativeSearchQuery deleteQuery = searchQueryBuilder.build();
        elasticsearchOperations.delete(deleteQuery, AccessLogDocument.class, IndexCoordinates.of(IndexName.LOG.getValue()));
    }
}
