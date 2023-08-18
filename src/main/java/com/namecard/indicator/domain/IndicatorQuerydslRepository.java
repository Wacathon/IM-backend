package com.namecard.indicator.domain;

import com.namecard.indicator.dto.result.IndicatorInfoResult;

import java.util.List;

public interface IndicatorQuerydslRepository {
    List<IndicatorInfoResult.IndicatorScoreResult> findIndicatorScoreByUserId(long userId);
}
