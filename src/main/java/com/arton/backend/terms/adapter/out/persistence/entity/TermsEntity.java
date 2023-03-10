package com.arton.backend.terms.adapter.out.persistence.entity;

import com.arton.backend.infra.shared.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Terms")
public class TermsEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String url;

    @Builder
    public TermsEntity(LocalDateTime createdDate, LocalDateTime updatedDate, Long id, String name, String url) {
        super(createdDate, updatedDate);
        this.id = id;
        this.name = name;
        this.url = url;
    }
}
