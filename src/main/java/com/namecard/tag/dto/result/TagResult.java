package com.namecard.tag.dto.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TagResult {

    @Schema(description = "태그 PK")
    private Long tagId;
    @Schema(description = "태그 이름")
    private String tagName;

    @Builder
    public TagResult(Long tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }
}
