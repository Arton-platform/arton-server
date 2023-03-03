package com.arton.backend.mail.application.port.out;

import com.arton.backend.mail.domain.Mail;
import com.arton.backend.mail.domain.MailCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MailPort {
    Mail findMailById(Long id);
    Mail findMailByCode(MailCode mailCode);
    List<Mail> findAll();
    Page<Mail> findAllWithPaging(Pageable pageable);
}
