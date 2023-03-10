package com.arton.backend.mail.application.port.in;

import com.arton.backend.mail.application.data.MailTemplateRequestDto;

public interface MailSaveUseCase {
    void save(MailTemplateRequestDto requestDto);
}
