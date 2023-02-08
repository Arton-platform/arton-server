package com.arton.backend.elastic.persistence.repository;

import com.arton.backend.elastic.persistence.document.PerformanceDocument;

import java.util.List;

/**
 * 실시간 검색 결과 제공을 위한 repository
 * JPA 와 동일하게 복잡한 쿼리는 Querydsl 같이 사용하면 된다.
 */
public interface CustomPerformanceSearchRepository {
    List<PerformanceDocument> findByPlace(String place);
    List<PerformanceDocument> findByTitle(String title);
    List<PerformanceDocument> findByType(String type);
}
