package com.namecard.search.dto.result;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchUsersResult {
    private long userId;
    private String userName;
    private String introduce;
}
