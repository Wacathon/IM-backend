package com.namecard.indicator.repository;

import com.namecard.indicator.dto.result.IndicatorInfoResult;
import com.namecard.indicator.dto.result.MyIndicatorInfoResult;

import java.util.List;

public interface IndicatorQuerydslRepository {
    List<IndicatorInfoResult.IndicatorScoreResult> findIndicatorScoreByMemberId(long memberId);

    List<MyIndicatorInfoResult> findIndicatorInfoByMemberId(long memberId);
}
