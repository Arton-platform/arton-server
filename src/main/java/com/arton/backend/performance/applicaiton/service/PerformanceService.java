package com.arton.backend.performance.applicaiton.service;

import com.arton.backend.image.application.port.out.PerformanceImageRepositoryPort;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.performance.applicaiton.data.ImageDto;
import com.arton.backend.performance.applicaiton.data.PerformanceDetailDto;
import com.arton.backend.performance.applicaiton.data.PerformanceDetailDtoV2;
import com.arton.backend.performance.applicaiton.data.PerformanceInterestDto;
import com.arton.backend.performance.applicaiton.port.in.PerformanceDeleteUseCase;
import com.arton.backend.performance.applicaiton.port.in.PerformanceSaveUseCase;
import com.arton.backend.performance.applicaiton.port.in.PerformanceUseCase;
import com.arton.backend.performance.applicaiton.port.out.PerformanceDeletePort;
import com.arton.backend.performance.applicaiton.port.out.PerformanceRepositoryPort;
import com.arton.backend.performance.applicaiton.port.out.PerformanceSavePort;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.price.application.data.PriceInfoDto;
import com.arton.backend.price.application.port.out.PriceGradeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PerformanceService implements PerformanceUseCase, PerformanceSaveUseCase, PerformanceDeleteUseCase {
    private final PerformanceRepositoryPort performanceRepositoryPort;
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
        List<ImageDto> images = performanceImageRepositoryPort.findByPerformanceId(id).stream().map(ImageDto::domainToDto).collect(Collectors.toList());
        List<PriceInfoDto> priceInfo = priceGradeRepositoryPort.findByPerformanceId(id).stream().map(PriceInfoDto::domainToDto).collect(Collectors.toList());
        return PerformanceDetailDto.toDto(performance, images, priceInfo);
    }

    @Override
    public PerformanceDetailDtoV2 getV2(Long id) {
        if (!performanceRepositoryPort.existsById(id)) {
            throw new CustomException(ErrorCode.PERFORMANCE_NOT_FOUND.getMessage(), ErrorCode.PERFORMANCE_NOT_FOUND);
        }
        return performanceRepositoryPort.getV2(id);
    }
}
