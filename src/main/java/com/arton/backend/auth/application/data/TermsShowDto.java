package com.arton.backend.auth.application.data;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class TermsShowDto {
    private String type;
    private String title;
    private String uri;
}