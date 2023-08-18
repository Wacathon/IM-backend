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

    @PostMapping("/")
    public ApiResult<Boolean> createFeedback(@RequestBody FeedbackRequest feedbackRequest, HttpServletRequest request) {
        String accessToken = jwtConfig.extractAccessToken(request).orElseThrow(
                () -> new UnauthorizedException("엑세스 토큰이 필요합니다.")
        );
        Optional<String> SMemberNo = jwtConfig.extractMemberNo(accessToken);
        long userId = 0;
        if (SMemberNo.isPresent()) {
            userId = Long.parseLong(SMemberNo.get());
        }

        feedbackService.createFeedback(userId, feedbackRequest);
        return success();
    }

    @GetMapping("/{userId}")
    public ApiResult<List<FeedbackResult>> loadUsersFeedbacks(@PathVariable("userId") Long userId) {
        return success(feedbackService.getFeedbackList(userId));
    }
}
