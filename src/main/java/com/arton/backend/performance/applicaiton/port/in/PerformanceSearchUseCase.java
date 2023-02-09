package com.arton.backend.performance.applicaiton.port.in;

import com.arton.backend.search.application.data.SearchResultDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PerformanceSearchUseCase {
    void saveAllDocuments();
    List<SearchResultDto> searchByTitle(String title, String sort);
    Page<SearchResultDto> searchByPlace(String place, String sort, Pageable pageable);
    List<SearchResultDto> searchByPerformanceType(String type, String sort);
}
