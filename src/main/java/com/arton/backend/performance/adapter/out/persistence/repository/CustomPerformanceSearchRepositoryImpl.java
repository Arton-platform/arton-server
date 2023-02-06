package com.arton.backend.performance.adapter.out.persistence.repository;

import com.arton.backend.performance.adapter.out.persistence.document.PerformanceDocument;
import com.arton.backend.performance.applicaiton.data.PerformanceSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class CustomPerformanceSearchRepositoryImpl implements CustomPerformanceSearchRepository{
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<PerformanceDocument> findByTitleContains(PerformanceSearchDto performanceSearchDto) {
        CriteriaQuery condition = createConditionCriteriaQuery(performanceSearchDto);
        return elasticsearchOperations.search(condition, PerformanceDocument.class).stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    private CriteriaQuery createConditionCriteriaQuery(PerformanceSearchDto searchCondition) {
        CriteriaQuery query = new CriteriaQuery(new Criteria());

        if (ObjectUtils.isEmpty(searchCondition))
            return query;

        if (hasText(searchCondition.getPerformanceType()))
            query.addCriteria(Criteria.where("performanceType").is(searchCondition.getPerformanceType()));

        if(hasText(searchCondition.getArtist()))
            query.addCriteria(Criteria.where("artist").is(searchCondition.getArtist()));

        if(hasText(searchCondition.getTitle()))
            query.addCriteria(Criteria.where("title").is(searchCondition.getTitle()));

        if(hasText(searchCondition.getPlace()))
            query.addCriteria(Criteria.where("place").is(searchCondition.getPlace()));
        return query;
    }
}
