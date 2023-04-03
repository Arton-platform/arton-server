package com.arton.backend.auth.application.service;

import com.arton.backend.auth.application.data.OAuthSignupDto;
import com.arton.backend.auth.application.data.TokenDto;
import com.arton.backend.auth.application.port.in.AppleUseCase;
import com.arton.backend.image.application.port.out.UserImageSaveRepositoryPort;
import com.arton.backend.image.domain.UserImage;
import com.arton.backend.infra.jwt.TokenProvider;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.user.application.port.out.UserRepositoryPort;
import com.arton.backend.user.domain.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
@Service
@Transactional
public class AppleService implements AppleUseCase {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepositoryPort userRepository;
    private final UserImageSaveRepositoryPort userImageSaveRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;
    // for log
    private final static Logger log = LoggerFactory.getLogger("LOGSTASH");
    private final ResourceLoader resourceLoader;
    // for api
    @Value("${apple.key-path}")
    private String keyPath;
    @Value("${spring.default-image}")
    private String defaultImage;

    /**
     * 아직 애플에서 정확하게 어떻게 리턴하는지 모르기 때문에 대충 채움.
     * @param request
     * @param signupDto
     * @return
     */
    @Override
    public synchronized TokenDto login(HttpServletRequest request, OAuthSignupDto signupDto) {
        // get apple access token from client
        String accessToken = Optional.ofNullable(tokenProvider.parseBearerToken(request)).orElseThrow(() -> new CustomException(ErrorCode.TOKEN_INVALID.getMessage(), ErrorCode.TOKEN_INVALID));
        return null;
    }

    private User signup(OAuthSignupDto signupDto) {
        String id = signupDto.getId();
        User user = userRepository.findByAppleId(id).orElse(null);
        if (user == null) {
            /** password is user's own kakao id */
            String password = signupDto.getId();
            user = User.builder()
                    .email(hasText(signupDto.getEmail()) ? signupDto.getEmail() : "")
                    .gender(hasText(signupDto.getGender()) ? Gender.get(signupDto.getGender().toUpperCase(Locale.ROOT)) : Gender.ETC)
                    .password(passwordEncoder.encode(password))
                    .appleId(id)
                    .nickname(hasText(signupDto.getNickname()) ? signupDto.getNickname() : "")
                    .ageRange(hasText(signupDto.getAge()) ? AgeRange.get(Integer.parseInt(signupDto.getAge().substring(0, 1))) : AgeRange.ETC)
                    .auth(UserRole.NORMAL)
                    .signupType(SignupType.KAKAO)
                    .userStatus(true)
                    .termsAgree("Y")
                    .build();
            user = userRepository.save(user);
            UserImage userImage = UserImage.builder().imageUrl(defaultImage).user(user).build();
            userImageSaveRepository.save(userImage);
        }
        return userRepository.findByAppleId(id).orElseThrow(()->new CustomException(ErrorCode.KAKAO_SIMPLE_LOGIN_ERROR.getMessage(), ErrorCode.KAKAO_SIMPLE_LOGIN_ERROR));
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


    private byte[] readPrivateKey() {
        InputStream inputStream = null;
        try {
            inputStream = resourceLoader.getResource(keyPath).getInputStream();
        } catch (IOException e) {
            throw new CustomException(ErrorCode.IO_EXCEPTION.getMessage(), ErrorCode.IO_EXCEPTION);
        }

        byte[] content = null;
        try{
            PemReader pemReader = new PemReader(new InputStreamReader(inputStream));
            PemObject pemObject = pemReader.readPemObject();
            content = pemObject.getContent();
        } catch (IOException e) {
            throw new CustomException(ErrorCode.IO_EXCEPTION.getMessage(), ErrorCode.IO_EXCEPTION);
        }
        return content;
    }
}
