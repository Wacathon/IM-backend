package com.namecard.member.dto.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;


@Builder
@Entity
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "members_seq")
    @Schema(description = "사용자 고유값(PK)")
    private long userId;

    @Schema(description = "사용자명", example = "테스터")
    private String userName;

    @Schema(description = "로그인 이메일")
    private String email;

    @Schema(description = "로그인 비밀번호")
    private String passwd;

    @Schema(description = "사용자 전화번호")
    private String phoneNum;

    @Schema(description = "로그인 횟수. 로그인시 1씩 증")
    private int loginCount;

    @Schema(description = "최종 로그인 일자")
    private LocalDateTime lastLoginDt;

    @Schema(hidden = true)
    private LocalDateTime createDt;

    public void afterLoginSuccess() {
        this.loginCount++;
        this.lastLoginDt = now();
    }

    public void updatePassword(String passwd) {
        this.passwd = passwd;
    }
}