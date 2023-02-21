package com.arton.backend.administer.performance.adapter.in;

import com.arton.backend.administer.performance.application.data.PerformanceAdminEditDto;
import com.arton.backend.administer.performance.application.port.in.PerformanceAdminDeleteUseCase;
import com.arton.backend.administer.performance.application.data.PerformanceAdminSearchDto;
import com.arton.backend.administer.performance.application.data.PerformanceAdminCreateDto;
import com.arton.backend.administer.performance.application.port.in.PerformanceAdminEditUseCase;
import com.arton.backend.administer.performance.application.port.in.PerformanceAdminSaveUseCase;
import com.arton.backend.administer.performance.application.port.in.PerformanceAdminUseCase;
import com.arton.backend.search.application.port.in.PerformanceSearchUseCase;
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
import org.springframework.web.bind.annotation.*;

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
    public String postPerformance(PerformanceAdminCreateDto performance) {
        performanceAdminSaveUseCase.addPerformance(performance);
        return "redirect:/web/performance";
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
        performanceAdminDeleteUseCase.deletePerformance(idx);
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

}
