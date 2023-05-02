package com.arton.backend.performance.applicaiton.port.in;

import com.arton.backend.performance.applicaiton.data.PerformanceDetailDto;
import com.arton.backend.performance.applicaiton.data.PerformanceDetailQueryDslDto;
import com.arton.backend.performance.applicaiton.data.PerformanceInterestDto;
import com.arton.backend.performance.applicaiton.data.PerformanceZzimDetailDTO;
import com.arton.backend.performance.domain.Performance;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PerformanceUseCase {
    List<Performance> getAllPerformances();
    List<PerformanceInterestDto> getZzimList();
    PerformanceZzimDetailDTO getZzimListV2(Pageable pageable);
    List<Performance> getMusicals();
    List<Performance> getConcerts();
    PerformanceDetailDto getOne(Long id);
}
