package com.arton.backend.elastic.persistence.repository;

import com.arton.backend.elastic.persistence.document.PerformanceDocument;
import com.arton.backend.elastic.persistence.repository.CustomPerformanceSearchRepository;
import com.arton.backend.performance.domain.PerformanceType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * 실시간 검색 결과 제공을 위한 repository
 * JPA 와 동일하게 복잡한 쿼리는 Querydsl 같이 사용하면 된다.
 */
public interface PerformanceSearchRepository extends ElasticsearchRepository<PerformanceDocument, Long>, CustomPerformanceSearchRepository {
    /** 제목 검색 */
    List<PerformanceDocument> findByTitleContains(String title);
    /** 공연타입 검색 */
    List<PerformanceDocument> findByPerformanceType(PerformanceType performanceType);
    /** 장소 검색 */
    List<PerformanceDocument> findByPlaceContains(String place);
}
