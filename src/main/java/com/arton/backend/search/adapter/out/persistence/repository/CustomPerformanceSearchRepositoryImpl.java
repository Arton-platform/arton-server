package com.arton.backend.search.adapter.out.persistence.repository;

import com.arton.backend.search.adapter.out.persistence.document.PerformanceDocument;
import com.arton.backend.performance.applicaiton.data.PerformanceSearchDto;
import com.arton.backend.search.domain.SortField;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class CustomPerformanceSearchRepositoryImpl implements CustomPerformanceSearchRepository {
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<PerformanceDocument> findByPlace(String place, String sort) {
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withQuery(matchQuery("place", place));
        if (StringUtils.hasText(sort)) {
            SortField sortField = SortField.get(sort);
            if (!ObjectUtils.isEmpty(sortField)) { // 유효한 정렬 조건이면 정렬해주기
                searchQueryBuilder.withSorts(SortBuilders.fieldSort(sort).order(SortOrder.DESC));
            }
        }
        NativeSearchQuery searchQuery = searchQueryBuilder.build();
        List<PerformanceDocument> documents = elasticsearchOperations
                . search(searchQuery, PerformanceDocument.class, IndexCoordinates.of("performance*")).stream().map(SearchHit::getContent).collect(Collectors.toList());
        return documents;
    }

    @Override
    public List<PerformanceDocument> findByTitle(String title) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("title", title))
                .build();
        List<PerformanceDocument> documents = elasticsearchOperations
                . search(searchQuery, PerformanceDocument.class, IndexCoordinates.of("performance*")).stream().map(SearchHit::getContent).collect(Collectors.toList());
        return documents;
    }

    @Override
    public List<PerformanceDocument> findByType(String type) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("performanceType", type))
                .build();
        List<PerformanceDocument> documents = elasticsearchOperations
                . search(searchQuery, PerformanceDocument.class, IndexCoordinates.of("performance*")).stream().map(SearchHit::getContent).collect(Collectors.toList());
        return documents;
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