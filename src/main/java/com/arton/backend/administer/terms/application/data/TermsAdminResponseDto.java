package com.arton.backend.administer.terms.application.data;

import com.arton.backend.terms.domain.Terms;
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

    @Builder
    public TermsAdminResponseDto(Long id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public static TermsAdminResponseDto toDtoFromDomain(Terms terms) {
        return TermsAdminResponseDto.builder()
                .id(terms.getId())
                .name(terms.getName())
                .url(terms.getUrl())
                .build();
    }

}
