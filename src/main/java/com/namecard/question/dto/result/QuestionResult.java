package com.namecard.question.dto.result;

import lombok.Builder;
import lombok.Data;

@Data
public class QuestionResult {

    private Long questionId;
    private String title;

    @Builder
    public QuestionResult(Long questionId, String title) {
        this.questionId = questionId;
        this.title = title;
    }
}
