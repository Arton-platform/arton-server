package com.arton.backend.performance.adapter.in;

import com.arton.backend.performance.applicaiton.data.PerformanceCreateDto;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.performance.domain.PerformanceType;
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
public class PerformanceAdminController {

    @GetMapping("/add")
    public String addPerformance(Model model) {
        model.addAttribute("performance", new PerformanceCreateDto());
        model.addAttribute("type", PerformanceType.values());
        return "performance/createForm";
    }

    @PostMapping(value = "/add")
    public String postPerformance(@ModelAttribute(name = "performance") PerformanceCreateDto performance, @RequestParam(required = false, name = "images") MultipartFile[] multipartFiles) {

        return "performance/index";
    }
}
