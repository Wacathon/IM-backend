package com.namecard.feedback.dto.request;

import lombok.Data;

@Data
public class FeedbackAnswerRequest {

    private Long questionId;
    private String title;
    private String content;
}
