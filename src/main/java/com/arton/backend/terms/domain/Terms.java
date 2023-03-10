package com.arton.backend.terms.domain;

import com.arton.backend.administer.terms.application.data.TermsAdminEditDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Terms {
    private Long id;
    private String name;
    private String url;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @Builder
    public Terms(Long id, String name, String url, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.name = name;
        this.url = url;
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
