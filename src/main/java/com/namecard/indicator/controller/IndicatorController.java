package com.namecard.indicator.controller;

import com.namecard.config.ApiResultUtil.ApiResult;
import com.namecard.config.JwtConfig;
import com.namecard.exception.UnauthorizedException;
import com.namecard.indicator.dto.request.IndicatorRequest;
import com.namecard.indicator.service.IndicatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static com.namecard.config.ApiResultUtil.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/indicator")
public class IndicatorController {

    private final IndicatorService indicatorService;
    private final JwtConfig jwtConfig;

    @PostMapping("/")
    public ApiResult<Boolean> saveIndicator(@RequestBody List<IndicatorRequest> indicatorList, HttpServletRequest request) {
        String accessToken = jwtConfig.extractAccessToken(request).orElseThrow(
                () -> new UnauthorizedException("엑세스 토큰이 필요합니다.")
        );
        Optional<String> SUserId = jwtConfig.extractMemberNo(accessToken);
        long userId = 0;
        if(SUserId.isPresent()) {
            userId = Long.parseLong(SUserId.get());
        }

        indicatorService.indicatorSave(indicatorList, userId);
        return success();
    }
}
