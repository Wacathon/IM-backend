package com.namecard.feedback.dto.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
public class FeedbackResult {

    @Schema(description = "피드백 대상자와의 관계")
    private String relationship;
    @Schema(description = "공통 질문에 대한 답변")
    private String commonAnswer;

    @Builder
    public FeedbackResult(String relationship, String commonAnswer) {
        this.relationship = relationship;
        this.commonAnswer = commonAnswer;
    }
}
