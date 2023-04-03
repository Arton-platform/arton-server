package com.arton.backend.mail.application.data;

import com.arton.backend.mail.domain.Mail;
import lombok.Builder;
import lombok.Data;

@Data
public class MailTemplateModifyDto {
    private Long id;
    /** 메일 내용 */
    private String content;

    @Builder
    public MailTemplateModifyDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public static MailTemplateModifyDto toDtoFromDomain(Mail mail) {
        return MailTemplateModifyDto.builder()
                .id(mail.getId())
                .content(mail.getContent())
                .build();
    }
}
