package com.namecard.tag.controller;

import com.namecard.config.ApiResultUtil.ApiResult;
import com.namecard.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.namecard.config.ApiResultUtil.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tag")
public class TagController {

    private final TagService tagService;

    @PostMapping("/")
    public ApiResult<Boolean> addTag() {
        tagService.tagSave();
        return success();
    }
}
