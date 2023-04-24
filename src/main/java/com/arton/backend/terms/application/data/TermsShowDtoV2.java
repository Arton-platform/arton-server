package com.arton.backend.terms.application.data;

import com.arton.backend.terms.domain.Terms;
import lombok.*;

/**
 * 0424 약관 내용 추가
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class TermsShowDtoV2 {
    private String type;
    private String title;
    private String content;

    public static TermsShowDtoV2 toDto(Terms terms) {
        return TermsShowDtoV2
                .builder()
                .title(terms.getName())
                .type(terms.getTermsCase().getValue())
                .content(terms.getContent())
                .build();
    }
}