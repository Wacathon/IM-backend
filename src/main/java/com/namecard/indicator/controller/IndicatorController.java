package com.namecard.indicator.controller;

import com.namecard.config.ApiResultUtil.ApiResult;
import com.namecard.config.JwtConfig;
import com.namecard.exception.UnauthorizedException;
import com.namecard.indicator.dto.request.IndicatorRequest;
import com.namecard.indicator.dto.result.IndicatorInfoResult;
import com.namecard.indicator.dto.result.MyIndicatorInfoResult;
import com.namecard.indicator.service.IndicatorService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

import static com.namecard.config.ApiResultUtil.success;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/indicator")
public class IndicatorController {

    private final IndicatorService indicatorService;
    private final JwtConfig jwtConfig;

    @ApiOperation(value="내 평가정보 조회")
    @GetMapping("")
    public ApiResult<List<MyIndicatorInfoResult>> getMyIndicatorInfo(
            HttpServletRequest request
    ) {
        String accessToken = jwtConfig.extractAccessToken(request).orElseThrow(
                () -> new UnauthorizedException("엑세스 토큰이 필요합니다.")
        );

        Optional<String> SUserId = jwtConfig.extractMemberNo(accessToken);
        long userId = 0;
        if(SUserId.isPresent()) {
            userId = Long.parseLong(SUserId.get());
        }

        List<MyIndicatorInfoResult> result = indicatorService.getMyIndicatorInfo(userId);
        return success(result);
    }

    @ApiOperation(value="내 평가 정보 세팅")
    @PostMapping("")
    public ApiResult<Boolean> saveIndicatorConnector(
            @RequestBody IndicatorRequest dto,
            HttpServletRequest request
    ) {
        String accessToken = jwtConfig.extractAccessToken(request).orElseThrow(
                () -> new UnauthorizedException("엑세스 토큰이 필요합니다.")
        );

        Optional<String> SUserId = jwtConfig.extractMemberNo(accessToken);
        long userId = 0;
        if(SUserId.isPresent()) {
            userId = Long.parseLong(SUserId.get());
        }
        dto.addUserId(userId);
        indicatorService.indicatorSave(dto);
        return success();
    }

    @ApiOperation(value="유저 명함 정보 조회")
    @GetMapping("/{userId}")
    public ApiResult<IndicatorInfoResult> getIndicatorInfo(
            @PathVariable(value = "userId") long userId
    ) {
        IndicatorInfoResult result =  indicatorService.getIndicatorInfo(userId);
        return success(result);
    }
}
