package com.namecard.answer.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnswerRequest {
    private String answer;

    @Builder
    public AnswerRequest(String answer) {
        this.answer = answer;
    }
}
