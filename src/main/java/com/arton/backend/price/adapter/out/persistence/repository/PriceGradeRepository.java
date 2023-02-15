package com.arton.backend.price.adapter.out.persistence.repository;

import com.arton.backend.price.adapter.out.persistence.entity.PriceGradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceGradeRepository extends JpaRepository<PriceGradeEntity, Long> {
    List<PriceGradeEntity> findByPerformance_Id(Long performanceId);
}
