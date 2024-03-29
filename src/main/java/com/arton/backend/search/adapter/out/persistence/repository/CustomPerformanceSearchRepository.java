package com.arton.backend.search.adapter.out.persistence.repository;

import com.arton.backend.administer.performance.application.data.PerformanceAdminSearchDto;
import com.arton.backend.search.adapter.out.persistence.document.PerformanceDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;

import java.util.List;

/**
 * 실시간 검색 결과 제공을 위한 repository
 * JPA 와 동일하게 복잡한 쿼리는 Querydsl 같이 사용하면 된다.
 */
public interface CustomPerformanceSearchRepository {
    SearchPage<PerformanceDocument> findByPlace(String place, String sort, Pageable pageable);
    SearchPage<PerformanceDocument> findByTitle(String title, String sort, Pageable pageable);
    SearchPage<PerformanceDocument> findByType(String type, String sort, Pageable pageable);
    SearchPage<PerformanceDocument> findByKeyword(String keyword, String sort, Pageable pageable);
    SearchPage<PerformanceDocument> findByDtoInAdmin(PerformanceAdminSearchDto searchDto, Pageable pageable);
    List<PerformanceDocument> findByDtoInAdminWithoutPaging(PerformanceAdminSearchDto searchDto);
}
