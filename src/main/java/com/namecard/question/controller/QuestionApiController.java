package com.namecard.question.controller;

import com.namecard.config.JwtConfig;
import com.namecard.exception.UnauthorizedException;
import com.namecard.question.dto.request.QuestionRequest;
import com.namecard.question.dto.result.QuestionResult;
import com.namecard.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static com.namecard.config.ApiResultUtil.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/question")
public class QuestionApiController {

    private final QuestionService questionService;
    private final JwtConfig jwtConfig;

    @PostMapping("")
    public ApiResult<Boolean> createQuestion(
            @RequestBody QuestionRequest questionRequest, HttpServletRequest request
    ) {
        String accessToken = jwtConfig.extractAccessToken(request).orElseThrow(
                () -> new UnauthorizedException("엑세스 토큰이 필요합니다.")
        );
        Optional<String> SMemberNo = jwtConfig.extractMemberNo(accessToken);
        long userId = 0;
        if (SMemberNo.isPresent()) {
            userId = Long.parseLong(SMemberNo.get());
        }
        questionService.createQuestion(userId, questionRequest);
        return success();
    }

    @PutMapping("/{questionId}")
    public ApiResult<Boolean> modifyQuestion(
            @PathVariable("questionId") Long questionId,
            @RequestBody QuestionRequest questionRequest,
            HttpServletRequest request
    ) {
        String accessToken = jwtConfig.extractAccessToken(request).orElseThrow(
                () -> new UnauthorizedException("엑세스 토큰이 필요합니다.")
        );
        Optional<String> SMemberNo = jwtConfig.extractMemberNo(accessToken);
        long userId = 0;
        if (SMemberNo.isPresent()) {
            userId = Long.parseLong(SMemberNo.get());
        }

        questionService.modifyQuestion(userId, questionId, questionRequest);
        return success();
    }

    @DeleteMapping("/{questionId}")
    public ApiResult<Boolean> deleteQuestion(
            @PathVariable("questionId") Long questionId,
            HttpServletRequest request
    ) {
        String accessToken = jwtConfig.extractAccessToken(request).orElseThrow(
                () -> new UnauthorizedException("엑세스 토큰이 필요합니다.")
        );
        Optional<String> SMemberNo = jwtConfig.extractMemberNo(accessToken);
        long userId = 0;
        if (SMemberNo.isPresent()) {
            userId = Long.parseLong(SMemberNo.get());
        }

        questionService.deleteQuestion(userId, questionId);
        return success();
    }

    @GetMapping("/{userId}")
    public ApiResult<List<QuestionResult>> loadUsersCustomQuestions(
            @PathVariable("userId") Long userId
    ) {
        return success(questionService.loadQuestions(userId));
    }

}
