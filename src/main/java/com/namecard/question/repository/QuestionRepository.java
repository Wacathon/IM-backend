package com.namecard.question.repository;

import com.namecard.member.dto.entity.Users;
import com.namecard.question.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Integer countQuestionsByUsers(Users users);

    List<Question> findQuestionsByUsers(Users users);
}
