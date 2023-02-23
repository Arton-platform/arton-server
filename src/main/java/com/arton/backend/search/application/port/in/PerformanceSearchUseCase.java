package com.arton.backend.search.application.port.in;

import com.arton.backend.administer.performance.application.data.PerformanceAdminSearchDto;
import com.arton.backend.search.application.data.SearchResultDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PerformanceSearchUseCase {
    void saveAllDocuments();
    Page<SearchResultDto> searchByTitle(String title, String sort, Pageable pageable);
    Page<SearchResultDto> searchByPlace(String place, String sort, Pageable pageable);
    Page<SearchResultDto> searchByPerformanceType(String type, String sort, Pageable pageable);
    Page<SearchResultDto> searchAll(String type, String sort, Pageable pageable);
    Page<SearchResultDto> searchInAdmin(PerformanceAdminSearchDto searchDto, Pageable pageable);
    Page<SearchResultDto> findAll(Pageable pageable);
}
