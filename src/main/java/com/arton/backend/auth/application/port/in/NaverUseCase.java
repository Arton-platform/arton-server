package com.arton.backend.auth.application.port.in;

public interface NaverUseCase {
    TokenDto login(OAuthSignupDto oAuthSignupDto);
}
