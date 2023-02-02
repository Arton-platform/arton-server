package com.arton.backend.auth.application.data;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class LoginCheckDto {
    private String refreshToken;
}
