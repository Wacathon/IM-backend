package com.namecard.tag.repository;

import com.namecard.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findTagByTagName(String tagName);
}
