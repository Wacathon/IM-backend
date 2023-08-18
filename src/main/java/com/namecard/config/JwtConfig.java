package com.namecard.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.namecard.member.dto.entity.Users;
import com.namecard.member.dto.entity.Role;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
@Setter(value = AccessLevel.PRIVATE)
public class JwtConfig {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.access.expiration}")
    private long accessTokenValidityInSeconds;
    @Value("${jwt.refresh.expiration}")
    private long refreshTokenValidityInSeconds;
    @Value("${jwt.access.header}")
    private String accessHeader;
    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String USERID_CLAIM = "memberNo";
    private static final String USER_ROLE = "ROLE_USER";
    private static final String BEARER = "Bearer ";

    public String createAccessToken(Users users) {
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenValidityInSeconds))
                .withClaim(USERID_CLAIM, users.getUserId())
                .withClaim(USER_ROLE, Role.USER.name())
                .sign(Algorithm.HMAC512(secret));
    }

    public String createRefreshToken() {
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenValidityInSeconds))
                .sign(Algorithm.HMAC512(secret));
    }

    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader)).filter(
                accessToken -> accessToken.startsWith(BEARER)
        ).map(accessToken -> accessToken.replace(BEARER, ""));
    }

    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader)).filter(
                refreshToken -> refreshToken.startsWith(BEARER)
        ).map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    public Optional<String> extractMemberNo(String accessToken) {
        try {
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secret)).build().verify(accessToken).getClaim(USERID_CLAIM).toString());
        }catch (Exception e){
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<String> extractMemberRole(String accessToken) {
        try {
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secret)).build().verify(accessToken).getClaim(USER_ROLE).asString());
        }catch (Exception e){
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    public boolean isTokenValid(String token){
        try {
            JWT.require(Algorithm.HMAC512(secret)).build().verify(token);
            return true;
        }catch (Exception e){
            log.error("유효하지 않은 Token입니다", e.getMessage());
            return false;
        }
    }

    public String getAccessHeader() {
        return this.accessHeader;
    }

    public String getRefreshHeader() {
        return this.refreshHeader;
    }
}