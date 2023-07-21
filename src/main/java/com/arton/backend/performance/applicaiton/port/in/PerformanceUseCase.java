package com.arton.backend.performance.applicaiton.port.in;

import com.arton.backend.performance.applicaiton.data.*;
import com.arton.backend.performance.domain.Performance;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PerformanceUseCase {
    List<Performance> getAllPerformances();
    List<CommonPerformanceDto> getPerformanceBySortAndPage(Pageable pageable, String sort);
    PerformanceZzimDetailDTO getZzimListV2(Pageable pageable);
    PerformanceZzimDetailDTOV2 getZzimListAllRelatedInfos(Pageable pageable);
    List<Performance> getMusicals();
    List<Performance> getConcerts();
    PerformanceDetailDtoV2 getOneWithArtistInfo(Long id);
    PerformanceDetailDtoV3 getOneWithArtistReviewInfo(Long userId, Long id);
}
