package com.arton.backend.administer.terms.application.data;

import com.arton.backend.terms.domain.Terms;
import com.arton.backend.terms.domain.TermsCase;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TermsAdminResponseDto {
    private Long id;
    private String name;
    private String url;
    private TermsCase termsCase;

    @Builder
    public TermsAdminResponseDto(Long id, String name, String url, TermsCase termsCase) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.termsCase = termsCase;
    }

    public static TermsAdminResponseDto toDtoFromDomain(Terms terms) {
        return TermsAdminResponseDto.builder()
                .id(terms.getId())
                .name(terms.getName())
                .url(terms.getUrl())
                .termsCase(terms.getTermsCase())
                .build();
    }

}
