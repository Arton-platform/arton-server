package com.arton.backend.mail.application.data;

import com.arton.backend.mail.domain.Mail;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MailTemplateResponseDto {
    private Long id;
    /** 분류명 */
    private String code;
    /** 메일 내용 */
    private String description;
    /** 제목 */
    private String subject;

    @Builder
    public MailTemplateResponseDto(Long id, String code, String description, String subject) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.subject = subject;
    }

    public static MailTemplateResponseDto toDtoFromDomain(Mail mail) {
        return MailTemplateResponseDto.builder()
                .id(mail.getId())
                .code(mail.getCode())
                .description(mail.getDescription())
                .subject(mail.getSubject())
                .build();
    }
}
