package com.arton.backend.performance.applicaiton.port.in;

import com.arton.backend.search.application.data.SearchResultDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PerformanceSearchUseCase {
    void saveAllDocuments();
    Page<SearchResultDto> searchByTitle(String title, String sort, Pageable pageable);
    Page<SearchResultDto> searchByPlace(String place, String sort, Pageable pageable);
    Page<SearchResultDto> searchByPerformanceType(String type, String sort, Pageable pageable);
}
