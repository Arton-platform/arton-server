package com.arton.backend.administer.performance.application.service;

import com.arton.backend.administer.performance.application.data.PerformanceAdminEditDto;
import com.arton.backend.administer.performance.application.data.PerformanceAdminSearchDto;
import com.arton.backend.administer.performance.application.data.PerformanceExcelDto;
import com.arton.backend.administer.performance.application.port.in.*;
import com.arton.backend.image.application.port.out.PerformanceImageDeleteRepositoryPort;
import com.arton.backend.image.application.port.out.PerformanceImageRepositoryPort;
import com.arton.backend.image.application.port.out.PerformanceImageSaveRepositoryPort;
import com.arton.backend.image.domain.PerformanceImage;
import com.arton.backend.infra.excel.core.ExcelFile;
import com.arton.backend.infra.excel.core.multisheet.MultiSheetExcelFile;
import com.arton.backend.infra.file.FileUploadUtils;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.performance.adapter.out.persistence.mapper.PerformanceMapper;
import com.arton.backend.administer.performance.application.data.PerformanceAdminCreateDto;
import com.arton.backend.performance.applicaiton.port.out.PerformanceDeletePort;
import com.arton.backend.performance.applicaiton.port.out.PerformanceRepositoryPort;
import com.arton.backend.performance.applicaiton.port.out.PerformanceSavePort;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.search.adapter.out.persistence.document.PerformanceDocument;
import com.arton.backend.search.application.data.SearchResultDto;
import com.arton.backend.search.application.port.out.PerformanceDocuemntSavePort;
import com.arton.backend.search.application.port.out.PerformanceDocumentDeletePort;
import com.arton.backend.search.application.port.out.PerformanceDocumentPort;
import com.arton.backend.search.application.port.out.PerformanceDocumentSearchPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PerformanceAdminService implements PerformanceAdminSaveUseCase, PerformanceAdminDeleteUseCase, PerformanceAdminUseCase, PerformanceAdminEditUseCase, PerformanceAdminCopyUseCase, PerformanceAdminExcelUseCase {
    private final PerformanceSavePort performanceSavePort;
    private final PerformanceDeletePort performanceDeletePort;
    private final PerformanceRepositoryPort performanceRepositoryPort;
    private final PerformanceImageSaveRepositoryPort performanceImageSaveRepositoryPort;
    private final PerformanceImageRepositoryPort performanceImageRepositoryPort;
    private final PerformanceImageDeleteRepositoryPort performanceImageDeleteRepositoryPort;
    private final PerformanceDocuemntSavePort performanceDocuemntSavePort;
    private final PerformanceDocumentDeletePort performanceDocumentDeletePort;
    private final FileUploadUtils fileUploadUtils;
    private final PerformanceDocumentSearchPort performanceDocumentSearchPort;
    @Value("${spring.performance.image.dir}")
    private String performanceDir;

    /**
     * 공연 추가하면 ElasticSearch performance 인덱스에 도큐먼트 추가해야함.
     * @param performanceCreateDto
     * @return
     */
    @Override
    public Performance addPerformance(PerformanceAdminCreateDto performanceCreateDto) {
        Performance performance = performanceSavePort.save(performanceCreateDto.dtoToDomain());
        PerformanceDocument performanceDocument = PerformanceMapper.domainToDocument(performance);
        performanceDocuemntSavePort.save(performanceDocument);
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
        // document 제거
        performanceDocumentDeletePort.deleteById(performanceId);
    }

    @Override
    public PerformanceAdminEditDto getPerformanceEditDto(Long performanceId) {
        Performance performance = performanceRepositoryPort.findById(performanceId).orElseThrow(() -> new CustomException(ErrorCode.PERFORMANCE_NOT_FOUND.getMessage(), ErrorCode.PERFORMANCE_NOT_FOUND));
        List<String> imageUrls = performanceImageRepositoryPort.findByPerformanceId(performanceId).stream().map(PerformanceImage::getImageUrl).collect(Collectors.toList());
        // to dto
        PerformanceAdminEditDto editDto = PerformanceAdminEditDto.domainToDto(performance);
        editDto.setImages(imageUrls);
        return editDto;
    }

    @Override
    public void editPerformance(Long id, PerformanceAdminEditDto performanceAdminEditDto) {
        Performance performance = performanceRepositoryPort.findById(id).orElseThrow(() -> new CustomException(ErrorCode.PERFORMANCE_NOT_FOUND.getMessage(), ErrorCode.PERFORMANCE_NOT_FOUND));
        performance.editPerformance(performanceAdminEditDto);
        // domain 반영
        performanceSavePort.save(performance);
        // document 변환
        PerformanceDocument performanceDocument = PerformanceMapper.domainToDocument(performance);
        // update
        performanceDocuemntSavePort.save(performanceDocument);
    }

    @Override
    public void copyPerformance(Long performanceId) {
        Performance performance = performanceRepositoryPort.findById(performanceId).orElseThrow(() -> new CustomException(ErrorCode.PERFORMANCE_NOT_FOUND.getMessage(), ErrorCode.PERFORMANCE_NOT_FOUND));
        List<String> images = performanceImageRepositoryPort.findByPerformanceId(performanceId).stream().map(PerformanceImage::getImageUrl).collect(Collectors.toList());
        // 어차피 엔터티가 아니라 도메인이라 id null 처리해도 문제 없음.
        performance.clearId();
        // 새 도메인 저장후 새 id 발급
        Performance copied = performanceSavePort.save(performance);
        // document 저장
        PerformanceDocument performanceDocument = PerformanceMapper.domainToDocument(copied);
        performanceDocuemntSavePort.save(performanceDocument);
        // 이미지 저장
        List<PerformanceImage> performanceImages = new ArrayList<>();
        for (String image : images) {
            String newImageUrl = fileUploadUtils.copyFile(copied.getPerformanceId(), image);
            performanceImages.add(PerformanceImage.builder()
                    .performance(copied)
                    .imageUrl(newImageUrl)
                    .build());
        }
        performanceImageSaveRepositoryPort.saveAll(performanceImages);
    }

    /**
     * 엑셀 다운로드
     * @param searchDto
     * @param response
     */
    @Override
    public void downloadExcel(PerformanceAdminSearchDto searchDto, HttpServletResponse response) {
        List<PerformanceExcelDto> result = Optional
                .ofNullable(performanceDocumentSearchPort.findByDtoInAdminWithoutPaging(searchDto))
                .orElseGet(Collections::emptyList)
                .stream().map(PerformanceExcelDto::toDtoFromDocument)
                .collect(Collectors.toList());
        System.out.println("result = " + result.size());
        for (PerformanceExcelDto performanceExcelDto : result) {
            System.out.println("performanceExcelDto = " + performanceExcelDto);
        }
        // excel type setting
        response.setHeader("Content-Disposition", "attachment; filename=\"" + "performance_" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)+".xls" + "\";");
        // 인코딩
        response.setContentType("application/vnd.ms-excel; charset=euc-kr");
        // get Excel
        ExcelFile excelFile = new MultiSheetExcelFile(result, PerformanceExcelDto.class);
        try{
            excelFile.write(response.getOutputStream());
        } catch (IOException e) {
            throw new CustomException(ErrorCode.EXCEL_INTERNAL_ERROR.getMessage(), ErrorCode.EXCEL_INTERNAL_ERROR);
        }
    }
}
