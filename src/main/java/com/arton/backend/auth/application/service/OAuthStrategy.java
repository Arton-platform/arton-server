package com.arton.backend.auth.application.service;

import com.arton.backend.auth.application.data.OAuthSignupDto;
import com.arton.backend.auth.application.data.TokenDto;

import javax.servlet.http.HttpServletRequest;

public interface OAuthStrategy {
    TokenDto signup(HttpServletRequest request, OAuthSignupDto signupDto);
}
