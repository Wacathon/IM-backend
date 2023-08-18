package com.namecard.users.domain;

import com.namecard.search.dto.result.SearchUsersResult;

import java.util.List;

public interface UsersQuerydslRepository {
    List<SearchUsersResult> findLikeUserName(String userName);
}
