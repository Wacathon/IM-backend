package com.namecard.feedback.domian;

import com.namecard.config.AuditBaseEntity;
import com.namecard.member.dto.entity.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Feedback extends AuditBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "feedback_seq")
    @Schema(description = "피드백 고유값(PK)")
    private Long feedbackId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    @Schema(description = "피드백 대상 유저")
    private Users users;

    @Column(length = 500)
    @Schema(description = "공통 질문에 대한 답변")
    private String commonAnswer;

    @Enumerated(EnumType.STRING)
    @Schema(description = "피드백 대상자와의 관계")
    private Relationship relationship;

    @Builder
    public Feedback(Users users, String commonAnswer, Relationship relationship) {
        this.users = users;
        this.commonAnswer = commonAnswer;
        this.relationship = relationship;
    }
}
