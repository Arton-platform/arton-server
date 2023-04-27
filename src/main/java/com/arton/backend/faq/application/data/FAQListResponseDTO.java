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
@Schema(description = "FAQ list api 요청 정보")
public class FAQListResponseDTO {
    private Long id;
    private String title;

    @Builder
    public FAQListResponseDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public static FAQListResponseDTO toDtoFromDomain(FAQ faq) {
        return FAQListResponseDTO.builder()
                .id(faq.getFaqId())
                .title(faq.getTitle())
                .build();
    }

    public static FAQListResponseDTO toDtoFromEntity(FAQEntity faq) {
        return FAQListResponseDTO.builder()
                .id(faq.getFaqId())
                .title(faq.getTitle())
                .build();
    }
}
