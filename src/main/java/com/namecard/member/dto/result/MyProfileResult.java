package com.namecard.member.dto.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MyProfileResult {

    @Schema(description = "사용자 이름")
    private String name;

    @Schema(description = "사용자 이메일")
    private String email;

    @Schema(description = "사용자 닉네임")
    private String nickname;

    @Schema(description = "사용자 전화번호")
    private String phoneNum;

    @Schema(description = "자기소개")
    private String introduce;

}
