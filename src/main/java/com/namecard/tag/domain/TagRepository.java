package com.namecard.tag.domain;

import com.namecard.tag.dto.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface TagRepository extends JpaRepository<Tag, Long> {
}
