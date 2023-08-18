package com.namecard.feedback.controller;

import com.namecard.config.JwtConfig;
import com.namecard.exception.UnauthorizedException;
import com.namecard.feedback.dto.request.FeedbackRequest;
import com.namecard.feedback.dto.result.FeedbackResult;
import com.namecard.feedback.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

import static com.namecard.config.ApiResultUtil.*;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackApiController {

    private final FeedbackService feedbackService;
    private final JwtConfig jwtConfig;

    @PostMapping("/{userId}")
    public ApiResult<Boolean> createFeedback(
            @PathVariable("userId") Long userId,
            @RequestBody FeedbackRequest feedbackRequest
    ) {
        feedbackService.createFeedback(userId, feedbackRequest);
        return success();
    }

    @GetMapping("/{userId}")
    public ApiResult<List<FeedbackResult>> loadUsersFeedbacks(@PathVariable("userId") Long userId) {
        return success(feedbackService.getFeedbackList(userId));
    }
}
