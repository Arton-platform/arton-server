package com.arton.backend.terms.adapter.out.persistence.mapper;

import com.arton.backend.terms.adapter.out.persistence.entity.TermsEntity;
import com.arton.backend.terms.domain.Terms;

public class TermsMapper {

    public static TermsEntity toEntity(Terms terms) {
        return TermsEntity
                .builder()
                .id(terms.getId())
                .name(terms.getName())
                .url(terms.getUrl())
                .termsCase(terms.getTermsCase())
                .createdDate(terms.getCreatedDate())
                .updatedDate(terms.getUpdatedDate())
                .content(terms.getContent())
                .build();
    }

    public static Terms toDomain(TermsEntity terms) {
        return Terms
                .builder()
                .id(terms.getId())
                .name(terms.getName())
                .url(terms.getUrl())
                .termsCase(terms.getTermsCase())
                .createdDate(terms.getCreatedDate())
                .updatedDate(terms.getUpdatedDate())
                .content(terms.getContent())
                .build();
    }


}
