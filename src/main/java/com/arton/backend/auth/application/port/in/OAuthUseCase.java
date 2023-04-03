package com.arton.backend.auth.application.port.in;

import com.arton.backend.auth.application.data.OAuthSignupDto;
import com.arton.backend.auth.application.data.TokenDto;

import javax.servlet.http.HttpServletRequest;

public interface OAuthUseCase {
    TokenDto signup(HttpServletRequest request, OAuthSignupDto signupDto);
}
