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
    private List<IndicatorScoreResult> myScoreList;
    private List<IndicatorScoreResult> friendsScoreList;

    @Data
    @AllArgsConstructor
    @Builder
    public static class IndicatorScoreResult {
        private long indicatorId;
        private String tagName;
        private int tagScore;
    }
}
