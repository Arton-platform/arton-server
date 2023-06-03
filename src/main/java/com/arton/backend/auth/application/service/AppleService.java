package com.arton.backend.auth.application.service;

import com.arton.backend.auth.application.data.KeyAlgDTO;
import com.arton.backend.auth.application.data.KeyAlgResponseDTO;
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
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AppleService implements AppleUseCase {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepositoryPort userRepository;
    private final UserImageSaveRepositoryPort userImageSaveRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;
    @Value("${spring.default-image}")
    private String defaultImage;
    @Value("${refresh.token.prefix}")
    private String refreshTokenPrefix;

    /**
     * 애플의 경우 id token을 디코드해서
     * 유저 고유값을 알아내야함
     * 애플 id service에서 공개키를 생성후 토큰 디코드하여
     * 프론트에서 넘긴 정보가 일치하는지 비교하자
     */
    @Override
    public synchronized TokenDto login(HttpServletRequest request, OAuthSignupDto signupDto) {
        String identityToken = Optional.ofNullable(tokenProvider.parseBearerToken(request)).orElseThrow(() -> new CustomException(ErrorCode.TOKEN_INVALID.getMessage(), ErrorCode.TOKEN_INVALID));
        JsonNode userInfo = getUserInfo(identityToken);
        if (!userInfo.get("sub").asText().equals(signupDto.getId())) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND);
        }
        User register = signup(signupDto);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(register.getId(), register.getPlatformId());
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDto tokenDto = tokenProvider.generateToken(authenticate);
        redisTemplate.opsForValue().set(refreshTokenPrefix+authenticate.getName(), tokenDto.getRefreshToken(), tokenDto.getRefreshTokenExpiresIn(), TimeUnit.MILLISECONDS);
        return tokenDto;
    }


    /**
     * 토큰 파싱하기
     * @return
     */
    private JsonNode getUserInfo(String identityToken) {
        String[] tokens = identityToken.split("\\.");
        String encodedHeader = tokens[0];
        String decodedHeader = new String(Base64.getDecoder().decode(encodedHeader));
        try {
            JsonNode headerJson = objectMapper.readTree(decodedHeader);
            String kid = headerJson.get("kid").asText();
            String alg = headerJson.get("alg").asText();
            PublicKey publickey = makePublicKey(kid, alg);
            Claims body = Jwts.parser().setSigningKey(publickey).parseClaimsJws(identityToken).getBody();
            return objectMapper.readTree(body.toString());
        } catch (JsonProcessingException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private KeyAlgResponseDTO getAppleIdKeys() {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<KeyAlgResponseDTO> response = restTemplate.exchange("https://appleid.apple.com/auth/keys",
                HttpMethod.GET,
                request,
                KeyAlgResponseDTO.class);
        if (response.getStatusCode().isError()) {
            throw new CustomException(ErrorCode.APPLE_SIMPLE_LOGIN_ERROR.getMessage(), ErrorCode.APPLE_SIMPLE_LOGIN_ERROR);
        }
        return response.getBody();
    }

    private PublicKey makePublicKey(String keyId, String alg) {
        KeyAlgResponseDTO response = getAppleIdKeys();
        PublicKey publicKey = null;
        for (KeyAlgDTO value : response.getKeys()) {
            if ((value.getKid().equals(keyId)) && (value.getAlg().equals(alg))) {
                // make public key
                String n = value.getN();
                String e = value.getE();
//                Base64.getUrlDecoder().decode(nStr.substring(1, nStr.length() - 1));
                byte[] nBytes = Base64.getUrlDecoder().decode(n);
                byte[] eBytes = Base64.getUrlDecoder().decode(e);
                BigInteger bigIntegerN = new BigInteger(1, nBytes);
                BigInteger bigIntegerE = new BigInteger(1, eBytes);
                try{
                    RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(bigIntegerN, bigIntegerE);
                    KeyFactory keyFactory = KeyFactory.getInstance(value.getKty());
                    publicKey = keyFactory.generatePublic(publicKeySpec);
                } catch (NoSuchAlgorithmException ex) {
                    throw new CustomException(ErrorCode.INVALID_ALGORITHM_REQUEST.getMessage(), ErrorCode.INVALID_ALGORITHM_REQUEST);
                } catch (InvalidKeySpecException ex) {
                    throw new CustomException(ErrorCode.INVALID_KEYSPEC_REQUEST.getMessage(), ErrorCode.INVALID_KEYSPEC_REQUEST);
                }
            }
        }
        return publicKey;
    }

    /**
     * 카카오로 회원가입된 계정이 있으면 해당 유저 정보 return
     * 아니면 회원가입 진행 + return
     * kakao ageRange example
     * 0~9, 10~14, 15~19, 20~29, ...
     * @param signupDto
     * @return
     */

    private User signup(OAuthSignupDto signupDto) {
        String id = signupDto.getId();
        User user = userRepository.findByPlatformId(id, SignupType.APPLE).orElse(null);
        if (user == null) {
            /** password is user's own kakao id */
            String password = id;
            user = User.builder()
                    .email(hasText(signupDto.getEmail()) ? signupDto.getEmail() : "")
                    .gender(hasText(signupDto.getGender()) ? getGender(signupDto.getGender()) : Gender.ETC)
                    .password(passwordEncoder.encode(password))
                    .platformId(id)
                    .nickname(hasText(signupDto.getNickname()) ? signupDto.getNickname() : "")
                    .ageRange(hasText(signupDto.getAge()) ? AgeRange.get(Integer.parseInt(signupDto.getAge().substring(0, 1))) : AgeRange.ETC)
                    .auth(UserRole.ROLE_NORMAL)
                    .signupType(SignupType.APPLE)
                    .userStatus(true)
                    .termsAgree("Y")
                    .build();
            user = userRepository.save(user);
            UserImage userImage = UserImage.builder().imageUrl(defaultImage).user(user).build();
            userImageSaveRepository.save(userImage);
        }
        return userRepository.findByPlatformId(id, SignupType.APPLE).orElseThrow(() -> new CustomException(ErrorCode.APPLE_SIMPLE_LOGIN_ERROR.getMessage(), ErrorCode.APPLE_SIMPLE_LOGIN_ERROR));
    }

    private Gender getGender(String gender){
        if (gender.equals("M") || gender.equals("m"))
            return Gender.MALE;
        else if(gender.equals("F") || gender.equals("f"))
            return Gender.FEMALE;
        return Gender.ETC;
    }
}
