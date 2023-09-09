package com.namecard.member.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MyProfileRequest {
    private String phoneNum;
    private String email;
    private String introduce;
    @JsonProperty("isPublicEmail")
    private boolean isPublicEmail;
    @JsonProperty("isPublicPhone")
    private boolean isPublicPhone;
    @JsonProperty("isPublicIntroduce")
    private boolean isPublicIntroduce;
}
