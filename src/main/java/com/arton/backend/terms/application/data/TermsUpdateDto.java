package com.arton.backend.terms.application.data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TermsUpdateDto {
    private Long id;
    private String url;
    private String name;
    private String content;
}
