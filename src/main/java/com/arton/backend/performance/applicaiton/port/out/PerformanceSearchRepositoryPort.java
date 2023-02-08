package com.arton.backend.performance.applicaiton.port.out;

import com.arton.backend.search.persistence.document.PerformanceDocument;

import java.util.List;

public interface PerformanceSearchRepositoryPort {
    /** 제목 검색 */
    List<PerformanceDocument> findByTitle(String title);
    /** 공연타입 검색 */
    List<PerformanceDocument> findByPerformanceType(String type);
    /** 장소 검색 */
    List<PerformanceDocument> findByPlace(String place);
    void saveAll(List<PerformanceDocument> performanceDocuments);
}
