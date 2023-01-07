package com.arton.backend.auth.application.port.in;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LoginRequestDto {
    private String email;
    private String password;
}
