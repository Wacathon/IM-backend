package com.namecard.feedback.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeedbackAnswerRequest {

    private Long questionId;
    private String title;
    private String content;
}
