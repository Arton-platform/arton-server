package com.arton.backend.auth.application.port.in;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OAuthSignupDto {
    @NotBlank
    private String id;
    @NotBlank
    private String nickname;
    @NotBlank
    private String email;
    @NotBlank
    private String age;
    @NotBlank
    private String gender;
}
