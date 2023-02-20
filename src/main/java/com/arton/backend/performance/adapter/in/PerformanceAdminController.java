package com.arton.backend.performance.adapter.in;

import com.arton.backend.performance.applicaiton.data.PerformanceAdminSearchDto;
import com.arton.backend.performance.applicaiton.data.PerformanceCreateDto;
import com.arton.backend.performance.applicaiton.port.in.PerformanceAdminSaveUseCase;
import com.arton.backend.performance.applicaiton.port.in.PerformanceSearchUseCase;
import com.arton.backend.performance.domain.PerformanceType;
import com.arton.backend.performance.domain.ShowCategory;
import com.arton.backend.search.application.data.SearchPageDto;
import com.arton.backend.search.application.data.SearchResultDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 관리자만 접근 가능.
 */
@Controller
//@Secured("ADMIN")
@RequiredArgsConstructor
public class PerformanceAdminController {
    private final PerformanceAdminSaveUseCase performanceAdminSaveUseCase;
    private final PerformanceSearchUseCase performanceSearchService;
    private PerformanceAdminSearchDto searchDto = new PerformanceAdminSearchDto();

    @GetMapping("/web/performance/add")
    public String addPerformance(Model model) {
        model.addAttribute("type", PerformanceType.values());
        model.addAttribute("category", ShowCategory.values());
        return "performance/createForm";
    }

    @GetMapping("/")
    public String goHome() {
        return "index";
    }

    @PostMapping(value = "/web/performance/add")
    public String postPerformance(PerformanceCreateDto performance) {
        performanceAdminSaveUseCase.addPerformance(performance);
        return "performance/index";
    }

    @GetMapping("/web/performance")
    public String goPerformanceHome(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<SearchResultDto> performances = performanceSearchService.searchInAdmin(searchDto, pageable); //처음만 init 하면
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("performances", performances);
        model.addAttribute("type", PerformanceType.values());
        model.addAttribute("category", ShowCategory.values());
        return "performance/index";
    }

    @PostMapping("/web/performance")
    public String searchPerformance(Model model, @ModelAttribute("searchDto") PerformanceAdminSearchDto searchDto, @PageableDefault(size = 10) Pageable pageable) {
        this.searchDto = searchDto;
        Page<SearchResultDto> performances = performanceSearchService.searchInAdmin(searchDto, pageable); //처음만 init 하면
        model.addAttribute("performances", performances);
        return "performance/index";
    }
}
