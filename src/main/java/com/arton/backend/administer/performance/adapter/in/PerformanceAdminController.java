package com.arton.backend.administer.performance.adapter.in;

import com.arton.backend.administer.performance.application.data.PerformanceAdminCreateDto;
import com.arton.backend.administer.performance.application.data.PerformanceAdminEditDto;
import com.arton.backend.administer.performance.application.data.PerformanceAdminSearchDto;
import com.arton.backend.administer.performance.application.port.in.*;
import com.arton.backend.search.application.data.SearchResultDto;
import com.arton.backend.search.application.port.in.PerformanceSearchUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 관리자만 접근 가능.
 */
@Controller
//@Secured("ADMIN")
@RequiredArgsConstructor
public class PerformanceAdminController {
    private final PerformanceAdminSaveUseCase performanceAdminSaveUseCase;
    private final PerformanceAdminUseCase performanceAdminUseCase;
    private final PerformanceSearchUseCase performanceSearchService;
    private final PerformanceAdminDeleteUseCase performanceAdminDeleteUseCase;
    private final PerformanceAdminEditUseCase performanceAdminEditUseCase;
    private final PerformanceAdminCopyUseCase performanceAdminCopyUseCase;
    private final PerformanceAdminExcelUseCase performanceAdminExcelUseCase;
    private PerformanceAdminSearchDto form = new PerformanceAdminSearchDto();

    @GetMapping("/")
    public String goHome() {
        return "index";
    }

    @GetMapping("/web/performance/add")
    public String addPerformance(Model model) {
        return "performance/createForm";
    }

    @PostMapping(value = "/web/performance/add")
    public String postPerformance(PerformanceAdminCreateDto performance) {
        performanceAdminSaveUseCase.addPerformance(performance);
        return "redirect:/web/performance";
    }

    @GetMapping("/web/performance")
    public String goPerformanceHome(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<SearchResultDto> performances = performanceSearchService.searchInAdmin(form, pageable); //처음만 init 하면
        model.addAttribute("searchDto", form);
        model.addAttribute("performances", performances);
        return "performance/index";
    }

    @PostMapping("/web/performance")
    public String searchPerformance(Model model, @ModelAttribute("searchDto") PerformanceAdminSearchDto searchDto, @PageableDefault(size = 10) Pageable pageable) {
        this.form = searchDto;
        Page<SearchResultDto> performances = performanceSearchService.searchInAdmin(form, pageable); //처음만 init 하면
        model.addAttribute("performances", performances);
        return "redirect:/web/performance";
    }

    // 공연 삭제
    @PostMapping("/web/performance/revoke")
    public String revokePerformance(@RequestParam(required = true, name = "idx") Long idx) {
        performanceAdminDeleteUseCase.deletePerformance(idx);
        return "redirect:/web/performance";
    }

    // 공연 복사
    @PostMapping("/web/performance/copy")
    public String copyPerformance(@RequestParam(required = true, name = "idx") Long idx) {
        performanceAdminCopyUseCase.copyPerformance(idx);
        return "redirect:/web/performance";
    }

    // 공연 수정
    @GetMapping("/web/performance/{id}")
    public String goEditHome(Model model, @PathVariable(name = "id") Long id) {
        PerformanceAdminEditDto editDto = performanceAdminUseCase.getPerformanceEditDto(id);
        model.addAttribute("editDto", editDto);
        return "performance/editForm";
    }

    // 공연 수정
    // 이미지 업로드도 해야 하므로 post
    @PostMapping("/web/performance/{id}")
    public String editPerformance(Model model, @PathVariable(name = "id") Long id, PerformanceAdminEditDto editDto) {
        performanceAdminEditUseCase.editPerformance(id, editDto);
        return "redirect:/web/performance";
    }

    // excel download
    @GetMapping("/web/performance/download")
    public String downloadExcel(HttpServletResponse response) {
        performanceAdminExcelUseCase.downloadExcel(form, response);
        return "redirect:/web/performance";
    }
}
