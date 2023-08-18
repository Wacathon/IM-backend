package com.namecard.feedback.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
public class FeedbackIndicatorRequest {
    private Long tagId;
    private Integer tagScore;

    @Builder
    public FeedbackIndicatorRequest(Long tagId, Integer tagScore) {
        this.tagId = tagId;
        this.tagScore = tagScore;
    }
}
