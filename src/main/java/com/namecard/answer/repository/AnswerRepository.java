package com.namecard.answer.repository;

import com.namecard.answer.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
