package com.arton.backend.search.application.service;

import com.arton.backend.administer.performance.application.data.PerformanceAdminSearchDto;
import com.arton.backend.performance.applicaiton.port.out.PerformanceRepositoryPort;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.search.application.data.SearchResultDto;
import com.arton.backend.search.application.port.in.PerformanceSearchUseCase;
import com.arton.backend.search.application.port.out.PerformanceDocuemntSavePort;
import com.arton.backend.search.application.port.out.PerformanceDocumentSearchPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PerformanceSearchService implements PerformanceSearchUseCase {
    private final PerformanceRepositoryPort performanceRepositoryPort;
    private final PerformanceDocuemntSavePort performanceDocuemntSavePort;
    private final PerformanceDocumentSearchPort performanceSearchRepository;

    public void saveAllDocuments() {
        List<Performance> performances = performanceRepositoryPort.findAll();
        performanceDocuemntSavePort.saveAll(performances);
    }

    @Override
    public Page<SearchResultDto> searchByTitle(String title, String sort, Pageable pageable) {
        return performanceSearchRepository.findByTitle(title, sort, pageable).map(search -> SearchResultDto.toResultFromDocument(search.getContent()));
    }

    @Override
    public Page<SearchResultDto> searchByPlace(String place, String sort, Pageable pageable) {
        // DTO 변환
        return performanceSearchRepository.findByPlace(place, sort, pageable).map(search -> SearchResultDto.toResultFromDocument(search.getContent()));
    }

    @Override
    public Page<SearchResultDto> searchByPerformanceType(String type, String sort, Pageable pageable) {
        return performanceSearchRepository.findByPerformanceType(type, sort, pageable).map(search -> SearchResultDto.toResultFromDocument(search.getContent()));
    }

    @Override
    public Page<SearchResultDto> searchAll(String type, String sort, Pageable pageable) {
        return performanceSearchRepository.findByKeyword(type, sort, pageable).map(search -> SearchResultDto.toResultFromDocument(search.getContent()));
    }

    @Override
    public Page<SearchResultDto> searchInAdmin(PerformanceAdminSearchDto searchDto, Pageable pageable) {
        return performanceSearchRepository.findByDtoInAdmin(searchDto, pageable).map(search -> SearchResultDto.toResultFromDocument(search.getContent()));
    }

    @Override
    public Page<SearchResultDto> findAll(Pageable pageable) {
        return performanceSearchRepository.findAll(pageable).map(SearchResultDto::toResultFromDocument);
    }
}
