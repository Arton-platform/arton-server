package com.arton.backend.search.adapter.out.persistence.repository;

import com.arton.backend.performance.applicaiton.data.PerformanceSearchDto;
import com.arton.backend.search.adapter.out.persistence.document.PerformanceDocument;
import com.arton.backend.search.domain.IndexName;
import com.arton.backend.search.domain.SortField;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class CustomPerformanceSearchRepositoryImpl implements CustomPerformanceSearchRepository {
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public SearchPage<PerformanceDocument> findByPlace(String place, String sort, Pageable pageable) {
        QueryBuilder query = boolQuery()
                .should(termQuery("place", place))
                .should(matchPhraseQuery("place.ngram", place))
                .minimumShouldMatch(1);
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withQuery(query);
        searchQueryBuilder.withPageable(pageable);
        setSort(sort, searchQueryBuilder);
        NativeSearchQuery searchQuery = searchQueryBuilder.build();
        return SearchHitSupport.searchPageFor(elasticsearchOperations.search(searchQuery, PerformanceDocument.class, IndexCoordinates.of(IndexName.PERFORMANCE.getValue())), searchQuery.getPageable());
    }

    @Override
    public SearchPage<PerformanceDocument> findByTitle(String title, String sort, Pageable pageable) {
        // 내부 자세한 쿼리
        QueryBuilder query = QueryBuilders.boolQuery()
                .should(termQuery("title", title))
                .should(matchPhraseQuery("title.ngram", title))
                .minimumShouldMatch(1);
        // 쿼리에 넣기
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withQuery(query);
        searchQueryBuilder.withPageable(pageable);
        setSort(sort, searchQueryBuilder);
        NativeSearchQuery searchQuery = searchQueryBuilder.build();
        return SearchHitSupport.searchPageFor(elasticsearchOperations.search(searchQuery, PerformanceDocument.class, IndexCoordinates.of(IndexName.PERFORMANCE.getValue())), searchQuery.getPageable());
    }

    @Override
    public SearchPage<PerformanceDocument> findByType(String type, String sort, Pageable pageable) {
        QueryBuilder query = boolQuery()
                .should(termQuery("performanceType", type))
                .should(matchPhraseQuery("performanceType.ngram", type))
                .minimumShouldMatch(1);
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withQuery(query);
        searchQueryBuilder.withPageable(pageable);
        setSort(sort, searchQueryBuilder);
        NativeSearchQuery searchQuery = searchQueryBuilder.build();
        return SearchHitSupport.searchPageFor(elasticsearchOperations.search(searchQuery, PerformanceDocument.class, IndexCoordinates.of(IndexName.PERFORMANCE.getValue())), searchQuery.getPageable());
    }

    /**
     * sort 옵션을 지정해준다.
     * @param sort
     * @param searchQueryBuilder
     */
    private void setSort(String sort, NativeSearchQueryBuilder searchQueryBuilder) {
        if (StringUtils.hasText(sort)) {
            SortField sortField = SortField.get(sort);
            if (!ObjectUtils.isEmpty(sortField)) { // 유효한 정렬 조건이면 정렬해주기
                searchQueryBuilder.withSorts(SortBuilders.fieldSort(sort).order(SortOrder.DESC));
            }
        }
    }

    private CriteriaQuery createConditionCriteriaQuery(PerformanceSearchDto searchCondition) {
        CriteriaQuery query = new CriteriaQuery(new Criteria());

        if (ObjectUtils.isEmpty(searchCondition))
            return query;

        if (hasText(searchCondition.getPerformanceType()))
            query.addCriteria(Criteria.where("performanceType").contains(searchCondition.getPerformanceType()));

        if(hasText(searchCondition.getArtist()))
            query.addCriteria(Criteria.where("artist").contains(searchCondition.getArtist()));

        if(hasText(searchCondition.getTitle()))
            query.addCriteria(Criteria.where("title").contains(searchCondition.getTitle()));

        if(hasText(searchCondition.getPlace()))
            query.addCriteria(Criteria.where("place").contains(searchCondition.getPlace()));
        return query;
    }
}
