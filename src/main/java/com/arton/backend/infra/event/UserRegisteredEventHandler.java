package com.arton.backend.infra.event;

import com.arton.backend.mail.application.port.in.EmailUseCase;
import com.arton.backend.mail.application.port.in.MailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegisteredEventHandler {
    private final MailUseCase mailUseCase;
    private final EmailUseCase emailUseCase;
}
