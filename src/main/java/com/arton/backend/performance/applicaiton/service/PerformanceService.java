package com.arton.backend.performance.applicaiton.service;

import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.performance.adapter.out.persistence.mapper.PerformanceMapper;
import com.arton.backend.performance.applicaiton.data.PerformanceInterestDto;
import com.arton.backend.performance.applicaiton.port.in.PerformanceSearchUseCase;
import com.arton.backend.performance.applicaiton.port.in.PerformanceUseCase;
import com.arton.backend.performance.applicaiton.port.out.PerformanceRepositoryPort;
import com.arton.backend.performance.applicaiton.port.out.PerformanceSearchRepositoryPort;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.search.adapter.out.persistence.document.PerformanceDocument;
import com.arton.backend.search.application.data.SearchResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PerformanceService implements PerformanceUseCase, PerformanceSearchUseCase {
    private final PerformanceRepositoryPort performanceRepositoryPort;
    private final PerformanceMapper performanceMapper;
    private final PerformanceSearchRepositoryPort performanceSearchRepository;

    @Override
    public List<Performance> getAllPerformances() {
        return performanceRepositoryPort.findAllPerformances();
    }

    @Override
    public List<PerformanceInterestDto> getZzimList() {
        return performanceRepositoryPort.findAllPerformances().stream().map(PerformanceInterestDto::of).collect(Collectors.toList());
    }

    @Override
    public List<Performance> getMusicals() {
        return performanceRepositoryPort.findAllMusicals();
    }

    @Override
    public List<Performance> getConcerts() {
        return performanceRepositoryPort.findAllConcerts();
    }

    @Override
    public Performance save(Performance performance) {
        return performanceRepositoryPort.save(performance);
    }

    @Override
    public void deletePerformance(Performance performance) {
        performanceRepositoryPort.deletePerformance(performance);
    }

    @Override
    public void deletePerformance(Long id) {
        performanceRepositoryPort.deleteById(id);
    }

    @Override
    public Performance getOne(Long id) {
        return performanceRepositoryPort.findById(id)
                .map(performanceEntity -> performanceMapper.toDomain(performanceEntity))
                .orElseThrow(() -> new CustomException("데이터가 없습니다.", ErrorCode.SELECT_ERROR));
    }

    public void saveAllDocuments() {
        List<PerformanceDocument> documents = performanceRepositoryPort.findAll().stream().map(PerformanceMapper::domainToDocument).collect(Collectors.toList());
        performanceSearchRepository.saveAll(documents);
    }

    public List<SearchResultDto> searchByTitle(String title, String sort) {
        return Optional.ofNullable(performanceSearchRepository.findByTitle(title, sort))
                .orElseGet(Collections::emptyList)
                .stream()
                .map(SearchResultDto::toResultFromDocument)
                .collect(Collectors.toList());
    }

    public Page<SearchResultDto> searchByPlace(String place, String sort, Pageable pageable) {
        // DTO 변환
        return performanceSearchRepository.findByPlace(place, sort, pageable).map(search -> SearchResultDto.toResultFromDocument(search.getContent()));
//        return Optional.ofNullable(performanceSearchRepository.findByPlace(place, sort, pageable))
//                .orElseGet(Collections::emptyList)
//                .stream()
//                .map(SearchResultDto::toResultFromDocument)
//                .collect(Collectors.toList());
    }

    public List<SearchResultDto> searchByPerformanceType(String type, String sort) {
        return Optional.ofNullable(performanceSearchRepository.findByPerformanceType(type, sort))
                .orElseGet(Collections::emptyList)
                .stream()
                .map(SearchResultDto::toResultFromDocument)
                .collect(Collectors.toList());
    }
}
