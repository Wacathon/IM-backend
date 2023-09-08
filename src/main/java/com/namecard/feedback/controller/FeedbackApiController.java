package com.namecard.feedback.controller;

import com.namecard.feedback.dto.request.FeedbackListRequest;
import com.namecard.feedback.dto.request.FeedbackPinnedRequest;
import com.namecard.feedback.dto.request.FeedbackRequest;
import com.namecard.feedback.dto.result.FeedbackResult;
import com.namecard.feedback.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.namecard.config.ApiResultUtil.*;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackApiController {

    private final FeedbackService feedbackService;

    @PostMapping("/{memberId}")
    public ApiResult<Boolean> createFeedback(
            @PathVariable("memberId") Long memberId,
            @RequestBody FeedbackRequest feedbackRequest
    ) {
        feedbackService.createFeedback(memberId, feedbackRequest);
        return success();
    }

    @GetMapping("")
    public ApiResult<List<FeedbackResult>> loadUsersFeedbacks(
            FeedbackListRequest feedback
    ) {
        return success(feedbackService.getFeedbackList(feedback));
    }

    @PostMapping("/pinned")
    public ApiResult<Boolean> changePinned(
            @RequestBody FeedbackPinnedRequest pinnedRequest
    ) {
        feedbackService.changePinned(pinnedRequest);
        return success();
    }
}
