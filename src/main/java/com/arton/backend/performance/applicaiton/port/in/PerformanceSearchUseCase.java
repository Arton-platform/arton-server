package com.arton.backend.performance.applicaiton.port.in;

import com.arton.backend.search.application.data.SearchResultDto;

import java.util.List;

public interface PerformanceSearchUseCase {
    void saveAllDocuments();
    List<SearchResultDto> searchByTitle(String title);
    List<SearchResultDto> searchByPlace(String place, String sort);
    List<SearchResultDto> searchByPerformanceType(String type);
}
