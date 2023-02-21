package com.arton.backend.administer.performance.application.service;

import com.arton.backend.administer.performance.application.port.in.PerformanceAdminDeleteUseCase;
import com.arton.backend.image.application.port.out.PerformanceImageDeleteRepositoryPort;
import com.arton.backend.image.application.port.out.PerformanceImageRepositoryPort;
import com.arton.backend.image.application.port.out.PerformanceImageSaveRepositoryPort;
import com.arton.backend.image.domain.PerformanceImage;
import com.arton.backend.infra.file.FileUploadUtils;
import com.arton.backend.performance.adapter.out.persistence.mapper.PerformanceMapper;
import com.arton.backend.performance.applicaiton.data.PerformanceCreateDto;
import com.arton.backend.administer.performance.application.port.in.PerformanceAdminSaveUseCase;
import com.arton.backend.performance.applicaiton.port.out.PerformanceDeletePort;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PerformanceAdminService implements PerformanceAdminSaveUseCase, PerformanceAdminDeleteUseCase {
    private final PerformanceSavePort performanceSavePort;
    private final PerformanceDeletePort performanceDeletePort;
    private final PerformanceImageSaveRepositoryPort performanceImageSaveRepositoryPort;
    private final PerformanceImageRepositoryPort performanceImageRepositoryPort;
    private final PerformanceImageDeleteRepositoryPort performanceImageDeleteRepositoryPort;
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
                if (image.isEmpty())
                    continue;
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

    @Override
    public void deletePerformance(Long performanceId) {
        List<String> imageUrls = performanceImageRepositoryPort.findByPerformanceId(performanceId).stream().map(PerformanceImage::getImageUrl).collect(Collectors.toList());
        // imageUrls 삭제
        fileUploadUtils.deleteFiles(performanceId, imageUrls);
        // 연관관계 제거
        performanceImageDeleteRepositoryPort.deletePerformanceImages(performanceId);
        // 공연 제거
        performanceDeletePort.deleteById(performanceId);
    }
}