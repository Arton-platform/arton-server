package com.arton.backend.administer.mail.application.port.in;

import com.arton.backend.administer.mail.application.data.AdminMailSendDto;

public interface MailAdminSendUseCase {
    void sendMail(AdminMailSendDto mailSendDto);
}
