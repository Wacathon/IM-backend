package com.namecard.tag.service;

import com.namecard.tag.domain.TagRepository;
import com.namecard.tag.dto.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;

    public void tagSave() {
        Tag tag = Tag.builder()
                .tagName("성실성")
                .build();
        tagRepository.save(tag);
    }
}
