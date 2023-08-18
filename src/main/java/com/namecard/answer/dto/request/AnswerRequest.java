package com.namecard.answer.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
public class AnswerRequest {
    private String answer;

    @Builder
    public AnswerRequest(String answer) {
        this.answer = answer;
    }
}
