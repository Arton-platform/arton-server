package com.arton.backend.mail.application.port.out;

import com.arton.backend.mail.domain.Mail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MailPort {
    Mail findMailById(Long id);
    List<Mail> findAll();
    Page<Mail> findAllWithPaging(Pageable pageable);
}
