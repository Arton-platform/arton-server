package com.arton.backend.auth.application.service;

import com.arton.backend.auth.application.data.OAuthSignupDto;
import com.arton.backend.auth.application.data.TokenDto;
import com.arton.backend.auth.application.port.in.OAuthUseCase;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class OAuthService implements OAuthUseCase {
    private final AppleStrategy appleStrategy;
    private final NaverStrategy naverStrategy;
    private final KaKaoStrategy kaKaoStrategy;
    private final GoogleStrategy googleStrategy;
    private Map<Integer, OAuthStrategy> strategyMap = new HashMap<>();

    public OAuthService(AppleStrategy appleStrategy, NaverStrategy naverStrategy, KaKaoStrategy kaKaoStrategy, GoogleStrategy googleStrategy) {
        this.appleStrategy = appleStrategy;
        this.naverStrategy = naverStrategy;
        this.kaKaoStrategy = kaKaoStrategy;
        this.googleStrategy = googleStrategy;

        strategyMap.put(0, kaKaoStrategy);
        strategyMap.put(1, naverStrategy);
        strategyMap.put(2, appleStrategy);
        strategyMap.put(3, googleStrategy);
    }

    @Override
    @Transactional
    public TokenDto signup(HttpServletRequest request, OAuthSignupDto signupDto) {
        try {
            return strategyMap.get(Integer.parseInt(signupDto.getLoginType())).signup(request, signupDto);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.PARAMETER_NOT_VALID.getMessage(), ErrorCode.PARAMETER_NOT_VALID);
        }
    }
}
