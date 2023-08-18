package com.namecard.indicator.dto.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class IndicatorInfoResult {
    private String userName;
    private String introduce;
    private String email;
    private String phoneNum;
    private List<IndicatorScoreResult> scoreList;

    @Data
    @AllArgsConstructor
    @Builder
    public static class IndicatorScoreResult {
        private String tagName;
        private double tagScore;
    }
}
