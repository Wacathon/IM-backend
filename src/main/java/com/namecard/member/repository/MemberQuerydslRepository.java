package com.namecard.member.repository;

import com.namecard.search.dto.result.SearchUsersResult;

import java.util.List;

public interface MemberQuerydslRepository {
    List<SearchUsersResult> findLikeUserName(String userName);
}
