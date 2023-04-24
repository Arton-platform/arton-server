package com.arton.backend.administer.terms.application.data;

import com.arton.backend.terms.domain.Terms;
import com.arton.backend.terms.domain.TermsCase;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TermsAdminResponseDtoV2 {
    private Long id;
    private String name;
    private String url;
    private TermsCase termsCase;
    private String content;

    @Builder
    public TermsAdminResponseDtoV2(Long id, String name, String url, TermsCase termsCase, String content) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.termsCase = termsCase;
        this.content = content;
    }

    public static TermsAdminResponseDtoV2 toDtoFromDomain(Terms terms) {
        return TermsAdminResponseDtoV2.builder()
                .id(terms.getId())
                .name(terms.getName())
                .url(terms.getUrl())
                .termsCase(terms.getTermsCase())
                .content(terms.getContent())
                .build();
    }

}
