package com.arton.backend.terms.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Terms {
    private Long id;
    private String name;
    private String url;
    private TermsCase termsCase;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @Builder
    public Terms(Long id, String name, String url, TermsCase termsCase, String content, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.termsCase = termsCase;
        this.content = content;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public void setUrl(String url){
        this.url = url;
    }
    public void setName(String name) {
        name = name;
    }

}
