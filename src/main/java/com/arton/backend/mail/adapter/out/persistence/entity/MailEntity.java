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
    private String code;
    private String description;
    private String url;
    private String title;

    @Builder
    public MailEntity(LocalDateTime createdDate, LocalDateTime updatedDate, Long id, String code, String description, String url, String title) {
        super(createdDate, updatedDate);
        this.id = id;
        this.code = code;
        this.description = description;
        this.url = url;
        this.title = title;
    }
}
