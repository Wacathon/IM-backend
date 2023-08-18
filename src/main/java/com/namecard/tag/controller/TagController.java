package com.namecard.tag.controller;

import com.namecard.config.ApiResultUtil.ApiResult;
import com.namecard.tag.dto.request.NewTagRequest;
import com.namecard.tag.dto.result.TagResult;
import com.namecard.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.namecard.config.ApiResultUtil.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tag")
public class TagController {

    private final TagService tagService;

    @PostMapping("")
    public ApiResult<Boolean> addTag(
            @RequestBody NewTagRequest tagRequest
    ) {
        tagService.saveNewTag(tagRequest);
        return success();
    }

    @GetMapping("")
    public ApiResult<List<TagResult>> loadAllTags() {
        return success(tagService.getAllTags());
    }
}
