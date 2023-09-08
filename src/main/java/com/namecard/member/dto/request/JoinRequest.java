package com.namecard.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class JoinRequest {

    @Schema(description = "가입에 사용할 이메일", example = "test@test.com")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @Schema(description = "사용자 이름", example = "테스터")
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @Schema(description = "비밀번호(비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.)", example = "qwer1234!@")
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String passwd;

    @Schema(description = "사용자 전화번호", example = "010-1234-5678")
    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    private String phoneNum;

    @Schema(hidden = true)
    private String encodePasswd;

    @Schema(description = "본인 한줄 소개")
    private String introduce;
}
