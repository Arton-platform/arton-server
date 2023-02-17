package com.arton.backend.performance.applicaiton.service;

import com.arton.backend.image.application.port.out.PerformanceImageSaveRepositoryPort;
import com.arton.backend.image.domain.PerformanceImage;
import com.arton.backend.infra.file.FileUploadUtils;
import com.arton.backend.performance.adapter.out.persistence.mapper.PerformanceMapper;
import com.arton.backend.performance.applicaiton.data.PerformanceCreateDto;
import com.arton.backend.performance.applicaiton.port.in.PerformanceAdminSaveUseCase;
import com.arton.backend.performance.applicaiton.port.out.PerformanceSavePort;
import com.arton.backend.performance.applicaiton.port.out.PerformanceSearchRepositoryPort;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.search.adapter.out.persistence.document.PerformanceDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PerformanceAdminService implements PerformanceAdminSaveUseCase {
    private final PerformanceSavePort performanceSavePort;
    private final PerformanceImageSaveRepositoryPort performanceImageSaveRepositoryPort;
    private final PerformanceSearchRepositoryPort performanceSearchRepositoryPort;
    private final FileUploadUtils fileUploadUtils;
    @Value("${spring.performance.image.dir}")
    private String performanceDir;

    /**
     * 공연 추가하면 ElasticSearch performance 인덱스에 도큐먼트 추가해야함.
     * @param performanceCreateDto
     * @return
     */
    @Override
    public Performance addPerformance(PerformanceCreateDto performanceCreateDto) {
        Performance performance = performanceSavePort.save(performanceCreateDto.dtoToDomain());
        PerformanceDocument performanceDocument = PerformanceMapper.domainToDocument(performance);
        System.out.println("performanceDocument = " + performanceDocument.getId());
        performanceSearchRepositoryPort.save(performanceDocument);
        List<MultipartFile> images = performanceCreateDto.getImages();
        boolean isEmpty = images.stream().filter(multipartFile -> !multipartFile.isEmpty()).count() == 0;
        List<PerformanceImage> performanceImages = new ArrayList<>();
        // 공연 이미지가 있다면
        if (!isEmpty) {
            for (MultipartFile image : images) {
                String upload = fileUploadUtils.upload(image, performanceDir + performance.getPerformanceId());
                performanceImages.add(PerformanceImage.builder()
                        .performance(performance)
                        .imageUrl(upload)
                        .build());
            }
            performanceImageSaveRepositoryPort.saveAll(performanceImages);
        }
        return performance;
    }
}