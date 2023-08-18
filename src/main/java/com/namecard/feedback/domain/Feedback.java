package com.namecard.feedback.domain;

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
public class Feedback extends AuditBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "feedback_seq")
    @Schema(description = "피드백 고유값(PK)")
    private Long feedbackId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    @Schema(description = "피드백 대상 유저")
    private Users users;

    @Enumerated(EnumType.STRING)
    @Schema(description = "피드백 대상자와의 관계")
    private Relationship relationship;

    @Builder
    public Feedback(Users users, Relationship relationship) {
        this.users = users;
        this.relationship = relationship;
    }
}
