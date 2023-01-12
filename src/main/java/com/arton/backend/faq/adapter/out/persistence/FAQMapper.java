package com.arton.backend.faq.adapter.out.persistence;

import com.arton.backend.faq.adapter.out.persistence.FAQEntity;
import com.arton.backend.faq.domain.FAQ;
import org.springframework.stereotype.Component;

@Component
public class FAQMapper {
    public FAQ toDomain(FAQEntity entity){
        return FAQ.builder()
                .faqId(entity.getFaqId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .hit(entity.getHit())
                .image(entity.getImage())
                .user(entity.getUser())
                .createdDate(entity.getCreatedDate())
                .updateDate(entity.getUpdateDate())
                .build();
    }

    public FAQEntity toEntity(FAQ faq){
        return FAQEntity.builder()
                .faqId(faq.getFaqId())
                .title(faq.getTitle())
                .content(faq.getContent())
                .hit(faq.getHit())
                .image(faq.getImage())
                .user(faq.getUser())
                .createdDate(faq.getCreatedDate())
                .updateDate(faq.getUpdateDate())
                .build();
    }
}
