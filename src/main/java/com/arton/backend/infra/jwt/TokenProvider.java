package com.arton.backend.infra.jwt;

import com.arton.backend.auth.application.data.TokenDto;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TokenProvider {
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";
    private static final long TOKEN_EXPIRE_TIME = 1000 * 60  * 60 * 24; // 테스트 용 만료 1일
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 60; // 테스트 용 만료 1달

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final Key key;

    public TokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] decode = Base64.getDecoder().decode(secretKey);
        this.key = Keys.hmacShaKeyFor(decode);
    }

    public TokenDto generateAccessToken(Authentication authentication, String refreshToken) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        // set expiration time
        long now = (new Date()).getTime();
        Date exp = new Date(now + TOKEN_EXPIRE_TIME);
        Date refreshExp = parseClaims(refreshToken).getExpiration();

        // create access token
        String accessToken = Jwts.builder().setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        return TokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(exp.getTime())
                .refreshToken(refreshToken)
                .refreshTokenExpiresIn(refreshExp.getTime())
                .build();
    }

    /**
     * 리프레쉬 토큰과, 액세스 토큰을 함께 발행한다.
     * @param authentication
     * @return
     */
    public TokenDto generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        // set expiration time
        long now = (new Date()).getTime();
        Date exp = new Date(now + TOKEN_EXPIRE_TIME);
        Date refreshExp = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        // create access token
        String accessToken = Jwts.builder().setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        String refreshToken = Jwts.builder().setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(refreshExp)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        return TokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiresIn(exp.getTime())
                .refreshTokenExpiresIn(refreshExp.getTime())
                .build();
    }

    public TokenDto generateForAdminToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        // set expiration time
        long now = (new Date()).getTime();
        // for monitoring token
        Long max_time = 0x7fffffffffffffffL;
        Date exp = new Date(max_time.longValue());
        Date refreshExp = new Date(max_time.longValue());

        // create access token
        String accessToken = Jwts.builder().setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        String refreshToken = Jwts.builder().setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(refreshExp)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        return TokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiresIn(exp.getTime())
                .refreshTokenExpiresIn(refreshExp.getTime())
                .build();
    }

    public Authentication getAuthentication(String accessToken) {
        // decode
        Claims claims = parseClaims(accessToken);

        // no authority
        if (claims.get(AUTHORITIES_KEY)==null){
            throw new CustomException(ErrorCode.USER_NOT_AUTHORITY.getMessage(), ErrorCode.USER_NOT_AUTHORITY);
        }

        List<SimpleGrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal,"",authorities);
    }

    public Authentication getAuthenticationByRefreshToken(String refreshToken) {
        // decode
        Claims claims = parseClaims(refreshToken);

        // no authority
        if (claims.get(AUTHORITIES_KEY)==null){
            throw new CustomException(ErrorCode.USER_NOT_AUTHORITY.getMessage(), ErrorCode.USER_NOT_AUTHORITY);
        }

        List<SimpleGrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal,"",authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Wrong JWT Signature");
        } catch (ExpiredJwtException e) {
            log.info("Expired Token");
        } catch (UnsupportedJwtException e) {
            log.info("Not Supported JWT Token");
        } catch (IllegalArgumentException e) {
            log.info("The Token is invalid");
        }
        return false;
    }

    /**
     * 토큰 만료일 체크
     * @param accessToken
     * @return
     */
    public Long getExpiration(String accessToken) {
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody().getExpiration();
        long now = new Date().getTime();
        return expiration.getTime() - now;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String parseBearerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
