package com.namecard.indicator.dto.result;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IndicatorListResult {
    private Long tagId;
    private String tagName;

    @Builder
    public IndicatorListResult(Long tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }
}
