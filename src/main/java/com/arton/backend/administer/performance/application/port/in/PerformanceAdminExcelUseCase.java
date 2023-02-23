package com.arton.backend.administer.performance.application.port.in;

import com.arton.backend.administer.performance.application.data.PerformanceAdminSearchDto;

import javax.servlet.http.HttpServletResponse;

public interface PerformanceAdminExcelUseCase {
    void downloadExcel(PerformanceAdminSearchDto searchDto, HttpServletResponse response);
}
