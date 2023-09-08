package com.namecard.member.controller;

import com.namecard.config.ApiResultUtil.ApiResult;
import com.namecard.member.dto.request.JoinRequest;
import com.namecard.member.dto.request.LoginRequest;
import com.namecard.member.dto.request.MyProfileRequest;
import com.namecard.member.dto.request.PasswdResetRequest;
import com.namecard.member.dto.result.LoginResult;
import com.namecard.member.dto.result.MyProfileResult;
import com.namecard.member.service.MemberService;
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
public class MemberApiController {

    private final MemberService memberService;

    @ApiOperation(value = "회원가입")
    @PostMapping("/sign-up")
    public ApiResult<Boolean> signUp(
            @Valid @RequestBody JoinRequest joinRequest
    ) {
        memberService.signUp(joinRequest);
        return success();
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/sign-in")
    public ApiResult<LoginResult> signIn(
            @Valid @RequestBody LoginRequest loginRequest
    ) {
        return success(memberService.signIn(loginRequest));
    }

    @ApiOperation(value = "비밀번호 변경 [엑세스 토큰 필요]")
    @PutMapping("/password")
    public ApiResult<Boolean> passwdReset(
            @Valid @RequestBody PasswdResetRequest request,
            @ApiParam(hidden = true) @AuthenticationPrincipal String memberId
    ) {
        memberService.resetNewPassword(request, memberId);
        return success();
    }

    @ApiOperation(value = "내 프로필 조회 [엑세스 토큰 필요]")
    @GetMapping("")
    public ApiResult<MyProfileResult> getMyProfile(
            @ApiParam(hidden = true) @AuthenticationPrincipal String memberId
    ) {
        MyProfileResult result = memberService.getMyProfile(memberId);
        return success(result);
    }

    @ApiOperation(value = "내 정보 수정 [엑세스 토큰 필요]")
    @PutMapping("")
    public ApiResult<Boolean> changeProfile(
            @ApiParam(hidden = true) @AuthenticationPrincipal String memberId,
            @RequestBody MyProfileRequest profileRequest
    ) {
        memberService.changeMyProfile(profileRequest, memberId);
        return success();
    }
}
