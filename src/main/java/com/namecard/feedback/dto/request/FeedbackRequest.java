package com.namecard.feedback.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FeedbackRequest {

    @Schema(description = "공통 질문에 대한 답변", example = "이 사람은 ~~한 것이 좋은 것 같습니다.")
    @NotBlank(message = "공통 질문 답변은 필수 입력 값입니다.")
    private String commonAnswer;

    @Schema(description = "피드백 대상자와의 관계", example = "SCHOOL_COLLEAGUE")
    @NotBlank(message = "대상자와의 관계는 필수 입력 값입니다.")
    private String relationship;

}
