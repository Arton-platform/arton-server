package com.arton.backend.mail.application.port.in;

import com.arton.backend.mail.application.data.MailTemplateModifyDto;

public interface MailModifyUseCase {
    void modify(MailTemplateModifyDto modifyDto);
}
