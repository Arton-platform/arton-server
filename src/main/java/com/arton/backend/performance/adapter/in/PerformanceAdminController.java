package com.arton.backend.performance.adapter.in;

import com.arton.backend.auth.application.data.SignupRequestDto;
import com.arton.backend.performance.applicaiton.data.PerformanceCreateDto;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.awt.*;

/**
 * 관리자만 접근 가능.
 */
@Controller
@Secured("ADMIN")
@RequestMapping("/web/performance")
public class PerformanceAdminController {

    @GetMapping("/add")
    public String addPerformance(Model model) {
        model.addAttribute("performance", new PerformanceCreateDto());
        return "performance/createForm";
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String postPerformance(@RequestPart(required = true, name = "performance") @Valid PerformanceCreateDto performanceCreateDto, @RequestPart(required = false, name = "images") MultipartFile[] multipartFiles) {
        return "performance/index";
    }
}
