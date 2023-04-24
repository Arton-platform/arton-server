package com.arton.backend.terms.adapter.out.persistence.entity;

import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.terms.domain.TermsCase;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Terms")
@Entity
public class TermsEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String url;
    @Enumerated(EnumType.STRING)
    private TermsCase termsCase;
    // DB로 추가하는 의견 반영
    @Column(length = 5000)
    private String content;

    @Builder
    public TermsEntity(LocalDateTime createdDate, LocalDateTime updatedDate, Long id, String name, String url, TermsCase termsCase, String content) {
        super(createdDate, updatedDate);
        this.id = id;
        this.name = name;
        this.url = url;
        this.termsCase = termsCase;
        this.content = content;
    }


}
