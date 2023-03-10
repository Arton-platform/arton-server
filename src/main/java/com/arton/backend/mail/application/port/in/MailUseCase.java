package com.arton.backend.mail.application.port.in;

import com.arton.backend.mail.application.data.MailTemplateResponseDto;
import com.arton.backend.mail.domain.Mail;
import com.arton.backend.mail.domain.MailCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MailUseCase {
    Page<Mail> getMailTemplatesWithPaging(Pageable pageable);
    MailTemplateResponseDto getMailTemplateById(Long id);
    Mail findMailByCode(MailCode mailCode);
}
