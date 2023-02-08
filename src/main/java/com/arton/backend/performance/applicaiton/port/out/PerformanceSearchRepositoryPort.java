package com.arton.backend.performance.applicaiton.port.out;

import com.arton.backend.elastic.persistence.document.PerformanceDocument;
import com.arton.backend.performance.applicaiton.data.PerformanceSearchDto;
import com.arton.backend.performance.domain.PerformanceType;

import java.util.List;

public interface PerformanceSearchRepositoryPort {
    /** 제목 검색 */
    List<PerformanceDocument> findByTitle(String title);
    /** 공연타입 검색 */
    List<PerformanceDocument> findByPerformanceType(PerformanceType performanceType);
    /** 장소 검색 */
    List<PerformanceDocument> findByPlace(String place);
    void saveAll(List<PerformanceDocument> performanceDocuments);
}
