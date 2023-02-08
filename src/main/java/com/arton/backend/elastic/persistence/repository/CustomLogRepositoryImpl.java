package com.arton.backend.elastic.persistence.repository;

import com.arton.backend.elastic.persistence.document.AccessLogDocument;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

import java.io.IOException;
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
    private final RestHighLevelClient client;
    /**
     * 시간을 비교할때 Long으로 넘겨줘야함..
     * 기본적으로 dynamic finder가 long으로 비교한다고 한다.
     * pageRequest 설정안하면 기본 10개만 가져와서 편리함.
     * @return
     */
    @Override
    public List<AccessLogDocument> getRecentTop10Keywords() {
        // 검색어로 집계하며 10순위까지만 뽑기
        TermsAggregationBuilder agg = AggregationBuilders
                .terms("keyword")
                .field("keyword.keyword")
                .size(10);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery()
                        .must(matchQuery("message", "/performance/search")) // 어느정도 일치만 하면 됨.
                        .must(matchPhraseQuery("logger_name", "LOGSTASH")) // 정확하게 일치
                        .must(rangeQuery("@timestamp")
                                .gte(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                                .lte(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())))
                .withAggregations(agg)
                .build();

        QueryBuilder query = QueryBuilders.boolQuery()
                .must(matchQuery("message", "/performance/search"))
                .must(matchPhraseQuery("logger_name", "LOGSTASH"))
                .must(rangeQuery("@timestamp")
                        .gte(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .lte(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(query).aggregation(agg);
        SearchRequest request = new SearchRequest().indices("logstash*").source(sourceBuilder);
        SearchResponse searchResponse;
        try {
            searchResponse = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new CustomException(ErrorCode.REAL_TIME_SEARCH_ERROR.getMessage(), ErrorCode.REAL_TIME_SEARCH_ERROR);
        }

        /**
         * nal TermsAggregationBuilder aggregation = AggregationBuilders.terms("top_tags")
         *             .field("tags")
         *             .order(BucketOrder.count(false));
         *
         *         final SearchSourceBuilder builder = new SearchSourceBuilder().aggregation(aggregation);
         *         final SearchRequest searchRequest = new SearchRequest().indices("blog")
         *             .source(builder);
         *
         *         final SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
         *
         *         final Map<String, Aggregation> results = response.getAggregations()
         *             .asMap();
         *         final ParsedStringTerms topTags = (ParsedStringTerms) results.get("top_tags");
         *
         *         final List<String> keys = topTags.getBuckets()
         *             .stream()
         *             .map(MultiBucketsAggregation.Bucket::getKeyAsString)
         *             .collect(toList());
         *         assertEquals(asList("elasticsearch", "spring data", "search engines", "tutorial"), keys);
         */

        List<AccessLogDocument> documents = elasticsearchOperations
                . search(searchQuery, AccessLogDocument.class, IndexCoordinates.of("logstash*")).stream().map(SearchHit::getContent).collect(Collectors.toList());
        return documents;
    }
}
