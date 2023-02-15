package com.arton.backend.price.adapter.out.persistence.repository;

import com.arton.backend.price.adapter.out.persistence.mapper.PriceGradeMapper;
import com.arton.backend.price.application.port.out.PriceGradeRepositoryPort;
import com.arton.backend.price.application.port.out.PriceGradeSavePort;
import com.arton.backend.price.domain.PriceGrade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.arton.backend.price.adapter.out.persistence.mapper.PriceGradeMapper.*;

@Repository
@RequiredArgsConstructor
public class PriceGradeRepositoryAdapter implements PriceGradeRepositoryPort, PriceGradeSavePort {
    private final PriceGradeRepository priceGradeRepository;

    @Override
    public List<PriceGrade> findByPerformanceId(Long performanceId) {
        return Optional.ofNullable(priceGradeRepository.findByPerformance_Id(performanceId)).orElseGet(Collections::emptyList)
                .stream()
                .map(PriceGradeMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<PriceGrade> findById(Long id) {
        return priceGradeRepository.findById(id).map(PriceGradeMapper::toDomain);
    }

    @Override
    public PriceGrade save(PriceGrade priceGrade) {
        return toDomain(priceGradeRepository.save(toEntity(priceGrade)));
    }
}
