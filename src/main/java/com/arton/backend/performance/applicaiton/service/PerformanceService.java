package com.arton.backend.performance.applicaiton.service;

import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import com.arton.backend.performance.applicaiton.port.in.PerformanceInterestDto;
import com.arton.backend.performance.applicaiton.port.in.PerformanceUseCase;
import com.arton.backend.performance.applicaiton.port.out.PerformanceRepositoryPort;
import com.arton.backend.performance.domain.Performance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PerformanceService implements PerformanceUseCase {
    private final PerformanceRepositoryPort performanceRepositoryPort;

    @Override
    public List<PerformanceEntity> getAllPerformances() {
        return performanceRepositoryPort.findAllPerformances();
    }

    @Override
    public List<PerformanceInterestDto> getZzimList() {
        return performanceRepositoryPort.findAllPerformances().stream().map(PerformanceInterestDto::of).collect(Collectors.toList());
    }

    @Override
    public List<PerformanceEntity> getMusicals() {
        return performanceRepositoryPort.findAllMusicals();
    }

    @Override
    public List<PerformanceEntity> getConcerts() {
        return performanceRepositoryPort.findAllConcerts();
    }

    @Override
    public PerformanceEntity save(PerformanceEntity performance) {
        return performanceRepositoryPort.save(performance);
    }

    @Override
    public void deletePerformance(PerformanceEntity performance) {
        performanceRepositoryPort.deletePerformance(performance);
    }

    @Override
    public void deletePerformance(Long id) {
        performanceRepositoryPort.deleteById(id);
    }
}
