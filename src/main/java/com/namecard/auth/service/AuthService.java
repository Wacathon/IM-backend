package com.namecard.auth.service;

import com.namecard.auth.dto.request.SendAuthRequest;
import com.namecard.auth.dto.request.ValidAuthRequest;
import com.namecard.auth.dto.result.AuthResult;
import com.namecard.config.jwt.JwtConfig;
import com.namecard.exception.UnauthorizedException;
import com.namecard.member.domain.Member;
import com.namecard.member.dto.result.LoginResult;
import com.namecard.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final JwtConfig jwtConfig;

    private final RedisService redisService;

    @Transactional(rollbackFor = Exception.class)
    public LoginResult generateNewToken(HttpServletRequest request) {
        String accessToken = jwtConfig.extractAccessToken(request).orElseThrow(
                () -> new UnauthorizedException("엑세스 토큰이 필요합니다.")
        );
        String refreshToken = jwtConfig.extractRefreshToken(request).orElseThrow(
                () -> new UnauthorizedException("리프레시 토큰이 없습니다. 로그인이 필요합니다.")
        );

        // JWT 엑세스 토큰으로부터 회원 ID 추출
        Optional<String> strMemberId = jwtConfig.extractMemberNo(accessToken);
        long memberNo = 0;
        if (strMemberId.isPresent()) {
            memberNo = Long.parseLong(strMemberId.get());
        }

        String redisRefreshToken = redisService.findRefreshToken(memberNo);

        // 요청으로 받은 refresh 토큰과 redis에 저장된 토큰 비교하고 요청으로 받은 토큰이 유효하면
        if (refreshToken.equals(redisRefreshToken) && jwtConfig.isTokenValid(refreshToken)) {
            Member member = Member.builder()
                    .memberId(memberNo)
                    .build();
            return redisService.createNewTokens(member);
        }
        throw new UnauthorizedException("로그인이 필요합니다.");
    }

    @Transactional(rollbackFor = Exception.class)
    public AuthResult sendAuth(SendAuthRequest request) {
        String authCode = this.randomAuthCode();
        redisService.savePhoneAuthCode(request.getPhoneNum(), authCode);

        return AuthResult.builder()
                .authCode(authCode)
                .build();
    }

    private String randomAuthCode() {
        Random rand = new Random();
        String numStr = "";
        for (int i = 0; i < 4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr += ran;
        }
        return numStr;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean validAuth(ValidAuthRequest request) {
        String redisAuthCode = redisService.findPhoneAuthCode(request.getPhoneNum());
        if (redisAuthCode == null) {
            throw new IllegalArgumentException("인증 정보가 없습니다.");
        }
        if (redisAuthCode.equals(request.getAuthCode())) {
            redisService.delete(request.getPhoneNum());
            redisService.savePhoneAuthSuccess(request.getPhoneNum());
            return true;
        }

        return false;
    }

}
