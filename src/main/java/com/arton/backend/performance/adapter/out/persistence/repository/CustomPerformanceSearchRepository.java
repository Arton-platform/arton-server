package com.arton.backend.performance.adapter.out.persistence.repository;

import com.arton.backend.performance.adapter.out.persistence.document.PerformanceDocument;
import com.arton.backend.performance.applicaiton.data.PerformanceSearchDto;

import java.util.List;

/**
 * 실시간 검색 결과 제공을 위한 repository
 * JPA 와 동일하게 복잡한 쿼리는 Querydsl 같이 사용하면 된다.
 */
public interface CustomPerformanceSearchRepository {
    /** 제목 검색 */
    List<PerformanceDocument> findByTitleContains(PerformanceSearchDto performanceSearchDto);
}
