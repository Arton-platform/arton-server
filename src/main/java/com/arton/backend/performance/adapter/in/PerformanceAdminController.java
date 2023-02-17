package com.arton.backend.performance.adapter.in;

import com.arton.backend.performance.applicaiton.data.EndDateBasedPerformanceDto;
import com.arton.backend.performance.applicaiton.data.PerformanceCreateDto;
import com.arton.backend.performance.applicaiton.data.StartDateBasedPerformanceDto;
import com.arton.backend.performance.applicaiton.port.in.PerformanceAdminSaveUseCase;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.performance.domain.PerformanceType;
import com.arton.backend.performance.domain.ShowCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.temporal.TemporalField;

/**
 * 관리자만 접근 가능.
 */
@Controller
//@Secured("ADMIN")
@RequestMapping("/web/performance")
@RequiredArgsConstructor
public class PerformanceAdminController {
    private final PerformanceAdminSaveUseCase performanceAdminSaveUseCase;

    @GetMapping("/add")
    public String addPerformance(Model model) {
        model.addAttribute("performance", new PerformanceCreateDto());
        model.addAttribute("type", PerformanceType.values());
        model.addAttribute("category", ShowCategory.values());
        return "performance/createForm";
    }

    @PostMapping(value = "/add")
    public String postPerformance(@ModelAttribute(name = "performance") PerformanceCreateDto performance) {
        performanceAdminSaveUseCase.addPerformance(performance);
        return "performance/index";
    }
}
