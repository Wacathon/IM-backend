package com.namecard.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ValidAuthRequest {

    @Schema(description = "전화번호", example = "010-1234-5678")
    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    private String phoneNum;

    @Schema(description = "인증번호", example = "1234")
    @NotBlank(message = "인증번호는 필수 입력 값입니다.")
    private String authCode;

}
