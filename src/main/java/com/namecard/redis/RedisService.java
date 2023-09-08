package com.namecard.redis;

import com.namecard.config.jwt.JwtConfig;
import com.namecard.member.domain.Member;
import com.namecard.member.dto.result.LoginResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    private final JwtConfig jwtConfig;

    @Value("${jwt.refresh.expiration}")
    private long refreshTokenValidityInSeconds;

    private final long phoneAuthValidityInSeconds = 180000;

    private final long phoneAuthSuccessValidityInSeconds = 86400000; //1일

    public void saveRefreshToken(long memberNo, String refreshToken) {
        redisTemplate.opsForValue()
                .set(String.valueOf(memberNo),
                        refreshToken,
                        refreshTokenValidityInSeconds,
                        TimeUnit.MILLISECONDS);
    }

    public String findRefreshToken(long memberNo) {
        return redisTemplate.opsForValue()
                .get(String.valueOf(memberNo));
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void savePhoneAuthCode(String phoneNum, String authCode) {
        redisTemplate.opsForValue()
                .set(String.valueOf(phoneNum),
                        authCode,
                        phoneAuthValidityInSeconds,
                        TimeUnit.MILLISECONDS);
    }

    public String findPhoneAuthCode(String phoneNum) {
        return redisTemplate.opsForValue()
                .get(String.valueOf(phoneNum));
    }

    public void savePhoneAuthSuccess(String phoneNum) {
        redisTemplate.opsForValue()
                .set(String.valueOf(phoneNum),
                        "Y",
                        phoneAuthSuccessValidityInSeconds,
                        TimeUnit.MILLISECONDS);
    }

    public String findPhoneAuthSuccess(String phoneNum) {
        return redisTemplate.opsForValue()
                .get(String.valueOf(phoneNum));
    }

    @Transactional(rollbackFor = Exception.class)
    public LoginResult createNewTokens(Member member) {
        String accessToken = jwtConfig.createAccessToken(member);
        String refreshToken = jwtConfig.createRefreshToken();

        this.saveRefreshToken(member.getMemberId(), refreshToken); // 레디스에 리프레시 토큰 저장
        return LoginResult.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
