package com.arton.backend.administer.mail.application.port.in;

import com.arton.backend.administer.mail.application.data.AdminMailResponseDto;
import com.arton.backend.administer.mail.application.data.AdminMailSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MailAdminSearchUseCase {
    Page<AdminMailResponseDto> getMailUserList(AdminMailSearchDto searchDto, Pageable pageable);
}
