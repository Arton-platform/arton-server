package com.arton.backend.administer.terms.adapter.in;

import com.arton.backend.administer.terms.application.data.TermsAdminCreateDtoV2;
import com.arton.backend.administer.terms.application.port.in.TermsAdminDeleteUseCase;
import com.arton.backend.administer.terms.application.port.in.TermsAdminSaveUseCase;
import com.arton.backend.administer.terms.application.port.in.TermsAdminUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
//@Secured("ADMIN")
@RequiredArgsConstructor
public class TermsAdminController {
    private final TermsAdminUseCase termsAdminUseCase;
    private final TermsAdminDeleteUseCase termsAdminDeleteUseCase;
    private final TermsAdminSaveUseCase termsAdminSaveUseCase;

    @GetMapping("/web/term")
    public String goTermsHome(Model model) {
        model.addAttribute("terms", termsAdminUseCase.getTermsV2());
        return "term/index";
    }

    // 약관 삭제
    @PostMapping("/web/term/revoke")
    public String revokePerformance(@RequestParam(required = true, name = "idx") Long idx) {
        termsAdminDeleteUseCase.deleteByIdV2(idx);
        return "redirect:/web/term";
    }

    // 약관 등록 페이지
    @GetMapping("/web/term/add")
    public String addPerformance(Model model) {
        model.addAttribute("template", new TermsAdminCreateDtoV2());
        return "term/register";
    }

    // 약관 등록
    @PostMapping(value = "/web/term/add")
    public String postPerformance(TermsAdminCreateDtoV2 createDto) {
        termsAdminSaveUseCase.addTermsV2(createDto);
        return "redirect:/web/term";
    }
}
