package com.namecard.users.controller;

import com.namecard.config.ApiResultUtil.ApiResult;
import com.namecard.exception.UnauthorizedException;
import com.namecard.users.dto.request.JoinRequest;
import com.namecard.users.dto.request.LoginRequest;
import com.namecard.users.dto.request.MyProfileRequest;
import com.namecard.users.dto.request.PasswdResetRequest;
import com.namecard.users.dto.result.LoginResult;
import com.namecard.users.dto.result.MyProfileResult;
import com.namecard.users.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.namecard.config.ApiResultUtil.success;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class UsersApiController {

    private final UserService memberService;

    @ApiOperation(value = "회원가입")
    @PostMapping("/join")
    public ApiResult<Boolean> join(@Valid @RequestBody JoinRequest joinRequest) {
        memberService.join(joinRequest);
        return success();
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ApiResult<LoginResult> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResult result = memberService.login(loginRequest);

        return success(result);
    }


    @ApiOperation(value = "비밀번호 재설정(전화번호 인증 필수)")
    @PutMapping("/passwdReset")
    public ApiResult<Boolean> passwdReset(@Valid @RequestBody PasswdResetRequest request) {
        memberService.passwdReset(request);
        return success();
    }

    @ApiOperation(value = "내 프로필 조회(엑세스 토큰 필수)")
    @GetMapping("/myProfile")
    public ApiResult<MyProfileResult> myProfile(@ApiParam(hidden = true) @AuthenticationPrincipal String SMemberNo) {
        long memberNo = 0;
        try {
            memberNo = Long.parseLong(SMemberNo);
        } catch (Exception e) {
            throw new UnauthorizedException("토큰이 필요합니다.");
        }
        MyProfileResult result = memberService.myProfile(memberNo);

        return success(result);
    }

    @ApiOperation(value = "자기소개 수정")
    @PostMapping("/introduce")
    public ApiResult<Boolean> changeIntroduce(
            @ApiParam(hidden = true) @AuthenticationPrincipal String SUserId,
            @RequestBody MyProfileRequest profileRequest
    ) {
        long userId = 0;
        try {
            userId = Long.parseLong(SUserId);
        } catch (Exception e) {
            throw new UnauthorizedException("토큰이 필요합니다.");
        }
        memberService.changeMyProfile(profileRequest, userId);
        return success();
    }

}