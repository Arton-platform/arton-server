package com.arton.backend.auth.application.service;

import com.arton.backend.SSLConnectionCover;
import com.arton.backend.auth.application.port.in.KaKaoUseCase;
import com.arton.backend.auth.application.port.in.TokenDto;
import com.arton.backend.infra.jwt.TokenProvider;
import com.arton.backend.user.adapter.out.repository.UserRepository;
import com.arton.backend.user.application.port.out.UserRepositoryPort;
import com.arton.backend.user.domain.AgeRange;
import com.arton.backend.user.domain.Gender;
import com.arton.backend.user.domain.User;
import com.arton.backend.user.domain.UserRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class KaKaoService implements KaKaoUseCase {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepositoryPort userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kakao.client.id}")
    private String clientId;
    @Value("${kakao.redirect.url}")
    private String redirectURL;
    @Value("${default.image}")
    private String defaultImage;
    @Value("${refresh.token.prefix}")
    private String refreshTokenPrefix;


    /**
     * token 발행
     * email, password 로 만들거임
     * 여기서 설정하는 값이 userdetails의 id password로 넘어감
     * 원래는 평문 password 여야 하지만 간편로그인 경우 password 입력이 없으므로.. 유일한 식별값으로 대체
     * @param code
     * @return
     */
    @Override
    public TokenDto login(String code) {
        String accessToken = getAccessToken(code);
//        String accessToken = SSLConnectionCover.getAccessToken(clientId, redirectURL, code);
        log.info("accessToken {}", accessToken);
        User register = signup(accessToken);
        // Generate ArtOn JWT
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(register.getId(), String.valueOf(register.getKakaoId()));
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDto tokenDto = tokenProvider.generateToken(authenticate);
        redisTemplate.opsForValue().set(refreshTokenPrefix+authenticate.getName(), tokenDto.getRefreshToken(), tokenDto.getRefreshTokenExpiresIn(), TimeUnit.MILLISECONDS);
        return tokenDto;
    }

    /**
     * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api
     * 토근 받기 참조
     * @param code
     * @return
     */
    private String getAccessToken(String code) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectURL);
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange("https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                request,
                String.class);
        String responseBody = response.getBody();
        try {
            return objectMapper.readTree(responseBody).get("access_token").asText();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 동의한 데이터 가져오기
     *
     * @param accessToken
     * @return
     */
    private JsonNode getUserInfo(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer "+accessToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange("https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                request,
                String.class);

        String responseBody = response.getBody();
        log.info("responseBody for userInfo {}", responseBody);
        try {
            return objectMapper.readTree(responseBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 카카오로 회원가입된 계정이 있으면 해당 유저 정보 return
     * 아니면 회원가입 진행 + return
     * kakao ageRange example
     * 0~9, 10~14, 15~19, 20~29, ...
     * @param accessToken
     * @return
     */
    private User signup(String accessToken) {
        JsonNode userInfo = getUserInfo(accessToken);
//        JsonNode userInfo = SSLConnectionCover.getUserInfo(accessToken);
        long id = userInfo.get("id").asLong();
        User user = userRepository.findByKakaoId(id).orElse(null);
        if (user == null) {
            String nickName = userInfo.get("kakao_account").get("profile").get("nickname").asText();
            log.info("nickName {}", nickName);
            String email = userInfo.get("kakao_account").get("email").asText();
            log.info("email {}", email);
            String ageRange = userInfo.get("kakao_account").get("age_range").asText();
            log.info("ageRange {}", ageRange);
            int age = Integer.parseInt(ageRange.substring(0, 1));
            String gender = userInfo.get("kakao_account").get("gender").asText();
            /** password is user's own kakao id */
            String password = userInfo.get("id").asText();
            user = User.builder().email(email)
                    .gender(Gender.get(gender.toUpperCase(Locale.ROOT)))
                    .password(passwordEncoder.encode(password))
                    .kakaoId(id)
                    .nickname(nickName)
                    .profileImageUrl(defaultImage)
                    .ageRange(AgeRange.get(age))
                    .auth(UserRole.NORMAL)
                    .build();
            userRepository.save(user);
        }
        return user;
    }


}
