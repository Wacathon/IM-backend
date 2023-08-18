package com.namecard.indicator.dto.result;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyIndicatorInfoResult {
    private long tagId;
    private String tagName;
    private double avrgTagScore;
}
