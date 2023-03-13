package com.arton.backend.terms.application.data;

import com.arton.backend.terms.domain.Terms;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class TermsShowDto {
    private String type;
    private String title;
    private String url;

    public static TermsShowDto toDto(Terms terms) {
        return TermsShowDto
                .builder()
                .title(terms.getName())
                .type(terms.getTermsCase().getValue())
                .url(terms.getUrl())
                .build();
    }
}