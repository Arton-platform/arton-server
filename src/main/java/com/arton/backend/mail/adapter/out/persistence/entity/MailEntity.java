package com.arton.backend.mail.adapter.out.persistence.entity;

import com.arton.backend.infra.shared.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Mail")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MailEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** 분류명 */
    private String code;
    /** 메일 내용 */
    private String description;
    /** 추후 구현시 s3 저장 url */
    private String url;
    /** 제목 */
    private String subject;

    @Builder
    public MailEntity(LocalDateTime createdDate, LocalDateTime updatedDate, Long id, String code, String description, String url, String subject) {
        super(createdDate, updatedDate);
        this.id = id;
        this.code = code;
        this.description = description;
        this.url = url;
        this.subject = subject;
    }
}
