package com.namecard.member.domain;

import com.namecard.config.AuditBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;


@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Member extends AuditBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "members_seq")
    @Schema(description = "사용자 고유값(PK)")
    private long memberId;

    @Schema(description = "사용자명", example = "테스터")
    private String userName;

    @Schema(description = "로그인 이메일")
    private String email;

    @Schema(description = "로그인 비밀번호")
    private String passwd;

    @Schema(description = "로그인 횟수. 로그인시 1씩 증")
    private int loginCount;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cardId")
    private Card card;

    @Schema(description = "최종 로그인 일자")
    private LocalDateTime lastLoginDt;

    public void afterLoginSuccess() {
        this.loginCount++;
        this.lastLoginDt = now();
    }

    public void updatePassword(String passwd) {
        this.passwd = passwd;
    }
}