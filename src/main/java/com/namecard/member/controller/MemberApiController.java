package com.namecard.member.controller;

import com.namecard.exception.UnauthorizedException;
import com.namecard.member.dto.request.JoinRequest;
import com.namecard.member.dto.request.LoginRequest;
import com.namecard.member.dto.request.PasswdResetRequest;
import com.namecard.member.dto.result.LoginResult;
import com.namecard.member.dto.result.MyProfileResult;
import com.namecard.member.service.MemberService;
import com.namecard.config.ApiResultUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberApiController {

    private final MemberService memberService;

    @ApiOperation(value = "회원가입")
    @PostMapping("/join")
    public ApiResultUtil.ApiResult<Boolean> join(@Valid @RequestBody JoinRequest joinRequest) {
        memberService.join(joinRequest);
        return ApiResultUtil.success();
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ApiResultUtil.ApiResult<LoginResult> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResult result = memberService.login(loginRequest);

        return ApiResultUtil.success(result);
    }


    @ApiOperation(value = "비밀번호 재설정(전화번호 인증 필수)")
    @PutMapping("/passwdReset")
    public ApiResultUtil.ApiResult<Boolean> passwdReset(@Valid @RequestBody PasswdResetRequest request) {
        memberService.passwdReset(request);
        return ApiResultUtil.success();
    }

    @ApiOperation(value = "내 프로필 조회(엑세스 토큰 필수)")
    @GetMapping("/myProfile")
    public ApiResultUtil.ApiResult<MyProfileResult> myProfile(@ApiParam(hidden = true) @AuthenticationPrincipal String SMemberNo) {
        long memberNo = 0;
        try {
            memberNo = Long.parseLong(SMemberNo);
        } catch (Exception e) {
            throw new UnauthorizedException("토큰이 필요합니다.");
        }
        MyProfileResult result = memberService.myProfile(memberNo);

        return ApiResultUtil.success(result);
    }
}
