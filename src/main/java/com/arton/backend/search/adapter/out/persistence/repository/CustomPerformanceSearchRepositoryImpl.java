package com.arton.backend.search.adapter.out.persistence.repository;

import com.arton.backend.administer.performance.application.data.PerformanceAdminSearchDto;
import com.arton.backend.performance.applicaiton.data.PerformanceSearchDto;
import com.arton.backend.performance.domain.PerformanceType;
import com.arton.backend.performance.domain.ShowCategory;
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
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.MultiMatchQueryBuilder.Type.BEST_FIELDS;
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
        setSortDESC(sort, searchQueryBuilder);
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
        setSortDESC(sort, searchQueryBuilder);
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
        setSortDESC(sort, searchQueryBuilder);
        NativeSearchQuery searchQuery = searchQueryBuilder.build();
        return SearchHitSupport.searchPageFor(elasticsearchOperations.search(searchQuery, PerformanceDocument.class, IndexCoordinates.of(IndexName.PERFORMANCE.getValue())), searchQuery.getPageable());
    }

    @Override
    public SearchPage<PerformanceDocument> findByKeyword(String keyword, String sort, Pageable pageable) {
        QueryBuilder query = boolQuery().should(multiMatchQuery(keyword,"performanceType.ngram", "performanceType", "title", "title.ngram", "place", "place.ngram")
                .type(BEST_FIELDS)
                .tieBreaker(0.3f));
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withQuery(query);
        searchQueryBuilder.withPageable(pageable);
        setSortDESC(sort, searchQueryBuilder);
        NativeSearchQuery searchQuery = searchQueryBuilder.build();
        return SearchHitSupport.searchPageFor(elasticsearchOperations.search(searchQuery, PerformanceDocument.class, IndexCoordinates.of(IndexName.PERFORMANCE.getValue())), searchQuery.getPageable());
    }

    @Override
    public SearchPage<PerformanceDocument> findByDtoInAdmin(PerformanceAdminSearchDto searchDto, Pageable pageable) {
        // 모든 키워드
        QueryBuilder query = boolQuery()
                .should(wildcardQuery("performanceType.ngram", "*"))
                .should(wildcardQuery("performanceType", "*"))
                .should(wildcardQuery("title.ngram", "*"))
                .should(wildcardQuery("title", "*"))
                .should(wildcardQuery("place", "*"))
                .should(wildcardQuery("place.ngram", "*"));

        QueryBuilder type = termsQuery("performanceType", Arrays.stream(PerformanceType.values()).map(PerformanceType::getValue).collect(Collectors.toList()));
        QueryBuilder category = termsQuery("showCategory", Arrays.stream(ShowCategory.values()).map(ShowCategory::getValue).collect(Collectors.toList()));
        if (!ObjectUtils.isEmpty(searchDto)) {
            if (StringUtils.hasText(searchDto.getKeyword())) {
                System.out.println("searchDto = " + searchDto.getKeyword());
                query = boolQuery().should(multiMatchQuery(searchDto.getKeyword(),"performanceType.ngram", "performanceType", "title", "title.ngram", "place", "place.ngram")
                        .type(BEST_FIELDS)
                        .tieBreaker(0.3f));
            }
            if (!ObjectUtils.isEmpty(searchDto.getPerformanceType())) {
                type = termQuery("performanceType", searchDto.getPerformanceType().getValue());
            }
            if (!ObjectUtils.isEmpty(searchDto.getShowCategory())) {
                category = termQuery("showCategory", searchDto.getShowCategory().getValue());
            }
        }

        QueryBuilder all = boolQuery().must(query).must(type).must(category);
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withQuery(all);
        searchQueryBuilder.withPageable(pageable);
        setSortASC("id", searchQueryBuilder);
        NativeSearchQuery searchQuery = searchQueryBuilder.build();
        return SearchHitSupport.searchPageFor(elasticsearchOperations.search(searchQuery, PerformanceDocument.class, IndexCoordinates.of(IndexName.PERFORMANCE.getValue())), searchQuery.getPageable());
    }

    @Override
    public List<PerformanceDocument> findByDtoInAdminWithoutPaging(PerformanceAdminSearchDto searchDto) {
        // 모든 키워드
        QueryBuilder query = boolQuery()
                .should(wildcardQuery("performanceType.ngram", "*"))
                .should(wildcardQuery("performanceType", "*"))
                .should(wildcardQuery("title.ngram", "*"))
                .should(wildcardQuery("title", "*"))
                .should(wildcardQuery("place", "*"))
                .should(wildcardQuery("place.ngram", "*"));

        QueryBuilder type = termsQuery("performanceType", Arrays.stream(PerformanceType.values()).map(PerformanceType::getValue).collect(Collectors.toList()));
        QueryBuilder category = termsQuery("showCategory", Arrays.stream(ShowCategory.values()).map(ShowCategory::getValue).collect(Collectors.toList()));
        if (!ObjectUtils.isEmpty(searchDto)) {
            if (StringUtils.hasText(searchDto.getKeyword())) {
                query = boolQuery().should(multiMatchQuery(searchDto.getKeyword(),"performanceType.ngram", "performanceType", "title", "title.ngram", "place", "place.ngram")
                        .type(BEST_FIELDS)
                        .tieBreaker(0.3f));
            }
            if (!ObjectUtils.isEmpty(searchDto.getPerformanceType())) {
                type = termQuery("performanceType", searchDto.getPerformanceType().getValue());
            }
            if (!ObjectUtils.isEmpty(searchDto.getShowCategory())) {
                category = termQuery("showCategory", searchDto.getShowCategory().getValue());
            }
        }

        QueryBuilder all = boolQuery().must(query).must(type).must(category);
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withQuery(all);
        setSortASC("id", searchQueryBuilder);
        NativeSearchQuery searchQuery = searchQueryBuilder.build();
        return elasticsearchOperations.search(searchQuery, PerformanceDocument.class, IndexCoordinates.of(IndexName.PERFORMANCE.getValue())).stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    /**
     * sort 옵션을 DESC 지정해준다.
     * @param sort
     * @param searchQueryBuilder
     */
    private void setSortDESC(String sort, NativeSearchQueryBuilder searchQueryBuilder) {
        if (StringUtils.hasText(sort)) {
            SortField sortField = SortField.get(sort);
            if (!ObjectUtils.isEmpty(sortField)) { // 유효한 정렬 조건이면 정렬해주기
                searchQueryBuilder.withSorts(SortBuilders.fieldSort(sort).order(SortOrder.DESC));
            }
        }
    }

    /**
     * sort 옵션을 ASC 지정해준다.
     * @param sort
     * @param searchQueryBuilder
     */
    private void setSortASC(String sort, NativeSearchQueryBuilder searchQueryBuilder) {
        if (StringUtils.hasText(sort)) {
            SortField sortField = SortField.get(sort);
            if (!ObjectUtils.isEmpty(sortField)) { // 유효한 정렬 조건이면 정렬해주기
                searchQueryBuilder.withSorts(SortBuilders.fieldSort(sort).order(SortOrder.ASC));
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
