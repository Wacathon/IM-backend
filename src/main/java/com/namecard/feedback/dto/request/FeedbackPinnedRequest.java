package com.namecard.feedback.dto.request;

import lombok.Data;

@Data
public class FeedbackPinnedRequest {
    private long answerId;
    private boolean pinned;
}
