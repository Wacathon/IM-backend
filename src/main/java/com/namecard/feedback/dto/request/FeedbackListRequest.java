package com.namecard.feedback.dto.request;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class FeedbackListRequest {

    private long userId;
    private Boolean pinned;

    public void addUserId(long userId) {
        this.userId = userId;
    }
}
