package com.arton.backend.performance.applicaiton.port.in;

import com.arton.backend.elastic.persistence.document.PerformanceDocument;
import com.arton.backend.performance.applicaiton.data.PerformanceSearchDto;

import java.util.List;

public interface PerformanceSearchUseCase {
    void saveAllDocuments();
    List<PerformanceDocument> searchByTitle(String title);
    List<PerformanceDocument> searchByPlace(String place);
    List<PerformanceDocument> searchByPerformanceType(String type);
    List<PerformanceDocument> searchByCondition(PerformanceSearchDto performanceSearchDto);
}
