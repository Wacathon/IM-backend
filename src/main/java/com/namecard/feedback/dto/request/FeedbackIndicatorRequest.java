package com.namecard.feedback.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeedbackIndicatorRequest {
    private Long tagId;
    private Integer tagScore;

    @Builder
    public FeedbackIndicatorRequest(Long tagId, Integer tagScore) {
        this.tagId = tagId;
        this.tagScore = tagScore;
    }
}
