package com.arton.backend.search.application.port.out;

import com.arton.backend.administer.performance.application.data.PerformanceAdminSearchDto;
import com.arton.backend.search.adapter.out.persistence.document.PerformanceDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;

public interface PerformanceDocumentSearchPort {
    /** 제목 검색 */
    SearchPage<PerformanceDocument> findByTitle(String title, String sort, Pageable pageable);
    /** 공연타입 검색 */
    SearchPage<PerformanceDocument> findByPerformanceType(String type, String sort, Pageable pageable);
    /** 장소 검색 */
    SearchPage<PerformanceDocument> findByPlace(String place, String sort, Pageable pageable);
    /** Multimatch 검색 */
    SearchPage<PerformanceDocument> findByKeyword(String keyword, String sort, Pageable pageable);
    /** 관리자 페이지 검색용*/
    SearchPage<PerformanceDocument> findByDtoInAdmin(PerformanceAdminSearchDto searchDto, Pageable pageable);
}
