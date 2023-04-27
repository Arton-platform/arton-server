package com.arton.backend.faq.application.data;

import com.arton.backend.faq.adapter.out.persistence.FAQEntity;
import com.arton.backend.faq.domain.FAQ;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "특정 faq 정보")
public class FAQResponseDTO {
    private Long id;
    private String title;
    private String content;

    @Builder
    public FAQResponseDTO(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public static FAQResponseDTO toDtoFromDomain(FAQ faq) {
        return FAQResponseDTO.builder()
                .id(faq.getFaqId())
                .title(faq.getTitle())
                .content(faq.getContent())
                .build();
    }

    public static FAQResponseDTO toDtoFromEntity(FAQEntity faq) {
        return FAQResponseDTO.builder()
                .id(faq.getFaqId())
                .title(faq.getTitle())
                .content(faq.getContent())
                .build();
    }
}
