package com.arton.backend.administer.performance.application.port.in;

import com.arton.backend.administer.performance.application.data.PerformanceAdminSearchDto;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;

public interface PerformanceAdminExcelUseCase {
    void downloadExcel(PerformanceAdminSearchDto searchDto, HttpServletResponse response);
}
