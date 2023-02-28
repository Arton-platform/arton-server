package com.arton.backend.administer.mail.adapter.in;

import com.arton.backend.administer.mail.application.data.AdminMailResponseDto;
import com.arton.backend.administer.mail.application.data.AdminMailSearchDto;
import com.arton.backend.administer.mail.application.port.in.MailAdminSearchUseCase;
import com.arton.backend.administer.performance.application.data.PerformanceAdminSearchDto;
import com.arton.backend.search.application.data.SearchResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MailAdminController {
    private final MailAdminSearchUseCase mailAdminSearchUseCase;
    private AdminMailSearchDto form = new AdminMailSearchDto();

    @GetMapping("/web/mail")
    public String goPerformanceHome(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<AdminMailResponseDto> users = mailAdminSearchUseCase.getMailUserList(form, pageable); //처음만 init 하면
        model.addAttribute("searchDto", form);
        model.addAttribute("users", users);
        return "mail/index";
    }

    @PostMapping("/web/mail")
    public String searchMail(Model model, @ModelAttribute("searchDto") AdminMailSearchDto searchDto, @PageableDefault(size = 10) Pageable pageable) {
        this.form = searchDto;
        Page<AdminMailResponseDto> users = mailAdminSearchUseCase.getMailUserList(form, pageable); //처음만 init 하면
        model.addAttribute("users", users);
        return "redirect:/web/mail";
    }

//    @PostMapping("/web/mail/send")
//    public String sendMail() {
//
//    }
}
