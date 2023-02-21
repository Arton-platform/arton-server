package com.arton.backend.administer.admin.adapter.in.web;

import com.arton.backend.performance.applicaiton.data.PerformanceAdminSearchDto;
import com.arton.backend.performance.applicaiton.data.PerformanceCreateDto;
import com.arton.backend.administer.admin.application.port.in.PerformanceAdminSaveUseCase;
import com.arton.backend.performance.applicaiton.port.in.PerformanceSearchUseCase;
import com.arton.backend.performance.domain.PerformanceType;
import com.arton.backend.performance.domain.ShowCategory;
import com.arton.backend.search.application.data.SearchResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 관리자만 접근 가능.
 */
@Controller
//@Secured("ADMIN")
@RequiredArgsConstructor
public class PerformanceAdminController {
    private final PerformanceAdminSaveUseCase performanceAdminSaveUseCase;
    private final PerformanceSearchUseCase performanceSearchService;
    private PerformanceAdminSearchDto form = new PerformanceAdminSearchDto();

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
        Page<SearchResultDto> performances = performanceSearchService.searchInAdmin(form, pageable); //처음만 init 하면
        model.addAttribute("searchDto", form);
        model.addAttribute("performances", performances);
        model.addAttribute("type", PerformanceType.values());
        model.addAttribute("category", ShowCategory.values());
        return "performance/index";
    }

    @PostMapping("/web/performance")
    public String searchPerformance(Model model, @ModelAttribute("searchDto") PerformanceAdminSearchDto searchDto, @PageableDefault(size = 10) Pageable pageable) {
        this.form = searchDto;
        if (!ObjectUtils.isEmpty(searchDto))
        {
            System.out.println("searchDto = " + searchDto);
        }
        Page<SearchResultDto> performances = performanceSearchService.searchInAdmin(form, pageable); //처음만 init 하면
        model.addAttribute("performances", performances);
        model.addAttribute("type", PerformanceType.values());
        model.addAttribute("category", ShowCategory.values());
        return "performance/index";
    }

    // 공연 삭제
    @PostMapping("/web/performance/revoke")
    public String revokePerformance(@RequestParam(required = true, name = "idx") Long idx) {
        return "redirect:/web/performance";
    }
}
