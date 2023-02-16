package com.arton.backend.performance.applicaiton.port.in;

import com.arton.backend.performance.applicaiton.data.PerformanceCreateDto;
import com.arton.backend.performance.domain.Performance;
import org.springframework.web.multipart.MultipartFile;

public interface PerformanceAdminSaveUseCase {
    Performance addPerformance(PerformanceCreateDto performanceCreateDto, MultipartFile[] multipartFiles);
}
