package com.arton.backend.mail.application.service;

import com.arton.backend.mail.application.data.MailTemplateRequestDto;
import com.arton.backend.mail.application.data.MailTemplateResponseDto;
import com.arton.backend.mail.application.port.in.MailDeleteUseCase;
import com.arton.backend.mail.application.port.in.MailSaveUseCase;
import com.arton.backend.mail.application.port.in.MailUseCase;
import com.arton.backend.mail.application.port.out.MailDeletePort;
import com.arton.backend.mail.application.port.out.MailPort;
import com.arton.backend.mail.application.port.out.MailSavePort;
import com.arton.backend.mail.domain.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 메일 템플릿을 관리하는 서비스 로직
 */
@Service
@Transactional
@RequiredArgsConstructor
public class MailService implements MailUseCase, MailDeleteUseCase, MailSaveUseCase {
    private final MailSavePort mailSavePort;
    private final MailDeletePort mailDeletePort;
    private final MailPort mailPort;

    @Override
    public void deleteById(Long id) {
        mailDeletePort.deleteMailById(id);
    }

    @Override
    public Page<Mail> getMailTemplatesWithPaging(Pageable pageable) {
        return mailPort.findAllWithPaging(pageable);
    }

    @Override
    public MailTemplateResponseDto getMailTemplateById(Long id) {
        Mail mail = mailPort.findMailById(id);
        return MailTemplateResponseDto.toDtoFromDomain(mail);
    }

    @Override
    public void save(MailTemplateRequestDto requestDto) {
        Mail mail = MailTemplateRequestDto.toDomainFromDto(requestDto);
        mailSavePort.save(mail);
    }
}
