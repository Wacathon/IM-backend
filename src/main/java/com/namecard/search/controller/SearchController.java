package com.namecard.search.controller;

import com.namecard.config.ApiResultUtil.ApiResult;
import com.namecard.member.service.MemberService;
import com.namecard.search.dto.result.SearchUsersResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.namecard.config.ApiResultUtil.success;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final MemberService memberService;

    @GetMapping("/users")
    public ApiResult<List<SearchUsersResult>> userSearch(String userName) {
        return success(memberService.getSearchUsers(userName));
    }
}
