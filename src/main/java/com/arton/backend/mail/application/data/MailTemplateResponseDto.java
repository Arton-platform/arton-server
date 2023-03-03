package com.arton.backend.mail.application.data;

import com.arton.backend.mail.domain.Mail;
import com.arton.backend.mail.domain.MailCode;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MailTemplateResponseDto {
    private Long id;
    /** 분류명 */
    private MailCode mailCode;
    /** 메일 내용 */
    private String content;
    /** 제목 */
    private String subject;

    @Builder
    public MailTemplateResponseDto(Long id, MailCode mailCode, String content, String subject) {
        this.id = id;
        this.mailCode = mailCode;
        this.content = content;
        this.subject = subject;
    }

    public static MailTemplateResponseDto toDtoFromDomain(Mail mail) {
        return MailTemplateResponseDto.builder()
                .id(mail.getId())
                .mailCode(mail.getMailCode())
                .content(mail.getContent())
                .subject(mail.getSubject())
                .build();
    }
}
