package com.arton.backend.mail.application.data;

import com.arton.backend.mail.domain.Mail;
import lombok.Builder;
import lombok.Data;

@Data
public class MailTemplateModifyDto {
    private Long id;
    /** 메일 내용 */
    private String description;

    @Builder
    public MailTemplateModifyDto(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public static MailTemplateModifyDto toDtoFromDomain(Mail mail) {
        return MailTemplateModifyDto.builder()
                .id(mail.getId())
                .description(mail.getDescription())
                .build();
    }
}
