package com.namecard.member.dto.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MyProfileResult {

    @Schema(description = "회원 ID")
    private Long memberId;

    @Schema(description = "사용자 이름")
    private String name;

    @Schema(description = "사용자 이메일")
    private String email;

    @Schema(description = "사용자 전화번호")
    private String phoneNum;

    @Schema(description = "자기소개")
    private String introduce;

    @Schema(description = "이메일 공개 여부")
    @JsonProperty("isPublicEmail")
    private boolean isPublicEmail;

    @Schema(description = "전화번호 공개 여부")
    @JsonProperty("isPublicPhone")
    private boolean isPublicPhone;

    @Schema(description = "자기소개 공개 여부")
    @JsonProperty("isPublicIntroduce")
    private boolean isPublicIntroduce;

}
