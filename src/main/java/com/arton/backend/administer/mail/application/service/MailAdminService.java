package com.arton.backend.administer.mail.application.service;

import com.arton.backend.administer.mail.application.data.AdminMailResponseDto;
import com.arton.backend.administer.mail.application.data.AdminMailSearchDto;
import com.arton.backend.administer.mail.application.data.AdminMailSendDto;
import com.arton.backend.administer.mail.application.port.in.MailAdminSearchUseCase;
import com.arton.backend.administer.mail.application.port.in.MailAdminSendUseCase;
import com.arton.backend.search.application.port.out.UserDocumentSearchPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class MailAdminService implements MailAdminSearchUseCase {
    private final UserDocumentSearchPort userDocumentSearchPort;

    @Override
    public Page<AdminMailResponseDto> getMailUserList(AdminMailSearchDto searchDto, Pageable pageable) {
        return userDocumentSearchPort.searchUserForMailing(searchDto, pageable).map(userDocumentSearchHit -> AdminMailResponseDto.toResultFromDocument(userDocumentSearchHit.getContent()));
    }
}
