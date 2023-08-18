package com.namecard.auth.dto.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResult {

    @Schema(description = "인증번호")
    private String authCode;

}
