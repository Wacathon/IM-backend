package com.namecard.feedback.domain;

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
public class Feedback extends AuditBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "feedback_seq")
    @Schema(description = "피드백 고유값(PK)")
    private Long feedbackId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "memberId")
    @Schema(description = "피드백 대상 유저")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Schema(description = "피드백 대상자와의 관계")
    private Relationship relationship;

    @Builder
    public Feedback(Member member, Relationship relationship) {
        this.member = member;
        this.relationship = relationship;
    }
}
