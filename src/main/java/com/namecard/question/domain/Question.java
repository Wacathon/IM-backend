package com.namecard.question.domain;

import com.namecard.config.AuditBaseEntity;
import com.namecard.users.dto.entity.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Question extends AuditBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "사용자 설정 질문 PK")
    private Long questionId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    @Schema(description = "사용자 설정 질문을 만들 유저")
    private Users users;

    @Schema(description = "사용자 설정 질문 내용")
    private String title;

    @Builder
    public Question(Users users, String title) {
        this.users = users;
        this.title = title;
    }

    public void updateTitle(String newTitle){
        this.title = newTitle;
    }
}
