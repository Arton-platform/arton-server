package com.arton.backend.faq.adapter.out;

import com.arton.backend.faq.domain.FAQ;
import org.springframework.stereotype.Component;

@Component
public class FAQMapper {
    public FAQ toDomain(FAQEntity entity){
        return FAQ.builder()
                .faqId(entity.getFaqId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .build();
    }

    public FAQEntity toEntity(FAQ faq){
        return FAQEntity.builder()
                .faqId(faq.getFaqId())
                .title(faq.getTitle())
                .content(faq.getContent())
                .build();
    }
}
