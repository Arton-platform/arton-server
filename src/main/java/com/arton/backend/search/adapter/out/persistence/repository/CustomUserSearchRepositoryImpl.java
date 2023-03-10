package com.arton.backend.search.adapter.out.persistence.repository;

import com.arton.backend.administer.mail.application.data.AdminMailSearchDto;
import com.arton.backend.search.adapter.out.persistence.document.UserDocument;
import com.arton.backend.search.domain.IndexName;
import com.arton.backend.search.domain.SortField;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.elasticsearch.index.query.MultiMatchQueryBuilder.Type.BEST_FIELDS;
import static org.elasticsearch.index.query.QueryBuilders.*;

@Repository
@RequiredArgsConstructor
public class CustomUserSearchRepositoryImpl implements CustomUserSearchRepository {
    private final ElasticsearchOperations elasticsearchOperations;

    /**
     * 단체 메일 발송 검색 조건에 따라 리스트 획득
     * @param searchDto
     * @param pageable
     * @return
     */
    @Override
    public SearchPage<UserDocument> searchUserForMailing(AdminMailSearchDto searchDto, Pageable pageable) {
        // init all search condition
        QueryBuilder query = boolQuery()
                .should(wildcardQuery("nickname.ngram", "*"))
                .should(wildcardQuery("nickname", "*"))
                .should(wildcardQuery("email", "*"));

        QueryBuilder termsAgree = wildcardQuery("termsAgree","*");

        if (!ObjectUtils.isEmpty(searchDto)) {
            if (StringUtils.hasText(searchDto.getKeyword())) {
                query = boolQuery().should(multiMatchQuery(searchDto.getKeyword(),"nickname.ngram", "nickname")
                        .type(BEST_FIELDS)
                        .tieBreaker(0.3f))
                        .should(termQuery("email", searchDto.getKeyword()))
                        .minimumShouldMatch(1);
            }
            if (!ObjectUtils.isEmpty(searchDto.getTermsAgree())) {
                termsAgree = termQuery("termsAgree", searchDto.getTermsAgree());
            }
        }

        QueryBuilder all = boolQuery().must(query).must(termsAgree);
        QueryBuilder filter = getDateFilter("createdDate", searchDto.getGteDate(), searchDto.getLtDate());
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withQuery(all).withFilter(filter);
        searchQueryBuilder.withPageable(pageable);
        setSortASC("id", searchQueryBuilder);
        NativeSearchQuery searchQuery = searchQueryBuilder.build();
        return SearchHitSupport.searchPageFor(elasticsearchOperations.search(searchQuery, UserDocument.class, IndexCoordinates.of(IndexName.USER.getValue())), searchQuery.getPageable());
    }

    /**
     * 날짜 필터 획득
     * @param field
     * @param gte
     * @param lt
     * @return
     */
    private QueryBuilder getDateFilter(String field, LocalDate gte, LocalDate lt) {
        if (!ObjectUtils.isEmpty(gte) && !ObjectUtils.isEmpty(lt)) {
            return rangeQuery(field).gte(gte).lt(lt);
        } else if (!ObjectUtils.isEmpty(gte) && ObjectUtils.isEmpty(lt)) {
            return rangeQuery(field).gte(gte);
        } else if (ObjectUtils.isEmpty(gte) && !ObjectUtils.isEmpty(lt)) {
            return rangeQuery(field).lt(lt);
        } else {
            return rangeQuery(field).lte(LocalDateTime.now());
        }
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
}
