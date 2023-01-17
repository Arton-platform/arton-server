package com.arton.backend.auth.application.port.in;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class LoginCheckDto {
    private String refreshToken;
}
