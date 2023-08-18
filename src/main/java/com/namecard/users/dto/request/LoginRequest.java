package com.namecard.users.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

    @Schema(description = "이메일", example = "tester@tester.com")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @Schema(description = "비밀번호", example = "1234")
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String passwd;

}
