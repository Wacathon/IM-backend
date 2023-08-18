package com.namecard.feedback.dto.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeedbackResult {
    private long answerId;
    private String questionTitle;
    private String title;
    private String content;
    private boolean pinned;
}
