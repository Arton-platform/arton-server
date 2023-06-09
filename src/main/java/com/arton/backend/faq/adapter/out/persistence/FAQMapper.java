package com.arton.backend.faq.adapter.out.persistence;

import com.arton.backend.faq.domain.FAQ;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class FAQMapper {
    public FAQ toDomain(FAQEntity entity){
        return FAQ.builder()
                .faqId(entity.getFaqId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .userId(entity.getUser().getId())
                .createdDate(entity.getCreatedDate())
                .updatedDate(entity.getUpdatedDate())
                .build();
    }

    public FAQEntity toEntity(FAQ faq){
        return FAQEntity.builder()
                .faqId(faq.getFaqId())
                .title(faq.getTitle())
                .content(faq.getContent())
                .user(UserEntity.builder().id(faq.getUserId()).build())
                .createdDate(faq.getCreatedDate())
                .updatedDate(faq.getUpdatedDate())
                .build();
    }
}
