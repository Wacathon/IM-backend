package com.namecard.feedback.repository;

import com.namecard.feedback.dto.request.FeedbackListRequest;
import com.namecard.feedback.dto.result.FeedbackResult;

import java.util.List;

public interface FeedbackQuerydslRepository {
    List<FeedbackResult> getFeedbackList(FeedbackListRequest feedbackListRequest);

    List<FeedbackResult> getFeedbackListLimit3(FeedbackListRequest feedbackListRequest);
}
