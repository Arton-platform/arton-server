package com.arton.backend.mail.application.port.out;

import com.arton.backend.mail.domain.Mail;

import java.util.List;

public interface MailSavePort {
    Mail save(Mail mail);
    List<Mail> saveAll(List<Mail> mailList);
}
