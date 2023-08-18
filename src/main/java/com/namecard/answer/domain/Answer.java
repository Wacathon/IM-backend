package com.namecard.answer.domain;

import com.namecard.config.AuditBaseEntity;
import com.namecard.feedback.domain.Feedback;
import com.namecard.question.domain.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Answer extends AuditBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "questionId")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "feedbackId")
    private Feedback feedback;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    private Boolean pinned;

    @Builder
    public Answer(Question question, Feedback feedback, String title, String content, Boolean pinned) {
        this.question = question;
        this.feedback = feedback;
        this.title = title;
        this.content = content;
        this.pinned = pinned;
    }

    public void changePinned(Boolean pinned) {
        this.pinned = pinned;
    }
}
