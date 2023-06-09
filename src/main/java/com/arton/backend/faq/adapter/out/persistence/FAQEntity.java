package com.arton.backend.faq.adapter.out.persistence;

import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="faq")
public class FAQEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long faqId;
    private String title;
    private String content;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Builder
    public FAQEntity(LocalDateTime createdDate, LocalDateTime updatedDate, long faqId, String title, String content, UserEntity user) {
        super(createdDate, updatedDate);
        this.faqId = faqId;
        this.title = title;
        this.content = content;
        this.user = user;
    }
}
