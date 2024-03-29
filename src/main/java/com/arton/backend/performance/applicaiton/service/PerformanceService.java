package com.arton.backend.performance.applicaiton.service;

import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.performance.applicaiton.data.*;
import com.arton.backend.performance.applicaiton.port.in.PerformanceDeleteUseCase;
import com.arton.backend.performance.applicaiton.port.in.PerformanceSaveUseCase;
import com.arton.backend.performance.applicaiton.port.in.PerformanceUseCase;
import com.arton.backend.performance.applicaiton.port.out.PerformanceDeletePort;
import com.arton.backend.performance.applicaiton.port.out.PerformanceRepositoryPort;
import com.arton.backend.performance.applicaiton.port.out.PerformanceSavePort;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.performance.domain.PerformanceType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PerformanceService implements PerformanceUseCase, PerformanceSaveUseCase, PerformanceDeleteUseCase {
    private final PerformanceRepositoryPort performanceRepositoryPort;
    private final PerformanceSavePort performanceSavePort;
    private final PerformanceDeletePort performanceDeletePort;
    private final static Logger log = LoggerFactory.getLogger("LOGSTASH");

    /**
     * 공연 리스트 상세 정보를 보내주자.
     * CSR 방식으로
     * @return
     */
    @Override
    public List<Performance> getAllPerformances() {
        return performanceRepositoryPort.findAllPerformances();
    }

    @Override
    @Cacheable(value = "performanceList", key = "{#sort, #pageable.pageNumber, #pageable.pageSize}")
    public List<CommonPerformanceDto> getPerformanceBySortAndPage(Pageable pageable, String sort) {
        return performanceRepositoryPort.findAllBySortAndPaging(pageable, sort).stream().map(CommonPerformanceDto::domainToDto).collect(Collectors.toList());
    }

    /**
     * 20230502
     * 동건님 요청사항 Data 타입 {musicals: [], concerts: []} 변경
     * @param pageable
     * @return
     */
    @Override
    public PerformanceZzimDetailDTO getZzimListV2(Pageable pageable) {
        List<Performance> musicals = performanceRepositoryPort.findAllMusicals(pageable);
        List<Performance> concerts = performanceRepositoryPort.findAllConcerts(pageable);
        List<PerformanceInterestDto> musicalZzim = musicals.stream().map(PerformanceInterestDto::of).collect(Collectors.toList());
        List<PerformanceInterestDto> concertZzim = concerts.stream().map(PerformanceInterestDto::of).collect(Collectors.toList());
        return PerformanceZzimDetailDTO.builder().musicals(musicalZzim).concerts(concertZzim).build();
    }

    @Override
    public PerformanceZzimDetailDTOV2 getZzimListAllRelatedInfos(Pageable pageable) {
        List<PerformanceDetailDto> musicals = performanceRepositoryPort.getAllRelatedInfosByType(pageable, PerformanceType.MUSICAL).stream().map(PerformanceDetailQueryDslDto::toDto).collect(Collectors.toList());
        List<PerformanceDetailDto> concerts = performanceRepositoryPort.getAllRelatedInfosByType(pageable, PerformanceType.CONCERT).stream().map(PerformanceDetailQueryDslDto::toDto).collect(Collectors.toList());

        return PerformanceZzimDetailDTOV2.builder().musicals(musicals).concerts(concerts).build();
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
    public PerformanceDetailDtoV2 getOneWithArtistInfo(Long id) {
        if (!performanceRepositoryPort.existsById(id)) {
            throw new CustomException(ErrorCode.PERFORMANCE_NOT_FOUND.getMessage(), ErrorCode.PERFORMANCE_NOT_FOUND);
        }
        return performanceRepositoryPort.getOneWithArtistInfo(id).toDto();
    }

    @Override
    public PerformanceDetailDtoV3 getOneWithArtistReviewInfo(Long userId, Long id) {
        if (!performanceRepositoryPort.existsById(id)) {
            throw new CustomException(ErrorCode.PERFORMANCE_NOT_FOUND.getMessage(), ErrorCode.PERFORMANCE_NOT_FOUND);
        }
        return performanceRepositoryPort.getOneWithArtistReviewInfo(userId, id).toDto();
    }

}
