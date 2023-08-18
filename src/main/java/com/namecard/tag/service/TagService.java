package com.namecard.tag.service;

import com.namecard.exception.DuplicateTagNameException;
import com.namecard.tag.domain.TagRepository;
import com.namecard.tag.dto.entity.Tag;
import com.namecard.tag.dto.request.NewTagRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public void saveNewTag(NewTagRequest tagRequest) {
        // 공백 제거
        String tagRemovedSpace = tagRequest.getTagName().replace(" ", "");

        // 태그 중복 체크
        if (tagRepository.findTagByTagName(tagRemovedSpace).isPresent()) {
            throw new DuplicateTagNameException("이미 존재하는 태그입니다.");
        }

        Tag tag = Tag.builder().tagName(tagRemovedSpace).build();
        tagRepository.save(tag);
    }
}
