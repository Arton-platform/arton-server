package com.arton.backend.performance.applicaiton.port.in;

import com.arton.backend.search.application.data.SearchResultDto;
import com.arton.backend.search.persistence.document.PerformanceDocument;

import java.util.List;

public interface PerformanceSearchUseCase {
    void saveAllDocuments();
    List<SearchResultDto> searchByTitle(String title);
    List<SearchResultDto> searchByPlace(String place);
    List<SearchResultDto> searchByPerformanceType(String type);
}
