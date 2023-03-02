package com.arton.backend.mail.adapter.out.persistence.mapper;

import com.arton.backend.mail.adapter.out.persistence.entity.MailEntity;
import com.arton.backend.mail.domain.Mail;

public class MailMapper {

    public static Mail toDomain(MailEntity mail) {
        return Mail.builder()
                .id(mail.getId())
                .createdDate(mail.getCreatedDate())
                .code(mail.getCode())
                .description(mail.getDescription())
                .updatedDate(mail.getUpdatedDate())
                .title(mail.getTitle())
                .url(mail.getUrl())
                .build();
    }

    public static MailEntity toEntity(Mail mail) {
        return MailEntity.builder()
                .id(mail.getId())
                .createdDate(mail.getCreatedDate())
                .code(mail.getCode())
                .description(mail.getDescription())
                .updatedDate(mail.getUpdatedDate())
                .title(mail.getTitle())
                .url(mail.getUrl())
                .build();
    }
}