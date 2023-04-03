package com.arton.backend.user.domain;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.util.StringUtils.hasText;

/**
 * 자체 서비스 가입 ARTON
 * 네이버 OAUTH NAVER
 * 카카오 OAUTH KAKAO
 */
public enum SignupType {
    ARTON("자사"),
    NAVER("네이버"),
    KAKAO("카카오"),
    APPLE("애플"),
    ;

    private String type;

    SignupType(String type) {
        this.type = type;
    }

    private static final Map<String, SignupType> USER_TYPE_MAP;

    static{
        Map<String, SignupType> map = new ConcurrentHashMap<>();
        for (SignupType userType : SignupType.values()) {
            map.put(userType.name(), userType);
        }
        USER_TYPE_MAP = Collections.unmodifiableMap(map);
    }

    public static SignupType get(String name){
        return hasText(name)?USER_TYPE_MAP.get(name):null;
    }
}
