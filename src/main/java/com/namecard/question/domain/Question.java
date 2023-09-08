package com.namecard.question.domain;

import com.namecard.config.AuditBaseEntity;
import com.namecard.member.domain.Member;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    @Schema(description = "사용자 설정 질문을 만들 유저")
    private Member member;

    @Schema(description = "사용자 설정 질문 내용")
    private String title;

    @Builder
    public Question(Member member, String title) {
        this.member = member;
        this.title = title;
    }

    public void updateTitle(String newTitle){
        this.title = newTitle;
    }
}
