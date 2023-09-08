package com.namecard.auth.controller;

import com.namecard.auth.service.AuthService;
import com.namecard.member.dto.result.LoginResult;
import com.namecard.config.ApiResultUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.namecard.config.ApiResultUtil.*;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthApiController {

    private final AuthService authService;

    @ApiOperation(value = "리프레시 토큰으로 토큰 재발급 [엑세스 토큰 및 리프레시 토큰 필수]")
    @PostMapping("/renew")
    public ApiResultUtil.ApiResult<LoginResult> refresh(HttpServletRequest request) {
        return success(authService.generateNewToken(request));
    }
}
