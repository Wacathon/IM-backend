package com.namecard.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SendAuthRequest {

    @Schema(description = "전화번호", example = "010-1234-5678")
    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    private String phoneNum;

}
