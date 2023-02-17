package com.arton.backend.performance.applicaiton.service;

import com.arton.backend.image.application.port.out.PerformanceImageRepositoryPort;
import com.arton.backend.image.domain.PerformanceImage;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.performance.adapter.out.persistence.mapper.PerformanceMapper;
import com.arton.backend.performance.applicaiton.data.PerformanceDetailDto;
import com.arton.backend.performance.applicaiton.data.PerformanceInterestDto;
import com.arton.backend.performance.applicaiton.port.in.PerformanceDeleteUseCase;
import com.arton.backend.performance.applicaiton.port.in.PerformanceSaveUseCase;
import com.arton.backend.performance.applicaiton.port.in.PerformanceSearchUseCase;
import com.arton.backend.performance.applicaiton.port.in.PerformanceUseCase;
import com.arton.backend.performance.applicaiton.port.out.PerformanceDeletePort;
import com.arton.backend.performance.applicaiton.port.out.PerformanceRepositoryPort;
import com.arton.backend.performance.applicaiton.port.out.PerformanceSavePort;
import com.arton.backend.performance.applicaiton.port.out.PerformanceSearchRepositoryPort;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.price.application.data.PriceInfoDto;
import com.arton.backend.price.application.port.out.PriceGradeRepositoryPort;
import com.arton.backend.search.adapter.out.persistence.document.PerformanceDocument;
import com.arton.backend.search.application.data.SearchResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PerformanceService implements PerformanceUseCase, PerformanceSearchUseCase, PerformanceSaveUseCase, PerformanceDeleteUseCase {
    private final PerformanceRepositoryPort performanceRepositoryPort;
    private final PerformanceSearchRepositoryPort performanceSearchRepository;
    private final PerformanceSavePort performanceSavePort;
    private final PerformanceDeletePort performanceDeletePort;
    private final PriceGradeRepositoryPort priceGradeRepositoryPort;
    private final PerformanceImageRepositoryPort performanceImageRepositoryPort;


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
        return performanceSavePort.save(performance);
    }

    @Override
    public void deletePerformance(Performance performance) {
        performanceDeletePort.deletePerformance(performance);
    }

    @Override
    public void deletePerformance(Long id) {
        performanceDeletePort.deleteById(id);
    }

    @Override
    public PerformanceDetailDto getOne(Long id) {
        Performance performance = performanceRepositoryPort.findById(id).orElseThrow(() -> new CustomException(ErrorCode.PERFORMANCE_NOT_FOUND.getMessage(), ErrorCode.PERFORMANCE_NOT_FOUND));
        List<String> images = performanceImageRepositoryPort.findByPerformanceId(id).stream().map(PerformanceImage::getImageUrl).collect(Collectors.toList());
        List<PriceInfoDto> priceInfo = priceGradeRepositoryPort.findByPerformanceId(id).stream().map(PriceInfoDto::domainToDto).collect(Collectors.toList());
        return PerformanceDetailDto.toDto(performance, images, priceInfo);
    }

    public void saveAllDocuments() {
        List<PerformanceDocument> documents = performanceRepositoryPort.findAll().stream().map(PerformanceMapper::domainToDocument).collect(Collectors.toList());
        performanceSearchRepository.saveAll(documents);
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
}
