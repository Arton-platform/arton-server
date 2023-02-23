package com.arton.backend.administer.search.adapter.in;

import com.arton.backend.administer.search.application.port.data.AdminKeywordDto;
import com.arton.backend.administer.search.application.port.in.KeywordDeleteUseCase;
import com.arton.backend.administer.search.application.port.in.KeywordUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 관리자만 접근 가능.
 */
@Controller
//@Secured("ADMIN")
@RequiredArgsConstructor
public class AdminSearchController {
    private final KeywordDeleteUseCase keywordDeleteUseCase;
    private final KeywordUseCase keywordUseCase;

    @GetMapping("/web/keyword")
    public String goKeywordPage(Model model) {
        List<AdminKeywordDto> adminKeywordList = keywordUseCase.getAdminKeywordList();
        model.addAttribute("keywords", adminKeywordList);
        return "keyword/index";
    }


    @PostMapping("/web/keyword/revoke")
    public String revokePerformance(@RequestParam(required = true, name = "keyword") String keyword) {
        keywordDeleteUseCase.deleteKeyword(keyword);
        return "redirect:/web/keyword";
    }

}
