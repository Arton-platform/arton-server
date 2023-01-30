package com.arton.backend.image.adapter.out.repository;

import com.arton.backend.image.application.port.out.PerformanceImageRepositoryPort;
import com.arton.backend.image.application.port.out.PerformanceImageSaveRepositoryPort;
import com.arton.backend.image.domain.PerformanceImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.arton.backend.image.adapter.out.repository.PerformanceImageMapper.toDomain;
import static com.arton.backend.image.adapter.out.repository.PerformanceImageMapper.toEntity;


@Repository
@RequiredArgsConstructor
public class PerformanceImageRepositoryAdapter implements PerformanceImageRepositoryPort, PerformanceImageSaveRepositoryPort {
    private final PerformanceImageRepository performanceImageRepository;

    @Override
    public Optional<PerformanceImage> findPerformanceImage(Long id) {
        Optional<PerformanceImageEntity> response = performanceImageRepository.findById(id);
        if (response.isPresent()) {
            return Optional.ofNullable(toDomain(response.get()));
        }
        return Optional.ofNullable(null);
    }

    @Override
    public List<PerformanceImage> findByPerformance(Long id) {
        return Optional.ofNullable(performanceImageRepository.findAllByPerformance_id(id)).orElseGet(Collections::emptyList)
                .stream().map(PerformanceImageMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public PerformanceImage save(PerformanceImage performanceImage) {
        return toDomain(performanceImageRepository.save(toEntity(performanceImage)));
    }

    @Override
    public void saveAll(List<PerformanceImage> performanceImageList) {
        List<PerformanceImageEntity> images = Optional.ofNullable(performanceImageList).orElseGet(Collections::emptyList).stream()
                .map(PerformanceImageMapper::toEntity).collect(Collectors.toList());
        performanceImageRepository.saveAll(images);
    }
}
