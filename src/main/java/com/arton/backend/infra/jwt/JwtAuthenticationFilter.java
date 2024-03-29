package com.arton.backend.infra.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final RedisTemplate redisTemplate;

    private final static Logger log = LoggerFactory.getLogger("LOGSTASH");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // get token
            String token = tokenProvider.parseBearerToken(request);
            if (!request.getRequestURI().equals("/actuator/prometheus")) {
                log.info("JwtAuth Filter, Request URI : {}", request.getRequestURI());
            }
            // 유효 검증
            if (!request.getRequestURI().equals("/auth/signup/oauth") && StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
                // logout 여부 확인
                String isLogout = (String)redisTemplate.opsForValue().get(token);
                if (ObjectUtils.isEmpty(isLogout)){
                    // 토큰이 유효할 경우 저장
                    Authentication authentication = tokenProvider.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            logger.error("Could not set member authentication in security context", e);
        }
        filterChain.doFilter(request, response);
    }

}
