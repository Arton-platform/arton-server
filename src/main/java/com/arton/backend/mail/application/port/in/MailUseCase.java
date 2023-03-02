package com.arton.backend.mail.application.port.in;

import com.arton.backend.mail.domain.Mail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MailUseCase {
    Page<Mail> getMailTemplatesWithPaging(Pageable pageable);
}
