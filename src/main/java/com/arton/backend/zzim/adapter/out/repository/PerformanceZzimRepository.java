package com.arton.backend.zzim.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceZzimRepository extends JpaRepository<PerformanceZzimEntity, Long>, CustomPerformanceZzimRepository {
}
