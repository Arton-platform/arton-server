package com.arton.backend.administer.mail.adapter.in;

import com.arton.backend.administer.mail.application.data.AdminMailResponseDto;
import com.arton.backend.administer.mail.application.data.AdminMailSearchDto;
import com.arton.backend.administer.mail.application.port.in.MailAdminSearchUseCase;
import com.arton.backend.mail.application.data.MailTemplateModifyDto;
import com.arton.backend.mail.application.data.MailTemplateRequestDto;
import com.arton.backend.mail.application.data.MailTemplateResponseDto;
import com.arton.backend.mail.application.port.in.EmailUseCase;
import com.arton.backend.mail.application.data.MailMultiReceiversDto;
import com.arton.backend.mail.application.port.in.MailDeleteUseCase;
import com.arton.backend.mail.application.port.in.MailSaveUseCase;
import com.arton.backend.mail.application.port.in.MailUseCase;
import com.arton.backend.mail.domain.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MailAdminController {
    private final MailAdminSearchUseCase mailAdminSearchUseCase;
    private final EmailUseCase emailUseCase;
    private final MailUseCase mailUseCase;
    private final MailDeleteUseCase mailDeleteUseCase;
    private final MailSaveUseCase mailSaveUseCase;
    private AdminMailSearchDto form = new AdminMailSearchDto();

    /**
     * 단체 메일 홈을 조회하는 api
     * @param model
     * @param pageable
     * @return
     */
    @GetMapping("/web/mail")
    public String goPerformanceHome(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<AdminMailResponseDto> users = mailAdminSearchUseCase.getMailUserList(form, pageable); //처음만 init 하면
        model.addAttribute("searchDto", form);
        model.addAttribute("users", users);
        model.addAttribute("mailDto", new MailMultiReceiversDto());
        return "mail/index";
    }

    /**
     * 단체 메일 홈에서 검색을 하는 api
     * @param model
     * @param searchDto
     * @param pageable
     * @return
     */
    @PostMapping("/web/mail")
    public String searchMail(Model model, @ModelAttribute("searchDto") AdminMailSearchDto searchDto, @PageableDefault(size = 10) Pageable pageable) {
        this.form = searchDto;
        Page<AdminMailResponseDto> users = mailAdminSearchUseCase.getMailUserList(form, pageable); //처음만 init 하면
        model.addAttribute("users", users);
        return "redirect:/web/mail";
    }

    /**
     * 메일 보내기를 요청하는 api
     * @param model
     * @param mailDto
     * @return
     */
    @PostMapping("/web/mail/send")
    public String sendMail(Model model, @ModelAttribute("mailDto")MailMultiReceiversDto mailDto) {
        // get emails
        List<String> receivers = mailAdminSearchUseCase.getUsersForMailing(form);
        mailDto.setReceivers(receivers);
        emailUseCase.sendMailWithMultipleReceivers(mailDto);
        return "redirect:/web/mail";
    }

    /**
     * 자동 메일 홈을 조회하는 api
     * @param model
     * @param pageable
     * @return
     */
    @GetMapping("/web/mail/auto")
    public String goAutoMailingHome(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<Mail> templates = mailUseCase.getMailTemplatesWithPaging(pageable);
        model.addAttribute("templates", templates);
        return "mail/auto/index";
    }

    /**
     * 자동 메일 템플릿을 등록하는 페이지
     * @return
     */
    @GetMapping("/web/mail/auto/register")
    public String goAutoMailingRegisterHome(Model model) {
        model.addAttribute("template", new MailTemplateResponseDto());
        return "mail/auto/register";
    }

    /**
     * 자동 메일 템플릿을 등록하는 api
     * @return
     */
    @PostMapping("/web/mail/auto")
    public String addMailTemplate(@ModelAttribute("requestDto") MailTemplateRequestDto requestDto) {
        mailSaveUseCase.save(requestDto);
        return "redirect:/web/mail/auto";
    }

    /**
     * 자동 메일 템플릿 수정페이지
     * @param model
     * @param idx
     * @return
     */
    @GetMapping("/web/mail/auto/modify")
    public String goModifyHome(Model model, @RequestParam(required = true, name = "idx") Long idx) {
        MailTemplateResponseDto template = mailUseCase.getMailTemplateById(idx);
        model.addAttribute("template", template);
        return "mail/auto/modify";
    }

    /**
     * 자동 메일 템플릿 수정요청
     * @return
     */
    @PostMapping("/web/mail/auto/modify")
    public String modifyMailTemplate(@ModelAttribute("modifyDto") MailTemplateModifyDto modifyDto) {
        return "redirect:/web/mail/auto";
    }

    /**
     * 메일 템플릿 상세보기
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/web/mail/auto/{id}")
    public String getMailTemplate(Model model, @PathVariable(name = "id") Long id) {
        MailTemplateResponseDto template = mailUseCase.getMailTemplateById(id);
        model.addAttribute("template", template);
        return "mail/auto/details";
    }

    /**
     * 자동 메일 템플릿을 삭제하는 api
     * @param idx
     * @return
     */
    @PostMapping("/web/mail/auto/revoke")
    public String revokePerformance(@RequestParam(required = true, name = "idx") Long idx) {
        mailDeleteUseCase.deleteById(idx);
        return "redirect:/web/mail/auto";
    }
}
