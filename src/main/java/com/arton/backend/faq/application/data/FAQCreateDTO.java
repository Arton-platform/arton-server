package com.arton.backend.faq.application.data;

import com.arton.backend.faq.domain.FAQ;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FAQCreateDTO {
    @NotBlank
    private Long userId;
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    @Builder
    public FAQCreateDTO(Long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public FAQ toDomainFromDTO() {
        return FAQ.builder()
                .userId(this.userId)
                .content(this.content)
                .title(this.title)
                .build();
    }
}
