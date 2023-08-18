package com.namecard.tag.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NewTagRequest {

    @Schema(description = "새로 추가할 역량 태그", example = "성실성")
    @NotBlank(message = "태그는 필수 입력 값입니다.")
    private String tagName;
}
