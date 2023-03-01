package com.arton.backend.search.application.port.out;

import com.arton.backend.performance.domain.Performance;

import java.util.List;

public interface PerformanceDocuemntSavePort {
    void saveAll(List<Performance> performances);
    void save(Performance performance);
}
