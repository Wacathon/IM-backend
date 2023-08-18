package com.namecard.auth.service;

import com.namecard.auth.dto.request.SendAuthRequest;
import com.namecard.auth.dto.request.ValidAuthRequest;
import com.namecard.auth.dto.result.AuthResult;
import com.namecard.config.JwtConfig;
import com.namecard.exception.UnauthorizedException;
import com.namecard.users.dto.entity.Users;
import com.namecard.users.dto.result.LoginResult;
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
    public LoginResult newAccessToken(HttpServletRequest request) {
        String accessToken = jwtConfig.extractAccessToken(request).orElseThrow(
                () -> new UnauthorizedException("엑세스 토큰이 필요합니다.")
        );
        String refreshToken = jwtConfig.extractRefreshToken(request).orElseThrow(
                () -> new UnauthorizedException("리프레시 토큰이 없습니다. 로그인이 필요합니다.")
        );

        Optional<String> SMemberNo = jwtConfig.extractMemberNo(accessToken);
        long memberNo = 0;
        if(SMemberNo.isPresent()) {
            memberNo = Long.parseLong(SMemberNo.get());
        }

        String redisRefreshToken = redisService.findRefreshToken(memberNo);

        if(refreshToken.equals(redisRefreshToken) && jwtConfig.isTokenValid(refreshToken)) {
            Users users = Users.builder().userId(memberNo).build();
            LoginResult result = redisService.tokenCreate(users);
            return result;
        }
        throw new UnauthorizedException("로그인이 필요합니다.");
    }

    @Transactional(rollbackFor = Exception.class)
    public AuthResult sendAuth(SendAuthRequest request) {
        String authCode = this.randomAuthCode();
        redisService.savePhoneAuthCode(request.getPhoneNum(), authCode);

        return AuthResult.builder().authCode(authCode).build();
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
        if(redisAuthCode == null) {
            throw new IllegalArgumentException("인증 정보가 없습니다.");
        }
        if(redisAuthCode.equals(request.getAuthCode())) {
            redisService.delete(request.getPhoneNum());
            redisService.savePhoneAuthSuccess(request.getPhoneNum());
            return true;
        }

        return false;
    }

}
