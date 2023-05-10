package com.arton.backend.performance.adapter.in;

import com.arton.backend.performance.applicaiton.port.in.PerformanceUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Tag(name = "PERFORMANCE", description = "공연 HTML API")
@Controller
@RequiredArgsConstructor
public class PerformanceHtmlController {
    private final PerformanceUseCase performanceService;

    @Operation(summary = "특정 공연 상세보기를 html 연결시킵니다.", description = "공연 상세보기 Html")
    @GetMapping("/performance.html")
    public String goDetailPage(Model model, @RequestParam(value = "performanceId", required = true) Long performanceId){
        model.addAttribute("performance", performanceService.getOneWithArtistInfo(performanceId));
        return "performance/detail";
    }
}
