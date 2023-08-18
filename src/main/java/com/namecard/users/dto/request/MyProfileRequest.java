package com.namecard.users.dto.request;

import lombok.Data;

@Data
public class MyProfileRequest {
    private String phoneNum;
    private String email;
    private String introduce;
}
