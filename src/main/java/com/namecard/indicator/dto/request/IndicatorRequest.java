package com.namecard.indicator.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class IndicatorRequest {
    private long userId;
    private List<IndicatorTag> tagList;

    public void addUserId(long userId) {
        this.userId = userId;
    }

    @Data
    public static class IndicatorTag {
        private long tagId;
    }
}
