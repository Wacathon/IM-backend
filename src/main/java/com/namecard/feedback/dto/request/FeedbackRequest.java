package com.namecard.feedback.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class FeedbackRequest {

    @Schema(description = "피드백 대상자와의 관계", example = "SCHOOL_COLLEAGUE")
    @NotBlank(message = "대상자와의 관계는 필수 입력 값입니다.")
    private String relationship;

    private List<FeedbackAnswerRequest> answers;

    private List<FeedbackIndicatorRequest> indicators;

    @Builder
    public FeedbackRequest(String relationship, List<FeedbackAnswerRequest> answers, List<FeedbackIndicatorRequest> indicators) {
        this.relationship = relationship;
        this.answers = answers;
        this.indicators = indicators;
    }
}
